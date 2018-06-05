package com.aliyun.kongming.unittest.fit.util.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unitils.core.dbsupport.DbSupport;
import org.unitils.core.dbsupport.DerbyDbSupport;
import org.unitils.core.dbsupport.HsqldbDbSupport;
import org.unitils.core.dbsupport.MySqlDbSupport;

import com.aliyun.kongming.unittest.fit.util.ConfigUtil;
import com.aliyun.kongming.unittest.fit.util.StringHelper;

public enum DatabaseType {
    /**
     * H2Db
     */
    H2DB("org.h2.Driver", "org.hibernate.dialect.H2Dialect", "public", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "", "public") {
        @Override
        public DbSupport getDbSupport() {
            return new H2DbSupport();
        }

        @Override
        public boolean autoExport() {
            return true;
        }

        @Override
        public String getDbUnitDialect() {
            return "h2db";
        }

        @Override
        public boolean isMemoryDB() {
            return true;
        }
    },
    /**
     * HsqlDb
     */
    HSQLDB("org.hsqldb.jdbcDriver", "org.hibernate.dialect.HSQLDialect", "public", "jdbc:hsqldb:mem:test", "sa", "", "public") {
        @Override
        public DbSupport getDbSupport() {
            return new HsqldbDbSupport();
        }

        @Override
        public boolean autoExport() {
            return true;
        }

        @Override
        public String getDbUnitDialect() {
            return "hsqldb";
        }

        @Override
        public boolean isMemoryDB() {
            return true;
        }
    },
    MYSQL() {
        @Override
        public DbSupport getDbSupport() {
            return new MySqlDbSupport();
        }

        @Override
        public String getDbUnitDialect() {
            return "mysql";
        }
    },

    DERBYDB() {
        @Override
        public DbSupport getDbSupport() {
            return new DerbyDbSupport();
        }

        @Override
        public String getDbUnitDialect() {
            return "derby";
        }

    },

    UNSUPPORT() {
        @Override
        public DbSupport getDbSupport() {
            throw new RuntimeException("unsupport database type");
        }

        @Override
        public String getDbUnitDialect() {
            throw new RuntimeException("unsupport database type");
        }
    };

    private static Log log               = LogFactory.getLog(DatabaseType.class);

    private String     clazz             = null;

    private String     hibernate_dialect = null;

    private String     url               = null;

    private String     user              = null;

    private String     pass              = null;

    private String     infoSchema        = null;

    private String     schemas           = null;

    private DatabaseType() {
    }

    private DatabaseType(String clazz, String hibernate_dialect, String infoSchema, String url, String user,
                         String pass, String schema) {
        this.clazz = clazz;
        this.hibernate_dialect = hibernate_dialect;
        this.infoSchema = infoSchema;
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.schemas = schema;
    }

    public String getDriveClass() {
        return this.clazz == null ? ConfigUtil.driverClazzName() : this.clazz;
    }

    public String getHibernateDialect() {
        return this.hibernate_dialect;
    }

    public abstract String getDbUnitDialect();

    public abstract DbSupport getDbSupport();

    public String getInfoSchema() {
        return this.infoSchema;
    }

    public String getConnUrl() {
        return this.url == null ? ConfigUtil.databaseUrl() : this.url;
    }

    public String getUserName() {
        return this.user == null ? ConfigUtil.databaseUserName() : this.user;
    }

    public String getUserPass() {
        return this.pass == null ? ConfigUtil.databasePassword() : this.pass;
    }

    public String getSchemas() {
        return this.schemas == null ? ConfigUtil.property("database.schemaNames") : this.schemas;
    }

    private static DatabaseType type = null;

    /**
     * 根据unitils.properties中的配置项database.type获得DataSourceType<br>
     * 此配置只对内存数据库有效
     * 
     * @return
     */
    public static DatabaseType type() {
        if (type != null) {
            return type;
        }
        try {
            String typeS = ConfigUtil.databaseType();
            if (StringHelper.isBlankOrNull(typeS)) {
                throw new RuntimeException("please config property 'database.type'");
            }
            type = DatabaseType.valueOf(typeS.toUpperCase());
            ConfigUtil.setDatabaseDialect(type);
            if (type.isMemoryDB()) {
                ConfigUtil.setMemoryDbConfig(type);
            }
            return type;
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException("unknown database type", e);
        }
    }

    public boolean autoExport() {
        return ConfigUtil.autoExport();
    }

    public boolean isMemoryDB() {
        return false;
    }
}
