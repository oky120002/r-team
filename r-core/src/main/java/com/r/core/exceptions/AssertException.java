/**
 * 描          述:  描述
 * 修  改   人:  oky
 * 修改时间:  2013-10-15
 * 修改描述:
 */
package com.r.core.exceptions;

/**
 * 断言错误
 * 
 * @author oky
 * @version 版本号, 2013-10-15
 */
public class AssertException extends SException {
	private static final long serialVersionUID = 516573567111981170L;

	@Override
	protected String doGetErrorCode() {
		return "ASSERT_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "断言失败";
	}

	public AssertException(String message, Object... objects) {
		super(message, objects);
	}

	public AssertException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssertException(String message) {
		super(message);
	}
}
