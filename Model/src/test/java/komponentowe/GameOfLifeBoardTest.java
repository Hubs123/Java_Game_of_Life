package komponentowe;/*
 * This file is part of Game Of Life jm_cz_1345_03 by Hubert Pacyna and Piotr Wlazło.
 *
 * Licensed under the MIT License. See the LICENSE file in the project root for more information.
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeBoardTest {

    private GameOfLifeBoard board;

    @BeforeEach
    void setUp() {
        board = new GameOfLifeBoard(5, 5);
        PlainGameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
        board.setSimulator(simulator);
    }

    @Test
    public void testDoStep() {
        GameOfLifeCell[][] initialState = {
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };
        board.setBoard(initialState);
        board.doSimulationStep();

        GameOfLifeCell[][] expectedState = {
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };

        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfColumns(); j++) {
                assertEquals(expectedState[i][j].getCellState(), board.getCell(i, j).getCellState());

            }
        }
    }

    @Test
    public void testRandomBoard() throws CloneNotSupportedException {
        GameOfLifeBoard board2 = new GameOfLifeBoard(5, 5);

        assertFalse(Arrays.deepEquals(board.getBoard(), board2.getBoard()));
    }

    @Test
    public void testWrapped(){

        GameOfLifeCell[][] initialState = {
                {new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };
        board.setBoard(initialState);
        board.doSimulationStep();

        GameOfLifeCell[][] expectedState = {
                {new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true)}
        };

        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfColumns(); j++) {
                assertEquals(expectedState[i][j].getCellState(), board.getCell(i, j).getCellState());
            }
        }
    }

    @Test
    public void testSetLiving () {

        GameOfLifeCell[][] initialState = {
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };
        board.setBoard(initialState);
        board.doSimulationStep();

        GameOfLifeCell[][] expectedState = {
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };

        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfColumns(); j++) {
                assertEquals(expectedState[i][j].getCellState(), board.getCell(i, j).getCellState());
            }
        }
    }

    @Test
    public void testTwoNeighbours() {
        GameOfLifeCell[][] initialState = {
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };
        board.setBoard(initialState);
        board.doSimulationStep();

        GameOfLifeCell[][] expectedState = {
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };

        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfColumns(); j++) {
                assertEquals(expectedState[i][j].getCellState(), board.getCell(i, j).getCellState());
            }
        }
    }

    @Test
    public void testThreeNeighbours() {
        GameOfLifeCell[][] initialState = {
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };
        board.setBoard(initialState);
        board.doSimulationStep();

        GameOfLifeCell[][] expectedState = {
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };

        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfColumns(); j++) {
                assertEquals(expectedState[i][j].getCellState(), board.getCell(i, j).getCellState());
            }
        }
    }

    @Test
    public void testListeners() {
        PlainGameOfLifeListener listener1 = new PlainGameOfLifeListener();
        PlainGameOfLifeListener listener2 = new PlainGameOfLifeListener();

        assertTrue(board.getListeners().isEmpty());

        board.addListener(listener1);
        board.addListener(listener2);
        board.doSimulationStep();
        assertTrue(listener1.isNotified());
        listener1.setNotified(false);
        board.removeListener(listener2);
        assertEquals(1, board.getListeners().size());
    }

    @Test
    public void testToString() {
        String text = board.toString();
        assertTrue(text.contains("board"));
        assertTrue(text.contains("rows"));
        assertTrue(text.contains("columns"));
        assertTrue(text.contains("simulator"));
        assertTrue(text.contains("listeners"));
    }

    @Test
    public void testEquals() {
        GameOfLifeBoard board1 =  new GameOfLifeBoard(5,5);
        GameOfLifeBoard board2 =  new GameOfLifeBoard(5,5);

        PlainGameOfLifeSimulator simulator2 = new PlainGameOfLifeSimulator();
        PlainGameOfLifeListener listener2 = new PlainGameOfLifeListener();

        GameOfLifeCell[][] test = {
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };

        board1.setSimulator(simulator2);
        board1.addListener(listener2);
        board1.setBoard(test);

        board2.setSimulator(simulator2);
        board2.addListener(listener2);
        board2.setBoard(test);

        assertTrue(board.equals(board));

        assertFalse(board.equals(null));

        assertFalse(board.equals(board.getCell(2,2)));
        assertTrue(board1.equals(board2));

        GameOfLifeBoard board3 =  new GameOfLifeBoard(6,5);
        assertFalse(board.equals(board3));

        GameOfLifeBoard board4 =  new GameOfLifeBoard(5,6);
        assertFalse(board.equals(board4));
    }

    @Test
    public void testHashCode() {
        GameOfLifeBoard board2 =  new GameOfLifeBoard(5,5);

        assertEquals(board.hashCode(), board.hashCode());
        assertNotEquals(board.hashCode(), board2.hashCode());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        GameOfLifeCell[][] test = {
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false), new GameOfLifeCell(false)},
                {new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)}
        };

        board.setBoard(test);

        GameOfLifeBoard board2 = board.clone();
        assertTrue(board.equals(board2));
        assertNotSame(board, board2);

        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfColumns(); j++) {
                assertEquals(board.getCell(i, j).getCellState(), board2.getCell(i, j).getCellState());
            }
        }

        board2.getCell(0,0).setCellState(true);

        assertTrue(board2.getCell(0,0).getCellState());
        assertFalse(board.getCell(0,0).getCellState());
    }
}

