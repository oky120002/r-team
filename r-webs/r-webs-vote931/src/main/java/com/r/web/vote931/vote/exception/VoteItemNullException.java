/**
 * 
 */
package com.r.web.vote931.vote.exception;

import com.r.core.exceptions.SException;

/**
 * 问卷项为空
 * @author Administrator
 *
 */
public class VoteItemNullException extends SException {
	private static final long serialVersionUID = -2651969243527328717L;

	public VoteItemNullException(String message, int mark) {
		super(message, mark);
	}

	public VoteItemNullException(String message, Object... objects) {
		super(message, objects);
	}

	public VoteItemNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public VoteItemNullException(String message) {
		super(message);
	}

	@Override
	protected String doGetErrorCode() {
		return "VOTEITEM_NULL";
	}

	@Override
	protected String doGetErrorMessage() {
		return "问卷项为空";
	}
}
