package com.aliyun.jtester.hamcrest.iassert.object.intf;

import com.aliyun.jtester.hamcrest.iassert.common.intf.IBaseAssert;
import com.aliyun.jtester.hamcrest.iassert.common.intf.IListAssert;
import com.aliyun.jtester.hamcrest.iassert.common.intf.IListHasItemsAssert;
import com.aliyun.jtester.hamcrest.iassert.common.intf.IReflectionAssert;

/**
 * 数组对象断言接口
 * 
 * @author darui.wudr
 */
public interface IArrayAssert extends IBaseAssert<Object[], IArrayAssert>, IListHasItemsAssert<IArrayAssert>,
        IReflectionAssert<IArrayAssert>, IListAssert<Object[], IArrayAssert> {

}
