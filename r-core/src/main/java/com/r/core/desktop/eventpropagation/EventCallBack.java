/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-2-16
 * <修改描述:>
 */
package com.r.core.desktop.eventpropagation;

/**
 * 事件执行后的回调
 * 
 * @author rain
 * @version [1.0, 2013-2-16]
 */
public interface EventCallBack {
	/**
	 * 
	 * 事件执行完成后的回调方法
	 * 
	 * @param objects
	 *            向回调方法传入的参数
	 * @param isSuccess
	 *            执行执行是否成功
	 * @param msg
	 *            执行成功或者失败时传入的失败信息(一般情况下,执行成功没有信息)
	 * 
	 * @author rain
	 */
	public void callBack(Object objects, boolean isSuccess, String msg);
}
