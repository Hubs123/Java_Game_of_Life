package komponentowe.exceptions;

public class LoadException extends DaoException {
    public LoadException(Throwable cause) {
        super(cause);
    }

    public LoadException() {
        super();
    }
}