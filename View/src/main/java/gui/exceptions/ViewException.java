package gui.exceptions;

import gui.App;

import java.util.ResourceBundle;

public abstract class ViewException extends RuntimeException {

    private final ResourceBundle bundle = ResourceBundle.getBundle("gui.text", App.currentLocale);

    public ViewException(Throwable cause) {
        super(cause);
    }

    public ViewException() {
        super();
    }

    protected abstract String getLocaleKey();

    @Override
    public String getLocalizedMessage() {
        return bundle.getString(getLocaleKey());
    }
}
