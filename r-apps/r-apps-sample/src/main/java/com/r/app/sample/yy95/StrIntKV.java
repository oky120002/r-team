package com.r.app.sample.yy95;

import com.r.core.support.KV;

public class StrIntKV extends KV<String, Integer> {

	private String nickname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/** 创建"键值对" */
	public static StrIntKV accountKV(String key, Integer value, String nickname) {
		StrIntKV kv = new StrIntKV();
		kv.setKey(key);
		kv.setValue(value);
		kv.setNickname(nickname);
		return kv;
	}

	@Override
	public String toString() {
		return getKey();
	}
}
