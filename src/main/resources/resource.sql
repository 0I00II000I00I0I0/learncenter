USE `learncenter`;

### 创建数据表users
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

### 创建数据表learnresources
DROP TABLE IF EXISTS `learnresources`;
CREATE TABLE `learnresources` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `author` varchar(20) DEFAULT NULL COMMENT '作者',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `url` varchar(100) DEFAULT NULL COMMENT '地址链接',
  `createdby` varchar(20) DEFAULT NULL COMMENT '添加人',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatedby` varchar(20) DEFAULT NULL COMMENT '修改人',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `userid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE InnoDB DEFAULT CHARSET=utf8mb4;

### 测试数据
insert  into `learnresources`(`id`,`author`,`title`,`url`) values (1,'官方SpriongBoot例子','官方SpriongBoot例子','https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples');
insert  into `learnresources`(`id`,`author`,`title`,`url`) values (2,'龙果学院','Spring Boot 教程系列学习','http://www.roncoo.com/article/detail/124661');
insert  into `learnresources`(`id`,`author`,`title`,`url`) values (3,'嘟嘟MD独立博客','Spring Boot干货系列','http://tengj.top/');
insert  into `learnresources`(`id`,`author`,`title`,`url`) values (4,'后端编程嘟','Spring Boot视频教程','http://www.toutiao.com/m1559096720023553/');
insert  into `learnresources`(`id`,`author`,`title`,`url`) values (5,'程序猿DD','Spring Boot系列','http://www.roncoo.com/article/detail/125488');
insert  into `learnresources`(`id`,`author`,`title`,`url`) values (6,'纯洁的微笑','Sping Boot系列文章','http://www.ityouknow.com/spring-boot');
insert  into `learnresources`(`id`,`author`,`title`,`url`) values (7,'CSDN——小当博客专栏','Sping Boot学习','http://blog.csdn.net/column/details/spring-boot.html');
insert  into `learnresources`(`id`,`author`,`title`,`url`) values (8,'梁桂钊的博客','Spring Boot 揭秘与实战','http://blog.csdn.net/column/details/spring-boot.html');
insert  into `learnresources`(`id`,`author`,`title`,`url`) values (9,'林祥纤博客系列','从零开始学Spring Boot','http://412887952-qq-com.iteye.com/category/356333');
insert  into `learnresources`(`id`,`author`,`title`,`url`) values (10,'杜琪','关于Spring Boot的博客集合','http://www.jianshu.com/p/7e2e5e7b32ab');
