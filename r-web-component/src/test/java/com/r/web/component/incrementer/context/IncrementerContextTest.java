package com.r.web.component.incrementer.context;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml", })
public class IncrementerContextTest {

    @Resource(name = "incrementer")
    private IncrementerContext incrementer;

    @Test
    public void test() {
        incrementer.getIncrementer().clearAll();
        
        System.out.println(incrementer.getIncrementer().nextIntValue());
        System.out.println(incrementer.getIncrementer().nextIntValue());
        System.out.println(incrementer.getIncrementer().nextIntValue());
        incrementer.getIncrementer().clear();
        System.out.println(incrementer.getIncrementer().nextIntValue());
        System.out.println(incrementer.getIncrementer().nextIntValue());

        
        System.out.println(incrementer.getIncrementer().nextIntValue("abcd"));
        System.out.println(incrementer.getIncrementer().nextIntValue("abcd"));
        incrementer.getIncrementer().clear("abcd");
        System.out.println(incrementer.getIncrementer().nextIntValue("abcd"));
        System.out.println(incrementer.getIncrementer().nextIntValue("abcd"));
        
        
        incrementer.getIncrementer().clearAll();
        System.out.println(incrementer.getIncrementer().nextIntValue());
        System.out.println(incrementer.getIncrementer().nextIntValue("abcd"));
    }

}
