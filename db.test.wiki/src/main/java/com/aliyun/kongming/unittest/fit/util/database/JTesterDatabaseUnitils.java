package com.aliyun.kongming.unittest.fit.util.database;

import java.lang.reflect.Method;

import org.unitils.core.ModulesRepository;
import org.unitils.core.Unitils;
import org.unitils.database.DatabaseModule;
import org.unitils.database.DatabaseUnitils;

public class JTesterDatabaseUnitils extends DatabaseUnitils {
    private final static ModulesRepository     modulesRepository = Unitils.getInstance().getModulesRepository();

    private final static Class<DatabaseModule> moduleClazz       = DatabaseModule.class;

    public static boolean isDisabledTransaction(Object testObject, Method testMethod) {
        if (modulesRepository.isModuleEnabled(moduleClazz) == false) {
            return false;
        } else {
            DatabaseModule module = modulesRepository.getModuleOfType(moduleClazz);
            return module.isTransactionsEnabled(testObject, testMethod);
        }
    }
}
