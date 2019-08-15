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

 Date: 15/08/2019 10:56:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for view_menu
-- ----------------------------
DROP TABLE IF EXISTS `view_menu`;
CREATE TABLE `view_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '视图菜单名称',
  `icon` varchar(255) DEFAULT '' COMMENT '图标（icon）',
  `url` text COMMENT '请求地址（url）',
  `view_menu_category_id` bigint(20) unsigned NOT NULL COMMENT '视图菜单分类 id',
  `sort` bigint(20) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `gmt_created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='视图菜单表';

-- ----------------------------
-- Records of view_menu
-- ----------------------------
BEGIN;
INSERT INTO `view_menu` VALUES (1, 'API 管理', 'api', '/user/api/page_all', 3, 0, '', '2019-06-06 02:52:53', '2018-07-01 21:28:04');
INSERT INTO `view_menu` VALUES (2, '数据字典', 'book', '/system/dictionary/page_all', 2, 0, '', '2019-06-06 02:51:54', '2018-07-01 21:28:04');
INSERT INTO `view_menu` VALUES (3, '视图页面管理', 'desktop', '/user/view_page/page_all', 3, 0, '', '2019-06-06 02:50:52', '2018-07-01 21:28:04');
INSERT INTO `view_menu` VALUES (4, 'Druid Monitor', 'alibaba', '/druid', 2, 0, '', '2019-06-13 04:16:42', '2018-07-01 21:28:04');
INSERT INTO `view_menu` VALUES (5, '用户管理', 'user', '/user/page_all', 3, 0, '', '2018-07-01 21:28:04', '2018-07-01 21:28:04');
INSERT INTO `view_menu` VALUES (6, '角色管理', 'team', '/user/role/page_all', 3, 0, '', '2019-06-06 03:16:52', '2018-07-01 21:28:04');
INSERT INTO `view_menu` VALUES (7, '电子邮件管理', 'mail', '/mail/page_all', 2, 0, '', '2019-01-21 02:59:02', '2018-07-08 13:22:30');
INSERT INTO `view_menu` VALUES (8, '操作日志', 'exception', '/system/operation_log/page_all', 2, 0, '', '2019-08-07 12:30:57', '2018-08-06 16:47:15');
INSERT INTO `view_menu` VALUES (9, '文件管理', 'file', '/system/file/page_all', 2, 0, '', '2018-10-20 19:07:33', '2018-10-20 19:01:23');
INSERT INTO `view_menu` VALUES (10, '部门管理', 'cluster', '/user/department/list_all', 3, 0, '', '2018-12-20 02:43:28', '2018-12-20 02:43:28');
INSERT INTO `view_menu` VALUES (11, '视图菜单管理', 'bars', '/user/view_menu/page_all', 3, 0, '', '2019-06-13 04:21:31', '2019-06-13 11:37:14');
INSERT INTO `view_menu` VALUES (12, 'Quartz 任务管理', 'bars', '/quartz/job/page_all', 4, 0, '', '2019-06-17 06:31:23', '2019-06-17 06:28:37');
INSERT INTO `view_menu` VALUES (13, 'Quartz 任务日志', 'exception', '/quartz/job_runtime_log/page_all', 4, 0, '', NULL, NULL);
INSERT INTO `view_menu` VALUES (14, '工作台', 'dashboard', '/workbench', 1, 0, '', '2019-07-27 16:11:47', '2019-02-11 01:17:37');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
