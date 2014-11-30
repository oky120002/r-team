/**
 * 
 */
package com.r.qqcard.notify.handler;

/**
 * 值类型
 * 
 * @author rain
 *
 */
public enum ValueType {
    昵称(String.class), ;

    private Class<?> clazz;

    /** 返回值的类型 */
    public Class<?> getClazz() {
        return clazz;
    }

    ValueType(Class<?> clazz) {
        this.clazz = clazz;
    }
}
