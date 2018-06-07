package com.aliyun.jtester.module.database.dbop;

import com.aliyun.jtester.module.ICore;

/**
 * 往数据库插入数据操作接口
 * 
 * @author darui.wudr 2013-1-11 下午5:31:57
 */
public interface IInsertOp {
    /**
     * 插入数据操作
     * 
     * @throws Exception
     */
    void insert(String table, ICore.DataMap data) throws Exception;
}
