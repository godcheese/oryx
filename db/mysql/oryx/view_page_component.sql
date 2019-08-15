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

 Date: 15/08/2019 10:53:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for view_page_component
-- ----------------------------
DROP TABLE IF EXISTS `view_page_component`;
CREATE TABLE `view_page_component` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `view_page_component_type` tinyint(2) NOT NULL COMMENT '视图页面组件类型 id',
  `name` varchar(255) NOT NULL COMMENT '视图页面组件名称',
  `authority` varchar(191) NOT NULL COMMENT '权限（authority）',
  `view_page_id` bigint(20) unsigned NOT NULL COMMENT '视图页面 id',
  `sort` bigint(20) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `gmt_created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_authority` (`authority`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='视图页面组件表';

-- ----------------------------
-- Records of view_page_component
-- ----------------------------
BEGIN;
INSERT INTO `view_page_component` VALUES (1, 1, '新增发送邮件', '/COMPONENT/MAIL/SEND_ONE', 7, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (2, 1, '删除', '/COMPONENT/MAIL/DELETE_ALL', 7, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (3, 1, '删除', '/COMPONENT/MAIL/REFRESH', 7, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (4, 1, '新增', '/COMPONENT/QUARTZ/JOB/ADD_ONE', 12, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (5, 1, '编辑', '/COMPONENT/QUARTZ/JOB/EDIT_ONE', 12, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (6, 1, '删除', '/COMPONENT/QUARTZ/JOB/DELETE_ALL', 12, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (7, 1, '暂停', '/COMPONENT/QUARTZ/JOB/PAUSE_ALL', 12, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (8, 1, '恢复', '/COMPONENT/QUARTZ/JOB/RESUME_ALL', 12, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (9, 1, '刷新', '/COMPONENT/QUARTZ/JOB/REFRESH', 12, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (10, 1, '清空', '/COMPONENT/QUARTZ/JOB_RUNTIME_LOG/CLEAR_ALL', 13, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (11, 1, '新增', '/COMPONENT/SYSTEM/DICTIONARY/DICTIONARY_CATEGORY_ADD_ONE', 2, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (12, 1, '编辑', '/COMPONENT/SYSTEM/DICTIONARY/DICTIONARY_CATEGORY_EDIT_ONE', 2, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (13, 1, '删除', '/COMPONENT/SYSTEM/DICTIONARY/DICTIONARY_CATEGORY_DELETE_ALL', 2, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (14, 1, '新增', '/COMPONENT/SYSTEM/DICTIONARY/DICTIONARY_ADD_ONE', 2, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (15, 1, '编辑', '/COMPONENT/SYSTEM/DICTIONARY/DICTIONARY_EDIT_ONE', 2, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (16, 1, '删除', '/COMPONENT/SYSTEM/DICTIONARY/DICTIONARY_DELETE_ALL', 2, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (17, 1, '同步到内存', '/COMPONENT/SYSTEM/DICTIONARY/DICTIONARY_SYNC_TO_MEMORY', 2, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (18, 1, '单文件上传', '/COMPONENT/SYSTEM/FILE/UPLOAD_ONE', 9, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (19, 1, '编辑', '/COMPONENT/SYSTEM/FILE/EDIT_ONE', 9, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (20, 1, '删除', '/COMPONENT/SYSTEM/FILE/DELETE_ALL', 9, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (21, 1, '清空', '/COMPONENT/SYSTEM/OPERATION_LOG/CLEAR_ALL', 8, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (22, 1, '新增', '/COMPONENT/USER/ADD_ONE', 5, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (23, 1, '编辑', '/COMPONENT/USER/EDIT_ONE', 5, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (24, 1, '删除', '/COMPONENT/USER/FAKE_DELETE_ALL', 5, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (25, 1, '撤销删除', '/COMPONENT/USER/REVOKE_FAKE_DELETE_ALL', 5, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (26, 1, '永久删除', '/COMPONENT/USER/DELETE_ALL', 5, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (27, 1, '角色分配', '/COMPONENT/USER/USER_ROLE_PAGE_ALL', 5, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (28, 1, '新增', '/COMPONENT/USER/API/API_CATEGORY_ADD_ONE', 1, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (29, 1, '编辑', '/COMPONENT/USER/API/API_CATEGORY_EDIT_ONE', 1, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (30, 1, '删除', '/COMPONENT/USER/API/API_CATEGORY_DELETE_ALL', 1, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (31, 1, '新增', '/COMPONENT/USER/API/API_ADD_ONE', 1, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (32, 1, '编辑', '/COMPONENT/USER/API/API_EDIT_ONE', 1, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (33, 1, '删除', '/COMPONENT/USER/API/API_DELETE_ALL', 1, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (34, 1, '新增', '/COMPONENT/USER/DEPARTMENT/ADD_ONE', 10, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (35, 1, '编辑', '/COMPONENT/USER/DEPARTMENT/EDIT_ONE', 10, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (36, 1, '删除', '/COMPONENT/USER/DEPARTMENT/DELETE_ALL', 10, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (37, 1, '新增', '/COMPONENT/USER/ROLE/ADD_ONE', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (38, 1, '编辑', '/COMPONENT/USER/ROLE/EDIT_ONE', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (39, 1, '删除', '/COMPONENT/USER/ROLE/DELETE_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (40, 1, '视图菜单管理', '/COMPONENT/USER/ROLE/VIEW_MENU_PAGE_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (41, 1, '视图页面管理', '/COMPONENT/USER/ROLE/VIEW_PAGE_PAGE_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (42, 1, 'API 管理', '/COMPONENT/USER/ROLE/API_PAGE_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (43, 1, '授权', '/COMPONENT/USER/ROLE/API/API_GRANT_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (44, 1, '撤销授权', '/COMPONENT/USER/ROLE/API/API_REVOKE_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (45, 1, '授权', '/COMPONENT/USER/ROLE/VIEW_MENU/VIEW_MENU_CATEGORY_GRANT_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (46, 1, '撤销授权', '/COMPONENT/USER/ROLE/VIEW_MENU/VIEW_MENU_CATEGORY_REVOKE_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (47, 1, '授权', '/COMPONENT/USER/ROLE/VIEW_MENU/VIEW_MENU_GRANT_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (48, 1, '撤销授权', '/COMPONENT/USER/ROLE/VIEW_MENU/VIEW_MENU_REVOKE_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (49, 1, '授权', '/COMPONENT/USER/ROLE/VIEW_PAGE/VIEW_PAGE_GRANT_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (50, 1, '撤销授权', '/COMPONENT/USER/ROLE/VIEW_PAGE/VIEW_PAGE_REVOKE_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (51, 1, '授权', '/COMPONENT/USER/ROLE/VIEW_PAGE/VIEW_PAGE_COMPONENT_GRANT_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (52, 1, '撤销授权', '/COMPONENT/USER/ROLE/VIEW_PAGE/VIEW_PAGE_COMPONENT_REVOKE_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (53, 1, '授权', '/COMPONENT/USER/USER_ROLE/GRANT_ALL', 5, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (54, 1, '撤销授权', '/COMPONENT/USER/USER_ROLE/REVOKE_ALL', 5, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (55, 1, '新增', '/COMPONENT/USER/VIEW_MENU/VIEW_MENU_CATEGORY_ADD_ONE', 11, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (56, 1, '编辑', '/COMPONENT/USER/VIEW_MENU/VIEW_MENU_CATEGORY_EDIT_ONE', 11, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (57, 1, '删除', '/COMPONENT/USER/VIEW_MENU/VIEW_MENU_CATEGORY_DELETE_ALL', 11, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (58, 1, '新增', '/COMPONENT/USER/VIEW_MENU/VIEW_MENU_ADD_ONE', 11, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (59, 1, '编辑', '/COMPONENT/USER/VIEW_MENU/VIEW_MENU_EDIT_ONE', 11, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (60, 1, '删除', '/COMPONENT/USER/VIEW_MENU/VIEW_MENU_DELETE_ALL', 11, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (61, 1, '新增', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_CATEGORY_ADD_ONE', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (62, 1, '编辑', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_CATEGORY_EDIT_ONE', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (63, 1, '删除', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_CATEGORY_DELETE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (64, 1, '新增', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_ADD_ONE', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (65, 1, '编辑', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_EDIT_ONE', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (66, 1, '删除', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_DELETE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (67, 1, '关联 API', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_API_PAGE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (68, 1, '新增', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_COMPONENT_ADD_ONE', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (69, 1, '编辑', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_COMPONENT_EDIT_ONE', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (70, 1, '删除', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_COMPONENT_DELETE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (71, 1, '关联 API', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_COMPONENT_API_PAGE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (72, 1, '关联', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_API/ASSOCIATE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (73, 1, '撤销关联', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_API/REVOKE_ASSOCIATE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (74, 1, '关联', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_COMPONENT_API/ASSOCIATE_ALL', 3, 0, '', NULL, NULL);
INSERT INTO `view_page_component` VALUES (75, 1, '撤销关联', '/COMPONENT/USER/VIEW_PAGE/VIEW_PAGE_COMPONENT_API/REVOKE_ASSOCIATE_ALL', 3, 0, '', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
