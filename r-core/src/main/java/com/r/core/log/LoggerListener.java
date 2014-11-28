/**
 * 
 */
package com.r.core.log;

/**
 * 日志监听器
 * 
 * @author rain
 *
 */
public interface LoggerListener {

    /**
     * 写日志
     * 
     * @param loglevel
     *            日志级别
     * @param pre
     *            前缀
     * @param message
     *            日志信息
     * @param e
     *            抛出的错误
     */
    void log(String loglevel, String pre, String message, Throwable e);
}
