/**
 * 
 */
package com.r.web.autogame.engine.battle.context;

/**
 * 战斗引擎容器<br/>
 * 主要负责战斗计算
 * 
 * @author rain
 *
 */
public class BattleEngineContext extends BattleEngineContextConfigurator {

    /** 战斗引擎 */
    private static BattleEngineContext battleEngine;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }

    /** 返回战斗引擎唯一实体 */
    public static BattleEngineContext getContext() {
        return BattleEngineContext.battleEngine;
    }

}
