package com.r.web.vote931.core.sequence;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.webcomponents.incrementer.RainMaxValueIncrementer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class RainMySQLMaxValueIncrementerTest {

    @Resource(name = "idGenarater")
    private RainMaxValueIncrementer incrementer;

    @Test
    public void test() {
        System.out.println(incrementer.nextIntValue());// 1
        System.out.println(incrementer.nextIntValue("测试1"));// 1
        System.out.println(incrementer.nextIntValue("测试1"));// 2
        System.out.println(incrementer.nextIntValue("测试2"));// 1
        System.out.println(incrementer.nextIntValue());// 2
        System.out.println(incrementer.nextIntValue("测试1"));// 3
        System.out.println(incrementer.nextIntValue("测试2"));// 2
    }

}
