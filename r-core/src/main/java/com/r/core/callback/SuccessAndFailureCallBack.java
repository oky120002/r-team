/**
 * 
 */
package com.r.core.callback;

/**
 * @author oky
 * 
 */
public interface SuccessAndFailureCallBack {
	/** 成功 */
	void success(String success, Object object);

	/** 失败 */
	void failure(String error, Object object);
}
