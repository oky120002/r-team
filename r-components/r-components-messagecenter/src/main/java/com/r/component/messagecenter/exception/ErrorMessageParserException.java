/**
 * 
 */
package com.r.component.messagecenter.exception;

/**
 * 消息 解析器异常
 * 
 * @author oky
 * 
 */
public class ErrorMessageParserException extends MessageCenterException {
	private static final long serialVersionUID = 894348275215129944L;

	@Override
	protected String doGetErrorCode() {
		return "MESSAGECENTER_MESSAGEPARSER_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "消息 解析器异常";
	}

	/** 消息 解析器异常 */
	public ErrorMessageParserException(String message, Object... objects) {
		super(message, objects);
	}

	/** 消息 解析器异常 */
	public ErrorMessageParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/** 消息 解析器异常 */
	public ErrorMessageParserException(String message) {
		super(message);
	}

	public ErrorMessageParserException(String message, int mark) {
		super(message, mark);
	}

}
