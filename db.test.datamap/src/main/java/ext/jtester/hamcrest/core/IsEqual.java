/*  Copyright (c) 2000-2006 hamcrest.org
 */
package ext.jtester.hamcrest.core;

import ext.jtester.hamcrest.BaseMatcher;
import ext.jtester.hamcrest.Description;
import ext.jtester.hamcrest.Factory;
import ext.jtester.hamcrest.Matcher;

import java.lang.reflect.Array;

import org.jtester.tools.commons.PrimitiveHelper;

/**
 * Is the value equal to another value, as tested by the
 * {@link java.lang.Object#equals} invokedMethod?
 */
public class IsEqual<T> extends BaseMatcher<T> {
	private final Object object;

	public IsEqual(T equalArg) {
		object = equalArg;
	}

	public boolean matches(Object arg) {
		return areEqual(arg, object);
	}

	public void describeTo(Description description) {
		description.appendValue(object);
	}

	private static boolean areEqual(Object o1, Object o2) {
		if (o1 == null) {
			return o2 == null;
		} else if (o2 != null && isArray(o1)) {
			return isArray(o2) && areArraysEqual(o1, o2);
		} else {
			if (o1 instanceof Number && o2 instanceof Number) {
				return PrimitiveHelper.doesEqual((Number) o1, (Number) o2);
			} else {
				return o1.equals(o2);
			}
		}
	}

	private static boolean areArraysEqual(Object o1, Object o2) {
		return areArrayLengthsEqual(o1, o2) && areArrayElementsEqual(o1, o2);
	}

	private static boolean areArrayLengthsEqual(Object o1, Object o2) {
		return Array.getLength(o1) == Array.getLength(o2);
	}

	private static boolean areArrayElementsEqual(Object o1, Object o2) {
		for (int i = 0; i < Array.getLength(o1); i++) {
			if (!areEqual(Array.get(o1, i), Array.get(o2, i)))
				return false;
		}
		return true;
	}

	private static boolean isArray(Object o) {
		return o.getClass().isArray();
	}

	/**
	 * Is the value equal to another value, as tested by the
	 * {@link java.lang.Object#equals} invokedMethod?
	 */
	@Factory
	public static <T> Matcher<T> equalTo(T operand) {
		return new IsEqual<T>(operand);
	}
}
