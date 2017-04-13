package pl.parser.nbp.exception;

/**
 * Created by Benek on 13.04.2017.
 */
public class TechnicalException extends AppException {

    public TechnicalException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public TechnicalException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalException(Throwable cause) {
        super(cause);
    }
}
