package com.aliyun.kongming.unittest.fit.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE, METHOD })
@Retention(RUNTIME)
public @interface DbFit {
    /**
     * 单元测试前运行的wiki文件
     * 
     * @return
     */
    String[] when() default {};

    /**
     * 单元测试后校验的wiki文件
     * 
     * @return
     */
    String[] then() default {};

    /**
     * 设置fit wiki参数
     * 
     * @return
     */
    FitVar[] vars() default {};

}
