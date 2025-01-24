/*
 * This file is part of Game Of Life jm_cz_1345_03 by Hubert Pacyna and Piotr Wlazło.
 *
 * Licensed under the MIT License. See the LICENSE file in the project root for more information.
 */

package komponentowe;

import java.util.Arrays;

public class GameOfLifeRow extends GameOfLifeAxis {

    public GameOfLifeRow(GameOfLifeBoard board, int x) {
        axis = Arrays.asList(new GameOfLifeCell[board.getNumberOfColumns()]);

        for (int i = 0; i < board.getNumberOfColumns(); i++) {
            axis.set(i, board.getCell(x, i));
        }
    }
}