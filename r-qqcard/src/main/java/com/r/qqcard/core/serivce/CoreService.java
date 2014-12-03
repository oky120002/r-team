/**
 * 
 */
package com.r.qqcard.core.serivce;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.qqcard.card.domain.Card;
import com.r.qqcard.card.domain.Compose;
import com.r.qqcard.card.domain.Gift;
import com.r.qqcard.card.domain.Theme;
import com.r.qqcard.card.domain.bean.CardInfo;
import com.r.qqcard.card.service.CardInfoService;
import com.r.qqcard.core.component.WebAction;
import com.r.qqcard.core.support.AbstractService;
import com.r.qqcard.notify.context.NotifyContext;
import com.r.qqcard.notify.handler.Event;
import com.r.qqcard.notify.handler.EventAnn;

/**
 * 核心业务处理器
 * 
 * @author rain
 *
 */
@Component("component.core")
public class CoreService extends AbstractService {

    /** 网络行为 */
    @Resource(name = "component.webaction")
    private WebAction action;
    /** 卡片信息业务处理器 */
    @Resource(name = "service.cardinfo")
    private CardInfoService cardInfoService;
    /** 通知 */
    @Resource(name = "springxml.notify")
    private NotifyContext notify;

    /** 程序启动,登陆前的初始化工作 */
    @EventAnn(Event.初始化数据)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void preLogin() {
        CardInfo cardInfoV3 = action.getCardInfoV3();

        Collection<Theme> themes = cardInfoV3.getThemes();
        Collection<Card> cards = cardInfoV3.getCards();
        Collection<Compose> composes = cardInfoV3.getComposes();
        Collection<Gift> gifts = cardInfoV3.getGifts();

        cardInfoService.saveThemes(themes);
        cardInfoService.saveCards(cards);
        cardInfoService.saveCompses(composes);
        cardInfoService.saveGifts(gifts);

        notify.notifyEvent(Event.初始化数据完成);

    }
}
