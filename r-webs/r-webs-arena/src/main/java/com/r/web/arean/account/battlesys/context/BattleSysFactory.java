/**
 * 
 */
package com.r.web.arean.account.battlesys.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 战斗容器工厂类
 * 
 * @author Administrator
 * 
 */
public class BattleSysFactory extends BattleSysContext implements FactoryBean<BattleSysContext> {

    @Override
    public BattleSysContext getObject() throws Exception {
        return BattleSysContext.getContext();
    }

    @Override
    public Class<?> getObjectType() {
        return BattleSysContext.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
