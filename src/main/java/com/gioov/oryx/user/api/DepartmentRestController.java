package com.gioov.oryx.user.api;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.ComboTree;
import com.gioov.oryx.common.easyui.TreeGrid;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.common.vue.antd.AntdTree;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.DepartmentEntityAsAntdTable;
import com.gioov.oryx.user.User;
import com.gioov.oryx.user.entity.DepartmentEntity;
import com.gioov.oryx.user.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value =  User.Api.DEPARTMENT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DepartmentRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentRestController.class);

    private static final String DEPARTMENT = "/API/USER/DEPARTMENT";

    @Autowired
    private DepartmentService departmentService;

//    /**
//     * 分页获取所有父级部门
//     * @param page 页
//     * @param rows 每页显示数量
//     * @return Pagination<DepartmentEntity>
//     */
//    @OperationLog(value = "分页获取所有父级部门", type = OperationLogType.API)
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/PAGE_ALL_PARENT')")
//    @GetMapping(value = "/page_all_parent")
//    public ResponseEntity<Pagination<DepartmentEntity>> pageAllParent(@RequestParam Integer page, @RequestParam Integer rows) {
//        return new ResponseEntity<>(departmentService.pageAllParent(page, rows), HttpStatus.OK);
//    }
//
//    /**
//     * 分页获取所有父级部门
//     * @return List<DepartmentEntity>
//     */
//    @OperationLog(value = "分页获取所有父级部门", type = OperationLogType.API)
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/LIST_ALL_PARENT')")
//    @GetMapping(value = "/list_all_parent")
//    public ResponseEntity<List<DepartmentEntity>> listAllParent() {
//        return new ResponseEntity<>(departmentService.listAllParent(), HttpStatus.OK);
//    }

//    /**
//     * 指定父级部门 id ，获取所有子级部门
//     *
//     * @param parentId API 分类父级 id
//     * @return ResponseEntity<List<DepartmentEntity>>
//     */
//    @OperationLog(value = "获取所有子级部门", type = OperationLogType.API)
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/LIST_ALL_BY_PARENT_ID')")
//    @GetMapping(value = "/list_all_by_parent_id/{parentId}")
//    public ResponseEntity<List<DepartmentEntity>> listAllByParentId(@PathVariable Long parentId) {
//        return new ResponseEntity<>(departmentService.listAllByParentId(parentId), HttpStatus.OK);
//    }

    /**
     * 新增部门
     * @param name 部门名称
     * @param parentId 父级部门 id
     * @param remark 备注
     * @return ResponseEntity<DepartmentEntity>
     */
    @OperationLog(value = "新增部门", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<DepartmentEntity> addOne(@RequestParam String name, @RequestParam Long parentId, @RequestParam String remark) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setName(name);
        departmentEntity.setParentId(parentId);
        departmentEntity.setRemark(remark);
        DepartmentEntity departmentEntity1 = departmentService.addOne(departmentEntity);
        return new ResponseEntity<>(departmentEntity1, HttpStatus.OK);
    }

    /**
     * 保存部门
     * @param id 部门 id
     * @param name 部门名称
     * @param parentId 父级部门 id
     * @param remark 备注
     * @return ResponseEntity<DepartmentEntity>
     */
    @OperationLog(value = "保存部门", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<DepartmentEntity> saveOne(@RequestParam Long id, @RequestParam String name, @RequestParam Long parentId, @RequestParam String remark) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(id);
        departmentEntity.setName(name);
        departmentEntity.setParentId(parentId);
        departmentEntity.setRemark(remark);
        DepartmentEntity departmentEntity1 = departmentService.saveOne(departmentEntity);
        return new ResponseEntity<>(departmentEntity1, HttpStatus.OK);
    }

    /**
     * 指定部门 id list，批量删除部门
     * @param idList 部门 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定部门 id list，批量删除部门", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) throws BaseResponseException {
        return new ResponseEntity<>(departmentService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定部门 id，获取部门
     * @param id 部门 id
     * @return ResponseEntity<DepartmentEntity>
     */
    @OperationLog(value = "指定部门 id，获取部门", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<DepartmentEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(departmentService.getOne(id), HttpStatus.OK);
    }

//    @OperationLog(value = "根据子部门 id 获取所有父级部门", type = OperationLogType.API)
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/LIST_ALL_BY_DEPARTMENT_ID')")
//    @GetMapping(value = "/list_all_by_department_id/{id}")
//    public List<DepartmentEntity> listAllByDepartmentId(@PathVariable Long id) {
//        return departmentService.listAllByDepartmentId(id);
//    }

//    /**
//     * 获取所有部门，以 EasyUI ComboTree 形式展示
//     * @return ResponseEntity<List<ComboTree>>
//     */
//    @OperationLog(value = "获取所有部门，以 EasyUI ComboTree 形式展示", type = OperationLogType.API)
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/LIST_ALL_AS_COMBO_TREE')")
//    @GetMapping(value = "/list_all_as_combo_tree")
//    public ResponseEntity<List<ComboTree>> listAllAsComboTree() {
//        List<ComboTree> comboTreeResultList = new ArrayList<>();
//        List<ComboTree> departmentComboTreeList = departmentService.listAllDepartmentComboTree();
//        for(ComboTree comboTree : departmentComboTreeList) {
//            if(comboTree.getParentId() == null) {
//                comboTreeResultList.add(comboTree);
//            }
//        }
//        for(ComboTree comboTree : comboTreeResultList) {
//            comboTree.setChildren(departmentService.getDepartmentChildrenComboTree(comboTree.getId(), departmentComboTreeList));
//        }
//        return new ResponseEntity<>(comboTreeResultList, HttpStatus.OK);
//    }
//
//    /**
//     * 获取所有部门，以 EasyUI TreeGrid 形式展示
//     * @return ResponseEntity<List<TreeGrid>>
//     */
//    @OperationLog(value = "获取所有部门，以 EasyUI TreeGrid 形式展示", type = OperationLogType.API)
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/LIST_ALL_AS_COMBO_TREE')")
//    @GetMapping(value = "/list_all_as_tree_grid")
//    public ResponseEntity<List<TreeGrid>> listAllAsTreeGrid() {
//        List<TreeGrid> treeGridResultList = new ArrayList<>();
//        List<TreeGrid> departmentTreeGridList = departmentService.listAllDepartmentTreeGrid();
//        for(TreeGrid treeGrid : departmentTreeGridList) {
//            if(treeGrid.getParentId() == null) {
//               treeGridResultList.add(treeGrid);
//            }
//        }
//        for(TreeGrid treeGrid : treeGridResultList) {
//            treeGrid.setChildren(departmentService.getDepartmentChildrenTreeGrid(treeGrid.getId(), departmentTreeGridList));
//        }
//        return new ResponseEntity<>(treeGridResultList, HttpStatus.OK);
//    }

    /**
     * 获取所有部门，以 Antd TreeNode 形式展示
     * @return ResponseEntity<List<AntdTreeNode>>
     */
    @OperationLog(value = "获取所有部门，以 Antd TreeNode 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/LIST_ALL_AS_ANTD_TREE_NODE')")
    @GetMapping(value = "/list_all_as_antd_tree_node")
    public ResponseEntity<List<AntdTreeNode>> listAllAsAntdTreeNode() {
        List<AntdTreeNode> antdTreeNodeResultList = new ArrayList<>();
        List<AntdTreeNode> departmentAntdTreeNodeList = departmentService.listAllDepartmentAntdTreeNode();
        for(AntdTreeNode antdTreeNode : departmentAntdTreeNodeList) {
            if(antdTreeNode.getParentId() == null) {
                antdTreeNodeResultList.add(antdTreeNode);
            }
        }
        for(AntdTreeNode antdTreeNode : antdTreeNodeResultList) {
            antdTreeNode.setChildren(departmentService.getDepartmentChildrenAntdTreeNode(antdTreeNode.getId(), departmentAntdTreeNodeList));
        }
        return new ResponseEntity<>(antdTreeNodeResultList, HttpStatus.OK);
    }

    /**
     * 获取所有部门，以 Antd Tree 形式展示
     * @return ResponseEntity<List<AntdTree>>
     */
    @OperationLog(value = "获取所有部门，以 Antd Tree 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/LIST_ALL_AS_ANTD_TREE')")
    @GetMapping(value = "/list_all_as_antd_tree")
    public ResponseEntity<List<AntdTree>> listAllAsAntdTree() {
        List<AntdTree> treeResultList = new ArrayList<>();
        List<AntdTree> departmentTreeList = departmentService.listAllDepartmentAntdTree();
        for(AntdTree tree : departmentTreeList) {
            if(tree.getParentId() == null) {
                treeResultList.add(tree);
            }
        }
        for(AntdTree tree : treeResultList) {
            tree.setChildren(departmentService.getDepartmentChildrenAntdTree(tree.getId(), departmentTreeList));
        }
        return new ResponseEntity<>(treeResultList, HttpStatus.OK);
    }

    /**
     * 获取所有部门，以 Antd Table 形式展示
     * @return ResponseEntity<List<DepartmentEntityAsAntdTable>>
     */
    @OperationLog(value = "获取所有部门，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + DEPARTMENT + "/LIST_ALL_AS_ANTD_TABLE')")
    @GetMapping(value = "/list_all_as_antd_table")
    public ResponseEntity<List<DepartmentEntityAsAntdTable>> listAllAsAntdTable() {
        List<DepartmentEntityAsAntdTable> departmentEntityAsAntdTableResultList = new ArrayList<>();
        List<DepartmentEntityAsAntdTable> departmentEntityAsAntdTableList = departmentService.listAllDepartmentEntityAsAntdTable();
        for(DepartmentEntityAsAntdTable departmentEntityAsAntdTable : departmentEntityAsAntdTableList) {
            if(departmentEntityAsAntdTable.getParentId() == null) {
                departmentEntityAsAntdTableResultList.add(departmentEntityAsAntdTable);
            }
        }
        for(DepartmentEntityAsAntdTable departmentEntityAsAntdTable : departmentEntityAsAntdTableResultList) {
            List<DepartmentEntityAsAntdTable> departmentEntityAsAntdTableList1 = departmentService.getDepartmentChildrenDepartmentEntityAsAntdTable(departmentEntityAsAntdTable.getId(), departmentEntityAsAntdTableList);
            if(departmentEntityAsAntdTableList1 != null && departmentEntityAsAntdTableList1.size()>0) {
                departmentEntityAsAntdTable.setChildren(departmentEntityAsAntdTableList1);
            }
        }
        return new ResponseEntity<>(departmentEntityAsAntdTableResultList, HttpStatus.OK);
    }

}
