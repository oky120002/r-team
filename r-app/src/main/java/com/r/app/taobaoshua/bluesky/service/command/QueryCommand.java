/**
 * 
 */
package com.r.app.taobaoshua.bluesky.service.command;

import java.util.List;

import org.hibernate.Session;

/**
 * @author oky
 * 
 */
public interface QueryCommand<Bean> {
	/** 查询数据 */
	List<Bean> queryCollection(Session session);
}
