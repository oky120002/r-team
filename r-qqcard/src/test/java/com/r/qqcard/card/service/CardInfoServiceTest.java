/**
 * 
 */
package com.r.qqcard.card.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.qqcard.card.model.Card;

/**
 * @author rain
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class CardInfoServiceTest {

    @Resource(name = "service.cardinfo")
    private CardInfoService cardinfoService;

    @Test
    public void test() {
        Card card = cardinfoService.findCardByCardId(4418);

        System.out.println(card.getName());
    }

}
