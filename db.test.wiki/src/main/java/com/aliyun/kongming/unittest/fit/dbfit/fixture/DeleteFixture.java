package com.aliyun.kongming.unittest.fit.dbfit.fixture;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.aliyun.kongming.unittest.fit.dbfit.HasMarkedException;
import com.aliyun.kongming.unittest.fit.dbfit.environment.DBEnvironment;
import com.aliyun.kongming.unittest.fit.dbfit.util.DbParameterAccessor;

import fit.Parse;

public class DeleteFixture extends InsertFixture {
    public DeleteFixture() {
        super();
    }

    public DeleteFixture(DBEnvironment env, String tableName) {
        super(env, tableName);
    }

    public void doRows(Parse rows) {
        if ((tableName == null || tableName.trim().length() == 0) && args.length > 0) {
            tableName = args[0];
        } else if (tableName == null) {
            tableName = rows.parts.text();
            rows = rows.more;
        }
        try {
            initParameters(rows.parts);// init parameters from the first row
            PreparedStatement statement = buildDeleteCommand(tableName, accessors);
            Parse row = rows;
            while ((row = row.more) != null) {
                runRow(statement, row);
                right(row);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (!(e instanceof HasMarkedException)) {
                exception(rows.parts, e);
            }
        }
    }

    public PreparedStatement buildDeleteCommand(String tableName, DbParameterAccessor[] accessors) throws SQLException {
        String delete = environment.buildDeleteCommand(tableName, accessors);
        PreparedStatement cs = (environment.supportsOuputOnInsert()) ? environment.getConnection().prepareCall(delete)
                : environment.getConnection().prepareStatement(delete, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < accessors.length; i++) {
            accessors[i].bindTo(this, cs, i + 1);
        }
        return cs;
    }
}
