package com.aliyun.kongming.unittest.fit.dbfit.fixture;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.aliyun.kongming.unittest.fit.dbfit.DbFactory;
import com.aliyun.kongming.unittest.fit.dbfit.base.JTesterFixture;
import com.aliyun.kongming.unittest.fit.dbfit.environment.DBEnvironment;
import com.aliyun.kongming.unittest.fit.dbfit.util.DataRow;
import com.aliyun.kongming.unittest.fit.dbfit.util.DataTable;
import com.aliyun.kongming.unittest.fit.dbfit.util.SymbolUtil;

import fit.Parse;

public class StoreQueryFixture extends JTesterFixture {
    private DBEnvironment dbEnvironment;
    private String        query;
    private String        symbolName;

    public StoreQueryFixture() {
        dbEnvironment = DbFactory.instance().factory();// DbEnvironmentFactory.getDefaultEnvironment();
    }

    public StoreQueryFixture(DBEnvironment environment, String query, String symbolName) {
        this.dbEnvironment = environment;
        this.query = query;
        this.symbolName = symbolName;
    }

    public void doTable(Parse table) {
        if (query == null || symbolName == null) {
            if (args.length < 2)
                throw new UnsupportedOperationException(
                        "No query and symbol name specified to StoreQuery constructor or argument list");
            query = args[0];
            symbolName = args[1];
        }
        if (symbolName.startsWith(">>"))
            symbolName = symbolName.substring(2);
        try {
            PreparedStatement st = dbEnvironment.createStatementWithBoundFixtureSymbols(query);
            ResultSet rs = st.executeQuery();
            DataTable dt = new DataTable(rs);
            st.close();

            int rowSize = dt.getRowSize();
            if (rowSize == 0) {
                throw new RuntimeException("can't retrieve any data by query:" + query);
            } else if (rowSize > 1) {
                throw new RuntimeException("allowing one row data retrieved by query:" + query);
            }
            int colSize = dt.getColSize();
            if (colSize == 0) {
                throw new RuntimeException("can't retrieve any column by query:" + query);
            } else if (colSize > 1) {
                throw new RuntimeException("allowing one column data retrieved by query:" + query);
            }
            DataRow row = dt.getUnprocessedRows().get(0);
            List<String> values = row.getStringValues();
            SymbolUtil.setSymbol(symbolName, values.get(0));
        } catch (SQLException sqle) {
            throw new Error(sqle);
        }
    }
}
