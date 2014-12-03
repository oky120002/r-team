/**
 * 
 */
package com.r.web.support.abs;

import javax.servlet.http.HttpServletRequest;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * <b>HttpServletRequest部分get方法说明</b><br />
 * HttpServletRequest.getContextPath        : /boda <br />
 * HttpServletRequest.getLocalAddr          : 0:0:0:0:0:0:0:1 <br />
 * HttpServletRequest.getLocalName          : IT-203.bodacredit.local <br />
 * HttpServletRequest.getLocalPort          : 8080 <br />
 * HttpServletRequest.getProtocol           : HTTP/1.1 <br />
 * HttpServletRequest.getRemoteAddr         : 0:0:0:0:0:0:0:1 <br />
 * HttpServletRequest.getRemoteHost         : 0:0:0:0:0:0:0:1 <br />
 * HttpServletRequest.getRemoteHost         : 1099 <br />
 * HttpServletRequest.getQueryString        : uploadgroup=r-boda-test <br />
 * HttpServletRequest.getRequestedSessionId : DCDCA0785B2EB78E07EC6EB80FBD7998 <br />
 * HttpServletRequest.getRequestURI         : /boda/upload/uploadpage <br />
 * HttpServletRequest.getScheme             : http <br />
 * HttpServletRequest.getServerName         : localhost <br />
 * HttpServletRequest.getServerPort         : 8080 <br />
 * HttpServletRequest.getServletPath        : /upload/uploadpage <br />
 * HttpServletRequest.getLocale             : zh_CN <br />
 * 
 * @author oky
 * 
 */
public abstract class AbstractControl {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    public AbstractControl() {
        super();
        logger.debug("Instance " + getClass().getSimpleName() + "............................");
    }

    /**
     * 获取本机对外的网络基本地址
     * 
     * @param request
     * @return
     */
    public String getAddr(HttpServletRequest request) {

        return null;
    }
}
