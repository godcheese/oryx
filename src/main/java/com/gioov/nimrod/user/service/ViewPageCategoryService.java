package com.gioov.nimrod.user.service;

import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.common.vue.antd.AntdTreeNode;
import com.gioov.nimrod.common.vue.antd.ViewPageCategoryEntityAsAntdTable;
import com.gioov.nimrod.common.vue.antd.ViewPageCategoryEntityAsAntdTable;
import com.gioov.nimrod.common.vue.antd.ViewPageCategoryEntityAsAntdTable;
import com.gioov.nimrod.user.entity.ViewPageCategoryEntity;
import com.gioov.nimrod.user.entity.ViewPageCategoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface ViewPageCategoryService {

    /**
     * 分页获取所有父级视图页面分类
     *
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<ViewPageCategoryEntity>
     */
    Pagination<ViewPageCategoryEntity> pageAllParent(Integer page, Integer rows);

    /**
     * 指定父级视图页面分类 id ，获取所有视图页面分类
     *
     * @param parentId 父级视图页面分类 id
     * @return List<ViewPageCategoryEntity>
     */
    List<ViewPageCategoryEntity> listAllByParentId(Long parentId);

    List<ViewPageCategoryEntity> listAll();
    
    /**
     * 新增视图页面分类
     *
     * @param viewPageCategoryEntity ViewPageCategoryEntity
     * @return ViewPageCategoryEntity
     */
    ViewPageCategoryEntity insertOne(ViewPageCategoryEntity viewPageCategoryEntity);

    /**
     * 保存视图页面分类
     *
     * @param viewPageCategoryEntity ViewPageCategoryEntity
     * @return ViewPageCategoryEntity
     */
    ViewPageCategoryEntity updateOne(ViewPageCategoryEntity viewPageCategoryEntity);

    /**
     * 指定视图页面分类 id ，批量删除视图页面分类
     *
     * @param idList 视图页面分类 id list
     * @return 已删除视图页面分类个数
     */
    int deleteAll(List<Long> idList) throws BaseResponseException;

    /**
     * 指定视图页面分类 id ，获取视图页面分类信息
     *
     * @param id 数据字典 id
     * @return ViewPageCategoryEntity
     */
    ViewPageCategoryEntity getOne(Long id);

    /**
     * 获取视图菜单分类，以 Antd Table 形式展示
     * @return
     */
    List<ViewPageCategoryEntityAsAntdTable> listAllViewPageCategoryEntityAsAntdTable();
    List<ViewPageCategoryEntityAsAntdTable> getViewPageCategoryChildrenViewPageCategoryEntityAsAntdTable(long parentId, List<ViewPageCategoryEntityAsAntdTable> viewPageCategoryAsAntdTableList);

    /**
     * 获取视图菜单分类，以 Antd Table 形式展示
     * 判断当前角色是否被授权此视图菜单分类
     * @return
     */
    List<ViewPageCategoryEntityAsAntdTable> listAllViewPageCategoryEntityAsAntdTableByRoleId(long roleId);

    /**
     * 获取所有部门，以 Antd TreeNode 形式展示
     * @return
     */
    List<AntdTreeNode> listAllViewPageCategoryAntdTreeNode();
    List<AntdTreeNode> getViewPageCategoryChildrenAntdTreeNode(long parentId, List<AntdTreeNode> viewPageCategoryAntdTreeNodeList);

    /**
     * 指定角色 id、API 权限（authority），批量授权
     *
     * @param roleId        角色 id
     * @param viewPageCategoryIdList 权限（authority） list
     * @return List<String>
     */
    List<Long> grantAllByRoleIdAndViewPageCategoryIdList(Long roleId, List<Long> viewPageCategoryIdList);

    /**
     * 指定角色 id、API 权限（authority），批量撤销授权
     *
     * @param roleId        角色 id
     * @param viewPageCategoryIdList 权限（authority） list
     * @return List<String>
     */
    List<Long> revokeAllByRoleIdAndViewPageCategoryIdList(Long roleId, List<Long> viewPageCategoryIdList);

    /**
     * 指定视图页面分类 id ，获取视图页面分类信息
     *
     * @param viewPageId 数据字典 id
     * @return ViewPageCategoryEntity
     */
    ViewPageCategoryEntity getOneByViewPageId(Long viewPageId);

}
