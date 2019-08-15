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

 Date: 15/08/2019 10:56:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for view_menu_category
-- ----------------------------
DROP TABLE IF EXISTS `view_menu_category`;
CREATE TABLE `view_menu_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '视图菜单分类名称',
  `icon` varchar(255) DEFAULT '' COMMENT '图标（icon）',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级视图菜单分类 id',
  `sort` bigint(20) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `gmt_created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='视图菜单分类表';

-- ----------------------------
-- Records of view_menu_category
-- ----------------------------
BEGIN;
INSERT INTO `view_menu_category` VALUES (1, '系统管理', 'setting', NULL, 0, '', '2019-06-06 02:51:28', '2018-07-01 21:28:04');
INSERT INTO `view_menu_category` VALUES (2, '系统配置', 'setting', 1, 0, '', '2019-06-13 04:16:55', '2018-07-01 21:28:04');
INSERT INTO `view_menu_category` VALUES (3, '用户配置', 'user', 1, 0, '', '2019-06-13 07:22:32', '2018-07-01 21:28:04');
INSERT INTO `view_menu_category` VALUES (4, 'Quartz 任务', 'bars', 2, 0, '', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
