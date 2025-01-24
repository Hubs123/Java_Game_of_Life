package komponentowe;

import komponentowe.exceptions.LoadException;
import komponentowe.exceptions.SaveException;
import komponentowe.exceptions.SqlException;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JdbcGameOfLifeDaoTest {

    private static JdbcGameOfLifeDaoFactoryDataBase factory;
    private static Dao<GameOfLifeBoard> dao;

    @BeforeAll
    static void setUp() throws SqlException {
        factory = new JdbcGameOfLifeDaoFactoryDataBase();
        try (Connection connection = JdbcGameOfLifeDaoFactoryDataBase.openNewConnection()) {
            JdbcGameOfLifeDaoFactoryDataBase.createTables(connection);
            dao = factory.getGameOfLifeBoardDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void tearDown() {
        if (dao != null) {
            dao.close();
        }
    }

    @AfterEach
    void cleanUp() throws SaveException {
        ArrayList<String> boards;
        try {
            boards = dao.showNameOfBoards();
            for (String boardName : boards) {
                dao.removeBoard(boardName);
            }
        } catch (LoadException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testWriteAndRead() throws Exception {
        GameOfLifeBoard board = new GameOfLifeBoard(5, 5);
        board.setSimulator(new PlainGameOfLifeSimulator());
        String boardName = "testBoard";

        board.setCellState(1, 1, true);
        board.setCellState(2, 2, true);
        board.setCellState(3, 3, true);

        dao.write(boardName, board);
        GameOfLifeBoard readBoard = dao.read(boardName);

        assertEquals(board.getNumberOfRows(), readBoard.getNumberOfRows());
        assertEquals(board.getNumberOfColumns(), readBoard.getNumberOfColumns());

        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfColumns(); j++) {
                assertEquals(board.getCellState(i, j), readBoard.getCellState(i, j));
            }
        }
    }

    @Test
    void testShowNameOfBoards() throws Exception {
        GameOfLifeBoard board1 = new GameOfLifeBoard(5, 5);
        GameOfLifeBoard board2 = new GameOfLifeBoard(5, 5);
        String boardName1 = "testBoard1";
        String boardName2 = "testBoard2";

        dao.write(boardName1, board1);
        dao.write(boardName2, board2);
        ArrayList<String> boardNames = dao.showNameOfBoards();

        assertEquals(2, boardNames.size());
        assertTrue(boardNames.contains(boardName1));
        assertTrue(boardNames.contains(boardName2));
    }

    @Test
    void testRemoveBoard() throws Exception {
        GameOfLifeBoard board = new GameOfLifeBoard(5, 5);
        String boardName = "testBoard";

        dao.write(boardName, board);
        dao.removeBoard(boardName);
        ArrayList<String> boardNames = dao.showNameOfBoards();

        assertTrue(boardNames.isEmpty());
    }

    @Test
    void testReadNonExistentBoard() {
        String nonExistentBoardName = "nonExistentBoard";

        assertThrows(LoadException.class, () -> dao.read(nonExistentBoardName));
    }

    @Test
    void testUpdateExistingBoard() throws Exception {
        GameOfLifeBoard originalBoard = new GameOfLifeBoard(5, 5);
        GameOfLifeBoard updatedBoard = new GameOfLifeBoard(6, 6);
        String boardName = "testBoard";

        originalBoard.setCellState(1, 1, true);
        updatedBoard.setCellState(2, 2, true);

        dao.write(boardName, originalBoard);
        dao.write(boardName, updatedBoard);
        GameOfLifeBoard readBoard = dao.read(boardName);

        assertEquals(updatedBoard.getNumberOfRows(), readBoard.getNumberOfRows());
        assertEquals(updatedBoard.getNumberOfColumns(), readBoard.getNumberOfColumns());
        assertEquals(updatedBoard.getCellState(2, 2), readBoard.getCellState(2, 2));
    }
}
