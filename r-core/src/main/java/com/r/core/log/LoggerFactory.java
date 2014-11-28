/**
 * 
 */
package com.r.core.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.r.core.util.AssertUtil;

/**
 * 自定义的log类,可以集成修改每个方法,
 * 
 * @author oky
 * 
 */
public class LoggerFactory {

    private static final LoggerFactory loggerFacotry = new LoggerFactory();
    private Map<String, Logger> map = new HashMap<String, Logger>();
    private List<LoggerListener> loggerListeners = new ArrayList<LoggerListener>();
    private String DEFAULT_PRE = ""; // 默认的前缀

    private LoggerFactory() {
        super();
    }

    /** 设置全局默认前缀 */
    public static final void setDefaultPre(String defaultPre) {
        LoggerFactory.loggerFacotry.DEFAULT_PRE = defaultPre;
    }

    /** 获取日志 */
    public static final Logger getLogger(Class<?> clazz, String pre) {
        return LoggerFactory.loggerFacotry.logger(clazz, pre);
    }

    /** 获取日志 */
    public static final Logger getLogger(Class<?> clazz) {
        return LoggerFactory.loggerFacotry.logger(clazz, null);
    }

    /** 返回默认前缀 */
    public static final String getDefaultPre() {
        return LoggerFactory.loggerFacotry.DEFAULT_PRE;
    }

    /** 添加日志监听器 */
    public static final void addLoggerListener(LoggerListener... loggerListeners) {
        AssertUtil.isNotEmpty("添加的日志监听器不能为空", loggerListeners);
        LoggerFactory.loggerFacotry.loggerListeners.addAll(Arrays.asList(loggerListeners));
    }

    public static Collection<LoggerListener> getLoggerListeners() {
        return LoggerFactory.loggerFacotry.loggerListeners;
    }

    /** 获取日志 */
    protected Logger logger(Class<?> clazz, String pre) {
        pre = (StringUtils.isBlank(pre) ? LoggerFactory.loggerFacotry.DEFAULT_PRE : pre);
        String loggerKey = LoggerFactory.getLoggerKey(clazz, pre);
        if (!map.containsKey(loggerKey)) {
            map.put(loggerKey, new Logger(clazz, pre));
        }
        return map.get(loggerKey);
    }

    /** 获取日志缓存Key */
    protected static String getLoggerKey(Class<?> clazz, String pre) {
        if (clazz == null) {
            return "null." + pre;
        }
        return clazz.getName() + "." + pre;
    }
}
