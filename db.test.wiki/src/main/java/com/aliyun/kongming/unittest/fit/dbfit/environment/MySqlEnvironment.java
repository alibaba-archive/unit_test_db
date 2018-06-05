package com.aliyun.kongming.unittest.fit.dbfit.environment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.aliyun.kongming.unittest.fit.dbfit.environment.typesmap.MySQLTypeMap;
import com.aliyun.kongming.unittest.fit.dbfit.util.DbParameterAccessor;
import com.aliyun.kongming.unittest.fit.dbfit.util.NameNormaliser;

public class MySqlEnvironment extends AbstractDbEnvironment {
    public boolean supportsOuputOnInsert() {
        return false;
    }

    private static String  paramNamePattern = "@([A-Za-z0-9_]+)";
    private static Pattern paramRegex       = Pattern.compile(paramNamePattern);

    public Pattern getParameterPattern() {
        return paramRegex;
    }

    // mysql jdbc driver does not support named parameters - so just map them
    // to standard jdbc question marks
    protected String parseCommandText(String commandText) {
        commandText = commandText.replaceAll(paramNamePattern, "?");
        return super.parseCommandText(commandText);
    }

    public Map<String, DbParameterAccessor> getAllColumns(String tableOrViewName) throws SQLException {
        String[] qualifiers = NameNormaliser.normaliseName(tableOrViewName).split("\\.");
        String qry = " select column_name, data_type, character_maximum_length "
                + "	as direction from information_schema.columns where ";
        if (qualifiers.length == 2) {
            qry += " lower(table_schema)=? and lower(table_name)=? ";
        } else {
            qry += " (table_schema=database() and lower(table_name)=?)";
        }
        qry += " order by ordinal_position";
        return readIntoParams(qualifiers, qry);
    }

    private Map<String, DbParameterAccessor> readIntoParams(String[] queryParameters, String query)
            throws SQLException {
        PreparedStatement dc = currentConnection.prepareStatement(query);
        for (int i = 0; i < queryParameters.length; i++) {
            dc.setString(i + 1, NameNormaliser.normaliseName(queryParameters[i]));
        }
        ResultSet rs = dc.executeQuery();
        Map<String, DbParameterAccessor> allParams = new HashMap<String, DbParameterAccessor>();
        int position = 0;
        while (rs.next()) {
            String paramName = rs.getString(1);
            if (paramName == null)
                paramName = "";
            String dataType = rs.getString(2);
            DbParameterAccessor dbp = new DbParameterAccessor(paramName, DbParameterAccessor.INPUT,
                    getSqlType(dataType), getJavaClass(dataType), position++);
            allParams.put(NameNormaliser.normaliseName(paramName), dbp);
        }
        rs.close();
        return allParams;
    }

    private static String normaliseTypeName(String dataType) {
        return dataType.toUpperCase().trim();
    }

    private static int getSqlType(String dataType) {
        dataType = normaliseTypeName(dataType);
        Integer type = MySQLTypeMap.sql.get(dataType);
        if (type != null) {
            return type;
        } else {
            throw new UnsupportedOperationException("Type " + dataType + " is not supported");
        }
    }

    public Class<?> getJavaClass(String dataType) {
        dataType = normaliseTypeName(dataType);
        Class<?> clazz = MySQLTypeMap.java.get(dataType);
        if (clazz != null) {
            return clazz;
        } else {
            throw new UnsupportedOperationException("Type " + dataType + " is not supported");
        }
    }

    public Map<String, DbParameterAccessor> getAllProcedureParameters(String procName) throws SQLException {

        String[] qualifiers = NameNormaliser.normaliseName(procName).split("\\.");
        String qry = " select type,param_list,returns from mysql.proc where ";
        if (qualifiers.length == 2) {
            qry += " lower(db)=? and lower(name)=? ";
        } else {
            qry += " (db=database() and lower(name)=?)";
        }

        PreparedStatement dc = currentConnection.prepareStatement(qry);
        for (int i = 0; i < qualifiers.length; i++) {
            dc.setString(i + 1, NameNormaliser.normaliseName(qualifiers[i]));
        }
        ResultSet rs = dc.executeQuery();
        if (!rs.next()) {
            throw new SQLException("Unknown procedure " + procName);
        }
        Map<String, DbParameterAccessor> allParams = new HashMap<String, DbParameterAccessor>();
        String type = rs.getString(1);
        String paramList = rs.getString(2);
        String returns = rs.getString(3);
        rs.close();
        int position = 0;
        for (String param : paramList.split(",")) {
            StringTokenizer s = new StringTokenizer(param.trim().toLowerCase(), " ()");
            String token = s.nextToken();
            int direction = DbParameterAccessor.INPUT;

            if (token.equals("inout")) {
                direction = DbParameterAccessor.INPUT_OUTPUT;
                token = s.nextToken();
            }
            if (token.equals("in")) {
                token = s.nextToken();
            } else if (token.equals("out")) {
                direction = DbParameterAccessor.OUTPUT;
                token = s.nextToken();
            }
            String paramName = token;
            String dataType = s.nextToken();

            DbParameterAccessor dbp = new DbParameterAccessor(paramName, direction, getSqlType(dataType),
                    getJavaClass(dataType), position++);
            allParams.put(NameNormaliser.normaliseName(paramName), dbp);
        }
        if ("FUNCTION".equals(type)) {
            StringTokenizer s = new StringTokenizer(returns.trim().toLowerCase(), " ()");
            String dataType = s.nextToken();
            allParams.put("", new DbParameterAccessor("", DbParameterAccessor.RETURN_VALUE, getSqlType(dataType),
                    getJavaClass(dataType), -1));
        }
        return allParams;
    }
}
