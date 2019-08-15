package com.gioov.oryx.user.service;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.ViewMenuCategoryEntityAsAntdTable;
import com.gioov.oryx.user.entity.ViewMenuCategoryEntity;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface ViewMenuCategoryService {

    /**
     * 指定用户 id，获取视图菜单分类
     * @param userId 用户 id
     * @return List<ViewMenuCategoryEntity>
     */
    List<ViewMenuCategoryEntity> listAllParentByUserId(Long userId);

    /**
     * 指定父级视图菜单分类 id、用户 id，获取视图菜单分类
     *
     * @param parentId 父级视图页面分类 id
     * @param userId 用户 id
     * @return List<ViewMenuCategoryEntity>
     */
    List<ViewMenuCategoryEntity> listAllChildByParentIdAndUserId(Long parentId, Long userId);

    /**
     * 指定父级视图菜单分类 id、角色 id，获取所有视图菜单分类
     * @param parentId 父级视图菜单分类 id
     * @param roleId 角色 id
     * @return List<ViewMenuCategoryEntity>
     */
    List<ViewMenuCategoryEntity> listAllByParentIdAndRoleId(Long parentId, Long roleId);

    /**
     * 指定角色 id，获取所有父级视图菜单分类
     * @param roleId 角色 id
     * @return List<ViewMenuCategoryEntity>
     */
    List<ViewMenuCategoryEntity> listAllParentByRoleId(Long roleId);

//    List<Map<String, Object>> listAllChildViewMenuCategoryAndViewMenuByParentIdAndUserId(Long parentId, Long userId);

    /**
     * 获取所有视图菜单分类
     * @return List<ViewMenuCategoryEntity>
     */
    List<ViewMenuCategoryEntity> listAll();

    /**
     * 指定视图菜单分类名称，模糊搜索获取所有视图菜单分类
     * @param name 视图菜单分类名称
     * @return List<ViewMenuCategoryEntity>
     */
    List<ViewMenuCategoryEntity> searchAllByName(String name);

    /**
     * 指定角色 id，分页获取所有父级视图菜单分类
     * @param roleId 角色 id
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<ViewMenuCategoryEntity>
     */
    Pagination<ViewMenuCategoryEntity> pageAllParent(Long roleId, Integer page, Integer rows);

    /**
     * 新增视图菜单分类
     * @param viewMenuCategoryEntity ViewMenuCategoryEntity
     * @return ViewMenuCategoryEntity
     */
    ViewMenuCategoryEntity addOne(ViewMenuCategoryEntity viewMenuCategoryEntity);

    /**
     * 保存视图菜单分类
     * @param viewMenuCategoryEntity ViewMenuCategoryEntity
     * @return ViewMenuCategoryEntity
     */
    ViewMenuCategoryEntity saveOne(ViewMenuCategoryEntity viewMenuCategoryEntity);

    /**
     * 指定视图菜单分类 id list，批量删除视图菜单分类
     * @param idList 视图菜单分类 id list
     * @return int
     * @throws BaseResponseException BaseResponseException
     */
    int deleteAll(List<Long> idList) throws BaseResponseException;

    /**
     * 指定角色 id，获取角色
     * @param id 角色 id
     * @return ViewMenuCategoryEntity
     */
    ViewMenuCategoryEntity getOne(Long id);

    /**
     * 获取视图菜单分类，以 Antd Table 形式展示
     * @return List<ViewMenuCategoryEntityAsAntdTable>
     */
    List<ViewMenuCategoryEntityAsAntdTable> listAllViewMenuCategoryEntityAsAntdTable();
    /**
     * 指定父级视图菜单分类 id，ViewMenuCategoryAsAntdTable list，获取所有子级视图菜单分类，以 Antd Table 形式展示
     * @param parentId 父级视图菜单分类 id
     * @param viewMenuCategoryAsAntdTableList ViewMenuCategoryAsAntdTable list
     * @return List<ViewMenuCategoryEntityAsAntdTable>
     */
    List<ViewMenuCategoryEntityAsAntdTable> getViewMenuCategoryChildrenViewMenuCategoryEntityAsAntdTable(long parentId, List<ViewMenuCategoryEntityAsAntdTable> viewMenuCategoryAsAntdTableList);

    /**
     * 指定角色 id，获取所有视图菜单分类，以 Antd Table 形式展示
     * @param roleId 角色 id
     * @return List<ViewMenuCategoryEntityAsAntdTable>
     */
    List<ViewMenuCategoryEntityAsAntdTable> listAllViewMenuCategoryEntityAsAntdTableByRoleId(long roleId);

    /**
     * 获取所有视图菜单分类，以 Antd TreeNode 形式展示
     * @return List<AntdTreeNode>
     */
    List<AntdTreeNode> listAllViewMenuCategoryAntdTreeNode();
    /**
     * 指定父级视图菜单分类 id，ViewMenuCategoryAntdTreeNode list，获取所有子级视图菜单分类，以 Antd TreeNode 形式展示
     * @param parentId 父级视图菜单分类 id
     * @param viewMenuCategoryAntdTreeNodeList ViewMenuCategoryAntdTreeNode list
     * @return List<AntdTreeNode>
     */
    List<AntdTreeNode> getViewMenuCategoryChildrenAntdTreeNode(long parentId, List<AntdTreeNode> viewMenuCategoryAntdTreeNodeList);

    /**
     * 指定角色 id、视图菜单分类 id list，批量授权
     * @param roleId 角色 id
     * @param viewMenuCategoryIdList 视图菜单分类 id list
     * @return int
     */
    int grantAllByRoleIdAndViewMenuCategoryIdList(Long roleId, List<Long> viewMenuCategoryIdList);

    /**
     * 指定角色 id、视图菜单分类 id list，批量撤销授权
     * @param roleId 角色 id
     * @param viewMenuCategoryIdList 视图菜单分类 id list
     * @return int
     */
    int revokeAllByRoleIdAndViewMenuCategoryIdList(Long roleId, List<Long> viewMenuCategoryIdList);

}
