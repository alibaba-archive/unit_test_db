package com.aliyun.kongming.unittest.fit;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;


import com.aliyun.kongming.unittest.fit.util.reflect.ReflectUtil;
import fitlibrary.batch.testRun.FitLibraryTestEngine;
import fitlibrary.batch.trinidad.TestDescriptor;
import fitlibrary.batch.trinidad.TestResult;

public class JTesterFitLibraryTestEngine extends FitLibraryTestEngine {
    /**
     * 禁止把log输出和system输出重定向到结果页面
     */
    @Override
    public TestResult runTest(TestDescriptor test) {
        OutputStream tempOut = new ByteArrayOutputStream();
        OutputStream tempErr = new ByteArrayOutputStream();
        Object o = ReflectUtil.invoke(FitLibraryTestEngine.class, this, "runTest", test, tempOut, tempErr);
        return (TestResult) o;
    }
}
