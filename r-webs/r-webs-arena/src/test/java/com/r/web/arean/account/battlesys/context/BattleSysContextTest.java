package com.r.web.arean.account.battlesys.context;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.web.arean.account.battlesys.battler.Battler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class BattleSysContextTest {

    @Resource(name = "battleSysContenxt")
    private BattleSysContext battleSysContext;

    @Test
    public void test() {
        battleSysContext.test();
    }

    /**
     * 战斗
     * 
     * @param srcBattler
     *            发起者
     * @param tarBattler
     *            被挑战者
     * @param outcom
     *            战斗结果
     */
    public void battle(Battler srcBattler, Battler tarBattler, BattleOutcome outcom) {
//        att - 攻击
//        def - 防御
//        hp - 血
        
        
    }
}
