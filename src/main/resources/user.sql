USE `learncenter`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(20) DEFAULT NULL COMMENT '账号',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `realname` varchar(20) DEFAULT NULL COMMENT '姓名',
  `phonenumber` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `logintime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `ip` varchar(20) DEFAULT NULL COMMENT '登陆IP',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE InnoDB DEFAULT CHARSET=utf8mb4;