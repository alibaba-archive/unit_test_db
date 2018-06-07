package com.aliyun.jtester.hamcrest.iassert.object.intf;

import com.aliyun.jtester.hamcrest.iassert.common.intf.IBaseAssert;
import com.aliyun.jtester.hamcrest.iassert.common.intf.IBaseAssert;

/**
 * char类型对象断言接口
 * 
 * @author darui.wudr
 * 
 */
public interface ICharacterAssert extends IBaseAssert<Character, ICharacterAssert> {
	ICharacterAssert is(char ch);
}
