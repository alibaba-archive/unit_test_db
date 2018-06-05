package com.aliyun.datamap.unittest;

/**
 * 数据库 key_value_config_info 表访问封装接口
 * 
 * @author guotao.tangt
 *
 */
public interface KeyValueConfigInfoDAO {
	
	/**
	 * 新增一条记录并返回对应记录的主键ID
	 * 
	 * @param keyValueConfigInfoDO
	 * @return
	 */
	public long insert(KeyValueConfigInfoDO keyValueConfigInfoDO);
	
	/**
	 * 更新指定记录信息并返回本次更新受影响的记录数目
	 * 
	 * @param keyValueConfigInfoDO
	 * @return
	 */
	public int update(KeyValueConfigInfoDO keyValueConfigInfoDO);
	
	/**
	 * 根据配置键值读取对应的数据记录
	 * 
	 * @param configKey
	 * @return
	 */
	public KeyValueConfigInfoDO readByConfigKey(String configKey);
	
}
