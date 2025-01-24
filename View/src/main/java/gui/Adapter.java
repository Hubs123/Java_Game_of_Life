package gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import komponentowe.GameOfLifeCell;

public class Adapter {
    private final GameOfLifeCell cell;
    private final BooleanProperty cellProperty;

    public Adapter(GameOfLifeCell cell) {
        this.cell = cell;
        this.cellProperty = new SimpleBooleanProperty(cell.getCellState());

        this.cellProperty.addListener((observable, oldValue, newValue) -> {
            cell.setCellState(newValue);
        });
    }

    public boolean getCellState() {
        return cellProperty.get();
    }

    public void setCellState(boolean state) {
        cellProperty.set(state);
    }

    public BooleanProperty getCellProperty() {
        return cellProperty;
    }
}
