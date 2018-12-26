/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : cbdp

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2018-06-20 15:38:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for c_cabinet
-- ----------------------------
DROP TABLE IF EXISTS `c_cabinet`;
CREATE TABLE `c_cabinet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '配电室名称',
  `desc` varchar(255) NOT NULL COMMENT '描述',
  `status` tinyint(1) NOT NULL COMMENT '状态；1：在用；4：删除',
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_cabinet
-- ----------------------------

-- ----------------------------
-- Table structure for c_cabinet_data
-- ----------------------------
DROP TABLE IF EXISTS `c_cabinet_data`;
CREATE TABLE `c_cabinet_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cabinetId` int(11) NOT NULL COMMENT '配电室id',
  `type` tinyint(1) NOT NULL COMMENT '类型：1-A;2-B;3-C',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `voltage` float NOT NULL COMMENT '电压',
  `current` float NOT NULL COMMENT '电流',
  `power` float NOT NULL COMMENT '电能',
  `inTem` float NOT NULL COMMENT '进线温度',
  `outTem` float NOT NULL COMMENT '出线温度',
  `createTime` datetime NOT NULL COMMENT '数据时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_cabinet_data
-- ----------------------------

-- ----------------------------
-- Table structure for c_resource
-- ----------------------------
DROP TABLE IF EXISTS `c_resource`;
CREATE TABLE `c_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `pId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_resource
-- ----------------------------
INSERT INTO `c_resource` VALUES ('1', 'root', '根目录', '根目录', '1', 'blank', '0');
INSERT INTO `c_resource` VALUES ('3', '11', '11', '111', '4', '1111', '1');
INSERT INTO `c_resource` VALUES ('4', '哈哈', '科研项目管理员', 'qq11', '4', 'https://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack', '3');
INSERT INTO `c_resource` VALUES ('5', '2', '22', '222', '4', 'https://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack', '1');
INSERT INTO `c_resource` VALUES ('6', '21', '21', '21', '4', '21', '5');
INSERT INTO `c_resource` VALUES ('7', '22', '22', '22', '4', '221', '5');
INSERT INTO `c_resource` VALUES ('8', 'cabinet', '配电柜', '配电柜模块', '1', 'blank', '1');
INSERT INTO `c_resource` VALUES ('9', 'transformer', '变压器', '变压器模块', '1', 'blank', '1');
INSERT INTO `c_resource` VALUES ('10', 'cabinetManager', '配电柜管理', '配电柜l列表页', '1', 'jsp/cabinet_list.jsp', '8');
INSERT INTO `c_resource` VALUES ('11', 'transformerManager', '变压器管理', '变压器列表页', '1', 'jsp/transformer_list.jsp', '9');
INSERT INTO `c_resource` VALUES ('12', 'resource', '资源', '资源模块', '1', 'blank', '1');
INSERT INTO `c_resource` VALUES ('13', 'resourceManager', '资源管理', '资源管理页面', '1', 'jsp/resources.jsp', '12');
INSERT INTO `c_resource` VALUES ('14', 'role', '角色', '角色模块', '1', 'blank', '1');
INSERT INTO `c_resource` VALUES ('15', 'roleManager', '角色管理', '角色列表页', '1', 'jsp/role_list.jsp', '14');
INSERT INTO `c_resource` VALUES ('16', 'system', '系统', '系统菜单', '1', 'blank', '1');
INSERT INTO `c_resource` VALUES ('17', 'test', 'test', 'test', '1', 'test', '16');

-- ----------------------------
-- Table structure for c_role
-- ----------------------------
DROP TABLE IF EXISTS `c_role`;
CREATE TABLE `c_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) NOT NULL,
  `roleName` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  `rIds` varchar(255) DEFAULT NULL COMMENT '资源id集合',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_role
-- ----------------------------
INSERT INTO `c_role` VALUES ('0', 'admin', '超级管理员', '超级管理员', '1', '1,8,10,9,11,12,13,14,15,16,17,');
INSERT INTO `c_role` VALUES ('2', 'dm', '部门管理员', '部门管理员', '1', '1,8,10,9,11,');

-- ----------------------------
-- Table structure for c_transformer
-- ----------------------------
DROP TABLE IF EXISTS `c_transformer`;
CREATE TABLE `c_transformer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '变压器名称',
  `department` varchar(255) NOT NULL COMMENT '部门',
  `type` varchar(255) NOT NULL COMMENT '类型',
  `address` varchar(255) NOT NULL,
  `status` int(11) NOT NULL COMMENT '1：在用；4：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_transformer
-- ----------------------------
INSERT INTO `c_transformer` VALUES ('2', 'dage11', 'haha1', '普通', '2222211', '1');
INSERT INTO `c_transformer` VALUES ('3', '科研普通', '到底1', '普通', '111111', '1');
INSERT INTO `c_transformer` VALUES ('4', '科研项目管理员', '到底', '普通', '111111', '1');
INSERT INTO `c_transformer` VALUES ('5', 'hehe', 'hehe', 'hehe', '111111', '1');
INSERT INTO `c_transformer` VALUES ('6', 'hehe', 'hehe', 'hehe', '111111', '1');
INSERT INTO `c_transformer` VALUES ('7', 'hehe', 'hehe', 'hehe', '111111', '1');
INSERT INTO `c_transformer` VALUES ('8', 'hehe', 'hehe', 'hehe', '111111', '1');
INSERT INTO `c_transformer` VALUES ('9', 'hehe', 'hehe', 'hehe', '111111', '1');
INSERT INTO `c_transformer` VALUES ('10', 'hehe', 'hehe', 'hehe', '111111', '1');
INSERT INTO `c_transformer` VALUES ('11', 'hehe', 'hehe', 'hehe', '111111', '1');
INSERT INTO `c_transformer` VALUES ('12', 'hehe', 'hehe', '普通', '111111', '4');
INSERT INTO `c_transformer` VALUES ('13', 'hehe', 'hehe', '普通1', '111111', '1');
INSERT INTO `c_transformer` VALUES ('14', 'hehe', 'hehe', 'hehe', '111111', '1');
INSERT INTO `c_transformer` VALUES ('15', 'hehe', 'hehe', 'hehe', '111111', '1');
INSERT INTO `c_transformer` VALUES ('16', '我是变压器', '特气', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('17', '我是变压器', '特气', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('18', '我是变压器', '特气', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('19', '科研项目管理员', '按时', '普通', '13623', '1');
INSERT INTO `c_transformer` VALUES ('20', '科研项目管理员', '按时', '普通', '13623', '1');
INSERT INTO `c_transformer` VALUES ('21', '哈哈', '快的打交道', '普通', '是', '1');
INSERT INTO `c_transformer` VALUES ('22', '科研项目管理', '到底1', '普通', '阿斯顿发1', '1');
INSERT INTO `c_transformer` VALUES ('23', '的乏二姨太', '到底', '普通为', '4223', '1');
INSERT INTO `c_transformer` VALUES ('24', '水电费哈尔和', '儿童文化的高房价', '啥是', '235234', '1');
INSERT INTO `c_transformer` VALUES ('25', 'sdhsdg', '使得法国', '普通', '使得法国', '1');
INSERT INTO `c_transformer` VALUES ('26', '科研普通', 'af', '普通', 'sd', '1');
INSERT INTO `c_transformer` VALUES ('27', '科研项目管理员', '儿童文化的高房价', '普通', '2345', '1');
INSERT INTO `c_transformer` VALUES ('28', '按时发生的退货', '而英文', '普通', '36234762', '1');
INSERT INTO `c_transformer` VALUES ('29', '科研项目管理员', '345', '普通', '54', '1');
INSERT INTO `c_transformer` VALUES ('30', '科研项目管理员', '儿童文化的高房价', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('31', '啊阿打发打发', 'Asher', '普通', '4837', '1');
INSERT INTO `c_transformer` VALUES ('32', '科研项目管理员', '儿童文化的高房价', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('33', '科研项目管理员', '到底', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('34', '科研项目管理员', '到底', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('35', '科研项目管理员', '到底', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('36', '科研项目管理员', '儿童文化的高房价', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('37', '科研项目管理员', '儿童文化的高房价', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('38', '科研项目管理员', '到底', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('39', '科研项目管理员', '到底', '普通', '235234', '1');
INSERT INTO `c_transformer` VALUES ('40', '科研项目管理员', '儿童文化的高房', '普通', '235234', '1');

-- ----------------------------
-- Table structure for c_transformer_data
-- ----------------------------
DROP TABLE IF EXISTS `c_transformer_data`;
CREATE TABLE `c_transformer_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transformerId` int(11) NOT NULL COMMENT '变压器id',
  `tem` float NOT NULL COMMENT '温度',
  `createTime` datetime NOT NULL COMMENT '数据时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_transformer_data
-- ----------------------------

-- ----------------------------
-- Table structure for c_user
-- ----------------------------
DROP TABLE IF EXISTS `c_user`;
CREATE TABLE `c_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) NOT NULL,
  `realName` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `createTime` datetime NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_user
-- ----------------------------
INSERT INTO `c_user` VALUES ('0', 'admin', '超级管理员', 'admin@cdwoo.com', '21232f297a57a5a743894a0e4a801fc3', '2018-06-07 10:26:08', '1');
