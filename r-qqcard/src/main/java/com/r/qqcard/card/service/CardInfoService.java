/**
 * 
 */
package com.r.qqcard.card.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.qqcard.card.dao.CardDao;
import com.r.qqcard.card.dao.ComposeDao;
import com.r.qqcard.card.dao.GiftDao;
import com.r.qqcard.card.dao.ThemeDao;
import com.r.qqcard.card.model.Card;
import com.r.qqcard.card.model.Compose;
import com.r.qqcard.card.model.Gift;
import com.r.qqcard.card.model.Theme;
import com.r.qqcard.core.component.WebAction;
import com.r.qqcard.core.support.AbstractService;

/**
 * @author rain
 *
 */
@Service("service.cardinfo")
public class CardInfoService extends AbstractService {
    /** 网络行为 */
    @Resource(name = "component.webaction")
    private WebAction action;

    /** 卡片Dao */
    @Resource(name = "dao.card")
    private CardDao cardDao;

    /** 主题Dao */
    @Resource(name = "dao.theme")
    private ThemeDao themeDao;

    /** QQ秀Dao */
    @Resource(name = "dao.gift")
    private GiftDao giftDao;

    /** 合成关系Dao */
    @Resource(name = "dao.compose")
    private ComposeDao composeDao;

    // -----------QQ秀
    /** 保存QQ秀 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void saveGifts(Collection<Gift> gifts) {
        giftDao.saves(gifts);
    }

    /** 获取QQ秀数量 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public long getGiftSize() {
        return giftDao.queryAllSize();
    }

    /** 清空QQ秀 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteAllGifts() {
        giftDao.deleteAll();
    }

    // -----------卡片主题
    /** 保存卡片主题 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void saveThemes(Collection<Theme> themes) {
        themeDao.saves(themes);
    }

    /** 获取卡片主题数量 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public long getThemeSize() {
        return themeDao.queryAllSize();
    }

    /** 清空主题 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteAllThemes() {
        themeDao.deleteAll();
    }

    // -----------卡片
    /** 根据卡片ID获取卡片 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public Card findCardByCardId(int id) {
        return cardDao.findCardByCardId(id);
    }

    /** 保存卡片 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void saveCards(Collection<Card> cards) {
        cardDao.saves(cards);
    }

    /** 获取卡片数量 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public long getCardSize() {
        return cardDao.queryAllSize();
    }

    /** 清空卡片 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteAllCards() {
        cardDao.deleteAll();
    }

    // -----------合成规则
    /** 保存合成规则 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void saveCompses(Collection<Compose> composes) {
        composeDao.saves(composes);
    }

    /** 获取合成规则数量 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public long getComposeSize() {
        return composeDao.queryAllSize();
    }

    /** 清空合成规则 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteAllCompose() {
        composeDao.deleteAll();
    }
}
