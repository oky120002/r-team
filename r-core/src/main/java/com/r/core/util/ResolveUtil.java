/**
 * 
 */
package com.r.core.util;

import com.r.core.bean.JsFunction;

/**
 * 解析实用工具
 * 
 * @author rain
 *
 */
public class ResolveUtil {
    /**
     * 解析js的function字符串
     * 
     * @param js
     *            数据
     * @return 解析完成的数据Bean
     */
    public static final JsFunction jsfunction(String js) {
        return new JsFunction(js);
    }
}
