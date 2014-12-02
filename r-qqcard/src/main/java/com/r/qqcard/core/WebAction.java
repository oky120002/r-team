/**
 * 
 */
package com.r.qqcard.core;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.r.core.httpsocket.HttpSocket;

/**
 * @author rain
 *
 */
@Component("component.webaction")
public class WebAction {
    /** 网络请求套接字 */
    @Resource(name = "springxml.httpsocket")
    private HttpSocket httpSocket;
    

}
