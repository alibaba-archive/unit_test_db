package com.aliyun.kongming.unittest.fit.dbfit.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataRow {
    private Map<String, Object> values = new HashMap<String, Object>();

    public Set<String> getColumnNames() {
        return values.keySet();
    }

    public DataRow(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            values.put(NameNormaliser.normaliseName(rsmd.getColumnName(i)),
                    DbParameterAccessor.normaliseValue(rs.getObject(i)));
        }
    }

    public String getStringValue(String columnName) {
        Object o = values.get(columnName);
        if (o == null)
            return "null";
        return o.toString();
    }

    /**
     * 返回整列值
     * 
     * @return
     */
    public List<String> getStringValues() {
        List<String> strs = new ArrayList<String>();
        for (Object o : values.values()) {
            strs.add(o == null ? "null" : o.toString());
        }
        return strs;
    }

    public boolean matches(Map<String, Object> keyProperties) {
        for (String key : keyProperties.keySet()) {
            String normalisedKey = NameNormaliser.normaliseName(key);
            if (!values.containsKey(normalisedKey))
                return false;
            if (!equals(keyProperties.get(key), values.get(normalisedKey)))
                return false;
        }
        return true;
    }

    private boolean equals(Object a, Object b) {
        if (a == null && b == null)
            return true;
        if (a == null || b == null)
            return false;
        return a.equals(b);
    }

    public Object get(String key) {
        String normalisedKey = NameNormaliser.normaliseName(key);
        return values.get(normalisedKey);
    }

    private boolean processed = false;

    public void markProcessed() {
        processed = true;
    }

    public boolean isProcessed() {
        return processed;
    }

}
