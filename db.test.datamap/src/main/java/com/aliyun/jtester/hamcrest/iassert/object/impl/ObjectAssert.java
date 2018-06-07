package com.aliyun.jtester.hamcrest.iassert.object.impl;

import com.aliyun.jtester.hamcrest.iassert.common.impl.ReflectionAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IObjectAssert;

public class ObjectAssert extends ReflectionAssert<Object, IObjectAssert> implements IObjectAssert {
    public ObjectAssert() {
        super(IObjectAssert.class);
        this.valueClaz = Object.class;
    }

    public ObjectAssert(Object bean) {
        super(bean, IObjectAssert.class);
        this.valueClaz = Object.class;
    }
}
