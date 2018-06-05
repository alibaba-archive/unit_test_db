package com.aliyun.kongming.unittest.fit.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * 返回当前日期的默认格式("yyyy-MM-dd")字符串
     * 
     * @return
     */
    public static final String currDateStr() {
        return currDateTimeStr(now(), "yyyy-MM-dd");
    }

    /**
     * 返回当前日期时间的默认格式("yyyy-MM-dd mm:hh:SS")字符串
     * 
     * @return
     */
    public static final String currDateTimeStr() {
        return currDateTimeStr(now(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 返回当前时间的格式化字符串
     * 
     * @param format
     * @return
     */
    public static final String currDateTimeStr(String format) {
        return currDateTimeStr(now(), format);
    }

    /**
     * 返回指定时间的格式化字符串
     * 
     * @param format
     * @return
     */
    public static final String currDateTimeStr(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static final Date now() {
        return new Date();
    }
}
