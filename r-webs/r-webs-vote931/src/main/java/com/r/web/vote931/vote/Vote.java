/**
 * 
 */
package com.r.web.vote931.vote;

import java.util.Iterator;

/**
 * 问卷
 * 
 * @author Administrator
 *
 */
public interface Vote extends Iterable<VoteItem> {

	/** 获得问卷名称 */
	String getName();

	/** 获得问题项数量 */
	int size();

	/** 获得问题项的迭代器,如果没有问题项则返回null */
	@Override
	Iterator<VoteItem> iterator();
}
