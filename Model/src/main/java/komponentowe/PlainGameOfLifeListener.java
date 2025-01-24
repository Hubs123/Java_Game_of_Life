/*
 * This file is part of Game Of Life jm_cz_1345_03 by Hubert Pacyna and Piotr Wlazło.
 *
 * Licensed under the MIT License. See the LICENSE file in the project root for more information.
 */

package komponentowe;

public class PlainGameOfLifeListener implements GameOfLifeListener {
    public boolean isNotified;

    public PlainGameOfLifeListener() {
        this.isNotified = false;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        this.isNotified = notified;
    }

    @Override
    public void boardChanged() {
        this.isNotified = true;
    }
}
