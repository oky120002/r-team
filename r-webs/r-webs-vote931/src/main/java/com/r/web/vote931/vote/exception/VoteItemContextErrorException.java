/**
 * 
 */
package com.r.web.vote931.vote.exception;

import com.r.core.exceptions.SException;

/**
 * 问卷项内容错误<br />
 * 例如: 内容为null,内容类是错误,内容出现根本不可能出现的内容等
 * 
 * 
 * @author Administrator
 *
 */
public class VoteItemContextErrorException extends SException {
	private static final long serialVersionUID = -9034707315327361166L;

	public VoteItemContextErrorException(String message, int mark) {
		super(message, mark);
	}

	public VoteItemContextErrorException(String message, Object... objects) {
		super(message, objects);
	}

	public VoteItemContextErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public VoteItemContextErrorException(String message) {
		super(message);
	}

	@Override
	protected String doGetErrorCode() {
		return "VOTE_ITEM_CONTEXT";
	}

	@Override
	protected String doGetErrorMessage() {
		return "问卷项内容错误";
	}

}
