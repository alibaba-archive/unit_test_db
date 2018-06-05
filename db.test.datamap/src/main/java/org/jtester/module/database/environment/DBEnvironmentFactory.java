package org.jtester.module.database.environment;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jtester.module.database.environment.types.DerbyEnvironment;
import org.jtester.module.database.environment.types.MySqlEnvironment;
import org.jtester.module.database.utility.DataSourceType;
import org.jtester.tools.commons.StringHelper;
import org.unitils.core.Unitils;

public final class DBEnvironmentFactory {
    public static final Properties            unitilscfg   = Unitils.getInstance().getConfiguration();
    private static Map<String, DBEnvironment> environments = new HashMap<String, DBEnvironment>();

    private static DBEnvironment newInstance(DataSourceType dataSourceType, String dataSourceName) {
        if (dataSourceType == null) {
            throw new RuntimeException("DatabaseType can't be null.");
        }
        switch (dataSourceType) {
            case MYSQL:
                return new MySqlEnvironment(dataSourceName);
            case DERBYDB:
                return new DerbyEnvironment(dataSourceName);
            default:
                throw new RuntimeException("unsupport database type:" + dataSourceType.name());
        }
    }

    /**
     * 获取默认的数据库连接识别码
     * 
     * @return
     */
    public static DBEnvironment getDefaultDBEnvironment() {
        DBEnvironment enviroment = getDBEnvironment(null);
        return enviroment;
    }

    /**
     * 从jtester配置中取指定的数据源
     * 
     * @param dataSourceName
     * @return
     */
    public static DBEnvironment getDBEnvironment(String dataSourceName) {
        DataSourceType dataSourceType = DataSourceType.MYSQL;
        if (StringHelper.isBlankOrNull(dataSourceName)) {
            dataSourceName = "";
            String dialect = unitilscfg.getProperty("database.dialect." + dataSourceName);
            if (dialect != null) {
                dataSourceType = DataSourceType.databaseType(dialect);
            }
        }
        DBEnvironment dbEnvironment = newInstance(dataSourceType, dataSourceName);
        environments.put(dataSourceName, dbEnvironment);
        return dbEnvironment;
    }

    /**
     * 当前正在使用的数据库类型
     */
    private static DBEnvironment currDBEnvironment = null;

    /**
     * 获取当前的数据库处理环境
     * 
     * @return
     */
    public static DBEnvironment getCurrentDBEnvironment() {
        if (currDBEnvironment == null) {
            currDBEnvironment = getDefaultDBEnvironment();
        }
        return currDBEnvironment;
    }

    /**
     * 切换数据库环境<br>
     * 先关闭上一个数据库连接，再设置当前数据库连接
     * 
     * @throws SQLException
     */
    public static void changeDBEnvironment(DBEnvironment environment) {
        // if (currDBEnvironment != null &&
        // currDBEnvironment.equals(environment) == false) {
        // currDBEnvironment.close();
        // }
        currDBEnvironment = environment;
    }

}
