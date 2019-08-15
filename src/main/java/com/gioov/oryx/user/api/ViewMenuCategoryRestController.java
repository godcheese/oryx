package com.gioov.oryx.user.api;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.ViewMenuCategoryEntityAsAntdTable;
import com.gioov.oryx.user.User;
import com.gioov.oryx.user.entity.ViewMenuCategoryEntity;
import com.gioov.oryx.user.service.ViewMenuCategoryService;
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
@RequestMapping(value = User.Api.VIEW_MENU_CATEGORY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ViewMenuCategoryRestController {

    private static final String VIEW_MENU_CATEGORY = "/API/USER/VIEW_MENU_CATEGORY";

    @Autowired
    private ViewMenuCategoryService viewMenuCategoryService;

    /**
     * 指定角色 id，分页获取所有父级视图菜单分类
     * @param roleId 角色 id
     * @param page 页
     * @param rows 每页显示数量
     * @return ResponseEntity<Pagination<ViewMenuCategoryEntity>>
     */
    @OperationLog(value = "指定角色 id，分页获取所有父级视图菜单分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/PAGE_ALL_PARENT_BY_ROLE_ID')")
    @GetMapping(value = "/page_all_parent_by_role_id")
    public ResponseEntity<Pagination<ViewMenuCategoryEntity>> pageAllParent(@RequestParam Long roleId, @RequestParam Integer page, @RequestParam Integer rows) {
        return new ResponseEntity<>(viewMenuCategoryService.pageAllParent(roleId, page, rows), HttpStatus.OK);
    }

    /**
     * 指定父级视图菜单分类 id、角色 id，获取所有视图菜单分类
     * @param parentId 父级视图菜单分类 id
     * @param roleId 角色 id
     * @return ResponseEntity<List<ViewMenuCategoryEntity>>
     */
    @OperationLog(value = "指定父级视图菜单分类 id、角色 id，获取所有视图菜单分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/LIST_ALL_BY_PARENT_ID_AND_ROLE_ID')")
    @GetMapping(value = "/list_all_by_parent_id_and_role_id")
    public ResponseEntity<List<ViewMenuCategoryEntity>> listAllByParentIdAndRoleId(@RequestParam Long parentId, @RequestParam Long roleId) {
        return new ResponseEntity<>(viewMenuCategoryService.listAllByParentIdAndRoleId(parentId, roleId), HttpStatus.OK);
    }

    /**
     * 新增视图菜单分类
     * @param name 视图菜单分类名称
     * @param icon 图标（icon）
     * @param parentId 父级视图菜单分类 id
     * @param sort 排序
     * @param remark 备注
     * @return <ViewMenuCategoryEntity>
     */
    @OperationLog(value = "新增视图菜单分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<ViewMenuCategoryEntity> addOne(@RequestParam String name, @RequestParam(required = false) String icon, @RequestParam(required = false) Long parentId, @RequestParam Long sort, @RequestParam String remark) {
        ViewMenuCategoryEntity viewMenuCategoryEntity = new ViewMenuCategoryEntity();
        viewMenuCategoryEntity.setName(name);
        viewMenuCategoryEntity.setIcon(icon);
        viewMenuCategoryEntity.setParentId(parentId);
        viewMenuCategoryEntity.setSort(sort);
        viewMenuCategoryEntity.setRemark(remark);
        ViewMenuCategoryEntity viewMenuCategoryEntity1 = viewMenuCategoryService.addOne(viewMenuCategoryEntity);
        return new ResponseEntity<>(viewMenuCategoryEntity1, HttpStatus.OK);
    }

    /**
     * 保存视图菜单分类
     * @param id 视图菜单分类 id
     * @param name 视图菜单分类名称
     * @param icon 图标（icon）
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ViewMenuCategoryEntity>
     */
    @OperationLog(value = "保存视图菜单分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<ViewMenuCategoryEntity> saveOne(@RequestParam Long id, @RequestParam String name, @RequestParam(required = false) String icon, @RequestParam Long sort, @RequestParam String remark) {
        ViewMenuCategoryEntity viewMenuCategoryEntity = new ViewMenuCategoryEntity();
        viewMenuCategoryEntity.setId(id);
        viewMenuCategoryEntity.setName(name);
        viewMenuCategoryEntity.setIcon(icon);
        viewMenuCategoryEntity.setSort(sort);
        viewMenuCategoryEntity.setRemark(remark);
        ViewMenuCategoryEntity viewMenuCategoryEntity1 = viewMenuCategoryService.saveOne(viewMenuCategoryEntity);
        return new ResponseEntity<>(viewMenuCategoryEntity1, HttpStatus.OK);
    }

    /**
     * 指定视图菜单分类 id，批量删除视图菜单分类
     * @param idList 视图菜单分类 id list
     * @return ResponseEntity<Integer>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "指定视图菜单分类 id，批量删除视图菜单分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) throws BaseResponseException {
        return new ResponseEntity<>(viewMenuCategoryService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定视图菜单分类 id，获取视图菜单分类
     * @param id 视图菜单分类 id
     * @return ResponseEntity<ViewMenuCategoryEntity>
     */
    @OperationLog(value = "指定视图菜单分类 id，获取视图菜单分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<ViewMenuCategoryEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(viewMenuCategoryService.getOne(id), HttpStatus.OK);
    }

    /**
     * 指定角色 id，获取所有父级视图菜单分类
     * @param roleId 角色 id
     * @return ResponseEntity<List<ViewMenuCategoryEntity>>
     */
    @OperationLog(value = "指定角色 id，获取所有父级视图菜单分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/LIST_ALL_PARENT_BY_ROLE_ID')")
    @GetMapping(value = "/list_all_parent_by_role_id")
    public ResponseEntity<List<ViewMenuCategoryEntity>> listAllParentByRoleId(@RequestParam Long roleId) {
        return new ResponseEntity<>(viewMenuCategoryService.listAllParentByRoleId(roleId), HttpStatus.OK);
    }

    /**
     * 指定用户 id，获取所有父级视图菜单分类
     * @param userId 用户 id
     * @return ResponseEntity<List<ViewMenuCategoryEntity>>
     */
    @OperationLog(value = "指定用户 id，获取所有父级视图菜单分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/LIST_ALL_PARENT_BY_USER_ID')")
    @GetMapping(value = "/list_all_parent_by_user_id")
    public ResponseEntity<List<ViewMenuCategoryEntity>> listAllParentByUserId(@RequestParam Long userId) {
        return new ResponseEntity<>(viewMenuCategoryService.listAllParentByUserId(userId), HttpStatus.OK);
    }

    /**
     * 指定用户 id、父级视图菜单分类 id，获取所有子级视图菜单分类
     * @param parentId 父级视图菜单分类 id
     * @param userId 用户 id
     * @return ResponseEntity<List<ViewMenuCategoryEntity>>
     */
    @OperationLog(value = "指定用户 id、父级视图菜单分类 id，获取所有子级视图菜单分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/LIST_ALL_CHILD_BY_PARENT_ID_AND_USER_ID')")
    @GetMapping(value = "/list_all_child_by_parent_id_and_user_id")
    public ResponseEntity<List<ViewMenuCategoryEntity>> listAllChildByParentIdAndUserId(@RequestParam Long parentId, @RequestParam Long userId) {
        return new ResponseEntity<>(viewMenuCategoryService.listAllChildByParentIdAndUserId(parentId, userId), HttpStatus.OK);
    }

    /**
     * 获取所有视图菜单分类
     * @return ResponseEntity<List<ViewMenuCategoryEntity>>
     */
    @OperationLog(value = "获取所有视图菜单分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/LIST_ALL')")
    @GetMapping(value = "/list_all")
    public ResponseEntity<List<ViewMenuCategoryEntity>> listAll() {
        return new ResponseEntity<>(viewMenuCategoryService.listAll(), HttpStatus.OK);
    }

    /**
     * 指定视图菜单分类名称，模糊搜索获取所有视图菜单分类
     * @param q 视图菜单名称
     * @return ResponseEntity<List<ViewMenuCategoryEntity>>
     */
    @OperationLog(value = "指定视图菜单分类名称，模糊搜索获取所有视图菜单分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/SEARCH_ALL_BY_NAME')")
    @GetMapping(value = "/search_all_by_name")
    public ResponseEntity<List<ViewMenuCategoryEntity>> searchAllByName(@RequestParam String q) {
        return new ResponseEntity<>(viewMenuCategoryService.searchAllByName(q), HttpStatus.OK);
    }

    /**
     * 指定角色 id，获取视图菜单分类，以 Antd Table 形式展示
     * @param roleId 角色 id
     * @return ResponseEntity<List<ViewMenuCategoryEntityAsAntdTable>>
     */
    @OperationLog(value = "指定角色 id，获取视图菜单分类，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/LIST_ALL_AS_ANTD_TABLE_BY_ROLE_ID')")
    @GetMapping(value = "/list_all_as_antd_table_by_role_id")
    public ResponseEntity<List<ViewMenuCategoryEntityAsAntdTable>> listAllAsAntdTableByRoleId(@RequestParam Long roleId) {
        List<ViewMenuCategoryEntityAsAntdTable> viewMenuCategoryEntityAsAntdTableResultList = new ArrayList<>();
        List<ViewMenuCategoryEntityAsAntdTable> viewMenuCategoryEntityAsAntdTableList = viewMenuCategoryService.listAllViewMenuCategoryEntityAsAntdTableByRoleId(roleId);

        for(ViewMenuCategoryEntityAsAntdTable viewMenuCategoryEntityAsAntdTable : viewMenuCategoryEntityAsAntdTableList) {
            if(viewMenuCategoryEntityAsAntdTable.getParentId() == null) {
                viewMenuCategoryEntityAsAntdTableResultList.add(viewMenuCategoryEntityAsAntdTable);
            }
        }
        for(ViewMenuCategoryEntityAsAntdTable viewMenuCategoryEntityAsAntdTable : viewMenuCategoryEntityAsAntdTableResultList) {

                viewMenuCategoryEntityAsAntdTable.setChildren(viewMenuCategoryService.getViewMenuCategoryChildrenViewMenuCategoryEntityAsAntdTable(viewMenuCategoryEntityAsAntdTable.getId(), viewMenuCategoryEntityAsAntdTableList));
        }
        return new ResponseEntity<>(viewMenuCategoryEntityAsAntdTableResultList, HttpStatus.OK);
    }

    /**
     * 获取视图菜单分类，以 Antd Table 形式展示
     * @return ResponseEntity<List<ViewMenuCategoryEntityAsAntdTable>>
     */
    @OperationLog(value = "分页获取所有父级部门", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/LIST_ALL_AS_ANTD_TABLE')")
    @GetMapping(value = "/list_all_as_antd_table")
    public ResponseEntity<List<ViewMenuCategoryEntityAsAntdTable>> listAllAsAntdTable() {
        List<ViewMenuCategoryEntityAsAntdTable> viewMenuCategoryEntityAsAntdTableResultList = new ArrayList<>();
        List<ViewMenuCategoryEntityAsAntdTable> viewMenuCategoryEntityAsAntdTableList = viewMenuCategoryService.listAllViewMenuCategoryEntityAsAntdTable();
        for(ViewMenuCategoryEntityAsAntdTable viewMenuCategoryEntityAsAntdTable : viewMenuCategoryEntityAsAntdTableList) {
            if(viewMenuCategoryEntityAsAntdTable.getParentId() == null) {
                viewMenuCategoryEntityAsAntdTableResultList.add(viewMenuCategoryEntityAsAntdTable);
            }
        }
        for(ViewMenuCategoryEntityAsAntdTable viewMenuCategoryEntityAsAntdTable : viewMenuCategoryEntityAsAntdTableResultList) {
            List<ViewMenuCategoryEntityAsAntdTable> viewMenuCategoryEntityAsAntdTableList1 = viewMenuCategoryService.getViewMenuCategoryChildrenViewMenuCategoryEntityAsAntdTable(viewMenuCategoryEntityAsAntdTable.getId(), viewMenuCategoryEntityAsAntdTableList);
            if(viewMenuCategoryEntityAsAntdTableList1 != null && viewMenuCategoryEntityAsAntdTableList1.size()>0) {
                viewMenuCategoryEntityAsAntdTable.setChildren(viewMenuCategoryEntityAsAntdTableList1);
            }
        }
        return new ResponseEntity<>(viewMenuCategoryEntityAsAntdTableResultList, HttpStatus.OK);
    }

    /**
     * 获取所有视图菜单分类，以 Antd TreeNode 形式展示
     * @return ResponseEntity<List<AntdTreeNode>>
     */
    @OperationLog(value = "获取所有视图菜单分类，以 Antd TreeNode 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/LIST_ALL_AS_ANTD_TREE_NODE')")
    @GetMapping(value = "/list_all_as_antd_tree_node")
    public ResponseEntity<List<AntdTreeNode>> listAllAsTreeNode() {
        List<AntdTreeNode> antdTreeNodeResultList = new ArrayList<>();
        List<AntdTreeNode> viewMenuCategoryAntdTreeNodeList = viewMenuCategoryService.listAllViewMenuCategoryAntdTreeNode();
        for(AntdTreeNode antdTreeNode : viewMenuCategoryAntdTreeNodeList) {
            if(antdTreeNode.getParentId() == null) {
                antdTreeNodeResultList.add(antdTreeNode);
            }
        }
        for(AntdTreeNode antdTreeNode : antdTreeNodeResultList) {
            antdTreeNode.setChildren(viewMenuCategoryService.getViewMenuCategoryChildrenAntdTreeNode(antdTreeNode.getId(), viewMenuCategoryAntdTreeNodeList));
        }
        return new ResponseEntity<>(antdTreeNodeResultList, HttpStatus.OK);
    }

    /**
     * 指定角色 id、视图菜单分类 id list，批量授权
     * @param roleId 角色 id
     * @param viewMenuCategoryIdList 视图菜单分类 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定角色 id、视图菜单分类 id list，批量授权", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/GRANT_ALL_BY_ROLE_ID_AND_VIEW_MENU_CATEGORY_ID_LIST')")
    @PostMapping(value = "/grant_all_by_role_id_and_view_menu_category_id_list")
    public ResponseEntity<Integer> grantAllByRoleIdAndViewMenuCategoryIdList(@RequestParam Long roleId, @RequestParam("viewMenuCategoryIdList[]") List<Long> viewMenuCategoryIdList) {
        return new ResponseEntity<>(viewMenuCategoryService.grantAllByRoleIdAndViewMenuCategoryIdList(roleId, viewMenuCategoryIdList), HttpStatus.OK);
    }

    /**
     * 指定角色 id、视图菜单分类 id list，批量撤销授权
     * @param roleId 角色 id
     * @param viewMenuCategoryIdList 视图菜单分类 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定角色 id、视图菜单分类 id list，批量授权", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU_CATEGORY + "/REVOKE_ALL_BY_ROLE_ID_AND_VIEW_MENU_CATEGORY_ID_LIST')")
    @PostMapping(value = "/revoke_all_by_role_id_and_view_menu_category_id_list")
    public ResponseEntity<Integer> revokeAllByRoleIdAndViewMenuCategoryIdList(@RequestParam Long roleId, @RequestParam("viewMenuCategoryIdList[]") List<Long> viewMenuCategoryIdList) {
        return new ResponseEntity<>(viewMenuCategoryService.revokeAllByRoleIdAndViewMenuCategoryIdList(roleId, viewMenuCategoryIdList), HttpStatus.OK);
    }

}
