package com.aliyun.kongming.unittest.fit.dbfit;

import static com.aliyun.kongming.unittest.fit.util.ConfigUtil.PROPKEY_DATASOURCE_DRIVERCLASSNAME;
import static com.aliyun.kongming.unittest.fit.util.ConfigUtil.PROPKEY_DATASOURCE_PASSWORD;
import static com.aliyun.kongming.unittest.fit.util.ConfigUtil.PROPKEY_DATASOURCE_URL;
import static com.aliyun.kongming.unittest.fit.util.ConfigUtil.PROPKEY_DATASOURCE_USERNAME;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aliyun.kongming.unittest.fit.dbfit.environment.DBEnvironment;
import com.aliyun.kongming.unittest.fit.dbfit.fixture.CleanFixture;
import com.aliyun.kongming.unittest.fit.dbfit.fixture.CompareStoredQueriesFixture;
import com.aliyun.kongming.unittest.fit.dbfit.fixture.DeleteFixture;
import com.aliyun.kongming.unittest.fit.dbfit.fixture.ExecuteProcedureFixture;
import com.aliyun.kongming.unittest.fit.dbfit.fixture.InsertFixture;
import com.aliyun.kongming.unittest.fit.dbfit.fixture.InspectFixture;
import com.aliyun.kongming.unittest.fit.dbfit.fixture.QueryFixture;
import com.aliyun.kongming.unittest.fit.dbfit.fixture.QueryStatsFixture;
import com.aliyun.kongming.unittest.fit.dbfit.fixture.StoreQueryFixture;
import com.aliyun.kongming.unittest.fit.dbfit.fixture.StoreQueryTableFixture;
import com.aliyun.kongming.unittest.fit.dbfit.fixture.UpdateFixture;
import com.aliyun.kongming.unittest.fit.dbfit.util.Options;
import com.aliyun.kongming.unittest.fit.dbfit.util.SymbolUtil;
import com.aliyun.kongming.unittest.fit.util.DateUtil;
import com.aliyun.kongming.unittest.fit.util.StringHelper;
import com.aliyun.kongming.unittest.fit.utility.DbFitUnitils;

import fit.Fixture;
import fitlibrary.SequenceFixture;
import fitlibrary.table.Table;
import fitlibrary.utility.TestResults;

public class DatabaseFixture extends SequenceFixture {
    private static Log      log = LogFactory.getLog(DatabaseFixture.class);
    protected DBEnvironment environment;

    public DatabaseFixture() {
        this.environment = DbFactory.instance().factory();
    }

    @Override
    public void setUp(Table firstTable, TestResults testResults) {
        Options.reset();
        super.setUp(firstTable, testResults);
    }

    /**
     * 在测试的最后结束事务
     * 
     * @throws SQLException
     */
    public static void endTransactional() {
        try {
            DBEnvironment environment = DbFactory.instance().factory();
            Connection conn = environment.getConnection();
            if (conn != null && conn.isClosed() == false) {
                environment.rollback();
                environment.closeConnection();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void tearDown(Table firstTable, TestResults testResults) {
        try {
            log.info("Rolling back");
            if (environment == null) {
                return;
            }
            if (DbFitUnitils.isSpringTransaction() == false) {
                environment.rollback();
                environment.closeConnection();
            } else if (DbFitUnitils.isTransactionEnabled() == false) {
                environment.commit();
                environment.closeConnection();
            }
            //			environment.commit();
            //			environment.closeConnection();
        } catch (Exception e) {
            this.exception(firstTable.parse, e);
        }
        super.tearDown(firstTable, testResults);
    }

    public boolean connect() throws Exception {
        environment.connect();
        environment.getConnection().setAutoCommit(false);
        return true;
    }

    /**
     * 使用和unitils.properties中定义的数据驱动class来建立连接
     * 
     * @param dataSource
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public boolean connect(String dataSource, String username, String password) throws Exception {
        environment.connect(dataSource, username, password);
        environment.getConnection().setAutoCommit(false);
        return true;
    }

    /**
     * 使用指定的driver来建立连接
     * 
     * @param dataSource
     * @param username
     * @param password
     * @param driver
     * @return
     * @throws Exception
     */
    public boolean connect(String dataSource, String username, String password, String driver) throws Exception {
        environment.connect(dataSource, username, password, driver);
        environment.getConnection().setAutoCommit(false);
        return true;
    }

    /**
     * 使用默认的driver（unitils.properties中指定)来建立连接
     * 
     * @param connectionString
     * @return
     * @throws Exception
     */
    public boolean connect(String connectionString) throws Exception {
        environment.connect(connectionString);
        environment.getConnection().setAutoCommit(false);
        return true;
    }

    /**
     * 使用指定的driver class来建立连接
     * 
     * @param connectionString
     * @param driver
     * @return
     * @throws Exception
     */
    public boolean connect(String connectionString, String driver) throws Exception {
        environment.connect(connectionString);
        environment.getConnection().setAutoCommit(false);
        return true;
    }

    final static String NO_VALID_VALUE_MESSAGE = "can't find valid value of key[%s] in file[%s]!";

    /**
     * 使用配置文件建立连接,prefix是数据库连接属性前缀
     * 
     * @param propFile
     * @param identifier
     * @return
     * @throws Exception
     */
    public boolean connectFromFile(String identifier, String propFile) throws Exception {
        InputStream in = ClassLoader.getSystemResourceAsStream(propFile);
        if (in == null) {
            throw new RuntimeException(String.format("can't find file[%s] in classpath!", propFile));
        }
        Properties props = new Properties();

        props.load(in);
        String prevName = StringHelper.isBlankOrNull(identifier) ? "" : identifier + ".";
        String url = props.getProperty(prevName + PROPKEY_DATASOURCE_URL);
        if (StringHelper.isBlankOrNull(url)) {
            throw new RuntimeException(
                    String.format(NO_VALID_VALUE_MESSAGE, prevName + PROPKEY_DATASOURCE_URL, propFile));
        }
        String user = props.getProperty(prevName + PROPKEY_DATASOURCE_USERNAME);
        if (StringHelper.isBlankOrNull(user)) {
            throw new RuntimeException(
                    String.format(NO_VALID_VALUE_MESSAGE, prevName + PROPKEY_DATASOURCE_USERNAME, propFile));
        }
        String pass = props.getProperty(prevName + PROPKEY_DATASOURCE_PASSWORD);
        if (pass == null) {
            pass = "";
        }
        String driver = props.getProperty(prevName + PROPKEY_DATASOURCE_DRIVERCLASSNAME);
        if (StringHelper.isBlankOrNull(driver)) {
            throw new RuntimeException(
                    String.format(NO_VALID_VALUE_MESSAGE, prevName + PROPKEY_DATASOURCE_DRIVERCLASSNAME, propFile));
        }

        return connect(url, user, pass, driver);
    }

    /**
     * 使用配置文件建立连接
     * 
     * @param propFile
     * @return
     * @throws Exception
     */
    public boolean connectFromFile(String identifier) throws Exception {
        return connectFromFile(identifier, "unitils.properties");
    }

    public boolean close() throws SQLException {
        environment.rollback();
        environment.closeConnection();
        return true;
    }

    public boolean setParameter(String name, String value) {
        DbFixtureUtil.setParameter(name, value);
        return true;
    }

    /**
     * query查询结果是单个值，以变量形式存储下来备用
     * 
     * @param para
     * @param query
     * @return
     */
    public Fixture storeQuery(String query, String symbolName) {
        return new StoreQueryFixture(environment, query, symbolName);
    }

    /**
     * 设置当前时间格式
     * 
     * @param format
     * @return
     */
    public boolean setDateTimeFormat(String format) {
        String datetime = DateUtil.currDateTimeStr(format);
        DbFixtureUtil.setParameter("datetime", datetime);
        return true;
    }

    /**
     * 设置当前日期格式
     * 
     * @param format
     * @return
     */
    public boolean setDateFormat(String format) {
        String date = DateUtil.currDateTimeStr(format);
        DbFixtureUtil.setParameter("date", date);
        return true;
    }

    public boolean clearParameters() {
        SymbolUtil.clearSymbols();
        return true;
    }

    public Fixture query(String query) {
        return new QueryFixture(environment, query);
    }

    public Fixture orderedQuery(String query) {
        return new QueryFixture(environment, query, true);
    }

    public boolean execute(String statement) {
        // return new ExecuteFixture(environment, statement);
        return DbFixtureUtil.execute(environment, statement);
    }

    public Fixture executeProcedure(String statement) {
        return new ExecuteProcedureFixture(environment, statement);
    }

    public Fixture executeProcedureExpectException(String statement) {
        return new ExecuteProcedureFixture(environment, statement, true);
    }

    public Fixture executeProcedureExpectException(String statement, int code) {
        return new ExecuteProcedureFixture(environment, statement, code);
    }

    public Fixture insert(String tableName) {
        return new InsertFixture(environment, tableName);
    }

    public Fixture update(String tableName) {
        return new UpdateFixture(environment, tableName);
    }

    public Fixture clean() {
        return new CleanFixture(environment);
    }

    public boolean cleanTable(String tables) {
        String ts[] = tables.split("[;,]");
        for (String table : ts) {
            DbFixtureUtil.cleanTable(environment, table);
        }
        return true;
    }

    /**
     * 根据表字段删除数据
     * 
     * @param table
     * @return
     */
    public Fixture delete(String table) {
        return new DeleteFixture(environment, table);
    }

    public boolean rollback() throws SQLException {
        environment.rollback();
        environment.getConnection().setAutoCommit(false);
        return true;
    }

    public boolean commit() throws SQLException {
        environment.commit();
        environment.getConnection().setAutoCommit(false);
        return true;
    }

    public Fixture queryStats() {
        return new QueryStatsFixture(environment);
    }

    public Fixture inspectProcedure(String procName) {
        return new InspectFixture(environment, InspectFixture.MODE_PROCEDURE, procName);
    }

    public Fixture inspectTable(String tableName) {
        return new InspectFixture(environment, InspectFixture.MODE_TABLE, tableName);
    }

    public Fixture inspectView(String tableName) {
        return new InspectFixture(environment, InspectFixture.MODE_TABLE, tableName);
    }

    public Fixture inspectQuery(String query) {
        return new InspectFixture(environment, InspectFixture.MODE_QUERY, query);
    }

    /**
     * 把查询结果的整张表作为结果存储下来 <br>
     * storeQuery把查询结果是单个值，以变量形式存储下来
     * 
     * @param query
     * @param symbolName
     * @return
     */
    public Fixture storeQueryTable(String query, String symbolName) {
        return new StoreQueryTableFixture(environment, query, symbolName);
    }

    public Fixture compareStoredQueries(String symbol1, String symbol2) {
        return new CompareStoredQueriesFixture(environment, symbol1, symbol2);
    }

    public boolean setOption(String option, String value) {
        Options.setOption(option, value);
        return true;
    }

    /**
     * 执行指定文件中的sql语句
     * 
     * @param file
     * @return
     */
    public boolean executeFile(String file) {
        return DbFixtureUtil.executeFromFile(this.environment, file == null ? null : file.trim());
    }
}
