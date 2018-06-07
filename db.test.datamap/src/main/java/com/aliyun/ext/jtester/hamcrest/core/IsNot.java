/*  Copyright (c) 2000-2009 hamcrest.org
 */
package com.aliyun.ext.jtester.hamcrest.core;

import com.aliyun.ext.jtester.hamcrest.BaseMatcher;
import com.aliyun.ext.jtester.hamcrest.Factory;
import com.aliyun.ext.jtester.hamcrest.Description;
import com.aliyun.ext.jtester.hamcrest.Matcher;


/**
 * Calculates the logical negation of a matcher.
 */
public class IsNot<T> extends BaseMatcher<T> {
    private final Matcher<T> matcher;

    public IsNot(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    public boolean matches(Object arg) {
        return !matcher.matches(arg);
    }

    public void describeTo(Description description) {
        description.appendText("not ").appendDescriptionOf(matcher);
    }

    
    /**
     * Inverts the rule.
     */
    @Factory
    public static <T> Matcher<T> not(Matcher<T> matcher) {
        return new IsNot<T>(matcher);
    }

    /**
     * This is a shortcut to the frequently used not(equalTo(x)).
     *
     * For example:  assertThat(cheese, is(not(equalTo(smelly))))
     *          vs.  assertThat(cheese, is(not(smelly)))
     */
    @Factory
    public static <T> Matcher<T> not(T value) {
        return not(IsEqual.equalTo(value));
    }
}
