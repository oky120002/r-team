/**
 * 
 */
package com.r.core.exceptions;

/**
 * 克隆错误异常
 * 
 * @author oky
 * 
 */
public class CloneErrorException extends SException {
	private static final long serialVersionUID = -6617172810601024796L;

	@Override
	protected String doGetErrorCode() {
		return "CLONE_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "克隆异常";
	}

	/** 克隆错误 */
	public CloneErrorException(String message, Object... objects) {
		super(message, objects);
	}

	/** 克隆错误 */
	public CloneErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	/** 克隆错误 */
	public CloneErrorException(String message) {
		super(message);
	}

	public CloneErrorException(String message, int mark) {
		super(message, mark);
	}

}
