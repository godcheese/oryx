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

 Date: 15/08/2019 10:57:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for api
-- ----------------------------
DROP TABLE IF EXISTS `api`;
CREATE TABLE `api` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT 'API 名称',
  `url` text COMMENT '请求地址（url）',
  `authority` varchar(191) NOT NULL COMMENT '权限（authority）',
  `api_category_id` bigint(20) unsigned NOT NULL COMMENT 'API 分类 id',
  `sort` bigint(20) unsigned DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `gmt_created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_authority` (`authority`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='API 表';

-- ----------------------------
-- Records of api
-- ----------------------------
BEGIN;
INSERT INTO `api` VALUES (1, '分页获取所有电子邮件队列', '/api/mail/page_all', '/API/MAIL/PAGE_ALL', 21, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (2, '新增电子邮件', '/api/mail/add_one', '/API/MAIL/ADD_ONE', 21, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (3, '指定电子邮件 id，获取电子邮件', '/api/mail/one', '/API/MAIL/ONE', 21, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (4, '指定队列电子邮件 id，批量删除队列电子邮件', '/api/mail/delete_all', '/API/MAIL/DELETE_ALL', 21, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (5, '新增任务', '/api/quartz/job/add_one', '/API/QUARTZ/JOB/ADD_ONE', 24, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (6, '指定 JobClassName、JobGroup，获取任务', '/api/quartz/job/one', '/API/QUARTZ/JOB/ONE', 24, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (7, '分页获取所有任务', '/api/quartz/job/page_all', '/API/QUARTZ/JOB/PAGE_ALL', 24, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (8, '指定 JobClassName list、JobGroup list，暂停所有任务', '/api/quartz/job/pause_all', '/API/QUARTZ/JOB/PAUSE_ALL', 24, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (9, '指定 JobClassName list、JobGroup list，恢复所有任务', '/api/quartz/job/resume_all', '/API/QUARTZ/JOB/RESUME_ALL', 24, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (10, '指定 JobClassName list、JobGroup list，删除所有任务', '/api/quartz/job/delete_all', '/API/QUARTZ/JOB/DELETE_ALL', 24, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (11, '保存任务', '/api/quartz/job/save_one', '/API/QUARTZ/JOB/SAVE_ONE', 24, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (12, '指定任务运行日志 id，获取任务运行日志', '/api/quartz/job_runtime_log/one', '/API/QUARTZ/JOB_RUNTIME_LOG/ONE', 25, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (13, '分页获取所有任务运行日志', '/api/quartz/job_runtime_log/page_all', '/API/QUARTZ/JOB_RUNTIME_LOG/PAGE_ALL', 25, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (14, '清空所有任务运行日志', '/api/quartz/job_runtime_log/clear_all', '/API/QUARTZ/JOB_RUNTIME_LOG/CLEAR_ALL', 25, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (15, '新增数据字典分类', '/api/system/dictionary_category/add_one', '/API/SYSTEM/DICTIONARY_CATEGORY/ADD_ONE', 6, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (16, '保存数据字典分类', '/api/system/dictionary_category/save_one', '/API/SYSTEM/DICTIONARY_CATEGORY/SAVE_ONE', 6, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (17, '指定数据字典分类 id list，批量删除数据字典分类', '/api/system/dictionary_category/delete_all', '/API/SYSTEM/DICTIONARY_CATEGORY/DELETE_ALL', 6, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (18, '指定数据字典分类 id，获取数据字典分类', '/api/system/dictionary_category/one', '/API/SYSTEM/DICTIONARY_CATEGORY/ONE', 6, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (19, '获取所有数据字典分类，以 Antd Table 形式展示', '/api/system/dictionary_category/list_all_as_antd_table', '/API/SYSTEM/DICTIONARY_CATEGORY/LIST_ALL_AS_ANTD_TABLE', 6, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (20, '获取所有数据字典分类，以 Antd TreeNode 形式展示', '/api/system/dictionary_category/list_all_as_antd_tree_node', '/API/SYSTEM/DICTIONARY_CATEGORY/LIST_ALL_AS_ANTD_TREE_NODE', 6, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (21, '新增数据字典', '/api/system/dictionary/add_one', '/API/SYSTEM/DICTIONARY/ADD_ONE', 7, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (22, '保存数据字典', '/api/system/dictionary/save_one', '/API/SYSTEM/DICTIONARY/SAVE_ONE', 7, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (23, '指定数据字典 id，批量删除数据字典', '/api/system/dictionary/delete_all', '/API/SYSTEM/DICTIONARY/DELETE_ALL', 7, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (24, '指定数据字典 id，获取数据字典', '/api/system/dictionary/one', '/API/SYSTEM/DICTIONARY/ONE', 7, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (25, '指定数据字典键，从内存中获取所有数据字典', '/api/system/dictionary/list_all_by_key', '/API/SYSTEM/DICTIONARY/LIST_ALL_BY_KEY', 7, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (26, '同步数据字典到内存', '/api/system/dictionary/sync_to_memory', '/API/SYSTEM/DICTIONARY/SYNC_TO_MEMORY', 7, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (27, '指定数据字典分类 id list，导出数据字典', '/api/system/dictionary/export_all_by_dictionary_category_id_list', '/API/SYSTEM/DICTIONARY/EXPORT_ALL_BY_DICTIONARY_CATEGORY_ID_LIST', 7, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (28, '指定数据字典分类 id，导入数据字典', '/api/system/dictionary/import_all_by_dictionary_category_id', '/API/SYSTEM/DICTIONARY/IMPORT_ALL_BY_DICTIONARY_CATEGORY_ID', 7, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (29, '指定数据字典分类 id，分页获取数据字典', '/api/system/dictionary/page_all_by_dictionary_category_id_list', '/API/SYSTEM/DICTIONARY/PAGE_ALL_BY_DICTIONARY_CATEGORY_ID_LIST', 7, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (30, '分页获取所有文件', '/api/system/file/page_all', '/API/SYSTEM/FILE/PAGE_ALL', 19, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (31, '单个文件上传', '/api/system/file/upload_one', '/API/SYSTEM/FILE/UPLOAD_ONE', 19, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (32, '保存文件', '/api/system/file/save_one', '/API/SYSTEM/FILE/SAVE_ONE', 19, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (33, '指定文件 id list，批量删除文件', '/api/system/file/delete_all', '/API/SYSTEM/FILE/DELETE_ALL', 19, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (34, '指定文件 id，获取文件', '/api/system/file/one', '/API/SYSTEM/FILE/ONE', 19, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (35, '指定文件 guid，下载文件', '/api/system/file/download', '/API/SYSTEM/FILE/DOWNLOAD', 19, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (36, '分页获取所有图片文件', '/api/system/file/page_all_image', '/API/SYSTEM/FILE/PAGE_ALL_IMAGE', 19, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (37, '分页获取所有操作日志', '/api/system/operation_log/page_all', '/API/SYSTEM/OPERATION_LOG/PAGE_ALL', 22, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (38, '指定操作日志 id，获取操作日志', '/api/system/operation_log/one', '/API/SYSTEM/OPERATION_LOG/ONE', 22, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (39, '清空所有操作日志', '/api/system/operation_log/clear_all', '/API/SYSTEM/OPERATION_LOG/CLEAR_ALL', 22, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (40, '获取验证码', '/api/system/verify_code', '/API/SYSTEM/VERIFY_CODE', 2, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (41, '获取系统信息', '/api/system/system_info', '/API/SYSTEM/SYSTEM_INFO', 2, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (42, '新增 API 分类', '/api/user/api_category/add_one', '/API/USER/API_CATEGORY/ADD_ONE', 4, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (43, '保存 API 分类', '/api/user/api_category/save_one', '/API/USER/API_CATEGORY/SAVE_ONE', 4, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (44, '指定 API 分类 id list，批量删除 API 分类', '/api/user/api_category/delete_all', '/API/USER/API_CATEGORY/DELETE_ALL', 4, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (45, '指定 API 分类 id，获取所有 API 分类', '/api/user/api_category/one', '/API/USER/API_CATEGORY/ONE', 4, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (46, '获取所有 API 分类，以 Antd Table 形式展示', '/api/user/api_category/list_all_as_antd_table', '/API/USER/API_CATEGORY/LIST_ALL_AS_ANTD_TABLE', 4, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (47, '指定 API 分类 id，分页获取所有 API', '/api/user/api/page_all_by_api_category_id', '/API/USER/API/PAGE_ALL_BY_API_CATEGORY_ID', 5, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (48, '新增 API', '/api/user/api/add_one', '/API/USER/API/ADD_ONE', 5, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (49, '指定 API id list，批量删除 API', '/api/user/api/delete_all', '/API/USER/API/DELETE_ALL', 5, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (50, '指定 API id，获取所有 API', '/api/user/api/one', '/API/USER/API/ONE', 5, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (51, '指定角色 id、API 分类 id list，分页获取所有 API，以 Antd Table 形式展示', '/api/user/api/page_all_as_antd_table_by_role_id_and_api_category_id_list', '/API/USER/API/PAGE_ALL_AS_ANTD_TABLE_BY_ROLE_ID_AND_API_CATEGORY_ID_LIST', 5, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (52, '指定 API 分类 id list，分页获取所有 API，以 Antd Table 形式展示', '/api/user/api/page_all_as_antd_table_by_api_category_id_list', '/API/USER/API/PAGE_ALL_AS_ANTD_TABLE_BY_API_CATEGORY_ID_LIST', 5, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (53, '指定角色 id、API id list，批量授权', '/api/user/api/grant_all_by_role_id_and_api_id_list', '/API/USER/API/GRANT_ALL_BY_ROLE_ID_AND_API_ID_LIST', 5, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (54, '指定角色 id、API id list，批量撤销授权', '/api/user/api/revoke_all_by_role_id_and_api_id_list', '/API/USER/API/REVOKE_ALL_BY_ROLE_ID_AND_API_ID_LIST', 5, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (55, '指定视图页面 id、API 分类 id list，分页获取所有 API，以 Antd Table 形式展示', '/api/user/api/page_all_as_antd_table_by_view_page_id_and_api_category_id_list', '/API/USER/API/PAGE_ALL_AS_ANTD_TABLE_BY_VIEW_PAGE_ID_AND_API_CATEGORY_ID_LIST', 5, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (56, '指定视图页面组件 id、API 分类 id list，分页获取 API，以 Antd Table 形式展示', '/api/user/api/page_all_as_antd_table_by_view_page_component_id_and_api_category_id_list', '/API/USER/API/PAGE_ALL_AS_ANTD_TABLE_BY_VIEW_PAGE_COMPONENT_ID_AND_API_CATEGORY_ID_LIST', 5, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (57, '新增部门', '/api/user/department/add_one', '/API/USER/DEPARTMENT/ADD_ONE', 20, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (58, '保存部门', '/api/user/department/save_one', '/API/USER/DEPARTMENT/SAVE_ONE', 20, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (59, '指定部门 id list，批量删除部门', '/api/user/department/delete_all', '/API/USER/DEPARTMENT/DELETE_ALL', 20, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (60, '指定部门 id，获取部门', '/api/user/department/one', '/API/USER/DEPARTMENT/ONE', 20, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (61, '获取所有部门，以 Antd TreeNode 形式展示', '/api/user/department/list_all_as_antd_tree_node', '/API/USER/DEPARTMENT/LIST_ALL_AS_ANTD_TREE_NODE', 20, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (62, '获取所有部门，以 Antd Tree 形式展示', '/api/user/department/list_all_as_antd_tree', '/API/USER/DEPARTMENT/LIST_ALL_AS_ANTD_TREE', 20, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (63, '获取所有部门，以 Antd Table 形式展示', '/api/user/department/list_all_as_antd_table', '/API/USER/DEPARTMENT/LIST_ALL_AS_ANTD_TABLE', 20, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (64, '分页获取所有角色', '/api/user/role/page_all', '/API/USER/ROLE/PAGE_ALL', 14, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (65, '新增角色', '/api/user/role/add_one', '/API/USER/ROLE/ADD_ONE', 14, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (66, '指定角色 id list，批量删除角色', '/api/user/role/delete_all', '/API/USER/ROLE/DELETE_ALL', 14, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (67, '指定角色 id，获取角色', '/api/user/role/one', '/API/USER/ROLE/ONE', 14, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (68, '指定用户 id，分页获取所有角色，以 Antd Table 形式展示', '/api/user/role/page_all_as_antd_table_by_user_id', '/API/USER/ROLE/PAGE_ALL_AS_ANTD_TABLE_BY_USER_ID', 14, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (69, '指定用户 id、角色 id list，批量授权', '/api/user/role/grant_all_by_user_id_and_role_id_list', '/API/USER/ROLE/GRANT_ALL_BY_USER_ID_AND_ROLE_ID_LIST', 14, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (70, '指定用户 id、角色 id list，批量撤销授权', '/api/user/role/revoke_all_by_user_id_and_role_id_list', '/API/USER/ROLE/REVOKE_ALL_BY_USER_ID_AND_ROLE_ID_LIST', 14, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (71, '分页获取所有用户', '/api/user/page_all', '/API/USER/PAGE_ALL', 15, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (72, '指定部门 id，分页获取所有用户', '/api/user/page_all_by_department_id', '/API/USER/PAGE_ALL_BY_DEPARTMENT_ID', 15, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (73, '新增用户', '/api/user/add_one', '/API/USER/ADD_ONE', 15, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (74, '保存用户', '/api/user/save_one', '/API/USER/SAVE_ONE', 15, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (75, '指定用户 id list，删除用户', '/api/user/fake_delete_all', '/API/USER/FAKE_DELETE_ALL', 15, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (76, '指定用户 id list，撤销删除用户', '/api/user/revoke_fake_delete_all', '/API/USER/REVOKE_FAKE_DELETE_ALL', 15, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (77, '指定用户 id list，批量永久删除用户', '/api/user/delete_all', '/API/USER/DELETE_ALL', 15, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (78, '指定用户 id，获取用户（除密码和角色）', '/api/user/one', '/API/USER/ONE', 15, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (79, '获取当前用户（用户 id、用户名、头像、电子邮箱、权限、部门）', '/api/user/get_current_user', '/API/USER/GET_CURRENT_USER', 15, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (80, '分页获取所有用户角色', '/api/user/user_role/page_all', '/API/USER/USER_ROLE/PAGE_ALL', 16, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (81, '新增用户角色', '/api/user/user_role/add_one', '/API/USER/USER_ROLE/ADD_ONE', 16, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (82, '指定用户 id、角色 id list，批量删除用户角色', '/api/user/user_role/delete_all_by_user_id_and_role_id_list', '/API/USER/USER_ROLE/DELETE_ALL_BY_USER_ID_AND_ROLE_ID_LIST', 16, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (83, '指定角色 id，分页获取所有父级视图菜单分类', '/api/user/view_menu_category/page_all_parent_by_role_id', '/API/USER/VIEW_MENU_CATEGORY/PAGE_ALL_PARENT_BY_ROLE_ID', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (84, '指定父级视图菜单分类 id、角色 id，获取所有视图菜单分类', '/api/user/view_menu_category/list_all_by_parent_id_and_role_id', '/API/USER/VIEW_MENU_CATEGORY/LIST_ALL_BY_PARENT_ID_AND_ROLE_ID', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (85, '新增视图菜单分类', '/api/user/view_menu_category/add_one', '/API/USER/VIEW_MENU_CATEGORY/ADD_ONE', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (86, '保存视图菜单分类', '/api/user/view_menu_category/save_one', '/API/USER/VIEW_MENU_CATEGORY/SAVE_ONE', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (87, '指定视图菜单分类 id，批量删除视图菜单分类', '/api/user/view_menu_category/delete_all', '/API/USER/VIEW_MENU_CATEGORY/DELETE_ALL', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (88, '指定视图菜单分类 id，获取视图菜单分类', '/api/user/view_menu_category/one', '/API/USER/VIEW_MENU_CATEGORY/ONE', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (89, '指定角色 id，获取所有父级视图菜单分类', '/api/user/view_menu_category/list_all_parent_by_role_id', '/API/USER/VIEW_MENU_CATEGORY/LIST_ALL_PARENT_BY_ROLE_ID', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (90, '指定用户 id，获取所有父级视图菜单分类', '/api/user/view_menu_category/list_all_parent_by_user_id', '/API/USER/VIEW_MENU_CATEGORY/LIST_ALL_PARENT_BY_USER_ID', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (91, '指定用户 id、父级视图菜单分类 id，获取所有子级视图菜单分类', '/api/user/view_menu_category/list_all_child_by_parent_id_and_user_id', '/API/USER/VIEW_MENU_CATEGORY/LIST_ALL_CHILD_BY_PARENT_ID_AND_USER_ID', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (92, '获取所有视图菜单分类', '/api/user/view_menu_category/list_all', '/API/USER/VIEW_MENU_CATEGORY/LIST_ALL', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (93, '指定视图菜单分类名称，模糊搜索获取所有视图菜单分类', '/api/user/view_menu_category/search_all_by_name', '/API/USER/VIEW_MENU_CATEGORY/SEARCH_ALL_BY_NAME', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (94, '指定角色 id，获取视图菜单分类，以 Antd Table 形式展示', '/api/user/view_menu_category/list_all_as_antd_table_by_role_id', '/API/USER/VIEW_MENU_CATEGORY/LIST_ALL_AS_ANTD_TABLE_BY_ROLE_ID', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (95, '获取视图菜单分类，以 Antd Table 形式展示', '/api/user/view_menu_category/list_all_as_antd_table', '/API/USER/VIEW_MENU_CATEGORY/LIST_ALL_AS_ANTD_TABLE', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (96, '获取所有视图菜单分类，以 Antd TreeNode 形式展示', '/api/user/view_menu_category/list_all_as_antd_tree_node', '/API/USER/VIEW_MENU_CATEGORY/LIST_ALL_AS_ANTD_TREE_NODE', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (97, '指定角色 id、视图菜单分类 id list，批量授权', '/api/user/view_menu_category/grant_all_by_role_id_and_view_menu_category_id_list', '/API/USER/VIEW_MENU_CATEGORY/GRANT_ALL_BY_ROLE_ID_AND_VIEW_MENU_CATEGORY_ID_LIST', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (98, '指定角色 id、视图菜单分类 id list，批量撤销授权', '/api/user/view_menu_category/revoke_all_by_role_id_and_view_menu_category_id_list', '/API/USER/VIEW_MENU_CATEGORY/REVOKE_ALL_BY_ROLE_ID_AND_VIEW_MENU_CATEGORY_ID_LIST', 17, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (99, '指定视图菜单分类 id、角色 id，分页获取所有视图菜单', '/api/user/view_menu/page_all_by_view_menu_category_id_and_role_id', '/API/USER/VIEW_MENU/PAGE_ALL_BY_VIEW_MENU_CATEGORY_ID_AND_ROLE_ID', 18, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (100, '新增视图菜单', '/api/user/view_menu/add_one', '/API/USER/VIEW_MENU/ADD_ONE', 18, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (101, '保存视图菜单', '/api/user/view_menu/save_one', '/API/USER/VIEW_MENU/SAVE_ONE', 18, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (102, '指定视图菜单 id list，批量删除视图菜单', '/api/user/view_menu/delete_all', '/API/USER/VIEW_MENU/DELETE_ALL', 18, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (103, '指定视图菜单 id，获取视图菜单', '/api/user/view_menu/one', '/API/USER/VIEW_MENU/ONE', 18, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (104, '指定视图菜单名称，模糊搜索获取所有视图菜单', '/api/user/view_menu/search_all_by_name', '/API/USER/VIEW_MENU/SEARCH_ALL_BY_NAME', 18, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (105, '获取当前用户视图菜单，以 Antd VueMenu 形式展示', '/api/user/view_menu/list_all_as_vue_menu_by_current_user', '/API/USER/VIEW_MENU/LIST_ALL_AS_VUE_MENU_BY_CURRENT_USER', 18, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (106, '指定角色 id、视图菜单分类 id list，分页获取视图菜单，以 Antd Table 形式展示', '/api/user/view_menu/page_all_as_antd_table_by_role_id_and_menu_category_id_list', '/API/USER/VIEW_MENU/PAGE_ALL_AS_ANTD_TABLE_BY_ROLE_ID_AND_MENU_CATEGORY_ID_LIST', 18, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (107, '指定视图菜单分类 id list，分页获取视图菜单，以 Antd Table 形式展示', '/api/user/view_menu/page_all_as_antd_table_by_view_menu_category_id_list', '/API/USER/VIEW_MENU/PAGE_ALL_AS_ANTD_TABLE_BY_VIEW_MENU_CATEGORY_ID_LIST', 18, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (108, '指定角色 id、视图菜单 id list，批量授权', '/api/user/view_menu/grant_all_by_role_id_and_view_menu_id_list', '/API/USER/VIEW_MENU/GRANT_ALL_BY_ROLE_ID_AND_VIEW_MENU_ID_LIST', 18, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (109, '指定角色 id、视图菜单 id list，批量撤销授权', '/api/user/view_menu/revoke_all_by_role_id_and_view_menu_id_list', '/API/USER/VIEW_MENU/REVOKE_ALL_BY_ROLE_ID_AND_VIEW_MENU_ID_LIST', 18, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (110, '指定视图页面 id，API id list，批量关联', '/api/user/view_page_api/associate_all_by_view_page_id_and_api_id_list', '/API/USER/VIEW_PAGE_API/ASSOCIATE_ALL_BY_VIEW_PAGE_ID_AND_API_ID_LIST', 8, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (111, '指定视图页面 id，API id list，批量撤销关联', '/api/user/view_page_api/revoke_associate_all_by_view_page_id_and_api_id_list', '/API/USER/VIEW_PAGE_API/REVOKE_ASSOCIATE_ALL_BY_VIEW_PAGE_ID_AND_API_ID_LIST', 8, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (112, '分页获取所有父级视图页面分类', '/api/user/view_page_category/page_all_parent', '/API/USER/VIEW_PAGE_CATEGORY/PAGE_ALL_PARENT', 9, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (113, '指定父级视图页面分类 id，获取所有视图页面分类', '/api/user/view_page_category/list_all_by_parent_id', '/API/USER/VIEW_PAGE_CATEGORY/LIST_ALL_BY_PARENT_ID', 9, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (114, '新增视图页面分类', '/api/user/view_page_category/add_one', '/API/USER/VIEW_PAGE_CATEGORY/ADD_ONE', 9, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (115, '保存视图页面分类', '/api/user/view_page_category/save_one', '/API/USER/VIEW_PAGE_CATEGORY/SAVE_ONE', 9, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (116, '指定视图页面分类 id ，批量删除视图页面分类', '/api/user/view_page_category/delete_all', '/API/USER/VIEW_PAGE_CATEGORY/DELETE_ALL', 9, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (117, '指定视图页面分类 id，获取视图页面分类', '/api/user/view_page_category/one', '/API/USER/VIEW_PAGE_CATEGORY/ONE', 9, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (118, '指定角色 id，获取所有视图页面分类，以 Antd Table 形式展示', '/api/user/view_page_category/list_all_as_antd_table_by_role_id', '/API/USER/VIEW_PAGE_CATEGORY/LIST_ALL_AS_ANTD_TABLE_BY_ROLE_ID', 9, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (119, '获取视图页面分类，以 Antd Table 形式展示', '/api/user/view_page_category/list_all_as_antd_table', '/API/USER/VIEW_PAGE_CATEGORY/LIST_ALL_AS_ANTD_TABLE', 9, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (120, '获取所有视图页面分类，以 Antd TreeNode 形式展示', '/api/user/view_page_category/list_all_as_antd_tree_node', '/API/USER/VIEW_PAGE_CATEGORY/LIST_ALL_AS_ANTD_TREE_NODE', 9, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (121, '指定视图页面 id，获取视图页面分类', '/api/user/view_page_category/get_one_by_view_page_id', '/API/USER/VIEW_PAGE_CATEGORY/GET_ONE_BY_VIEW_PAGE_ID', 9, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (122, '指定视图页面组件 id、API id list，批量关联', '/api/user/view_page_component_api/associate_all_by_view_page_component_id_and_api_id_list', '/API/USER/VIEW_PAGE_COMPONENT_API/ASSOCIATE_ALL_BY_VIEW_PAGE_COMPONENT_ID_AND_API_ID_LIST', 12, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (123, '指定视图页面组件 id、API id，批量撤销关联', '/api/user/view_page_component_api/revoke_associate_all_by_role_id_and_authority', '/API/USER/VIEW_PAGE_COMPONENT_API/REVOKE_ASSOCIATE_ALL_BY_ROLE_ID_AND_AUTHORITY', 12, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (124, '指定视图页面 id，分页获取所有视图页面组件', '/api/user/view_page_component/page_all_by_view_page_id', '/API/USER/VIEW_PAGE_COMPONENT/PAGE_ALL_BY_VIEW_PAGE_ID', 11, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (125, '新增视图页面组件', '/api/user/view_page_component/add_one', '/API/USER/VIEW_PAGE_COMPONENT/ADD_ONE', 11, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (126, '保存视图页面组件', '/api/user/view_page_component/save_one', '/API/USER/VIEW_PAGE_COMPONENT/SAVE_ONE', 11, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (127, '指定视图页面组件 id，批量删除视图页面组件', '/api/user/view_page_component/delete_all', '/API/USER/VIEW_PAGE_COMPONENT/DELETE_ALL', 11, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (128, '指定视图组件 id，获取视图组件', '/api/user/view_page_component/one', '/API/USER/VIEW_PAGE_COMPONENT/ONE', 11, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (129, '指定角色 id、视图页面 id list，分页获取所有视图页面组件，以 Antd Table 形式展示', '/api/user/view_page_component/page_all_as_antd_table_by_role_id_and_view_page_id_list', '/API/USER/VIEW_PAGE_COMPONENT/PAGE_ALL_AS_ANTD_TABLE_BY_ROLE_ID_AND_VIEW_PAGE_ID_LIST', 11, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (130, '指定视图页面 id list，分页获取视图页面组件，以 Antd Table 形式展示', '/api/user/view_page_component/page_all_as_antd_table_by_view_page_id_list', '/API/USER/VIEW_PAGE_COMPONENT/PAGE_ALL_AS_ANTD_TABLE_BY_VIEW_PAGE_ID_LIST', 11, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (131, '指定角色 id、视图页面组件 id list，批量授权', '/api/user/view_page_component/grant_all_by_role_id_and_view_page_component_id_list', '/API/USER/VIEW_PAGE_COMPONENT/GRANT_ALL_BY_ROLE_ID_AND_VIEW_PAGE_COMPONENT_ID_LIST', 11, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (132, '指定角色 id、视图页面组件 id list，批量撤销授权', '/api/user/view_page_component/revoke_all_by_role_id_and_view_page_component_id_list', '/API/USER/VIEW_PAGE_COMPONENT/REVOKE_ALL_BY_ROLE_ID_AND_VIEW_PAGE_COMPONENT_ID_LIST', 11, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (133, '指定视图页面分类 id ，分页获取所有视图页面', '/api/user/view_page/page_all_by_view_page_category_id', '/API/USER/VIEW_PAGE/PAGE_ALL_BY_VIEW_PAGE_CATEGORY_ID', 10, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (134, '新增视图页面', '/api/user/view_page/add_one', '/API/USER/VIEW_PAGE/ADD_ONE', 10, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (135, '保存视图页面', '/api/user/view_page/save_one', '/API/USER/VIEW_PAGE/SAVE_ONE', 10, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (136, '指定视图页面 id ，批量删除视图页面', '/api/user/view_page/delete_all', '/API/USER/VIEW_PAGE/DELETE_ALL', 10, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (137, '指定视图页面 id，获取视图页面', '/api/user/view_page/one', '/API/USER/VIEW_PAGE/ONE', 10, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (138, '指定角色 id，视图页面分类 id list，分页获取视图页面，以 Antd Table 形式展示', '/api/user/view_page/page_all_as_antd_table_by_role_id_and_view_page_category_id_list', '/API/USER/VIEW_PAGE/PAGE_ALL_AS_ANTD_TABLE_BY_ROLE_ID_AND_VIEW_PAGE_CATEGORY_ID_LIST', 10, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (139, '指定视图页面分类 id list，分页获取所有视图页面，以 Antd Table 形式展示', '/api/user/view_page/page_all_as_antd_table_by_view_page_category_id_list', '/API/USER/VIEW_PAGE/PAGE_ALL_AS_ANTD_TABLE_BY_VIEW_PAGE_CATEGORY_ID_LIST', 10, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (140, '指定角色 id、视图页面 id list，批量授权', '/api/user/view_page/grant_all_by_role_id_and_view_page_id_list', '/API/USER/VIEW_PAGE/GRANT_ALL_BY_ROLE_ID_AND_VIEW_PAGE_ID_LIST', 10, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (141, '指定角色 id、视图页面 id list，批量撤销授权', '/api/user/view_page/revoke_all_by_role_id_and_view_page_id_list', '/API/USER/VIEW_PAGE/REVOKE_ALL_BY_ROLE_ID_AND_VIEW_PAGE_ID_LIST', 10, 0, '', NULL, NULL);
INSERT INTO `api` VALUES (142, '指定视图页面分类 id，获取所有视图页面', '/api/user/view_page/list_all_by_view_page_category_id', '/API/USER/VIEW_PAGE/LIST_ALL_BY_VIEW_PAGE_CATEGORY_ID', 10, 0, '', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
