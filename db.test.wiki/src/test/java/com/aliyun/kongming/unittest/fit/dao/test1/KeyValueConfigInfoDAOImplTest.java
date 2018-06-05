package com.aliyun.kongming.unittest.fit.dao.test1;

import com.aliyun.kongming.unittest.fit.annotations.DbFit;


import com.aliyun.kongming.unittest.fit.dao.BaseRegionDbDAOTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByName;


public class KeyValueConfigInfoDAOImplTest extends BaseRegionDbDAOTestCase {

    @SpringBeanByName
    private KeyValueConfigInfoDAO keyValueConfigInfoDAO;

    @Test
    @DbFit(when = "KeyValueConfigInfoDAOImplTest.testInsert.when.wiki", then = "KeyValueConfigInfoDAOImplTest.testInsert.then.wiki")
    public void testInsert() {
        KeyValueConfigInfoDO keyValueConfigInfoDO = new KeyValueConfigInfoDO();
        keyValueConfigInfoDO.setConfigKey("best");
        keyValueConfigInfoDO.setConfigValue("messi");
        keyValueConfigInfoDAO.insert(keyValueConfigInfoDO);
    }

    @Test
    @DbFit(when = "KeyValueConfigInfoDAOImplTest.testUpdate.when.wiki", then = "KeyValueConfigInfoDAOImplTest.testUpdate.then.wiki")
    public void testUpdate() {
        KeyValueConfigInfoDO keyValueConfigInfoDO = new KeyValueConfigInfoDO();
        keyValueConfigInfoDO.setConfigKey("best");
        keyValueConfigInfoDO.setConfigValue("messi");
        keyValueConfigInfoDAO.update(keyValueConfigInfoDO);
    }

    @Test
    @DbFit(when = "KeyValueConfigInfoDAOImplTest.testReadByConfigKey.when.wiki")
    public void testReadByConfigKey() {
        String configKey = "best";
        KeyValueConfigInfoDO keyValueConfigInfoDO = keyValueConfigInfoDAO.readByConfigKey(configKey);
        Assert.assertEquals(keyValueConfigInfoDO.getConfigValue(), "messi");
        Assert.assertEquals(keyValueConfigInfoDO.getConfigKey(), configKey);
    }

    @Test
    @DbFit(when = "KeyValueConfigInfoDAOImplTest.testReadByConfigKey.when.wiki")
    public void testReadByConfigKey1() {
        String configKey = "better";
        KeyValueConfigInfoDO keyValueConfigInfoDO = keyValueConfigInfoDAO.readByConfigKey(configKey);
        Assert.assertEquals(keyValueConfigInfoDO.getConfigValue(), "123,234");
        Assert.assertEquals(keyValueConfigInfoDO.getConfigKey(), configKey);
    }

}
