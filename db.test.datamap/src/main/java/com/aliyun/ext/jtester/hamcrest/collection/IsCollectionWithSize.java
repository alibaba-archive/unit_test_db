package com.aliyun.ext.jtester.hamcrest.collection;

import java.util.Collection;

import com.aliyun.ext.jtester.hamcrest.Factory;
import com.aliyun.ext.jtester.hamcrest.FeatureMatcher;
import com.aliyun.ext.jtester.hamcrest.Matcher;
import com.aliyun.ext.jtester.hamcrest.core.IsEqual;

/**
 * Matches if collection size satisfies a nested matcher.
 */
public class IsCollectionWithSize<E> extends FeatureMatcher<Collection<? extends E>, Integer> {
    public IsCollectionWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "a collection with size", "collection size");
    }

    @Override
    protected Integer featureValueOf(Collection<? extends E> actual) {
        return actual.size();
    }

    /**
     * Does collection size satisfy a given matcher?
     */
    @Factory
    public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> size) {
        return new IsCollectionWithSize<E>(size);
    }

    /**
     * This is a shortcut to the frequently used hasSize(equalTo(x)). For
     * example, assertThat(hasSize(equal_to(x))) vs. assertThat(hasSize(x))
     */
    @Factory
    public static <E> Matcher<Collection<? extends E>> hasSize(int size) {
        Matcher<? super Integer> matcher = IsEqual.equalTo(size);
        return IsCollectionWithSize.<E> hasSize(matcher);
    }

}
