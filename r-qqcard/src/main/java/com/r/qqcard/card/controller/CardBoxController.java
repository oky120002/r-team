/**
 * 
 */
package com.r.qqcard.card.controller;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;

import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.qqcard.card.bean.CardBoxListItem;
import com.r.qqcard.card.model.CardBox;
import com.r.qqcard.card.service.CardBoxService;
import com.r.qqcard.card.view.CardBoxDialog;
import com.r.qqcard.notify.context.NotifyContext;
import com.r.qqcard.notify.handler.Event;
import com.r.qqcard.notify.handler.EventAnn;

/**
 * @author rain
 *
 */
@Controller("controller.cardbox")
public class CardBoxController {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(CardBoxController.class);
    /** 通知 */
    @Resource(name = "springxml.notify")
    private NotifyContext notify;
    /** 卡片箱子业务处理器 */
    @Resource(name = "service.cardbox")
    private CardBoxService cardboxService;

    /** 卡片箱子对话框 */
    private CardBoxDialog cardBoxDialog;

    @EventAnn(Event.box$显示卡片箱子对话框)
    public void showCardBoxDialog() {
        if (this.cardBoxDialog == null) {
            this.cardBoxDialog = new CardBoxDialog(notify, "卡片箱子");
        }
        this.cardBoxDialog.setVisible(true);
        updateCardBox();
    }

    @EventAnn(Event.box$交换卡片)
    public void changeCardBox(CardBoxListItem cardBoxListItem) {
        if (isValid()) {
            CardBox cardBox = this.cardboxService.find(cardBoxListItem.getCardBoxid());
            int destSlot = this.cardboxService.changeCardBox(cardBox);
            if (0 <= destSlot) {
                this.cardBoxDialog.changeCardBox(cardBoxListItem, destSlot);
            } else {
                switch (destSlot) {
                case -1:
                    logger.info("交换卡片失败,网络错误");
                    HAlert.showWarnTips("交换卡片失败,网络错误", this.cardBoxDialog);
                    break;
                case -2:
                    logger.info("交换卡片失败,找不到相应的记录,可能是卡箱已满");
                    HAlert.showWarnTips("交换卡片失败,找不到相应的记录,可能是卡箱已满", this.cardBoxDialog);
                    break;
                case -3:
                    logger.info("交换卡片失败,系统繁忙");
                    HAlert.showWarnTips("交换卡片失败,系统繁忙", this.cardBoxDialog);
                    break;
                default:
                    logger.info("交换卡片失败,未知错误");
                    HAlert.showWarnTips("交换卡片失败,未知错误", this.cardBoxDialog);
                    break;
                }
            }
        }
    }

    @EventAnn(Event.box$一键抽卡)
    public void doRandomAllCard() {
        if (isValid()) {
            Collection<CardBox> cardBoxs = this.cardboxService.randomAllCard();
            if (CollectionUtils.isNotEmpty(cardBoxs)) {
                this.cardBoxDialog.addCardBoxToChange(cardBoxs);
            }
        }
    }

    /** 初始化卡片箱子控件(只有在显示的时候,更新卡箱信息) */
    private void updateCardBox() {
        if (isValid()) {
            Collection<CardBox> cardboxChange = cardboxService.queryAllByChange(); // 变卡箱的卡片
            Collection<CardBox> cardboxStore = cardboxService.queryAllByStore();// 储藏箱的卡片
            this.cardBoxDialog.initCardBox(cardboxChange, cardboxStore); // 初始化卡箱信息
        }
    }

    /** 对话框是否有效 */
    private boolean isValid() {
        return this.cardBoxDialog != null && this.cardBoxDialog.isVisible();
    }
}
