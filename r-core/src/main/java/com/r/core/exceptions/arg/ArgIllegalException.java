/**
 * 
 */
package com.r.core.exceptions.arg;

import com.r.core.exceptions.SException;

/**
 * 参数非法异常<br />
 * 
 * @author rain
 * 
 */
public class ArgIllegalException extends SException {
	private static final long serialVersionUID = 8754997095073948279L;

	/** 参数非法异常 */
	public ArgIllegalException(String message, Object... objects) {
		super(message, objects);
	}

	/** 参数非法异常 */
	public ArgIllegalException(String message, Throwable cause) {
		super(message, cause);
	}

	/** 参数非法异常 */
	public ArgIllegalException(String message) {
		super(message);
	}

	/** 参数非法异常 */
	public ArgIllegalException(String message, int mark) {
		super(message, mark);
	}
	
	@Override
	protected String doGetErrorCode() {
		return "ARGUMENT_ILLEGAL_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "参数非法错误";
	}
}
