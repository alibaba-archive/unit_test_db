package com.aliyun.kongming.unittest.fit.dbfit.util;

import java.sql.SQLException;

public interface TypeNormaliser {
    Object normalise(Object o) throws SQLException;
}
