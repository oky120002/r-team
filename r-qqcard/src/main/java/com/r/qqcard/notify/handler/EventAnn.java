/**
 * 
 */
package com.r.qqcard.notify.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事件注解
 * 
 * @author rain
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventAnn {
    /** 事件 */
    Event value();
}
