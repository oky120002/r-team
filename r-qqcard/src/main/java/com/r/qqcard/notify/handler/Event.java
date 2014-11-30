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
    程序启动, //
    登陆前, //
    /** 账号,密码,是否保存密码和账号 */
    登陆成功(String.class, String.class, Boolean.class), //
    /** 账号,密码,是否保存密码和账号 */
    登陆失败(String.class, String.class, Boolean.class), //
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
