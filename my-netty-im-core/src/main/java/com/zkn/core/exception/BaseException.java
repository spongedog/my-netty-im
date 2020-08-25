package com.zkn.core.exception;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/19
 */
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}
