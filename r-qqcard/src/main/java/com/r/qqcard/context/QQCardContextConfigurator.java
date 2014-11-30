package com.r.qqcard.context;

import org.springframework.beans.factory.InitializingBean;

/**
 * QQ辅助程序容器配置器
 * 
 * @author rain
 *
 */
public class QQCardContextConfigurator implements InitializingBean {

    /** 网络请求超时时间,默认10秒 */
    private int httpSocketTimeout = 10 * 1000;
    /** QQ网络应用id */
    private String appid = null;
    /** 程序名字 */
    private String appName = "QQ卡片辅助程序";
    /** 版本号 */
    private String appVersion = "test_0.0.1";

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    /** 返回网络请求超时时间 */
    public int getHttpSocketTimeout() {
        return httpSocketTimeout;
    }

    /** 设置网络请求超时时间 */
    public void setHttpSocketTimeout(int httpSocketTimeout) {
        this.httpSocketTimeout = httpSocketTimeout;
    }

    /** 获取QQ网络应用id */
    public String getAppid() {
        return appid;
    }

    /** 设置QQ网络应用id */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /** 获取程序名字 */
    public String getAppName() {
        return appName;
    }

    /** 设置程序名字 */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /** 获取版本号 */
    public String getAppVersion() {
        return appVersion;
    }

    /** 设置版本号 */
    public void setAppVersion(String version) {
        this.appVersion = version;
    }

}
