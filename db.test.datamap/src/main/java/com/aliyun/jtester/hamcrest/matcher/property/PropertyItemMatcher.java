package com.aliyun.jtester.hamcrest.matcher.property;

import java.util.List;

import com.aliyun.jtester.tools.commons.ArrayHelper;
import com.aliyun.jtester.tools.commons.ListHelper;
import com.aliyun.jtester.tools.reflector.PropertyAccessor;

import com.aliyun.ext.jtester.hamcrest.BaseMatcher;
import com.aliyun.ext.jtester.hamcrest.Description;
import com.aliyun.ext.jtester.hamcrest.Matcher;

/**
 * 属性值集合或属性值作为一个对象，要满足指定的断言
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings("rawtypes")
public class PropertyItemMatcher extends BaseMatcher {
	private String property;
	private Matcher matcher;

	public PropertyItemMatcher(String property, Matcher matcher) {
		this.property = property;
		this.matcher = matcher;
		if (this.property == null) {
			throw new RuntimeException("the property can't be null.");
		}
	}

	public boolean matches(Object actual) {
		if (actual == null) {
			buff.append("properties equals matcher, the actual value can't be null.");
			return false;
		}
		if (ArrayHelper.isCollOrArray(actual)) {
			List list = ListHelper.toList(actual);
			List actuals = PropertyAccessor.getPropertyOfList(list, property, true);
			boolean match = this.matcher.matches(actuals);
			return match;
		} else {
			Object actuals = PropertyAccessor.getPropertyByOgnl(actual, property, true);
			boolean match = this.matcher.matches(actuals);
			return match;
		}
	}

	private final StringBuilder buff = new StringBuilder();

	public void describeTo(Description description) {
		description.appendText("the propery[" + this.property + "] of object must match");
		description.appendText(buff.toString());

		matcher.describeTo(description);
	}
}
