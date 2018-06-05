package com.aliyun.kongming.unittest.fit.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract method accessor.
 *
 * @version 1.0
 * @since 0.1
 * @author Alessandro Nistico
 * @param <T> the return type of the method
 */
public abstract class AbstractMethodAccessor<T> {

    /**
     * The method to invoke.
     */

    private final Method method;

    /**
     * The target on which to invoke the method.
     */

    private final Object target;

    protected AbstractMethodAccessor(Object targetObj, Class<?> targetClazz, String methodName,
                                     Class<?>... parametersType) {
        method = getMethod(targetClazz, methodName, parametersType);
        target = targetObj;
    }

    /**
     * 构造函数
     *
     * @param methodName
     * @param target
     * @param parametersType
     */
    AbstractMethodAccessor(String methodName, Object target, Class<?>... parametersType) {
        try {
            method = getMethod(target.getClass(), methodName, parametersType);
            this.target = target;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    protected AbstractMethodAccessor(Object target, Method method) {
        this.method = method;
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    /**
     * Common method invocation.
     *
     * @param args the arguments for the method.
     * @return the result of the specified type.
     */
    @SuppressWarnings("unchecked")
    protected final T invokeBase(Object... args) {
        try {
            return (T) method.invoke(target, args);
        } catch (InvocationTargetException e) {
            Throwable e1 = e.getTargetException();
            if (e1 instanceof RuntimeException) {
                throw (RuntimeException) e1;
            } else {
                throw new RuntimeException(e1);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    static final Method getMethod(Class cls, String name, Class<?>... parametersType) {
        while (cls != Object.class) {
            try {
                Method[] methods = cls.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().equals(name) == false) {
                        continue;
                    }
                    if (matchParasType(parametersType, method.getParameterTypes())) {
                        method.setAccessible(true);
                        return method;
                    }
                }
                throw new NoSuchMethodException();
            } catch (NoSuchMethodException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new RuntimeException("No such method: " + name + "(" + Arrays.toString(parametersType) + ")");
    }

    /**
     * 查找名为name，参数个数为args的方法列表
     *
     * @param cls
     * @param name
     * @param args
     * @return
     */
    static final List<Method> getMethod(Class<?> cls, String name, int args) {
        List<Method> methods = new ArrayList<Method>();
        while (cls != Object.class) {
            try {
                Method[] declares = cls.getDeclaredMethods();
                for (Method method : declares) {
                    if (method.getName().equals(name) == false) {
                        continue;
                    }
                    if (method.getParameterTypes().length == args) {
                        methods.add(method);
                    }
                }
                throw new NoSuchMethodException();
            } catch (NoSuchMethodException e) {
                cls = cls.getSuperclass();
            }
        }
        return methods;
    }

    static boolean matchParasType(Class<?>[] expecteds, Class<?>[] actuals) {
        if (expecteds == null && actuals == null) {
            return true;
        } else if (expecteds == null || actuals == null) {
            return false;
        } else if (expecteds.length != actuals.length) {
            return false;
        }
        for (int index = 0; index < expecteds.length; index++) {
            Class<?> expected = expecteds[index];
            Class<?> actual = actuals[index];
            if (expected == null || expected == actual) {
                continue;
            } else if (actual != null && actual.isAssignableFrom(expected)) {
                continue;
            } else if (isPrimitiveTypeEquals(expected, actual)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
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
