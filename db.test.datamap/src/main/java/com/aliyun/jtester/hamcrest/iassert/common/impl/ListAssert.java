package com.aliyun.jtester.hamcrest.iassert.common.impl;

import java.util.List;

import com.aliyun.jtester.hamcrest.iassert.common.intf.IAssert;
import com.aliyun.jtester.hamcrest.matcher.property.PropertyAnyItemMatcher;
import com.aliyun.jtester.hamcrest.matcher.property.reflection.EqMode;
import com.aliyun.jtester.module.ICore;
import com.aliyun.jtester.hamcrest.iassert.common.intf.IAssert;
import com.aliyun.jtester.hamcrest.iassert.common.intf.IListAssert;
import com.aliyun.jtester.hamcrest.matcher.modes.ItemsMode;
import com.aliyun.jtester.hamcrest.matcher.property.MapListPropertyEqaulMatcher;
import com.aliyun.jtester.hamcrest.matcher.property.PropertyAllItemsMatcher;
import com.aliyun.jtester.hamcrest.matcher.property.PropertyAnyItemMatcher;
import com.aliyun.jtester.hamcrest.matcher.property.ReflectionEqualMatcher;
import com.aliyun.jtester.hamcrest.matcher.property.reflection.EqMode;
import com.aliyun.jtester.module.ICore.DataMap;
import com.aliyun.jtester.tools.commons.ListHelper;
import com.aliyun.jtester.tools.datagen.DataSet;

import com.aliyun.ext.jtester.hamcrest.Matcher;

@SuppressWarnings("rawtypes")
public class ListAssert<T, E extends IAssert> extends SizeAssert<T, E> implements IAssert<T, E>, IListAssert<T, E> {
	public ListAssert(Class<? extends IAssert> clazE) {
		super(clazE);
	}

	public ListAssert(T value, Class<? extends IAssert> clazE) {
		super(value, clazE);
	}

	public E isEqualTo(Object expected, EqMode... modes) {
		ReflectionEqualMatcher matcher = new ReflectionEqualMatcher(expected, modes);
		return this.assertThat(matcher);
	}

	public E eqIgnoreOrder(Object expected) {
		ReflectionEqualMatcher matcher = new ReflectionEqualMatcher(expected, new EqMode[] { EqMode.IGNORE_ORDER });
		return this.assertThat(matcher);
	}

	@SuppressWarnings("unchecked")
	public E reflectionEqMap(List<ICore.DataMap> expected, EqMode... modes) {
		List _modes = ListHelper.toList(modes);
		if (_modes.contains(EqMode.IGNORE_DEFAULTS) == false) {
			_modes.add(EqMode.IGNORE_DEFAULTS);
		}
		EqMode[] arrays = (EqMode[]) _modes.toArray(new EqMode[0]);
		MapListPropertyEqaulMatcher matcher = new MapListPropertyEqaulMatcher(expected, arrays);
		return this.assertThat(matcher);
	}

	public E propertyEqMap(int count, ICore.DataMap expected, EqMode... modes) {
		List<ICore.DataMap> lists = DataSet.parseMapList(count, expected);
		return reflectionEqMap(lists, modes);
	}

	public E reflectionEqMap(int count, ICore.DataMap expected, EqMode... modes) {
		return this.propertyEqMap(count, expected, modes);
	}

	public E propertyMatch(ItemsMode itemsMode, String property, Matcher matcher) {
		switch (itemsMode) {
		case AllItems:
			PropertyAllItemsMatcher m1 = new PropertyAllItemsMatcher(property, matcher);
			return this.assertThat(m1);
		case AnyItems:
			PropertyAnyItemMatcher m2 = new PropertyAnyItemMatcher(property, matcher);
			return this.assertThat(m2);
		default:
			throw new RuntimeException("the argument[ItemsMode] of property match API can't be null.");
		}
	}
}
