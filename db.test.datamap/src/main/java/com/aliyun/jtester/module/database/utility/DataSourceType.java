package com.aliyun.jtester.module.database.utility;

import com.aliyun.jtester.module.database.exception.UnConfigDataBaseTypeException;
import com.aliyun.jtester.tools.commons.StringHelper;

public enum DataSourceType {
    /**
     * H2Db<br>
     * "org.hibernate.dialect.H2Dialect", "public", "public"
     */
    H2DB() {

        @Override
        public boolean isMemoryDB() {
            return true;
        }
    },
    /**
     * HsqlDb<br>
     * "org.hibernate.dialect.HSQLDialect", "public", "public"
     */
    HSQLDB() {

        @Override
        public boolean isMemoryDB() {
            return true;
        }
    },
    MYSQL(),

    DERBYDB(),

    DB2(),

    UNSUPPORT();

    private String hibernate_dialect = null;

    private String infoSchema        = null;

    private DataSourceType() {
    }

    public String getHibernateDialect() {
        return this.hibernate_dialect;
    }

    public String getInfoSchema() {
        return this.infoSchema;
    }

    public boolean isMemoryDB() {
        return false;
    }

    /**
     * 根据配置查找对应的数据库类型<br>
     * type=null || "",表示配置文件中设置的默认数据库
     * 
     * @param type
     * @return
     */
    public static DataSourceType databaseType(final String type) {
        String _type = type;
        if (StringHelper.isBlankOrNull(_type)) {
            throw new UnConfigDataBaseTypeException("please config property 'database.type'");
        }
        try {
            DataSourceType dbType = DataSourceType.valueOf(_type.toUpperCase());

            return dbType;
        } catch (Throwable e) {
            throw new RuntimeException("unknown database type", e);
        }
    }
}
