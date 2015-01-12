package com.r.web.support.abs;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.r.web.support.bean.KeyValue;

/**
 * 
 * 顶级Dao接口
 * 
 * @author rain
 */
public interface AbstractDao<T> {

    /**
     * 
     * 获得当前事务Seesion
     * 
     * @return Session
     * @author rain
     */
    Session getSession();

    /**
     * 
     * 新增实体
     * 
     * @param <T>
     *            实体类型
     * @param model
     *            实体
     * 
     * @author rain
     */
    void create(T model);

    /**
     * 
     * 新增实体集
     * 
     * @param <T>
     *            实体类型
     * @param models
     *            实体集
     * 
     * @author rain
     */
    void creates(Collection<T> models);

    /**
     * 
     * 新增实体集
     * 
     * @param <T>
     *            实体类型
     * @param models
     *            实体集
     * 
     * @author rain
     */
    void creates(T... models);

    /**
     * 
     * 修改实体
     * 
     * @param <T>
     *            实体类型
     * @param model
     *            实体
     * 
     * @author rain
     */
    void update(T model);

    /**
     * 
     * 修改实体集
     * 
     * @param <T>
     *            实体类型
     * @param models
     *            实体集
     * 
     * @author rain
     */
    void updates(Collection<T> models);

    /**
     * 
     * 修改实体集
     * 
     * @param <T>
     *            实体类型
     * @param models
     *            实体集
     * 
     * @author rain
     */
    void updates(T... models);

    /**
     * 根据Hql语句更新或者删除实体
     * 
     * @param hql
     *            Hql语句
     * @param pars
     *            条件参数
     * @return 成功实体条数
     */
    int updateOrDeleteByHql(CharSequence hql, Map<String, Object> pars);

    /**
     * 根据Sql语句更新或者删除实体
     * 
     * @param hql
     *            Hql语句
     * @param pars
     *            条件参数
     * @return 成功实体条数
     */
    int updateOrDeleteBySql(CharSequence sql, Map<String, Object> pars);

    /**
     * 
     * 保存实体
     * 
     * @param <T>
     *            实体类型
     * @param model
     *            实体
     * 
     * @author rain
     */
    void save(T model);

    /**
     * 
     * 保存实体集
     * 
     * @param <T>
     *            实体类型
     * @param models
     *            实体集
     * 
     * @author rain
     */
    void saves(Collection<T> models);

    /**
     * 
     * 保存实体集
     * 
     * @param <T>
     *            实体类型
     * @param models
     *            实体集
     * 
     * @author rain
     */
    void saves(T... models);

    /**
     * 合并对象后执行saveOrUpdate()方法<br />
     * <b>如果session中没有缓存数据或者有一个缓存数据,则直接saveOrUpdate()</b><br />
     * <b>如果session中有超过一个的缓存数据,则先以最后一个为准进行数据合并,然后把合并后的数据saveOrUpdate()到数据库</b>
     * 
     * @param model
     *            [参数说明]
     * 
     * @author rain
     */
    void merge(T model);

    /**
     * 合并对象集后执行saveOrUpdate()方法<br />
     * <b>如果session中没有缓存数据或者有一个缓存数据,则直接saveOrUpdate()</b><br />
     * <b>如果session中有超过一个的缓存数据,则先以最后一个为准进行数据合并,然后把合并后的数据saveOrUpdate()到数据库</b>
     * 
     * @param models
     *            实体集
     * 
     * @author rain
     */
    void merges(Collection<T> models);

    /**
     * 合并对象集后执行saveOrUpdate()方法<br />
     * <b>如果session中没有缓存数据或者有一个缓存数据,则直接saveOrUpdate()</b><br />
     * <b>如果session中有超过一个的缓存数据,则先以最后一个为准进行数据合并,然后把合并后的数据saveOrUpdate()到数据库</b>
     * 
     * @param models
     *            实体集
     * 
     * @author rain
     */
    void merges(T... models);

    /**
     * 
     * 删除实体
     * 
     * @param <T>
     *            实体类型
     * @param model
     *            实体
     * 
     * @author rain
     */
    void delete(T model);

    /**
     * 
     * 删除实体集
     * 
     * @param <T>
     *            实体类型
     * @param models
     *            实体集
     * 
     * @author rain
     */
    void deletes(Collection<T> models);

    /**
     * 
     * 删除实体集
     * 
     * @param <T>
     *            实体类型
     * @param models
     *            实体集
     * 
     * @author rain
     */
    void deletes(T... models);

    /**
     * 删除全部实体集
     * 
     * @author rain
     * @return integer 删除实体数量
     */
    int deleteAll();

    /**
     * 
     * 查找单个实体
     * 
     * @param <T>
     *            实体类型
     * @param id
     *            唯一键
     * 
     * @return T 查询到的实体
     * @author rain
     */
    T find(CharSequence id);

    /**
     * 
     * 查询全部实体的条数
     * 
     * @return int 实体条数
     * @author rain
     */
    long queryAllSize();

    /**
     * 
     * 查询全部实体
     * 
     * @param <T>
     *            实体类型
     * @param orders
     *            排序
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> queryAll(Order... orders);

    /**
     * 
     * 查询实体
     * 
     * @param <T>
     *            实体类型
     * @param firstResult
     *            起始结果条数,-1则不设置起始条数
     * @param maxResults
     *            最大结果条数,-1则不设置最大条数
     * @param orders
     *            排序
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> query(int firstResult, int maxResults, Order... orders);

    /**
     * 
     * 根据实体查询实体<br />
     * 实例里面不为{@code null}的所有属性全部称为查询条件,集合类型数据不参与查询,实体类型必须拥有ID否则按照null操作<br />
     * <b style="color:red;">如果实体中有@ManyToMany属性,则查询结果不可预知</b>
     * 
     * @param <T>
     *            实体类型
     * @param models
     *            实体
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> queryByExample(T model);

    /**
     * 
     * 根据实体查询实体<br />
     * 实例里面不为{@code null}的所有属性全部称为查询条件,集合类型数据不参与查询,实体类型必须拥有ID否则按照null操作<br />
     * <b style="color:red;">如果实体中有@ManyToMany属性,则查询结果不可预知</b>
     * 
     * @param <T>
     *            实体类型
     * @param models
     *            实体
     * @param firstResult
     *            起始结果条数,-1则不设置起始条数
     * @param maxResults
     *            最大结果条数,-1则不设置最大条数
     * @param orders
     *            排序
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> queryByExample(T model, int firstResult, int maxResults, Order... orders);

    /**
     * 
     * 根据hql语句查询实体
     * 
     * @param <T>
     *            实体类型
     * @param hql
     *            hql语句
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> queryByHql(CharSequence hql);

    /**
     * 
     * 根据hql语句查询实体
     * 
     * @param <T>
     *            实体类型
     * @param hql
     *            hql语句
     * @param keyValue
     *            参数
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> queryByHql(CharSequence hql, KeyValue<String, ?>... keyValues);

    /**
     * 
     * 根据hql语句查询实体
     * 
     * @param <T>
     *            实体类型
     * @param hql
     *            hql语句
     * @param pars
     *            参数
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> queryByHql(CharSequence hql, Map<String, Object> pars);

    /**
     * 
     * 根据hql语句查询实体
     * 
     * @param <T>
     *            实体类型
     * @param hql
     *            hql语句
     * @param pars
     *            参数
     * @param firstResult
     *            起始结果条数,-1则不设置起始条数
     * @param maxResults
     *            最大结果条数,-1则不设置最大条数
     * @param orders
     *            排序
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> queryByHql(CharSequence hql, Map<String, Object> pars, int firstResult, int maxResults, Order... orders);

    /**
     * 
     * 根据sql语句查询实体
     * 
     * @param <T>
     *            实体类型
     * @param sql
     *            sql语句
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> queryBySql(CharSequence sql);

    /**
     * 
     * 根据sql语句查询实体
     * 
     * @param <T>
     *            实体类型
     * @param sql
     *            sql语句
     * @param pars
     *            参数
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> queryBySql(CharSequence sql, KeyValue<String, ?>... keyValues);

    /**
     * 
     * 根据sql语句查询实体
     * 
     * @param <T>
     *            实体类型
     * @param sql
     *            sql语句
     * @param pars
     *            参数
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> queryBySql(CharSequence sql, Map<String, Object> pars);

    /**
     * 
     * 根据sql语句查询实体
     * 
     * @param <T>
     *            实体类型
     * @param sql
     *            sql语句
     * @param pars
     *            参数
     * @param firstResult
     *            起始结果条数,-1则不设置起始条数
     * @param maxResults
     *            最大结果条数,-1则不设置最大条数
     * @param orders
     *            排序
     * 
     * @return List<T> 查询到的实体集
     * @author rain
     */
    List<T> queryBySql(CharSequence sql, Map<String, Object> pars, int firstResult, int maxResults, Order... orders);
}
