package pl.parser.nbp.exception;

/**
 * Created by Benek on 11.04.2017.
 */
public class DirectoryNotFoundException extends AppException {


    public DirectoryNotFoundException(String message) {
        super(ErrorCode.ErrorCode_2001, message);
    }

    public DirectoryNotFoundException() {
        super(ErrorCode.ErrorCode_2001);
    }
}
