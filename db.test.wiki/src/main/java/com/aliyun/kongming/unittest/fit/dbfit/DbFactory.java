package com.aliyun.kongming.unittest.fit.dbfit;

import com.aliyun.kongming.unittest.fit.dbfit.environment.DBEnvironment;
import com.aliyun.kongming.unittest.fit.dbfit.environment.DerbyEnvironment;
import com.aliyun.kongming.unittest.fit.dbfit.environment.MySqlEnvironment;
import com.aliyun.kongming.unittest.fit.util.database.DatabaseType;

public class DbFactory {
    private static DbFactory instance = null;

    public static DbFactory instance() {
        if (instance == null) {
            instance = new DbFactory();
        }
        return instance;
    }

    public DBEnvironment factory() {
        switch (databaseType) {
            case MYSQL:
                return new MySqlEnvironment();
            case DERBYDB:
                return new DerbyEnvironment();
            default:
                throw new RuntimeException("unsupport database type," + databaseType.getDriveClass());
        }
    }

    private DatabaseType databaseType = null;

    private DbFactory() {
        this.databaseType = DatabaseType.type();
    }

    public String getDataSource() {
        return databaseType.getConnUrl();
    }

    public String getDbUserName() {
        return databaseType.getUserName();
    }

    public String getDbPassword() {
        return databaseType.getUserPass();
    }

    public String getDriverName() {
        return databaseType.getDriveClass();
    }

    /**
     * "org.apache.derby.jdbc.EmbeddedDriver" <br>
     * "org.apache.derby.jdbc.ClientDriver" <br>
     * "com.ibm.db2.jcc.DB2Driver"<br>
     * "oracle.jdbc.OracleDriver" <br>
     * "com.microsoft.sqlserver.jdbc.SQLServerDriver"<br>
     * "com.mysql.jdbc.Driver"
     */
}
