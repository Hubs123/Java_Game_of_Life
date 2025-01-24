package gui.exceptions;

public class ViewSaveException extends ViewException {
    public ViewSaveException(Throwable cause) {
        super(cause);
    }

    @Override
    protected String getLocaleKey() {
        return "saveException";
    }
}
