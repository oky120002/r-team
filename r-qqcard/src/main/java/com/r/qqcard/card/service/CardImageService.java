/**
 * 
 */
package com.r.qqcard.card.service;

import java.awt.Image;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.r.qqcard.core.component.WebAction;
import com.r.qqcard.core.support.AbstractService;

/**
 * 卡片图片业务处理器
 * 
 * @author rain
 *
 */
@Service("service.cardimage")
public class CardImageService extends AbstractService {
    /** 网络行为 */
    @Resource(name = "component.webaction")
    private WebAction action;
    /** 卡片信息处理器 */
    @Resource(name = "service.cardinfo")
    private CardInfoService cardInfoService;

    /**
     * 获取卡片图片
     * 
     * @throws IOException
     *             图片保存失败
     */
    public Image getCardImage(int cardid) throws IOException {
        return action.getCardImage(cardInfoService.findCardByCardId(cardid));
    }

}
