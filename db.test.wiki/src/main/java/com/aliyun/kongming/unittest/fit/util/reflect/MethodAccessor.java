package com.aliyun.kongming.unittest.fit.util.reflect;

import java.lang.reflect.Method;
import java.util.List;

/**
 * An accessor to a method that returns a value.
 *
 * @version 1.0
 * @since 0.1
 * @author Alessandro Nistico
 * @param <T> the return type of the method
 */
public class MethodAccessor<T> extends AbstractMethodAccessor<T> {

    public MethodAccessor(Object target, String methodName, Class<?>... parametersType) {
        super(methodName, target, parametersType);
    }

    /**
     * @param targetObj
     * @param targetClazz
     * @param methodName
     * @param parametersType
     */
    public MethodAccessor(Object targetObj, Class<?> targetClazz, String methodName, Class<?>... parametersType) {
        super(targetObj, targetClazz, methodName, parametersType);
    }

    MethodAccessor(Object target, Method method) {
        super(target, method);
    }

    /**
     * Invoke the method using the specified parameters.
     *
     * @param args the arguments for the method.
     * @return the result of the specified type.
     */
    public T invoke(Object... args) {
        return super.invokeBase(args);
    }

    /**
     * 根据方法的名称和参数个数查找方法访问器，如果有多于1个同名且参数个数一样的方法，那么抛出异常
     *
     * @param target
     * @param methodName
     * @param args
     * @return
     */
    public static <T> MethodAccessor<T> findMethodByArgCount(Object target, Class<?> targetClazz, String methodName,
                                                             int args) {
        List<Method> methods = getMethod(targetClazz, methodName, args);
        if (methods.size() == 0) {
            throw new RuntimeException("No such method: " + methodName + ",parameter count:" + args);
        }
        if (methods.size() > 1) {
            throw new RuntimeException("More then one method: " + methodName + ",parameter count:" + args);
        }
        Method method = methods.get(0);
        return new MethodAccessor<T>(target, method);
    }

    /**
     * 根据方法的名称和参数个数查找方法访问器，如果有多于1个同名且参数个数一样的方法，那么抛出异常
     *
     * @param <T>
     * @param target
     * @param methodName
     * @param args
     * @return
     */
    public static <T> MethodAccessor<T> findMethodByArgCount(Object target, String methodName, int args) {
        return findMethodByArgCount(target, target.getClass(), methodName, args);
    }
}
