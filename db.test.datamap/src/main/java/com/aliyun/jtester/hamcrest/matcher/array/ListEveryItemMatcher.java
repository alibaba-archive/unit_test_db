package com.aliyun.jtester.hamcrest.matcher.array;

import java.util.Collection;

import com.aliyun.ext.jtester.hamcrest.BaseMatcher;
import com.aliyun.ext.jtester.hamcrest.Description;
import com.aliyun.ext.jtester.hamcrest.Matcher;
import com.aliyun.jtester.hamcrest.matcher.modes.ItemsMode;
import com.aliyun.jtester.tools.commons.ListHelper;

@SuppressWarnings("rawtypes")
public class ListEveryItemMatcher extends BaseMatcher {
    private Matcher   matcher   = null;

    private ItemsMode itemsMode = null;

    public ListEveryItemMatcher(Matcher matcher, ItemsMode itemsMode) {
        this.matcher = matcher;
        this.itemsMode = itemsMode;
    }

    public boolean matches(Object actual) {
        if (actual == null) {
            return false;
        }
        Collection _actual = ListHelper.toList(actual, true);

        for (Object item : _actual) {
            boolean match = false;
            if (item != null) {
                match = matcher.matches(item);
            }
            if (match == false && itemsMode == ItemsMode.AllItems) {
                return false;
            }
            if (match == true && itemsMode == ItemsMode.AnyItems) {
                return true;
            }
        }
        if (itemsMode == ItemsMode.AllItems) {
            return true;
        } else {
            return false;
        }
    }

    public void describeTo(Description description) {
        if (itemsMode == ItemsMode.AllItems) {
            description.appendText("all of item is ");
        } else {
            description.appendText("any of item is ");
        }
        description.appendDescriptionOf(matcher);
    }
}
