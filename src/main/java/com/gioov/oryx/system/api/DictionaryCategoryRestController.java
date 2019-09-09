package com.gioov.oryx.system.api;

import com.gioov.oryx.common.FailureMessage;
import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.DictionaryCategoryEntityAsAntdTable;
import com.gioov.oryx.system.System;
import com.gioov.oryx.system.entity.DictionaryCategoryEntity;
import com.gioov.oryx.system.service.DictionaryCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.gioov.oryx.user.service.UserService.SYSTEM_ADMIN;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@RestController
@RequestMapping(System.Api.DICTIONARY_CATEGORY)
public class DictionaryCategoryRestController {

    private static final String DICTIONARY_CATEGORY = "/API/SYSTEM/DICTIONARY_CATEGORY";

    @Autowired
    private DictionaryCategoryService dictionaryCategoryService;

    /**
     * 新增数据字典分类
     * @param name 数据字典分类名称
     * @param parentId 数据字典分类父级 id
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<DictionaryCategoryEntity>
     */
    @OperationLog(value = "新增数据字典分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DICTIONARY_CATEGORY + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<DictionaryCategoryEntity> addOne(@RequestParam String name, @RequestParam(required = false) Long parentId, @RequestParam Long sort, @RequestParam String remark) {
        DictionaryCategoryEntity dictionaryCategoryEntity = new DictionaryCategoryEntity();
        dictionaryCategoryEntity.setName(name);
        dictionaryCategoryEntity.setParentId(parentId);
        dictionaryCategoryEntity.setSort(sort);
        dictionaryCategoryEntity.setRemark(remark);
        DictionaryCategoryEntity dictionaryCategoryEntity1 = dictionaryCategoryService.insertOne(dictionaryCategoryEntity);
        return new ResponseEntity<>(dictionaryCategoryEntity1, HttpStatus.OK);
    }

    /**
     * 保存数据字典分类
     * @param id 数据字典分类 id
     * @param name 数据字典分类名称
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<DictionaryCategoryEntity>
     */
    @OperationLog(value = "保存数据字典分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DICTIONARY_CATEGORY + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<DictionaryCategoryEntity> saveOne(@RequestParam Long id, @RequestParam String name, @RequestParam Long sort, @RequestParam String remark) {
        DictionaryCategoryEntity dictionaryCategoryEntity = new DictionaryCategoryEntity();
        dictionaryCategoryEntity.setId(id);
        dictionaryCategoryEntity.setName(name);
        dictionaryCategoryEntity.setSort(sort);
        dictionaryCategoryEntity.setRemark(remark);
        DictionaryCategoryEntity dictionaryCategoryEntity1 = dictionaryCategoryService.updateOne(dictionaryCategoryEntity);
        return new ResponseEntity<>(dictionaryCategoryEntity1, HttpStatus.OK);
    }

    /**
     * 指定数据字典分类 id list，批量删除数据字典分类
     * @param idList 数据字典分类 id list
     * @return ResponseEntity<Integer>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "指定数据字典分类 id list，批量删除数据字典分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DICTIONARY_CATEGORY + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) throws BaseResponseException {
        return new ResponseEntity<>(dictionaryCategoryService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定数据字典分类 id，获取数据字典分类
     * @param id 数据字典分类 id
     * @return ResponseEntity<DictionaryCategoryEntity>
     */
    @OperationLog(value = "指定数据字典分类 id，获取数据字典分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DICTIONARY_CATEGORY + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<DictionaryCategoryEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(dictionaryCategoryService.getOne(id), HttpStatus.OK);
    }

    /**
     * 获取所有数据字典分类，以 Antd Table 形式展示
     * @return ResponseEntity<List<DictionaryCategoryEntityAsAntdTable>>
     */
    @OperationLog(value = "获取所有数据字典分类，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DICTIONARY_CATEGORY + "/LIST_ALL_AS_ANTD_TABLE')")
    @GetMapping(value = "/list_all_as_antd_table")
    public ResponseEntity<List<DictionaryCategoryEntityAsAntdTable>> listAllAsAntdTable() {
        List<DictionaryCategoryEntityAsAntdTable> dictionaryCategoryEntityAsAntdTableResultList = new ArrayList<>();
        List<DictionaryCategoryEntityAsAntdTable> dictionaryCategoryEntityAsAntdTableList = dictionaryCategoryService.listAllDictionaryCategoryEntityAsAntdTable();

        for(DictionaryCategoryEntityAsAntdTable dictionaryCategoryEntityAsAntdTable : dictionaryCategoryEntityAsAntdTableList) {
            if(dictionaryCategoryEntityAsAntdTable.getParentId() == null) {
                dictionaryCategoryEntityAsAntdTableResultList.add(dictionaryCategoryEntityAsAntdTable);
            }
        }
        for(DictionaryCategoryEntityAsAntdTable dictionaryCategoryEntityAsAntdTable : dictionaryCategoryEntityAsAntdTableResultList) {
            dictionaryCategoryEntityAsAntdTable.setChildren(dictionaryCategoryService.getDictionaryCategoryChildrenDictionaryCategoryEntityAsAntdTable(dictionaryCategoryEntityAsAntdTable.getId(), dictionaryCategoryEntityAsAntdTableList));
        }
        return new ResponseEntity<>(dictionaryCategoryEntityAsAntdTableResultList, HttpStatus.OK);
    }

    /**
     * 获取所有数据字典分类，以 Antd TreeNode 形式展示
     * @return ResponseEntity<List<AntdTreeNode>>
     */
     @OperationLog(value = "获取所有数据字典分类，以 Antd TreeNode 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DICTIONARY_CATEGORY + "/LIST_ALL_AS_ANTD_TREE_NODE')")
    @GetMapping(value = "/list_all_as_antd_tree_node")
    public ResponseEntity<List<AntdTreeNode>> listAllAsTreeNode() {
        List<AntdTreeNode> antdTreeNodeResultList = new ArrayList<>();
        List<AntdTreeNode> dictionaryCategoryAntdTreeNodeList = dictionaryCategoryService.listAllDictionaryCategoryAntdTreeNode();
        for(AntdTreeNode antdTreeNode : dictionaryCategoryAntdTreeNodeList) {
            if(antdTreeNode.getParentId() == null) {
                antdTreeNodeResultList.add(antdTreeNode);
            }
        }
        for(AntdTreeNode antdTreeNode : antdTreeNodeResultList) {
            antdTreeNode.setChildren(dictionaryCategoryService.getDictionaryCategoryChildrenAntdTreeNode(antdTreeNode.getId(), dictionaryCategoryAntdTreeNodeList));
        }
        return new ResponseEntity<>(antdTreeNodeResultList, HttpStatus.OK);
    }

}
