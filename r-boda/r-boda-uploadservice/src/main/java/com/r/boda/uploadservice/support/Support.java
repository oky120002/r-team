/**
 * 
 */
package com.r.boda.uploadservice.support;

import java.util.ArrayList;
import java.util.Arrays;
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
	private String message; // 提示信息
	private Boolean success = true; // 数据是否正确返回;true:正确返回|false:有错误信息,没有正确返回
	private T model; // 任意Object
	private List<T> entities = new ArrayList<T>(); // 返回的列表
	private Map<String, Object> params = new HashMap<String, Object>(); // 返回参数
	private List<String> errors = new ArrayList<String>(); // 错误信息

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(Boolean success) {
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

	/**
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * @param errors
	 *            the errors to set
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public void addErrors(String... ts) {
		this.errors.addAll(Arrays.asList(ts));
	}

	@SuppressWarnings("unchecked")
	public void addEntities(T... ts) {
		this.entities.addAll(Arrays.asList(ts));
	}

	public void putParam(String key, Object obj) {
		this.params.put(key, obj);
	}
}
