package com.r.web.components.incrementer;

import org.springframework.dao.DataAccessException;

/**
 * 数据库自增长值获取器<br />
 * 次自增长获取器会在服务器启动自动检查自增长表是否存在,里面的有效列是否存在,如果不存在则会尝试删除表再重建<br />
 */
public interface RainMaxValueIncrementer {
    /**
     * Increment the data store field's max value as int.
     * 
     * @return int next data store value such as <b>max + 1</b>
     * @throws org.springframework.dao.DataAccessException
     *             in case of errors
     */
    int nextIntValue() throws DataAccessException;

    /**
     * Increment the data store field's max value as long.
     * 
     * @return int next data store value such as <b>max + 1</b>
     * @throws org.springframework.dao.DataAccessException
     *             in case of errors
     */
    long nextLongValue() throws DataAccessException;

    /**
     * Increment the data store field's max value as String.
     * 
     * @return next data store value such as <b>max + 1</b>
     * @throws org.springframework.dao.DataAccessException
     *             in case of errors
     */
    String nextStringValue() throws DataAccessException;

    /**
     * 
     * <根据编号生成器类型获取下一个编号>
     * 
     * @param columnTypeValue
     *            类型
     * 
     * @return int 编号
     * @exception throws [异常类型] [异常说明]
     * @author rain
     * @see [类、类#方法、类#成员]
     */
    int nextIntValue(String columnTypeValue) throws DataAccessException;

    /**
     * 
     * <根据编号生成器类型获取下一个编号>
     * 
     * @param columnTypeValue
     *            类型
     * 
     * @return long 编号
     * @exception throws [异常类型] [异常说明]
     * @author rain
     * @see [类、类#方法、类#成员]
     */
    long nextLongValue(String columnTypeValue) throws DataAccessException;

    /**
     * 
     * <根据编号生成器类型获取下一个编号>
     * 
     * @param columnTypeValue
     *            类型
     * 
     * @return String 编号
     * @exception throws [异常类型] [异常说明]
     * @author rain
     * @see [类、类#方法、类#成员]
     */
    String nextStringValue(String columnTypeValue) throws DataAccessException;
}
