package com.r.web.component.incrementer.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 自增长类工厂类
 * 
 * @author Administrator
 *
 */
public class IncrementerFactory extends IncrementerContext implements FactoryBean<IncrementerContext> {

    @Override
    public IncrementerContext getObject() throws Exception {
        return IncrementerContext.getContext();
    }

    @Override
    public Class<?> getObjectType() {
        return IncrementerContext.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
