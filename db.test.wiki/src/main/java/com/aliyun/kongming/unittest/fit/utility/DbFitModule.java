package com.aliyun.kongming.unittest.fit.utility;

import static org.unitils.util.AnnotationUtils.getMethodOrClassLevelAnnotation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.unitils.core.Module;
import org.unitils.core.TestListener;

import com.aliyun.kongming.unittest.fit.annotations.DbFit;
import com.aliyun.kongming.unittest.fit.annotations.FitVar;
import com.aliyun.kongming.unittest.fit.dbfit.DatabaseFixture;
import com.aliyun.kongming.unittest.fit.dbfit.DbFitRunner;
import com.aliyun.kongming.unittest.fit.dbfit.util.SymbolUtil;
import com.aliyun.kongming.unittest.fit.util.database.JTesterDatabaseUnitils;

public class DbFitModule implements Module {
    public void init(Properties configuration) {
        ;
    }

    public void afterInit() {
        ;
    }

    public TestListener getTestListener() {
        return new DbFitTestListener();
    }

    protected class DbFitTestListener extends TestListener {
        /**
         * dbfit放在beforetestmethod中初始化，而不是放在beforeTestSetUp中初始化的原因是：<br>
         * beforeTestSetUp中的异常会导致后面所有的测试方法broken <br>
         * <br>
         * {@inheritDoc}
         */
        @Override
        public void beforeTestMethod(Object testObject, Method testMethod) {

            boolean isTransactionEnabled = JTesterDatabaseUnitils.isDisabledTransaction(testObject, testMethod);
            DbFitUnitils.setTransactionEnabled(isTransactionEnabled);

            Class<?> claz = testObject.getClass();
            DbFit dbfit = getMethodOrClassLevelAnnotation(DbFit.class, testMethod, claz);
            if (dbfit != null) {
                Map<String, String> symbols = exactFitVars(dbfit);
                SymbolUtil.setSymbol(symbols);

                String[] wikis = dbfit.when();
                runDbWiki(claz, wikis);
            }
        }

        @Override
        public void afterTestMethod(Object testObject, Method testMethod, Throwable testThrowable) {

            Class<?> claz = testObject.getClass();
            DbFit dbfit = getMethodOrClassLevelAnnotation(DbFit.class, testMethod, claz);
            if (dbfit != null) {
                String[] wikis = dbfit.then();
                runDbWiki(claz, wikis);

                SymbolUtil.clearSymbols();
                DatabaseFixture.endTransactional();
            }
        }
    }

    /**
     * 获取DbFit中设置的参数列表
     * 
     * @param dbFit
     * @return
     */
    public Map<String, String> exactFitVars(DbFit dbFit) {
        FitVar[] vars = dbFit.vars();
        Map<String, String> symbols = new HashMap<String, String>();
        for (FitVar var : vars) {
            symbols.put(var.key(), var.value());
        }
        return symbols;
    }

    public void runDbWiki(Class<?> testClazz, String[] wikis) {
        for (String wiki : wikis) {
            DbFitRunner.runDbFit(testClazz, wiki);
        }
    }
}
