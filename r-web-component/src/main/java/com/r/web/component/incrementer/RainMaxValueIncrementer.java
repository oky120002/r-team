package com.r.web.component.incrementer;

import org.springframework.dao.DataAccessException;

/**
 * 数据库自增长值获取器<br />
 * 次自增长获取器会在服务器启动自动检查自增长表是否存在,里面的有效列是否存在,如果不存在则会尝试删除表再重建<br />
 */
public interface RainMaxValueIncrementer {
    /**
     * 根据编号生成器类型获取下一个编号
     * 
     * @return 整型编号
     * @throws DataAccessException
     *             数据库操作出错抛出此异常
     */
    int nextIntValue() throws DataAccessException;

    /**
     * 根据编号生成器类型获取下一个编号
     * 
     * @return 长整型编号
     * @throws DataAccessException
     *             数据库操作出错抛出此异常
     */
    long nextLongValue() throws DataAccessException;

    /**
     * 根据编号生成器类型获取下一个编号
     * 
     * @return 字符串编号
     * @throws DataAccessException
     *             数据库操作出错抛出此异常
     */
    String nextStringValue() throws DataAccessException;

    /**
     *
     * 根据编号生成器类型获取下一个编号
     *
     * @param columnTypeValue
     *            自增长类型
     * @return 整型编号
     * @throws DataAccessException
     *             数据库操作出错抛出此异常
     */
    int nextIntValue(String columnTypeValue) throws DataAccessException;

    /**
     *
     * 根据编号生成器类型获取下一个编号
     *
     * @param columnTypeValue
     *            自增长类型
     * @return 长整型编号
     * @throws DataAccessException
     *             数据库操作出错抛出此异常
     */
    long nextLongValue(String columnTypeValue) throws DataAccessException;

    /**
     * 根据编号生成器类型获取下一个编号
     * 
     * @param columnTypeValue
     *            自增长类型
     * @return 字符串编号
     * @throws DataAccessException
     *             数据库操作出错抛出此异常
     */
    String nextStringValue(String columnTypeValue) throws DataAccessException;

    /**
     * 默认类型编号归零,重新计算
     * 
     * @throws DataAccessException
     *             数据库操作出错抛出此异常
     */
    void clear() throws DataAccessException;

    /**
     * 根据类型编号归零,重新计算
     * 
     * @throws DataAccessException
     *             数据库操作出错抛出此异常
     */
    void clear(String columnTypeValue) throws DataAccessException;

    /**
     * 所有编号归零,重新计算
     * 
     * @throws DataAccessException
     *             数据库操作出错抛出此异常
     */
    void clearAll() throws DataAccessException;
}