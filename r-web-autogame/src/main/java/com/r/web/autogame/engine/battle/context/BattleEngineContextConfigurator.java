/**
 * 
 */
package com.r.web.autogame.engine.battle.context;

import org.springframework.beans.factory.InitializingBean;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 战斗引擎配置容器<br/>
 * 主要负责战斗引擎各项参数得配置和管理
 * 
 * @author rain
 *
 */
public class BattleEngineContextConfigurator implements InitializingBean {
    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(BattleEngineContextConfigurator.class);

    /** 初始化战斗人员基础属性最大值 */
    private int initialFighterBaseAttrMaxNumber = -1;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("战斗引擎参数初始化......");
        
    }

    /** 获取初始化战斗人员基础属性最大值 */
    public int getInitialFighterBaseAttrMaxNumber() {
        return initialFighterBaseAttrMaxNumber;
    }

    /** 设置初始化战斗人员基础属性最大值 */
    public void setInitialFighterBaseAttrMaxNumber(int initialFighterBaseAttrMaxNumber) {
        this.initialFighterBaseAttrMaxNumber = initialFighterBaseAttrMaxNumber;
    }

}
