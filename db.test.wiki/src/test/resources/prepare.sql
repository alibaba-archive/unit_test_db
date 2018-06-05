use mysql;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` VALUES ('localhost','admin','*6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0,'',NULL),('127.0.0.1','admin','*6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0,'',NULL),('::1','admin','*6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0,'',NULL),('%','admin','*6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0,'',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

CREATE DATABASE IF NOT EXISTS test_db DEFAULT CHARACTER SET utf8 ;

grant select,insert,update,delete on test_db.* to admin@localhost identified by "123456";

use test_db;

CREATE TABLE IF NOT EXISTS `key_value_config_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '@desc 主键 ID',
  `config_key` varchar(128) NOT NULL COMMENT '@desc 配置项的 key',
  `config_value` varchar(1024) NOT NULL COMMENT '@desc 配置项的值',
  `gmt_create` datetime NOT NULL COMMENT '@desc 记录创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '@desc 记录最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_key` (`config_key`),
  KEY `idx_config_key` (`config_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='kv配置信息表';
