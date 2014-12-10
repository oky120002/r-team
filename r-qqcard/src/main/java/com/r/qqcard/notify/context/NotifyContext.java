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
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;

import com.r.core.exceptions.InvokeMethodException;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.StrUtil;
import com.r.core.util.TaskUtil;
import com.r.qqcard.notify.handler.Event;
import com.r.qqcard.notify.handler.EventAnn;

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
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Controller.class);
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            Class<?> userClass = ClassUtils.getUserClass(entry.getValue().getClass()); // 直接获取用户类(代理类实质上是用户类的子类),一般只有加了事务的类才会被Hibernate代理
            Method[] methods = userClass.getMethods();
            for (Method method : methods) {
                // 获取事件注解
                EventAnn eventAnn = method.getAnnotation(EventAnn.class);
                if (eventAnn != null) {
                    // 校验
                    checkMethodParam(method);
                    Event event = eventAnn.value();
                    if (!this.events.containsKey(event)) {
                        this.events.put(event, new ArrayList<EventMethod>());
                    }
                    String methodName = method.getName();
                    logger.debug("扫描到注解@EventAnn({}) : {}:{}({})", eventAnn.value().name(), userClass.getName(), methodName, ArrayUtils.toString(event.getClazzes()));
                    this.events.get(event).add(new EventMethod(entry.getKey(), methodName));
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
                TaskUtil.executeTask(new NotifyTask(bean, eventMethod.methodName, event.getClazzes(), objects));
            }
        }
    }

    /**
     * 检查方法入参和事件能否一致
     * 
     * @param method
     */
    private void checkMethodParam(Method method) {
        Class<?> beanClass = method.getDeclaringClass();
        EventAnn annotation = method.getAnnotation(EventAnn.class);
        Event event = annotation.value();
        if (event == null) {
            throw new NullPointerException(StrUtil.formart("{}.{}({})方法上的注解事件不能为空", beanClass.getName(), method.getName(), ArrayUtils.toString(method.getParameterTypes())));
        }

        Class<?>[] clazzes = event.getClazzes();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (!ArrayUtils.isEquals(clazzes, parameterTypes)) {
            throw new IllegalArgumentException(StrUtil.formart("{}.{}({})方法入参和注解配置入参不一致", beanClass.getName(), method.getName(), ArrayUtils.toString(clazzes)));
        }
    }

    private class EventMethod {
        String beanName;
        String methodName;

        public EventMethod(String beanName, String methodName) {
            super();
            this.beanName = beanName;
            this.methodName = methodName;
        }
    }

    private class NotifyTask implements Runnable {
        private Object bean;
        private String methodName;
        private Class<?>[] clazzes;
        private Object[] objects;

        /**
         * @param bean
         * @param methodName
         * @param clazzes
         * @param objects
         */
        public NotifyTask(Object bean, String methodName, Class<?>[] clazzes, Object[] objects) {
            super();
            this.bean = bean;
            this.methodName = methodName;
            this.clazzes = clazzes;
            this.objects = objects;
        }

        @Override
        public void run() {
            try {
                Method method = bean.getClass().getMethod(methodName, clazzes);
                method.invoke(bean, objects);
            } catch (Exception e) {
                throw new InvokeMethodException(e);
            }
        }
    }
}
