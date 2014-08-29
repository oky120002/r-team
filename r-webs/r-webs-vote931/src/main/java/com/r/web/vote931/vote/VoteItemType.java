package com.r.web.vote931.vote;

/** 问卷项类型 */
public enum VoteItemType {
	yesno("是非题"), // 是非
	single("单选题"), // 单选
	multiple("多选题"), // 多选
	completion("填空题"), // 填空
	;

	private String cnName;

	/** 获取类型名称 */
	public String getCnName() {
		return cnName;
	}
	
	public String getName(){
	    return name();
	}

	VoteItemType(String cnName) {
		this.cnName = cnName;
	}
}
