/**
 * 
 */
package com.r.qqcard.core.component;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.r.core.httpsocket.HttpSocket;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author rain
 *
 */
@Component("component.websocket")
public class WebSocket {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);

    /** 网络请求套接字 */
    @Resource(name = "springxml.httpsocket")
    private HttpSocket httpSocket;

    /**
     * 获取第三版卡片信息
     * 
     * @throws IOException
     *             网络错误时抛出此异常
     */
    public String getCardInfoV3() {
        logger.debug("获取第三版卡片信息:card_info_v3.js");
        return httpSocket.send("http://appimg2.qq.com/card/mk/card_info_v3.js").bodyToString("utf8");
    }
}
