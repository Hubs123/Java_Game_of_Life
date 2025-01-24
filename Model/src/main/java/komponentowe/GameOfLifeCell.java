/*
 * This file is part of Game Of Life jm_cz_1345_03 by Hubert Pacyna and Piotr Wlazło.
 *
 * Licensed under the MIT License. See the LICENSE file in the project root for more information.
 */

package komponentowe;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import komponentowe.exceptions.CloneException;
import komponentowe.exceptions.InvalidArgumentException;
import komponentowe.exceptions.NullPtrException;

import java.io.Serializable;

public class GameOfLifeCell implements Serializable, Comparable<GameOfLifeCell>, Cloneable {
    private boolean cellState;
    private GameOfLifeCell[] neighbors;

    public GameOfLifeCell(boolean cellState) {
        this.cellState = cellState;
        this.neighbors = new GameOfLifeCell[8];
    }

    @Override
    public GameOfLifeCell clone() throws CloneException {
        try {
            GameOfLifeCell clone = (GameOfLifeCell) super.clone();

            clone.cellState = this.cellState;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new CloneException();
        }
    }

    public boolean getCellState() {
        return cellState;
    }

    public void setCellState(boolean cellState) {
        this.cellState = cellState;
    }

    public void setNeighbors(GameOfLifeCell[] neighbors) {
        if (neighbors.length != 8) {
            throw new InvalidArgumentException();
        }
        this.neighbors = neighbors;
    }

    public GameOfLifeCell[] getNeighbors() {
        return neighbors;
    }

    public boolean nextState() {
        int livingNeighbors = 0;

        for (GameOfLifeCell neighbor : neighbors) {
            if (neighbor != null && neighbor.getCellState()) {
                livingNeighbors++;
            }
        }
        return livingNeighbors == 3 || (livingNeighbors == 2 && this.getCellState());
    }

    public void updateState(boolean nextState) {
        this.cellState = nextState;
    }

    private int countLivingNeighbors() {
        int count = 0;
        for (GameOfLifeCell neighbor : neighbors) {
            if (neighbor != null && neighbor.getCellState()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cellState", cellState)
                .add("livingNeighbors", countLivingNeighbors())
                .toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object == null || getClass() != object.getClass()) {
            return false;
        }

        GameOfLifeCell cell = (GameOfLifeCell) object;

        return Objects.equal(cellState, cell.cellState);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cellState);
    }

    @Override
    public int compareTo(GameOfLifeCell cell) {
        int result;

        try {
            result = Boolean.compare(this.cellState, cell.cellState);
        } catch (NullPointerException e) {
            throw new NullPtrException();
        }

        return result;
    }
}
