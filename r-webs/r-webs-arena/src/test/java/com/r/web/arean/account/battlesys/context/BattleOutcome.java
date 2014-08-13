/**
 * 
 */
package com.r.web.arean.account.battlesys.context;

import com.r.web.arean.account.battlesys.battler.Battler;

/**
 * 战斗结果
 * 
 * @author oky
 * 
 */
public interface BattleOutcome {
    /** 开始战斗 */
    void battleStart(Battler srcBattler, Battler tarBattler);

    /** 战斗过程 */
    void battleOutcome(Battler srcBattler, Battler tarBattler, String battleContent);

    /** 开始战斗 */
    void battlerEnd(Battler winBattler, Battler loseBattler);
}
