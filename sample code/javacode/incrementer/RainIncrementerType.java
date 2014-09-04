/**
 * 
 */
package com.r.web.vote931.core.sequence;

/**
 * 自增长类型
 * 
 * @author rain
 *
 */
public enum RainIncrementerType {
    /**默认的全局类型*/
    DefaultGlobal("global"), //
    ;
    
    /**类型*/
    private String type;
    
    public String getType() {
        return type;
    }
    
    RainIncrementerType(String type) {
        this.type = type;
    }
}
