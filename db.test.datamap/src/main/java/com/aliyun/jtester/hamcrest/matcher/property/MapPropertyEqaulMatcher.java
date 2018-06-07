package com.aliyun.jtester.hamcrest.matcher.property;

import static com.aliyun.jtester.hamcrest.matcher.property.reflection.ReflectionComparatorFactory.createRefectionComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliyun.jtester.hamcrest.matcher.property.difference.Difference;
import com.aliyun.jtester.hamcrest.matcher.property.reflection.EqMode;
import com.aliyun.jtester.hamcrest.matcher.property.reflection.ReflectionComparator;
import com.aliyun.jtester.hamcrest.matcher.property.reflection.ReflectionComparatorFactory;
import com.aliyun.jtester.hamcrest.matcher.property.report.DefaultDifferenceReport;
import com.aliyun.jtester.hamcrest.matcher.property.report.DifferenceReport;
import com.aliyun.jtester.module.ICore;
import com.aliyun.jtester.tools.commons.ArrayHelper;
import com.aliyun.jtester.tools.commons.ListHelper;
import com.aliyun.jtester.tools.reflector.PropertyAccessor;
import com.aliyun.jtester.hamcrest.matcher.property.difference.Difference;
import com.aliyun.jtester.hamcrest.matcher.property.reflection.EqMode;
import com.aliyun.jtester.hamcrest.matcher.property.reflection.ReflectionComparator;
import com.aliyun.jtester.hamcrest.matcher.property.report.DefaultDifferenceReport;
import com.aliyun.jtester.hamcrest.matcher.property.report.DifferenceReport;
import com.aliyun.jtester.module.ICore.DataMap;
import com.aliyun.jtester.tools.commons.ArrayHelper;
import com.aliyun.jtester.tools.commons.ListHelper;
import com.aliyun.jtester.tools.reflector.PropertyAccessor;

import com.aliyun.ext.jtester.hamcrest.BaseMatcher;
import com.aliyun.ext.jtester.hamcrest.Description;
import com.aliyun.ext.jtester.hamcrest.StringDescription;

/**
 * 把实际对象按照Map中的key值取出来，进行反射比较
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings("rawtypes")
public class MapPropertyEqaulMatcher extends BaseMatcher {

	private final ICore.DataMap expected;

	private EqMode[] modes;

	public MapPropertyEqaulMatcher(ICore.DataMap expected, EqMode[] modes) {
		this.expected = expected;
		this.modes = modes;
		if (expected == null) {
			throw new AssertionError("MapPropertyEqaulMatcher, the expected map can't be null.");
		}
	}

	public boolean matches(Object actual) {
		if (actual == null) {
			this.self.appendText("MapPropertyEqaulMatcher, the actual object can't be null or list/array.");
			return false;
		}

		List<Map<String, ?>> expecteds = new ArrayList<Map<String, ?>>();
		List<Map<String, ?>> actuals = new ArrayList<Map<String, ?>>();
		List list = ListHelper.toList(actual);
		if (list == null || list.size() == 0) {
			this.self.appendText("the actual value is empty.");
			return false;
		} else if (list.size() > 1) {
			this.self
					.appendText("the size of actual list/array are greater than one, please use API: reflectionEqMap(count, DataMap).");
			return false;
		}
		Object item = list.get(0);
		if (ArrayHelper.isCollOrArray(item)) {
			this.self.appendText("MapPropertyEqaulMatcher, the item of actual list can't list/array.");
			return false;
		} else {
			Map<String, Object> map = item == null ? null : this.getValueByMapKeys(item);
			actuals.add(map);
			expecteds.add(this.expected);
		}

		ReflectionComparator reflectionComparator = ReflectionComparatorFactory.createRefectionComparator(modes);
		this.difference = reflectionComparator.getDifference(expecteds, actuals);
		return difference == null;
	}

	private Map<String, Object> getValueByMapKeys(Object actualItem) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String property : expected.keySet()) {
			Object value = PropertyAccessor.getPropertyByOgnl(actualItem, property, true);
			map.put(property, value);
		}
		return map;
	}

	private StringDescription self = new StringDescription();

	private Difference difference;

	public void describeTo(Description description) {
		description.appendText(self.toString());
		if (difference != null) {
			DifferenceReport differenceReport = new DefaultDifferenceReport();
			description.appendText(differenceReport.createReport(difference));
		}
	}
}
