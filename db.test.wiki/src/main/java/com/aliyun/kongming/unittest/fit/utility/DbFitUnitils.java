package com.aliyun.kongming.unittest.fit.utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.unitils.core.ModulesRepository;
import org.unitils.core.Unitils;

public class DbFitUnitils {
    private final static Map<Long, Boolean> isSpringTransactionMap  = new ConcurrentHashMap<Long, Boolean>();

    private final static Map<Long, Boolean> isTransactionEnabledMap = new ConcurrentHashMap<Long, Boolean>();

    public static Connection getConnection(DataSource dataSource) throws SQLException {
        ModulesRepository modulesRepository = Unitils.getInstance().getModulesRepository();
        Connection connection = null;
        //		if (modulesRepository.isModuleEnabled(ModuleClazz.springModule())) {
        //			connection = JTesterSpringUnitils.getConnection(dataSource);
        //			setSpringTransaction(connection != null);
        //		}
        if (connection == null) {
            connection = dataSource.getConnection();
            setSpringTransaction(connection != null);
        }
        return connection;
    }

    private static void setSpringTransaction(boolean isSpringTransaction) {
        long currThread = Thread.currentThread().getId();
        isSpringTransactionMap.put(currThread, isSpringTransaction);
    }

    public static boolean isSpringTransaction() {
        long currThread = Thread.currentThread().getId();
        Boolean isSpringTransaction = isSpringTransactionMap.get(currThread);
        return isSpringTransaction == null ? false : isSpringTransaction;
    }

    public static void setTransactionEnabled(boolean isTransactionEnabled) {
        long currThread = Thread.currentThread().getId();
        isTransactionEnabledMap.put(currThread, isTransactionEnabled);
    }

    public static boolean isTransactionEnabled() {
        long currThread = Thread.currentThread().getId();
        Boolean isTransactionEnabled = isTransactionEnabledMap.get(currThread);
        return isTransactionEnabled == null ? false : isTransactionEnabled;
    }
}
