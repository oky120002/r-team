package com.r.web.component.incrementer.impl.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.incrementer.AbstractColumnMaxValueIncrementer;

import com.r.web.component.incrementer.RainMaxValueIncrementer;

/**
 * MySql自增长值获取器<br/>
 * 次自增长获取器会在服务器启动自动检查自增长表是否存在,里面的有效列是否存在,如果不存在则会尝试删除表再重建<br/>
 * dataSource : 实现{@link BasicDataSource}接口的数据源<br/>
 * incrementerName : 表名<br/>
 * columnName : 自增长数值列名<br/>
 * incrementerColumnName : 自增长类型列名<br/>
 * cacheSize : 缓存长度,每生成这个长度的数字,则刷新缓存到数据库<br/>
 * max : 自增长数值最大值,超过则重新计算自增长值,如果为null则无限大<br/>
 *
 * @author rain
 *
 */
public class RainMySQLMaxValueIncrementer extends AbstractColumnMaxValueIncrementer implements RainMaxValueIncrementer {
    /** 默认的自增长类型 */
    private static final String DEFAULT_INCREMENTER_TYPE_VALUE = "_DefaultIncrementerType";
    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(RainMySQLMaxValueIncrementer.class);
    /** 检索新的序列值的SQL字符串 */
    private static final String VALUE_SQL = "select last_insert_id()";
    /** 所有自增长值的集合 */
    private ConcurrentMap<String, Long> ids = new ConcurrentHashMap<String, Long>();
    /** 自增长值的最大值,超过则从新从0开始,如果为null则为无限大 */
    private Long max;
    /** 自增长类型所在列名 */
    private String incrementerColumnName;
    /** 自增长类型值 */
    private String incrementerTypeValue = DEFAULT_INCREMENTER_TYPE_VALUE;

    /**
     * Default constructor for bean property style usage.
     *
     * @see #setDataSource
     * @see #setIncrementerName
     * @see #setColumnName
     */
    public RainMySQLMaxValueIncrementer() {
    }

    /**
     * Convenience constructor.
     *
     * @param dataSource
     *            the DataSource to use
     * @param incrementerName
     *            the name of the sequence/table to use
     * @param columnName
     *            the name of the column in the sequence table to use
     */
    public RainMySQLMaxValueIncrementer(DataSource dataSource, String incrementerName, String columnName) {
        super(dataSource, incrementerName, columnName);
    }

    /** 获取自增长最大值 */
    public Long getMax() {
        return max;
    }

    /** 设置自增长最大值 */
    public void setMax(Long max) {
        this.max = max;
    }

    /** 获取自增长类型的列名 */
    public String getIncrementerColumnName() {
        return incrementerColumnName;
    }

    /** 设置自增长类型的列名 */
    public void setIncrementerColumnName(String incrementerColumnName) {
        this.incrementerColumnName = incrementerColumnName;
    }

    @Override
    public void afterPropertiesSet() {
        logger.info("Mysql Incrementer模块启动...........................");
        // 设置默认值
        if (StringUtils.isBlank(getIncrementerName())) {
            setIncrementerName("tab_sequence");
        }
        if (StringUtils.isBlank(getColumnName())) {
            setColumnName("value");
        }
        if (StringUtils.isBlank(getIncrementerColumnName())) {
            setIncrementerColumnName("incrementertype");
        }
        if (getCacheSize() < 1) {
            setCacheSize(1);
        }
        super.afterPropertiesSet();
        initTable();
    }

    @Override
    public int nextIntValue() throws DataAccessException {
        return nextIntValue(DEFAULT_INCREMENTER_TYPE_VALUE);
    }

    @Override
    public long nextLongValue() throws DataAccessException {
        return nextLongValue(DEFAULT_INCREMENTER_TYPE_VALUE);
    }

    @Override
    public String nextStringValue() throws DataAccessException {
        return nextStringValue(DEFAULT_INCREMENTER_TYPE_VALUE);
    }

    @Override
    public int nextIntValue(String columnTypeValue) throws DataAccessException {
        synchronized (DEFAULT_INCREMENTER_TYPE_VALUE) {
            this.incrementerTypeValue = StringUtils.isBlank(columnTypeValue) ? DEFAULT_INCREMENTER_TYPE_VALUE : columnTypeValue;
            return super.nextIntValue();
        }
    }

    @Override
    public long nextLongValue(String columnTypeValue) throws DataAccessException {
        synchronized (DEFAULT_INCREMENTER_TYPE_VALUE) {
            this.incrementerTypeValue = StringUtils.isBlank(columnTypeValue) ? DEFAULT_INCREMENTER_TYPE_VALUE : columnTypeValue;
            return super.nextLongValue();
        }
    }

    @Override
    public String nextStringValue(String columnTypeValue) throws DataAccessException {
        synchronized (DEFAULT_INCREMENTER_TYPE_VALUE) {
            this.incrementerTypeValue = StringUtils.isBlank(columnTypeValue) ? DEFAULT_INCREMENTER_TYPE_VALUE : columnTypeValue;
            return super.nextStringValue();
        }
    }

    @Override
    public void returnZero() throws DataAccessException {
        initTable();
        ids.clear();
    }

    @Override
    protected long getNextKey() throws DataAccessException {
        if (getValue("maxId") == getValue("nextId")) {
            /*
             * Need to use straight JDBC code because we need to make sure that
             * the insert and select are performed on the same connection
             * (otherwise we can't be sure that last_insert_id() returned the
             * correct value)
             */
            Connection con = DataSourceUtils.getConnection(getDataSource());
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
                String incrementerName = getIncrementerName();
                String columnName = getColumnName();
                String incrementerColumnName = getIncrementerColumnName();
                if (max != null && getValue("nextId") + getCacheSize() > max.longValue()) {
                    stmt.executeUpdate("update " + incrementerName + " set " + columnName + " = 0 where " + incrementerColumnName + " = '" + this.incrementerTypeValue + "'");
                    stmt.executeUpdate("alter table " + incrementerName + " AUTO_INCREMENT = " + getCacheSize());
                }
                stmt.executeUpdate("update " + incrementerName + " set " + columnName + " = last_insert_id(" + columnName + " + " + getCacheSize() + ") where " + incrementerColumnName + " = '" + this.incrementerTypeValue + "'");
                // Retrieve the new max of the sequence column...
                ResultSet rs = null;
                try {
                    rs = stmt.executeQuery(VALUE_SQL);
                    if (!rs.next()) {
                        throw new DataAccessResourceFailureException("last_insert_id() failed after executing an update");
                    }
                    setValue("maxId", rs.getLong(1));
                } finally {
                    JdbcUtils.closeResultSet(rs);
                }
                setValue("nextId", getValue("maxId") - getCacheSize() + 1);
            } catch (SQLException ex) {
                throw new DataAccessResourceFailureException("Could not obtain last_insert_id()", ex);
            } finally {
                JdbcUtils.closeStatement(stmt);
                DataSourceUtils.releaseConnection(con, getDataSource());
            }
        } else {
            setValue("nextId", getValue("nextId") + 1);
        }
        return getValue("nextId");
    }

    /** 从缓存集合中取值 */
    private long getValue(String key) {
        // 这个是用来判断次类型的自增长行是否存在的.不存在则创建一个
        if (!ids.containsKey(this.incrementerTypeValue)) {
            initIncrementerTypeValue(this.incrementerTypeValue);
            ids.put(this.incrementerTypeValue, Long.MAX_VALUE);
        }
        Long value = ids.get(this.incrementerTypeValue + "_" + key);
        if (value == null || value.longValue() < 0) {
            return 0;
        }
        return value.longValue();
    }

    /** 设置缓存集合中的值 */
    private void setValue(String key, long value) {
        ids.put(this.incrementerTypeValue + "_" + key, Long.valueOf(value));
    }

    /**
     * 初始化数据表中的自增长类型内容
     *
     * @param incrementerTypeValue
     */
    private void initIncrementerTypeValue(String incrementerTypeValue) {
        Connection con = DataSourceUtils.getConnection(getDataSource());
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
            String incrementerName = getIncrementerName();
            String columnName = getColumnName();
            String incrementerType = getIncrementerColumnName();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("select count(1) from `" + incrementerName + "` where `" + incrementerType + "` = '" + incrementerTypeValue + "'");
                if (!rs.next()) {
                    throw new DataAccessResourceFailureException("'select count' 语句的下一句'sql'语句执行错误");
                }
                if (rs.getLong(1) == 0) {
                    try {
                        logger.info("初始化自增长类型值 : " + incrementerTypeValue + "..................");
                        stmt.executeUpdate("insert into `" + incrementerName + "` (`" + columnName + "`, `" + incrementerType + "`) values (0, '" + incrementerTypeValue + "')");
                    } catch (SQLException e) {
                        throw new DataAccessResourceFailureException("初始化自增长类型值失败.........", e);
                    }
                }
            } catch (SQLException e) {
                throw new DataAccessResourceFailureException("'sql'语句执行错误", e);
            } finally {
                JdbcUtils.closeResultSet(rs);
            }
        } catch (DataAccessResourceFailureException darfe) {
            throw darfe;
        } catch (SQLException ex) {
            throw new DataAccessResourceFailureException("开启数据库链接或者开启事务出错", ex);
        } finally {
            JdbcUtils.closeStatement(stmt);
            DataSourceUtils.releaseConnection(con, getDataSource());
        }
    }

    /**
     * 初始化数据表<br />
     * 步骤1.首先校验是否存在,如果不存在则建表<br />
     * 步骤2.不论表内的列是否正确,都进行删除表再建表的操作
     *
     * @param stmt
     */
    private void initTable() {
        Connection con = DataSourceUtils.getConnection(getDataSource());
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
            String incrementerName = getIncrementerName();
            String columnName = getColumnName();
            String incrementerColumnName = getIncrementerColumnName();
            // 校验表incrementerName,列columnName和列incrementerColumnName是否正确,如果不正确会通过查询抛出SQLException异常,从而建表
            try {
                logger.info("初始化数据表.................");
                stmt.executeUpdate("DROP TABLE IF EXISTS `" + incrementerName + "`");
                stmt.executeUpdate("CREATE TABLE `" + incrementerName + "` ( `" + columnName + "` int(11) NOT NULL DEFAULT '0', `" + incrementerColumnName + "` varchar(255) DEFAULT NULL )");
            } catch (SQLException ex) {
                throw new DataAccessResourceFailureException("初始化数据表失败.........", ex);
            }
        } catch (DataAccessResourceFailureException darfe) {
            throw darfe;
        } catch (SQLException ex) {
            throw new DataAccessResourceFailureException("开启数据库链接或者开启事务出错", ex);
        } finally {
            JdbcUtils.closeStatement(stmt);
            DataSourceUtils.releaseConnection(con, getDataSource());
        }
    }
}