package com.r.web.vote931.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;

import com.r.core.util.AssertUtil;

/**
 * 顶级Dao实现
 * 
 * @author rain
 */
public abstract class AbstractDaoImpl<T> implements AbstractDao<T> {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    
    /** 此Dao查询的Model类型 */
    private Class<?> modelClass = null;
    
    /** 在实现时,要实现一个无参构造方法,同时调用此构造方法 */
    public AbstractDaoImpl(Class<?> modelClass) {
        super();
        if (modelClass != null) {
            this.modelClass = modelClass;
        }
    }
    
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
    
    @Override
    public final void create(T model) {
        AssertUtil.isNotNull("创建实体时，实体不能为空。", model);
        getSession().save(model);
    }
    
    @Override
    public final void creates(List<T> models) {
        AssertUtil.isNotEmpty("创建实体时，实体集合不能为空。", models);
        for (T t : models) {
            if (t != null) {
                create(t);
            }
        }
    }
    
    @SafeVarargs
    @Override
    public final void creates(T... models) {
        AssertUtil.isNotEmpty("创建实体时，实体集合不能为空。", models);
        for (T t : models) {
            if (t != null) {
                create(t);
            }
        }
    }
    
    @Override
    public final void update(T model) {
        AssertUtil.isNotNull("更新实体时，实体不能为空。", model);
        getSession().update(model);
    }
    
    @Override
    public final void updates(List<T> models) {
        AssertUtil.isNotEmpty("更新实体时，实体集合不能为空。", models);
        for (T t : models) {
            if (t != null) {
                update(t);
            }
        }
    }
    
    @Override
    @SafeVarargs
    public final void updates(T... models) {
        AssertUtil.isNotEmpty("更新实体时，实体集合不能为空。", models);
        for (T t : models) {
            if (t != null) {
                update(t);
            }
        }
    }
    
    @Override
    public final void save(T model) {
        AssertUtil.isNotNull("保存实体时，实体不能为空。", model);
        getSession().saveOrUpdate(model);
    }
    
    @Override
    public final void saves(List<T> models) {
        AssertUtil.isNotEmpty("保存实体时，实体集合不能为空。", models);
        for (T t : models) {
            if (t != null) {
                save(t);
            }
        }
    }
    
    @Override
    @SafeVarargs
    public final void saves(T... models) {
        AssertUtil.isNotEmpty("保存实体时，实体集合不能为空。", models);
        for (T t : models) {
            if (t != null) {
                save(t);
            }
        }
    }
    
    @Override
    public final void merge(T model) {
        AssertUtil.isNotNull("合并实体时，实体不能为空。", model);
        getSession().merge(model);
    }
    
    @Override
    public final void merges(List<T> models) {
        AssertUtil.isNotEmpty("合并实体时，实体集合不能为空。", models);
        for (T t : models) {
            if (t != null) {
                merge(t);
            }
        }
    }
    
    @Override
    @SafeVarargs
    public final void merges(T... models) {
        AssertUtil.isNotEmpty("合并实体时，实体集合不能为空。", models);
        for (T t : models) {
            if (t != null) {
                merge(t);
            }
        }
    }
    
    @Override
    public final void delete(T model) {
        AssertUtil.isNotNull("删除实体时，实体不能为空。", model);
        getSession().delete(model);
    }
    
    @Override
    public final void deletes(List<T> models) {
        AssertUtil.isNotEmpty("删除实体时，实体集合不能为空。", models);
        for (T t : models) {
            if (t != null) {
                delete(t);
            }
        }
    }
    
    @SafeVarargs
    @Override
    public final void deletes(T... models) {
        AssertUtil.isNotEmpty("删除实体时，实体集合不能为空。", models);
        for (T t : models) {
            if (t != null) {
                delete(t);
            }
        }
    }
    
    @Override
    public final int deleteAll() {
        return getSession().createQuery("delete " + modelClass.getName()).executeUpdate();
    }
    
    @Override
    public int updateOrDeleteByHql(CharSequence hql, Map<String, Object> pars) {
        AssertUtil.isNotBlank("根据Hql语句查询时，Hql语句不能为空。", hql);
        Query createQuery = getSession().createQuery(hql.toString());
        if (MapUtils.isNotEmpty(pars)) {
            createQuery.setProperties(pars);
        }
        return createQuery.executeUpdate();
    }
    
    @Override
    public int updateOrDeleteBySql(CharSequence sql, Map<String, Object> pars) {
        AssertUtil.isNotBlank("根据Hql语句查询时，Hql语句不能为空。", sql);
        SQLQuery createSQLQuery = getSession().createSQLQuery(sql.toString());
        if (MapUtils.isNotEmpty(pars)) {
            createSQLQuery.setProperties(pars);
        }
        return createSQLQuery.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public final T find(CharSequence id) {
        AssertUtil.isNotBlank("根据实体id查询实体时，实体id不能为空。", id);
        return (T) getSession().get(this.modelClass, id.toString());
    }
    
    @Override
    public long queryAllSize() {
        StringBuilder hql = new StringBuilder();
        hql.append("select count(t) from ").append(this.modelClass.getName()).append(" t ");
        Query query = getSession().createQuery(hql.toString());
        return ((Long) query.uniqueResult()).longValue();
    }
    
    @Override
    public final List<T> queryAll() {
        return query(-1, -1);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public final List<T> query(int firstResult, int maxResults, Order... orders) {
        StringBuilder hql = new StringBuilder();
        hql.append("from ").append(this.modelClass.getName());
        if (ArrayUtils.isNotEmpty(orders)) {
            hql.append(" order");
            for (Order order : orders) {
                if (order != null) {
                    hql.append(' ').append(hql).append(order.toString()).append(",");
                }
            }
            hql.deleteCharAt(hql.length() - 1);
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
    public final List<T> queryByExample(T model) {
        return queryByExample(model, -1, -1);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public final List<T> queryByExample(T model, int firstResult, int maxResults, Order... orders) {
        AssertUtil.isNotNull("根据实体属性查询时，实体不能为空。", model);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(model.getClass());
        criteria.add(Example.create(model).ignoreCase());
        if (ArrayUtils.isNotEmpty(orders)) {
            for (Order order : orders) {
                if (order != null) {
                    criteria.addOrder(order);
                }
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
    public final List<T> queryByHql(CharSequence hql) {
        AssertUtil.isNotBlank("根据Hql语句查询时，Hql语句不能为空。", hql);
        return queryByHql(hql, null, -1, -1);
    }
    
    @SafeVarargs
    @Override
    public final List<T> queryByHql(CharSequence hql, KekValue<String, ?>... keyValues) {
        if (ArrayUtils.isEmpty(keyValues)) {
            return queryByHql(hql, null, -1, -1);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        for (KekValue<String, ?> kv : keyValues) {
            map.put(kv.key, kv.value);
        }
        return queryByHql(hql, map, -1, -1);
    }
    
    @Override
    public final List<T> queryByHql(CharSequence hql, Map<String, Object> pars) {
        return queryByHql(hql, pars, -1, -1);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public final List<T> queryByHql(CharSequence hql, Map<String, Object> pars, int firstResult, int maxResults, Order... orders) {
        AssertUtil.isNotBlank("根据Hql语句查询时，Hql语句不能为空。", hql);
        StringBuilder sb = new StringBuilder();
        sb.append(hql);
        if (ArrayUtils.isNotEmpty(orders)) {
            sb.append(" order");
            for (Order order : orders) {
                if (order != null) {
                    sb.append(' ').append(sb).append(order.toString()).append(",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        Query query = getSession().createQuery(sb.toString());
        if (MapUtils.isNotEmpty(pars)) {
            query.setProperties(pars);
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
    public final List<T> queryBySql(CharSequence sql) {
        return queryBySql(sql, null, -1, -1);
    }
    
    @SafeVarargs
    @Override
    public final List<T> queryBySql(CharSequence sql, KekValue<String, ?>... keyValues) {
        if (ArrayUtils.isEmpty(keyValues)) {
            return queryBySql(sql, null, -1, -1);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        for (KekValue<String, ?> kv : keyValues) {
            map.put(kv.key, kv.value);
        }
        return queryBySql(sql, map, -1, -1);
    }
    
    @Override
    public final List<T> queryBySql(CharSequence sql, Map<String, Object> pars) {
        return queryBySql(sql, pars, -1, -1);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public final List<T> queryBySql(CharSequence sql, Map<String, Object> pars, int firstResult, int maxResults, Order... orders) {
        AssertUtil.isNotBlank("根据Sql语句查询时，Sql语句不能为空。", sql);
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        if (ArrayUtils.isNotEmpty(orders)) {
            sb.append(" order");
            for (Order order : orders) {
                if (order != null) {
                    sb.append(' ').append(sb).append(order.toString()).append(",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        
        SQLQuery sqlQuery = getSession().createSQLQuery(sb.toString());
        if (MapUtils.isNotEmpty(pars)) {
            sqlQuery.setProperties(pars);
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
