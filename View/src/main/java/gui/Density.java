package gui;

import komponentowe.GameOfLifeBoard;

import java.util.Random;
import java.util.ResourceBundle;

public enum Density {
    MALE("male", 0.1F),
    SREDNIE("srednie", 0.3F),
    DUZE("duze", 0.5F);

    private final String text;
    private final float density;
    private ResourceBundle bundle;

    Density(String text, float density) {
        this.text = text;
        this.density = density;
    }

    public float getDensity() {
        return density;
    }

    @Override
    public String toString() {
        return bundle.getString("density." + text);
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }


    public GameOfLifeBoard setBoardDensity(GameOfLifeBoard board) {
        Random random = new Random();
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfColumns(); j++) {
                board.getCell(i, j).setCellState(false);
                board.getCell(i, j).setCellState(random.nextFloat() < this.density);
            }
        }

        return board;
    }

}
