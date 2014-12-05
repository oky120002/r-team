/**
 * 
 */
package com.r.qqcard.notify.handler;

/**
 * 事件
 * 
 * @author rain
 *
 */
public enum Event {
    init$全局数据初始化, //
    init$全局数据初始化完成, //
    init$玩家信息初始化, //
    init$玩家信息初始化完成, //
    login$程序启动, //
    login$登陆完成(Boolean.class, String.class, String.class, Boolean.class), // 登陆是否成功,账号,密码,是否保存密码和账号
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
