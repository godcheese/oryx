package com.gioov.oryx.system.service;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.DictionaryCategoryEntityAsAntdTable;
import com.gioov.oryx.system.entity.DictionaryCategoryEntity;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface DictionaryCategoryService {

//    /**
//     * 分页获取所有父级数据字典分类
//     * @param page 页
//     * @param rows 每页显示数量
//     * @return Pagination<DictionaryEntity>
//     */
//    Pagination<DictionaryCategoryEntity> pageAllParent(Integer page, Integer rows);

    /**
     * 指定父级数据字典分类 id，获取所有数据字典分类
     * @param parentId 父级数据字典分类 id
     * @return Pagination<DictionaryEntity>
     */
    List<DictionaryCategoryEntity> listAllByParentId(Long parentId);

    /**
     * 新增数据字典分类
     * @param dictionaryCategoryEntity DictionaryCategoryEntity
     * @return DictionaryCategoryEntity
     */
    DictionaryCategoryEntity insertOne(DictionaryCategoryEntity dictionaryCategoryEntity);

    /**
     * 保存数据字典分类
     * @param dictionaryCategoryEntity DictionaryCategoryEntity
     * @return DictionaryCategoryEntity
     */
    DictionaryCategoryEntity updateOne(DictionaryCategoryEntity dictionaryCategoryEntity);

    /**
     * 指定数据字典分类 id list，批量删除数据字典分类
     * @param idList 数据字典分类 id list
     * @return int
     * @throws BaseResponseException BaseResponseException
     */
    int deleteAll(List<Long> idList) throws BaseResponseException;

    /**
     * 指定数据字典分类 id，获取数据字典分类
     * @param id 数据字典分类 id
     * @return DictionaryCategoryEntity
     */
    DictionaryCategoryEntity getOne(Long id);

    /**
     * 获取所有数据字典分类
     * @return List<DictionaryCategoryEntity>
     */
    List<DictionaryCategoryEntity> listAll();

    /**
     * 获取所有数据字典分类，以 Antd Table 形式展示
     * @return List<DictionaryCategoryEntityAsAntdTable>
     */
    List<DictionaryCategoryEntityAsAntdTable> listAllDictionaryCategoryEntityAsAntdTable();
    /**
     * 指定父级数据字典分类 id、DictionaryCategoryAsAntdTable list，获取所有子级数据字典分类
     * @param parentId 父级数据字典分类 id
     * @param dictionaryCategoryAsAntdTableList DictionaryCategoryAsAntdTable list
     * @return List<DictionaryCategoryEntityAsAntdTable>
     */
    List<DictionaryCategoryEntityAsAntdTable> getDictionaryCategoryChildrenDictionaryCategoryEntityAsAntdTable(long parentId, List<DictionaryCategoryEntityAsAntdTable> dictionaryCategoryAsAntdTableList);

    /**
     * 获取所有数据字典分类，以 Antd TreeNode 形式展示
     * @return List<AntdTreeNode>
     */
    List<AntdTreeNode> listAllDictionaryCategoryAntdTreeNode();
    /**
     * 指定父级数据字典分类 id，DictionaryCategoryAntdTreeNode list，获取所有子级数据字典分类
     * @param parentId 父级数据字典分类 id
     * @param dictionaryCategoryAntdTreeNodeList DictionaryCategoryAntdTreeNode list
     * @return List<AntdTreeNode>
     */
    List<AntdTreeNode> getDictionaryCategoryChildrenAntdTreeNode(long parentId, List<AntdTreeNode> dictionaryCategoryAntdTreeNodeList);

}
