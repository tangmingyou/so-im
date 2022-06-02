package net.sopod.soim.common.dubbo.exception;

import java.io.Serializable;

/**
 * SoimException
 *
 * @author tmy
 * @date 2022-06-02 11:29
 */
public class SoimException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 7148131411144540939L;

    public SoimException() {}

    public SoimException(String message) {
        super(message, null, false, false);
    }

    public SoimException(String message, Throwable cause) {
        super(message, cause, false, false);
    }

    public SoimException(Throwable cause) {
        super(cause == null ? null : cause.getMessage(), cause, false, false);
    }

    /**
     *
     * @param message 错误信息
     * @param cause 错误对象
     * @param writableStackTrace 是否启用堆栈追踪
     */
    public SoimException(String message, Throwable cause , boolean writableStackTrace) {
        super(message, cause, false, writableStackTrace);
    }

}
