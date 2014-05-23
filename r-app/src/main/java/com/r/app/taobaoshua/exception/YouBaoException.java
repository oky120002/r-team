/**
 * 
 */
package com.r.app.taobaoshua.exception;

import com.r.core.exceptions.SException;

/**
 * 友保错误
 * 
 * @author Administrator
 *
 */
public class YouBaoException extends SException {
	private static final long serialVersionUID = -3926775518461395465L;

	public YouBaoException(String message, Object... objects) {
		super(message, objects);
	}

	public YouBaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public YouBaoException(String message) {
		super(message);
	}

	@Override
	protected String doGetErrorCode() {
		return "YOU_BAO_EXCEPTION";
	}

	@Override
	protected String doGetErrorMessage() {
		return "友保相关异常";
	}

	public YouBaoException(String message, int mark) {
		super(message, mark);
	}
}
