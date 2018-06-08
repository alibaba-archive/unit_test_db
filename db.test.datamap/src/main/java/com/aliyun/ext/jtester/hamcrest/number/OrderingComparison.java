/*  Copyright (c) 2000-2009 hamcrest.org
 */
package com.aliyun.ext.jtester.hamcrest.number;

import com.aliyun.ext.jtester.hamcrest.Description;
import com.aliyun.ext.jtester.hamcrest.Factory;
import com.aliyun.ext.jtester.hamcrest.Matcher;
import com.aliyun.ext.jtester.hamcrest.TypeSafeMatcher;

public class OrderingComparison<T extends Comparable<T>> extends TypeSafeMatcher<T> {
    private static final int      LESS_THAN              = -1;
    private static final int      GREATER_THAN           = 1;
    private static final int      EQUAL                  = 0;
    private final T               expected;
    private final int             minCompare, maxCompare;

    private static final String[] comparisonDescriptions = { "less than", "equal to", "greater than" };

    private OrderingComparison(T expected, int minCompare, int maxCompare) {
        this.expected = expected;
        this.minCompare = minCompare;
        this.maxCompare = maxCompare;
    }

    @Override
    public boolean matchesSafely(T actual) {
        int compare = Integer.signum(actual.compareTo(expected));
        return minCompare <= compare && compare <= maxCompare;
    }

    @Override
    public void describeMismatchSafely(T actual, Description mismatchDescription) {
        mismatchDescription.appendValue(actual).appendText(" was ").appendText(asText(actual.compareTo(expected)))
                .appendText(" ").appendValue(expected);
    }

    public void describeTo(Description description) {
        description.appendText("a value ").appendText(asText(minCompare));
        if (minCompare != maxCompare) {
            description.appendText(" or ").appendText(asText(maxCompare));
        }
        description.appendText(" ").appendValue(expected);
    }

    private String asText(int comparison) {
        return comparisonDescriptions[comparison + 1];
    }

    /**
     * @return Is value = expected?
     */
    @Factory
    public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T value) {
        return new OrderingComparison<T>(value, EQUAL, EQUAL);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThan(T value) {
        return new OrderingComparison<T>(value, GREATER_THAN, GREATER_THAN);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T value) {
        return new OrderingComparison<T>(value, EQUAL, GREATER_THAN);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThan(T value) {
        return new OrderingComparison<T>(value, LESS_THAN, LESS_THAN);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T value) {
        return new OrderingComparison<T>(value, LESS_THAN, EQUAL);
    }
}
