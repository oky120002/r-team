/**
 * 
 */
package com.r.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 校验,判断实用工具
 * 
 * @author rain
 *
 */
public abstract class CheckUtil {
    /** 校验传入的email字符串的格式是否正确 */
    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }

        return email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");
    }
}
