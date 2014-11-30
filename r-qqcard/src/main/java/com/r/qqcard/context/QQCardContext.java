package com.r.qqcard.context;

import org.springframework.beans.factory.InitializingBean;

import com.r.core.httpsocket.HttpSocket;

/**
 * QQ卡片辅助程序容器
 * 
 * @author rain
 *
 */
public class QQCardContext extends QQCardContextConfigurator implements InitializingBean {
    /** 对自身的引用 */
    private static QQCardContext context = null;

    /** web请求套接字 */
    private HttpSocket httpSocket = null;

    /** 返回容器的唯一引用 */
    public static QQCardContext getContext() {
        return context;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        context = this;
        httpSocket = HttpSocket.newHttpSocket(true, null);
        httpSocket.setTimeout(getHttpSocketTimeout()); // 设置web请求超时时间
        
        
    }

    /** 返回web请求套接字 */
    public HttpSocket getHttpSocket() {
        return this.httpSocket;
    }
}
