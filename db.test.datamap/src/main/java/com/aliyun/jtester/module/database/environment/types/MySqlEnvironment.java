package com.aliyun.jtester.module.database.environment.types;

import com.aliyun.jtester.module.database.utility.DataSourceType;
import com.aliyun.jtester.module.database.environment.BaseEnvironment;
import com.aliyun.jtester.module.database.environment.typesmap.MySQLTypeMap;
import com.aliyun.jtester.module.database.utility.DataSourceType;

public class MySqlEnvironment extends BaseEnvironment {
	public MySqlEnvironment(String dataSourceName) {
		super(DataSourceType.MYSQL, dataSourceName);
		typeMap = new MySQLTypeMap();
	}

	public String getFieldQuato() {
		return "`";
	}
}
