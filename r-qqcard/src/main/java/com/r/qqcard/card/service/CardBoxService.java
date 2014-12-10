/**
 * 
 */
package com.r.qqcard.card.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.qqcard.card.dao.CardBoxDao;
import com.r.qqcard.card.model.CardBox;
import com.r.qqcard.card.qqhome.QQHomeBoxChange;
import com.r.qqcard.card.qqhome.QQHomeBoxStore;
import com.r.qqcard.card.qqhome.QQHomeCardChange;
import com.r.qqcard.card.qqhome.QQHomeCardStore;

/**
 * 卡片箱子业务处理器
 * 
 * @author rain
 *
 */
@Service("service.cardbox")
public class CardBoxService {
    /** 卡箱Dao */
    @Resource(name = "dao.cardbox")
    private CardBoxDao cardboxDao;
    /** 卡片信息业务处理器 */
    @Resource(name = "service.cardinfo")
    private CardInfoService cardinfoService;

    /** 初始化换卡箱,储藏箱 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void initCardBox(QQHomeBoxChange changeBox, QQHomeBoxStore storeBox) {
        cardboxDao.deleteAll(); // 清空数据
        Collection<QQHomeCardChange> changeCards = changeBox.getChangeCards();
        Collection<QQHomeCardStore> storeCards = storeBox.getStoreCards();

        // 换卡箱
        for (QQHomeCardChange cc : changeCards) {
            if (cc.getType() != 0) {
                continue; // 非正常状态直接跳过
            }
            CardBox cb = new CardBox();
            cb.setCard(cardinfoService.findCardByCardId(cc.getId()));
            cb.setSlot(cc.getSlot());
            cb.setStatus(cc.getStatus());
            cb.setType(cc.getType());
            cb.setBoxtype(0);
            cardboxDao.create(cb);
        }

        // 储藏箱
        for (QQHomeCardStore cs : storeCards) {
            if (cs.getType() != 0) {
                continue; // 非正常状态直接跳过
            }
            CardBox cb = new CardBox();
            cb.setCard(cardinfoService.findCardByCardId(cs.getId()));
            cb.setSlot(cs.getSlot());
            cb.setStatus(cs.getStatus());
            cb.setType(cs.getType());
            cb.setBoxtype(1);
            cardboxDao.create(cb);
        }
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
}
