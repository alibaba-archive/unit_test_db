package com.aliyun.jtester.hamcrest.iassert.common.intf;

import com.aliyun.ext.jtester.hamcrest.Matcher;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IStringAssert;

/**
 * the basic asserting matcher
 * 
 * @author darui.wudr
 * @param <T>
 * @param <E>
 */
@SuppressWarnings("rawtypes")
public interface IBaseAssert<T, E extends IAssert> extends Matcher<T>, IAssert<T, E> {
    /**
     * 对象的toString等于期望值
     * 
     * @param expected

     */
    E eqToString(String expected);

    /**
     * 对象的toString符合断言器判断
     * 
     * @param matcher

     */
    E eqToString(IStringAssert matcher);

    /**
     * 断言对象等于期望的值<br>
     * same as method "isEqualTo(T)"
     * 
     * @param expected 期望值

     */
    E eq(T expected);

    /**
     * 断言对象等于期望的值<br>
     * same as method "eq(T)"
     * 
     * @param expected 期望值

     */
    E isEqualTo(T expected);

    /**
     * 断言对象等于期望的值
     * 
     * @param message 错误信息
     * @param expected 期望值

     */
    E isEqualTo(String message, T expected);

    /**
     * 断言对象不等于期望的值
     * 
     * @param expected 期望值

     */
    E notEqualTo(T expected);

    /**
     * 断言对象可以在期望值里面找到
     * 
     * @param values 期望值

     */
    E in(T... values);

    /**
     * 断言对象不可以在期望值里面找到
     * 
     * @param values 期望值

     */
    E notIn(T... values);

    /**
     * 断言对象的类型等于期望类型
     * 
     * @param claz 期望类型

     */
    E clazIs(Class claz);

    /**
     * 断言对象的类型是期望类型的子类
     * 
     * @param claz

     */
    E clazIsSubFrom(Class claz);

    /**
     * 断言对象符合任一个对象行为定义<br>
     * same as "matchAny(...)"
     * 
     * @param matcher 对象行为定义，具体定义参见 Matcher
     */
    E any(E matcher, E... matchers);

    /**
     * 断言对象符合所有的对象行为定义<br>
     * same as "matchAll(Matcher...)"
     * 
     */
    E all(E matcher, E... matchers);

    /**
     * 断言对象不符合matcher所定义的行为
     * 
     * @param matcher 对象行为定义，具体定义参见 Matcher

     */
    E not(E matcher);

    /**
     * 没有一个断言器可以匹配实际对象，即每一个断言器都匹配失败
     * 
     */
    E notAny(Matcher matcher, Matcher... matchers);

    /**
     * 不是所有的断言器都可以匹配实际对象，即至少有一个断言器失败
     * 
     */
    E notAll(Matcher matcher, Matcher... matchers);

    /**
     * 断言对象和期望值是同一个对象
     * 
     * @param value 期望值
     */
    E same(T value);

    /**
     * 断言对象可以使任意的值
     *
     */
    E any();

    /**
     * 断言对象值等于null
     *
     */
    E isNull();

    /**
     * 断言对象值等于null
     */
    E isNull(String message);

    /**
     * 断言对象值不等于null
     *
     */
    E notNull();

    /**
     * 断言对象值不等于null
     *
     */
    E notNull(String message);
}
