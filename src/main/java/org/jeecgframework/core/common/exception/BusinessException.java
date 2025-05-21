package org.jeecgframework.core.common.exception;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
