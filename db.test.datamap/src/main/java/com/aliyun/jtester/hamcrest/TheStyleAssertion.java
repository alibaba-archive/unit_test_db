package com.aliyun.jtester.hamcrest;

import java.util.Calendar;
import java.util.Date;

import com.aliyun.jtester.hamcrest.iassert.object.impl.ArrayAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.BooleanAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.ByteAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.CharacterAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.CollectionAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.DateAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.DoubleAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.FileAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.FloatAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.IntegerAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.LongAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.MapAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.NumberAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.ObjectAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.ShortAssert;
import com.aliyun.jtester.hamcrest.iassert.object.impl.StringAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IArrayAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IBooleanAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IByteAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.ICharacterAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.ICollectionAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IDateAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IDoubleAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IFileAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IFloatAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IIntegerAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.ILongAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IMapAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.INumberAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IObjectAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IShortAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IStringAssert;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TheStyleAssertion {
    /**
     * a parameter string will be asserted
     * 
     * @return
     */
    public IStringAssert string() {
        return new StringAssert();
    }

    /**
     * a parameter boolean will be expected
     * 
     * @return
     */
    public IBooleanAssert bool() {
        return new BooleanAssert();
    }

    /**
     * a parameter number(integer, long, double,short,float) will be expected
     * 
     * @return
     */
    public INumberAssert number() {
        return new NumberAssert(NumberAssert.class);
    }

    /**
     * a parameter integer number will be asserted
     * 
     * @return
     */
    public IIntegerAssert integer() {
        return new IntegerAssert();
    }

    /**
     * a parameter long number will be asserted
     * 
     * @return
     */
    public ILongAssert longnum() {
        return new LongAssert();
    }

    /**
     * a parameter double number will be asserted
     * 
     * @return
     */
    public IDoubleAssert doublenum() {
        return new DoubleAssert();
    }

    /**
     * a parameter float number will be asserted
     * 
     * @return
     */
    public IFloatAssert floatnum() {
        return new FloatAssert();
    }

    /**
     * a parameter short number will be asserted
     * 
     * @return
     */
    public IShortAssert shortnum() {
        return new ShortAssert();
    }

    /**
     * a parameter character will be asserted
     * 
     * @return
     */
    public ICharacterAssert character() {
        return new CharacterAssert();
    }

    /**
     * a parameter bite will be asserted
     * 
     * @return
     */
    public IByteAssert bite() {
        return new ByteAssert();
    }

    /**
     * a parameter array will be asserted
     * 
     * @return
     */
    public IArrayAssert array() {
        return new ArrayAssert();
    }

    /**
     * a parameter map will be asserted
     * 
     * @return
     */
    public IMapAssert map() {
        return new MapAssert();
    }

    /**
     * a parameter collection will be asserted
     * 
     * @return
     */
    public ICollectionAssert collection() {
        return new CollectionAssert();
    }

    /**
     * a parameter general object will be asserted
     * 
     * @return
     */
    public IObjectAssert object() {
        return new ObjectAssert();
    }

    /**
     * a parameter file will be asserted
     * 
     * @return
     */
    public IFileAssert file() {
        return new FileAssert();
    }

    /**
     * a parameter calendar will be asserted
     * 
     * @return
     */
    public IDateAssert<Calendar> calendar() {
        return new DateAssert<Calendar>(Calendar.class);
    }

    /**
     * a parameter date will be asserted
     * 
     * @return
     */
    public IDateAssert<Date> date() {
        return new DateAssert<Date>(Date.class);
    }
}
