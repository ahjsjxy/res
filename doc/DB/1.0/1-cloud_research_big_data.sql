/*
Navicat MySQL Data Transfer

Source Server         : 172.18.1.33
Source Server Version : 50725
Source Host           : 172.18.1.33:3306
Source Database       : cloud_research_big_data

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-06-28 15:48:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_hosp_center
-- ----------------------------
DROP TABLE IF EXISTS `base_hosp_center`;
CREATE TABLE `base_hosp_center` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) DEFAULT NULL COMMENT '医疗机构名称',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_hosp_center_dept
-- ----------------------------
DROP TABLE IF EXISTS `base_hosp_center_dept`;
CREATE TABLE `base_hosp_center_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `center_id` bigint(20) NOT NULL COMMENT '医疗机构ID',
  `dept_id` bigint(20) NOT NULL COMMENT '科室ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for base_hosp_center_dept_user
-- ----------------------------
DROP TABLE IF EXISTS `base_hosp_center_dept_user`;
CREATE TABLE `base_hosp_center_dept_user` (
  `center_dept_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  KEY `center_dept_id` (`center_dept_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for base_hosp_dept
-- ----------------------------
DROP TABLE IF EXISTS `base_hosp_dept`;
CREATE TABLE `base_hosp_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) DEFAULT NULL COMMENT '科室名称',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for crf
-- ----------------------------
DROP TABLE IF EXISTS `crf`;
CREATE TABLE `crf` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称(crf/题组)',
  `table_name` varchar(255) DEFAULT NULL COMMENT '数据库表名',
  `type` int(11) DEFAULT NULL COMMENT '表单类型(1:crf; 2:题组)',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `parent_id` bigint(20) NOT NULL COMMENT '父级id',
  `property` int(11) DEFAULT NULL COMMENT 'crf/题组属性（1：对象、2：随访、3：访视、4：其它、0：初始）',
  `level` int(11) DEFAULT NULL COMMENT '层级(1: CRF，2：题组)',
  `generate_to_db` int(11) DEFAULT NULL COMMENT '动态表单是否已创建（0：未创建  1：已创建）',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for crf_dict_field
-- ----------------------------
DROP TABLE IF EXISTS `crf_dict_field`;
CREATE TABLE `crf_dict_field` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `crf_id` bigint(20) NOT NULL COMMENT 'crf题组id(crf的二级ID)',
  `crf_form_id` bigint(20) NOT NULL COMMENT 'crf的id(crf的一级ID)',
  `dict_id` bigint(20) NOT NULL COMMENT '字典id',
  `field_id` bigint(20) NOT NULL COMMENT '字段id',
  `field_name` varchar(500) DEFAULT NULL COMMENT '数据库字段DB列名',
  `generate_to_db` int(11) DEFAULT NULL COMMENT '动态表单字段是否创建（0：未创建  1：已创建）',
  `display` int(11) DEFAULT NULL COMMENT '是否显示(0：显示；1隐藏)',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_code` varchar(32) DEFAULT NULL COMMENT '父级id',
  `code` varchar(32) DEFAULT NULL COMMENT '编码',
  `name` varchar(500) DEFAULT NULL COMMENT '字典名称',
  `property` int(11) DEFAULT NULL COMMENT '字典属性 (1：对象、2：随访、3：访视、4：其它)',
  `level` int(11) DEFAULT NULL COMMENT '层级(1: 一级字典，2：二级字典，-1：初始)',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id（0代表初始化）',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`),
  KEY `index_dictionary_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=807 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for disease
-- ----------------------------
DROP TABLE IF EXISTS `disease`;
CREATE TABLE `disease` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '疾病编码',
  `name` varchar(255) DEFAULT NULL COMMENT '疾病名称',
  `status` int(11) DEFAULT '0' COMMENT '是否开启(0：开启  1：关闭)',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for disease_field
-- ----------------------------
DROP TABLE IF EXISTS `disease_field`;
CREATE TABLE `disease_field` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disease_id` bigint(20) NOT NULL COMMENT '疾病id （疾病表的主键id）',
  `dict_id` bigint(20) NOT NULL COMMENT '字典id',
  `field_id` bigint(20) NOT NULL COMMENT '字段id',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1015 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for field
-- ----------------------------
DROP TABLE IF EXISTS `field`;
CREATE TABLE `field` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dictionary_code` varchar(32) NOT NULL COMMENT '关联的二级字典code',
  `code` varchar(32) DEFAULT NULL COMMENT 'code',
  `label` varchar(500) DEFAULT NULL COMMENT '中文名称',
  `name` varchar(500) DEFAULT NULL COMMENT '英文名称',
  `field_type` int(11) DEFAULT NULL COMMENT '（1：单选题  2：多选题  3：填空  4：日期）',
  `data_type` int(11) DEFAULT NULL COMMENT '数据类型(1:单选; 2:下拉; 4:文本; 5:整数; 6:小数; 7:日期_年月日; 8:日期_年月; 9:时间_时分秒; 10:日期时间_年月日时分秒)',
  `required` varchar(255) DEFAULT NULL COMMENT '是否必填（0：是；1：否）',
  `unit` varchar(255) DEFAULT NULL COMMENT '单位',
  `min_value` decimal(10,0) DEFAULT NULL COMMENT '最小值',
  `max_value` decimal(10,0) DEFAULT NULL COMMENT '最大值',
  `description` varchar(1000) DEFAULT NULL COMMENT '说明',
  `default_value` varchar(255) DEFAULT NULL COMMENT '默认值',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '依赖字段id',
  `status` int(11) DEFAULT '0' COMMENT '是否开启(0：开启  1：关闭)',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`),
  KEY `index_field_dictionary_code` (`dictionary_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5121 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for field_option
-- ----------------------------
DROP TABLE IF EXISTS `field_option`;
CREATE TABLE `field_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `field_id` bigint(20) NOT NULL COMMENT 'field的主键id',
  `field_option` varchar(255) DEFAULT NULL COMMENT '当字段类型是(1：单选题; 2：多选题)时的下拉选项值',
  `display_order` decimal(10,1) DEFAULT NULL COMMENT '下拉选项值的显示顺序',
  `status` int(11) DEFAULT NULL COMMENT '是否启用(0：启用  1：未启用)',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;



-- ----------------------------
-- Table structure for portal_button
-- ----------------------------
DROP TABLE IF EXISTS `portal_button`;
CREATE TABLE `portal_button` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(100) DEFAULT NULL COMMENT '项目下-按钮代码',
  `name` varchar(100) DEFAULT NULL COMMENT '项目下-按钮名称',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for study
-- ----------------------------
DROP TABLE IF EXISTS `study`;
CREATE TABLE `study` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '项目类型 1:前瞻性临床研究 2：回顾性临床研究 3：数据库及其它',
  `name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `test_stage` int(11) DEFAULT NULL COMMENT '试验分期(1: I期临床试验 2:Ⅱ期临床试验 3:Ⅲ期临床试验 4: Ⅳ期临床试验 5:生物等效性试验)',
  `entry_num` int(11) DEFAULT NULL COMMENT '计划录入数量',
  `group_type` int(11) DEFAULT NULL COMMENT '分组设计（0：单臂试验；1：分组实验）',
  `blind_setting` int(11) DEFAULT NULL COMMENT '盲法设置，0-开放，1-单盲，2-双盲',
  `random_way` int(11) DEFAULT NULL COMMENT '随机方式，0-无，1-简单随机，2-分层随机',
  `plan_entry_num` int(11) DEFAULT NULL COMMENT '计划入组数量',
  `treat_time` int(11) DEFAULT NULL COMMENT '治疗时间',
  `follow_time` int(11) DEFAULT NULL COMMENT '随访时间',
  `disease_id` bigint(20) DEFAULT NULL COMMENT '疾病id，关联的disease的主键ID',
  `study_flag` int(11) DEFAULT '0' COMMENT '项目标志(0: 单中心；1：多中心)',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `status` int(11) DEFAULT NULL COMMENT '项目状态(1：创建中、2：已创建)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for study_center
-- ----------------------------
DROP TABLE IF EXISTS `study_center`;
CREATE TABLE `study_center` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `study_id` bigint(20) NOT NULL COMMENT '项目ID',
  `center_id` bigint(20) NOT NULL COMMENT '医疗机构ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for study_crf
-- ----------------------------
DROP TABLE IF EXISTS `study_crf`;
CREATE TABLE `study_crf` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `crf_form_id` bigint(20) NOT NULL COMMENT 'crf表单id',
  `study_id` bigint(20) NOT NULL COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for study_group
-- ----------------------------
DROP TABLE IF EXISTS `study_group`;
CREATE TABLE `study_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `study_id` bigint(20) NOT NULL COMMENT '项目id',
  `name` varchar(255) DEFAULT NULL COMMENT '队列名称',
  `entry_condition` varchar(255) DEFAULT NULL COMMENT '入组条件',
  `exclude_condition` varchar(255) DEFAULT NULL COMMENT '排除条件',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for study_object
-- ----------------------------
DROP TABLE IF EXISTS `study_object`;
CREATE TABLE `study_object` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `study_id` bigint(20) NOT NULL COMMENT '项目id',
  `study_group_id` bigint(20) NOT NULL COMMENT '队列id',
  `center_id` bigint(20) NOT NULL COMMENT '中心ID',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  `study_flag` int(11) DEFAULT '0' COMMENT '项目类型(0: 单中心；1：多中心)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for study_role
-- ----------------------------
DROP TABLE IF EXISTS `study_role`;
CREATE TABLE `study_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `study_id` bigint(20) NOT NULL COMMENT '项目ID',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '角色备注',
  `allow_operate` int(11) DEFAULT NULL COMMENT '是否允许操作(0：允许修改删除  1：禁止修改删除)',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for study_role_button
-- ----------------------------
DROP TABLE IF EXISTS `study_role_button`;
CREATE TABLE `study_role_button` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT 'study_role表的主键ID',
  `button_code` varchar(100) NOT NULL COMMENT '按钮代码(portal_button表的code)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for study_role_user
-- ----------------------------
DROP TABLE IF EXISTS `study_role_user`;
CREATE TABLE `study_role_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT 'study_role表的主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for study_user
-- ----------------------------
DROP TABLE IF EXISTS `study_user`;
CREATE TABLE `study_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `study_id` bigint(20) NOT NULL COMMENT '项目ID',
  `user_id` bigint(20) NOT NULL COMMENT '成员ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for study_user_group_center
-- ----------------------------
DROP TABLE IF EXISTS `study_user_group_center`;
CREATE TABLE `study_user_group_center` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `study_id` bigint(20) NOT NULL COMMENT 'study表的主键id',
  `user_id` bigint(20) NOT NULL COMMENT 'user表的主键id',
  `group_id` bigint(20) NOT NULL COMMENT '队列id',
  `center_id` bigint(20) NOT NULL COMMENT '中心id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;
