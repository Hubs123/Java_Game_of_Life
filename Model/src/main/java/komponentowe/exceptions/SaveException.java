package komponentowe.exceptions;

public class SaveException extends DaoException {
    public SaveException(Throwable cause) {
        super(cause);
    }

    public SaveException() {
        super();
    }
}