/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 顶级Dao实现
 * 
 * @author rain
 */
public abstract class AbstractDaoImpl<T> implements AbstractDao<T> {
	/** 日志 */
	protected Logger logger = null;

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	/** 此Dao查询的Model类型 */
	private Class<?> modelClass = null;

	/**
	 * 
	 * 获得当前事务Seesion
	 * 
	 * @return Session
	 * @author rain
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/** 在实现时,要实现一个无参构造方法,同时调用此构造方法 */
	public AbstractDaoImpl(Class<?> modelClass) {
		super();
		if (modelClass != null) {
			this.modelClass = modelClass;
			logger = LoggerFactory.getLogger(this.getClass());
			logger.info("------@Repository--------初始化" + this.getClass().getSimpleName() + "类------------------");
		}
	}

	@Override
	public void create(T model) {
		getSession().save(model);
	}

	@Override
	public void creates(List<T> models) {
		for (T t : models) {
			create(t);
		}
	}

	@Override
	public void update(T model) {
		getSession().update(model);
	}

	@Override
	public void updates(List<T> models) {
		for (T t : models) {
			update(t);
		}
	}

	@Override
	public void save(T model) {
		getSession().saveOrUpdate(model);
	}

	@Override
	public void saves(List<T> models) {
		for (T t : models) {
			save(t);
		}
	}

	@Override
	public void merge(T model) {
		getSession().merge(model);
	}

	@Override
	public void merges(List<T> models) {
		for (T t : models) {
			merge(t);
		}
	}

	@Override
	public void delete(T model) {
		getSession().delete(model);
	}

	@Override
	public void deletes(List<T> models) {
		for (T t : models) {
			delete(t);
		}
	}

	@Override
	public void deleteAll() {
		Query query = getSession().createQuery("delete " + modelClass.getName());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T find(String id) {
		return (T) getSession().get(this.modelClass, id);
	}

	@Override
	public List<T> queryAll() {
		return query(-1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> query(int firstResult, int maxResults, Order... orders) {
		String hql = "from " + this.modelClass.getName();
		if (ArrayUtils.isNotEmpty(orders)) {
			hql += " order";
			for (Order order : orders) {
				hql = " " + hql + order.toString() + ",";
			}
			hql = StringUtils.left(hql, hql.length() - 1);
		}
		Query query = getSession().createQuery(hql.toString());

		if (firstResult > 0) {
			query.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			query.setMaxResults(maxResults);
		}
		return (List<T>) query.list();
	}

	@Override
	public List<T> queryByExample(T model) {
		return queryByExample(model, -1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryByExample(T model, int firstResult, int maxResults, Order... orders) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(model.getClass());
		criteria.add(Example.create(model).ignoreCase());
		if (ArrayUtils.isNotEmpty(orders)) {
			for (Order order : orders) {
				criteria.addOrder(order);
			}
		}
		if (firstResult > 0) {
			criteria.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			criteria.setMaxResults(maxResults);
		}
		return (List<T>) criteria.list();
	}

	@Override
	public List<T> queryByHql(String hql) {
		return queryByHql(hql, null, -1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryByHql(String hql, KeyValue<String, ?>... keyValues) {
		if (ArrayUtils.isEmpty(keyValues)) {
			return queryByHql(hql, null, -1, -1);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (KeyValue<String, ?> kv : keyValues) {
			map.put(kv.key, kv.value);
		}
		return queryByHql(hql, map, -1, -1);
	}

	@Override
	public List<T> queryByHql(String hql, Map<String, Object> pars) {
		return queryByHql(hql, pars, -1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryByHql(String hql, Map<String, Object> pars, int firstResult, int maxResults, Order... orders) {
		if (ArrayUtils.isNotEmpty(orders)) {
			hql += " order";
			for (Order order : orders) {
				hql = " " + hql + order.toString() + ",";
			}
			hql = StringUtils.left(hql, hql.length() - 1);
		}
		Query query = getSession().createQuery(hql);
		if (MapUtils.isNotEmpty(pars)) {
			for (Map.Entry<String, Object> entry : pars.entrySet()) {
				Object value = entry.getValue();
				if (value != null && value.getClass().isArray()) {
					query.setParameterList(entry.getKey(), (Object[]) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		if (firstResult > 0) {
			query.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			query.setMaxResults(maxResults);
		}
		return (List<T>) query.list();
	}

	@Override
	public List<T> queryBySql(String sql) {
		return queryBySql(sql, null, -1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryBySql(String sql, KeyValue<String, ?>... keyValues) {
		if (ArrayUtils.isEmpty(keyValues)) {
			return queryBySql(sql, null, -1, -1);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (KeyValue<String, ?> kv : keyValues) {
			map.put(kv.key, kv.value);
		}
		return queryBySql(sql, map, -1, -1);
	}

	@Override
	public List<T> queryBySql(String sql, Map<String, Object> pars) {
		return queryBySql(sql, pars, -1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryBySql(String sql, Map<String, Object> pars, int firstResult, int maxResults, Order... orders) {
		if (ArrayUtils.isNotEmpty(orders)) {
			sql += " order";
			for (Order order : orders) {
				sql = " " + sql + order.toString() + ",";
			}
			sql = StringUtils.left(sql, sql.length() - 1);
		}

		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		if (MapUtils.isNotEmpty(pars)) {
			for (Map.Entry<String, Object> entry : pars.entrySet()) {
				Object value = entry.getValue();
				if (value != null && value.getClass().isArray()) {
					sqlQuery.setParameterList(entry.getKey(), (Object[]) entry.getValue());
				} else {
					sqlQuery.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		if (firstResult > 0) {
			sqlQuery.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			sqlQuery.setMaxResults(maxResults);
		}
		return (List<T>) sqlQuery.list();
	}
}
