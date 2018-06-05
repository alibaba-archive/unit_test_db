package org.jtester.hamcrest.iassert.common.intf;

import ext.jtester.hamcrest.Matcher;

/**
 * jtester断言超基类接口
 * 
 * @author darui.wudr
 * 
 * @param <T>
 * @param <E>
 */
@SuppressWarnings("rawtypes")
public interface IAssert<T, E extends IAssert> extends Matcher<T> {

}
