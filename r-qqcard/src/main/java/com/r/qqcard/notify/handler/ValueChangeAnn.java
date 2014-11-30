/**
 * 
 */
package com.r.qqcard.notify.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 值变化注解
 * 
 * @author rain
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueChangeAnn {
    /** 值类型 */
    ValueType[] value();
}
