/**
 * 
 */
package com.r.core.exceptions.initialization;

import com.r.core.exceptions.SException;

/**
 * 初始化失败异常
 * 
 * @author oky
 * 
 */
public class InitializationErrorException extends SException {
	private static final long serialVersionUID = 5733364861293130690L;

	@Override
	protected String doGetErrorCode() {
		return "INITIALIZATION_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "初始化失败";
	}

	/** 初始化失败错误 */
	public InitializationErrorException(String message, Object... objects) {
		super(message, objects);
	}

	/** 初始化失败错误 */
	public InitializationErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	/** 初始化失败错误 */
	public InitializationErrorException(String message) {
		super(message);
	}

}
