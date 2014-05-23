/**
 * 
 */
package com.r.core.exceptions.arg;

import com.r.core.exceptions.SException;

/**
 * 参数为空异常
 * 
 * @author oky
 * 
 */
public class ArgNullException extends SException {
	private static final long serialVersionUID = -7935658310000289263L;

	@Override
	protected String doGetErrorCode() {
		return "ARGUMENT_NULL_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "参数为空错误";
	}

	/** 参数为空异常 */
	public ArgNullException(String message, Object... objects) {
		super(message, objects);
	}

	/** 参数为空异常 */
	public ArgNullException(String message, Throwable cause) {
		super(message, cause);
	}

	/** 参数为空异常 */
	public ArgNullException(String message) {
		super(message);
	}

	public ArgNullException(String message, int mark) {
		super(message, mark);
	}
	
}
