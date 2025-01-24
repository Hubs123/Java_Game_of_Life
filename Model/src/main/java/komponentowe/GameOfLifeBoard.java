/*
 * This file is part of Game Of Life jm_cz_1345_03 by Hubert Pacyna and Piotr Wlazło.
 *
 * Licensed under the MIT License. See the LICENSE file in the project root for more information.
 */

package komponentowe;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import komponentowe.exceptions.CloneException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameOfLifeBoard implements Serializable, Cloneable {
    private GameOfLifeCell[][] board;
    private final int rows;
    private final int columns;
    private GameOfLifeSimulator simulator;
    private List<GameOfLifeListener> listeners;

    public GameOfLifeBoard(int rows, int columns) {
        this.rows = Math.max(rows, 5);
        this.columns = Math.max(columns, 5);
        this.board = new GameOfLifeCell[rows][columns];
        this.simulator = new PlainGameOfLifeSimulator();
        this.listeners = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new GameOfLifeCell(random.nextBoolean());
            }
        }
        assignNeighbors(board);
    }

    public void addListener(GameOfLifeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameOfLifeListener listener) {
        listeners.remove(listener);
    }

    public List<GameOfLifeListener> getListeners() {
        return listeners;
    }

    private void assignNeighbors(GameOfLifeCell[][] board) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                GameOfLifeCell[] neighbors = new GameOfLifeCell[8];
                int index = 0;

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (k == 0 && l == 0) {
                            continue;
                        }

                        int x = (i + k + rows) % rows;
                        int y = (j + l + columns) % columns;

                        neighbors[index++] = board[x][y];
                    }
                }
                board[i][j].setNeighbors(neighbors);
            }
        }
    }

    public GameOfLifeCell[][] getBoard() {
        return copyBoard();
    }

    private GameOfLifeCell[][] copyBoard() {
        GameOfLifeCell[][] copy = new GameOfLifeCell[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                copy[i][j] = new GameOfLifeCell(board[i][j].getCellState());
            }
        }

        assignNeighbors(copy);

        return copy;
    }

    @Override
    public GameOfLifeBoard clone() throws CloneException {
        try {
            GameOfLifeBoard clone = (GameOfLifeBoard) super.clone();

            clone.board = this.copyBoard();

            clone.listeners = new ArrayList<>(this.listeners);

            clone.simulator = this.simulator;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new CloneException();
        }
    }

    public void setBoard(GameOfLifeCell[][] newBoard) {
        board = new GameOfLifeCell[rows][columns];

        for (int i = 0; i < rows; i++) {
            board[i] = Arrays.copyOf(newBoard[i], columns);
        }

        assignNeighbors(board);
    }

    public int getNumberOfRows() {
        return rows;
    }

    public int getNumberOfColumns() {
        return columns;
    }

    public GameOfLifeRow getRow(int x) {
        return new GameOfLifeRow(this, x);
    }

    public GameOfLifeColumn getColumn(int y) {
        return new GameOfLifeColumn(this, y);
    }

    public GameOfLifeCell getCell(int row, int column) {
        return board[row][column];
    }

    public void setSimulator(GameOfLifeSimulator simulator) {
        this.simulator = simulator;
    }

    public void doSimulationStep() {
        simulator.doStep(this);
        for (GameOfLifeListener listener : listeners) {
            listener.boardChanged();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("board", Arrays.deepToString(board))
                .add("rows", rows)
                .add("columns", columns)
                .add("simulator", simulator)
                .add("listeners", listeners)
                .toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object == null || getClass() != object.getClass()) {
            return false;
        }

        GameOfLifeBoard board = (GameOfLifeBoard) object;

        return rows == board.rows
                && columns == board.columns;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(board, rows, columns);
    }

    public boolean getCellState(int row, int column) {
        return board[row][column].getCellState();
    }

    public void setCellState(int row, int column, boolean state) {
        board[row][column].setCellState(state);
    }
}
