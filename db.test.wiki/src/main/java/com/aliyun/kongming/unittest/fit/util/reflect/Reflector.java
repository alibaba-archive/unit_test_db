package com.aliyun.kongming.unittest.fit.util.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chao.qianc on 2016/5/6.
 */
public class Reflector {

    public static Object getField(Object obj, String fieldName) {
        if (obj == null) {
            throw new RuntimeException("the obj can\'t be null!");
        } else {
            return getFieldValue(obj.getClass(), obj, fieldName);
        }
    }

    public static Object getFieldValue(Class<?> claz, Object obj, String fieldName) {
        if (obj == null) {
            throw new RuntimeException("the obj can\'t be null!");
        } else {
            Field field = null;

            try {
                field = claz.getDeclaredField(fieldName);
            } catch (Exception var13) {
                throw new RuntimeException(var13);
            }

            boolean accessible = field.isAccessible();

            Object var6;
            try {
                field.setAccessible(true);
                Object e = field.get(obj);
                var6 = e;
            } catch (Exception var11) {
                throw new RuntimeException("Unable to get the value in field[" + fieldName + "]", var11);
            } finally {
                field.setAccessible(accessible);
            }

            return var6;
        }
    }

    public static Object getFieldValue(Object obj, Field field) {
        if (obj == null) {
            throw new RuntimeException("the obj can\'t be null!");
        } else {
            boolean accessible = field.isAccessible();

            Object var4;
            try {
                field.setAccessible(true);
                Object e = field.get(obj);
                var4 = e;
            } catch (Exception var8) {
                throw new RuntimeException("Unable to get the value in field[" + field.getName() + "]", var8);
            } finally {
                field.setAccessible(accessible);
            }

            return var4;
        }
    }

    public static void setField(Object target, String field, Object value) {
        if (target == null) {
            throw new RuntimeException("the target object can't be null!");
        }
        Object _target = getProxiedObject(target);
        setField(_target.getClass(), _target, field, value);
    }

    public static void setField(Class<?> clazz, Object target, String field, Object value) {
        if (target == null) {
            throw new RuntimeException("the target object can't be null!");
        }
        Object _target = getProxiedObject(target);
        FieldAccessor accessor = new FieldAccessor(field, clazz);
        accessor.set(_target, value);
    }

    /**
     * 给对象obj名为fieldname的属性赋值
     *
     * @param obj 赋值对象
     * @param fieldName 赋值属性
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) {
        if (obj == null) {
            throw new RuntimeException("the obj can't be null!");
        }
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(accessible);
        } catch (Exception e) {
            String error = "Unable to update the value in field[" + fieldName + "]";
            throw new RuntimeException(error, e);
        }
    }

    /**
     * 如果是spring代理对象，获得被代理的对象
     *
     * @param target
     * @return
     */
    public static Object getProxiedObject(Object target) {
        try {
            if (target instanceof org.springframework.aop.framework.Advised) {
                return ((org.springframework.aop.framework.Advised) target).getTargetSource().getTarget();
            } else {
                return target;
            }
        } catch (NoClassDefFoundError error) {
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T invoke(Object target, String method, Object... paras) {
        if (target == null) {
            throw new RuntimeException("the target object can't be null!");
        }
        Object _target = getProxiedObject(target);
        return (T) invoke(_target.getClass(), _target, method, paras);
    }

    /**
     * 调用类为clazz,名称为method的方法
     *
     * @param methodName
     * @param paras
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Class<?> clazz, Object target, String methodName, Object... paras) {
        List<Class<?>> paraClazz = new ArrayList<Class<?>>();
        if (paras != null) {
            for (Object para : paras) {
                paraClazz.add(para == null ? null : para.getClass());
            }
        }
        MethodAccessor accessor = new MethodAccessor(target, clazz, methodName, paraClazz.toArray(new Class<?>[0]));
        return (T) accessor.invoke(paras);
    }
}
