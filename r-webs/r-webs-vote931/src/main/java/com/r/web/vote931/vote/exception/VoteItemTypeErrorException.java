package com.r.web.vote931.vote.exception;

import com.r.core.exceptions.SException;

/**
 * 问卷项类型错误
 * 
 * @author Administrator
 *
 */
public class VoteItemTypeErrorException extends SException {
	private static final long serialVersionUID = -8033744807366327812L;

	public VoteItemTypeErrorException(String message, int mark) {
		super(message, mark);
	}

	public VoteItemTypeErrorException(String message, Object... objects) {
		super(message, objects);
	}

	public VoteItemTypeErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public VoteItemTypeErrorException(String message) {
		super(message);
	}

	@Override
	protected String doGetErrorCode() {
		return "VOTEITEM_TYPE_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "问卷项类型错误";
	}
}
