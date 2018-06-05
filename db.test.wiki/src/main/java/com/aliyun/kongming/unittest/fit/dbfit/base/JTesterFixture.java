package com.aliyun.kongming.unittest.fit.dbfit.base;

import com.aliyun.kongming.unittest.fit.dbfit.HasMarkedException;

import fit.Fixture;
import fit.Parse;

public class JTesterFixture extends Fixture {
    /**
     * 过滤掉已经标记过的异常<br>
     * 这样做是为了异常定位更加正确
     */
    @Override
    public void exception(Parse cell, Throwable exception) {
        if (exception instanceof HasMarkedException) {
            return;
        }
        if (cell != null) {
            super.exception(cell, exception);
            return;
        }
        if (exception instanceof RuntimeException) {
            throw (RuntimeException) exception;
        } else {
            throw new RuntimeException(exception);
        }
    }
}
