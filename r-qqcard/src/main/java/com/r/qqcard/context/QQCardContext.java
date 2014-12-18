package com.r.qqcard.context;

import java.io.File;

import org.springframework.beans.factory.InitializingBean;

import com.r.core.util.SystemPropertyUtil;

/**
 * QQ卡片辅助程序容器
 * 
 * @author rain
 *
 */
public class QQCardContext extends QQCardContextConfigurator implements InitializingBean {
    /** 对自身的引用 */
    private static QQCardContext context = null;

    /** 图片路径 */
    private String cardImagePath;

    /** 返回容器的唯一引用 */
    public static QQCardContext getContext() {
        return context;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        QQCardContext.context = this;
        this.cardImagePath = SystemPropertyUtil.getUserDir() + "/cache/image/card/";

        new File(this.cardImagePath).mkdirs(); // 建立目录
    }

    /** 获取图片路径 */
    public String getCardImagePath() {
        return cardImagePath;
    }

    /** 设置图片路径 */
    public void setCardImagePath(String cardImagePath) {
        this.cardImagePath = cardImagePath;
    }

}
