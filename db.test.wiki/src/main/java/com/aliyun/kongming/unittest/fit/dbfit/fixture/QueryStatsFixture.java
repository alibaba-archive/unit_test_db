package com.aliyun.kongming.unittest.fit.dbfit.fixture;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aliyun.kongming.unittest.fit.dbfit.DbFactory;
import com.aliyun.kongming.unittest.fit.dbfit.environment.DBEnvironment;

import fit.ColumnFixture;

public class QueryStatsFixture extends ColumnFixture {
    private DBEnvironment environment;

    public QueryStatsFixture() {
        environment = DbFactory.instance().factory();// DbEnvironmentFactory.getDefaultEnvironment();
    }

    public QueryStatsFixture(DBEnvironment environment) {
        this.environment = environment;
    }

    public String tableName;
    public String where;
    public String query;

    public void setViewName(String value) {
        tableName = value;
    }

    private boolean hasExecuted = false;

    public void reset() {
        hasExecuted = false;
        where = null;
        query = null;
        _rows = 0;
        tableName = null;
    }

    private int _rows;

    private void execQuery() throws SQLException {
        if (hasExecuted)
            return;
        if (query == null) {
            query = "select * from " + tableName + (where != null ? " where " + where : "");
        }
        PreparedStatement st = environment
                .createStatementWithBoundFixtureSymbols("select count(*) from (" + query + ") temp");
        ResultSet rs = st.executeQuery();
        if (rs.next())
            _rows = rs.getInt(1);

        hasExecuted = true;
        rs.close();
        st.close();
    }

    public int rowCount() throws SQLException {
        execQuery();
        return _rows;
    }

    public boolean isEmpty() throws SQLException {
        return rowCount() == 0;
    }
}
