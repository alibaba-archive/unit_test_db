package com.aliyun.kongming.unittest.fit.util.reflect;

import java.lang.reflect.Modifier;

/**
 * Allow to access a private field of a class.
 *
 * @version 1.0
 * @since 0.1
 * @author Alessandro Nistico
 * @param <T> the type of the target type which hierarchy contains the field.
 * @param <V> the type of the field.
 */
public class FieldAccessor<T, V> extends BaseFieldAccessor<T, V> {

    /**
     * Constructor.
     *
     * @param fieldName the field name
     * @param type the class from which to start searching the field
     */
    public FieldAccessor(String fieldName, Class<? extends T> type) {
        super(fieldName, type);
        if (Modifier.isStatic(field.getModifiers())) {
            throw new IllegalArgumentException("Field " + fieldName + " is static");
        }
    }

    /**
     * Gets the value of the field.
     *
     * @param target the instance from which to get the value.
     * @return the current field value.
     */
    @SuppressWarnings("unchecked")
    public final V get(T target) {
        try {
            return (V) field.get(target);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the value for the field.
     *
     * @param target the target instance on which to set the new value.
     * @param value the value to set.
     */
    public final void set(T target, V value) {
        try {
            field.set(target, value);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
