/**
 * 
 */
package com.r.core.log;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
    public static final String LOGGER_LEVEL_WARN = "warn";
    public static final String LOGGER_LEVEL_DEBUG = "debug";
    public static final String LOGGER_LEVEL_INFO = "info";
    public static final String LOGGER_LEVEL_ERROR = "error";
    private org.slf4j.Logger logger = null; // 日志
    private String pre = null; // 前缀

    public Logger(Class<?> clazz, String pre) {
        this.logger = LoggerFactory.getLogger(clazz);
        this.pre = StringUtils.isBlank(pre) ? "" : pre;
    }

    /** 记录"警告"级别的日志 */
    public void warn(String msg) {
        log(LOGGER_LEVEL_WARN, msg);
    }

    /** 记录"警告"级别的日志 */
    public void warn(String msg, Object... arguments) {
        log(LOGGER_LEVEL_WARN, msg, arguments);
    }

    /** 记录"警告"级别的日志 */
    public void warn(String msg, Throwable throwable) {
        log(LOGGER_LEVEL_WARN, msg, throwable);
    }

    /** 记录"调试"级别的日志 */
    public void debug(String msg) {
        log(LOGGER_LEVEL_DEBUG, msg);
    }

    /** 记录"调试"级别的日志 */
    public void debug(String msg, Object... arguments) {
        log(LOGGER_LEVEL_DEBUG, msg, arguments);
    }

    /** 记录"调试"级别的日志 */
    public void debug(String msg, Throwable throwable) {
        log(LOGGER_LEVEL_DEBUG, msg, throwable);
    }

    /** 记录"信息"级别的日志 */
    public void info(String msg) {
        log(LOGGER_LEVEL_INFO, msg);
    }

    /** 记录"信息"级别的日志 */
    public void info(String msg, Object... arguments) {
        log(LOGGER_LEVEL_INFO, msg, arguments);
    }

    /** 记录"调试"级别的日志 */
    public void info(String msg, Throwable throwable) {
        log(LOGGER_LEVEL_INFO, msg, throwable);
    }

    /** 记录"调试"级别的日志 */
    public void error(String msg) {
        log(LOGGER_LEVEL_ERROR, msg);
    }

    /** 记录"调试"级别的日志 */
    public void error(String msg, Object... arguments) {
        log(LOGGER_LEVEL_ERROR, msg, arguments);
    }

    /** 记录"调试"级别的日志 */
    public void error(String msg, Throwable throwable) {
        log(LOGGER_LEVEL_ERROR, msg, throwable);
    }

    /** 记录"自定义"级别的日志 */
    public void log(String loglevel, String msg) {
        log(loglevel, msg, (Throwable) null);
    }

    /** 记录"自定义"级别的日志 */
    public void log(String loglevel, String msg, Object... arguments) {
        if (ArrayUtils.isEmpty(arguments)) {
            log(loglevel, msg);
        } else {
            log(loglevel, MessageFormatter.arrayFormat(msg, arguments).getMessage());
        }
    }

    /** 记录"自定义"级别的日志 */
    public void log(String loglevel, String msg, Throwable throwable) {
        fireListener(loglevel, StringUtils.isBlank(this.pre) ? "" : (this.pre + " - "), msg, throwable);
    }

    /**
     * 执行监听器
     * 
     * @param loglevel
     *            日志级别
     * @param pre
     *            浅醉
     * @param message
     *            日志信息
     * @param throwable
     *            异常
     */
    private void fireListener(String loglevel, String pre, String message, Throwable throwable) {
        Collection<LoggerListener> loggerListeners = com.r.core.log.LoggerFactory.getLoggerListeners();

        if (Logger.LOGGER_LEVEL_INFO.equals(loglevel)) {
            if (throwable == null) {
                logger.info(pre + message);
            } else {
                logger.info(pre + message, throwable);
            }
        }
        if (Logger.LOGGER_LEVEL_DEBUG.equals(loglevel)) {
            if (throwable == null) {
                logger.debug(pre + message);
            } else {
                logger.debug(pre + message, throwable);
            }
        }
        if (Logger.LOGGER_LEVEL_WARN.equals(loglevel)) {
            if (throwable == null) {
                logger.warn(pre + message);
            } else {
                logger.warn(pre + message, throwable);
            }
        }
        if (Logger.LOGGER_LEVEL_ERROR.equals(loglevel)) {
            if (throwable == null) {
                logger.error(pre + message);
            } else {
                logger.error(pre + message, throwable);
            }
        }

        if (CollectionUtils.isNotEmpty(loggerListeners)) {
            for (LoggerListener loggerListener : loggerListeners) {
                loggerListener.log(loglevel, pre, message, throwable);
            }
        }
    }
}
