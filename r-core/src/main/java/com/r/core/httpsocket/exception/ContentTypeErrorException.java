package com.r.core.httpsocket.exception;

import com.r.core.exceptions.SException;

/**
 * ContentType类型错误<br>
 * 在返回Response的Body时,需要校验Body的文件类型,如果错误,则抛出此错误
 * 
 * @author rain
 * 
 */
public class ContentTypeErrorException extends SException {
	private static final long serialVersionUID = -3919642276436105765L;

	@Override
	protected String doGetErrorCode() {
		return "CONTENT_TYPE_ERROR_EXCEPTION";
	}

	@Override
	protected String doGetErrorMessage() {
		return "Response的Body类型错误";
	}

	public ContentTypeErrorException(String message, Object... objects) {
		super(message, objects);
	}

	public ContentTypeErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContentTypeErrorException(String message) {
		super(message);
	}

}
