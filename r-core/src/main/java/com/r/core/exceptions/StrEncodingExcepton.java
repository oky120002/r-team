/**
 * 
 */
package com.r.core.exceptions;

/**
 * 字符串编码异常
 * 
 * @author rain
 *
 */
public class StrEncodingExcepton extends SException {
    private static final long serialVersionUID = 6074958157888678889L;

    @Override
    protected String doGetErrorCode() {
        return "STR_ENCODING";
    }

    @Override
    protected String doGetErrorMessage() {
        return "字符串编码错误";
    }

    public StrEncodingExcepton() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public StrEncodingExcepton(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public StrEncodingExcepton(String message) {
        super(message);
    }
}
