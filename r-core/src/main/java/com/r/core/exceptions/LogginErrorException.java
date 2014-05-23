/**
 * 
 */
package com.r.core.exceptions;

/**
 * 登陆异常
 * 
 * @author oky
 * 
 */
public class LogginErrorException extends SException {
	private static final long serialVersionUID = 712032820330054128L;

	public LogginErrorException(String message, Object... objects) {
		super(message, objects);
	}

	public LogginErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogginErrorException(String message) {
		super(message);
	}
	
	public LogginErrorException(String message, int mark) {
		super(message, mark);
	}

	@Override
	protected String doGetErrorCode() {
		return "LOGGIN_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "登陆账户异常";
	}

}
