package com.aliyun.kongming.unittest.fit.util.database;

import java.lang.reflect.Method;

import org.unitils.core.TestListener;
import org.unitils.database.DatabaseModule;

public class JTesterDatabaseModule extends DatabaseModule {
    @Override
    public TestListener getTestListener() {
        return new JtesterDatabaseTestListener();
    }

    /**
     * The {@link TestListener} for this module
     */
    protected class JtesterDatabaseTestListener extends DatabaseTestListener {
        /**
         * 拦截测试方法提交事务时，由于spring事务管理回滚事务导致unitls抛出UnexpectedRollbackException异常
         * <br>
         * 从而导致测试失败，而后面的测试也无法继续
         */
        @Override
        public void afterTestTearDown(Object testObject, Method testMethod) {
            try {
                super.afterTestTearDown(testObject, testMethod);
            } catch (RuntimeException e) {
                throw e;
            }
        }
    }
}
