package komponentowe;

import komponentowe.exceptions.*;

import java.sql.*;
import java.util.ArrayList;

public class JdbcGameOfLifeBoardDao implements Dao<GameOfLifeBoard> {
    private final Connection connection;
    private final PreparedStatement listBoardsStatement;
    private final PreparedStatement deleteBoardStatement;
    private final PreparedStatement readBoardPreparedStatement;
    private final PreparedStatement updateBoardPreparedStatement;
    private final PreparedStatement insertBoardPreparedStatement;
    private final PreparedStatement readAllCellsForBoardStatement;
    private final PreparedStatement insertCellStatement;
    private final PreparedStatement deleteCellsStatement;

    public JdbcGameOfLifeBoardDao(Connection connection) throws SqlException {
        this.connection = connection;
        try {
            listBoardsStatement = connection.prepareStatement("SELECT boardName FROM boards ORDER BY boardID");
            deleteBoardStatement = connection.prepareStatement("DELETE FROM boards WHERE boardName = ?");
            readAllCellsForBoardStatement = connection.prepareStatement("SELECT rowIndex, columnIndex, value FROM "
                    + "cells WHERE boardID = ?");
            readBoardPreparedStatement = connection.prepareStatement("SELECT boardID, rows, columns FROM boards "
                    + "WHERE boardName = ?");
            updateBoardPreparedStatement = connection.prepareStatement("UPDATE boards SET rows = ?, columns = ? "
                    + "WHERE boardID = ?");
            insertBoardPreparedStatement = connection.prepareStatement("INSERT INTO boards "
                    + "(boardName, rows, columns) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertCellStatement = connection.prepareStatement("INSERT INTO cells "
                    + "(cellID, boardID, rowIndex, columnIndex, value) VALUES (?, ?, ?, ?, ?)");
            deleteCellsStatement = connection.prepareStatement("DELETE FROM cells WHERE boardID = ?");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            close();
            throw new SqlException(e);
        }
    }

    @Override
    public GameOfLifeBoard read(String boardName) throws LoadException {
        try {
            int boardID;
            int rows;
            int columns;
            readBoardPreparedStatement.setString(1, boardName);
            try (ResultSet rs = readBoardPreparedStatement.executeQuery()) {
                if (!rs.next()) {
                    throw new LoadException(new SQLException());
                }
                boardID = rs.getInt("boardID");
                rows = rs.getInt("rows");
                columns = rs.getInt("columns");
            }

            GameOfLifeBoard board = new GameOfLifeBoard(rows, columns);
            board.setSimulator(new PlainGameOfLifeSimulator());

            readAllCellsForBoardStatement.setInt(1, boardID);
            try (ResultSet rs = readAllCellsForBoardStatement.executeQuery()) {
                while (rs.next()) {
                    int row = rs.getInt("rowIndex") - 1;
                    int col = rs.getInt("columnIndex") - 1;
                    boolean state = rs.getBoolean("value");
                    board.setCellState(row, col, state);
                }
            }
            connection.commit();
            return board;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.addSuppressed(ex);
            }
            throw new LoadException(e);
        }
    }

    @Override
    public void write(String boardName, GameOfLifeBoard board) throws SaveException {
        try {
            if (boardName == null || boardName.trim().isEmpty()) {
                throw new SaveException();
            }
        } catch (SaveException e) {
            throw new RuntimeException(e);
        }

        try {
            int boardID = -1;
            readBoardPreparedStatement.setString(1, boardName);
            try (ResultSet rs = readBoardPreparedStatement.executeQuery()) {
                if (rs.next()) {
                    boardID = rs.getInt("boardID");
                }
            }
            if (boardID == -1) {
                insertBoardPreparedStatement.setString(1, boardName);
                insertBoardPreparedStatement.setInt(2, board.getNumberOfRows());
                insertBoardPreparedStatement.setInt(3, board.getNumberOfColumns());
                insertBoardPreparedStatement.executeUpdate();
                readBoardPreparedStatement.setString(1, boardName);
                try (ResultSet rs = insertBoardPreparedStatement.getGeneratedKeys()) {
                    rs.next();
                    boardID = rs.getInt(1);
                }
            } else {
                updateBoardPreparedStatement.setInt(1, board.getNumberOfRows());
                updateBoardPreparedStatement.setInt(2, board.getNumberOfColumns());
                updateBoardPreparedStatement.setInt(3, boardID);
                updateBoardPreparedStatement.executeUpdate();
                deleteCellsStatement.setInt(1, boardID);
                deleteCellsStatement.executeUpdate();
            }
            int startCellId = 1;
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COALESCE(MAX(cellID), 0) + 1 FROM cells");
                if (rs.next()) {
                    startCellId = rs.getInt(1);
                }
            }

            int cellID = startCellId;            for (int i = 0; i < board.getNumberOfRows(); ++i) {
                for (int j = 0; j < board.getNumberOfColumns(); ++j) {
                    insertCellStatement.setInt(1, cellID++);
                    insertCellStatement.setInt(2, boardID);
                    insertCellStatement.setInt(3, i + 1);
                    insertCellStatement.setInt(4, j + 1);
                    insertCellStatement.setBoolean(5, board.getCellState(i, j));
                    insertCellStatement.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.addSuppressed(ex);
            }
            throw new SaveException(e);
        }
    }

    @Override
    public ArrayList<String> showNameOfBoards() throws LoadException {
        ArrayList<String> result = new ArrayList<>();
        try {
            try (ResultSet rs = listBoardsStatement.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString(1));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new LoadException(e);
        }
    }

    @Override
    public void removeBoard(String boardName) throws SaveException {
        try {
            int boardID = -1;
            readBoardPreparedStatement.setString(1, boardName);
            try (ResultSet rs = readBoardPreparedStatement.executeQuery()) {
                if (rs.next()) {
                    boardID = rs.getInt("boardID");
                }
            }
            deleteCellsStatement.setInt(1, boardID);
            deleteCellsStatement.executeUpdate();
            deleteBoardStatement.setString(1, boardName);
            deleteBoardStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.addSuppressed(ex);
            }
            throw new SaveException(e);
        }
    }

    @Override
    public void close() {
        closeAll(
                listBoardsStatement,
                deleteBoardStatement,
                readBoardPreparedStatement,
                updateBoardPreparedStatement,
                insertBoardPreparedStatement,
                readAllCellsForBoardStatement,
                insertCellStatement,
                deleteCellsStatement,
                connection
        );
    }

    private void closeAll(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource == null) {
                continue;
            }
            try {
                resource.close();
            } catch (Exception e) {
                ignoreException();
            }
        }
    }

    private void ignoreException() {
    }
}
