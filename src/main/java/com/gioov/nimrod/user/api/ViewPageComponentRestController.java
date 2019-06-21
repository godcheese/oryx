package com.gioov.nimrod.user.api;

import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.common.vue.antd.ViewPageComponentEntityAsAntdTable;
import com.gioov.nimrod.common.vue.antd.ViewPageEntityAsAntdTable;
import com.gioov.nimrod.system.service.DictionaryService;
import com.gioov.nimrod.user.User;
import com.gioov.nimrod.user.entity.ViewPageComponentEntity;
import com.gioov.nimrod.user.entity.ViewPageEntity;
import com.gioov.nimrod.user.mapper.RoleAuthorityMapper;
import com.gioov.nimrod.user.mapper.ViewPageComponentMapper;
import com.gioov.nimrod.user.service.ViewPageComponentService;
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
@RequestMapping(value = User.Api.VIEW_PAGE_COMPONENT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ViewPageComponentRestController {

    private static final String VIEW_PAGE_COMPONENT = "/API/SYSTEM/VIEW_PAGE_COMPONENT";

    @Autowired
    private ViewPageComponentService viewPageComponentService;

    @Autowired
    private ViewPageComponentMapper viewPageComponentMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    /**
     * 分页获取所有视图页面组件
     *
     * @param page 页
     * @param rows 每页显示数量
     * @return ResponseEntity<Pagination<ViewPageComponentEntity>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/PAGE_ALL_BY_PAGE_ID')")
    @GetMapping(value = "/page_all_by_page_id/{pageId}")
    public ResponseEntity<Pagination<ViewPageComponentEntity>> pageAllByPageId(@PathVariable Long pageId, @RequestParam Integer page, @RequestParam Integer rows) {
        return new ResponseEntity<>(viewPageComponentService.pageAllByPageId(pageId, page, rows), HttpStatus.OK);
    }

    /**
     * 新增视图页面组件
     *
     * @param pageComponentType 视图页面组件类型
     * @param name              视图页面组件名
     * @param authority         权限（authority）
     * @param pageId            视图页面 id
     * @param sort              排序
     * @param remark            备注
     * @return ViewPageComponentEntity
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<ViewPageComponentEntity> addOne(@RequestParam Long pageComponentType, @RequestParam String name, @RequestParam String authority, @RequestParam Long pageId, @RequestParam Long sort, @RequestParam String remark) throws BaseResponseException {
        ViewPageComponentEntity viewPageComponentEntity = new ViewPageComponentEntity();
        viewPageComponentEntity.setPageComponentType(pageComponentType);
        viewPageComponentEntity.setName(name);
        viewPageComponentEntity.setAuthority(authority);
        viewPageComponentEntity.setPageId(pageId);
        viewPageComponentEntity.setSort(sort);
        viewPageComponentEntity.setRemark(remark);
        ViewPageComponentEntity viewPageComponentEntity1 = viewPageComponentService.insertOne(viewPageComponentEntity);
        return new ResponseEntity<>(viewPageComponentEntity1, HttpStatus.OK);
    }

    /**
     * 保存视图页面组件
     *
     * @param id                视图页面组件 id
     * @param pageComponentType 视图页面组件类型
     * @param name              视图页面组件名
     * @param authority         权限（authority）
     * @param sort              排序
     * @param remark            备注
     * @return ViewPageComponentEntity
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<ViewPageComponentEntity> saveOne(@RequestParam Long id, @RequestParam Long pageComponentType, @RequestParam String name, @RequestParam String authority, @RequestParam Long sort, @RequestParam String remark) throws BaseResponseException {
        ViewPageComponentEntity viewPageComponentEntity = new ViewPageComponentEntity();
        viewPageComponentEntity.setId(id);
        viewPageComponentEntity.setName(name);
        viewPageComponentEntity.setPageComponentType(pageComponentType);
        viewPageComponentEntity.setAuthority(authority);
        viewPageComponentEntity.setSort(sort);
        viewPageComponentEntity.setRemark(remark);
        ViewPageComponentEntity viewPageComponentEntity1 = viewPageComponentService.updateOne(viewPageComponentEntity);
        return new ResponseEntity<>(viewPageComponentEntity1, HttpStatus.OK);
    }

    /**
     * 指定视图页面组件 id ，批量删除视图页面组件
     *
     * @param idList 视图页面组件 id list
     * @return ResponseEntity<Integer>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) {
        return new ResponseEntity<>(viewPageComponentService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定视图组件 id ，获取视图组件信息
     *
     * @param id 视图页面组件 id
     * @return ResponseEntity<ViewPageComponentEntity>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<ViewPageComponentEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(viewPageComponentService.getOne(id), HttpStatus.OK);
    }

    /**
     * 获取当前用户的菜单

     * @return ResponseEntity<Pagination<ViewPageEntityAsAntdTable>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/PAGE_ALL_AS_ANTD_TABLE_BY_ROLE_ID_AND_PAGE_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_role_id_and_page_id_list")
    public ResponseEntity<Pagination<ViewPageComponentEntityAsAntdTable>> pageAllAsAntdTableByRoleIdAndPageIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long roleId, @RequestParam List<Long> pageIdList) {
        Pagination<ViewPageComponentEntityAsAntdTable> pagination = new Pagination<>();
        List<ViewPageComponentEntityAsAntdTable> viewPageComponentEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ViewPageComponentEntity> viewPageComponentEntityPage = viewPageComponentMapper.pageAllByPageIdList(pageIdList);
        Integer isOrtNotIs = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
        Integer isOrtNotNot = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));
        for(ViewPageComponentEntity viewPageComponentEntity : viewPageComponentEntityPage.getResult()) {
            ViewPageComponentEntityAsAntdTable viewPageComponentEntityAsAntdTable = new ViewPageComponentEntityAsAntdTable();
            viewPageComponentEntityAsAntdTable.setId(viewPageComponentEntity.getId());
            viewPageComponentEntityAsAntdTable.setPageComponentType(viewPageComponentEntity.getPageComponentType());
            viewPageComponentEntityAsAntdTable.setName(viewPageComponentEntity.getName());
            viewPageComponentEntityAsAntdTable.setAuthority(viewPageComponentEntity.getAuthority());
            viewPageComponentEntityAsAntdTable.setPageId(viewPageComponentEntity.getPageId());
            viewPageComponentEntityAsAntdTable.setSort(viewPageComponentEntity.getSort());
            viewPageComponentEntityAsAntdTable.setRemark(viewPageComponentEntity.getRemark());
            viewPageComponentEntityAsAntdTable.setGmtModified(viewPageComponentEntity.getGmtModified());
            viewPageComponentEntityAsAntdTable.setGmtCreated(viewPageComponentEntity.getGmtCreated());
            // 判断当前角色是否被授权此视图菜单
            viewPageComponentEntityAsAntdTable.setIsGranted(roleAuthorityMapper.getOneByRoleIdAndAuthority(roleId, viewPageComponentEntity.getAuthority()) != null ? isOrtNotIs : isOrtNotNot);
            viewPageComponentEntityAsAntdTableResultList.add(viewPageComponentEntityAsAntdTable);
        }
        pagination.setRows(viewPageComponentEntityAsAntdTableResultList);
        pagination.setTotal(viewPageComponentEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    /**
     * 获取当前用户的菜单

     * @return ResponseEntity<Pagination<ViewPageEntityAsAntdTable>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/PAGE_ALL_AS_ANTD_TABLE_BY_PAGE_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_page_id_list")
    public ResponseEntity<Pagination<ViewPageComponentEntityAsAntdTable>> pageAllAsAntdTableByPageIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam List<Long> pageIdList) {
        Pagination<ViewPageComponentEntityAsAntdTable> pagination = new Pagination<>();
        List<ViewPageComponentEntityAsAntdTable> viewPageComponentEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ViewPageComponentEntity> viewPageComponentEntityPage = viewPageComponentMapper.pageAllByPageIdList(pageIdList);
        for(ViewPageComponentEntity viewPageComponentEntity : viewPageComponentEntityPage.getResult()) {
            ViewPageComponentEntityAsAntdTable viewPageComponentEntityAsAntdTable = new ViewPageComponentEntityAsAntdTable();
            viewPageComponentEntityAsAntdTable.setId(viewPageComponentEntity.getId());
            viewPageComponentEntityAsAntdTable.setPageComponentType(viewPageComponentEntity.getPageComponentType());
            viewPageComponentEntityAsAntdTable.setName(viewPageComponentEntity.getName());
            viewPageComponentEntityAsAntdTable.setAuthority(viewPageComponentEntity.getAuthority());
            viewPageComponentEntityAsAntdTable.setPageId(viewPageComponentEntity.getPageId());
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
     * 指定角色 id、API 权限（authority），批量授权
     *
     * @param roleId        角色 id
     * @param viewPageComponentIdList 权限（authority） list
     * @return ResponseEntity<List < String>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/GRANT_ALL_BY_ROLE_ID_AND_VIEW_PAGE_COMPONENT_ID_LIST')")
    @PostMapping(value = "/grant_all_by_role_id_and_view_page_component_id_list")
    public ResponseEntity<List<String>> grantAllByRoleIdAndViewPageComponentIdList(@RequestParam Long roleId, @RequestParam("viewPageComponentIdList[]") List<Long> viewPageComponentIdList) {
        return new ResponseEntity<>(viewPageComponentService.grantAllByRoleIdAndViewPageComponentIdList(roleId, viewPageComponentIdList), HttpStatus.OK);
    }

    /**
     * 指定角色 id、API 权限（authority），批量授权
     *
     * @param roleId        角色 id
     * @param viewPageComponentIdList 权限（authority） list
     * @return ResponseEntity<List < String>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE_COMPONENT + "/REVOKE_ALL_BY_ROLE_ID_AND_VIEW_PAGE_COMPONENT_ID_LIST')")
    @PostMapping(value = "/revoke_all_by_role_id_and_view_page_component_id_list")
    public ResponseEntity<List<String>> revokeAllByRoleIdAndViewPageComponentIdList(@RequestParam Long roleId, @RequestParam("viewPageComponentIdList[]") List<Long> viewPageComponentIdList) {
        return new ResponseEntity<>(viewPageComponentService.revokeAllByRoleIdAndViewPageComponentIdList(roleId, viewPageComponentIdList), HttpStatus.OK);
    }

}
