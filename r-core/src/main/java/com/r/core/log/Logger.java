/**
 * 
 */
package com.r.core.log;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

/**
 * 日志实体<br />
 * 封装org.slf4j.Logger
 * 
 * @see org.slf4j.Logger
 * 
 * @author oky
 * 
 */
public class Logger {

	private org.slf4j.Logger logger = null; // 日志
	private String pre = null; // 前缀

	public Logger(Class<?> clazz, String pre) {
		this.logger = LoggerFactory.getLogger(clazz);
		this.pre = StringUtils.isBlank(pre) ? "" : pre;
	}

	/** 记录"警告"级别的日志 */
	public void warn(String msg) {
		logger.warn(getPre() + msg);
	}

	/** 记录"警告"级别的日志 */
	public void warn(String msg, Object... arguments) {
		msg = getPre() + msg;
		if (ArrayUtils.isEmpty(arguments)) {
			logger.warn(msg);
		} else {
			int size = arguments.length;
			if (size == 1) {
				logger.warn(msg, arguments[0]);
			} else if (size == 2) {
				logger.warn(msg, arguments[0], arguments[1]);
			} else {
				logger.warn(msg, arguments);
			}
		}
	}

	/** 记录"警告"级别的日志 */
	public void warn(String msg, Throwable throwable) {
		logger.warn(getPre() + msg, throwable);
	}

	/** 记录"调试"级别的日志 */
	public void debug(String msg) {
		logger.debug(getPre() + msg);
	}

	/** 记录"调试"级别的日志 */
	public void debug(String msg, Object... arguments) {
		msg = getPre() + msg;
		if (ArrayUtils.isEmpty(arguments)) {
			logger.debug(msg);
		} else {
			int size = arguments.length;
			if (size == 1) {
				logger.debug(msg, arguments[0]);
			} else if (size == 2) {
				logger.debug(msg, arguments[0], arguments[1]);
			} else {
				logger.debug(msg, arguments);
			}
		}
	}

	/** 记录"调试"级别的日志 */
	public void debug(String msg, Throwable throwable) {
		logger.debug(getPre() + msg, throwable);
	}

	/** 记录"信息"级别的日志 */
	public void info(String msg) {
		logger.info(getPre() + msg);
	}

	/** 记录"信息"级别的日志 */
	public void info(String msg, Object... arguments) {
		msg = getPre() + msg;
		if (ArrayUtils.isEmpty(arguments)) {
			logger.info(msg);
		} else {
			int size = arguments.length;
			if (size == 1) {
				logger.info(msg, arguments[0]);
			} else if (size == 2) {
				logger.info(msg, arguments[0], arguments[1]);
			} else {
				logger.info(msg, arguments);
			}
		}
	}

	/** 记录"调试"级别的日志 */
	public void info(String msg, Throwable throwable) {
		logger.info(getPre() + msg, throwable);
	}

	/** 记录"调试"级别的日志 */
	public void error(String msg) {
		logger.error(getPre() + msg);
	}

	/** 记录"调试"级别的日志 */
	public void error(String msg, Object... arguments) {
		msg = getPre() + msg;
		if (ArrayUtils.isEmpty(arguments)) {
			logger.error(msg);
		} else {
			int size = arguments.length;
			if (size == 1) {
				logger.error(msg, arguments[0]);
			} else if (size == 2) {
				logger.error(msg, arguments[0], arguments[1]);
			} else {
				logger.error(msg, arguments);
			}
		}
	}

	/** 记录"调试"级别的日志 */
	public void error(String msg, Throwable throwable) {
		logger.error(getPre() + msg, throwable);
	}

	/** 返回前缀 */
	private String getPre() {
		return this.pre;
	}
}
