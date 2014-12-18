/**
 * 
 */
package com.r.qqcard.card.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.r.core.desktop.ctrl.impl.dialog.HImageDialog;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel.ShowMode;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.StrUtil;
import com.r.qqcard.card.service.CardImageService;
import com.r.qqcard.notify.handler.Event;
import com.r.qqcard.notify.handler.EventAnn;

/**
 * 卡片图片控制器
 * 
 * @author rain
 *
 */
@Controller("controller.cardimage")
public class CardImageController {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(CardImageController.class);
    /** 卡片图片业务处理器 */
    @Resource(name = "service.cardimage")
    private CardImageService cardimageService;

    /** 图片对话框 */
    private HImageDialog imageDialog;

    @EventAnn(Event.cardimage$显示卡片图片对话框)
    public void showCardImageDialog() {
        if (this.imageDialog == null) {
            this.imageDialog = new HImageDialog("卡片图片");
            this.imageDialog.setSize(120, 170);
        }
        this.imageDialog.setVisible(true);
    }

    @EventAnn(Event.cardimage$显示卡片图片)
    public void showCardImage(Integer cardid) {
        if (cardid != null && isValid()) {
            try {
                this.imageDialog.setImage(cardimageService.getCardImage(cardid.intValue()), ShowMode.拉伸);
            } catch (IOException e) {
                String msg = StrUtil.formart("卡片图片加在失败(卡片id:{})", cardid.toString());
                logger.debug(msg, e);
                logger.info(msg);
            }
        }
    }

    /** 对话框是否有效 */
    private boolean isValid() {
        return (this.imageDialog != null && this.imageDialog.isVisible());
    }
}
