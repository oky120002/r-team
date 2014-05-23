/**
 * 
 */
package com.r.core.exceptions;

/**
 * switch分支异常,程序运行到不应该走到的switch分支上,抛出此异常
 * 
 * @author oky
 * 
 */
public class SwitchPathException extends SException {
	private static final long serialVersionUID = -2845413097321002632L;

	@Override
	protected String doGetErrorCode() {
		return "SWITCH_PATH_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "switch分支错误，走到了不该走的switch分支。";
	}

	/** switch分支异常 */
	public SwitchPathException(String message, Object... objects) {
		super(message, objects);
	}

	/** switch分支异常 */
	public SwitchPathException(String message, Throwable cause) {
		super(message, cause);
	}

	/** switch分支异常 */
	public SwitchPathException(String message) {
		super(message);
	}

	public SwitchPathException(String message, int mark) {
		super(message, mark);
	}
}
