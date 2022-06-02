package net.sopod.soim.common.dubbo.exception;

/**
 * DasException
 *
 * @author tmy
 * @date 2022-06-02 12:02
 */
public class DasException extends SoimException {

    private static final long serialVersionUID = -533031075736547194L;

    public DasException() {
        super();
    }

    public DasException(String message) {
        super(message);
    }

    public DasException(String message, Throwable cause) {
        super(message, cause);
    }

    public DasException(Throwable cause) {
        super(cause);
    }

    public DasException(String message, Throwable cause, boolean writableStackTrace) {
        super(message, cause, writableStackTrace);
    }

}
