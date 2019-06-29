/*
Navicat MySQL Data Transfer

Source Server         : 172.18.1.33
Source Server Version : 50725
Source Host           : 172.18.1.33:3306
Source Database       : permission_system

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-06-28 16:01:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_application
-- ----------------------------
DROP TABLE IF EXISTS `base_application`;
CREATE TABLE `base_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_user_name` varchar(255) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `update_user_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_l194l66jkbda1g2dw0ylv7h3u` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_dept
-- ----------------------------
DROP TABLE IF EXISTS `base_dept`;
CREATE TABLE `base_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_user_name` varchar(255) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `update_user_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_39bt8dg2kfaiq0g7bys11d525` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_dept_user
-- ----------------------------
DROP TABLE IF EXISTS `base_dept_user`;
CREATE TABLE `base_dept_user` (
  `department_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  KEY `FK64mv6h45ojj506kuondq6bnbx` (`user_id`),
  KEY `FK7ef5nabrbnwjv8r71t28d4afw` (`department_id`),
  CONSTRAINT `FK64mv6h45ojj506kuondq6bnbx` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`),
  CONSTRAINT `FK7ef5nabrbnwjv8r71t28d4afw` FOREIGN KEY (`department_id`) REFERENCES `base_dept` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_login_log
-- ----------------------------
DROP TABLE IF EXISTS `base_login_log`;
CREATE TABLE `base_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_user_name` varchar(255) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `update_user_name` varchar(255) DEFAULT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe7oebdpous7ffea6m39gh98gp` (`user_id`),
  CONSTRAINT `FKe7oebdpous7ffea6m39gh98gp` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=337 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_major
-- ----------------------------
DROP TABLE IF EXISTS `base_major`;
CREATE TABLE `base_major` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_user_name` varchar(255) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `update_user_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_org
-- ----------------------------
DROP TABLE IF EXISTS `base_org`;
CREATE TABLE `base_org` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_user_name` varchar(255) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `update_user_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1cjejdcq8j7331yw8a1l9nv15` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_org_dept
-- ----------------------------
DROP TABLE IF EXISTS `base_org_dept`;
CREATE TABLE `base_org_dept` (
  `org_id` bigint(20) NOT NULL,
  `dept_id` bigint(20) NOT NULL,
  KEY `FK32ue0q00rac4qr0swukd9v4e4` (`dept_id`),
  KEY `FKe4bqmxhotkcdw431am20x0ntu` (`org_id`),
  CONSTRAINT `FK32ue0q00rac4qr0swukd9v4e4` FOREIGN KEY (`dept_id`) REFERENCES `base_dept` (`id`),
  CONSTRAINT `FKe4bqmxhotkcdw431am20x0ntu` FOREIGN KEY (`org_id`) REFERENCES `base_org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_org_role
-- ----------------------------
DROP TABLE IF EXISTS `base_org_role`;
CREATE TABLE `base_org_role` (
  `org_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FKbihnjxjwj1custlxppvyfctw` (`role_id`),
  KEY `FK1egp8tblrhx9rdk7oyt4a7jq4` (`org_id`),
  CONSTRAINT `FK1egp8tblrhx9rdk7oyt4a7jq4` FOREIGN KEY (`org_id`) REFERENCES `base_org` (`id`),
  CONSTRAINT `FKbihnjxjwj1custlxppvyfctw` FOREIGN KEY (`role_id`) REFERENCES `base_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_permission
-- ----------------------------
DROP TABLE IF EXISTS `base_permission`;
CREATE TABLE `base_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_user_name` varchar(255) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `update_user_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `application_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6gcpqueiwtbpcchf48e1dejsr` (`name`),
  UNIQUE KEY `UK_a6hlf6uvvfnltdaatic450j8u` (`url`),
  KEY `FKbpjrmfjmxr11n6c4mnh425lfj` (`application_id`),
  CONSTRAINT `FKbpjrmfjmxr11n6c4mnh425lfj` FOREIGN KEY (`application_id`) REFERENCES `base_application` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_role
-- ----------------------------
DROP TABLE IF EXISTS `base_role`;
CREATE TABLE `base_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_user_name` varchar(255) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `update_user_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q4una43yw61p9mjtk70jti0dy` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `base_role_permission`;
CREATE TABLE `base_role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  KEY `FK5qjbtjxc4q6b7tg51ojok8o98` (`permission_id`),
  KEY `FKh83yq6qohtkv0e6r96tlt7qqs` (`role_id`),
  CONSTRAINT `FK5qjbtjxc4q6b7tg51ojok8o98` FOREIGN KEY (`permission_id`) REFERENCES `base_permission` (`id`),
  CONSTRAINT `FKh83yq6qohtkv0e6r96tlt7qqs` FOREIGN KEY (`role_id`) REFERENCES `base_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_security_group
-- ----------------------------
DROP TABLE IF EXISTS `base_security_group`;
CREATE TABLE `base_security_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_user_name` varchar(255) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `update_user_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cudn264w46nu9xwdq4bsfglps` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_user
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_user_name` varchar(255) DEFAULT NULL,
  `deleted` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `update_user_name` varchar(255) DEFAULT NULL,
  `admin_flag` varchar(255) DEFAULT '0',
  `audit_status` varchar(255) DEFAULT '1',
  `enable_flag` varchar(255) DEFAULT '1',
  `mobile_phone` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_salt` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `major_id` bigint(20) DEFAULT NULL,
  `org_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nt92p9abadrya41y29v851m51` (`username`),
  KEY `FKi8s033nd6skc0wyp5wecrkka0` (`major_id`),
  KEY `FKe80e914rn4a0mosm91arfuiun` (`org_id`),
  CONSTRAINT `FKe80e914rn4a0mosm91arfuiun` FOREIGN KEY (`org_id`) REFERENCES `base_org` (`id`),
  CONSTRAINT `FKi8s033nd6skc0wyp5wecrkka0` FOREIGN KEY (`major_id`) REFERENCES `base_major` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_user_application
-- ----------------------------
DROP TABLE IF EXISTS `base_user_application`;
CREATE TABLE `base_user_application` (
  `user_id` bigint(20) NOT NULL,
  `application_id` bigint(20) NOT NULL,
  KEY `FKbdy50j7u5p5ce8wmd8xgmyo79` (`application_id`),
  KEY `FK9d30i1jl7wqm6l1k0uyoxl1ie` (`user_id`),
  CONSTRAINT `FK9d30i1jl7wqm6l1k0uyoxl1ie` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`),
  CONSTRAINT `FKbdy50j7u5p5ce8wmd8xgmyo79` FOREIGN KEY (`application_id`) REFERENCES `base_application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_user_org
-- ----------------------------
DROP TABLE IF EXISTS `base_user_org`;
CREATE TABLE `base_user_org` (
  `user_id` bigint(20) NOT NULL,
  `org_id` bigint(20) NOT NULL,
  KEY `FKmujckliiafosvebo04gejtuob` (`org_id`),
  KEY `FKmjyc4sgyy778o9dxxtxy3suxy` (`user_id`),
  CONSTRAINT `FKmjyc4sgyy778o9dxxtxy3suxy` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`),
  CONSTRAINT `FKmujckliiafosvebo04gejtuob` FOREIGN KEY (`org_id`) REFERENCES `base_org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_user_role
-- ----------------------------
DROP TABLE IF EXISTS `base_user_role`;
CREATE TABLE `base_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FK7207h5wf1iklhllgpinv1wv59` (`role_id`),
  KEY `FKnyj9tvjsrgcvj8wktia751w1b` (`user_id`),
  CONSTRAINT `FK7207h5wf1iklhllgpinv1wv59` FOREIGN KEY (`role_id`) REFERENCES `base_role` (`id`),
  CONSTRAINT `FKnyj9tvjsrgcvj8wktia751w1b` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_user_security_group
-- ----------------------------
DROP TABLE IF EXISTS `base_user_security_group`;
CREATE TABLE `base_user_security_group` (
  `user_id` bigint(20) NOT NULL,
  `security_group_id` bigint(20) NOT NULL,
  KEY `FKr81vw4c6ilhj0ispdso08aopi` (`security_group_id`),
  KEY `FKai19y8d3kojichlee2r9a3sr6` (`user_id`),
  CONSTRAINT `FKai19y8d3kojichlee2r9a3sr6` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`),
  CONSTRAINT `FKr81vw4c6ilhj0ispdso08aopi` FOREIGN KEY (`security_group_id`) REFERENCES `base_security_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
