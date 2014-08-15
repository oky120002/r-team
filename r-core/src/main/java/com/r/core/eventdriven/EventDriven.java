/**
 * 
 */
package com.r.core.eventdriven;

/**
 * 事件驱动
 * 
 * @author Administrator
 *
 */
public class EventDriven {
	private static EventDriven eventDriven = null; // 对自身的引用

	/** 返回唯一引用 */
	public static EventDriven getInstance() {
		return EventDriven.eventDriven;
	}
	
//	public 
}
