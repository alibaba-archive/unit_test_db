package org.jtester.module;

import org.jtester.hamcrest.TheStyleAssertion;
import org.jtester.hamcrest.WantStyleAssertion;
import org.jtester.tools.commons.Reflector;
import org.jtester.tools.datagen.AbastractDataGenerator;
import org.jtester.tools.datagen.AbstractDataMap;
import org.jtester.tools.datagen.DataProviderIterator;

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
