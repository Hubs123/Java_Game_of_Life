package komponentowe;/*
 * This file is part of Game Of Life jm_cz_1345_03 by Hubert Pacyna and Piotr Wlazło.
 *
 * Licensed under the MIT License. See the LICENSE file in the project root for more information.
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeAxisTest {

    private GameOfLifeBoard board;

    @BeforeEach
    void setUp() {
        board = new GameOfLifeBoard(5, 5);
        GameOfLifeCell[][] test = {
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };
        board.setBoard(test);
    }

    @Test
    public void testGameOfLifeRowInitialization() {
        GameOfLifeAxis row = new GameOfLifeRow(board, 1);

        for (int i = 0; i < 5; i++) {
            assertEquals(board.getCell(i, 1).getCellState(), board.getRow(1).getCell(i).getCellState());
        }

        assertEquals(2, row.countAliveCells());
        assertEquals(3, row.countDeadCells());
    }

    @Test
    public void testGameOfLifeRowCountAliveAndDeadCells() {
        GameOfLifeAxis row = new GameOfLifeRow(board, 1);

        assertEquals(2, row.countAliveCells());
        assertEquals(3, row.countDeadCells());
    }

    @Test
    public void testGameOfLifeColumnInitialization() {
        GameOfLifeAxis column = new GameOfLifeColumn(board, 1);

        for (int i = 0; i < 5; i++) {
            assertEquals(board.getCell(1, i).getCellState(), board.getColumn(1).getCell(i).getCellState());
        }

        assertEquals(2, column.countAliveCells());
        assertEquals(3, column.countDeadCells());
    }

    @Test
    public void testGameOfLifeColumnCountAliveAndDeadCells() {
        GameOfLifeAxis column = new GameOfLifeColumn(board, 1);

        assertEquals(2, column.countAliveCells());
        assertEquals(3, column.countDeadCells());
    }

    @Test
    public void testRowToString() {
        GameOfLifeAxis row = new GameOfLifeRow(board, 1);

        String text = row.toString();

        assertTrue(text.contains("axis"));
        assertTrue(text.contains("alive cells"));
        assertTrue(text.contains("dead cells"));
        assertTrue(text.contains("2"));
        assertTrue(text.contains("3"));
    }

    @Test
    public void testColumnToString() {
        GameOfLifeAxis row = new GameOfLifeRow(board, 1);

        String text = row.toString();

        assertTrue(text.contains("axis"));
        assertTrue(text.contains("alive cells"));
        assertTrue(text.contains("dead cells"));
        assertTrue(text.contains("2"));
        assertTrue(text.contains("3"));
    }

    @Test
    public void testEquals() {
        GameOfLifeAxis row = new GameOfLifeRow(board, 1);
        GameOfLifeAxis row2 = new GameOfLifeRow(board, 1);
        GameOfLifeAxis row3 = new GameOfLifeRow(board, 0);
        GameOfLifeAxis column = new GameOfLifeColumn(board, 0);

        assertTrue(row.equals(row));
        assertTrue(row.equals(row2));
        assertFalse(row.equals(row3));
        assertFalse(row.equals(null));
        assertFalse(row.equals(column));
    }

    @Test
    public void testHashCode() {
        GameOfLifeAxis row = new GameOfLifeRow(board, 1);
        GameOfLifeAxis row2 = new GameOfLifeRow(board, 1);
        GameOfLifeAxis row3 = new GameOfLifeRow(board, 0);
        GameOfLifeAxis column = new GameOfLifeColumn(board, 0);

        assertEquals(row.hashCode(), row.hashCode());
        assertEquals(row.hashCode(), row2.hashCode());
        assertNotEquals(row.hashCode(), row3.hashCode());
        assertNotEquals(row.hashCode(), column.hashCode());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        GameOfLifeAxis row = new GameOfLifeRow(board, 1);
        GameOfLifeAxis cloneRow = row.clone();
        assertEquals(row, cloneRow);

        GameOfLifeAxis column = new GameOfLifeColumn(board, 0);
        GameOfLifeAxis cloneColumn = column.clone();
        assertEquals(column, cloneColumn);
    }
}
