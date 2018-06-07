package com.aliyun.jtester.module;

import com.aliyun.jtester.hamcrest.TheStyleAssertion;
import com.aliyun.jtester.hamcrest.WantStyleAssertion;
import com.aliyun.jtester.tools.commons.Reflector;
import com.aliyun.jtester.tools.datagen.AbastractDataGenerator;
import com.aliyun.jtester.tools.datagen.AbstractDataMap;
import com.aliyun.jtester.tools.datagen.DataProviderIterator;

@SuppressWarnings("serial")
public interface ICore {
    final WantStyleAssertion want      = new WantStyleAssertion();

    final TheStyleAssertion  the       = new TheStyleAssertion();

    final Reflector          reflector = Reflector.instance;

    /**
     * 数据生成器<br>
     * index计数从0开始
     * 
     * @author darui.wudr
     */
    public static abstract class DataGenerator extends AbastractDataGenerator {
    }

    public static class DataMap extends AbstractDataMap {
    }

    public static class DataIterator extends DataProviderIterator<Object> {
    }
}
