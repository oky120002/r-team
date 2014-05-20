/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-3-13
 * <修改描述:>
 */
package com.r.core.desktop.eventlistener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.r.core.desktop.eventlistener.change.ChangeEvent;
import com.r.core.desktop.eventlistener.change.ChangeListener;
import com.r.core.desktop.eventlistener.move.MoveEvent;
import com.r.core.desktop.eventlistener.move.MoveListener;

/**
 * <功能简述> <功能详细描述>
 * 
 * @author rain
 * @version [版本号, 2013-3-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EventListenerImpl {
	/** 事件容器 */
	private Map<String, List<EventListener>> eventMap = new HashMap<String, List<EventListener>>();

	/*----------------改变事件---------START--------------*/
	/** 添加改变事件监听器 */
	public <T> void addChangeListener(ChangeListener<T> changeListener) {
		addListener(ChangeListener.class, changeListener);
	}

	/** 执行改变事件 */
	public <T> void fireChangeListener(ChangeEvent<T> changeEvent) {
		fireListener(ChangeListener.class, changeEvent);
	}

	/*----------------改变事件--------END---------------*/

	/*----------------移动事件---------START--------------*/
	/** 添加改变事件监听器 */
	public <T> void addMoveListener(MoveListener moveListener) {
		addListener(MoveListener.class, moveListener);
	}

	/** 执行改变事件 */
	public <T> void fireMoveListener(MoveEvent moveEvent) {
		fireListener(MoveListener.class, moveEvent);
	}

	/*----------------移动事件--------END---------------*/

	/** 添加监听器 */
	private void addListener(Class<?> eventClass, EventListener eventListener) {
		String className = eventClass.getName();
		if (!this.eventMap.containsKey(className)) {
			this.eventMap.put(className, new ArrayList<EventListener>());
		}
		this.eventMap.get(className).add(eventListener);
	}

	/** 执行监听 */
	@SuppressWarnings("unchecked")
	private <T> void fireListener(Class<?> eventClass, EventObjectImpl object) {
		if (MapUtils.isEmpty(eventMap)) {
			return;
		}

		// XXX core 这里需要修改,封装一下.自动识别类型,来进行传参
		String className = eventClass.getName();
		if (ChangeListener.class.getName().equals(className)) {
			List<EventListener> list = eventMap.get(className);
			if (CollectionUtils.isNotEmpty(list)) {
				for (EventListener event : list) {
					((ChangeListener<T>) event).valueChanged((ChangeEvent<T>) object);
				}
			}
		} else if (MoveListener.class.getName().equals(className)) {
			List<EventListener> list = eventMap.get(className);
			if (CollectionUtils.isNotEmpty(list)) {
				for (EventListener event : list) {
					((MoveListener) event).endMoved((MoveEvent) object);
				}
			}
		} else {

		}
	}
}
