/*  Copyright (c) 2000-2006 hamcrest.org
 */
package ext.jtester.hamcrest;

/**
 * BaseClass for all Matcher implementations.
 *
 * @see Matcher
 */
public abstract class BaseMatcher<T> implements Matcher<T> {

    /**
     * @see Matcher#_dont_implement_Matcher___instead_extend_BaseMatcher_()
     */
    public final void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
        // See Matcher interface for an explanation of this method.
    }

    public void describeMismatch(Object item, Description description) {
    	description.appendText("was ").appendValue(item);
    }

    @Override
    public String toString() {
        return StringDescription.toString(this);
    }
}
