package com.aliyun.kongming.unittest.fit.dbfit.util;

import java.math.BigInteger;

/**
 * Created by chao.qianc on 2016/6/6.
 */
public class BigIntegerParseDelegate {
    public static Object parse(String s) {
        return new BigInteger(s);
    }
}
