package com.r.core.httpsocket;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

import com.r.core.httpsocket.context.Cookie;
import com.r.core.httpsocket.context.HttpPost;
import com.r.core.httpsocket.context.ResponseHeader;

public class HttpSocketTest {

    // @Test
    public void test() {
        HttpSocket s = HttpSocket.newHttpSocket(true, null);
        ResponseHeader send = s.send("http://192.168.0.239:81/zentao/user-login.html");
        System.out.println(send.bodyToString());

        HttpPost post = new HttpPost("UTF-8");
        post.add("account", "rain");
        post.add("keepLogin[]", "on");
        post.add("password", "abcd1234");
        post.add("referer", "http://192.168.0.239:81/zentao/testtask-view-35.html");
        ResponseHeader send2 = s.send("http://192.168.0.239:81/zentao/user-login.html", post);
        System.out.println(send2.bodyToString());

        for (Entry<String, Cookie> cookie : send2.getCookies().entrySet()) {
            System.out.println(cookie.toString());
        }
    }

    @Test
    public void convertCode() throws UnsupportedEncodingException {
        System.out.println("URLDecoder.decode  GBK              " + URLDecoder.decode("%B2%E2%CA%D4", "GBK"));
        System.out.println("URLDecoder.decode  GBK              " + URLDecoder.decode("B5", "GBK"));
        System.out.println("URLEncoder.encode  GBK              " + URLEncoder.encode("测试", "GBK"));
        System.out.println("URLEncoder.encode  UTF-8            " + URLEncoder.encode("测试", "UTF-8"));
        System.out.println("StringEscapeUtils.unescapeHtml4     " + StringEscapeUtils.unescapeHtml4("&quot;bread&quot; &amp; &quot;butter&quot;."));
        System.out.println("StringEscapeUtils.escapeHtml4       " + StringEscapeUtils.escapeHtml4("\"bread\" & \"butter\"."));
        System.out.println("StringEscapeUtils.escapeHtml4       " + StringEscapeUtils.unescapeXml("\u51af\u9752\u677e"));
        System.out.println(StringEscapeUtils.unescapeHtml4("\u5929"));
        System.out.println(new String("\u5929"));
    }

}
