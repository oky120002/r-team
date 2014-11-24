/**
 * 
 */
package com.r.core.util;

import com.r.core.util.bean.ResolveBeanOfJsFunction;

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
    public static final ResolveBeanOfJsFunction jsfunction(String js) {
        return new ResolveBeanOfJsFunction(js);
    }
}
