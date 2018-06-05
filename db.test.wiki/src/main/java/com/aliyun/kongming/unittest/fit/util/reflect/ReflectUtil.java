package com.aliyun.kongming.unittest.fit.util.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.unitils.core.UnitilsException;

import com.aliyun.kongming.unittest.fit.exception.JTesterException;

import ognl.DefaultMemberAccess;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

/**
 * POJO反射处理工具类
 *
 * @author darui.wudr
 */
public class ReflectUtil {
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
            throw new JTesterException(error, e);
        }
    }

    /**
     * Sets the given value to the given field on the given object<br>
     * 和 org.unitils.util.ReflectionUtils setFieldValue方法相比，
     * 多了判断value对象是否是spring的proxy对象<br>
     *
     * @param object The object containing the field, not null
     * @param field The field, not null
     * @param value The value for the given field in the given object
     * @throws UnitilsException if the field could not be accessed
     */
    public static void setFieldValue(Object object, Field field, Object value) {
        try {
            org.unitils.util.ReflectionUtils.setFieldValue(object, field, value);
        } catch (Exception e) {
            throw decoratedAdvisedException(value, e);
        }
    }

    /**
     * 包装一下异常信息的消息，使提示更明显
     *
     * @param e
     */
    public static RuntimeException decoratedAdvisedException(Object object, Exception e) {
        if (!(object instanceof org.springframework.aop.framework.Advised)) {
            return new RuntimeException(e);
        }
        org.springframework.aop.framework.Advised advised = (org.springframework.aop.framework.Advised) object;
        StringBuilder sb = new StringBuilder();

        sb.append("value[" + object + "] is org.springframework.aop.framework.Advised\n");
        if (advised.isProxyTargetClass()) {
            sb.append("proxied by the full target class");
            return new RuntimeException(sb.toString(), e);
        } else {
            Class<?>[] clazzes = ((org.springframework.aop.framework.Advised) object).getProxiedInterfaces();
            sb.append("proxyed by the interfaces:\n");
            for (Class<?> clazz : clazzes) {
                sb.append("\t" + clazz.getName() + "\n");
            }
            return new RuntimeException(sb.toString(), e);
        }
    }

    /**
     * 获得对象obj的属性名为fieldname的字段值
     *
     * @param obj
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj == null) {
            throw new RuntimeException("the obj can't be null!");
        }
        return getFieldValue(obj.getClass(), obj, fieldName);
    }

    /**
     * 获得对象obj的属性名为fieldname的字段值
     *
     * @param claz
     * @param obj
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public static Object getFieldValue(Class<?> claz, Object obj, String fieldName) {
        if (obj == null) {
            throw new RuntimeException("the obj can't be null!");
        }
        Field field = null;
        try {
            field = claz.getDeclaredField(fieldName);
        } catch (Exception e) {
            throw new JTesterException(e);
        }
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            Object o = field.get(obj);
            return o;
        } catch (Exception e) {
            throw new JTesterException("Unable to get the value in field[" + fieldName + "]", e);
        } finally {
            field.setAccessible(accessible);
        }
    }

    public static Object getFieldValue(Object obj, Field field) {
        if (obj == null) {
            throw new RuntimeException("the obj can't be null!");
        }
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            Object o = field.get(obj);
            return o;
        } catch (Exception e) {
            throw new JTesterException("Unable to get the value in field[" + field.getName() + "]", e);
        } finally {
            field.setAccessible(accessible);
        }
    }

    public static Object getPropertyValue(Object object, String ognlExpression, boolean throwException) {
        try {
            OgnlContext ognlContext = new OgnlContext();
            ognlContext.setMemberAccess(new DefaultMemberAccess(true));
            Object ognlExprObj = Ognl.parseExpression(ognlExpression);
            return Ognl.getValue(ognlExprObj, ognlContext, object);
        } catch (OgnlException e) {
            if (throwException) {
                throw new JTesterException("Failed to get property value using OGNL expression " + ognlExpression, e);
            } else {
                return object;
            }
        } catch (RuntimeException e) {
            if (throwException) {
                throw e;
            } else {
                return object;
            }
        }
    }

    public static Object[] getPropertyValue(Object object, String[] ognlExpression, boolean throwException) {
        List<Object> os = new ArrayList<Object>();
        for (String ognl : ognlExpression) {
            Object value = getPropertyValue(object, ognl, throwException);
            os.add(value);
        }
        return os.toArray(new Object[0]);
    }

    public static Collection<?> getPropertyCollection(Object o, String property) {
        Collection<Object> coll = new ArrayList<Object>();
        if (o == null) {
            coll.add(null);
        } else if (o instanceof Collection<?>) {
            Collection<?> oc = (Collection<?>) o;
            for (Object o1 : oc) {
                Object value = ReflectUtil.getPropertyValue(o1, property, false);
                coll.add(value);
            }
        } else if (o instanceof Object[]) {
            Object[] oa = (Object[]) o;
            for (Object o2 : oa) {
                Object value = ReflectUtil.getPropertyValue(o2, property, false);
                coll.add(value);
            }
        } else {
            Object value = ReflectUtil.getPropertyValue(o, property, false);
            coll.add(value);
        }
        return coll;
    }

    public static Object[][] getPropertyCollection(Object o, String[] properties) {
        List<Object> coll = new ArrayList<Object>();
        if (o == null) {
            coll.add(new Object[] { null });
        } else if (o instanceof Collection<?>) {
            Collection<?> oc = (Collection<?>) o;
            for (Object o1 : oc) {
                Object[] values = ReflectUtil.getPropertyValue(o1, properties, false);
                coll.add(values);
            }
        } else if (o instanceof Object[]) {
            Object[] oa = (Object[]) o;
            for (Object o2 : oa) {
                Object value = ReflectUtil.getPropertyValue(o2, properties, false);
                coll.add(value);
            }
        } else {
            Object value = ReflectUtil.getPropertyValue(o, properties, false);
            coll.add(new Object[] { value });
        }
        return coll.toArray(new Object[0][0]);
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
            throw new JTesterException(e);
        }
    }

    /**
     * 调用类为clazz,名称为method的方法
     *
     * @param paras
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Class<?> clazz, Object target, String method, Object... paras) {
        List<Class<?>> paraClazz = new ArrayList<Class<?>>();
        if (paras != null) {
            for (Object para : paras) {
                paraClazz.add(para == null ? null : para.getClass());
            }
        }
        MethodAccessor accessor = new MethodAccessor(target, clazz, method, paraClazz.toArray(new Class<?>[0]));
        return (T) accessor.invoke(paras);
    }
}
