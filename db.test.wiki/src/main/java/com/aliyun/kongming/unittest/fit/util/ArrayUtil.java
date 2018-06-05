package com.aliyun.kongming.unittest.fit.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ArrayUtil {
    public static boolean isArray(Object o) {
        if (o instanceof char[]) {// char
            return true;
        } else if (o instanceof boolean[]) {// boolean
            return true;
        } else if (o instanceof byte[]) {// byte
            return true;
        } else if (o instanceof short[]) {// short
            return true;
        } else if (o instanceof int[]) {// int
            return true;
        } else if (o instanceof long[]) {// long
            return true;
        } else if (o instanceof float[]) {// float
            return true;
        } else if (o instanceof double[]) {// double
            return true;
        } else {
            return o instanceof Object[];
        }
    }

    @SuppressWarnings("unchecked")
    public static Collection convertArray(Object o) {
        Collection coll = new ArrayList();
        if (o instanceof char[]) {// char
            char[] chars = (char[]) o;
            for (char ch : chars) {
                coll.add(ch);
            }
        } else if (o instanceof boolean[]) {// boolean
            boolean[] bls = (boolean[]) o;
            for (boolean bl : bls) {
                coll.add(bl);
            }
        } else if (o instanceof byte[]) {// byte
            byte[] bys = (byte[]) o;
            for (byte by : bys) {
                coll.add(by);
            }
        } else if (o instanceof short[]) {// short
            short[] shs = (short[]) o;
            for (short sh : shs) {
                coll.add(sh);
            }
        } else if (o instanceof int[]) {// int
            int[] ints = (int[]) o;
            for (int i : ints) {
                coll.add(i);
            }
        } else if (o instanceof long[]) {// long
            long[] ls = (long[]) o;
            for (long l : ls) {
                coll.add(l);
            }
        } else if (o instanceof float[]) {// float
            float[] fs = (float[]) o;
            for (float f : fs) {
                coll.add(f);
            }
        } else if (o instanceof double[]) {// double
            double[] ds = (double[]) o;
            for (double d : ds) {
                coll.add(d);
            }
        } else if (o instanceof Object[]) {
            Object[] os = (Object[]) o;
            for (Object _o : os) {
                coll.add(_o);
            }
        } else {
            coll.add(o);
        }
        return coll;
    }

    @SuppressWarnings("unchecked")
    public static Object[] convert(Collection coll) {
        return coll.toArray(new Object[0]);
    }

    /**
     * 把一个对象转化为collection<br>
     * o value是collection，直接返回<br>
     * o value是数组，转化为collection<br>
     * o value是单值对象，new一个collection，返回
     * 
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Collection convertToCollection(Object value) {
        if (isCollection(value)) {
            return (Collection) value;
        } else if (isArray(value)) {
            return convertArray(value);
        } else {
            Collection colls = new ArrayList<Object>();
            colls.add(value);
            return colls;
        }
    }

    public static boolean isCollection(Object o) {
        if (o == null) {
            return false;
        }
        return o instanceof Collection<?>;
    }

    public static int sizeOf(Object o) {
        if (o == null) {
            return 0;
        }
        int size = 0;
        if (o instanceof Collection<?>) {
            size = ((Collection<?>) o).size();
        } else if (o instanceof char[]) {// char
            size = ((char[]) o).length;
        } else if (o instanceof boolean[]) {// boolean
            size = ((boolean[]) o).length;
        } else if (o instanceof byte[]) {// byte
            size = ((byte[]) o).length;
        } else if (o instanceof short[]) {// short
            size = ((short[]) o).length;
        } else if (o instanceof int[]) {// int
            size = ((int[]) o).length;
        } else if (o instanceof long[]) {// long
            size = ((long[]) o).length;
        } else if (o instanceof float[]) {// float
            size = ((float[]) o).length;
        } else if (o instanceof double[]) {// double
            size = ((double[]) o).length;
        } else if (o instanceof Object[]) {
            size = ((Object[]) o).length;
        } else {
            return 1;
        }

        return size;
    }

    public static String toString(long[] a) {
        if (a == null)
            return "null";
        if (a.length == 0)
            return "[]";

        StringBuffer buf = new StringBuffer();
        buf.append('[');
        buf.append(a[0]);

        for (int i = 1; i < a.length; i++) {
            buf.append(", ");
            buf.append(a[i]);
        }

        buf.append("]");
        return buf.toString();
    }

    public static String toString(int[] a) {
        if (a == null)
            return "null";
        if (a.length == 0)
            return "[]";

        StringBuffer buf = new StringBuffer();
        buf.append('[');
        buf.append(a[0]);

        for (int i = 1; i < a.length; i++) {
            buf.append(", ");
            buf.append(a[i]);
        }

        buf.append("]");
        return buf.toString();
    }

    public static String toString(short[] a) {
        if (a == null)
            return "null";
        if (a.length == 0)
            return "[]";

        StringBuffer buf = new StringBuffer();
        buf.append('[');
        buf.append(a[0]);

        for (int i = 1; i < a.length; i++) {
            buf.append(", ");
            buf.append(a[i]);
        }

        buf.append("]");
        return buf.toString();
    }

    public static String toString(char[] a) {
        if (a == null)
            return "null";
        if (a.length == 0)
            return "[]";

        StringBuffer buf = new StringBuffer();
        buf.append('[');
        buf.append(a[0]);

        for (int i = 1; i < a.length; i++) {
            buf.append(", ");
            buf.append(a[i]);
        }

        buf.append("]");
        return buf.toString();
    }

    public static String toString(byte[] a) {
        if (a == null)
            return "null";
        if (a.length == 0)
            return "[]";

        StringBuffer buf = new StringBuffer();
        buf.append('[');
        buf.append(a[0]);

        for (int i = 1; i < a.length; i++) {
            buf.append(", ");
            buf.append(a[i]);
        }

        buf.append("]");
        return buf.toString();
    }

    public static String toString(boolean[] a) {
        if (a == null)
            return "null";
        if (a.length == 0)
            return "[]";

        StringBuffer buf = new StringBuffer();
        buf.append('[');
        buf.append(a[0]);

        for (int i = 1; i < a.length; i++) {
            buf.append(", ");
            buf.append(a[i]);
        }

        buf.append("]");
        return buf.toString();
    }

    public static String toString(float[] a) {
        if (a == null)
            return "null";
        if (a.length == 0)
            return "[]";

        StringBuffer buf = new StringBuffer();
        buf.append('[');
        buf.append(a[0]);

        for (int i = 1; i < a.length; i++) {
            buf.append(", ");
            buf.append(a[i]);
        }

        buf.append("]");
        return buf.toString();
    }

    public static String toString(double[] a) {
        if (a == null)
            return "null";
        if (a.length == 0)
            return "[]";

        StringBuffer buf = new StringBuffer();
        buf.append('[');
        buf.append(a[0]);

        for (int i = 1; i < a.length; i++) {
            buf.append(", ");
            buf.append(a[i]);
        }

        buf.append("]");
        return buf.toString();
    }

    public static String toString(Object[] a) {
        if (a == null)
            return "null";
        if (a.length == 0)
            return "[]";

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < a.length; i++) {
            if (i == 0)
                buf.append('[');
            else
                buf.append(", ");

            buf.append(String.valueOf(a[i]));
        }

        buf.append("]");
        return buf.toString();
    }

    public static String deepToString(Object[] a) {
        if (a == null)
            return "null";

        int bufLen = 20 * a.length;
        if (a.length != 0 && bufLen <= 0)
            bufLen = Integer.MAX_VALUE;
        StringBuffer buf = new StringBuffer(bufLen);
        deepToString(a, buf, new HashSet<Object[]>());
        return buf.toString();
    }

    private static void deepToString(Object[] a, StringBuffer buf, Set<Object[]> dejaVu) {
        if (a == null) {
            buf.append("null");
            return;
        }
        dejaVu.add(a);
        buf.append('[');
        for (int i = 0; i < a.length; i++) {
            if (i != 0)
                buf.append(", ");

            Object element = a[i];
            if (element == null) {
                buf.append("null");
            } else {
                Class<?> eClass = element.getClass();

                if (eClass.isArray()) {
                    if (eClass == byte[].class)
                        buf.append(toString((byte[]) element));
                    else if (eClass == short[].class)
                        buf.append(toString((short[]) element));
                    else if (eClass == int[].class)
                        buf.append(toString((int[]) element));
                    else if (eClass == long[].class)
                        buf.append(toString((long[]) element));
                    else if (eClass == char[].class)
                        buf.append(toString((char[]) element));
                    else if (eClass == float[].class)
                        buf.append(toString((float[]) element));
                    else if (eClass == double[].class)
                        buf.append(toString((double[]) element));
                    else if (eClass == boolean[].class)
                        buf.append(toString((boolean[]) element));
                    else { // element is an array of object references
                        if (dejaVu.contains(element))
                            buf.append("[...]");
                        else
                            deepToString((Object[]) element, buf, dejaVu);
                    }
                } else { // element is non-null and not an array
                    buf.append(element.toString());
                }
            }
        }
        buf.append("]");
        dejaVu.remove(a);
    }
}
