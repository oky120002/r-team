/**
 * 
 */
package com.r.web.autogame.engine.battle.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 战斗引擎容器工厂<br/>
 * 主要生产战斗引擎实例
 * 
 * @author Administrator
 *
 */
public class BattleEngineContextFactory extends BattleEngineContext implements FactoryBean<BattleEngineContext> {

    @Override
    public BattleEngineContext getObject() throws Exception {
        return BattleEngineContext.getContext();
    }

    @Override
    public Class<?> getObjectType() {
        return BattleEngineContext.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
