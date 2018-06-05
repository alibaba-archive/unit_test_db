package com.aliyun.kongming.unittest.fit.dbfit;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.aliyun.kongming.unittest.fit.dbfit.environment.DBEnvironment;
import com.aliyun.kongming.unittest.fit.dbfit.util.SymbolUtil;
import com.aliyun.kongming.unittest.fit.util.ResourceUtil;
import com.aliyun.kongming.unittest.fit.util.StringHelper;

public class DbFixtureUtil {
    /**
     * 执行statement语句
     * 
     * @param dbEnvironment
     * @param statement
     * @return
     */
    public static final boolean execute(final DBEnvironment dbEnvironment, final String statement) {
        try {
            PreparedStatement st = dbEnvironment.createStatementWithBoundFixtureSymbols(statement);
            // return st.execute();
            // 注释掉的原因是apache的dbcp中st.execute()执行成功也是返回false，郁闷！
            st.execute();
            st.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 清除表table中所有数据
     * 
     * @param env
     * @param table
     */
    public static void cleanTable(DBEnvironment env, String table) {
        try {
            String statement = String.format("delete from %s ", table);
            PreparedStatement st = env.createStatementWithBoundFixtureSymbols(statement);
            st.execute();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setParameter(String name, String value) {
        if (value == null || "<null>".equals(value.toString().toLowerCase())) {
            SymbolUtil.setSymbol(name, null);
        } else if (value != null && value.toString().startsWith("<<")) {
            String varname = value.toString().substring(2);
            if (!name.equals(varname)) {
                SymbolUtil.setSymbol(name, SymbolUtil.getSymbol(varname));
            }
        } else {
            SymbolUtil.setSymbol(name, value);
        }
    }

    /**
     * 批量执行sql文件中的脚本
     * 
     * @param files
     * @return
     * @throws SQLException
     */
    public static boolean executeFromFile(final String... files) throws SQLException {
        final DBEnvironment dbEnvironment = DbFactory.instance().factory();
        dbEnvironment.connect();
        // dbEnvironment.getConnection().setAutoCommit(true);
        for (String file : files) {
            executeFromFile(dbEnvironment, file);
        }
        return true;
    }

    public static boolean executeFromFile(final DBEnvironment dbEnvironment, final String file) {
        InputStream is = ResourceUtil.getInputStreamFromFile(file);
        String temp = ResourceUtil.convertStreamToSQL(is);
        String[] sqls = temp.split(";");
        for (String sql : sqls) {
            if (StringHelper.isBlankOrNull(sql)) {
                continue;
            }
            execute(dbEnvironment, sql);
        }
        return true;
    }
}
