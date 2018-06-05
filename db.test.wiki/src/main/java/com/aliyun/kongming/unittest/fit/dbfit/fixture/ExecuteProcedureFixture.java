package com.aliyun.kongming.unittest.fit.dbfit.fixture;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.aliyun.kongming.unittest.fit.dbfit.DbFactory;
import com.aliyun.kongming.unittest.fit.dbfit.HasMarkedException;
import com.aliyun.kongming.unittest.fit.dbfit.base.JTesterFixture;
import com.aliyun.kongming.unittest.fit.dbfit.environment.DBEnvironment;
import com.aliyun.kongming.unittest.fit.dbfit.util.DbParameterAccessor;
import com.aliyun.kongming.unittest.fit.dbfit.util.NameNormaliser;
import com.aliyun.kongming.unittest.fit.dbfit.util.SymbolAccessQueryBinding;
import com.aliyun.kongming.unittest.fit.dbfit.util.SymbolAccessSetBinding;

import fit.Binding;
import fit.Parse;

public class ExecuteProcedureFixture extends JTesterFixture {
    private DBEnvironment         environment;
    // private CallableStatement statement;
    private String                procName;
    private DbParameterAccessor[] accessors;
    private Binding[]             columnBindings;
    private boolean               exceptionExpected = false;
    private boolean               excNumberDefined  = false;
    private int                   excNumberExpected;

    public ExecuteProcedureFixture() {
        this.environment = DbFactory.instance().factory();// DbEnvironmentFactory.getDefaultEnvironment();
    }

    public ExecuteProcedureFixture(DBEnvironment dbEnvironment, String procName, int expectedErrorCode) {
        this.procName = procName;
        this.environment = dbEnvironment;
        this.exceptionExpected = true;
        this.excNumberDefined = true;
        this.excNumberExpected = expectedErrorCode;
    }

    public ExecuteProcedureFixture(DBEnvironment dbEnvironment, String procName, boolean exceptionExpected) {
        this.procName = procName;
        this.environment = dbEnvironment;
        this.exceptionExpected = exceptionExpected;
        this.excNumberDefined = false;
    }

    public ExecuteProcedureFixture(DBEnvironment dbEnvironment, String procName) {
        this(dbEnvironment, procName, false);
    }

    private class PositionComparator implements Comparator<DbParameterAccessor> {
        public int compare(DbParameterAccessor o1, DbParameterAccessor o2) {
            return (int) Math.signum(o1.getPosition() - o2.getPosition());
        }
    }

    List<String> getSortedAccessorNames(DbParameterAccessor[] accessors) {
        DbParameterAccessor[] newacc = new DbParameterAccessor[accessors.length];
        System.arraycopy(accessors, 0, newacc, 0, accessors.length);
        Arrays.sort(newacc, new PositionComparator());
        List<String> nameList = new ArrayList<String>();
        String lastName = null;
        for (DbParameterAccessor p : newacc) {
            if (lastName != p.getName()) {
                lastName = p.getName();
                nameList.add(p.getName());
            }
        }
        return nameList;
    }

    private boolean containsReturnValue(DbParameterAccessor[] accessors) {
        for (DbParameterAccessor ac : accessors) {
            if (ac.getDirection() == DbParameterAccessor.RETURN_VALUE)
                return true;
        }
        return false;
    }

    public CallableStatement BuildCommand(String procName, DbParameterAccessor[] accessors) throws SQLException {
        List<String> accessorNames = getSortedAccessorNames(accessors);
        boolean isFunction = containsReturnValue(accessors);

        StringBuilder ins = new StringBuilder("{ ");
        if (isFunction) {
            ins.append("? =");
        }
        ins.append("call ").append(procName);
        String comma = "(";
        boolean hasArguments = false;
        for (int i = (isFunction ? 1 : 0); i < accessorNames.size(); i++) {
            ins.append(comma);
            ins.append("?");
            comma = ",";
            hasArguments = true;
        }
        if (hasArguments)
            ins.append(")");
        ins.append("}");

        CallableStatement cs = environment.getConnection().prepareCall(ins.toString());
        // System.out.println("Statement:"+ins.toString());
        for (DbParameterAccessor ac : accessors) {

            int realindex = accessorNames.indexOf(ac.getName());
            // System.out.println("Binding "+ac.getName()+" to "+(realindex+1));
            ac.bindTo(this, cs, realindex + 1); // jdbc params are 1-based
        }
        return cs;
    }

    private Parse headerRow;

    public void doTable(Parse table) {
        this.headerRow = table.parts;
        try {
            super.doTable(table);
        } catch (Exception e) {
            exception(headerRow, e);
        }
    }

    public void doRows(Parse rows) {
        // if table not defined as parameter, read from fixture argument; if
        // still not defined, read from first row
        if ((procName == null || procName.trim().length() == 0) && args.length > 0) {
            procName = args[0];
        }
        if (rows != null) {
            executeStatementForEachRow(rows);
        } else {
            executeUsingHeaderRow();
        }
    }

    private void executeUsingHeaderRow() {
        try {
            accessors = new DbParameterAccessor[0];
            CallableStatement statement = BuildCommand(procName, accessors);
            if (!exceptionExpected) {
                statement.execute();
            } else {
                executeExpectingException(statement, headerRow);// execute using
                // header row
            }
            statement.close();
        } catch (SQLException e) {
            exception(headerRow, e);
            headerRow.parts.last().more = new Parse("td", e.getMessage(), null, null);
            e.printStackTrace();
        }
    }

    private void executeStatementForEachRow(Parse rows) {
        try {
            initParameters(rows.parts);// init parameters from the first row
            CallableStatement statement = BuildCommand(procName, accessors);
            Parse row = rows;
            while ((row = row.more) != null) {
                runRow(statement, row);
            }
            statement.close();
        } catch (Exception e) {
            exception(rows.parts, e);
        }
    }

    private void initParameters(Parse headerCells) throws SQLException {
        Map<String, DbParameterAccessor> allParams = environment.getAllProcedureParameters(procName);
        if (allParams.isEmpty()) {
            throw new SQLException(
                    "Cannot retrieve list of parameters for " + procName + " - check spelling and access rights");
        }
        accessors = new DbParameterAccessor[headerCells.size()];
        columnBindings = new Binding[headerCells.size()];
        for (int i = 0; headerCells != null; i++, headerCells = headerCells.more) {
            String name = headerCells.text();
            String paramName = NameNormaliser.normaliseName(name);
            accessors[i] = allParams.get(paramName);
            if (accessors[i] == null)
                throw new SQLException("Cannot find parameter for column " + i + " name=\"" + paramName + "\"");
            boolean isOutput = headerCells.text().endsWith("?");
            if (accessors[i].getDirection() == DbParameterAccessor.INPUT_OUTPUT) {
                // clone, separate into input and output
                accessors[i] = new DbParameterAccessor(accessors[i]);
                accessors[i].setDirection(isOutput ? DbParameterAccessor.OUTPUT : DbParameterAccessor.INPUT);
            }
            if (isOutput) {
                columnBindings[i] = new SymbolAccessQueryBinding();
            } else {
                // sql server quirk. if output parameter is used in an input
                // column, then
                // the param should be cloned and remapped to IN/OUT
                if (accessors[i].getDirection() == DbParameterAccessor.OUTPUT) {
                    accessors[i] = new DbParameterAccessor(accessors[i]);
                    accessors[i].setDirection(DbParameterAccessor.INPUT);
                }
                columnBindings[i] = new SymbolAccessSetBinding();
            }
            columnBindings[i].adapter = accessors[i];
        }
    }

    private void runRow(CallableStatement statement, Parse row) {
        Parse cell = row.parts;
        try {
            statement.clearParameters();
            // first set input params
            for (int column = 0; column < accessors.length; column++, cell = cell.more) {
                if (accessors[column].getDirection() == DbParameterAccessor.INPUT) {
                    columnBindings[column].doCell(this, cell);
                }
            }
            if (!exceptionExpected) {
                statement.execute();
                cell = row.parts;
                // next evaluate output params
                for (int column = 0; column < accessors.length; column++, cell = cell.more) {
                    if (accessors[column].getDirection() == DbParameterAccessor.OUTPUT
                            || accessors[column].getDirection() == DbParameterAccessor.RETURN_VALUE) {
                        columnBindings[column].doCell(this, cell);
                    }
                }
            } else {
                executeExpectingException(statement, row);
            }
        } catch (Throwable e) {
            exception(cell, e);
            throw new HasMarkedException(e);
        }
    }

    private void executeExpectingException(CallableStatement statement, Parse row) {
        try {
            statement.execute();
            // no exception if we are here, mark whole row
            wrong(row);
        } catch (SQLException sqle) {
            if (!excNumberDefined)
                right(row);
            else {
                int realError = environment.getExceptionCode(sqle);
                if (realError == excNumberExpected)
                    right(row);
                else {
                    wrong(row);
                    row.parts.addToBody(fit.Fixture.gray(" got error code " + realError));
                }
            }
        }
    }
}
