/*
 * This file is part of Game Of Life jm_cz_1345_03 by Hubert Pacyna and Piotr Wlazło.
 *
 * Licensed under the MIT License. See the LICENSE file in the project root for more information.
 */

package komponentowe;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import komponentowe.exceptions.CloneException;

import java.util.ArrayList;
import java.util.List;

public abstract class GameOfLifeAxis implements Cloneable {
    protected List<GameOfLifeCell> axis;

    public int countAliveCells() {
        int count = 0;

        for (GameOfLifeCell cell : axis) {
            if (cell.getCellState()) {
                count++;
            }
        }
        return count;
    }

    public int countDeadCells() {
        return axis.size() - countAliveCells();
    }

    public GameOfLifeCell getCell(int x) {
        return axis.get(x);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("axis", axis.size())
                .add("alive cells", countAliveCells())
                .add("dead cells", countDeadCells())
                .toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof GameOfLifeAxis)) {
            return false;
        }

        GameOfLifeAxis that = (GameOfLifeAxis) object;

        return Objects.equal(axis, that.axis);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(axis);
    }

    @Override
    public GameOfLifeAxis clone() throws CloneException {
        try {
            GameOfLifeAxis copy = (GameOfLifeAxis) super.clone();

            copy.axis = new ArrayList<>();

            for (GameOfLifeCell cell : axis) {
                copy.axis.add(cell.clone());
            }

            return copy;
        } catch (CloneNotSupportedException e) {
            throw new CloneException();
        }
    }
}
