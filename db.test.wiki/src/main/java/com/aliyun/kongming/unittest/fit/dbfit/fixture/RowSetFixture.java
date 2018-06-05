package com.aliyun.kongming.unittest.fit.dbfit.fixture;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aliyun.kongming.unittest.fit.dbfit.util.DataColumn;
import com.aliyun.kongming.unittest.fit.dbfit.util.DataRow;
import com.aliyun.kongming.unittest.fit.dbfit.util.DataTable;
import com.aliyun.kongming.unittest.fit.dbfit.util.DbTypeAdapter;
import com.aliyun.kongming.unittest.fit.dbfit.util.NameNormaliser;
import com.aliyun.kongming.unittest.fit.dbfit.util.NoMatchingRowFoundException;
import com.aliyun.kongming.unittest.fit.dbfit.util.SymbolAccessQueryBinding;

import fit.Binding;
import fit.ColumnFixture;
import fit.Fixture;
import fit.Parse;

public abstract class RowSetFixture extends ColumnFixture {

    private DataTable dt;
    // private ResultSetMetaData rsmd;
    private DataRow   currentRow;

    private class CurrentDataRowTypeAdapter extends DbTypeAdapter {
        public String key;

        @SuppressWarnings("unchecked")
        public CurrentDataRowTypeAdapter(String key, Class type) throws NoSuchMethodException {
            target = null;
            method = CurrentDataRowTypeAdapter.class.getMethod("get", new Class[] {});
            fixture = RowSetFixture.this;
            this.type = type;
            this.key = key;
        }

        public Object get() {
            return currentRow.get(key);
        }

        public Object invoke() throws IllegalAccessException {
            return get();
        }
    }

    private int findColumn(String name) throws Exception {
        // todo: implement non-key
        String normalisedName = NameNormaliser.normaliseName(name);
        for (int i = 0; i < dt.getColumns().size(); i++) {
            String colName = dt.getColumns().get(i).getName();
            if (normalisedName.equals(NameNormaliser.normaliseName(colName)))
                return i;
        }
        throw new Exception("Unknown column " + normalisedName);
    }

    // if element not 0, fixture column -> result set column index
    private String[] keyColumns;

    protected void bind(Parse heads) {
        try {
            columnBindings = new Binding[heads.size()];
            keyColumns = new String[heads.size()];
            for (int i = 0; heads != null; i++, heads = heads.more) {
                String name = heads.text();
                columnBindings[i] = new SymbolAccessQueryBinding();
                int idx = findColumn(name);
                String columnName = dt.getColumns().get(idx).getName();
                if (!name.endsWith("?"))
                    keyColumns[i] = columnName;
                columnBindings[i].adapter = new CurrentDataRowTypeAdapter(columnName,
                        getJavaClassForColumn(dt.getColumns().get(idx)));
            }
        } catch (Exception sqle) {
            exception(heads, sqle);
        }
    }

    protected abstract DataTable getDataTable() throws SQLException;

    protected abstract boolean isOrdered();

    public void doRows(Parse rows) {
        try {
            dt = getDataTable();
            super.doRows(rows);
            addSurplusRows(rows.last());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            exception(rows, sqle);
        }
    }

    public void doRow(Parse row) {
        try {
            if (isOrdered())
                currentRow = dt.findFirstUnprocessedRow();
            else
                currentRow = findMatchingRow(row);
            super.doRow(row);
            currentRow.markProcessed();
        } catch (NoMatchingRowFoundException e) {
            row.parts.addToBody(Fixture.gray(" missing"));
            wrong(row);
        }
    }

    public DataRow findMatchingRow(Parse row) throws NoMatchingRowFoundException {
        Parse columns = row.parts;
        Map<String, Object> keyMap = new HashMap<String, Object>();
        for (int i = 0; i < keyColumns.length; i++, columns = columns.more) {
            if (keyColumns[i] != null) {
                try {
                    Object value = columnBindings[i].adapter.parse(columns.text());
                    keyMap.put(keyColumns[i], value);
                } catch (Exception e) {
                    exception(columns, e);
                }
            }
        }
        return dt.findMatching(keyMap);
    }

    private void addSurplusRows(Parse rows) {
        Parse lastRow = rows;
        for (DataRow dr : dt.getUnprocessedRows()) {
            Parse newRow = new Parse("tr", null, null, null);
            lastRow.more = newRow;
            lastRow = newRow;
            try {
                currentRow = dr; // for getting
                Parse firstCell = new Parse("td", String.valueOf(columnBindings[0].adapter.invoke()), null, null);
                newRow.parts = firstCell;
                firstCell.addToBody(Fixture.gray(" surplus"));
                wrong(firstCell);
                for (int i = 1; i < columnBindings.length; i++) {
                    Parse nextCell = new Parse("td", String.valueOf(columnBindings[i].adapter.invoke()), null, null);
                    firstCell.more = nextCell;
                    firstCell = nextCell;
                }
            } catch (Exception e) {
                exception(newRow, e);
            }
        }
    }

    protected Class<?> getJavaClassForColumn(DataColumn col) {
        try {
            return Class.forName(col.getJavaClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
