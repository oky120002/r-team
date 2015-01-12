/**
 * 
 */
package com.r.web.autogame.engine.battle.box;

import com.r.web.autogame.engine.battle.Tearm;

/**
 * 战斗沙盒<br/>
 * 每场战斗都在单独的沙盒中进行
 * 
 * @author rain
 *
 */
public class BattleBox {
    /** 战斗团体A */
    private Tearm groupA;
    /** 战斗团体B */
    private Tearm groupB;

    /**
     * @param groupA
     *            战队A
     * @param groupB
     *            战队B
     */
    public BattleBox(Tearm groupA, Tearm groupB) {
        super();
        this.groupA = groupA;
        this.groupB = groupB;
    }

    /** 设置战队A */
    public void setGroupA(Tearm groupA) {
        this.groupA = groupA;
    }

    /** 设置战队B */
    public void setGroupB(Tearm groupB) {
        this.groupB = groupB;
    }

    /**
     * 开始战斗
     */
    public void startBattle() {

    }
}
