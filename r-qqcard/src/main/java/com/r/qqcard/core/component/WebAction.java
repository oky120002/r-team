/**
 * 
 */
package com.r.qqcard.core.component;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.r.core.util.XStreamUtil;
import com.r.qqcard.account.service.AccountService;
import com.r.qqcard.account.service.AccountService.AccountEnum;
import com.r.qqcard.card.domain.bean.CardInfo;
import com.r.qqcard.card.qqhome.QQHome;
import com.r.qqcard.card.qqhome.impl.QQHomeImpl;

/**
 * @author rain
 *
 */
@Component("component.webaction")
public class WebAction {
    /** 网络请求套接字 */
    @Resource(name = "component.websocket")
    private WebSocket webSocket;
    /** 账号业务处理器 */
    @Resource(name = "service.account")
    private AccountService accountService;

    /**
     * 获取第三版卡片信息
     * 
     * @throws IOException
     *             网络错误时抛出此异常
     */
    public CardInfo getCardInfoV3() {
        return new CardInfo(webSocket.getCardInfoV3());
    }

    /**
     * 获取QQ魔法卡片主要信息(包括但不限于:交换箱信息，卡箱信息，用户信息等)
     */
    public QQHome getQQHome() {
        String username = accountService.getValueString(AccountEnum.登录名, null);
        String cardUserMainpage = webSocket.getCardUserMainpage(username);
        return XStreamUtil.fromXML(QQHomeImpl.class, cardUserMainpage);
    }

}
