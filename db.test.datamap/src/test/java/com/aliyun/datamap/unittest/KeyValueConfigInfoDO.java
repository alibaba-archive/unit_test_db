package com.aliyun.datamap.unittest;

import java.util.Date;

/**
 * 数据库 key_value_config_info 表对应的数据对象
 * 
 * @author guotao.tangt
 *
 */
public class KeyValueConfigInfoDO {

	private long id;

	private String configKey;

	private String configValue;

	private Date gmtCreate;

	private Date gmtModify;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	
}
