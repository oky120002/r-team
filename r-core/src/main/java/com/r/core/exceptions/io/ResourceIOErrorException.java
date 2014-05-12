/**
 * 
 */
package com.r.core.exceptions.io;

import com.r.core.exceptions.SException;

/**
 * 资源IO读取错误
 * 
 * @author oky
 * 
 */
public class ResourceIOErrorException extends SException {
	private static final long serialVersionUID = -3961726094614150479L;

	@Override
	protected String doGetErrorCode() {
		return "RESOURCE_IO_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "资源IO读取错误";
	}

	/** 资源解析错误 */
	public ResourceIOErrorException(String message, Object... objects) {
		super(message, objects);
	}

	/** 资源解析错误 */
	public ResourceIOErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	/** 资源解析错误 */
	public ResourceIOErrorException(String message) {
		super(message);
	}

}
