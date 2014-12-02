package com.r.qqcard.context;

import org.springframework.beans.factory.InitializingBean;

/**
 * QQ辅助程序容器配置器
 * 
 * @author rain
 *
 */
public class QQCardContextConfigurator implements InitializingBean {

    /** QQ网络应用id */
    private String appid = null;
    /** 程序名字 */
    private String appName = "QQ卡片辅助程序";
    /** 版本号 */
    private String appVersion = "test_0.0.1";

    @Override
    public void afterPropertiesSet() throws Exception {
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
