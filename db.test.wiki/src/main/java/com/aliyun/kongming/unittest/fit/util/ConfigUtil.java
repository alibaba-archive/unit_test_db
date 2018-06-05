package com.aliyun.kongming.unittest.fit.util;

import java.util.Properties;

import org.unitils.core.Unitils;

import com.aliyun.kongming.unittest.fit.util.database.DatabaseType;

public class ConfigUtil {
    public static final String     dbexport_auto                      = "dbexport.auto";

    public static final String     database_type                      = "database.type";

    public static final String     PROPKEY_DATASOURCE_DRIVERCLASSNAME = "database.driverClassName";

    public static final String     PROPKEY_DATASOURCE_URL             = "database.url";

    public static final String     PROPKEY_DATASOURCE_USERNAME        = "database.userName";

    public static final String     PROPKEY_DATASOURCE_PASSWORD        = "database.password";

    public static final String     DBMAINTAINER_DISABLECONSTRAINTS    = "dbMaintainer.disableConstraints.enabled";

    public static final String     SPRING_DATASOURCE_NAME             = "spring.datasource.name";

    public static final String     CONNECT_ONLY_TESTDB                = "database.only.testdb.allowing";

    public static final Properties unitilscfg                         = Unitils.getInstance().getConfiguration();

    public static String property(String key) {
        return unitilscfg.getProperty(key);
    }

    public static boolean boolProperty(String key) {
        String prop = unitilscfg.getProperty(key);
        return "true".equalsIgnoreCase(prop);
    }

    public static boolean initJMockit() {
        return ConfigUtil.boolProperty("jmockit.init");
    }

    public static String driverClazzName() {
        return unitilscfg.getProperty(PROPKEY_DATASOURCE_DRIVERCLASSNAME);
    }

    public static String databaseUrl() {
        return unitilscfg.getProperty(PROPKEY_DATASOURCE_URL);
    }

    public static String databaseUserName() {
        return unitilscfg.getProperty(PROPKEY_DATASOURCE_USERNAME);
    }

    public static String databasePassword() {
        return unitilscfg.getProperty(PROPKEY_DATASOURCE_PASSWORD);
    }

    public static boolean doesDisableConstraints() {
        String disableConstraints = unitilscfg.getProperty(DBMAINTAINER_DISABLECONSTRAINTS);
        return "TRUE".equalsIgnoreCase(disableConstraints);
    }

    /**
     * 除非显示的声明了database.istest=false，否则jtester认为只能连接测试库
     * 
     * @return
     */
    public static boolean doesOnlyTestDatabase() {
        String onlytest = unitilscfg.getProperty(CONNECT_ONLY_TESTDB);
        return !"FALSE".equalsIgnoreCase(onlytest);
    }

    /**
     * 判断是否是spring的data source bean name
     * 
     * @param beanName
     * @return
     */
    public static boolean isSpringDataSourceName(String beanName) {
        String dataSourceName = unitilscfg.getProperty(SPRING_DATASOURCE_NAME);
        return beanName.equals(dataSourceName);
    }

    public static boolean autoExport() {
        String auto = unitilscfg.getProperty(dbexport_auto);
        if (auto != null && auto.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isScript() {
        String script = unitilscfg.getProperty("dbexport.script");
        if (script != null && script.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static String databaseType() {
        String type = System.getProperty(database_type);// from vm
        if (!StringHelper.isBlankOrNull(type)) {
            return type;
        }
        type = unitilscfg.getProperty(database_type);// from property
        if (type == null) {
            type = "unsupport";
        }
        return type;
    }

    /**
     * dbfit测试结果文件输出目录
     * 
     * @return
     */
    public static String dbfitDir() {
        String dir = unitilscfg.getProperty("dbfit.dir");
        return StringHelper.isBlankOrNull(dir) ? "target/dbfit" : dir;
    }

    public static Properties config() {
        return unitilscfg;
    }

    public static void disableDbMaintain() {
        // disable dbmaintainer properties
        unitilscfg.setProperty("updateDataBaseSchema.enabled", "false");
        unitilscfg.setProperty("dbMaintainer.dbVersionSource.autoCreateVersionTable", "false");
    }

    public static void setMemoryDbConfig(DatabaseType type) {
        unitilscfg.setProperty("database.driverClassName", type.getDriveClass());
        unitilscfg.setProperty("database.url", type.getConnUrl());
        unitilscfg.setProperty("database.userName", type.getUserName());
        unitilscfg.setProperty("database.password", type.getUserPass());
        unitilscfg.setProperty("database.schemaNames", type.getSchemas());
    }

    public static void setDatabaseDialect(DatabaseType type) {
        unitilscfg.setProperty("database.dialect", type.getDbUnitDialect());
    }
}
