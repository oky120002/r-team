/**
 * 
 */
package com.r.qqcard.core.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.ImageUtil;
import com.r.core.util.TaskUtil;
import com.r.core.util.XStreamUtil;
import com.r.qqcard.account.service.AccountService;
import com.r.qqcard.account.service.AccountService.AccountEnum;
import com.r.qqcard.card.bean.CardInfo;
import com.r.qqcard.card.model.Card;
import com.r.qqcard.card.model.Theme;
import com.r.qqcard.card.qqhome.QQHome;
import com.r.qqcard.card.qqhome.QQHomeCardChange;
import com.r.qqcard.card.qqhome.impl.QQHomeImpl;
import com.r.qqcard.card.qqhome.other.QQExChangeBox;
import com.r.qqcard.card.qqhome.other.QQOneAllRandom;
import com.r.qqcard.context.QQCardContext;

/**
 * @author rain
 *
 */
@Component("component.webaction")
public class WebAction {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(WebAction.class);
    /** QQ卡片辅助程序容器 */
    @Resource(name = "springxml.context")
    private QQCardContext context;
    /** 网络请求套接字 */
    @Resource(name = "component.websocket")
    private WebSocket webSocket;
    /** 账号业务处理器 */
    @Resource(name = "service.account")
    private AccountService accountService;

    /**
     * 获取QQ卡片对应的卡牌的内容图片
     * 
     * @param card
     *            卡片
     * @return 内容图片
     * @throws IOException
     *             图片保存失败
     */
    public Image getCardMiniImage(Card card) throws IOException {
        File cardFile = new File(context.getCardImagePath() + card.toString() + " mini.png");
        if (!cardFile.exists()) {
            BufferedImage cardImage = webSocket.getCardImage(card.getCardid());
            ImageUtil.saveImageToFile(cardImage, cardFile); // 合并图片
            TaskUtil.sleep(10);
        }
        return ImageUtil.readImageFromFile(cardFile);
    }

    /**
     * 获取QQ卡片对应的卡牌图片
     * 
     * @param card
     *            卡片
     * @return 图片
     * @throws IOException
     *             图片保存失败
     */
    public Image getCardImage(Card card) throws IOException {
        File cardFile = new File(context.getCardImagePath() + card.toString() + ".png");
        if (!cardFile.exists()) {
            Theme theme = card.getTheme();
            // 如果不存在,则从网络读取使用,且保存
            Image cardImage = getCardMiniImage(card);
            BufferedImage themeImage = webSocket.getCardThemeImage(theme.getThemeid());
            ImageUtil.merge(themeImage, cardImage, 10, 20);

            // 在图片上加上卡牌信息
            Graphics graphics = themeImage.getGraphics();
            graphics.setColor(Color.decode(theme.getColor().toString()));

            // 打印卡牌名称
            String str = card.getName();
            int width = graphics.getFontMetrics().stringWidth(str);
            graphics.drawString(str, (themeImage.getWidth() - width) / 2, 15);

            // 打印卡牌星级
            int difficulty = theme.getDifficulty();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < difficulty; i++) {
                sb.append('★');
            }
            width = graphics.getFontMetrics().stringWidth(sb.toString());
            graphics.drawString(sb.toString(), 15, 115);

            // 打印卡牌价格
            str = String.valueOf(card.getPrice());
            width = graphics.getFontMetrics().stringWidth(str);
            graphics.drawString(str, themeImage.getWidth() - width - 13, 138);

            // graphics.setColor(oldColor); // 还原画笔颜色
            // graphics.setFont(font);// 还原字体
            ImageUtil.saveImageToFile(themeImage, cardFile); // 合并图片
            TaskUtil.sleep(10);
        }
        return ImageUtil.readImageFromFile(cardFile);
    }

    /**
     * 获取第三版卡片信息
     * 
     * @throws IOException
     *             网络错误时抛出此异常
     */
    public CardInfo getCardInfoV3() {
        return new CardInfo(this.webSocket.getCardInfoV3());
    }

    /**
     * 获取QQ魔法卡片主要信息(包括但不限于:交换箱信息，卡箱信息，用户信息等)
     */
    public QQHome getQQHome() {
        String username = this.accountService.getValueString(AccountEnum.登录名, null);
        String cardUserMainpage = this.webSocket.getCardUserMainpage(username);

        if (context.isDebug()) {
            try {
                FileUtils.write(new File("./doc/cardUserMainpage.xml"), cardUserMainpage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return XStreamUtil.fromXML(QQHomeImpl.class, cardUserMainpage);
    }

    /**
     * 交换卡片:从换卡箱交换到保险箱
     * 
     * @param changeSlot
     *            换卡箱位置
     * @return 交换结果(大于等于0:换成功后保险箱位置,-1:网络错误,-2:找不到相应的记录,-3:系统繁忙)
     */
    public int doChange2Store(int changeSlot) {
        String username = this.accountService.getValueString(AccountEnum.登录名, null);
        String xml = this.webSocket.doChangeCard(username, changeSlot, 18, 0);
        QQExChangeBox result = XStreamUtil.fromXML(QQExChangeBox.class, xml);

        if (0 <= result.getCode()) {
            return result.getCode(); // 正常返回
        }

        logger.warn("交换卡片:从换卡箱交换到保险箱出现错误(错误编码:{} 错误信息:{})", result.getCode(), result.getMessage());

        // 错误信息
        switch (result.getCode()) {
        case -32001: // 系统繁忙
            return -3;
        case -32002: // 找不到相应的记录
            return -2;
        default:
            return -1; // 网络未知错误
        }
    }

    /**
     * 交换卡片:从保险箱交换到换卡箱
     * 
     * @param storeSlot
     *            保险箱位置
     * @return 交换结果(大于等于0:换成功后保险箱位置,-1:网络错误,-2:找不到相应的记录,-3:系统繁忙)
     */
    public int doStore2Change(int storeSlot) {
        String username = this.accountService.getValueString(AccountEnum.登录名, null);
        String xml = this.webSocket.doChangeCard(username, storeSlot, 100, 1);
        QQExChangeBox result = XStreamUtil.fromXML(QQExChangeBox.class, xml);

        if (0 <= result.getCode()) {
            return result.getCode(); // 正常返回
        }

        logger.warn("交换卡片:从保险箱交换到换卡箱(错误编码:{} 错误信息:{})", result.getCode(), result.getMessage());

        // 错误信息
        switch (result.getCode()) {
        case -32001: // 系统繁忙
            return -3;
        case -32002: // 找不到相应的记录
            return -2;
        default:
            return -1; // 网络未知错误
        }
    }

    /** 一键抽卡 */
    public Collection<QQHomeCardChange> doRandomAllCard() {
        String xml = this.webSocket.doRandomAllCard();
        QQOneAllRandom result = XStreamUtil.fromXML(QQOneAllRandom.class, xml);
        return result.getCards();
    }
}
