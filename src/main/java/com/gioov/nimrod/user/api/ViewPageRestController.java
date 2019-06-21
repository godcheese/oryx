package com.gioov.nimrod.user.api;

import com.alibaba.druid.sql.parser.Lexer;
import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.common.operationlog.OperationLog;
import com.gioov.nimrod.common.operationlog.OperationLogType;
import com.gioov.nimrod.common.vue.antd.AntdTreeNode;
import com.gioov.nimrod.common.vue.antd.ViewPageEntityAsAntdTable;
import com.gioov.nimrod.system.service.DictionaryService;
import com.gioov.nimrod.user.User;
import com.gioov.nimrod.user.entity.ViewPageEntity;
import com.gioov.nimrod.user.mapper.RoleAuthorityMapper;
import com.gioov.nimrod.user.mapper.ViewPageMapper;
import com.gioov.nimrod.user.service.ViewPageService;
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

import static com.gioov.nimrod.user.service.UserService.SYSTEM_ADMIN;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@RestController
@RequestMapping(value = User.Api.VIEW_PAGE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ViewPageRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewPageRestController.class);

    private static final String VIEW_PAGE = "/API/SYSTEM/VIEW_PAGE";

    @Autowired
    private ViewPageService viewPageService;

    @Autowired
    private ViewPageMapper viewPageMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    /**
     * 指定父级视图页面分类 id ，获取所有视图页面
     *
     * @param page           页
     * @param rows           每页显示数量
     * @param pageCategoryId 视图页面分类
     * @return ResponseEntity<Pagination<ViewPageEntity>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/PAGE_ALL_BY_PAGE_CATEGORY_ID')")
    @GetMapping(value = "/page_all_by_page_category_id/{pageCategoryId}")
    public ResponseEntity<Pagination<ViewPageEntity>> pageAllByPageCategoryId(@RequestParam Integer page, @RequestParam Integer rows, @PathVariable Long pageCategoryId) {
        return new ResponseEntity<>(viewPageService.pageAllByPageCategoryId(pageCategoryId, page, rows), HttpStatus.OK);
    }

    /**
     * 新增视图页面
     *
     * @param name           视图页面名
     * @param url            url
     * @param authority      权限（authority）
     * @param pageCategoryId 视图页面分类 id
     * @param sort           排序
     * @param remark         备注
     * @return ResponseEntity<ViewPageEntity>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<ViewPageEntity> addOne(@RequestParam String name, @RequestParam String url, @RequestParam String authority, @RequestParam Long pageCategoryId, @RequestParam Long sort, @RequestParam String remark) throws BaseResponseException {
        ViewPageEntity viewPageEntity = new ViewPageEntity();
        viewPageEntity.setName(name);
        viewPageEntity.setUrl(url);
        viewPageEntity.setAuthority(authority);
        viewPageEntity.setPageCategoryId(pageCategoryId);
        viewPageEntity.setSort(sort);
        viewPageEntity.setRemark(remark);
        ViewPageEntity viewPageEntity1 = viewPageService.insertOne(viewPageEntity);
        return new ResponseEntity<>(viewPageEntity1, HttpStatus.OK);
    }

    /**
     * 保存视图页面
     *
     * @param id        视图页面 id
     * @param name      视图页面名
     * @param url       url
     * @param authority 权限（authority）
     * @param sort      排序
     * @param remark    备注
     * @return ResponseEntity<ViewPageEntity>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<ViewPageEntity> saveOne(@RequestParam Long id, @RequestParam String name, @RequestParam String url, @RequestParam String authority, @RequestParam Long pageCategoryId, @RequestParam Long sort, @RequestParam String remark) throws BaseResponseException {
        ViewPageEntity viewPageEntity = new ViewPageEntity();
        viewPageEntity.setId(id);
        viewPageEntity.setName(name);
        viewPageEntity.setUrl(url);
        viewPageEntity.setAuthority(authority);
        viewPageEntity.setPageCategoryId(pageCategoryId);
        viewPageEntity.setSort(sort);
        viewPageEntity.setRemark(remark);
        ViewPageEntity viewPageEntity1 = viewPageService.updateOne(viewPageEntity);
        return new ResponseEntity<>(viewPageEntity1, HttpStatus.OK);
    }

    /**
     * 指定视图页面 id ，批量删除视图页面
     *
     * @param idList 视图页面 id list
     * @return ResponseEntity<Integer>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) {
        return new ResponseEntity<>(viewPageService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定视图页面 id ，获取视图页面信息
     *
     * @param id 视图页面 id
     * @return ResponseEntity<ViewPageEntity>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<ViewPageEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(viewPageService.getOne(id), HttpStatus.OK);
    }

    /**
     * 获取当前用户的菜单

     * @return ResponseEntity<Pagination<ViewPageEntityAsAntdTable>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/PAGE_ALL_AS_ANTD_TABLE_BY_ROLE_ID_AND_PAGE_CATEGORY_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_role_id_and_page_category_id_list")
    public ResponseEntity<Pagination<ViewPageEntityAsAntdTable>> pageAllAsAntdTableByRoleIdAndPageCategoryIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long roleId, @RequestParam List<Long> pageCategoryIdList) {
        Pagination<ViewPageEntityAsAntdTable> pagination = new Pagination<>();
        List<ViewPageEntityAsAntdTable> viewPageEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ViewPageEntity> viewPageEntityPage = viewPageMapper.pageAllByPageCategoryIdList(pageCategoryIdList);
        Integer isOrtNotIs = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
        Integer isOrtNotNot = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));
        for(ViewPageEntity viewPageEntity : viewPageEntityPage.getResult()) {
            ViewPageEntityAsAntdTable viewPageEntityAsAntdTable = new ViewPageEntityAsAntdTable();
            viewPageEntityAsAntdTable.setId(viewPageEntity.getId());
            viewPageEntityAsAntdTable.setName(viewPageEntity.getName());
            viewPageEntityAsAntdTable.setUrl(viewPageEntity.getUrl());
            viewPageEntityAsAntdTable.setAuthority(viewPageEntity.getAuthority());
            viewPageEntityAsAntdTable.setPageCategoryId(viewPageEntity.getPageCategoryId());
            viewPageEntityAsAntdTable.setSort(viewPageEntity.getSort());
            viewPageEntityAsAntdTable.setRemark(viewPageEntity.getRemark());
            viewPageEntityAsAntdTable.setGmtModified(viewPageEntity.getGmtModified());
            viewPageEntityAsAntdTable.setGmtCreated(viewPageEntity.getGmtCreated());
            // 判断当前角色是否被授权此视图页面
            viewPageEntityAsAntdTable.setIsGranted(roleAuthorityMapper.getOneByRoleIdAndAuthority(roleId, viewPageEntity.getAuthority()) != null ? isOrtNotIs : isOrtNotNot);
            viewPageEntityAsAntdTableResultList.add(viewPageEntityAsAntdTable);
        }
        pagination.setRows(viewPageEntityAsAntdTableResultList);
        pagination.setTotal(viewPageEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    /**
     * 获取当前用户的菜单

     * @return ResponseEntity<Pagination<ViewPageEntityAsAntdTable>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/PAGE_ALL_AS_ANTD_TABLE_BY_PAGE_CATEGORY_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_page_category_id_list")
    public ResponseEntity<Pagination<ViewPageEntityAsAntdTable>> pageAllAsAntdTableByPageCategoryIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam List<Long> pageCategoryIdList) {
        Pagination<ViewPageEntityAsAntdTable> pagination = new Pagination<>();
        List<ViewPageEntityAsAntdTable> viewPageEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ViewPageEntity> viewPageEntityPage = viewPageMapper.pageAllByPageCategoryIdList(pageCategoryIdList);
        for(ViewPageEntity viewPageEntity : viewPageEntityPage.getResult()) {
            ViewPageEntityAsAntdTable viewPageEntityAsAntdTable = new ViewPageEntityAsAntdTable();
            viewPageEntityAsAntdTable.setId(viewPageEntity.getId());
            viewPageEntityAsAntdTable.setName(viewPageEntity.getName());
            viewPageEntityAsAntdTable.setUrl(viewPageEntity.getUrl());
            viewPageEntityAsAntdTable.setAuthority(viewPageEntity.getAuthority());
            viewPageEntityAsAntdTable.setPageCategoryId(viewPageEntity.getPageCategoryId());
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
     * 指定角色 id、API 权限（authority），批量授权
     *
     * @param roleId        角色 id
     * @param viewPageIdList 权限（authority） list
     * @return ResponseEntity<List < String>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/GRANT_ALL_BY_ROLE_ID_AND_VIEW_PAGE_ID_LIST')")
    @PostMapping(value = "/grant_all_by_role_id_and_view_page_id_list")
    public ResponseEntity<List<String>> grantAllByRoleIdAndViewPageCategoryIdList(@RequestParam Long roleId, @RequestParam("viewPageIdList[]") List<Long> viewPageIdList) {
        return new ResponseEntity<>(viewPageService.grantAllByRoleIdAndViewPageIdList(roleId, viewPageIdList), HttpStatus.OK);
    }

    /**
     * 指定角色 id、API 权限（authority），批量授权
     *
     * @param roleId        角色 id
     * @param viewPageIdList 权限（authority） list
     * @return ResponseEntity<List < String>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/REVOKE_ALL_BY_ROLE_ID_AND_VIEW_PAGE_ID_LIST')")
    @PostMapping(value = "/revoke_all_by_role_id_and_view_page_id_list")
    public ResponseEntity<List<String>> revokeAllByRoleIdAndViewPageIdList(@RequestParam Long roleId, @RequestParam("viewPageIdList[]") List<Long> viewPageIdList) {
        return new ResponseEntity<>(viewPageService.revokeAllByRoleIdAndViewPageIdList(roleId, viewPageIdList), HttpStatus.OK);
    }

    /**
     * 获取所有视图菜单分类，以 Antd TreeNode 形式展示
     *
     * @return Pagination<DepartmentEntity>
     */
    @OperationLog(value = "分页获取所有父级部门", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_PAGE + "/LIST_ALL_BY_VIEW_PAGE_CATEGORY_ID')")
    @GetMapping(value = "/list_all_by_view_page_category_id")
    public ResponseEntity<List<ViewPageEntity>> listAllByViewPageCategoryId(@RequestParam Long viewPageCategoryId) {
        return new ResponseEntity<>(viewPageService.listAllViewPageByViewPageCategoryId(viewPageCategoryId), HttpStatus.OK);
    }

}
