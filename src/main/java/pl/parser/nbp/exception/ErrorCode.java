package pl.parser.nbp.exception;

/**
 * Technical ErrorCode code format - Three-digit number, older number even.
 * Business ErrorCode code format - Three-digit number, older number even.
 * Created by Benek on 12.04.2017.
 */
public enum ErrorCode {


    //Technical ErrorCode
    ErrorCode_2000("sample technical error message", 2000),
    ErrorCode_2001("directory not found exception", 2001),
    ErrorCode_2002("wrong number of input parameters", 2002),
    ErrorCode_2003("not supported date format", 2003),
    ErrorCode_2004("network or remote host issue", 2004),

    //Business ErrorCode
    ErrorCode_3000("sample business error message", 3000),
    ErrorCode_3001("not supported currency", 3001),
    ErrorCode_3002("absence of a issue of a currency rate for given period", 3002),
    ErrorCode_3003("bad date sequence", 3003),
    ErrorCode_3004("date in future", 3004),
    ErrorCode_3005("no such a data for the specific period", 3005),

    //Spec code
    ErrorCode_0001("under construction", 1),
    ErrorCode_0002("not yest implanted", 2);

    private String message;
    private int id;

    ErrorCode(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public int getCodeId() {
        return id;
    }
}
