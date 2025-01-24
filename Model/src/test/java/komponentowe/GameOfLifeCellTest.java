package komponentowe;/*
 * This file is part of Game Of Life jm_cz_1345_03 by Hubert Pacyna and Piotr Wlazło.
 *
 * Licensed under the MIT License. See the LICENSE file in the project root for more information.
 */

import komponentowe.exceptions.NullPtrException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeCellTest {

    private GameOfLifeBoard board;

    @BeforeEach
    void setUp() {
        board = new GameOfLifeBoard(5, 5);
    }

    @Test
    public void testSetCellState() {
        GameOfLifeCell cell = new GameOfLifeCell(true);
        cell.setCellState(false);
        assertFalse(cell.getCellState());
    }


    @Test
    public void testConstructorNeighbor() {
        GameOfLifeCell cell1 = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = new GameOfLifeCell(false);
        GameOfLifeCell cell3 = new GameOfLifeCell(false);
        GameOfLifeCell cell4 = new GameOfLifeCell(false);
        GameOfLifeCell cell5 = new GameOfLifeCell(false);
        GameOfLifeCell cell6 = new GameOfLifeCell(false);
        GameOfLifeCell cell7 = new GameOfLifeCell(false);
        GameOfLifeCell cell8 = new GameOfLifeCell(false);

        GameOfLifeCell[] cells = {cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8};

        GameOfLifeCell cell = new GameOfLifeCell(true);
        cell.setNeighbors(cells);

        GameOfLifeCell[] neighbors = cell.getNeighbors();
        assertEquals(cells.length, neighbors.length);

        GameOfLifeCell[] incorrectNeighbors = new GameOfLifeCell[7];

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cell.setNeighbors(incorrectNeighbors);
        });

        GameOfLifeCell nullCell = null;
        GameOfLifeCell[] neighbors2 = new GameOfLifeCell[8];
        neighbors[0] = nullCell;
        neighbors[1] = cell1;
        neighbors[2] = cell2;

        cell1.setNeighbors(neighbors2);
        boolean nextState = cell1.nextState();
        assertFalse(nextState);
    }

    @Test
    public void testCellToString() {
        GameOfLifeCell cell = new GameOfLifeCell(true);
        String text = cell.toString();
        assertTrue(text.contains("cellState=true"));
    }

    @Test
    public void testEquals() {
        GameOfLifeCell cell1 = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = new GameOfLifeCell(false);
        GameOfLifeCell cell3 = new GameOfLifeCell(true);

        assertTrue(cell1.equals(cell3));
        assertFalse(cell1.equals(cell2));
        assertFalse(cell1.equals(null));
        assertFalse(cell1.equals(board));
    }

    @Test
    public void testCompareTo() {
        GameOfLifeCell cell1 = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = new GameOfLifeCell(true);
        GameOfLifeCell cell3 = new GameOfLifeCell(false);

        assertNotSame(cell1, cell2);
        assertEquals(-1, cell3.compareTo(cell1));
        assertEquals(0, cell1.compareTo(cell2));
        assertEquals(1, cell1.compareTo(cell3));


        assertThrows(NullPtrException.class, () -> {cell1.compareTo(null);});
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        GameOfLifeCell cell1 = new GameOfLifeCell(true);

        GameOfLifeCell neighbor1 = new GameOfLifeCell(false);
        GameOfLifeCell neighbor2 = new GameOfLifeCell(false);
        GameOfLifeCell neighbor3 = new GameOfLifeCell(true);
        GameOfLifeCell neighbor4 = new GameOfLifeCell(false);
        GameOfLifeCell neighbor5 = new GameOfLifeCell(true);
        GameOfLifeCell neighbor6 = new GameOfLifeCell(true);
        GameOfLifeCell neighbor7 = new GameOfLifeCell(false);
        GameOfLifeCell neighbor8 = new GameOfLifeCell(false);

        GameOfLifeCell[] neighbors = {neighbor1, neighbor2, neighbor3, neighbor4, neighbor5, neighbor6, neighbor7, neighbor8};

        cell1.setNeighbors(neighbors);

        GameOfLifeCell clone = cell1.clone();

        assertNotSame(cell1, clone);
        assertEquals(cell1.getCellState(), clone.getCellState());

        clone.setCellState(false);
        assertNotEquals(cell1.getCellState(), clone.getCellState());
    }

}