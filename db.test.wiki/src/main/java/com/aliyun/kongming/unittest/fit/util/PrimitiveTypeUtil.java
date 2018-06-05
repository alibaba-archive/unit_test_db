package com.aliyun.kongming.unittest.fit.util;

import java.util.HashMap;
import java.util.Map;

/**
 * java原生类型的默认值
 * 
 * @author darui.wudr
 */
public class PrimitiveTypeUtil {
    private static Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
    static {
        map.put(String.class, "");
        map.put(Integer.class, 0);
        map.put(Short.class, (short) 0);
        map.put(Long.class, (long) 0);
        map.put(Byte.class, (byte) 0);
        map.put(Float.class, 0.0f);
        map.put(Double.class, 0.0d);
        map.put(Character.class, '\0');
        map.put(Boolean.class, false);

        map.put(int.class, 0);
        map.put(short.class, 0);
        map.put(long.class, 0);
        map.put(byte.class, 0);
        map.put(float.class, 0.0f);
        map.put(double.class, 0.0d);
        map.put(char.class, '\0');
        map.put(boolean.class, false);
    }

    /**
     * 返回有对应primitive类型的默认值
     * 
     * @param claz
     * @return
     */
    public static Object getPrimitiveDefaultValue(Class<?> claz) {
        if (map.containsKey(claz)) {
            return map.get(claz);
        } else {
            return null;
        }
    }

    /**
     * 判断2个类型的primitive类型是否一致
     * 
     * @param expected
     * @param actual
     * @return
     */
    public static boolean isPrimitiveTypeEquals(final Class<?> expected, final Class<?> actual) {
        Class<?> _expected = expected;
        if (couples.containsKey(expected)) {
            _expected = couples.get(expected);
        }
        Class<?> _actual = actual;
        if (couples.containsKey(actual)) {
            _actual = couples.get(actual);
        }
        return _expected == _actual;
    }

    private static Map<Class<?>, Class<?>> couples = new HashMap<Class<?>, Class<?>>();
    static {
        couples.put(Integer.class, int.class);
        couples.put(Short.class, short.class);
        couples.put(Long.class, long.class);
        couples.put(Byte.class, byte.class);
        couples.put(Float.class, float.class);
        couples.put(Double.class, double.class);
        couples.put(Character.class, char.class);
        couples.put(Boolean.class, boolean.class);
    }
}
