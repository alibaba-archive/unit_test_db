package com.aliyun.jtester.hamcrest.iassert.common.impl;

import com.aliyun.ext.jtester.hamcrest.BaseMatcher;
import com.aliyun.ext.jtester.hamcrest.Description;
import com.aliyun.ext.jtester.hamcrest.Matcher;
import com.aliyun.ext.jtester.hamcrest.MatcherAssert;
import com.aliyun.jtester.hamcrest.TheStyleAssertion;
import com.aliyun.jtester.hamcrest.iassert.common.intf.IAssert;
import com.aliyun.jtester.hamcrest.matcher.LinkMatcher;
import com.aliyun.jtester.module.JTesterException;
import com.aliyun.jtester.tools.reflector.MethodAccessor;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class Assert<T, E extends IAssert> extends BaseMatcher<T> implements IAssert<T, E> {
    protected static final TheStyleAssertion the       = new TheStyleAssertion();

    protected Class                          valueClaz = null;

    protected Object                         value;

    protected AssertType                     type;

    protected Class<? extends IAssert>       assertClaz;

    protected LinkMatcher<?>                 link;

    public Assert() {
    }

    public Assert(Class<? extends IAssert> clazE) {
        this.value = null;
        this.type = AssertType.MatcherStyle;
        this.link = new LinkMatcher<T>();
        this.assertClaz = clazE;
    }

    public Assert(T value, Class<? extends IAssert> clazE) {
        this.type = AssertType.AssertStyle;
        this.value = value;
        this.assertClaz = clazE;
    }

    public Assert(Class<T> clazT, Class<? extends IAssert> clazE) {
        this.type = AssertType.MatcherStyle;
        this.valueClaz = clazT;
        this.assertClaz = clazE;
        this.link = new LinkMatcher<T>();
    }

    public void describeTo(Description description) {
        if (link != null && this.type == AssertType.MatcherStyle) {
            link.describeTo(description);
        } else if (this.value != null) {
            description.appendText(this.value.toString());
        }
    }

    public E assertThat(Matcher matcher) {
        if (this.type == AssertType.AssertStyle) {
            MatcherAssert.assertThat(this.value, matcher);
        } else {
            this.link.add(matcher);
        }
        return (E) this;
    }

    public E assertThat(String message, Matcher matcher) {
        if (this.type == AssertType.AssertStyle) {
            MatcherAssert.assertThat(message, this.value, matcher);
        } else {
            this.link.add(message, matcher);
        }
        return (E) this;
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }

    public boolean matches(Object item) {
        if (this.type == AssertType.MatcherStyle && this.value instanceof String) {
            String value = MethodAccessor.invokeMethodUnThrow(item, (String) item);
            return this.link.matches(value);
        } else {
            return this.link.matches(item);
        }
    }

    protected static enum AssertType {
        /**
         * 立即断言模式<br>
         * want.object(o)...
         */
        AssertStyle,
        /**
         * 定义断言器方式<br>
         * the.object()...
         */
        MatcherStyle;
    }

    @Override
    public boolean equals(Object obj) {
        throw new JTesterException("the method can't be used,please use isEqualTo() instead");
    }

    @Override
    public int hashCode() {
        throw new JTesterException("the method can't be used!");
    }

    public Class getValueClaz() {
        return valueClaz;
    }
}
