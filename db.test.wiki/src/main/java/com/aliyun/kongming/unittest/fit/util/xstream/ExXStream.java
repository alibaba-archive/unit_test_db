package com.aliyun.kongming.unittest.fit.util.xstream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.basic.BigDecimalConverter;
import com.thoughtworks.xstream.converters.basic.BigIntegerConverter;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.ByteConverter;
import com.thoughtworks.xstream.converters.basic.CharConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.FloatConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.converters.basic.ShortConverter;
import com.thoughtworks.xstream.converters.basic.StringBufferConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.converters.basic.URLConverter;
import com.thoughtworks.xstream.converters.collections.ArrayConverter;
import com.thoughtworks.xstream.converters.collections.BitSetConverter;
import com.thoughtworks.xstream.converters.collections.CharArrayConverter;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.converters.collections.PropertiesConverter;
import com.thoughtworks.xstream.converters.collections.TreeMapConverter;
import com.thoughtworks.xstream.converters.collections.TreeSetConverter;
import com.thoughtworks.xstream.converters.extended.DynamicProxyConverter;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;
import com.thoughtworks.xstream.converters.extended.FileConverter;
import com.thoughtworks.xstream.converters.extended.GregorianCalendarConverter;
import com.thoughtworks.xstream.converters.extended.JavaClassConverter;
import com.thoughtworks.xstream.converters.extended.JavaMethodConverter;
import com.thoughtworks.xstream.converters.extended.LocaleConverter;
import com.thoughtworks.xstream.converters.extended.SqlDateConverter;
import com.thoughtworks.xstream.converters.extended.SqlTimeConverter;
import com.thoughtworks.xstream.converters.extended.SqlTimestampConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.SelfStreamingInstanceChecker;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * 重载XStream的setupConverters的方法,为了和eclipse plugin的一致<br>
 * 去掉SerializableConverter和ExternalizableConverter这2个converter<br>
 * 去掉awt和swing的支持<br>
 * 
 * @author darui.wudr
 */
public class ExXStream extends AccessibleXStream {

    public ExXStream(HierarchicalStreamDriver hierarchicalStreamDriver) {
        super(hierarchicalStreamDriver);
    }

    @Override
    protected void setupConverters() {
        final ReflectionConverter reflectionConverter = new ReflectionConverter(mapper, reflectionProvider);
        registerConverter(reflectionConverter, PRIORITY_VERY_LOW);

        // registerConverter(new SerializableConverter(mapper,
        // reflectionProvider), PRIORITY_LOW);
        // registerConverter(new ExternalizableConverter(mapper), PRIORITY_LOW);

        registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
        registerConverter(new IntConverter(), PRIORITY_NORMAL);
        registerConverter(new FloatConverter(), PRIORITY_NORMAL);
        registerConverter(new DoubleConverter(), PRIORITY_NORMAL);
        registerConverter(new LongConverter(), PRIORITY_NORMAL);
        registerConverter(new ShortConverter(), PRIORITY_NORMAL);
        registerConverter((Converter) new CharConverter(), PRIORITY_NORMAL);
        registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
        registerConverter(new ByteConverter(), PRIORITY_NORMAL);

        registerConverter(new StringConverter(), PRIORITY_NORMAL);
        registerConverter(new StringBufferConverter(), PRIORITY_NORMAL);
        registerConverter(new DateConverter(), PRIORITY_NORMAL);
        registerConverter(new BitSetConverter(), PRIORITY_NORMAL);
        registerConverter(new URLConverter(), PRIORITY_NORMAL);
        registerConverter(new BigIntegerConverter(), PRIORITY_NORMAL);
        registerConverter(new BigDecimalConverter(), PRIORITY_NORMAL);

        registerConverter(new ArrayConverter(mapper), PRIORITY_NORMAL);
        registerConverter(new CharArrayConverter(), PRIORITY_NORMAL);
        registerConverter(new CollectionConverter(mapper), PRIORITY_NORMAL);
        registerConverter(new MapConverter(mapper), PRIORITY_NORMAL);
        registerConverter(new TreeMapConverter(mapper), PRIORITY_NORMAL);
        registerConverter(new TreeSetConverter(mapper), PRIORITY_NORMAL);
        registerConverter(new PropertiesConverter(), PRIORITY_NORMAL);
        registerConverter(new EncodedByteArrayConverter(), PRIORITY_NORMAL);

        registerConverter(new FileConverter(), PRIORITY_NORMAL);
        if (jvm.supportsSQL()) {
            registerConverter(new SqlTimestampConverter(), PRIORITY_NORMAL);
            registerConverter(new SqlTimeConverter(), PRIORITY_NORMAL);
            registerConverter(new SqlDateConverter(), PRIORITY_NORMAL);
        }
        registerConverter(new DynamicProxyConverter(mapper, classLoaderReference), PRIORITY_NORMAL);
        registerConverter(new JavaClassConverter(classLoaderReference), PRIORITY_NORMAL);
        registerConverter(new JavaMethodConverter(classLoaderReference), PRIORITY_NORMAL);
        // if (jvm.supportsAWT()) {
        // registerConverter(new FontConverter(), PRIORITY_NORMAL);
        // registerConverter(new ColorConverter(), PRIORITY_NORMAL);
        // registerConverter(new TextAttributeConverter(), PRIORITY_NORMAL);
        // }
        // if (jvm.supportsSwing()) {
        // registerConverter(new LookAndFeelConverter(mapper,
        // reflectionProvider), PRIORITY_NORMAL);
        // }
        registerConverter(new LocaleConverter(), PRIORITY_NORMAL);
        registerConverter(new GregorianCalendarConverter(), PRIORITY_NORMAL);

        if (JVM.is14()) {
            // late bound converters - allows XStream to be compiled on earlier
            // JDKs
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.extended.SubjectConverter",
                    PRIORITY_NORMAL, new Class[] { Mapper.class }, new Object[] { mapper });
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.extended.ThrowableConverter",
                    PRIORITY_NORMAL, new Class[] { Converter.class }, new Object[] { reflectionConverter });
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.extended.StackTraceElementConverter",
                    PRIORITY_NORMAL, null, null);
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.extended.CurrencyConverter",
                    PRIORITY_NORMAL, null, null);
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.extended.RegexPatternConverter",
                    PRIORITY_NORMAL, new Class[] { Converter.class }, new Object[] { reflectionConverter });
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.extended.CharsetConverter",
                    PRIORITY_NORMAL, null, null);
        }

        if (JVM.is15()) {
            // late bound converters - allows XStream to be compiled on earlier
            // JDKs
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.extended.DurationConverter",
                    PRIORITY_NORMAL, null, null);
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.enums.EnumConverter", PRIORITY_NORMAL,
                    null, null);
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.enums.EnumSetConverter", PRIORITY_NORMAL,
                    new Class[] { Mapper.class }, new Object[] { mapper });
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.enums.EnumMapConverter", PRIORITY_NORMAL,
                    new Class[] { Mapper.class }, new Object[] { mapper });
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.basic.StringBuilderConverter",
                    PRIORITY_NORMAL, null, null);
            dynamicallyRegisterConverter("com.thoughtworks.xstream.converters.basic.UUIDConverter", PRIORITY_NORMAL,
                    null, null);
        }

        registerConverter(new SelfStreamingInstanceChecker(reflectionConverter, this), PRIORITY_NORMAL);
    }
}
