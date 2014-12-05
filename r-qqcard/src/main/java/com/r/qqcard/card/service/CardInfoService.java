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
import com.r.qqcard.card.domain.Card;
import com.r.qqcard.card.domain.Compose;
import com.r.qqcard.card.domain.Gift;
import com.r.qqcard.card.domain.Theme;
import com.r.qqcard.card.domain.bean.CardInfo;
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
    private GiftDao gitfDao;

    /** 合成关系Dao */
    @Resource(name = "dao.compose")
    private ComposeDao composeDao;

    // -----------QQ秀
    /** 保存QQ秀 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void saveGifts(Collection<Gift> gifts) {
        gitfDao.saves(gifts);
    }

    /** 获取QQ秀数量 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public long getGiftSize() {
        return gitfDao.queryAllSize();
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

    // -----------卡片
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

}
