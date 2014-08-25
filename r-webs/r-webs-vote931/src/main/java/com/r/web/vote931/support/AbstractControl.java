/**
 * 
 */
package com.r.web.vote931.support;

import javax.servlet.http.HttpServletRequest;

/**
 * @author oky
 * 
 */
public abstract class AbstractControl {

    /**
     * 获取本机对外的网络基本地址
     * 
     * @param request
     * @return
     */
    public String getAddr(HttpServletRequest request) {
//        getContextPath : /boda
//        getLocalAddr : 0:0:0:0:0:0:0:1
//        getLocalName : IT-203.bodacredit.local
//        getLocalPort : 8080
//        getProtocol : HTTP/1.1
//        getRemoteAddr : 0:0:0:0:0:0:0:1
//        getRemoteHost : 0:0:0:0:0:0:0:1
//        getRemoteHost : 1099
//        getQueryString : uploadgroup=r-boda-test
//        getRequestedSessionId : DCDCA0785B2EB78E07EC6EB80FBD7998
//        getRequestURI : /boda/upload/uploadpage
//        getScheme : http
//        getServerName : localhost
//        getServerPort : 8080
//        getServletPath : /upload/uploadpage
//        getLocale : zh_CN

        return null;
    }
}
