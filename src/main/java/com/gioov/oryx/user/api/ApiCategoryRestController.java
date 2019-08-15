package com.gioov.oryx.user.api;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.ApiCategoryEntityAsAntdTable;
import com.gioov.oryx.user.User;
import com.gioov.oryx.user.entity.ApiCategoryEntity;
import com.gioov.oryx.user.service.ApiCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = User.Api.API_CATEGORY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApiCategoryRestController {

    private static final String API_CATEGORY = "/API/USER/API_CATEGORY";

    @Autowired
    private ApiCategoryService apiCategoryService;

//    /**
//     * 分页获取所有父级 API 分类
//     *
//     * @param page 页
//     * @param rows 每页显示数量
//     * @return Pagination<ApiCategoryEntity>
//     */
//    @OperationLog(value = "分页获取所有父级 API 分类", type = OperationLogType.API)
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/PAGE_ALL_PARENT')")
//    @GetMapping(value = "/page_all_parent")
//    public ResponseEntity<Pagination<ApiCategoryEntity>> pageAllParent(@RequestParam Integer page, @RequestParam Integer rows) {
//        return new ResponseEntity<>(apiCategoryService.pageAllParent(page, rows), HttpStatus.OK);
//    }

//    /**
//     * 指定父级 API 分类 id，获取所有 API 分类
//     *
//     * @param parentId API 分类父级 id
//     * @return ResponseEntity<List<ApiCategoryEntity>>
//     */
//    @OperationLog(value = "获取所有 API 分类", type = OperationLogType.API)
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/LIST_ALL_BY_PARENT_ID')")
//    @GetMapping(value = "/list_all_by_parent_id/{parentId}")
//    public ResponseEntity<List<ApiCategoryEntity>> listAllByParentId(@PathVariable Long parentId) {
//        return new ResponseEntity<>(apiCategoryService.listAllByParentId(parentId), HttpStatus.OK);
//    }

    /**
     * 新增 API 分类
     * @param name API 分类名称
     * @param parentId API 分类父级 id
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ApiCategoryEntity>
     */
    @OperationLog(value = "新增 API 分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<ApiCategoryEntity> addOne(@RequestParam String name, @RequestParam(required = false) Long parentId, @RequestParam Long sort, @RequestParam String remark) {
        ApiCategoryEntity apiCategoryEntity = new ApiCategoryEntity();
        apiCategoryEntity.setName(name);
        apiCategoryEntity.setParentId(parentId);
        apiCategoryEntity.setSort(sort);
        apiCategoryEntity.setRemark(remark);
        ApiCategoryEntity apiCategoryEntity1 = apiCategoryService.addOne(apiCategoryEntity);
        return new ResponseEntity<>(apiCategoryEntity1, HttpStatus.OK);
    }

    /**
     * 保存 API 分类
     * @param id  API 分类 id
     * @param name API 分类名称
     * @param parentId API 分类父级 id
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ApiCategoryEntity>
     */
    @OperationLog(value = "保存 API 分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<ApiCategoryEntity> saveOne(@RequestParam Long id, @RequestParam String name, @RequestParam Long parentId, @RequestParam Long sort, @RequestParam String remark) {
        ApiCategoryEntity apiCategoryEntity = new ApiCategoryEntity();
        apiCategoryEntity.setId(id);
        apiCategoryEntity.setName(name);
        apiCategoryEntity.setParentId(parentId);
        apiCategoryEntity.setSort(sort);
        apiCategoryEntity.setRemark(remark);
        ApiCategoryEntity apiCategoryEntity1 = apiCategoryService.saveOne(apiCategoryEntity);
        return new ResponseEntity<>(apiCategoryEntity1, HttpStatus.OK);
    }

    /**
     * 指定 API 分类 id list，批量删除 API 分类
     * @param idList API 分类 id list
     * @return ResponseEntity<Integer>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "指定 API 分类 id list，批量删除 API 分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) throws BaseResponseException {
        return new ResponseEntity<>(apiCategoryService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定 API 分类 id，获取所有 API 分类
     * @param id API 分类 id
     * @return ResponseEntity<ApiCategoryEntity>
     */
    @OperationLog(value = "指定 API 分类 id，获取所有 API 分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<ApiCategoryEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(apiCategoryService.getOne(id), HttpStatus.OK);
    }

//    /**
//     * 指定角色 id ，获取所有 API 分类，以 Antd Table 形式展示
//     * @param roleId 角色 id
//     * @return ResponseEntity<List<ApiCategoryEntityAsAntdTable>>
//     */
//    @OperationLog(value = "指定角色 id ，获取所有 API 分类，以 Antd Table 形式展示", type = OperationLogType.API)
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/LIST_ALL_AS_ANTD_TABLE_BY_ROLE_ID')")
//    @GetMapping(value = "/list_all_as_antd_table_by_role_id")
//    public ResponseEntity<List<ApiCategoryEntityAsAntdTable>> listAllAsAntdTableByRoleId(@RequestParam Long roleId) {
//        List<ApiCategoryEntityAsAntdTable> apiCategoryEntityAsAntdTableResultList = new ArrayList<>();
//        List<ApiCategoryEntityAsAntdTable> apiCategoryEntityAsAntdTableList = apiCategoryService.listAllApiCategoryEntityAsAntdTableByRoleId(roleId);
//        for(ApiCategoryEntityAsAntdTable apiCategoryEntityAsAntdTable : apiCategoryEntityAsAntdTableList) {
//            if(apiCategoryEntityAsAntdTable.getParentId() == null) {
//                apiCategoryEntityAsAntdTableResultList.add(apiCategoryEntityAsAntdTable);
//            }
//        }
//        for(ApiCategoryEntityAsAntdTable apiCategoryEntityAsAntdTable : apiCategoryEntityAsAntdTableResultList) {
//            apiCategoryEntityAsAntdTable.setChildren(apiCategoryService.getApiCategoryChildrenApiCategoryEntityAsAntdTable(apiCategoryEntityAsAntdTable.getId(), apiCategoryEntityAsAntdTableList));
//
//        }
//        return new ResponseEntity<>(apiCategoryEntityAsAntdTableResultList, HttpStatus.OK);
//    }
//
    /**
     * 获取所有 API 分类，以 Antd Table 形式展示
     * @return ResponseEntity<List<ApiCategoryEntityAsAntdTable>>
     */
    @OperationLog(value = "获取所有 API 分类，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/LIST_ALL_AS_ANTD_TABLE')")
    @GetMapping(value = "/list_all_as_antd_table")
    public ResponseEntity<List<ApiCategoryEntityAsAntdTable>> listAllApiCategoryEntityAsAntdTable() {
        List<ApiCategoryEntityAsAntdTable> apiCategoryEntityAsAntdTableResultList = new ArrayList<>();
        List<ApiCategoryEntityAsAntdTable> apiCategoryEntityAsAntdTableList = apiCategoryService.listAllApiCategoryEntityAsAntdTable();
        for(ApiCategoryEntityAsAntdTable apiCategoryEntityAsAntdTable : apiCategoryEntityAsAntdTableList) {
            if(apiCategoryEntityAsAntdTable.getParentId() == null) {
                apiCategoryEntityAsAntdTableResultList.add(apiCategoryEntityAsAntdTable);
            }
        }
        for(ApiCategoryEntityAsAntdTable apiCategoryEntityAsAntdTable : apiCategoryEntityAsAntdTableResultList) {
            apiCategoryEntityAsAntdTable.setChildren(apiCategoryService.getApiCategoryChildrenApiCategoryEntityAsAntdTable(apiCategoryEntityAsAntdTable.getId(), apiCategoryEntityAsAntdTableList));

        }
        return new ResponseEntity<>(apiCategoryEntityAsAntdTableResultList, HttpStatus.OK);
    }

    /**
     * 获取所有 API 分类，以 Antd TreeNode 形式展示
     * @return ResponseEntity<List<AntdTreeNode>>
     */
    @OperationLog(value = "获取所有 API 分类，以 Antd TreeNode 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/LIST_ALL_AS_ANTD_TREE_NODE')")
    @GetMapping(value = "/list_all_as_antd_tree_node")
    public ResponseEntity<List<AntdTreeNode>> listAllAsTreeNode() {
        List<AntdTreeNode> antdTreeNodeResultList = new ArrayList<>();
        List<AntdTreeNode> apiCategoryAntdTreeNodeList = apiCategoryService.listAllApiCategoryAntdTreeNode();
        for(AntdTreeNode antdTreeNode : apiCategoryAntdTreeNodeList) {
            if(antdTreeNode.getParentId() == null) {
                antdTreeNodeResultList.add(antdTreeNode);
            }
        }
        for(AntdTreeNode antdTreeNode : antdTreeNodeResultList) {
            antdTreeNode.setChildren(apiCategoryService.getApiCategoryChildrenAntdTreeNode(antdTreeNode.getId(), apiCategoryAntdTreeNodeList));
        }
        return new ResponseEntity<>(antdTreeNodeResultList, HttpStatus.OK);
    }

}
