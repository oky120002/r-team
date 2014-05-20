/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2012-12-17
 * <修改描述:>
 */
package com.r.core.desktop.eventpropagation;

/**
 * 事件
 * 
 * @author rain
 * @version [1.0, 2012-12-17]
 */
public class Event {

	/** 线程堆栈序号 */
	public static final int DEFAULT_TRACE = 3;

	/** 事件ID */
	private String id;

	/** 事件所在容器ID,如果不为空,则只执行对应容器中的事件,其它容器中的事件不执行 */
	private String ecId;

	/** 事件调用类型 */
	private String sourceClassName;

	/** 事件调用方法 */
	private String sourceMethodName;

	/** 是否同步事件,默认false */
	private boolean isSynchronous = false;

	private Event() {

	}

	/**
	 * 
	 * <创建一个拥有唯一标识符且为异步执行的事件>
	 * 
	 * @param id
	 *            事件ID
	 * 
	 * @return Event [事件]
	 * @author rain
	 */
	public static final Event newEvent(String id) {
		return event(id, null, true, Event.DEFAULT_TRACE);
	}

	/**
	 * 
	 * <创建一个拥有唯一标识符且为异步执行的事件>
	 * 
	 * @param id
	 *            事件ID
	 * @param ecId
	 *            事件容器ID
	 * 
	 * @return Event [事件]
	 * @author rain
	 */
	public static final Event newEvent(String id, String ecId) {
		return event(id, ecId, true, Event.DEFAULT_TRACE);
	}

	/**
	 * 
	 * <创建一个拥有唯一标识符且为异步执行的事件>
	 * 
	 * @param id
	 *            事件ID
	 * @param ecId
	 *            事件容器ID
	 * 
	 * @return Event [事件]
	 * @author rain
	 */
	public static final Event newEvent(String id, String ecId, int trace) {
		return event(id, ecId, true, trace);
	}

	/**
	 * 
	 * <创建一个拥有唯一标识符的事件>
	 * 
	 * @param id
	 *            事件ID
	 * @param isSynchronous
	 *            是否同步事件
	 * 
	 * @return Event [事件]
	 * @author rain
	 */
	public static final Event newEvent(String id, boolean isSynchronous) {
		return event(id, null, isSynchronous, Event.DEFAULT_TRACE);
	}

	/**
	 * 
	 * <创建一个拥有唯一标识符的事件>
	 * 
	 * @param id
	 *            事件ID
	 * @param ecId
	 *            事件容器ID
	 * @param isSynchronous
	 *            是否同步事件
	 * 
	 * @return Event [事件]
	 * @author rain
	 */
	public static final Event newEvent(String id, String ecId, boolean isSynchronous) {
		return event(id, ecId, isSynchronous, Event.DEFAULT_TRACE);
	}

	/**
	 * 
	 * <创建一个拥有唯一标识符的事件>
	 * 
	 * @param id
	 *            事件ID
	 * @param ecId
	 *            事件容器ID
	 * @param isSynchronous
	 *            是否同步事件
	 * 
	 * @return Event [事件]
	 * @author rain
	 */
	public static final Event newEvent(String id, String ecId, boolean isSynchronous, int trace) {
		return event(id, ecId, isSynchronous, trace);
	}

	/**
	 * 
	 * <创建一个拥有唯一标识符的事件>
	 * 
	 * @param id
	 *            事件ID
	 * @param ecId
	 *            事件容器ID
	 * @param isSynchronous
	 *            是否同步事件
	 * @param trace
	 *            线程堆栈序号,一般来说填入3就可以从线程堆栈中取出哪个类的方法调用了此生成方法
	 * 
	 * @return Event [事件]
	 * @author rain
	 */
	private static final Event event(String id, String ecId, boolean isSynchronous, int trace) {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[trace];

		Event eid = new Event();
		eid.id = id;
		eid.sourceClassName = stackTraceElement.getClassName();
		eid.sourceMethodName = stackTraceElement.getMethodName();
		eid.isSynchronous = isSynchronous;
		eid.ecId = ecId;
		return eid;
	}

	/**
	 * @return 返回 id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return 返回 ecId
	 */
	public String getEcId() {
		return ecId;
	}

	/**
	 * @return 返回 sourceClassName
	 */
	public String getSourceClassName() {
		return sourceClassName;
	}

	/**
	 * @return 返回 sourceMethodName
	 */
	public String getSourceMethodName() {
		return sourceMethodName;
	}

	/**
	 * @return 返回 isSynchronous
	 */
	public boolean isSynchronous() {
		return isSynchronous;
	}

	/**
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		if (ecId == null || ecId.trim().length() == 0) {
			return "[" + id + "," + isSynchronous + "]";
		} else {
			return "[" + id + "," + ecId + "," + isSynchronous + "]";
		}
	}
}
