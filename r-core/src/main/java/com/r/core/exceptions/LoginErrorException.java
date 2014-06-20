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
public class LoginErrorException extends SException {
	private static final long serialVersionUID = 712032820330054128L;

	public LoginErrorException(String message, Object... objects) {
		super(message, objects);
	}

	public LoginErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginErrorException(String message) {
		super(message);
	}
	
	public LoginErrorException(String message, int mark) {
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
