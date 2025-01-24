package gui;

import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class Converter extends StringConverter<Boolean> {

    @Override
    public String toString(Boolean state) {
        if (state) {
            return "alive";
        }
        return "dead";
    }

    @Override
    public Boolean fromString(String string) {
        return "alive".equals(string);
    }

    public Color toColor(Boolean state) {
        if (state) {
            return Color.PURPLE;
        }
        return Color.WHITE;
    }
}
