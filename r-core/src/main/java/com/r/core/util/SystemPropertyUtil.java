/**
 * 
 */
package com.r.core.util;

/**
 * 系统参数实用工具
 * 
 * @author rain
 *
 */
public final class SystemPropertyUtil {

    /** 返回用户当前工作目录 */
    public static final String getUserDir() {
        return System.getProperty("user.dir");
    }
}
