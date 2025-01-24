package gui.exceptions;

public class ViewLoadException extends ViewException {
    public ViewLoadException(Throwable cause) {
        super(cause);
    }

    @Override
    protected String getLocaleKey() {
        return "loadException";
    }
}
