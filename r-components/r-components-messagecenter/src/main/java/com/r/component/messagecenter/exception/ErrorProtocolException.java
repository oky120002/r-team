/**
 * 
 */
package com.r.component.messagecenter.exception;

/**
 * 消息格式错误异常
 * 
 * @author oky
 * 
 */
public class ErrorProtocolException extends MessageCenterException {
	private static final long serialVersionUID = 4352526263514456786L;

	@Override
	protected String doGetErrorCode() {
		return "MESSAGECENTER_MESSAGE_PROTOCOL_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "消息格式错误异常";
	}

	/** 消息格式错误异常 */
	public ErrorProtocolException(String message, Object... objects) {
		super(message, objects);
	}

	/** 消息格式错误异常 */
	public ErrorProtocolException(String message, Throwable cause) {
		super(message, cause);
	}

	/** 消息格式错误异常 */
	public ErrorProtocolException(String message) {
		super(message);
	}
}
