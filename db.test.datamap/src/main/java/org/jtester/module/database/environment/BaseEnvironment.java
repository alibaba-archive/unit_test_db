package org.jtester.module.database.environment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.jtester.module.database.environment.typesmap.AbstractTypeMap;
import org.jtester.module.database.utility.DataSourceType;
import org.jtester.tools.commons.ExceptionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unitils.database.DatabaseUnitils;

public abstract class BaseEnvironment implements DBEnvironment {

    private static Logger     logger = LoggerFactory.getLogger(BaseEnvironment.class);
    protected final String    dataSourceName;
    protected DataSourceType  dataSourceType;

    protected AbstractTypeMap typeMap;

    protected Connection      currentConnection;

    protected BaseEnvironment(DataSourceType dataSourceType, String dataSourceName) {
        this.dataSourceName = dataSourceName;
        this.dataSourceType = dataSourceType;
    }

    private boolean driverRegistered = false;

    public DataSource getDataSource() {
        return DatabaseUnitils.getDataSource();
    }

    /**
     * 当前线程的连接
     * 
     * @return
     */
    public Connection connect() {
        DataSource dataSource = DatabaseUnitils.getDataSource(dataSourceName);
        try {
            currentConnection = dataSource.getConnection();
            currentConnection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currentConnection;
    }

    public void commit() throws SQLException {
        currentConnection.commit();
        currentConnection.setAutoCommit(false);
    }

    public void rollback() throws SQLException {
        checkConnectionValid(currentConnection);
        currentConnection.rollback();
    }

    /** Check the validity of the supplied connection. */
    public static void checkConnectionValid(final Connection conn) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new IllegalArgumentException("No open connection to a database is available. "
                    + "Make sure your database is running and that you have connected before performing any queries.");
        }
    }

    public PreparedStatement createStatementWithBoundFixtureSymbols(String commandText) throws SQLException {
        Connection connection = this.connect();
        PreparedStatement cs = connection.prepareStatement(commandText);
        return cs;
    }

    private Map<String, TableMeta> metas = new HashMap<String, TableMeta>();

    /**
     * 获得数据表的元信息
     * 
     * @param table
     * @return
     * @throws Exception
     */
    public TableMeta getTableMetaData(String table) {
        TableMeta meta = metas.get(table);
        if (meta == null) {
            try {
                String query = "select * from " + table + " where 1!=1";
                PreparedStatement st = this.createStatementWithBoundFixtureSymbols(query);
                ResultSet rs = st.executeQuery();

                meta = new TableMeta(table, rs.getMetaData(), this);
                metas.put(table, meta);
            } catch (Exception e) {
                throw ExceptionWrapper.getUndeclaredThrowableExceptionCaused(e);
            }
        }
        return meta;
    }

    public Object getDefaultValue(String javaType) {
        Object value = this.typeMap.getDefaultValue(javaType);
        return value;
    }

    public Object toObjectValue(String input, String javaType) {
        try {
            Object value = this.typeMap.toObjectByType(input, javaType);
            return value;
        } catch (Exception e) {
            logger.info("convert input[" + input + "] to type[" + javaType + "] error, so return input value.\n"
                    + e.getMessage());
            return input;
        }
    }

    /**
     * {@inheritDoc} <br>
     * <br>
     */
    @SuppressWarnings("rawtypes")
    public Object converToSqlValue(Object value) {
        if (value instanceof Enum) {
            return ((Enum) value).name();
        }
        return value;
    }
}
