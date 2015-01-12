/**
 * 
 */
package com.r.web.autogame.engine.battle.context;

import java.util.Collection;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.web.autogame.engine.battle.Fighter;
import com.r.web.autogame.engine.battle.Skill;
import com.r.web.autogame.engine.battle.core.BattleUtils;

/**
 * 战斗引擎容器<br/>
 * 主要负责战斗计算
 * 
 * @author rain
 *
 */
public class BattleEngineContext extends BattleEngineContextConfigurator {
    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(BattleEngineContext.class);

    /** 战斗引擎 */
    private static BattleEngineContext battleEngineContext = null;
    /** 战斗引擎助手 */
    private BattleEngineHepler battleEngineHepler = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        logger.debug("战斗引擎初始化......");
        BattleEngineContext.battleEngineContext = this;
        if (battleEngineHepler == null) {
            battleEngineHepler = BattleEngineHepler.createDefaultBattleEngineHepler();
        }

    }

    /** 返回战斗引擎唯一实体 */
    public static BattleEngineContext getContext() {
        return BattleEngineContext.battleEngineContext;
    }

    /** 获取战斗助手 */
    public BattleEngineHepler getBattleEngineHepler() {
        return this.battleEngineHepler;
    }

    /** 生成初始战斗者 */
    public Fighter createInitialFighter() {
        return this.battleEngineHepler.createInitialFighter();
    }

}
