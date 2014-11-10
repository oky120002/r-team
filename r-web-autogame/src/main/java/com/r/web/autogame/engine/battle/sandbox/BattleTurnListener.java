/**
 * 
 */
package com.r.web.autogame.engine.battle.sandbox;

import com.r.web.autogame.engine.battle.FighterDetail;
import com.r.web.autogame.engine.battle.Skill;

/**
 * 战斗回合监听器<br/>
 * 负责对战斗每个回合进行详细的数据监听
 * 
 * @author rain
 *
 */
public interface BattleTurnListener {

    /**
     * 战斗回合
     * 
     * @param turn
     *            战斗回合数
     * @param attFighter
     *            攻击方
     * @param skill
     *            攻击方使用的技能
     * @param targetFighters
     *            攻击目标
     */
    void battleTurn(int turn, FighterDetail attFighter, Skill skill, FighterDetail... targetFighters);
}
