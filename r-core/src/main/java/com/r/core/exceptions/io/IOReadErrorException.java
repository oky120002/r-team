/**
 * 
 */
package com.r.core.exceptions.io;

import com.r.core.exceptions.SException;

/**
 * IO读取错误异常
 * 
 * @author oky
 * 
 */
public class IOReadErrorException extends SException {
	private static final long serialVersionUID = -713465659364964973L;

	@Override
	protected String doGetErrorCode() {
		return "IO_READ_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "IO读取异常";
	}

	/** IO读取错误 */
	public IOReadErrorException(String message, Object... objects) {
		super(message, objects);
	}

	/** IO读取错误 */
	public IOReadErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	/** IO读取错误 */
	public IOReadErrorException(String message) {
		super(message);
	}

}
