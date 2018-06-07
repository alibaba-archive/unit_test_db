package com.aliyun.ext.jtester.hamcrest;

/**
 * Utility class for writing one off matchers.
 * <p>
 * This class is designed for scenarios where an anonymous inner class matcher
 * makes sense. It should not be used by API designers implementing matchers.
 *
 * @author Neil Dunn
 * @see CustomTypeSafeMatcher for a type safe variant of this class that you
 *      probably want to use.
 * @param <T> The type of object being matched.
 */
public abstract class CustomMatcher<T> extends BaseMatcher<T> {
    private final String fixedDescription;

    public CustomMatcher(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description should be non null!");
        }
        this.fixedDescription = description;
    }

    public final void describeTo(Description description) {
        description.appendText(fixedDescription);
    }
}
