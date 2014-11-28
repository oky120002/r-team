package com.r.qqcard.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * QQ辅助程序容器工厂
 * 
 * @author Administrator
 *
 */
public class QQCardFactory extends QQCardContext implements FactoryBean<QQCardContext> {

    @Override
    public QQCardContext getObject() throws Exception {
        return QQCardContext.getContext();
    }

    @Override
    public Class<?> getObjectType() {
        return QQCardContext.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
