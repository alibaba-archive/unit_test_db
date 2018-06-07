package com.aliyun.jtester.module.database.environment.types;

import com.aliyun.jtester.module.database.utility.DataSourceType;
import com.aliyun.jtester.module.database.environment.BaseEnvironment;
import com.aliyun.jtester.module.database.utility.DataSourceType;

/**
 * Encapsulates support for the Derby database (also known as JavaDB). Operates
 * in Client mode.
 * 
 */
public class DerbyEnvironment extends BaseEnvironment {
	public DerbyEnvironment(String dataSourceName) {
		super(DataSourceType.DERBYDB, dataSourceName);
		this.typeMap = null;// TODO
	}

	public String getFieldQuato() {
		return "";
	}
}
