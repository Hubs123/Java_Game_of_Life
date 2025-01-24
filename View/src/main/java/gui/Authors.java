package gui;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {
    private static final Object[][] contents = {
            {"author.1", "Hubert Pacyna, 251603"},
            {"author.2", "Piotr Wlazło, 251662"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
