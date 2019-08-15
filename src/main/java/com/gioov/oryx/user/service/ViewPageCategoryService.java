package com.gioov.oryx.user.service;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.ViewPageCategoryEntityAsAntdTable;
import com.gioov.oryx.user.entity.ViewPageCategoryEntity;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface ViewPageCategoryService {

    /**
     * 分页获取所有父级视图页面分类
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<ViewPageCategoryEntity>
     */
    Pagination<ViewPageCategoryEntity> pageAllParent(Integer page, Integer rows);

    /**
     * 指定父级视图页面分类 id ，获取所有视图页面分类
     * @param parentId 父级视图页面分类 id
     * @return List<ViewPageCategoryEntity>
     */
    List<ViewPageCategoryEntity> listAllByParentId(Long parentId);

    /**
     * 获取所有视图页面分类
     * @return List<ViewPageCategoryEntity>
     */
    List<ViewPageCategoryEntity> listAll();
    
    /**
     * 新增视图页面分类
     * @param viewPageCategoryEntity ViewPageCategoryEntity
     * @return ViewPageCategoryEntity
     */
    ViewPageCategoryEntity addOne(ViewPageCategoryEntity viewPageCategoryEntity);

    /**
     * 保存视图页面分类
     * @param viewPageCategoryEntity ViewPageCategoryEntity
     * @return ViewPageCategoryEntity
     */
    ViewPageCategoryEntity saveOne(ViewPageCategoryEntity viewPageCategoryEntity);

    /**
     * 指定视图页面分类 id list，批量删除视图页面分类
     * @param idList 视图页面分类 id list
     * @return int
     * @throws BaseResponseException BaseResponseException
     */
    int deleteAll(List<Long> idList) throws BaseResponseException;

    /**
     * 指定视图页面分类 id，获取所有视图页面分类
     * @param id 数据字典 id
     * @return ViewPageCategoryEntity
     */
    ViewPageCategoryEntity getOne(Long id);

    /**
     * 获取所有视图菜单分类，以 Antd Table 形式展示
     * @return List<ViewPageCategoryEntityAsAntdTable>
     */
    List<ViewPageCategoryEntityAsAntdTable> listAllViewPageCategoryEntityAsAntdTable();
    /**
     * 指定父级视图页面分类 id、ViewPageCategoryAsAntdTable list，获取所有子级视图页面分类
     * @param parentId 父级视图页面分类 id
     * @param viewPageCategoryAsAntdTableList ViewPageCategoryAsAntdTable list
     * @return List<ViewPageCategoryEntityAsAntdTable>
     */
    List<ViewPageCategoryEntityAsAntdTable> getViewPageCategoryChildrenViewPageCategoryEntityAsAntdTable(long parentId, List<ViewPageCategoryEntityAsAntdTable> viewPageCategoryAsAntdTableList);

    /**
     * 指定角色 id，获取所有视图页面分类，以 Antd Table 形式展示
     * @param roleId 角色 id
     * @return List<ViewPageCategoryEntityAsAntdTable>
     */
    List<ViewPageCategoryEntityAsAntdTable> listAllViewPageCategoryEntityAsAntdTableByRoleId(long roleId);

    /**
     * 获取所有视图页面分类，以 Antd TreeNode 形式展示
     * @return List<AntdTreeNode>
     */
    List<AntdTreeNode> listAllViewPageCategoryAntdTreeNode();
    /**
     * 指定父级视图页面分类 id，获取所有子级视图页面分类，以 Antd TreeNode 形式展示
     * @param parentId 父级视图页面分类 id
     * @param viewPageCategoryAntdTreeNodeList ViewPageCategoryAntdTreeNode list
     * @return List<AntdTreeNode>
     */
    List<AntdTreeNode> getViewPageCategoryChildrenAntdTreeNode(long parentId, List<AntdTreeNode> viewPageCategoryAntdTreeNodeList);

    /**
     * 指定视图页面 id，获取视图页面分类
     * @param viewPageId 视图页面 id
     * @return ViewPageCategoryEntity
     */
    ViewPageCategoryEntity getOneByViewPageId(Long viewPageId);

}
