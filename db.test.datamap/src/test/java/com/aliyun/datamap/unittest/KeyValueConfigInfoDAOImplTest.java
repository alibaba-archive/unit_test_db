package com.aliyun.datamap.unittest;

import java.util.Date;

import com.aliyun.jtester.module.ICore;
import com.aliyun.jtester.module.database.IDatabase;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByName;

public class KeyValueConfigInfoDAOImplTest extends BaseRegionDbDAOTestCase {

    private final String          TABLE = "key_value_config_info";
    @SpringBeanByName
    private KeyValueConfigInfoDAO keyValueConfigInfoDAO;

    @Test
    public void testInsert() {
        IDatabase.db.table(TABLE).clean();
        KeyValueConfigInfoDO keyValueConfigInfoDO = new KeyValueConfigInfoDO();
        keyValueConfigInfoDO.setConfigKey("best");
        keyValueConfigInfoDO.setConfigValue("messi");
        keyValueConfigInfoDAO.insert(keyValueConfigInfoDO);
        IDatabase.db.table(TABLE).query().sizeEq(1).propertyEq("config_key", "best");
    }

    @Test
    public void testUpdate() {
        IDatabase.db.table(TABLE).clean().insert(2, new DataMap() {
            {
                this.put("config_key", "best1", "best");
                this.put("config_value", "raw_value");
                this.put("gmt_create", new Date());
                this.put("gmt_modify", new Date());
            }
        });
        KeyValueConfigInfoDO keyValueConfigInfoDO = new KeyValueConfigInfoDO();
        keyValueConfigInfoDO.setConfigKey("best");
        keyValueConfigInfoDO.setConfigValue("messi");
        keyValueConfigInfoDAO.update(keyValueConfigInfoDO);
        IDatabase.db.table(TABLE).query().sizeEq(2);
        IDatabase.db.table(TABLE).queryWhere("config_key='best'").sizeEq(1).propertyEq("config_value", "messi");
        IDatabase.db.table(TABLE).queryWhere("config_key='best1'").sizeEq(1).propertyEq("config_value", "raw_value");
    }

    @Test
    public void testReadByConfigKey() {
        IDatabase.db.table(TABLE).clean().insert(new DataMap() {
            {
                this.put("config_key", "best");
                this.put("config_value", "messi");
                this.put("gmt_create", new Date());
                this.put("gmt_modify", new Date());
            }
        });
        String configKey = "best";
        KeyValueConfigInfoDO keyValueConfigInfoDO = keyValueConfigInfoDAO.readByConfigKey(configKey);
        Assert.assertEquals(keyValueConfigInfoDO.getConfigValue(), "messi");
        Assert.assertEquals(keyValueConfigInfoDO.getConfigKey(), configKey);
    }
}
