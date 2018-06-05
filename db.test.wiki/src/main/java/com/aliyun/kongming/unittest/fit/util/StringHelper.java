package com.aliyun.kongming.unittest.fit.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringHelper {
    /**
     * 判断string是否为null或空字符串
     * 
     * @param in
     * @return
     */
    public static boolean isBlankOrNull(String in) {
        if (in == null) {
            return true;
        } else if (in.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 把exception的trace信息写到string中
     * 
     * @param e
     * @return
     */
    public static String exceptionTrace(Throwable e) {
        StringWriter w = new StringWriter();
        e.printStackTrace(new PrintWriter(w));
        return w.toString();
    }

    /**
     * 把exception的trace信息写到string中,同时过滤掉filters中指定的信息
     * 
     * @param e
     * @param filters
     * @return
     */
    public static String exceptionTrace(Throwable e, List<String> filters) {
        StringWriter w = new StringWriter();
        e.printStackTrace(new PrintWriter(w));
        String tracer = w.toString();
        for (String regex : filters) {
            tracer = tracer.replaceAll(regex, "");
        }
        return tracer;
    }

    /**
     * 默认的Exception信息过滤规则
     */
    public final static List<String> DEFAULT_EXCEPTION_FILTER = new ArrayList<String>() {
                                                                  private static final long serialVersionUID = -5015223773312948340L;

                                                                  {
                                                                      add("\\s*at\\s*org\\.jtester\\.fit\\.FitRunner\\.[^\\s]+");
                                                                      add("\\s*at\\s*org\\.testng\\.[^\\s]+");
                                                                      add("\\s*at\\s*sun\\.reflect\\.[^\\s]+");
                                                                      add("\\s*at\\s*java\\.lang\\.reflect\\.[^\\s]+");
                                                                      add("\\s*at\\s*fitlibrary\\.[^\\s]+");
                                                                  }
                                                              };

    private static SimpleDateFormat  simpleDateTimeFormate    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat  simpleTimeFormate        = new SimpleDateFormat("mm:ss");

    public static String simpleDateStr(Timestamp time) {
        return simpleTimeFormate.format(time);
    }

    public static String simpleDateTimeStr(long time) {
        if (time > 0) {
            return simpleDateTimeFormate.format(new Timestamp(time));
        } else {
            return "--:--";
        }
    }

    public static String simpleTimeStr(long time) {
        if (time > 0) {
            return simpleTimeFormate.format(new Timestamp(time));
        } else {
            return "--";
        }
    }

    public static int parseInt(String str, int _default) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return _default;
        }
    }

    /**
     * 根据splitStr分割字符串string，并且过滤掉空串
     * 
     * @param string
     * @param splitStr
     * @return
     */
    public static String[] splits(String string, String splitStr) {
        if (StringHelper.isBlankOrNull(string)) {
            return new String[0];
        }
        String temp = string.replaceAll("\\s", "");
        String[] splits = temp.split(splitStr);
        List<String> clazzes = new ArrayList<String>();
        for (String split : splits) {
            if (StringHelper.isBlankOrNull(split)) {
                continue;
            }
            clazzes.add(split);
        }
        return clazzes.toArray(new String[0]);
    }

    /**
     * 把文本转义成正确格式的Html 比如:"<"转换成"&lt"等等
     * 
     * @param html
     * @return format后的文本
     */
    public static String formatHtml(String html) {
        String result = html;

        result = result.replaceAll("\"", "&quot;");
        result = result.replaceAll("<", "&lt;");
        result = result.replaceAll(">", "&gt;");
        result = result.replaceAll(" ", "&nbsp;");
        result = result.replaceAll("&", "&amp;");
        result = result.replaceAll("\n", "<br/>");
        return result;
    }

    /**
     * 将对象转化为字符串{ddd,ddd}
     * 
     * @param o
     * @return
     */
    public static String toString(Object o) {
        StringBuilder buff = new StringBuilder("{");
        boolean first = true;
        if (o == null) {
            return null;
        } else if (o instanceof Collection<?>) {
            Collection<?> oc = (Collection<?>) o;
            for (Object o1 : oc) {
                if (first == false) {
                    buff.append(",");
                } else {
                    first = false;
                }
                buff.append(toString(o1));
            }
            buff.append("}");
            return buff.toString();
        } else if (o instanceof Object[]) {
            Object[] oa = (Object[]) o;
            for (Object o2 : oa) {
                if (first == false) {
                    buff.append(",");
                } else {
                    first = false;
                }
                buff.append(toString(o2));
            }
            buff.append("}");
            return buff.toString();
        } else {
            return o.toString();
        }
    }
}
