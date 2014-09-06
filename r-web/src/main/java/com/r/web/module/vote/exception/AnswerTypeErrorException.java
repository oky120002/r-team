/**
 * 
 */
package com.r.web.module.vote.exception;

import com.r.core.exceptions.SException;

/**
 * 答案类型错误
 * 
 * @author Administrator
 *
 */
public class AnswerTypeErrorException extends SException {
	private static final long serialVersionUID = 3872066386031303039L;

	public AnswerTypeErrorException(String message, int mark) {
		super(message, mark);
	}

	public AnswerTypeErrorException(String message, Object... objects) {
		super(message, objects);
	}

	public AnswerTypeErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnswerTypeErrorException(String message) {
		super(message);
	}

	@Override
	protected String doGetErrorCode() {
		return "ANSWER_TYPE_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "答案类型错误";
	}
}
