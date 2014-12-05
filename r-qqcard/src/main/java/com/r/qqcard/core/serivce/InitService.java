/**
 * 
 */
package com.r.qqcard.core.serivce;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.qqcard.account.service.AccountService;
import com.r.qqcard.card.domain.Card;
import com.r.qqcard.card.domain.Compose;
import com.r.qqcard.card.domain.Gift;
import com.r.qqcard.card.domain.Theme;
import com.r.qqcard.card.domain.bean.CardInfo;
import com.r.qqcard.card.qqhome.QQHome;
import com.r.qqcard.card.service.CardInfoService;
import com.r.qqcard.core.component.WebAction;
import com.r.qqcard.core.support.AbstractService;
import com.r.qqcard.notify.context.NotifyContext;
import com.r.qqcard.notify.handler.Event;

/**
 * 初始化业务处理器
 * 
 * @author rain
 *
 */
@Component("service.init")
public class InitService extends AbstractService {

    /** 通知 */
    @Resource(name = "springxml.notify")
    private NotifyContext notify;
    /** 网络行为 */
    @Resource(name = "component.webaction")
    private WebAction action;
    /** 卡片信息业务处理器 */
    @Resource(name = "service.cardinfo")
    private CardInfoService cardInfoService;
    /** 账户信息业务处理类 */
    @Resource(name = "service.account")
    private AccountService accountService;

    /** 全局数据初始化,登陆前的初始化工作 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void initGlobal() {
        logger.info("初始化卡片,卡片主题,合成后礼物和合成规则信息......");

        CardInfo cardInfoV3 = action.getCardInfoV3();

        Collection<Theme> themes = cardInfoV3.getThemes();
        Collection<Card> cards = cardInfoV3.getCards();
        Collection<Compose> composes = cardInfoV3.getComposes();
        Collection<Gift> gifts = cardInfoV3.getGifts();

        long themeSize = cardInfoService.getThemeSize();
        if (themes.size() != themeSize) {
            cardInfoService.saveThemes(themes);
        }

        long cardSize = cardInfoService.getCardSize();
        if (cards.size() != cardSize) {
            cardInfoService.saveCards(cards);
        }

        long composeSize = cardInfoService.getComposeSize();
        if (composes.size() != composeSize) {
            cardInfoService.saveCompses(composes);
        }

        long giftSize = cardInfoService.getGiftSize();
        if (gifts.size() != giftSize) {
            cardInfoService.saveGifts(gifts);
        }

        logger.debug("从数据库查找到卡片主题{}套,网络返回卡片主题{}套", String.valueOf(themeSize), themes.size());
        logger.debug("从数据库查找到卡片{}张,网络返回卡片{}张", String.valueOf(cardSize), cards.size());
        logger.debug("从数据库查找到合成规则{}条,网络返回合成规则{}条", String.valueOf(composeSize), composes.size());
        logger.debug("从数据库查找到QQ秀{}条,网络返回QQ秀{}条", String.valueOf(giftSize), gifts.size());
        notify.notifyEvent(Event.init$全局数据初始化完成);
    }

    /** 初始化交换箱信息，卡箱信息，账号信息 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void initGameDatas() {
        logger.info("初始化交换箱信息，卡箱信息，账号信息......");
        QQHome home = action.getQQHome();
        accountService.initAccount(home.getUser());
        notify.notifyEvent(Event.init$玩家信息初始化完成);
    }
}
