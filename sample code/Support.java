/**
 * 
 */
package com.r.web.arean.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于contraol和jsp通信
 * 
 * @author oky
 * 
 */
public class Support<T> {
    private String tips = ""; // 提示信息(包括成功提示和错误信息提示)
    private boolean success = true; // 数据是否正确返回;true:正确返回|false:有错误信息,没有正确返回
    private T model = null; // 任意Object
    private List<T> entities = new ArrayList<T>(); // 返回的列表
    private Map<String, Object> params = new HashMap<String, Object>(); // 返回参数

    public Support() {
        super();
    }

    /**
     * @return the tips
     */
    public String getTips() {
        return tips;
    }

    /**
     * @param tips
     *            the tips to set
     */
    public void setTips(String tips) {
        this.tips = tips;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success
     *            the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the model
     */
    public T getModel() {
        return model;
    }

    /**
     * @param model
     *            the model to set
     */
    public void setModel(T model) {
        this.model = model;
    }

    /**
     * @return the entities
     */
    public List<T> getEntities() {
        return entities;
    }

    /**
     * @param entities
     *            the entities to set
     */
    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    /**
     * @return the params
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * @param params
     *            the params to set
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void putParam(String key, String value) {
        this.params.put(key, value);
    }

    public void putParam(String key, int value) {
        this.params.put(key, Integer.toString(value));
    }

    public void putParam(String key, long value) {
        this.params.put(key, Long.toString(value));
    }

    public void putParam(String key, boolean value) {
        this.params.put(key, Boolean.toString(value));
    }
}
