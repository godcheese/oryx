package com.gioov.nimrod.user.api;

import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.common.operationlog.OperationLog;
import com.gioov.nimrod.common.operationlog.OperationLogType;
import com.gioov.nimrod.common.vue.antd.AntdTreeNode;
import com.gioov.nimrod.common.vue.antd.ApiCategoryEntityAsAntdTable;
import com.gioov.nimrod.common.vue.antd.ApiCategoryEntityAsAntdTable;
import com.gioov.nimrod.common.vue.antd.ViewMenuEntityAsAntdTable;
import com.gioov.nimrod.user.User;
import com.gioov.nimrod.user.entity.ApiCategoryEntity;
import com.gioov.nimrod.user.entity.ViewMenuEntity;
import com.gioov.nimrod.user.service.ApiCategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.gioov.nimrod.user.service.UserService.SYSTEM_ADMIN;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@RestController
@RequestMapping(value = User.Api.API_CATEGORY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApiCategoryRestController {

    private static final String API_CATEGORY = "/API/SYSTEM/API_CATEGORY";

    @Autowired
    private ApiCategoryService apiCategoryService;

    /**
     * 分页获取所有父级 API 分类
     *
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<ApiCategoryEntity>
     */
    @OperationLog(value = "分页获取所有父级 API 分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/PAGE_ALL_PARENT')")
    @GetMapping(value = "/page_all_parent")
    public ResponseEntity<Pagination<ApiCategoryEntity>> pageAllParent(@RequestParam Integer page, @RequestParam Integer rows) {
        return new ResponseEntity<>(apiCategoryService.pageAllByParentIdIsNull(page, rows), HttpStatus.OK);
    }

    /**
     * 指定父级 API 分类 id ，获取所有 API 分类
     *
     * @param parentId API 分类父级 id
     * @return ResponseEntity<List                                                               <                                                               ApiCategoryEntity>>
     */
    @OperationLog(value = "获取所有 API 分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/LIST_ALL_BY_PARENT_ID')")
    @GetMapping(value = "/list_all_by_parent_id/{parentId}")
    public ResponseEntity<List<ApiCategoryEntity>> listAllByParentId(@PathVariable Long parentId) {
        return new ResponseEntity<>(apiCategoryService.listAllByParentId(parentId), HttpStatus.OK);
    }

    /**
     * 新增 API 分类
     *
     * @param name     API 分类名
     * @param parentId API 分类 父级 id
     * @param sort     排序
     * @param remark   备注
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
        ApiCategoryEntity apiCategoryEntity1 = apiCategoryService.insertOne(apiCategoryEntity);
        return new ResponseEntity<>(apiCategoryEntity1, HttpStatus.NO_CONTENT);
    }

    /**
     * 保存 API 分类
     *
     * @param id     API 分类 id
     * @param name   API 分类名
     * @param sort   排序
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
        ApiCategoryEntity apiCategoryEntity1 = apiCategoryService.updateOne(apiCategoryEntity);
        return new ResponseEntity<>(apiCategoryEntity1, HttpStatus.OK);
    }

    /**
     * 指定 API 分类 id list ，批量删除 API 分类
     *
     * @param idList API 分类 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "批量删除 API 分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) throws BaseResponseException {
        return new ResponseEntity<>(apiCategoryService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定 API 分类 id ，获取 API 分类信息
     *
     * @param id API 分类 id
     * @return ResponseEntity<ApiCategoryEntity>
     */
    @OperationLog(value = "获取 API 分类信息", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<ApiCategoryEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(apiCategoryService.getOne(id), HttpStatus.OK);
    }

    /**
     * 分页获取所有父级部门
     *
     * @return Pagination<DepartmentEntity>
     */
    @OperationLog(value = "分页获取所有父级部门", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API_CATEGORY + "/LIST_ALL_AS_ANTD_TABLE_BY_ROLE_ID')")
    @GetMapping(value = "/list_all_as_antd_table_by_role_id")
    public ResponseEntity<List<ApiCategoryEntityAsAntdTable>> listAllApiCategoryEntityAsAntdTableByRoleId(@RequestParam Long roleId) {
        List<ApiCategoryEntityAsAntdTable> apiCategoryEntityAsAntdTableResultList = new ArrayList<>();
        List<ApiCategoryEntityAsAntdTable> apiCategoryEntityAsAntdTableList = apiCategoryService.listAllApiCategoryEntityAsAntdTableByRoleId(roleId);

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
     * 分页获取所有父级部门
     *
     * @return Pagination<DepartmentEntity>
     */
    @OperationLog(value = "分页获取所有父级部门", type = OperationLogType.API)
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
     * 获取所有视图菜单分类，以 Antd TreeNode 形式展示
     *
     * @return Pagination<DepartmentEntity>
     */
    @OperationLog(value = "分页获取所有父级部门", type = OperationLogType.API)
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
