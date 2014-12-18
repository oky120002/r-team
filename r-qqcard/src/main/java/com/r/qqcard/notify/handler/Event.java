/**
 * 
 */
package com.r.qqcard.notify.handler;

import java.util.Collection;

/**
 * 事件
 * 
 * @author rain
 *
 */
public enum Event {
    core$全局数据初始化, //
    core$全局数据初始化完成, //
    core$玩家信息初始化, //
    core$玩家信息初始化完成, //
    core$启动自动炼卡, //
    core$同步数据, //
    login$程序启动, //
    login$登陆完成(Boolean.class, String.class, String.class, Boolean.class), // 登陆是否成功,账号,密码,是否保存密码和账号
    box$显示卡片箱子对话框, //
    box$一键抽卡, //
    box$交换卡片(String.class), // CardBox.id
    box$增加卡箱卡片(Collection.class), //
    box$移除卡箱卡片(Collection.class), //
    box$整理卡片, //
    cardimage$显示卡片图片对话框, //
    cardimage$显示卡片图片(Integer.class), // Card.cardid
    ;

    private Class<?>[] clazzes;

    /** 获取事件参数类型 */
    public Class<?>[] getClazzes() {
        return clazzes;
    }

    Event(Class<?>... clazzes) {
        this.clazzes = clazzes;
    }
}
