/**
 * 
 */
package com.r.core.exceptions;

/**
 * 验证码异常
 * 
 * @author oky
 * 
 */
public class CaptchaErrorException extends SException {
	private static final long serialVersionUID = 712032820330054128L;

	public CaptchaErrorException(String message, Object... objects) {
		super(message, objects);
	}

	public CaptchaErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public CaptchaErrorException(String message) {
		super(message);
	}

	@Override
	protected String doGetErrorCode() {
		return "CAPTCHA_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "验证码异常";
	}

}
