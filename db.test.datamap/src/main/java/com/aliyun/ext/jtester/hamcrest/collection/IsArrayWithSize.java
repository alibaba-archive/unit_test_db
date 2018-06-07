package com.aliyun.ext.jtester.hamcrest.collection;

import com.aliyun.ext.jtester.hamcrest.Factory;
import com.aliyun.ext.jtester.hamcrest.FeatureMatcher;
import com.aliyun.ext.jtester.hamcrest.Matcher;
import com.aliyun.ext.jtester.hamcrest.core.DescribedAs;
import com.aliyun.ext.jtester.hamcrest.core.IsEqual;

/**
 * Matches if array size satisfies a nested matcher.
 */
public class IsArrayWithSize<E> extends FeatureMatcher<E[], Integer> {
    public IsArrayWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an array with size", "array size");
    }

    @Override
    protected Integer featureValueOf(E[] actual) {
        return actual.length;
    };

    /**
     * Does array size satisfy a given matcher?
     */
    @Factory
    public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsArrayWithSize<E>(sizeMatcher);
    }

    /**
     * This is a shortcut to the frequently used arrayWithSize(equalTo(x)). For
     * example, assertThat(arrayWithSize(equal_to(x))) vs.
     * assertThat(arrayWithSize(x))
     */
    @Factory
    public static <E> Matcher<E[]> arrayWithSize(int size) {
        return arrayWithSize(IsEqual.equalTo(size));
    }

    /**
     * Matches an empty array.
     */
    @Factory
    public static <E> Matcher<E[]> emptyArray() {
        Matcher<E[]> isEmpty = arrayWithSize(0);
        return DescribedAs.describedAs("an empty array", isEmpty);
    }
}
