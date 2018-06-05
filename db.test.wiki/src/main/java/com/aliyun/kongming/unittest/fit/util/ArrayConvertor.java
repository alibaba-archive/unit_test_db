package com.aliyun.kongming.unittest.fit.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 将原生类型的数组转换成对象数组
 * 
 * @author darui.wudr
 */
public class ArrayConvertor {
    // boolean
    // byte
    // char
    // short int long
    // float double
    public static Object[] convert(char values[]) {
        List<Object> objs = new ArrayList<Object>();
        for (Character value : values) {
            objs.add(value);
        }
        return objs.toArray();
    }

    public static Object[] convert(float values[]) {
        List<Object> objs = new ArrayList<Object>();
        for (Float value : values) {
            objs.add(value);
        }
        return objs.toArray();
    }

    public static Object[] convert(long values[]) {
        List<Object> objs = new ArrayList<Object>();
        for (Long value : values) {
            objs.add(value);
        }
        return objs.toArray();
    }

    public static Object[] convert(short values[]) {
        List<Object> objs = new ArrayList<Object>();
        for (Short value : values) {
            objs.add(value);
        }
        return objs.toArray();
    }

    public static Object[] convert(int values[]) {
        List<Object> objs = new ArrayList<Object>();
        for (Integer value : values) {
            objs.add(value);
        }
        return objs.toArray();
    }

    public static Object[] convert(double values[]) {
        List<Object> objs = new ArrayList<Object>();
        for (Double value : values) {
            objs.add(value);
        }
        return objs.toArray();
    }

    public static Object[] convert(boolean values[]) {
        List<Object> objs = new ArrayList<Object>();
        for (Boolean value : values) {
            objs.add(value);
        }
        return objs.toArray();
    }

    public static Object[] convert(byte values[]) {
        List<Object> objs = new ArrayList<Object>();
        for (Byte value : values) {
            objs.add(value);
        }
        return objs.toArray();
    }

    public static <T> Object convert(T value) {
        if (value instanceof int[]) {
            return convert((int[]) value);
        } else if (value instanceof long[]) {
            return convert((long[]) value);
        } else if (value instanceof short[]) {
            return convert((short[]) value);
        } else if (value instanceof float[]) {
            return convert((float[]) value);
        } else if (value instanceof double[]) {
            return convert((double[]) value);
        } else if (value instanceof char[]) {
            return convert((char[]) value);
        } else if (value instanceof byte[]) {
            return convert((byte[]) value);
        } else if (value instanceof boolean[]) {
            return convert((boolean[]) value);
        } else {
            return value;
        }
    }

    public static List<?> convert(Object values[]) {
        if (values == null) {
            return null;
        }
        List<Object> list = new ArrayList<Object>();
        for (Object o : values) {
            list.add(o);
        }
        return list;
    }
}
