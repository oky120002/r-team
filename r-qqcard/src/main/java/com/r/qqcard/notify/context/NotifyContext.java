package com.r.qqcard.notify.context;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.r.core.exceptions.InvokeMethodException;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.qqcard.notify.handler.Event;
import com.r.qqcard.notify.handler.EventAnn;
import com.r.qqcard.notify.handler.ValueChangeAnn;
import com.r.qqcard.notify.handler.ValueType;

/**
 * 通知容器<br/>
 * 请再需要通知的事件和值变化的方法上,写上注解EventAnn和ValueChangeAnn<br/>
 * 这些方法必须是由spring管理的类
 * 
 * @author rain
 *
 */
public class NotifyContext extends NotifyContextConfigurator implements InitializingBean, ApplicationContextAware {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(NotifyContext.class);
    /** 对自身的引用 */
    private static NotifyContext context = null;
    /** 事件方法映射 */
    private Map<Event, List<EventMethod>> events = new HashMap<Event, List<EventMethod>>();
    /** 值变换方法映射 */
    private Map<ValueType, List<EventMethod>> valueChanges = new HashMap<ValueType, List<EventMethod>>();
    /** spring 容器 */
    private ApplicationContext applicationContext;

    /** 返回容器的唯一引用 */
    public static NotifyContext getContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        context = this;

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanName);
            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                EventAnn eventAnn = method.getAnnotation(EventAnn.class);
                ValueChangeAnn valueChangeAnn = method.getAnnotation(ValueChangeAnn.class);
                // 获取事件注解
                if (eventAnn != null) {
                    Event event = eventAnn.value();
                    if (event == null) {
                        continue;
                    }
                    checkMethodParam(event, method);
                    if (!this.events.containsKey(event)) {
                        this.events.put(event, new ArrayList<EventMethod>());
                    }
                    String methodName = method.getName();
                    logger.debug("扫描到注解@EventAnn : {}:{}({})", bean.getClass().getName(), methodName, ArrayUtils.toString(event.getClazzes()));
                    this.events.get(event).add(new EventMethod(beanName, methodName));
                }

                // 获取之变换注解
                if (valueChangeAnn != null) {
                    ValueType[] valueTypes = valueChangeAnn.value();
                    if (ArrayUtils.isEmpty(valueTypes)) {
                        continue;
                    }

                    for (ValueType valueType : valueTypes) {
                        checkMethodParam(valueType, method);
                        if (!this.valueChanges.containsKey(valueType)) {
                            this.valueChanges.put(valueType, new ArrayList<EventMethod>());
                        }
                        String methodName = method.getName();
                        logger.debug("扫描到注解@ValueChangeAnn : {}:{}()", bean.getClass().getName(), methodName, valueType.getClazz());
                        this.valueChanges.get(valueType).add(new EventMethod(beanName, methodName));
                    }
                }
            }
        }
    }

    /**
     * 通知发生某事件
     * 
     * @param event
     *            事件
     * @param objects
     *            事件发生时传入的参数
     */
    public void notifyEvent(Event event, Object... objects) {
        List<EventMethod> eventMethods = this.events.get(event);
        if (CollectionUtils.isNotEmpty(eventMethods)) {
            for (EventMethod eventMethod : eventMethods) {
                Object bean = this.applicationContext.getBean(eventMethod.beanName);
                try {
                    Method method = bean.getClass().getMethod(eventMethod.methodName, event.getClazzes());
                    method.invoke(bean, objects);
                } catch (Exception e) {
                    throw new InvokeMethodException(e);
                }
            }
        }
    }

    /**
     * 通知某值发生了改变
     * 
     * @param valueType
     *            值类型
     * @param newValue
     *            改变后的新值
     */
    public void notifyEvent(ValueType valueType, Object newValue) {
        List<EventMethod> eventMethods = this.valueChanges.get(valueType);
        if (CollectionUtils.isNotEmpty(eventMethods)) {
            for (EventMethod eventMethod : eventMethods) {
                Object bean = this.applicationContext.getBean(eventMethod.beanName);
                try {
                    Method method = bean.getClass().getMethod(eventMethod.methodName, valueType.getClazz());
                    method.invoke(bean, newValue);
                } catch (Exception e) {
                    throw new InvokeMethodException(e);
                }
            }
        }
    }

    /**
     * 检查方法入参和事件能否一致
     * 
     * @param event
     * @param method
     */
    private void checkMethodParam(Event event, Method method) {
        Class<?>[] clazzes = event.getClazzes();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (!ArrayUtils.isEquals(clazzes, parameterTypes)) {
            throw new IllegalArgumentException("方法入参和注解配置入参不一致");
        }
    }

    /** 检查值变换方法参数的正确性 */
    private void checkMethodParam(ValueType type, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (!ValueType.class.equals(parameterTypes[0])) {
            throw new IllegalArgumentException("方法第一个参数必须是ValueType");
        }
        if (!type.getClazz().equals(parameterTypes[1])) {
            throw new IllegalArgumentException("方法第二个参数必须是ValueType枚举对应的类型");
        }
    }

    class EventMethod {
        String beanName;
        String methodName;

        public EventMethod(String beanName, String methodName) {
            super();
            this.beanName = beanName;
            this.methodName = methodName;
        }

    }

}
