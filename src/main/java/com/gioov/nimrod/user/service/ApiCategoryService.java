package com.gioov.nimrod.user.service;

import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.common.vue.antd.AntdTreeNode;
import com.gioov.nimrod.common.vue.antd.ApiCategoryEntityAsAntdTable;
import com.gioov.nimrod.common.vue.antd.ApiCategoryEntityAsAntdTable;
import com.gioov.nimrod.user.entity.ApiCategoryEntity;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface ApiCategoryService {

    /**
     * 分页获取所有父级 API 分类
     *
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<ApiCategoryEntity>
     */
    Pagination<ApiCategoryEntity> pageAllByParentIdIsNull(Integer page, Integer rows);

    /**
     * 新增 API 分类
     *
     * @param apiCategoryEntity ApiCategoryEntity
     * @return ApiCategoryEntity
     */
    ApiCategoryEntity insertOne(ApiCategoryEntity apiCategoryEntity);

    /**
     * 保存 API 分类
     *
     * @param apiCategoryEntity ApiCategoryEntity
     * @return ApiCategoryEntity
     */
    ApiCategoryEntity updateOne(ApiCategoryEntity apiCategoryEntity);

    /**
     * 指定 API 分类 id list ，批量删除 API 分类
     *
     * @param idList API 分类 id list
     * @return 已删除 API 分类个数
     * @throws BaseResponseException BaseResponseException
     */
    int deleteAll(List<Long> idList) throws BaseResponseException;

    /**
     * 指定 API 分类 id ，获取 API 分类信息
     *
     * @param id API 分类 id
     * @return ApiCategoryEntity
     */
    ApiCategoryEntity getOne(Long id);

    /**
     * 指定父级 API 分类 id ，获取所有 API 分类
     *
     * @param parentId API 分类父级 id
     * @return List<ApiCategoryEntity>
     */
    List<ApiCategoryEntity> listAllByParentId(Long parentId);

    List<ApiCategoryEntity> listAll();

    /**
     * 获取视图菜单分类，以 Antd Table 形式展示
     * 判断当前角色是否被授权此视图菜单分类
     * @return
     */
    List<ApiCategoryEntityAsAntdTable> listAllApiCategoryEntityAsAntdTableByRoleId(long roleId);

    /**
     * 获取视图菜单分类，以 Antd Table 形式展示
     * @return
     */
    List<ApiCategoryEntityAsAntdTable> listAllApiCategoryEntityAsAntdTable();
    List<ApiCategoryEntityAsAntdTable> getApiCategoryChildrenApiCategoryEntityAsAntdTable(long parentId, List<ApiCategoryEntityAsAntdTable> apiCategoryAsAntdTableList);

    /**
     * 获取所有部门，以 Antd TreeNode 形式展示
     * @return
     */
    List<AntdTreeNode> listAllApiCategoryAntdTreeNode();
    List<AntdTreeNode> getApiCategoryChildrenAntdTreeNode(long parentId, List<AntdTreeNode> apiCategoryAntdTreeNodeList);

}
