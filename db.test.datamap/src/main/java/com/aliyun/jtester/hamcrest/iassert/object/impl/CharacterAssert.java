package com.aliyun.jtester.hamcrest.iassert.object.impl;

import com.aliyun.jtester.hamcrest.iassert.common.impl.AllAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.ICharacterAssert;

public class CharacterAssert extends AllAssert<Character, ICharacterAssert> implements ICharacterAssert {

	public CharacterAssert() {
		super(ICharacterAssert.class);
		this.valueClaz = Character.class;
	}

	public CharacterAssert(Character value) {
		super(value, ICharacterAssert.class);
		this.valueClaz = Character.class;
	}

	public ICharacterAssert is(char ch) {
		return super.isEqualTo(ch);
	}
}
