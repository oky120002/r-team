/**
 * 
 */
package com.r.qqcard.core.component;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.qqcard.card.domain.bean.CardInfo;

/**
 * @author rain
 *
 */
@Component("component.webaction")
public class WebAction {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(WebAction.class);

    /** 网络请求套接字 */
    @Resource(name = "component.websocket")
    private WebSocket webSocket;

    /**
     * 获取第三版卡片信息
     * 
     * @throws IOException
     *             网络错误时抛出此异常
     */
    public CardInfo getCardInfoV3() {
        return new CardInfo(webSocket.getCardInfoV3());
    }
}
