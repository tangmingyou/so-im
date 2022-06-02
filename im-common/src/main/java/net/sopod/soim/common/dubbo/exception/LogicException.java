package net.sopod.soim.common.dubbo.exception;

/**
 * LogicException
 *
 * @author tmy
 * @date 2022-06-02 12:03
 */
public class LogicException extends SoimException {

    private static final long serialVersionUID = 8123008638375992724L;

    public LogicException() {
        super();
    }

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicException(Throwable cause) {
        super(cause);
    }

    public LogicException(String message, Throwable cause, boolean writableStackTrace) {
        super(message, cause, writableStackTrace);
    }

}
