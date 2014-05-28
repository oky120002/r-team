/**
 * 
 */
package com.r.core.exceptions;

import org.slf4j.helpers.MessageFormatter;

/**
 * 
 * <b>系统异常：System Exception</b>
 * <ul>
 * 系统内部错误
 * <li>errorMessage用于异常抛送到页面后，为用户显示错误</li>
 * <li>因具体的系统使用者不用太过关心message(详细错误信息,默认为简体中文)</li>
 * <li>如果为简体中文系统，则可以通过errorMessage得到一个错误信息</li>
 * <li>如果非简体中文系统，则可以通过errorCode得到一个唯一的errorCode</li>
 * </ul>
 * <ul>
 * 系统内部逻辑异常封装理念（从彭总处获得灵感）：
 * <li>告诉使用者的错误消息尽量精炼。</li>
 * <li>最终使用者不期望看到一堆错误堆栈，堆栈应当打到后台日志中，显示在前台的应该很简单的语言能够描述。</li>
 * <li>合法性的提示应该在客户提交以前，就以js的形式进行提示，纠正</li>
 * <li>如果遇到提交到后台才发现错误，错误反馈到前端，应该能容忍说明不清楚，这个应该算作BUG进行修正。</li>
 * <li>不应当客户提交信息后，才发现错误存在</li>
 * </ul>
 * 
 * @author Rain
 */
public class SException extends RuntimeException {
	private static final long serialVersionUID = 5663686170421308292L;

	/** 错误编码 */
	private String errorCode = "";

	/** 错误信息 */
	private String errorMessage = "";

	/** 自定义标记,提供额外的标记信息 */
	private int mark = -1;

	/**
	 * 获得错误编码
	 * <ul>
	 * 系统内部错误
	 * <li>errorMessage用于异常抛送到页面后，为用户显示错误</li>
	 * <li>因具体的系统使用者不用太过关心message(详细错误信息,默认为简体中文)</li>
	 * <li>如果为简体中文系统，则可以通过errorMessage得到一个错误信息</li>
	 * <li>如果非简体中文系统，则可以通过errorCode得到一个唯一的errorCode</li>
	 * </ul>
	 * 
	 * @return 错误编码
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 获得错误信息
	 * <ul>
	 * 系统内部错误信息
	 * <li>errorMessage用于异常抛送到页面后，为用户显示错误</li>
	 * <li>因具体的系统使用者不用太过关心message(详细错误信息,默认为简体中文)</li>
	 * <li>如果为简体中文系统，则可以通过errorMessage得到一个错误信息</li>
	 * <li>如果非简体中文系统，则可以通过errorCode得到一个唯一的errorCode</li>
	 * </ul>
	 * 
	 * @return 错误信息
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * 获得额外的信息标记
	 * <ul>
	 * 系统内部错误的额外信息标记
	 * <li>提供一种用以分别不同细微差异的信息标记</li>
	 * <li>提供一种自定义的信息传递标记</li>
	 * </ul>
	 * 
	 * @return
	 */
	public int getErrorMark() {
		return mark;
	}

	/**
	 * 获取错误编码，提供子异常进行重写<br/>
	 * <ul>
	 * 系统内部错误
	 * <li>errorMessage用于异常抛送到页面后，为用户显示错误</li>
	 * <li>因具体的系统使用者不用太过关心message(详细错误信息,默认为简体中文)</li>
	 * <li>如果为简体中文系统，则可以通过errorMessage得到一个错误信息</li>
	 * <li>如果非简体中文系统，则可以通过errorCode得到一个唯一的errorCode</li>
	 * </ul>
	 * 
	 * @return 系统错误编码
	 */
	protected String doGetErrorCode() {
		return "SYSTEM_ERROR";
	}

	/**
	 * 获得错误信息，提供子异常进行重写
	 * <ul>
	 * 系统内部错误
	 * <li>errorMessage用于异常抛送到页面后，为用户显示错误</li>
	 * <li>因具体的系统使用者不用太过关心message(详细错误信息,默认为简体中文)</li>
	 * <li>如果为简体中文系统，则可以通过errorMessage得到一个错误信息</li>
	 * <li>如果非简体中文系统，则可以通过errorCode得到一个唯一的errorCode</li>
	 * </ul>
	 * 
	 * @return 错误信息
	 */
	protected String doGetErrorMessage() {
		return "系统错误";
	}

	public SException(String message) {
		super(message);
		this.errorCode = doGetErrorCode();
		this.errorMessage = doGetErrorMessage();
	}

	public SException(String message, Object... objects) {
		super((objects == null || objects.length == 0) ? message : MessageFormatter.arrayFormat(message, objects).getMessage());
		this.errorCode = doGetErrorCode();
		this.errorMessage = doGetErrorMessage();
	}

	public SException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = doGetErrorCode();
		this.errorMessage = doGetErrorMessage();
	}

	public SException(String message, int mark) {
		super(message);
		this.errorCode = doGetErrorCode();
		this.errorMessage = doGetErrorMessage();
		this.mark = mark;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SException: ").append(this.getClass().getName()).append("\n");
		sb.append("   errorCode: ").append(this.errorCode).append("\n");
		sb.append("   errorMessage: ").append(this.errorMessage).append("\n");
		sb.append("   message: ").append(super.getMessage()).append("\n");
		sb.append("   mark: ").append(this.mark).append("\n");
		sb.append("   Exception toString: ").append(super.toString());
		return sb.toString();
	}

}
