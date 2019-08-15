package com.gioov.oryx.user.api;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.common.vue.antd.RoleEntityAsAntdTable;
import com.gioov.oryx.system.service.DictionaryService;
import com.gioov.oryx.user.User;
import com.gioov.oryx.user.entity.RoleEntity;
import com.gioov.oryx.user.mapper.RoleMapper;
import com.gioov.oryx.user.mapper.UserRoleMapper;
import com.gioov.oryx.user.service.RoleService;
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

import static com.gioov.oryx.user.service.UserService.SYSTEM_ADMIN;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@RestController
@RequestMapping(value = User.Api.ROLE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleRestController {

    private static final String ROLE = "/API/USER/ROLE";

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 分页获取所有角色
     * @param page 页
     * @param rows 每页显示数量
     * @return ResponseEntity<Pagination<RoleEntity>>
     */
    @OperationLog(value = "分页获取所有角色", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ROLE + "/PAGE_ALL')")
    @GetMapping(value = "/page_all")
    public ResponseEntity<Pagination<RoleEntity>> pageAll(@RequestParam Integer page, @RequestParam Integer rows) {
        return new ResponseEntity<>(roleService.pageAll(page, rows), HttpStatus.OK);
    }

//    /**
//     * 获取所有角色
//     * @return ResponseEntity<List<RoleEntity>>
//     */
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ROLE + "/LIST_ALL')")
//    @GetMapping(value = "/list_all")
//    public ResponseEntity<List<RoleEntity>> listAll() {
//        return new ResponseEntity<>(roleService.listAll(), HttpStatus.OK);
//    }

//    /**
//     * 指定用户 id，获取用户角色
//     * @param userId 用户 id
//     * @return ResponseEntity<List<RoleEntity>>
//     */
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ROLE + "/ONE')")
//    @GetMapping(value = "/list_all_by_user_id/{userId}")
//    public ResponseEntity<List<RoleEntity>> listAllByUserId(@PathVariable Long userId) {
//        return new ResponseEntity<>(roleService.listAllByUserId(userId), HttpStatus.OK);
//    }

    /**
     * 新增角色
     * @param name 角色名称
     * @param value 角色值
     * @param remark 备注
     * @return ResponseEntity<RoleEntity>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "新增角色", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ROLE + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<RoleEntity> addOne(@RequestParam String name, @RequestParam String value, @RequestParam String remark) throws BaseResponseException {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(name);
        roleEntity.setValue(value);
        roleEntity.setRemark(remark);
        RoleEntity roleEntity1 = roleService.addOne(roleEntity);
        return new ResponseEntity<>(roleEntity1, HttpStatus.OK);
    }

    /**
     * 保存角色
     * @param id 角色 id
     * @param name 角色名称
     * @param value 角色值
     * @param remark 备注
     * @return ResponseEntity<RoleEntity>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "保存角色", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ROLE + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<RoleEntity> saveOne(@RequestParam Long id, @RequestParam String name, @RequestParam String value, @RequestParam String remark) throws BaseResponseException {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(id);
        roleEntity.setName(name);
        roleEntity.setValue(value);
        roleEntity.setRemark(remark);
        RoleEntity roleEntity1 = roleService.saveOne(roleEntity);
        return new ResponseEntity<>(roleEntity1, HttpStatus.OK);
    }

    /**
     * 指定角色 id list，批量删除角色
     * @param idList 角色 id list
     * @return ResponseEntity<Integer>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "指定角色 id list，批量删除角色", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ROLE + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) throws BaseResponseException {
        return new ResponseEntity<>(roleService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定角色 id，获取角色
     * @param id 角色 id
     * @return ResponseEntity<RoleEntity>
     */
    @OperationLog(value = "指定角色 id，获取角色", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ROLE + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<RoleEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(roleService.getOne(id), HttpStatus.OK);
    }

    /**
     * 指定用户 id，分页获取所有角色，以 Antd Table 形式展示
     * @param page 页
     * @param rows 每页显示数量
     * @param userId 用户 id
     * @return ResponseEntity<Pagination<RoleEntityAsAntdTable>>
     */
    @OperationLog(value = "指定用户 id，分页获取所有角色，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ROLE + "/PAGE_ALL_AS_ANTD_TABLE_BY_USER_ID')")
    @GetMapping(value = "/page_all_as_antd_table_by_user_id")
    public ResponseEntity<Pagination<RoleEntityAsAntdTable>> pageAllAsAntdTableByUserId(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long userId) {
        Pagination<RoleEntityAsAntdTable> pagination = new Pagination<>();
        List<RoleEntityAsAntdTable> roleEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<RoleEntity> roleEntityPage = roleMapper.pageAll();
        Integer isOrNotIs = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
        Integer isOrNotNot = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));
        for(RoleEntity roleEntity : roleEntityPage.getResult()) {
            RoleEntityAsAntdTable roleEntityAsAntdTable = new RoleEntityAsAntdTable();
            roleEntityAsAntdTable.setId(roleEntity.getId());
            roleEntityAsAntdTable.setName(roleEntity.getName());
            roleEntityAsAntdTable.setValue(roleEntity.getValue());
            roleEntityAsAntdTable.setRemark(roleEntity.getRemark());
            roleEntityAsAntdTable.setGmtModified(roleEntity.getGmtModified());
            roleEntityAsAntdTable.setGmtCreated(roleEntity.getGmtCreated());
            // 判断当前角色是否被授权此视图菜单
            roleEntityAsAntdTable.setIsGranted(userRoleMapper.getOneByUserIdAndRoleId(userId, roleEntity.getId()) != null ? isOrNotIs : isOrNotNot);
            roleEntityAsAntdTableResultList.add(roleEntityAsAntdTable);
        }
        pagination.setRows(roleEntityAsAntdTableResultList);
        pagination.setTotal(roleEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    /**
     * 指定用户 id、角色 id list，批量授权
     * @param userId 用户 id
     * @param roleIdList 角色 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定用户 id、角色 id list，批量授权", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ROLE + "/GRANT_ALL_BY_USER_ID_AND_ROLE_ID_LIST')")
    @PostMapping(value = "/grant_all_by_user_id_and_role_id_list")
    public ResponseEntity<Integer> grantAllByUserIdAndRoleIdList(@RequestParam Long userId, @RequestParam("roleIdList[]") List<Long> roleIdList) {
        return new ResponseEntity<>(roleService.grantAllByUserIdAndRoleIdList(userId, roleIdList), HttpStatus.OK);
    }

    /**
     * 指定用户 id、角色 id list，批量撤销授权
     * @param userId 用户 id
     * @param roleIdList 角色 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定用户 id、角色 id list，批量撤销授权", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ROLE + "/REVOKE_ALL_BY_USER_ID_AND_ROLE_ID_LIST')")
    @PostMapping(value = "/revoke_all_by_user_id_and_role_id_list")
    public ResponseEntity<Integer> revokeAllByUserIdAndRoleIdList(@RequestParam Long userId, @RequestParam("roleIdList[]") List<Long> roleIdList) {
        return new ResponseEntity<>(roleService.revokeAllByUserIdAndRoleIdList(userId, roleIdList), HttpStatus.OK);
    }

}
