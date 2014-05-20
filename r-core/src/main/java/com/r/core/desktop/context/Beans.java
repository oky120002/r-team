/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-3-5
 * <修改描述:>
 */
package com.r.core.desktop.context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <Bean注解>
 * 
 * @author  rain
 * @version  [版本号, 2013-3-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface Beans {
    String value() default "";
}
