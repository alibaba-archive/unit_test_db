package com.aliyun.ext.jtester.hamcrest.collection;

import java.util.Iterator;

import com.aliyun.ext.jtester.hamcrest.Factory;
import com.aliyun.ext.jtester.hamcrest.FeatureMatcher;
import com.aliyun.ext.jtester.hamcrest.Matcher;
import com.aliyun.ext.jtester.hamcrest.core.IsEqual;

public class IsIterableWithSize<E> extends FeatureMatcher<Iterable<E>, Integer> {

    public IsIterableWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an iterable with size", "iterable size");
    }

    @Override
    protected Integer featureValueOf(Iterable<E> actual) {
        int size = 0;
        for (Iterator<E> iterator = actual.iterator(); iterator.hasNext(); iterator.next()) {
            size++;
        }
        return size;
    }

    @Factory
    public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsIterableWithSize<E>(sizeMatcher);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> iterableWithSize(int size) {
        return iterableWithSize(IsEqual.equalTo(size));
    }
}
