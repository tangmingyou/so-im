package net.sopod.soim.common.dubbo.exception;

/**
 * 通用服务异常
 * 默认不生成堆栈信息，设置 writableStackTrace 为 true 则生成堆栈信息
 *
 * @author 晚星
 * @date 2019/12/30 16:30
 */
public class ServiceException extends SoimException {

    private static final long serialVersionUID = 6555896535065406344L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean writableStackTrace) {
        super(message, cause, writableStackTrace);
    }

}
