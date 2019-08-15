package com.gioov.oryx.user.service;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.ApiCategoryEntityAsAntdTable;
import com.gioov.oryx.user.entity.ApiCategoryEntity;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface ApiCategoryService {

//    /**
//     * 分页获取所有父级 API 分类
//     * @param page 页
//     * @param rows 每页显示数量
//     * @return Pagination<ApiCategoryEntity>
//     */
//    Pagination<ApiCategoryEntity> pageAllParent(Integer page, Integer rows);

    /**
     * 新增 API 分类
     * @param apiCategoryEntity ApiCategoryEntity
     * @return ApiCategoryEntity
     */
    ApiCategoryEntity addOne(ApiCategoryEntity apiCategoryEntity);

    /**
     * 保存 API 分类
     * @param apiCategoryEntity ApiCategoryEntity
     * @return ApiCategoryEntity
     */
    ApiCategoryEntity saveOne(ApiCategoryEntity apiCategoryEntity);

    /**
     * 指定 API 分类 id list，批量删除 API 分类
     * @param idList API 分类 id list
     * @return 已删除 API 分类个数
     * @throws BaseResponseException BaseResponseException
     */
    int deleteAll(List<Long> idList) throws BaseResponseException;

    /**
     * 指定 API 分类 id，获取所有 API 分类
     * @param id API 分类 id
     * @return ApiCategoryEntity
     */
    ApiCategoryEntity getOne(Long id);

    /**
     * 指定父级 API 分类 id，获取所有 API 分类
     * @param parentId API 分类父级 id
     * @return List<ApiCategoryEntity>
     */
    List<ApiCategoryEntity> listAllByParentId(Long parentId);

    /**
     * 获取所有 API 分类
     * @return List<ApiCategoryEntity>
     */
    List<ApiCategoryEntity> listAll();

//    /**
//     * 指定角色 id，获取所有 API 分类，以 Antd Table 形式展示
//     * @param roleId 角色 id
//     * @return List<ApiCategoryEntityAsAntdTable>
//     */
//    List<ApiCategoryEntityAsAntdTable> listAllApiCategoryEntityAsAntdTableByRoleId(long roleId);

    /**
     * 获取所有 API 分类，以 Antd Table 形式展示
     * @return List<ApiCategoryEntityAsAntdTable>
     */
    List<ApiCategoryEntityAsAntdTable> listAllApiCategoryEntityAsAntdTable();
    /**
     * 指定父级 API 分类 id，ApiCategoryAsAntdTable list，获取所有子级 API 分类
     * @param parentId 父级 API 分类 id
     * @param apiCategoryAsAntdTableList ApiCategoryAsAntdTable list
     * @return List<ApiCategoryEntityAsAntdTable>
     */
    List<ApiCategoryEntityAsAntdTable> getApiCategoryChildrenApiCategoryEntityAsAntdTable(long parentId, List<ApiCategoryEntityAsAntdTable> apiCategoryAsAntdTableList);

    /**
     * 获取所有 API 分类，以 Antd TreeNode 形式展示
     * @return List<AntdTreeNode>
     */
    List<AntdTreeNode> listAllApiCategoryAntdTreeNode();
    /**
     * 指定父级 API 分类 id，ApiCategoryAntdTreeNode list，获取所有子级 API 分类
     * @param parentId 父级 API 分类 id
     * @param apiCategoryAntdTreeNodeList ApiCategoryAntdTreeNode list
     * @return List<AntdTreeNode>
     */
    List<AntdTreeNode> getApiCategoryChildrenAntdTreeNode(long parentId, List<AntdTreeNode> apiCategoryAntdTreeNodeList);

}
