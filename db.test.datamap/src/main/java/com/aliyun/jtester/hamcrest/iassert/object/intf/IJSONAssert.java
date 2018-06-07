package com.aliyun.jtester.hamcrest.iassert.object.intf;

import com.aliyun.jtester.hamcrest.iassert.common.intf.IAssert;
import com.aliyun.jtester.hamcrest.iassert.common.intf.IAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.JSONAssert;

public interface IJSONAssert extends IAssert<Object, JSONAssert> {
	/**
	 * json对象是集合
	 * 
	 * @return
	 */
	ICollectionAssert isJSONArray();

	/**
	 * json对象是key-value对象
	 * 
	 * @return
	 */
	IMapAssert isJSONMap();

	/**
	 * json对象是个简单对象
	 * 
	 * @return
	 */
	IStringAssert isSimple();
}
