/**
 * 
 */
package com.r.core.exceptions.initialization;

import com.r.core.exceptions.SException;

/**
 * SpringBean属性配置错误
 * @author oky
 * 
 */
public class SpringConfiguratorErrorException extends SException {
	private static final long serialVersionUID = 4780010872200579037L;

	@Override
	protected String doGetErrorCode() {
		return "SPRING_CONFIGURATOR_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "SpringBean属性配置错误";
	}

	public SpringConfiguratorErrorException(String message, Object... objects) {
		super(message, objects);
	}

	public SpringConfiguratorErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public SpringConfiguratorErrorException(String message) {
		super(message);
	}

}
