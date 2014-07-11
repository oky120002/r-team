/**
 * 
 */
package com.r.core.log;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

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
	private static final String LOGGER_LEVEL_WARN = "warn";
	private static final String LOGGER_LEVEL_DEBUG = "debug";
	private static final String LOGGER_LEVEL_INFO = "info";
	private static final String LOGGER_LEVEL_ERROR = "error";
	private com.r.core.log.LoggerFactory loggerFactroy = null;
	private org.slf4j.Logger logger = null; // 日志
	private String pre = null; // 前缀

	public Logger(com.r.core.log.LoggerFactory loggerFactroy, Class<?> clazz, String pre) {
		this.loggerFactroy = loggerFactroy;
		this.logger = LoggerFactory.getLogger(clazz);
		this.pre = StringUtils.isBlank(pre) ? "" : pre;
	}

	/** 记录"警告"级别的日志 */
	public void warn(String msg) {
		warn(msg, (Throwable) null);
	}

	/** 记录"警告"级别的日志 */
	public void warn(String msg, Object... arguments) {
		if (ArrayUtils.isEmpty(arguments)) {
			warn(msg);
		} else {
			int size = arguments.length;
			if (size == 1) {
				warn(MessageFormatter.format(msg, arguments[0]).getMessage());
			} else if (size == 2) {
				warn(MessageFormatter.format(msg, arguments[0], arguments[1]).getMessage());
			} else {
				warn(MessageFormatter.arrayFormat(msg, arguments).getMessage());
			}
		}

	}

	/** 记录"警告"级别的日志 */
	public void warn(String msg, Throwable throwable) {
		msg = getPre() + msg;
		fireListener(LOGGER_LEVEL_WARN, msg, throwable);
		if (throwable == null) {
			logger.warn(msg);
		} else {
			logger.warn(msg, throwable);
		}

	}

	/** 记录"调试"级别的日志 */
	public void debug(String msg) {
		debug(msg, (Throwable) null);
	}

	/** 记录"调试"级别的日志 */
	public void debug(String msg, Object... arguments) {
		if (ArrayUtils.isEmpty(arguments)) {
			debug(msg);
		} else {
			int size = arguments.length;
			if (size == 1) {
				debug(MessageFormatter.format(msg, arguments[0]).getMessage());
			} else if (size == 2) {
				debug(MessageFormatter.format(msg, arguments[0], arguments[1]).getMessage());
			} else {
				debug(MessageFormatter.arrayFormat(msg, arguments).getMessage());
			}
		}
	}

	/** 记录"调试"级别的日志 */
	public void debug(String msg, Throwable throwable) {
		msg = getPre() + msg;
		fireListener(LOGGER_LEVEL_DEBUG, msg, throwable);
		if (throwable == null) {
			logger.debug(msg);
		} else {
			logger.debug(msg, throwable);
		}
	}

	/** 记录"信息"级别的日志 */
	public void info(String msg) {
		info(msg, (Throwable) null);
	}

	/** 记录"信息"级别的日志 */
	public void info(String msg, Object... arguments) {
		if (ArrayUtils.isEmpty(arguments)) {
			info(msg);
		} else {
			int size = arguments.length;
			if (size == 1) {
				info(MessageFormatter.format(msg, arguments[0]).getMessage());
			} else if (size == 2) {
				info(MessageFormatter.format(msg, arguments[0], arguments[1]).getMessage());
			} else {
				info(MessageFormatter.arrayFormat(msg, arguments).getMessage());
			}
		}
	}

	/** 记录"调试"级别的日志 */
	public void info(String msg, Throwable throwable) {
		msg = getPre() + msg;
		fireListener(LOGGER_LEVEL_INFO, msg, throwable);
		if (throwable == null) {
			logger.info(msg);
		} else {
			logger.info(msg, throwable);
		}

	}

	/** 记录"调试"级别的日志 */
	public void error(String msg) {
		error(msg, (Throwable) null);
	}

	/** 记录"调试"级别的日志 */
	public void error(String msg, Object... arguments) {
		if (ArrayUtils.isEmpty(arguments)) {
			error(msg);
		} else {
			int size = arguments.length;
			if (size == 1) {
				error(MessageFormatter.format(msg, arguments[0]).getMessage());
			} else if (size == 2) {
				error(MessageFormatter.format(msg, arguments[0], arguments[1]).getMessage());
			} else {
				error(MessageFormatter.arrayFormat(msg, arguments).getMessage());
			}
		}
	}

	/** 记录"调试"级别的日志 */
	public void error(String msg, Throwable throwable) {
		msg = getPre() + msg;
		fireListener(LOGGER_LEVEL_ERROR, msg, throwable);
		if (throwable == null) {
			logger.error(msg);
		} else {
			logger.error(msg, throwable);
		}

	}

	/** 返回前缀 */
	private String getPre() {
		return this.pre;
	}

	private void fireListener(String key, String message, Throwable error) {
		if (loggerFactroy != null) {
			Collection<LoggerListener> loggerListeners = loggerFactroy.getLoggerListeners();
			if (CollectionUtils.isNotEmpty(loggerListeners)) {
				switch (key) {
				case LOGGER_LEVEL_WARN:
					for (LoggerListener loggerListener : loggerListeners) {
						if (!loggerListener.warn(message, error)) {
							return;
						}
					}
					break;
				case LOGGER_LEVEL_DEBUG:
					for (LoggerListener loggerListener : loggerListeners) {
						if (!loggerListener.debug(message, error)) {
							return;
						}
					}
					break;
				case LOGGER_LEVEL_INFO:
					for (LoggerListener loggerListener : loggerListeners) {
						if (!loggerListener.info(message, error)) {
							return;
						}
					}
					break;
				case LOGGER_LEVEL_ERROR:
					for (LoggerListener loggerListener : loggerListeners) {
						if (!loggerListener.error(message, error)) {
							return;
						}
					}
					break;
				}
			}
		}
	}
}
