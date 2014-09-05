package com.r.core.httpsocket;

import java.util.Map;

import org.junit.Test;

import com.r.core.httpsocket.context.HttpPost;
import com.r.core.httpsocket.context.ResponseHeader;

public class HttpSocketTest {

    @Test
    public void test() {
        HttpSocket s = HttpSocket.newHttpSocket(true, null);
        ResponseHeader send = s.send("http://192.168.0.239:81/zentao/user-login.html");
        System.out.println(send.bodyToString());
        
        HttpPost post = new HttpPost("UTF-8");
        post.add("account", "rain");
        post.add("keepLogin[]","on");
        post.add("password","abcd1234");
        post.add("referer", "http://192.168.0.239:81/zentao/testtask-view-35.html");
        ResponseHeader send2 = s.send("http://192.168.0.239:81/zentao/user-login.html", post);
        System.out.println(send2.bodyToString());
        
        for (Map.Entry cookie : send2.getCookies().entrySet()) {
            System.out.println(cookie.toString());
        }
    }

}
