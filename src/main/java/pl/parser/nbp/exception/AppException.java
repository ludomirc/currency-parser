package pl.parser.nbp.exception;

/**
 * Created by Benek on 09.04.2017.
 */
public class AppException extends Exception {

    protected ErrorCode errorCode;

    public AppException(ErrorCode errorCode, String message) {
        super(errorCode.getCodeId() + ", " + errorCode.getMessage() + ", message: " + message);
        this.errorCode = errorCode;
    }

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
        extractErrorCode(cause);
    }

    public AppException(Throwable cause, ErrorCode errorCode, String message) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public AppException(Throwable cause) {
        super(cause);
        extractErrorCode(cause);

    }

    private void extractErrorCode(Throwable cause) {
        if (cause instanceof AppException) {
            errorCode = ((AppException) cause).errorCode;
        } else {
            errorCode = ErrorCode.ErrorCode_0001;
        }
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void extractErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
