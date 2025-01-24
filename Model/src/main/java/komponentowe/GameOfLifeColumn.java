/*
 * This file is part of Game Of Life jm_cz_1345_03 by Hubert Pacyna and Piotr Wlazło.
 *
 * Licensed under the MIT License. See the LICENSE file in the project root for more information.
 */

package komponentowe;

import java.util.Arrays;

public class GameOfLifeColumn extends GameOfLifeAxis {

    public GameOfLifeColumn(GameOfLifeBoard board, int y) {
        axis = Arrays.asList(new GameOfLifeCell[board.getNumberOfRows()]);

        for (int i = 0; i < board.getNumberOfRows(); i++) {
            axis.set(i, board.getCell(i, y));
        }
    }
}