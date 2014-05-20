/**
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2012-12-30
 * <修改描述:>
 */
package com.r.core.desktop.eventpropagation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义事件方法.value事件ID<br />
 * 所有的事件,都是异步响应的
 * 
 * @author Administrator
 * @version [1.0, 2012-12-30]
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventSign {
	/** 事件ID,如果为空,则事件ID为{类简单名} + "." + {方法名} */
	public String value() default "";

	/** 是否同步,默认由传入的Event决定 */
	public Synchronous synchronous() default Synchronous.NULL;

	public static enum Synchronous {
		/** 此事件的调用是否同步由传入的事件本身决定 */
		NULL,
		/** 此事件是异步调用 */
		异步,
		/** 此事件是同步调用 */
		同步, ;
	}
}
