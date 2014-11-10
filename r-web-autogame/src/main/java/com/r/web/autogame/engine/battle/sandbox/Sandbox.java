/**
 * 
 */
package com.r.web.autogame.engine.battle.sandbox;

import com.r.web.autogame.engine.battle.Group;
import com.r.web.autogame.engine.battle.Scene;

/**
 * 战斗沙盒<br/>
 * 每场战斗都在单独的沙盒中进行
 * 
 * @author rain
 *
 */
public class Sandbox {
    /** 战斗场景 */
    private Scene scene;
    /** 战斗团体A */
    private Group groupA;
    /** 战斗团体B */
    private Group groupB;

    /**
     * @param scene
     *            战斗场景
     * @param groupA
     *            战斗团体A
     * @param groupB
     *            战斗团体B
     * @param battleTurnListener
     *            战斗回合监听器
     */
    public Sandbox(Scene scene, Group groupA, Group groupB) {
        super();
        this.scene = scene;
        this.groupA = groupA;
        this.groupB = groupB;
    }

    /**
     * 开始战斗
     */
    public void startBattle() {

    }

}
