package com.aliyun.ext.jtester.hamcrest.object;

import com.aliyun.ext.jtester.hamcrest.Factory;
import com.aliyun.ext.jtester.hamcrest.TypeSafeMatcher;
import com.aliyun.ext.jtester.hamcrest.Description;
import com.aliyun.ext.jtester.hamcrest.Matcher;

public class IsCompatibleType<T> extends TypeSafeMatcher<Class<?>> {
    private final Class<T> type;
    
    public IsCompatibleType(Class<T> type) {
        this.type = type;
    }
    
    @Override
    public boolean matchesSafely(Class<?> cls) {
        return type.isAssignableFrom(cls);
    }
    
    @Override
    public void describeMismatchSafely(Class<?> cls, Description mismatchDescription) {
      mismatchDescription.appendValue(cls.getName());
    }
    
    public void describeTo(Description description) {
        description.appendText("type < ").appendText(type.getName());
    }
    
    @Factory
    public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> baseType) {
        return new IsCompatibleType<T>(baseType);
    }
}
