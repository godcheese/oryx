/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : oryx

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 15/08/2019 10:57:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for api_category
-- ----------------------------
DROP TABLE IF EXISTS `api_category`;
CREATE TABLE `api_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '分类名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级分类 id',
  `sort` bigint(20) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `gmt_created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='API 分类表';

-- ----------------------------
-- Records of api_category
-- ----------------------------
BEGIN;
INSERT INTO `api_category` VALUES (1, '系统管理', NULL, 0, '', '2018-06-20 14:36:43', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (2, '系统配置', 1, 0, '', '2018-06-20 14:36:43', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (3, '用户配置', 1, 0, '', '2018-06-20 14:36:43', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (4, 'API 分类', 3, 0, '', '2019-06-27 11:39:12', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (5, 'API', 3, 0, '', '2019-06-27 11:39:23', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (6, '数据字典分类', 2, 0, '', '2018-06-20 14:36:43', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (7, '数据字典', 2, 0, '', '2018-06-20 14:36:43', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (8, '视图页面关联 API', 3, 0, '', '2019-06-27 11:39:31', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (9, '视图页面分类', 3, 0, '', '2019-06-27 11:39:59', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (10, '视图页面', 3, 0, '', '2019-06-27 11:39:39', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (11, '视图页面组件', 3, 0, '', '2019-06-27 11:39:46', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (12, '视图页面组件关联 API', 3, 0, '', '2019-06-27 11:39:52', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (13, '角色关联权限', 3, 0, '', '2018-06-20 14:36:43', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (14, '角色管理', 3, 0, '', '2018-06-20 14:36:43', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (15, '用户管理', 3, 0, '', '2018-06-20 14:36:43', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (16, '用户关联角色', 3, 0, '', '2018-06-20 14:36:43', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (17, '视图菜单分类', 3, 0, '', '2018-06-20 14:36:43', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (18, '视图菜单', 3, 0, '', '2018-06-20 14:36:43', '2018-06-20 14:36:43');
INSERT INTO `api_category` VALUES (19, '文件管理', 2, 0, '', '2019-06-27 11:38:43', '2019-06-27 11:38:43');
INSERT INTO `api_category` VALUES (20, '部门管理', 2, 0, '', '2019-07-16 12:50:58', '2019-07-16 12:50:03');
INSERT INTO `api_category` VALUES (21, '电子邮件管理', 2, 0, '', '2019-07-16 12:50:37', '2019-07-16 12:50:37');
INSERT INTO `api_category` VALUES (22, '操作日志', 2, 0, '', '2019-08-07 12:45:40', '2019-08-07 12:45:40');
INSERT INTO `api_category` VALUES (23, 'Quartz 任务', 2, 0, '', '2019-08-07 12:46:17', '2019-08-07 12:46:01');
INSERT INTO `api_category` VALUES (24, 'Quartz 任务管理', 21, 0, '', NULL, NULL);
INSERT INTO `api_category` VALUES (25, 'Quartz 任务日志', 21, 0, '', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
