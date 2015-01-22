/**
 * 
 */
package com.r.web.autogame.engine.battle.context;

import com.r.core.util.RandomUtil;
import com.r.web.autogame.engine.battle.Fighter;
import com.r.web.autogame.engine.battle.Tearm;
import com.r.web.autogame.engine.battle.context.fighter.FighterDefaultImpl;

/**
 * 战斗引擎助手
 * 
 * @author rain
 *
 */
public class BattleEngineHepler {

    /** 初始化战斗人员基础属性最大值 */
    private int initialFighterBaseAttrMaxNumber = 21;

    /** 创建默认参数的战斗引擎助手 */
    public static BattleEngineHepler createDefaultBattleEngineHepler() {
        return new BattleEngineHepler();
    }

    /** 生成初始战斗者 */
    public Fighter createInitialFighter() {
        FighterDefaultImpl fighter = new FighterDefaultImpl();
        fighter.setEnd(getInitialFighterBaseAttrMaxNumberByRandom());
        
        return fighter;
    }

    /** 创建战斗团队 */
    public Tearm createTeram(String name, Fighter... fighters) {
        return null;
    }

    /** 获取初始化战斗人员基础属性最大值 */
    public int getInitialFighterBaseAttrMaxNumber() {
        return initialFighterBaseAttrMaxNumber;
    }

    /**
     * 设置初始化战斗人员基础属性最大值<br/>
     * 此值必须大于0,否则设置成默认值
     */
    public void setInitialFighterBaseAttrMaxNumber(int initialFighterBaseAttrMaxNumber) {
        if (initialFighterBaseAttrMaxNumber > 0) {
            this.initialFighterBaseAttrMaxNumber = initialFighterBaseAttrMaxNumber + 1;
        }
    }

    /** 获取初始化战斗人员基础属性随机值,此值不大于设置的最大值 */
    private int getInitialFighterBaseAttrMaxNumberByRandom() {
        return RandomUtil.randomInteger(initialFighterBaseAttrMaxNumber);
    }
}
