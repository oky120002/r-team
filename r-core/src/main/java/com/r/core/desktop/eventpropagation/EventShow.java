/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2012-12-17
 * <修改描述:>
 */
package com.r.core.desktop.eventpropagation;

import java.util.EventListener;

/**
 * 事件传递接口
 * 
 * @author rain
 * @version [1.0, 2012-12-17]
 */
public interface EventShow extends EventListener {

	/** 遍历所有的子控件ShowEvent并且执行子事件控件上的事件 */
	void executeEvent(Event event, Object object, EventCallBack callBack);

	/** 添加子事件控件 */
	void addEventShow(EventShow eventShow);

	/** 删除子事件控件 */
	void removeEventShow(EventShow eventShow);

	/** 给此控件设置唯一标识符,如果传入空,则自动生成 */
	void setEventUid(String uid);

	/** 返回此控件的唯一标识符,始终都有非空的唯一的返回值 */
	String getEventUid();

	/** 初始化 */
	void eventInit(Event evetn, Object object);

	/** 启动 */
	void eventStart(Event evetn, Object object);

	/** 退出 */
	void eventExit(Event evetn, Object object);

	/** 暂停.和eventContinue()相对应 */
	void eventPause(Event evetn, Object object);

	/** 继续.和eventPause()相对应 */
	void eventContinue(Event evetn, Object object);
}
