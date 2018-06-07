package com.aliyun.jtester.module.database.dbop;

import java.util.List;

import com.aliyun.jtester.hamcrest.iassert.object.impl.CollectionAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.LongAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.ObjectAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.ICollectionAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.INumberAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IObjectAssert;
import com.aliyun.jtester.json.JSON;
import com.aliyun.jtester.module.ICore;
import com.aliyun.jtester.module.database.utility.DBHelper;
import com.aliyun.jtester.module.database.utility.SqlRunner;
import com.aliyun.jtester.tools.commons.StringHelper;
import com.aliyun.jtester.tools.datagen.DataSet;
import com.aliyun.jtester.tools.datagen.EmptyDataSet;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TableOp implements ITableOp {

    private String table;

    public TableOp(String table) {
        this.table = table;
        if (StringHelper.isBlankOrNull(this.table)) {
            throw new RuntimeException("the table name can't be null.");
        }
    }

    public ITableOp clean() {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            String sql = "delete from " + table;
            SqlRunner.instance.execute(sql);
            return this;
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public void commit() {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            SqlRunner.instance.commit();
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public void rollback() {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            SqlRunner.instance.rollback();
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public ITableOp insert(ICore.DataMap data, ICore.DataMap... more) {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            InsertOp.insertNoException(table, data);
            for (ICore.DataMap map : more) {
                InsertOp.insertNoException(table, map);
            }
            return this;
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public ITableOp insert(String json, String... more) {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            ICore.DataMap map = JSON.toObject(json, ICore.DataMap.class);
            InsertOp.insertNoException(table, map);
            for (String item : more) {
                map = JSON.toObject(item, ICore.DataMap.class);
                InsertOp.insertNoException(table, map);
            }
            return this;
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public ITableOp insert(final int count, final ICore.DataMap datas) {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            DataSet ds = new EmptyDataSet();
            ds.data(count, datas);
            ds.insert(table);
            return this;
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public ITableOp insert(DataSet dataset) {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            if (dataset == null) {
                throw new RuntimeException("the insert dataset can't be null.");
            }
            dataset.insert(table);
            return this;
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public ICollectionAssert query() {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            String query = "select * from " + table;
            List list = SqlRunner.instance.queryMapList(query);
            return new CollectionAssert(list);
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public ICollectionAssert queryList(Class pojo) {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            String query = "select * from " + table;
            List list = SqlRunner.instance.queryList(query, pojo);
            return new CollectionAssert(list);
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public INumberAssert count() {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            String query = "select count(*) from " + table;
            Number number = (Number) SqlRunner.instance.query(query, Object.class);
            return new LongAssert(number.longValue());
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public IObjectAssert queryAs(Class pojo) {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            String query = "select * from " + table;
            Object o = SqlRunner.instance.query(query, pojo);
            return new ObjectAssert(o);
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public ICollectionAssert queryWhere(String where) {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            String query = "select * from " + table + " where " + where;
            List list = SqlRunner.instance.queryMapList(query);
            return new CollectionAssert(list);
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }

    public ICollectionAssert queryWhere(ICore.DataMap dataMap) {
        DBOperator.IN_DB_OPERATOR.set(true);
        try {
            StringBuilder query = new StringBuilder("select * from ");
            query.append(table).append(" ");
            String where = DBHelper.getWhereCondiction(dataMap);
            query.append(where);
            List list = SqlRunner.instance.queryMapList(query.toString(), dataMap);
            return new CollectionAssert(list);
        } finally {
            DBOperator.IN_DB_OPERATOR.set(false);
        }
    }
}
