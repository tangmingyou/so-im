package net.sopod.soim.client.config.exception;

import net.sopod.soim.common.dubbo.exception.SoimException;

/**
 * ClientException
 *
 * @author tmy
 * @date 2022-06-02 22:46
 */
public class ClientException extends SoimException {

    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }

    public ClientException(String message, Throwable cause, boolean writableStackTrace) {
        super(message, cause, writableStackTrace);
    }

}
