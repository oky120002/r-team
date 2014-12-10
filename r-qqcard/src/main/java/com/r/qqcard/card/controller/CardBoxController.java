/**
 * 
 */
package com.r.qqcard.card.controller;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
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

    @EventAnn(Event.box$显示卡片箱子)
    public void showCardBoxDialog() {
        if (this.cardBoxDialog == null) {
            this.cardBoxDialog = new CardBoxDialog(notify, "卡片箱子");
        }
        this.cardBoxDialog.setVisible(true);
        updateCardBox();
    }

    /** 初始化卡片箱子控件(只有在显示的时候,更新卡箱信息) */
    private void updateCardBox() {
        if (this.cardBoxDialog != null && this.cardBoxDialog.isVisible()) {
            Collection<CardBox> cardboxChange = cardboxService.queryAllByChange(); // 变卡箱的卡片
            Collection<CardBox> cardboxStore = cardboxService.queryAllByStore();// 储藏箱的卡片
            
        }
    }
}
