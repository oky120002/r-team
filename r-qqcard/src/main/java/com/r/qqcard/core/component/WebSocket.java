/**
 * 
 */
package com.r.qqcard.core.component;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpPost;
import com.r.core.httpsocket.context.ResponseHeader;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.tool.QQTool;

/**
 * @author rain
 *
 */
@Component("component.websocket")
public class WebSocket {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);
    /** 执行操作时,需要的参数(从cookie中的skey中通过算法计算获得 */
    private String gtk = null;

    /** 网络请求套接字 */
    @Resource(name = "springxml.httpsocket")
    private HttpSocket httpSocket;

    /**
     * 获取第三版卡片信息
     * 
     * @return javascript格式的信息
     */
    public String getCardInfoV3() {
        logger.debug("获取第三版卡片信息:card_info_v3.js");
        return httpSocket.send("http://appimg2.qq.com/card/mk/card_info_v3.js").bodyToString("utf8");
    }

    /**
     * 获取QQ魔法卡片主要信息<br />
     * 包括但不限于:交换箱信息，卡箱信息，用户信息等
     * 
     * @param username
     *            QQ账号
     * @return XML格式的信息
     */
    public String getCardUserMainpage(String username) {
        logger.info("获取主要信息(交换箱信息，卡箱信息，用户信息......)");
        HttpPost post = new HttpPost();
        post.add("code", "");
        post.add("uid", username);

        ResponseHeader send = httpSocket.send("http://card.show.qq.com/cgi-bin/card_user_mainpage?g_tk=" + getGTK(), post);
        
//        FileUtils.write(new File(), data);
        
        return send.bodyToString();
    }

    /** 获取gtk */
    private String getGTK() {
        if (this.gtk == null) {
            this.gtk = Integer.toString(QQTool.getGTK(httpSocket.getCookie("skey").getValue()));
        }
        return this.gtk;
    }
}
