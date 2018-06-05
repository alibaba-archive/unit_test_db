package com.aliyun.kongming.unittest.fit.dbfit.fixture;

/**
 * Wrapper for the query fixture in standalone mode that initialises ordered row
 * comparisons
 */
public class OrderedQueryFixture extends QueryFixture {
    @Override
    protected boolean isOrdered() {
        return true;
    }
}
