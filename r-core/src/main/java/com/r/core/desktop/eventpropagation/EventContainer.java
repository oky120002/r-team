/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2012-12-31
 * <修改描述:>
 */
package com.r.core.desktop.eventpropagation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.core.exceptions.SException;
import com.r.core.exceptions.SwitchPathException;
import com.r.core.util.RandomUtil;
import com.r.core.util.TaskUtil;

/**
 * 事件核心容器<br />
 * 要使用事件机制,必须在方法上添加EventSing注解,且调用register方法注册此类
 * 
 * @author rain
 * @version [1.0, 2012-12-31]
 */
public class EventContainer {
	/** 空 */
	public static final Object EMPTY = new Object();

	/** 容器ID */
	private String uid = null;

	/** 拥有事件传递性质的子容器 */
	private Set<EventShow> subEventShows = new HashSet<EventShow>();

	/** 事件ID和执行方法的关系对应列表 */
	private Map<String, Method> eventIdMethods = new HashMap<String, Method>();

	public EventContainer(Class<?> clazz) {
		uid = RandomUtil.uuid();
		register(clazz);
	}

	/** 事件执行和传递 */
	public void execEvent(final EventShow eventShow, final Event event, final Object object, final EventCallBack callBack) {
		if (eventShow == null) {
			return;
		}
		if (event == null) {
			return;
		}
		final Method method = this.eventIdMethods.get(event.getId());
		if (method != null && (event.getEcId() == null || event.getEcId() == this.uid)) {
			EventSign eventSign = method.getAnnotation(EventSign.class);
			boolean isSynchronous = false;
			switch (eventSign.synchronous()) {
			case NULL:
				isSynchronous = event.isSynchronous();
				break;
			case 同步:
				isSynchronous = true;
				break;
			case 异步:
				isSynchronous = false;
				break;
			default:
				throw new SwitchPathException("不应该走到此switch分支.");
			}
			if (isSynchronous) {
				try {
					method.invoke(eventShow, event, object == null ? EventContainer.EMPTY : object);
				} catch (IllegalArgumentException e) {
					throw new SException("向" + eventShow.getClass().getName() + "类的" + method.getName() + "()方法传递了一个不合法或不正确的参。<code>" + e.getCause().toString() + "</code>", e);
				} catch (IllegalAccessException e) {
					throw new SException("无法访问" + eventShow.getClass().getName() + "类、它的字段、它的方法或者它的构造方法。<code>" + e.getCause().toString() + "</code>", e);
				} catch (InvocationTargetException e) {
					throw new SException("调用" + eventShow.getClass().getName() + "类的" + method.getName() + "()方法或者调用它的构造错误。<code>" + e.getCause().toString() + "</code>", e);
				} catch (final Exception e) {
					if (callBack != null) {
						callBack.callBack(object, false, e.toString());
					}
				}
			} else {
				TaskUtil.executeTask(new Runnable() {
					@Override
					public void run() {
						try {
							method.invoke(eventShow, event, object);
						} catch (IllegalArgumentException e) {
							throw new SException("向" + eventShow.getClass().getName() + "类的" + method.getName() + "()方法传递了一个不合法或不正确的参。<code>" + e.getCause().toString() + "</code>", e);
						} catch (IllegalAccessException e) {
							throw new SException("无法访问" + eventShow.getClass().getName() + "类、它的字段、它的方法或者它的构造方法。<code>" + e.getCause().toString() + "</code>", e);
						} catch (InvocationTargetException e) {
							throw new SException("调用" + eventShow.getClass().getName() + "类的" + method.getName() + "()方法或者调用它的构造错误。<code>" + e.getCause().toString() + "</code>", e);
						} catch (final Exception e) {
							if (callBack != null) {
								callBack.callBack(object, false, e.toString());
							}
						}
					}
				});
			}

			if (callBack != null) {
				callBack.callBack(object, true, null);
			}
		}

		// 继续事件链
		for (EventShow subShowEvent : this.subEventShows) {
			subShowEvent.executeEvent(event, object, callBack);
		}
	}

	/** 添加事件子容器,用来做事件传递,会递归扫描子容器的事件 */
	public void addShowEvent(EventShow subShowEvent) {
		subEventShows.add(subShowEvent);
	}

	/** 删除事件子容器 */
	public void removeShowEvent(EventShow eventShow) {
		subEventShows.remove(eventShow);
	}

	/** 注册传入的类,使事件容器能够识别此类的事件注解 */
	public void register(Class<?> clazz) {
		this.eventIdMethods.clear();
		// 扫描注册类的所有事件方法
		for (Method method : clazz.getMethods()) {
			EventSign eventSign = method.getAnnotation(EventSign.class);
			if (eventSign != null) {
				if (StringUtils.isBlank(eventSign.value())) {
					this.eventIdMethods.put(clazz.getSimpleName() + "." + method.getName(), method);
				} else {
					this.eventIdMethods.put(eventSign.value(), method);
				}
			}
		}
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return 返回 subEventShows
	 */
	@SuppressWarnings("unchecked")
	public Set<EventShow> getSubEventShows() {
		return SetUtils.unmodifiableSet(subEventShows);
	}

}
