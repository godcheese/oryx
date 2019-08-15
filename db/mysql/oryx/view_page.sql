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

 Date: 15/08/2019 10:56:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for view_page
-- ----------------------------
DROP TABLE IF EXISTS `view_page`;
CREATE TABLE `view_page` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '视图页面名称',
  `url` text COMMENT '请求地址（url）',
  `authority` varchar(191) NOT NULL COMMENT '权限（authority）',
  `view_page_category_id` bigint(20) unsigned NOT NULL COMMENT '视图页面分类 id',
  `sort` bigint(20) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `gmt_created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_authority` (`authority`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='视图页面表';

-- ----------------------------
-- Records of view_page
-- ----------------------------
BEGIN;
INSERT INTO `view_page` VALUES (1, 'API 管理', '/user/api/page_all', '/USER/API/PAGE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (2, '数据字典', '/system/dictionary/page_all', '/SYSTEM/DICTIONARY/PAGE_ALL', 2, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (3, '视图页面管理', '/user/view_page/page_all', '/USER/VIEW_PAGE/PAGE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (4, 'Druid Monitor', '/druid', '/DRUID', 2, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (5, '用户管理', '/user/page_all', '/USER/PAGE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (6, '角色管理', '/user/role/page_all', '/USER/ROLE/PAGE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (7, '电子邮件管理', '/mail/page_all', '/MAIL/PAGE_ALL', 2, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (8, '操作日志', '/system/operation_log/page_all', '/SYSTEM/OPERATION_LOG/PAGE_ALL', 2, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (9, '文件管理', '/system/file/page_all', '/SYSTEM/FILE/PAGE_ALL', 2, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (10, '部门管理', '/user/department/list_all', '/USER/DEPARTMENT/LIST_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (11, '视图菜单管理', '/user/view_menu/page_all', '/USER/VIEW_MENU/PAGE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (12, 'Quartz 任务管理', '/quartz/job/page_all', '/QUARTZ/JOB/PAGE_ALL', 4, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (13, 'Quartz 任务日志', '/quartz/job_runtime_log/page_all', '/QUARTZ/JOB_RUNTIME_LOG/PAGE_ALL', 4, 0, '', NULL, NULL);
INSERT INTO `view_page` VALUES (14, '工作台', '/workbench', '/WORKBENCH', 1, 0, '', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
