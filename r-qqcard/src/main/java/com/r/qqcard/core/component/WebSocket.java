/**
 * 
 */
package com.r.qqcard.core.component;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpPost;
import com.r.core.tool.QQTool;

/**
 * @author rain
 *
 */
@Component("component.websocket")
public class WebSocket {
    /** 返回字符串编码 */
    private static final String ENCODING = "UTF-8";
    /** 执行操作时,需要的参数(从cookie中的skey中通过算法计算获得 */
    private String gtk = null;

    /** 网络请求套接字 */
    @Resource(name = "springxml.httpsocket")
    private HttpSocket httpSocket;

    /**
     * 获取第三版卡片信息
     * 
     * @return javascript格式的信息
     * @throws IOException
     *             网络错误时抛出此异常
     */
    public String getCardInfoV3() {
        return httpSocket.send("http://appimg2.qq.com/card/mk/card_info_v3.js").bodyToString(ENCODING);
    }

    /**
     * 获取QQ魔法卡片主要信息<br />
     * 包括但不限于:交换箱信息，卡箱信息，用户信息等
     * 
     * @param uid
     *            QQ账号
     * @return XML格式的信息
     * @throws IOException
     *             网络错误时抛出此异常
     */
    public String getCardUserMainpage(String uid) {
        HttpPost post = new HttpPost();
        post.add("code", "");
        post.add("uid", uid);

        return httpSocket.send("http://card.show.qq.com/cgi-bin/card_user_mainpage?g_tk=" + getGTK(), post).bodyToString(ENCODING);
    }

    /**
     * 一键抽卡
     * 
     * @return XML格式的信息
     * @throws IOException
     *             网络错误时抛出此异常
     */
    public String doRandomAllCard() {
        HttpPost post = new HttpPost();
        post.add("type", "2");
        return httpSocket.send("http://card.show.qq.com/cgi-bin/card_user_random_get?g_tk=" + getGTK(), post).bodyToString(ENCODING);
    }

    /**
     * 贩卖一张卡片<br />
     * 卡片只有在换卡箱中才能被贩卖
     * 
     * @param uin
     *            QQ账号
     * @param cardid
     *            要贩卖的卡片id
     * @param slot
     *            该卡片在换卡箱中的位置
     * @return XML格式的信息
     * @throws IOException
     *             网络错误时抛出此异常
     */
    public String doSellOneCard(String uin, int cardid, int slot) {
        HttpPost post = new HttpPost();
        post.add("cardid", Integer.toString(cardid));
        post.add("type", "0");
        post.add("uin", uin);
        post.add("slot_no", Integer.toString(slot));

        return httpSocket.send("http://card.show.qq.com/cgi-bin/card_market_npc_sell?g_tk=" + getGTK(), post).bodyToString(ENCODING);
    }

    /**
     * 购卡
     * 
     * @param cardid
     *            购买的卡片id
     * @param themeid
     *            购买的卡片主题id
     * @return XML格式的信息
     * @throws IOException
     *             网络错误时抛出此异常
     */
    public String doBuyOneCard(int cardid, int themeid) {
        HttpPost post = new HttpPost();
        post.add("card_id", Integer.toString(cardid));
        post.add("theme_id", Integer.toString(themeid));

        return httpSocket.send("http://card.show.qq.com/cgi-bin/card_market_npc_buy?g_tk=" + getGTK(), post).bodyToString(ENCODING);
    }

    /**
     * 把换卡箱中的卡片存储到保险箱中
     * 
     * @param uin
     *            账号
     * @param srcSlot
     *            源卡片所在位置
     * @param destSlot
     *            目标卡片所在位置
     * @param type
     *            换卡方向(0:换卡箱卡片存储到保险箱,1:把保险箱中的卡片存储到换卡箱)
     * @return XML格式的信息
     * @throws IOException
     *             网络错误时抛出此异常
     */
    public String doChangeCard(String uin, int srcSlot, int destSlot, int type) {
        HttpPost post = new HttpPost();
        post.add("src", Integer.toString(srcSlot));
        post.add("dest", Integer.toString(destSlot));
        post.add("type", Integer.toString(type));
        post.add("uin", uin);

        return httpSocket.send("http://card.show.qq.com/cgi-bin/card_user_storage_exchange?g_tk=" + getGTK(), post).bodyToString(ENCODING);
    }

    // /**
    // * 取卡<br />
    // *
    // * @param uin
    // * 账号
    // * @param card
    // * 炼卡箱中的卡片
    // * @return 取卡成功后的xml信息
    // * @throws IOException
    // * 网络错误时抛出此异常
    // */
    // public String refinedCard(String uin, QQHomeCardStove card) throws
    // IOException {
    // if (card == null) {
    // return null;
    // }
    // logger.info("[{}]取卡 : {}", QQCardGameMain.GAME_NAME, card);
    // Integer opuin = card.getOpuin();
    // Integer opuin2 = card.getOpuin2();
    // HttpSocket httpSocket = QQCardGameMain.getInstance().getHttpSocket();
    // String url = null;
    // ResponseHeader responseHeader = null;
    // StringBuilder post = new StringBuilder();
    // post.append("ver=1");
    // post.append("&code=");
    // post.append("&slottype=1");
    // post.append("&uin=").append(uin);
    // post.append("&slotid=").append(card.getSlot());
    // url = "http://card.show.qq.com/cgi-bin/card_stove_stealcard_get?g_tk=" +
    // gtk;
    // if ((opuin != null && opuin.intValue() > 0)) { // 取 "偷卡"
    // post.append("opuin=").append(opuin.intValue());
    // } else if ((opuin2 != null && opuin2.intValue() > 0)) { // 取"偷卡2"
    // // XXX 这个opuin2不能确定是不是这个参数
    // post.append("opuin2=").append(opuin2.intValue());
    // } else { // 取自己的卡
    // url = "http://card.show.qq.com/cgi-bin/card_stove_refinedcard_get?g_tk="
    // + gtk;
    // }
    // responseHeader = httpSocket.send(url, post.toString(), null);
    // return responseHeader.bodyToString("utf8");
    // }

    /** 获取gtk */
    private String getGTK() {
        if (this.gtk == null) {
            this.gtk = Integer.toString(QQTool.getGTK(httpSocket.getCookie("skey").getValue()));
        }
        return this.gtk;
    }
}
