package com.aliyun.jtester.module.database;

import com.aliyun.jtester.module.database.dbop.IDBOperator;

public interface IDatabase {

    final IDBOperator db = IDBInitial.initDBOperator();
}
