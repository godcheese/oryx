package com.gioov.oryx.user.api;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.common.vue.antd.ViewPageEntityAsAntdTable;
import com.gioov.oryx.system.service.DictionaryService;
import com.gioov.oryx.user.User;
import com.gioov.oryx.user.entity.ViewPageEntity;
import com.gioov.oryx.user.mapper.RoleAuthorityMapper;
import com.gioov.oryx.user.mapper.ViewPageMapper;
import com.gioov.oryx.user.service.ViewPageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
@RequestMapping(value = User.Api.VIEW_PAGE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ViewPageRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewPageRestController.class);

    private static final String VIEW_PAGE = "/API/USER/VIEW_PAGE";

    @Autowired
    private ViewPageService viewPageService;

    @Autowired
    private ViewPageMapper viewPageMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    /**
     * 指定视图页面分类 id ，分页获取所有视图页面
     * @param page 页
     * @param rows 每页显示数量
     * @param viewPageCategoryId 视图页面分类
     * @return ResponseEntity<Pagination<ViewPageEntity>>
     */
    @OperationLog(value = "指定视图页面分类 id ，分页获取所有视图页面", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/PAGE_ALL_BY_VIEW_PAGE_CATEGORY_ID')")
    @GetMapping(value = "/page_all_by_view_page_category_id")
    public ResponseEntity<Pagination<ViewPageEntity>> pageAllByViewPageCategoryId(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long viewPageCategoryId) {
        return new ResponseEntity<>(viewPageService.pageAllByViewPageCategoryId(viewPageCategoryId, page, rows), HttpStatus.OK);
    }

    /**
     * 新增视图页面
     * @param name 视图页面名称
     * @param url 请求地址（url）
     * @param authority 权限（authority）
     * @param viewPageCategoryId 视图页面分类 id
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ViewPageEntity>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "新增视图页面", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<ViewPageEntity> addOne(@RequestParam String name, @RequestParam String url, @RequestParam String authority, @RequestParam Long viewPageCategoryId, @RequestParam Long sort, @RequestParam String remark) throws BaseResponseException {
        ViewPageEntity viewPageEntity = new ViewPageEntity();
        viewPageEntity.setName(name);
        viewPageEntity.setUrl(url);
        viewPageEntity.setAuthority(authority);
        viewPageEntity.setViewPageCategoryId(viewPageCategoryId);
        viewPageEntity.setSort(sort);
        viewPageEntity.setRemark(remark);
        ViewPageEntity viewPageEntity1 = viewPageService.addOne(viewPageEntity);
        return new ResponseEntity<>(viewPageEntity1, HttpStatus.OK);
    }

    /**
     * 保存视图页面
     * @param id 视图页面 id
     * @param name 视图页面名称
     * @param url 请求地址（url）
     * @param authority 权限（authority）
     * @param viewPageCategoryId 视图页面分类 id
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ViewPageEntity>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "保存视图页面", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<ViewPageEntity> saveOne(@RequestParam Long id, @RequestParam String name, @RequestParam String url, @RequestParam String authority, @RequestParam Long viewPageCategoryId, @RequestParam Long sort, @RequestParam String remark) throws BaseResponseException {
        ViewPageEntity viewPageEntity = new ViewPageEntity();
        viewPageEntity.setId(id);
        viewPageEntity.setName(name);
        viewPageEntity.setUrl(url);
        viewPageEntity.setAuthority(authority);
        viewPageEntity.setViewPageCategoryId(viewPageCategoryId);
        viewPageEntity.setSort(sort);
        viewPageEntity.setRemark(remark);
        ViewPageEntity viewPageEntity1 = viewPageService.saveOne(viewPageEntity);
        return new ResponseEntity<>(viewPageEntity1, HttpStatus.OK);
    }

    /**
     * 指定视图页面 id ，批量删除视图页面
     * @param idList 视图页面 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定视图页面 id ，批量删除视图页面", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) {
        return new ResponseEntity<>(viewPageService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定视图页面 id，获取视图页面
     * @param id 视图页面 id
     * @return ResponseEntity<ViewPageEntity>
     */
    @OperationLog(value = "指定视图页面 id，获取视图页面", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<ViewPageEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(viewPageService.getOne(id), HttpStatus.OK);
    }

    /**
     * 指定角色 id，视图页面分类 id list，分页获取视图页面，以 Antd Table 形式展示
     * @param page 页
     * @param rows 每页显示数量
     * @param roleId 角色 id
     * @param viewPageCategoryIdList 视图页面分类 id list
     * @return ResponseEntity<Pagination<ViewPageEntityAsAntdTable>>
     */
    @OperationLog(value = "指定角色 id，视图页面分类 id list，分页获取视图页面，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/PAGE_ALL_AS_ANTD_TABLE_BY_ROLE_ID_AND_VIEW_PAGE_CATEGORY_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_role_id_and_view_page_category_id_list")
    public ResponseEntity<Pagination<ViewPageEntityAsAntdTable>> pageAllAsAntdTableByRoleIdAndPageCategoryIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long roleId, @RequestParam List<Long> viewPageCategoryIdList) {
        Pagination<ViewPageEntityAsAntdTable> pagination = new Pagination<>();
        List<ViewPageEntityAsAntdTable> viewPageEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ViewPageEntity> viewPageEntityPage = viewPageMapper.pageAllByViewPageCategoryIdList(viewPageCategoryIdList);
        Integer isOrNotIs = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
        Integer isOrNotNot = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));
        for(ViewPageEntity viewPageEntity : viewPageEntityPage.getResult()) {
            ViewPageEntityAsAntdTable viewPageEntityAsAntdTable = new ViewPageEntityAsAntdTable();
            viewPageEntityAsAntdTable.setId(viewPageEntity.getId());
            viewPageEntityAsAntdTable.setName(viewPageEntity.getName());
            viewPageEntityAsAntdTable.setUrl(viewPageEntity.getUrl());
            viewPageEntityAsAntdTable.setAuthority(viewPageEntity.getAuthority());
            viewPageEntityAsAntdTable.setViewPageCategoryId(viewPageEntity.getViewPageCategoryId());
            viewPageEntityAsAntdTable.setSort(viewPageEntity.getSort());
            viewPageEntityAsAntdTable.setRemark(viewPageEntity.getRemark());
            viewPageEntityAsAntdTable.setGmtModified(viewPageEntity.getGmtModified());
            viewPageEntityAsAntdTable.setGmtCreated(viewPageEntity.getGmtCreated());
            // 判断当前角色是否被授权此视图页面
            viewPageEntityAsAntdTable.setIsGranted(roleAuthorityMapper.getOneByRoleIdAndAuthority(roleId, viewPageEntity.getAuthority()) != null ? isOrNotIs : isOrNotNot);
            viewPageEntityAsAntdTableResultList.add(viewPageEntityAsAntdTable);
        }
        pagination.setRows(viewPageEntityAsAntdTableResultList);
        pagination.setTotal(viewPageEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    /**
     * 指定视图页面分类 id list，分页获取所有视图页面，以 Antd Table 形式展示
     * @param page 页
     * @param rows 每页显示数量
     * @param viewPageCategoryIdList 视图页面分类 id list
     * @return ResponseEntity<Pagination<ViewPageEntityAsAntdTable>>
     */
    @OperationLog(value = "指定角色 id，视图页面分类 id list，分页获取视图页面，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/PAGE_ALL_AS_ANTD_TABLE_BY_VIEW_PAGE_CATEGORY_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_view_page_category_id_list")
    public ResponseEntity<Pagination<ViewPageEntityAsAntdTable>> pageAllAsAntdTableByViewPageCategoryIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam List<Long> viewPageCategoryIdList) {
        Pagination<ViewPageEntityAsAntdTable> pagination = new Pagination<>();
        List<ViewPageEntityAsAntdTable> viewPageEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ViewPageEntity> viewPageEntityPage = viewPageMapper.pageAllByViewPageCategoryIdList(viewPageCategoryIdList);
        for(ViewPageEntity viewPageEntity : viewPageEntityPage.getResult()) {
            ViewPageEntityAsAntdTable viewPageEntityAsAntdTable = new ViewPageEntityAsAntdTable();
            viewPageEntityAsAntdTable.setId(viewPageEntity.getId());
            viewPageEntityAsAntdTable.setName(viewPageEntity.getName());
            viewPageEntityAsAntdTable.setUrl(viewPageEntity.getUrl());
            viewPageEntityAsAntdTable.setAuthority(viewPageEntity.getAuthority());
            viewPageEntityAsAntdTable.setViewPageCategoryId(viewPageEntity.getViewPageCategoryId());
            viewPageEntityAsAntdTable.setSort(viewPageEntity.getSort());
            viewPageEntityAsAntdTable.setRemark(viewPageEntity.getRemark());
            viewPageEntityAsAntdTable.setGmtModified(viewPageEntity.getGmtModified());
            viewPageEntityAsAntdTable.setGmtCreated(viewPageEntity.getGmtCreated());
            viewPageEntityAsAntdTableResultList.add(viewPageEntityAsAntdTable);
        }
        pagination.setRows(viewPageEntityAsAntdTableResultList);
        pagination.setTotal(viewPageEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    /**
     * 指定角色 id、视图页面 id list，批量授权
     * @param roleId 角色 id
     * @param viewPageIdList 视图页面 id list
     * @return ResponseEntity<List<String>>
     */
    @OperationLog(value = "指定角色 id、视图页面 id list，批量授权", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/GRANT_ALL_BY_ROLE_ID_AND_VIEW_PAGE_ID_LIST')")
    @PostMapping(value = "/grant_all_by_role_id_and_view_page_id_list")
    public ResponseEntity<Integer> grantAllByRoleIdAndViewPageCategoryIdList(@RequestParam Long roleId, @RequestParam("viewPageIdList[]") List<Long> viewPageIdList) {
        return new ResponseEntity<>(viewPageService.grantAllByRoleIdAndViewPageIdList(roleId, viewPageIdList), HttpStatus.OK);
    }

    /**
     * 指定角色 id、视图页面 id list，批量撤销授权
     * @param roleId 角色 id
     * @param viewPageIdList 视图页面 id list
     * @return ResponseEntity<List<String>>
     */
    @OperationLog(value = "指定角色 id、视图页面 id list，批量撤销授权", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/REVOKE_ALL_BY_ROLE_ID_AND_VIEW_PAGE_ID_LIST')")
    @PostMapping(value = "/revoke_all_by_role_id_and_view_page_id_list")
    public ResponseEntity<Integer> revokeAllByRoleIdAndViewPageIdList(@RequestParam Long roleId, @RequestParam("viewPageIdList[]") List<Long> viewPageIdList) {
        return new ResponseEntity<>(viewPageService.revokeAllByRoleIdAndViewPageIdList(roleId, viewPageIdList), HttpStatus.OK);
    }

    /**
     * 指定视图页面分类 id，获取所有视图页面
     * @param viewPageCategoryId 视图页面分类 id
     * @return ResponseEntity<List<ViewPageEntity>>
     */
    @OperationLog(value = "指定视图页面分类 id，获取所有视图页面", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/LIST_ALL_BY_VIEW_PAGE_CATEGORY_ID')")
    @GetMapping(value = "/list_all_by_view_view_page_category_id")
    public ResponseEntity<List<ViewPageEntity>> listAllByViewPageCategoryId(@RequestParam Long viewPageCategoryId) {
        return new ResponseEntity<>(viewPageService.listAllViewPageByViewPageCategoryId(viewPageCategoryId), HttpStatus.OK);
    }

}
