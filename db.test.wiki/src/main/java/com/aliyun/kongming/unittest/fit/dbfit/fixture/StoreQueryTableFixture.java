package com.aliyun.kongming.unittest.fit.dbfit.fixture;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aliyun.kongming.unittest.fit.dbfit.DbFactory;
import com.aliyun.kongming.unittest.fit.dbfit.base.JTesterFixture;
import com.aliyun.kongming.unittest.fit.dbfit.environment.DBEnvironment;
import com.aliyun.kongming.unittest.fit.dbfit.util.DataTable;
import com.aliyun.kongming.unittest.fit.dbfit.util.SymbolUtil;

import fit.Parse;

public class StoreQueryTableFixture extends JTesterFixture {
    private DBEnvironment dbEnvironment;
    private String        query;
    private String        symbolName;

    public StoreQueryTableFixture() {
        dbEnvironment = DbFactory.instance().factory();// DbEnvironmentFactory.getDefaultEnvironment();
    }

    public StoreQueryTableFixture(DBEnvironment environment, String query, String symbolName) {
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
            SymbolUtil.setSymbol(symbolName, dt);
        } catch (SQLException sqle) {
            throw new Error(sqle);
        }
    }
}
