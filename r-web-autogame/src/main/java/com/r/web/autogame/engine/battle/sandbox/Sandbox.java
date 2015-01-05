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
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * @param scene the scene to set
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * @return the groupA
     */
    public Group getGroupA() {
        return groupA;
    }

    /**
     * @param groupA the groupA to set
     */
    public void setGroupA(Group groupA) {
        this.groupA = groupA;
    }

    /**
     * @return the groupB
     */
    public Group getGroupB() {
        return groupB;
    }

    /**
     * @param groupB the groupB to set
     */
    public void setGroupB(Group groupB) {
        this.groupB = groupB;
    }

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
