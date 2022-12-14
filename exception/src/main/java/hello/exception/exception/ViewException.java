package hello.exception.exception;

public class ViewException extends RuntimeException {

    public ViewException() {
        super();
    }

    public ViewException(String message) {
        super(message);
    }

    public ViewException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewException(Throwable cause) {
        super(cause);
    }

    protected ViewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
