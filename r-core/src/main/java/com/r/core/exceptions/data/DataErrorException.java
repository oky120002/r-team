/**
 * 
 */
package com.r.core.exceptions.data;

import com.r.core.exceptions.SException;

/**
 * 数据错误异常
 * 
 * @author rain
 *
 */
public class DataErrorException extends SException {
    private static final long serialVersionUID = 45488618253151212L;

    @Override
    protected String doGetErrorCode() {
        return "DATA_ERROR";
    }

    @Override
    protected String doGetErrorMessage() {
        return "数据错误异常";
    }

    public DataErrorException(String message, Object... objects) {
        super(message, objects);
    }

    public DataErrorException(String message) {
        super(message);
    }
}
