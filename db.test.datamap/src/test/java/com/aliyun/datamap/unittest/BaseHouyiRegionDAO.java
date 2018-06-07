package com.aliyun.datamap.unittest;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * @Described：访问后羿regiondb的数据库基类
 * @author YHJ create at 2015年4月15日 上午11:51:12
 * @ClassNmae com.aliyun.houyi.regionmaster.dao.regiondb.BaseHouyiRegionDAO
 */
public class BaseHouyiRegionDAO extends SqlMapClientDaoSupport {

    private SqlMapClientTemplate mirrorRegiondbSqlMapClientTemplate;

    /**
     * 获取镜像数据库的连接模版
     * 
     * @return
     * @Author YHJ create at 2015年4月15日 上午11:53:23
     */
    public SqlMapClientTemplate getMirrorRegiondbSqlMapClientTemplate() {
        return mirrorRegiondbSqlMapClientTemplate;
    }

    public void setMirrorRegiondbSqlMapClientTemplate(SqlMapClientTemplate mirrorRegiondbSqlMapClientTemplate) {
        this.mirrorRegiondbSqlMapClientTemplate = mirrorRegiondbSqlMapClientTemplate;
    }

}
