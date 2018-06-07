package com.aliyun.datamap.unittest;

public class KeyValueConfigInfoDAOImpl extends BaseHouyiRegionDAO implements KeyValueConfigInfoDAO {

    @Override
    public long insert(KeyValueConfigInfoDO keyValueConfigInfoDO) {
        return (Long) getSqlMapClientTemplate().insert("KeyValueConfigInfo.insert", keyValueConfigInfoDO);
    }

    @Override
    public int update(KeyValueConfigInfoDO keyValueConfigInfoDO) {
        return getSqlMapClientTemplate().update("KeyValueConfigInfo.update", keyValueConfigInfoDO);
    }

    @Override
    public KeyValueConfigInfoDO readByConfigKey(String configKey) {
        return (KeyValueConfigInfoDO) getSqlMapClientTemplate().queryForObject("KeyValueConfigInfo.readByConfigKey",
                configKey);
    }

}
