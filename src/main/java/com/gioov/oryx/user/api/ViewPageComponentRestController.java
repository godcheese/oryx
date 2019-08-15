package com.gioov.oryx.user.api;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.common.vue.antd.ViewPageComponentEntityAsAntdTable;
import com.gioov.oryx.system.service.DictionaryService;
import com.gioov.oryx.user.User;
import com.gioov.oryx.user.entity.ViewPageComponentEntity;
import com.gioov.oryx.user.mapper.RoleAuthorityMapper;
import com.gioov.oryx.user.mapper.ViewPageComponentMapper;
import com.gioov.oryx.user.service.ViewPageComponentService;
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
@RequestMapping(value = User.Api.VIEW_PAGE_COMPONENT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ViewPageComponentRestController {

    private static final String VIEW_PAGE_COMPONENT = "/API/USER/VIEW_PAGE_COMPONENT";

    @Autowired
    private ViewPageComponentService viewPageComponentService;

    @Autowired
    private ViewPageComponentMapper viewPageComponentMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    /**
     * 指定视图页面 id，分页获取所有视图页面组件
     * @param viewPageId 视图页面 id
     * @param page 页
     * @param rows 每页显示数量
     * @return ResponseEntity<Pagination<ViewPageComponentEntity>>
     */
    @OperationLog(value = "指定视图页面组件 id、API id，批量撤销关联", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/PAGE_ALL_BY_VIEW_PAGE_ID')")
    @GetMapping(value = "/page_all_by_view_page_id")
    public ResponseEntity<Pagination<ViewPageComponentEntity>> pageAllByViewPageId(@RequestParam Long viewPageId, @RequestParam Integer page, @RequestParam Integer rows) {
        return new ResponseEntity<>(viewPageComponentService.pageAllByViewPageId(viewPageId, page, rows), HttpStatus.OK);
    }

    /**
     * 新增视图页面组件
     * @param viewPageComponentType 视图页面组件类型
     * @param name 视图页面组件名称
     * @param authority 权限（authority）
     * @param viewPageId 视图页面 id
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ViewPageComponentEntity>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "新增视图页面组件", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<ViewPageComponentEntity> addOne(@RequestParam Long viewPageComponentType, @RequestParam String name, @RequestParam String authority, @RequestParam Long viewPageId, @RequestParam Long sort, @RequestParam String remark) throws BaseResponseException {
        ViewPageComponentEntity viewPageComponentEntity = new ViewPageComponentEntity();
        viewPageComponentEntity.setViewPageComponentType(viewPageComponentType);
        viewPageComponentEntity.setName(name);
        viewPageComponentEntity.setAuthority(authority);
        viewPageComponentEntity.setViewPageId(viewPageId);
        viewPageComponentEntity.setSort(sort);
        viewPageComponentEntity.setRemark(remark);
        ViewPageComponentEntity viewPageComponentEntity1 = viewPageComponentService.addOne(viewPageComponentEntity);
        return new ResponseEntity<>(viewPageComponentEntity1, HttpStatus.OK);
    }

    /**
     * 保存视图页面组件
     * @param id 视图页面组件 id
     * @param viewPageComponentType 视图页面组件类型
     * @param name 视图页面组件名称
     * @param authority 权限（authority）
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ViewPageComponentEntity>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "保存视图页面组件", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<ViewPageComponentEntity> saveOne(@RequestParam Long id, @RequestParam Long viewPageComponentType, @RequestParam String name, @RequestParam String authority, @RequestParam Long sort, @RequestParam String remark) throws BaseResponseException {
        ViewPageComponentEntity viewPageComponentEntity = new ViewPageComponentEntity();
        viewPageComponentEntity.setId(id);
        viewPageComponentEntity.setName(name);
        viewPageComponentEntity.setViewPageComponentType(viewPageComponentType);
        viewPageComponentEntity.setAuthority(authority);
        viewPageComponentEntity.setSort(sort);
        viewPageComponentEntity.setRemark(remark);
        ViewPageComponentEntity viewPageComponentEntity1 = viewPageComponentService.saveOne(viewPageComponentEntity);
        return new ResponseEntity<>(viewPageComponentEntity1, HttpStatus.OK);
    }

    /**
     * 指定视图页面组件 id，批量删除视图页面组件
     * @param idList 视图页面组件 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定视图页面组件 id，批量删除视图页面组件", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) {
        return new ResponseEntity<>(viewPageComponentService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定视图组件 id，获取视图组件
     * @param id 视图页面组件 id
     * @return ResponseEntity<ViewPageComponentEntity>
     */
    @OperationLog(value = "指定视图组件 id，获取视图组件", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<ViewPageComponentEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(viewPageComponentService.getOne(id), HttpStatus.OK);
    }

    /**
     * 指定角色 id、视图页面 id list，分页获取所有视图页面组件，以 Antd Table 形式展示
     * @param page 页
     * @param rows 每页显示数量
     * @param roleId 角色 id
     * @param viewPageIdList 视图页面 id list
     * @return ResponseEntity<Pagination<ViewPageComponentEntityAsAntdTable>>
     */
    @OperationLog(value = "指定角色 id、视图页面 id list，分页获取所有视图页面组件，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/PAGE_ALL_AS_ANTD_TABLE_BY_ROLE_ID_AND_VIEW_PAGE_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_role_id_and_view_page_id_list")
    public ResponseEntity<Pagination<ViewPageComponentEntityAsAntdTable>> pageAllAsAntdTableByRoleIdAndViewPageIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long roleId, @RequestParam List<Long> viewPageIdList) {
        Pagination<ViewPageComponentEntityAsAntdTable> pagination = new Pagination<>();
        List<ViewPageComponentEntityAsAntdTable> viewPageComponentEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ViewPageComponentEntity> viewPageComponentEntityPage = viewPageComponentMapper.pageAllByViewPageIdList(viewPageIdList);
        Integer isOrNotIs = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
        Integer isOrNotNot = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));
        for(ViewPageComponentEntity viewPageComponentEntity : viewPageComponentEntityPage.getResult()) {
            ViewPageComponentEntityAsAntdTable viewPageComponentEntityAsAntdTable = new ViewPageComponentEntityAsAntdTable();
            viewPageComponentEntityAsAntdTable.setId(viewPageComponentEntity.getId());
            viewPageComponentEntityAsAntdTable.setViewPageComponentType(viewPageComponentEntity.getViewPageComponentType());
            viewPageComponentEntityAsAntdTable.setName(viewPageComponentEntity.getName());
            viewPageComponentEntityAsAntdTable.setAuthority(viewPageComponentEntity.getAuthority());
            viewPageComponentEntityAsAntdTable.setViewPageId(viewPageComponentEntity.getViewPageId());
            viewPageComponentEntityAsAntdTable.setSort(viewPageComponentEntity.getSort());
            viewPageComponentEntityAsAntdTable.setRemark(viewPageComponentEntity.getRemark());
            viewPageComponentEntityAsAntdTable.setGmtModified(viewPageComponentEntity.getGmtModified());
            viewPageComponentEntityAsAntdTable.setGmtCreated(viewPageComponentEntity.getGmtCreated());
            // 判断当前角色是否被授权此视图菜单
            viewPageComponentEntityAsAntdTable.setIsGranted(roleAuthorityMapper.getOneByRoleIdAndAuthority(roleId, viewPageComponentEntity.getAuthority()) != null ? isOrNotIs : isOrNotNot);
            viewPageComponentEntityAsAntdTableResultList.add(viewPageComponentEntityAsAntdTable);
        }
        pagination.setRows(viewPageComponentEntityAsAntdTableResultList);
        pagination.setTotal(viewPageComponentEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    /**
     * 指定视图页面 id list，分页获取视图页面组件，以 Antd Table 形式展示
     * @param page 页
     * @param rows 每页显示数量
     * @param viewPageIdList 视图页面 id list
     * @return ResponseEntity<Pagination<ViewPageComponentEntityAsAntdTable>>
     */
    @OperationLog(value = "指定视图页面 id list，分页获取视图页面组件，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/PAGE_ALL_AS_ANTD_TABLE_BY_VIEW_PAGE_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_view_page_id_list")
    public ResponseEntity<Pagination<ViewPageComponentEntityAsAntdTable>> pageAllAsAntdTableByViewPageIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam List<Long> viewPageIdList) {
        Pagination<ViewPageComponentEntityAsAntdTable> pagination = new Pagination<>();
        List<ViewPageComponentEntityAsAntdTable> viewPageComponentEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ViewPageComponentEntity> viewPageComponentEntityPage = viewPageComponentMapper.pageAllByViewPageIdList(viewPageIdList);
        for(ViewPageComponentEntity viewPageComponentEntity : viewPageComponentEntityPage.getResult()) {
            ViewPageComponentEntityAsAntdTable viewPageComponentEntityAsAntdTable = new ViewPageComponentEntityAsAntdTable();
            viewPageComponentEntityAsAntdTable.setId(viewPageComponentEntity.getId());
            viewPageComponentEntityAsAntdTable.setViewPageComponentType(viewPageComponentEntity.getViewPageComponentType());
            viewPageComponentEntityAsAntdTable.setName(viewPageComponentEntity.getName());
            viewPageComponentEntityAsAntdTable.setAuthority(viewPageComponentEntity.getAuthority());
            viewPageComponentEntityAsAntdTable.setViewPageId(viewPageComponentEntity.getViewPageId());
            viewPageComponentEntityAsAntdTable.setSort(viewPageComponentEntity.getSort());
            viewPageComponentEntityAsAntdTable.setRemark(viewPageComponentEntity.getRemark());
            viewPageComponentEntityAsAntdTable.setGmtModified(viewPageComponentEntity.getGmtModified());
            viewPageComponentEntityAsAntdTable.setGmtCreated(viewPageComponentEntity.getGmtCreated());
            viewPageComponentEntityAsAntdTableResultList.add(viewPageComponentEntityAsAntdTable);
        }
        pagination.setRows(viewPageComponentEntityAsAntdTableResultList);
        pagination.setTotal(viewPageComponentEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    /**
     * 指定角色 id、视图页面组件 id list，批量授权
     * @param roleId 角色 id
     * @param viewPageComponentIdList 视图页面组件 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定角色 id、视图页面组件 id list，批量授权", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/GRANT_ALL_BY_ROLE_ID_AND_VIEW_PAGE_COMPONENT_ID_LIST')")
    @PostMapping(value = "/grant_all_by_role_id_and_view_page_component_id_list")
    public ResponseEntity<Integer> grantAllByRoleIdAndViewPageComponentIdList(@RequestParam Long roleId, @RequestParam("viewPageComponentIdList[]") List<Long> viewPageComponentIdList) {
        return new ResponseEntity<>(viewPageComponentService.grantAllByRoleIdAndViewPageComponentIdList(roleId, viewPageComponentIdList), HttpStatus.OK);
    }

    /**
     * 指定角色 id、视图页面组件 id list，批量撤销授权
     * @param roleId 角色 id
     * @param viewPageComponentIdList 视图页面组件 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定角色 id、视图页面组件 id list，批量撤销授权", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/REVOKE_ALL_BY_ROLE_ID_AND_VIEW_PAGE_COMPONENT_ID_LIST')")
    @PostMapping(value = "/revoke_all_by_role_id_and_view_page_component_id_list")
    public ResponseEntity<Integer> revokeAllByRoleIdAndViewPageComponentIdList(@RequestParam Long roleId, @RequestParam("viewPageComponentIdList[]") List<Long> viewPageComponentIdList) {
        return new ResponseEntity<>(viewPageComponentService.revokeAllByRoleIdAndViewPageComponentIdList(roleId, viewPageComponentIdList), HttpStatus.OK);
    }

}
