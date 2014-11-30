package com.r.qqcard.notify.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 通知容器工厂
 * 
 * @author Administrator
 *
 */
public class NotifyFactory extends NotifyContext implements FactoryBean<NotifyContext> {

    @Override
    public NotifyContext getObject() throws Exception {
        return NotifyContext.getContext();
    }

    @Override
    public Class<?> getObjectType() {
        return NotifyContext.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
