/**
 * 
 */
package com.r.qqcard.card.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.core.exceptions.SwitchPathException;
import com.r.qqcard.card.dao.CardBoxDao;
import com.r.qqcard.card.model.CardBox;
import com.r.qqcard.card.qqhome.QQHomeBoxChange;
import com.r.qqcard.card.qqhome.QQHomeBoxStore;
import com.r.qqcard.card.qqhome.QQHomeCardChange;
import com.r.qqcard.card.qqhome.QQHomeCardStore;
import com.r.qqcard.core.component.WebAction;
import com.r.qqcard.core.support.AbstractService;

/**
 * 卡片箱子业务处理器
 * 
 * @author rain
 *
 */
@Service("service.cardbox")
public class CardBoxService extends AbstractService {
    // /** 拥有的变卡箱最大格子数Key */
    // private static final String KEY_MAX_BOXCHANGE =
    // "service.cardbox.max.boxchange";
    // /** 拥有的储藏箱最大格子数Key */
    // private static final String KEY_MAX_BOXSTORE =
    // "service.cardbox.max.boxstore";

    /** 卡箱Dao */
    @Resource(name = "dao.cardbox")
    private CardBoxDao cardboxDao;
    // /** 基础数据业务处理类 */
    // @Resource(name = "service.basedata")
    // private BaseDataService baseDataService;
    /** 网络行为 */
    @Resource(name = "component.webaction")
    private WebAction action;
    /** 卡片信息业务处理器 */
    @Resource(name = "service.cardinfo")
    private CardInfoService cardinfoService;

    /** 初始化换卡箱,储藏箱 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void initCardBox(QQHomeBoxChange changeBox, QQHomeBoxStore storeBox) {
        cardboxDao.deleteAll(); // 清空数据
        int maxBoxChange = changeBox.getMax(); // 拥有的变卡箱最大格子数
        int maxBoxStore = storeBox.getMax(); // 拥有的储藏箱最大格子数

        Collection<QQHomeCardChange> changeCards = changeBox.getChangeCards();
        Collection<QQHomeCardStore> storeCards = storeBox.getStoreCards();

        // 初始化临时变量
        Map<Integer, CardBox> changeMap = new HashMap<Integer, CardBox>();
        for (int index = 0; index < maxBoxChange; index++) {
            CardBox cb = new CardBox();
            cb.setCardBoxtype(0);
            cb.setSlot(index);
            cb.setStatus(4);
            cb.setType(0);
            changeMap.put(Integer.valueOf(index), cb);
        }
        Map<Integer, CardBox> storeMap = new HashMap<Integer, CardBox>();
        for (int index = 0; index < maxBoxStore; index++) {
            CardBox cb = new CardBox();
            cb.setCardBoxtype(1);
            cb.setSlot(index);
            cb.setStatus(4);
            cb.setType(0);
            storeMap.put(Integer.valueOf(index), cb);
        }

        // 换卡箱
        for (QQHomeCardChange cc : changeCards) {
            if (cc.getStatus() != 0) {
                continue; // 非正常状态直接跳过
            }
            CardBox cb = changeMap.get(Integer.valueOf(cc.getSlot()));
            homeCard2CardBox(cc, cb);
            changeMap.put(Integer.valueOf(cc.getSlot()), cb);
        }

        // 储藏箱
        for (QQHomeCardStore cs : storeCards) {
            if (cs.getStatus() != 0) {
                continue; // 非正常状态直接跳过
            }
            CardBox cb = storeMap.get(Integer.valueOf(cs.getSlot()));
            homeCard2CardBox(cs, cb);
            storeMap.put(Integer.valueOf(cs.getSlot()), cb);

        }
        cardboxDao.creates(changeMap.values());
        cardboxDao.creates(storeMap.values());

        // baseDataService.setValue(KEY_MAX_BOXCHANGE, maxBoxChange);
        // baseDataService.setValue(KEY_MAX_BOXCHANGE, maxBoxStore);
    }

    /** 换卡箱卡片bean转成卡箱实体 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public void homeCard2CardBox(QQHomeCardChange cc, CardBox cardBox) {
        cardBox.setCard(cardinfoService.findCardByCardId(cc.getId()));
        cardBox.setSlot(cc.getSlot());
        cardBox.setStatus(cc.getStatus());
        cardBox.setType(cc.getType());
        cardBox.setCardBoxtype(0);
    }

    /** 保险箱箱卡片bean转成卡箱实体 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public void homeCard2CardBox(QQHomeCardStore cs, CardBox cardBox) {
        cardBox.setCard(cardinfoService.findCardByCardId(cs.getId()));
        cardBox.setSlot(cs.getSlot());
        cardBox.setStatus(cs.getStatus());
        cardBox.setType(cs.getType());
        cardBox.setCardBoxtype(1);
    }

    /** 根据卡片箱子ID查找卡片箱子 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public CardBox find(String cardBoxId) {
        return cardboxDao.find(cardBoxId);
    }

    /** 返回变卡箱所有卡片 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public Collection<CardBox> queryAllByChange() {
        return cardboxDao.queryAll(0);
    }

    /** 返回储藏箱所有卡片 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public Collection<CardBox> queryAllByStore() {
        return cardboxDao.queryAll(1);
    }

    /**
     * 交换卡片
     * 
     * @param cardBox
     *            换卡箱卡片
     * @return 交换结果(大于等于0:换成功后卡片位置,-1:网络错误,-2:找不到相应的记录,-3:系统繁忙)
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public int changeCardBox(CardBox cardBox) {
        int flag = -1;
        int slot = cardBox.getSlot();
        switch (cardBox.getCardBoxtype()) {
        case 0: // 换卡箱
            flag = this.action.doChange2Store(slot);
            break;
        case 1: // 保险箱
            flag = this.action.doStore2Change(slot);
            break;
        default:
            throw new SwitchPathException();
        }
        if (0 <= flag) {
            cardBox.setCardBoxtype(cardBox.getCardBoxtype() == 0 ? 1 : 0);
            cardBox.setSlot(flag);
            cardboxDao.update(cardBox);
        }
        return flag;
    }

    /** 一键抽卡 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Collection<CardBox> randomAllCard() {
        Collection<QQHomeCardChange> cards = this.action.doRandomAllCard();
        Collection<CardBox> cbs = new ArrayList<CardBox>();
        if (CollectionUtils.isNotEmpty(cards)) {
            for (QQHomeCardChange cc : cards) {
                CardBox cardBox = new CardBox();
                homeCard2CardBox(cc, cardBox);
                cbs.add(cardBox);
            }
            this.cardboxDao.creates(cbs);
        }
        return cbs;
    }
}
