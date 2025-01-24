/*
 * This file is part of Game Of Life jm_cz_1345_03 by Hubert Pacyna and Piotr Wlazło.
 *
 * Licensed under the MIT License. See the LICENSE file in the project root for more information.
 */

package komponentowe;

import java.io.Serializable;

public class PlainGameOfLifeSimulator implements GameOfLifeSimulator, Serializable {
    @Override
    public void doStep(GameOfLifeBoard board) {
        int rows = board.getNumberOfRows();
        int columns = board.getNumberOfColumns();
        boolean[][] newBoard = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newBoard[i][j] = board.getCell(i, j).nextState();
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board.getCell(i, j).updateState(newBoard[i][j]);
            }
        }
    }
}
