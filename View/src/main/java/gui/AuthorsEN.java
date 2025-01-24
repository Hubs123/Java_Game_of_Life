package gui;

import java.util.ListResourceBundle;

public class AuthorsEN extends ListResourceBundle {
    private static final Object[][] contents = {
            {"author.1", "Hubert Pacyna, 251603"},
            {"author.2", "Piotr Wlazlo, 251662"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
