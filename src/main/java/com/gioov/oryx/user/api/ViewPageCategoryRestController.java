package com.gioov.oryx.user.api;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.ViewPageCategoryEntityAsAntdTable;
import com.gioov.oryx.user.User;
import com.gioov.oryx.user.entity.ViewPageCategoryEntity;
import com.gioov.oryx.user.service.ViewPageCategoryService;
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
@RequestMapping(value = User.Api.VIEW_PAGE_CATEGORY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ViewPageCategoryRestController {

    private static final String VIEW_PAGE_CATEGORY = "/API/USER/VIEW_PAGE_CATEGORY";

    @Autowired
    private ViewPageCategoryService viewPageCategoryService;

    /**
     * 分页获取所有父级视图页面分类
     * @param page 页
     * @param rows 每页显示数量
     * @return ResponseEntity<Pagination<ViewPageCategoryEntity>>
     */
    @OperationLog(value = "分页获取所有父级视图页面分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_CATEGORY + "/PAGE_ALL_PARENT')")
    @GetMapping(value = "/page_all_parent")
    public ResponseEntity<Pagination<ViewPageCategoryEntity>> pageAllParent(@RequestParam Integer page, @RequestParam Integer rows) {
        return new ResponseEntity<>(viewPageCategoryService.pageAllParent(page, rows), HttpStatus.OK);
    }

    /**
     * 指定父级视图页面分类 id，获取所有视图页面分类
     * @param parentId 父级视图页面分类 id
     * @return ResponseEntity<List<ViewPageCategoryEntity>>
     */
    @OperationLog(value = "指定父级视图页面分类 id，获取所有视图页面分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_CATEGORY + "/LIST_ALL_BY_PARENT_ID')")
    @GetMapping(value = "/list_all_by_parent_id")
    public ResponseEntity<List<ViewPageCategoryEntity>> listAllByParentId(@RequestParam Long parentId) {
        return new ResponseEntity<>(viewPageCategoryService.listAllByParentId(parentId), HttpStatus.OK);
    }

    /**
     * 新增视图页面分类
     * @param name 视图页面分类名称
     * @param parentId 父级视图菜单 id
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ViewPageCategoryEntity>
     */
    @OperationLog(value = "新增视图页面分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_CATEGORY + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<ViewPageCategoryEntity> addOne(@RequestParam String name, @RequestParam(required = false) Long parentId, @RequestParam Long sort, @RequestParam String remark) {
        ViewPageCategoryEntity viewPageCategoryEntity = new ViewPageCategoryEntity();
        viewPageCategoryEntity.setName(name);
        viewPageCategoryEntity.setParentId(parentId);
        viewPageCategoryEntity.setSort(sort);
        viewPageCategoryEntity.setRemark(remark);
        ViewPageCategoryEntity viewPageCategoryEntity1 = viewPageCategoryService.addOne(viewPageCategoryEntity);
        return new ResponseEntity<>(viewPageCategoryEntity1, HttpStatus.OK);
    }

    /**
     * 保存视图页面分类
     * @param id 视图页面分类 id
     * @param name 视图页面分类名称
     * @param parentId 父级视图页面分类 id
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ViewPageCategoryEntity>
     */
    @OperationLog(value = "保存视图页面分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_CATEGORY + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<ViewPageCategoryEntity> saveOne(@RequestParam Long id, @RequestParam String name, @RequestParam Long parentId, @RequestParam Long sort, @RequestParam String remark) {
        ViewPageCategoryEntity viewPageCategoryEntity = new ViewPageCategoryEntity();
        viewPageCategoryEntity.setId(id);
        viewPageCategoryEntity.setName(name);
        viewPageCategoryEntity.setParentId(parentId);
        viewPageCategoryEntity.setSort(sort);
        viewPageCategoryEntity.setRemark(remark);
        ViewPageCategoryEntity viewPageCategoryEntity1 = viewPageCategoryService.saveOne(viewPageCategoryEntity);
        return new ResponseEntity<>(viewPageCategoryEntity1, HttpStatus.OK);
    }

    /**
     * 指定视图页面分类 id ，批量删除视图页面分类
     * @param idList 视图页面分类 id list
     * @return ResponseEntity<Integer>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "指定视图页面分类 id ，批量删除视图页面分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_CATEGORY + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) throws BaseResponseException {
        return new ResponseEntity<>(viewPageCategoryService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定视图页面分类 id，获取视图页面分类
     * @param id 视图页面分类 id
     * @return ResponseEntity<ViewPageCategoryEntity>
     */
    @OperationLog(value = "指定视图页面分类 id，获取视图页面分类", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_CATEGORY + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<ViewPageCategoryEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(viewPageCategoryService.getOne(id), HttpStatus.OK);
    }

    /**
     * 指定角色 id，获取所有视图页面分类，以 Antd Table 形式展示
     * @param roleId 角色 id
     * @return ResponseEntity<List<ViewPageCategoryEntityAsAntdTable>>
     */
    @OperationLog(value = "指定角色 id，获取所有视图页面分类，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_CATEGORY + "/LIST_ALL_AS_ANTD_TABLE_BY_ROLE_ID')")
    @GetMapping(value = "/list_all_as_antd_table_by_role_id")
    public ResponseEntity<List<ViewPageCategoryEntityAsAntdTable>> listAllAsAntdTableByRoleId(@RequestParam Long roleId) {
        List<ViewPageCategoryEntityAsAntdTable> viewPageCategoryEntityAsAntdTableResultList = new ArrayList<>();
        List<ViewPageCategoryEntityAsAntdTable> viewPageCategoryEntityAsAntdTableList = viewPageCategoryService.listAllViewPageCategoryEntityAsAntdTableByRoleId(roleId);
        for(ViewPageCategoryEntityAsAntdTable viewPageCategoryEntityAsAntdTable : viewPageCategoryEntityAsAntdTableList) {
            if(viewPageCategoryEntityAsAntdTable.getParentId() == null) {
                viewPageCategoryEntityAsAntdTableResultList.add(viewPageCategoryEntityAsAntdTable);
            }
        }
        for(ViewPageCategoryEntityAsAntdTable viewPageCategoryEntityAsAntdTable : viewPageCategoryEntityAsAntdTableResultList) {
            viewPageCategoryEntityAsAntdTable.setChildren(viewPageCategoryService.getViewPageCategoryChildrenViewPageCategoryEntityAsAntdTable(viewPageCategoryEntityAsAntdTable.getId(), viewPageCategoryEntityAsAntdTableList));
        }
        return new ResponseEntity<>(viewPageCategoryEntityAsAntdTableResultList, HttpStatus.OK);
    }

    /**
     * 获取视图页面分类，以 Antd Table 形式展示
     * @return ResponseEntity<List<ViewPageCategoryEntityAsAntdTable>>
     */
    @OperationLog(value = "获取视图页面分类，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_CATEGORY + "/LIST_ALL_AS_ANTD_TABLE')")
    @GetMapping(value = "/list_all_as_antd_table")
    public ResponseEntity<List<ViewPageCategoryEntityAsAntdTable>> listAllAsAntdTable() {
        List<ViewPageCategoryEntityAsAntdTable> viewPageCategoryEntityAsAntdTableResultList = new ArrayList<>();
        List<ViewPageCategoryEntityAsAntdTable> viewPageCategoryEntityAsAntdTableList = viewPageCategoryService.listAllViewPageCategoryEntityAsAntdTable();
        for(ViewPageCategoryEntityAsAntdTable viewPageCategoryEntityAsAntdTable : viewPageCategoryEntityAsAntdTableList) {
            if(viewPageCategoryEntityAsAntdTable.getParentId() == null) {
                viewPageCategoryEntityAsAntdTableResultList.add(viewPageCategoryEntityAsAntdTable);
            }
        }
        for(ViewPageCategoryEntityAsAntdTable viewPageCategoryEntityAsAntdTable : viewPageCategoryEntityAsAntdTableResultList) {
            viewPageCategoryEntityAsAntdTable.setChildren(viewPageCategoryService.getViewPageCategoryChildrenViewPageCategoryEntityAsAntdTable(viewPageCategoryEntityAsAntdTable.getId(), viewPageCategoryEntityAsAntdTableList));
        }
        return new ResponseEntity<>(viewPageCategoryEntityAsAntdTableResultList, HttpStatus.OK);
    }

    /**
     * 获取所有视图页面分类，以 Antd TreeNode 形式展示
     * @return ResponseEntity<List<AntdTreeNode>>
     */
    @OperationLog(value = "获取所有视图页面分类，以 Antd TreeNode 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_CATEGORY + "/LIST_ALL_AS_ANTD_TREE_NODE')")
    @GetMapping(value = "/list_all_as_antd_tree_node")
    public ResponseEntity<List<AntdTreeNode>> listAllAsTreeNode() {
        List<AntdTreeNode> antdTreeNodeResultList = new ArrayList<>();
        List<AntdTreeNode> viewPageCategoryAntdTreeNodeList = viewPageCategoryService.listAllViewPageCategoryAntdTreeNode();
        for(AntdTreeNode antdTreeNode : viewPageCategoryAntdTreeNodeList) {
            if(antdTreeNode.getParentId() == null) {
                antdTreeNodeResultList.add(antdTreeNode);
            }
        }
        for(AntdTreeNode antdTreeNode : antdTreeNodeResultList) {
            antdTreeNode.setChildren(viewPageCategoryService.getViewPageCategoryChildrenAntdTreeNode(antdTreeNode.getId(), viewPageCategoryAntdTreeNodeList));
        }
        return new ResponseEntity<>(antdTreeNodeResultList, HttpStatus.OK);
    }

    /**
     * 指定视图页面 id，获取视图页面分类
     * @param viewPageId 视图页面分类 id
     * @return ResponseEntity<ViewPageCategoryEntity>
     */
    @OperationLog(value = "指定视图页面 id，获取视图页面分", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_CATEGORY + "/GET_ONE_BY_VIEW_PAGE_ID')")
    @GetMapping(value = "/get_one_by_view_page_id")
    public ResponseEntity<ViewPageCategoryEntity> getOneByViewPageId(@RequestParam Long viewPageId) {
        return new ResponseEntity<>(viewPageCategoryService.getOneByViewPageId(viewPageId), HttpStatus.OK);
    }

}
