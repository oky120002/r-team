/**
 * 
 */
package com.r.core.exceptions.io;

/**
 * @author Administrator
 *
 */
public class NetworkIOReadErrorException extends IOReadErrorException {
	private static final long serialVersionUID = -4165742130756527470L;

	public NetworkIOReadErrorException(String message, Object... objects) {
		super(message, objects);
	}

	public NetworkIOReadErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public NetworkIOReadErrorException(String message) {
		super(message);
	}

	@Override
	protected String doGetErrorCode() {
		return "NETWORK_IO_READ_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "网络IO读取错误";
	}

}
