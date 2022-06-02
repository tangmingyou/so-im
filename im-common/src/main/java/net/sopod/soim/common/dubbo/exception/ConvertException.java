package net.sopod.soim.common.dubbo.exception;

/**
 * ConvertException
 *
 * @author tmy
 * @date 2022-06-02 22:58
 */
public class ConvertException extends SoimException {

    public ConvertException() {
        super();
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertException(Throwable cause) {
        super(cause);
    }

    public ConvertException(String message, Throwable cause, boolean writableStackTrace) {
        super(message, cause, writableStackTrace);
    }

}
