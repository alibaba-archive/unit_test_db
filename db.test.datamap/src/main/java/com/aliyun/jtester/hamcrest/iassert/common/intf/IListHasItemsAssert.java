package com.aliyun.jtester.hamcrest.iassert.common.intf;

import com.aliyun.ext.jtester.hamcrest.Matcher;
import com.aliyun.jtester.hamcrest.matcher.modes.ItemsMode;
import com.aliyun.jtester.hamcrest.matcher.modes.MatchMode;

/**
 * 数组或collection类型的对象容器断言
 * 
 * @author darui.wudr
 * @param <E>
 */

@SuppressWarnings("rawtypes")
public interface IListHasItemsAssert<E extends IAssert> {
    /**
     * want: the collection or the array should contain all items listed by
     * following arguments.
     * 
     * @param value expected item
     * @param values other expected items
     */
    E hasAllItems(Object value, Object... values);

    /**
     * want: the collection or the array should contain item specified by
     * argument.
     * 
     */
    E hasItems(Object value);

    /**
     * want: the collection or the array should contain item specified by
     * argument.
     * 
     */
    E hasItems(Object item, Object... items);

    /**
     * want: the collection or the array should contain any items listed by
     * following arguments.
     * 
     * @param value expected item
     * @param values other expected items
     * @return
     */
    E hasAnyItems(Object value, Object... values);

    /**
     * all of items in collection(array) should match all of matchers specified
     * by following arguments.<br>
     * <br>
     * 
     */
    E allItemsMatchAll(Matcher matcher, Matcher... matchers);

    /**
     * all of items in collection(array) should match any of matchers specified
     * by following arguments.<br>
     *
     */
    E allItemsMatchAny(Matcher matcher, Matcher... matchers);

    /**
     * any of items in collection(array) should match all of matchers specified
     * by following arguments.<br>
     *
     */
    E anyItemsMatchAll(Matcher matcher, Matcher... matchers);

    /**
     * any of items in collection(array) should match any of matchers specified
     * by following arguments.<br>
     *
     */
    E anyItemsMatchAny(Matcher matcher, Matcher... matchers);

    /**
     * all of any(specified by {@link ItemsMode}) items(properties) should match
     * all or any(specified by {@link MatchMode}) matchers.<br>
     * 数组中所有或任一（由ItemsMode决定）元素必须和下列全部或任一（由MatchMode决定）Matcher匹配。
     * 
     */
    E match(ItemsMode itemsMode, MatchMode matchMode, Matcher matcher, Matcher... matchers);
}
