package com.aliyun.kongming.unittest.fit.dbfit.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fit.Fixture;

public class DbParameterAccessor extends DbTypeAdapter {
    public static final int RETURN_VALUE = 0;
    public static final int INPUT        = 1;
    public static final int OUTPUT       = 2;
    public static final int INPUT_OUTPUT = 3;

    private int             index;           // index in effective sql statement (not necessarily the
    // same as position below)
    private int             direction;
    private String          name;
    private int             sqlType;
    private int             position;        // zero-based index of parameter in procedure or

    // column in table

    public static Object normaliseValue(Object currVal) throws SQLException {
        if (currVal == null)
            return null;
        TypeNormaliser tn = TypeNormaliserFactory.getNormaliser(currVal.getClass());
        if (tn != null)
            currVal = tn.normalise(currVal);
        return currVal;
    }

    public DbParameterAccessor(DbParameterAccessor acc) {
        this.name = acc.name;
        this.direction = acc.direction;
        this.sqlType = acc.sqlType;
        this.type = acc.type;
        this.position = acc.position;
    }

    @SuppressWarnings("unchecked")
    public DbParameterAccessor(String name, int direction, int sqlType, Class javaType, int position) {
        this.name = name;
        this.direction = direction;
        this.sqlType = sqlType;
        this.type = javaType;
        this.position = position;
    }

    public int getSqlType() {
        return sqlType;
    }

    /**
     * One of the constants from this class declaring whether the param is
     * input, output or a return value. JDBC does not have a return value
     * parameter directions, so a new constant list had to be introduced public
     * static final int RETURN_VALUE=0; public static final int INPUT=1; public
     * static final int OUTPUT=2; public static final int INPUT_OUTPUT=3;
     */
    public int getDirection() {
        return direction;
    }

    public String getName() {
        return name;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    // really ugly, but a hack to support mysql, because it will not execute
    // inserts with a callable statement
    private CallableStatement convertStatementToCallable() throws SQLException {
        if (cs instanceof CallableStatement)
            return (CallableStatement) cs;
        throw new SQLException("This operation requires a callable statement instead of " + cs.getClass().getName());
    }

    /*******************************************/
    private PreparedStatement cs;

    public void bindTo(Fixture f, PreparedStatement cs, int ind) throws SQLException {
        this.cs = cs;
        this.fixture = f;
        this.index = ind;
        if (direction == DbParameterAccessor.OUTPUT || direction == DbParameterAccessor.RETURN_VALUE
                || direction == DbParameterAccessor.INPUT_OUTPUT) {
            convertStatementToCallable().registerOutParameter(ind, getSqlType());
        }
    }

    public void set(Object value) throws Exception {
        if (direction == OUTPUT || direction == RETURN_VALUE)
            throw new UnsupportedOperationException("Trying to set value of output parameter " + name);
        cs.setObject(index, value);
    }

    public Object get() throws IllegalAccessException, InvocationTargetException {
        try {
            if (direction == INPUT)
                throw new UnsupportedOperationException("Trying to get value of input parameter " + name);
            return normaliseValue(convertStatementToCallable().getObject(index));
        } catch (SQLException sqle) {
            throw new InvocationTargetException(sqle);
        }
    }

    /**
     * Zero-based column or parameter position in a query, table or stored proc
     */
    public int getPosition() {
        return position;
    }

}
