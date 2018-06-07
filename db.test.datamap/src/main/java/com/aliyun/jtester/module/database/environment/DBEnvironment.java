package com.aliyun.jtester.module.database.environment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public interface DBEnvironment {

    /**
     * 获取当前的DataSource 是否包装事务
     * 
     * @return
     */
    DataSource getDataSource();

    /**
     * 连接数据源
     * 
     */
    Connection connect();

    /**
     * Create a {@link PreparedStatement} object and binds fixture symbols to
     * SQL statement parameters with matching names.
     */
    PreparedStatement createStatementWithBoundFixtureSymbols(String commandText) throws SQLException;

    /**
     * 获得数据表的元信息
     * 
     */
    TableMeta getTableMetaData(String table);

    /**
     * 返回指定类型的默认值
     * 
     * @param javaType
     * @return
     */
    Object getDefaultValue(String javaType);

    /**
     * 将字符串类型转换为java对象
     * 
     * @param input
     * @param javaType
     * @return
     */
    Object toObjectValue(String input, String javaType);

    /**
     * 返回数据库的字段引号符(mysql:"'"),oracle(")
     * 
     * @return
     */
    String getFieldQuato();

    /**
     * 将java对象转换为对应的SQL对象<br>
     * 比如: java.util.Date对象在oracle插入时必须转为java.sql.Date对象
     * 
     * @param value
     * @return
     */
    Object converToSqlValue(Object value);

    // =======================================

    /**
     * Commit current transaction.
     */
    void commit() throws SQLException;

    /**
     * Rollback current transaction.
     */
    void rollback() throws SQLException;

}
