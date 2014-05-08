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

	@Override
	protected String doGetErrorCode() {
		return "ARGUMENT_ILLEGAL_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "参数非法错误";
	}

	public ArgIllegalException(String message, Object... objects) {
		super(message, objects);
	}

	public ArgIllegalException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgIllegalException(String message) {
		super(message);
	}

}
