package com.gioov.oryx.user.api;

import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.common.vue.antd.AntdVueMenu;
import com.gioov.oryx.common.vue.antd.ViewMenuEntityAsAntdTable;
import com.gioov.oryx.system.service.DictionaryService;
import com.gioov.oryx.user.User;
import com.gioov.oryx.user.entity.*;
import com.gioov.oryx.user.mapper.RoleViewMenuMapper;
import com.gioov.oryx.user.mapper.UserRoleMapper;
import com.gioov.oryx.user.mapper.ViewMenuCategoryMapper;
import com.gioov.oryx.user.mapper.ViewMenuMapper;
import com.gioov.oryx.user.service.RoleService;
import com.gioov.oryx.user.service.UserService;
import com.gioov.oryx.user.service.ViewMenuService;
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
@RequestMapping(value =  User.Api.VIEW_MENU, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ViewMenuRestController {

    private static final String VIEW_MENU = "/API/USER/VIEW_MENU";

    @Autowired
    private ViewMenuService viewMenuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ViewMenuCategoryMapper viewMenuCategoryMapper;

    @Autowired
    private ViewMenuMapper viewMenuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private RoleViewMenuMapper roleViewMenuMapper;

    /**
     * 指定视图菜单分类 id、角色 id，分页获取所有视图菜单
     * @param viewMenuCategoryId 视图菜单分类 id
     * @param roleId 角色 id
     * @param page 页
     * @param rows 每页显示数量
     * @return ResponseEntity<Pagination<ViewMenuEntity>>
     */
    @OperationLog(value = "指定视图菜单分类 id、角色 id，分页获取所有视图菜单", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/PAGE_ALL_BY_VIEW_MENU_CATEGORY_ID_AND_ROLE_ID')")
    @GetMapping(value = "/page_all_by_view_menu_category_id_and_role_id")
    public ResponseEntity<Pagination<ViewMenuEntity>> pageAllByViewMenuCategoryIdAndRoleId(@RequestParam Long viewMenuCategoryId, @RequestParam Long roleId, @RequestParam Integer page, @RequestParam Integer rows) {
        return new ResponseEntity<>(viewMenuService.pageAllByViewMenuCategoryIdAndRoleId(viewMenuCategoryId, roleId, page, rows), HttpStatus.OK);
    }

    /**
     * 新增视图菜单
     * @param name 视图菜单名称
     * @param icon 图标（icon）
     * @param url 请求地址（url）
     * @param viewMenuCategoryId 视图菜单分类 id
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ViewMenuEntity>
     */
    @OperationLog(value = "新增视图菜单", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<ViewMenuEntity> addOne(@RequestParam String name, @RequestParam(required = false) String icon, @RequestParam String url, @RequestParam Long viewMenuCategoryId, @RequestParam Long sort, @RequestParam String remark) {
        ViewMenuEntity viewMenuEntity = new ViewMenuEntity();
        viewMenuEntity.setName(name);
        viewMenuEntity.setIcon(icon);
        viewMenuEntity.setUrl(url);
        viewMenuEntity.setViewMenuCategoryId(viewMenuCategoryId);
        viewMenuEntity.setSort(sort);
        viewMenuEntity.setRemark(remark);
        ViewMenuEntity viewMenuEntity1 = viewMenuService.addOne(viewMenuEntity);
        return new ResponseEntity<>(viewMenuEntity1, HttpStatus.OK);
    }

    /**
     * 保存视图菜单
     * @param id 视图菜单 id
     * @param name 视图菜单名称
     * @param icon 图标（icon）
     * @param url 请求地址（url）
     * @param viewMenuCategoryId 视图菜单分类 id
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ViewMenuEntity>
     */
    @OperationLog(value = "保存视图菜单", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<ViewMenuEntity> saveOne(@RequestParam Long id, @RequestParam String name, @RequestParam(required = false) String icon, @RequestParam String url, @RequestParam Long viewMenuCategoryId, @RequestParam Long sort, @RequestParam String remark) {
        ViewMenuEntity viewMenuEntity = new ViewMenuEntity();
        viewMenuEntity.setId(id);
        viewMenuEntity.setName(name);
        viewMenuEntity.setIcon(icon);
        viewMenuEntity.setUrl(url);
        viewMenuEntity.setViewMenuCategoryId(viewMenuCategoryId);
        viewMenuEntity.setSort(sort);
        viewMenuEntity.setRemark(remark);
        ViewMenuEntity viewMenuEntity1 = viewMenuService.saveOne(viewMenuEntity);
        return new ResponseEntity<>(viewMenuEntity1, HttpStatus.OK);
    }

    /**
     * 指定视图菜单 id list，批量删除视图菜单
     * @param idList 视图菜单 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定视图菜单 id list，批量删除视图菜单", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) {
        return new ResponseEntity<>(viewMenuService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定视图菜单 id，获取视图菜单
     * @param id 视图菜单 id
     * @return ResponseEntity<ViewMenuEntity>
     */
    @OperationLog(value = "指定视图菜单 id，获取视图菜单", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<ViewMenuEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(viewMenuService.getOne(id), HttpStatus.OK);
    }

    /**
     * 指定视图菜单名称，模糊搜索获取所有视图菜单
     * @param q 视图菜单名称
     * @return ResponseEntity<List<ViewMenuEntity>>
     */
    @OperationLog(value = "指定视图菜单名称，模糊搜索获取所有视图菜单", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/SEARCH_ALL_BY_NAME')")
    @GetMapping(value = "/search_all_by_name")
    public ResponseEntity<List<ViewMenuEntity>> searchAllByName(@RequestParam String q) {
        return new ResponseEntity<>(viewMenuService.searchAllByName(q), HttpStatus.OK);
    }

    /**
     * 获取当前用户视图菜单，以 Antd VueMenu 形式展示
     * @return ResponseEntity<List<AntdVueMenu>>
     */
    @OperationLog(value = "获取当前用户视图菜单，以 Antd VueMenu 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/LIST_ALL_AS_VUE_MENU_BY_CURRENT_USER')")
    @GetMapping(value = "/list_all_as_antd_vue_menu_by_current_user")
    public ResponseEntity<List<AntdVueMenu>> listAllAsAntdVueMenuByCurrentUser() {
        List<AntdVueMenu> vueMenuList = new ArrayList<>(0);
        List<AntdVueMenu> vueMenuListResult = new ArrayList<>(0);
        List<ViewMenuCategoryEntity> viewMenuCategoryEntityList = null;
        List<UserRoleEntity> userRoleEntityList;
        UserEntity userEntity = userService.getCurrentUser();
        List<Long> roleIdList = new ArrayList<>(1);
        if ((userRoleEntityList = userRoleMapper.listAllByUserId(userEntity.getId())) != null) {
            List<RoleEntity> roleEntityList;
            if ((roleEntityList = roleService.listAllByUserRoleList(userRoleEntityList)) != null) {
                for (RoleEntity roleEntity : roleEntityList) {
                    roleIdList.add(roleEntity.getId());
                }
            }
        }
        viewMenuCategoryEntityList = viewMenuCategoryMapper.listAllByParentIdIsNullAndRoleIdList(roleIdList);
        if(viewMenuCategoryEntityList != null) {
            for (ViewMenuCategoryEntity viewMenuCategoryEntity : viewMenuCategoryEntityList) {
                viewMenuService.forEachViewMenuAndViewMenuCategoryByViewMenuCategoryId(viewMenuCategoryEntity.getId(), roleIdList, vueMenuList);
                AntdVueMenu vueMenu = new AntdVueMenu();
                vueMenu.setId(viewMenuCategoryEntity.getId());
                vueMenu.setName(viewMenuCategoryEntity.getName());
                vueMenu.setIcon(viewMenuCategoryEntity.getIcon());
                vueMenu.setParentId(viewMenuCategoryEntity.getParentId());
                vueMenu.setIsCategory(true);
                vueMenuList.add(vueMenu);
            }
        }
        for(AntdVueMenu vueMenu : vueMenuList) {
            if(vueMenu.getParentId() == null) {
                vueMenuListResult.add(vueMenu);
            }
        }
        for(AntdVueMenu vueMenu : vueMenuListResult) {
            vueMenu.setChildren(viewMenuService.forEachVueMenuByVueMenuParentId(vueMenu.getId(), vueMenuList));
        }
        return new ResponseEntity<>(vueMenuListResult, HttpStatus.OK);
    }

//    /**
//     * 获取当前用户的菜单
//
//     * @return ResponseEntity<Pagination<VueMenu>>
//     */
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/LIST_ALL_AS_ANTD_TABLE_BY_ROLE_ID_AND_MENU_CATEGORY_ID_LIST')")
//    @GetMapping(value = "/list_all_as_antd_table_by_role_id_and_view_menu_category_id_list")
//    public ResponseEntity<List<ViewMenuEntityAsAntdTable>> listAllAntdTableByRoleIdAndMenuCategoryId(@RequestParam Long roleId, @RequestParam List<Long> viewMenuCategoryIdList) {
//        List<ViewMenuEntityAsAntdTable> viewMenuEntityAsAntdTableResultList = new ArrayList<>(0);
//        List<ViewMenuEntity> viewMenuEntityTableList;
//
//        viewMenuEntityTableList = viewMenuMapper.listAllByViewMenuCategoryIdList(viewMenuCategoryIdList);
//
//        Integer is = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
//        Integer not = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));
//        for(ViewMenuEntity viewMenuEntity : viewMenuEntityTableList) {
//            ViewMenuEntityAsAntdTable viewMenuEntityAsAntdTable = new ViewMenuEntityAsAntdTable();
//            viewMenuEntityAsAntdTable.setId(viewMenuEntity.getId());
//            viewMenuEntityAsAntdTable.setName(viewMenuEntity.getName());
//            viewMenuEntityAsAntdTable.setIcon(viewMenuEntity.getIcon());
//            viewMenuEntityAsAntdTable.setUrl(viewMenuEntity.getUrl());
//            viewMenuEntityAsAntdTable.setViewMenuCategoryId(viewMenuEntity.getViewMenuCategoryId());
//            viewMenuEntityAsAntdTable.setSort(viewMenuEntity.getSort());
//            viewMenuEntityAsAntdTable.setRemark(viewMenuEntity.getRemark());
//            viewMenuEntityAsAntdTable.setGmtModified(viewMenuEntity.getGmtModified());
//            viewMenuEntityAsAntdTable.setGmtCreated(viewMenuEntity.getGmtCreated());
//            // 判断当前角色是否被授权此视图菜单
//            viewMenuEntityAsAntdTable.setIsGranted(roleViewMenuMapper.getOneByRoleIdAndViewMenuId(roleId, viewMenuEntity.getId()) != null ? is : not);
//            viewMenuEntityAsAntdTableResultList.add(viewMenuEntityAsAntdTable);
//        }
//
//        return new ResponseEntity<>(viewMenuEntityAsAntdTableResultList, HttpStatus.OK);
//    }

    /**
     * 指定角色 id、视图菜单分类 id list，分页获取视图菜单，以 Antd Table 形式展示
     * @param page 页
     * @param rows 每页显示数量
     * @param roleId 角色 id
     * @param viewMenuCategoryIdList 视图菜单分类 id list
     * @return ResponseEntity<Pagination<ViewMenuEntityAsAntdTable>>
     */
    @OperationLog(value = "指定角色 id、视图菜单分类 id list，分页获取视图菜单，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/PAGE_ALL_AS_ANTD_TABLE_BY_ROLE_ID_AND_MENU_CATEGORY_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_role_id_and_view_menu_category_id_list")
    public ResponseEntity<Pagination<ViewMenuEntityAsAntdTable>> pageAllAsAntdTableByRoleIdAndMenuCategoryIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long roleId, @RequestParam List<Long> viewMenuCategoryIdList) {
        Pagination<ViewMenuEntityAsAntdTable> pagination = new Pagination<>();
        List<ViewMenuEntityAsAntdTable> viewMenuEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
            PageHelper.startPage(page, rows);
//        }
       Page<ViewMenuEntity> viewMenuEntityPage = viewMenuMapper.pageAllByViewMenuCategoryIdList(viewMenuCategoryIdList);
        Integer isOrNotIs = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
        Integer isOrNotNot = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));
        for(ViewMenuEntity viewMenuEntity : viewMenuEntityPage.getResult()) {
            ViewMenuEntityAsAntdTable viewMenuEntityAsAntdTable = new ViewMenuEntityAsAntdTable();
            viewMenuEntityAsAntdTable.setId(viewMenuEntity.getId());
            viewMenuEntityAsAntdTable.setName(viewMenuEntity.getName());
            viewMenuEntityAsAntdTable.setIcon(viewMenuEntity.getIcon());
            viewMenuEntityAsAntdTable.setUrl(viewMenuEntity.getUrl());
            viewMenuEntityAsAntdTable.setViewMenuCategoryId(viewMenuEntity.getViewMenuCategoryId());
            viewMenuEntityAsAntdTable.setSort(viewMenuEntity.getSort());
            viewMenuEntityAsAntdTable.setRemark(viewMenuEntity.getRemark());
            viewMenuEntityAsAntdTable.setGmtModified(viewMenuEntity.getGmtModified());
            viewMenuEntityAsAntdTable.setGmtCreated(viewMenuEntity.getGmtCreated());
            // 判断当前角色是否被授权此视图菜单
            viewMenuEntityAsAntdTable.setIsGranted(roleViewMenuMapper.getOneByRoleIdAndViewMenuId(roleId, viewMenuEntity.getId()) != null ? isOrNotIs : isOrNotNot);
            viewMenuEntityAsAntdTableResultList.add(viewMenuEntityAsAntdTable);
        }
        pagination.setRows(viewMenuEntityAsAntdTableResultList);
        pagination.setTotal(viewMenuEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    /**
     * 指定视图菜单分类 id list，分页获取视图菜单，以 Antd Table 形式展示
     * @param page 页
     * @param rows 每页显示数量
     * @param viewMenuCategoryIdList 视图菜单分类 id list
     * @return ResponseEntity<Pagination<ViewMenuEntityAsAntdTable>>
     */
    @OperationLog(value = "指定视图菜单分类 id list，分页获取视图菜单，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/PAGE_ALL_AS_ANTD_TABLE_BY_VIEW_MENU_CATEGORY_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_view_menu_category_id_list")
    public ResponseEntity<Pagination<ViewMenuEntityAsAntdTable>> pageAllAsAntdTableByViewMenuCategoryIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam List<Long> viewMenuCategoryIdList) {
        Pagination<ViewMenuEntityAsAntdTable> pagination = new Pagination<>();
        List<ViewMenuEntityAsAntdTable> viewMenuEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ViewMenuEntity> viewMenuEntityPage = viewMenuMapper.pageAllByViewMenuCategoryIdList(viewMenuCategoryIdList);
        for(ViewMenuEntity viewMenuEntity : viewMenuEntityPage.getResult()) {
            ViewMenuEntityAsAntdTable viewMenuEntityAsAntdTable = new ViewMenuEntityAsAntdTable();
            viewMenuEntityAsAntdTable.setId(viewMenuEntity.getId());
            viewMenuEntityAsAntdTable.setName(viewMenuEntity.getName());
            viewMenuEntityAsAntdTable.setIcon(viewMenuEntity.getIcon());
            viewMenuEntityAsAntdTable.setUrl(viewMenuEntity.getUrl());
            viewMenuEntityAsAntdTable.setViewMenuCategoryId(viewMenuEntity.getViewMenuCategoryId());
            viewMenuEntityAsAntdTable.setSort(viewMenuEntity.getSort());
            viewMenuEntityAsAntdTable.setRemark(viewMenuEntity.getRemark());
            viewMenuEntityAsAntdTable.setGmtModified(viewMenuEntity.getGmtModified());
            viewMenuEntityAsAntdTable.setGmtCreated(viewMenuEntity.getGmtCreated());
            viewMenuEntityAsAntdTableResultList.add(viewMenuEntityAsAntdTable);
        }
        pagination.setRows(viewMenuEntityAsAntdTableResultList);
        pagination.setTotal(viewMenuEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

//    /**
//     * 获取当前用户的菜单
//
//     * @return ResponseEntity<Pagination<VueMenu>>
//     */
//    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/LIST_ALL_BY_CURRENT_USER')")
//    @GetMapping(value = "/list_all_antd_view_menu_by_current_user")
//    public ResponseEntity<List<AntdVueMenu>> listAllAntdViewMenuByCurrentUser() {
//        List<AntdVueMenu> vueMenuList = new ArrayList<>(0);
//        List<AntdVueMenu> vueMenuListResult = new ArrayList<>(0);
//        List<ViewMenuCategoryEntity> viewMenuCategoryEntityList = null;
//        List<UserRoleEntity> userRoleEntityList;
//        UserEntity userEntity = userService.getCurrentUser();
//        List<Long> roleIdList = new ArrayList<>(1);
//        if ((userRoleEntityList = userRoleMapper.listAllByUserId(userEntity.getId())) != null) {
//            List<RoleEntity> roleEntityList;
//            if ((roleEntityList = roleService.listAllByUserRoleList(userRoleEntityList)) != null) {
//                for (RoleEntity roleEntity : roleEntityList) {
//                    roleIdList.add(roleEntity.getId());
//                }
//            }
//        }
//
//        viewMenuCategoryEntityList = viewMenuCategoryMapper.listAllByParentIdIsNullAndRoleIdList(roleIdList);
//
//        if(viewMenuCategoryEntityList != null) {
//            for (ViewMenuCategoryEntity viewMenuCategoryEntity : viewMenuCategoryEntityList) {
//                viewMenuService.forEachViewMenuAndViewMenuCategoryByViewMenuCategoryId(viewMenuCategoryEntity.getId(), roleIdList, vueMenuList);
//                AntdVueMenu vueMenu = new AntdVueMenu();
//                vueMenu.setId(viewMenuCategoryEntity.getId());
//                vueMenu.setName(viewMenuCategoryEntity.getName());
//                vueMenu.setIcon(viewMenuCategoryEntity.getIcon());
//                vueMenu.setParentId(viewMenuCategoryEntity.getParentId());
//                vueMenu.setIsCategory(true);
//                vueMenuList.add(vueMenu);
//            }
//        }
//
//        for(AntdVueMenu vueMenu : vueMenuList) {
//            if(vueMenu.getParentId() == null) {
//                vueMenuListResult.add(vueMenu);
//            }
//        }
//
//        for(AntdVueMenu vueMenu : vueMenuListResult) {
//            vueMenu.setChildren(viewMenuService.forEachVueMenuByVueMenuParentId(vueMenu.getId(), vueMenuList));
//        }
//
//        return new ResponseEntity<>(vueMenuListResult, HttpStatus.OK);
//    }

    /**
     * 指定角色 id、视图菜单 id list，批量授权
     * @param roleId 角色 id
     * @param viewMenuIdList 视图菜单 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定角色 id、视图菜单 id list，批量授权", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/GRANT_ALL_BY_ROLE_ID_AND_VIEW_MENU_ID_LIST')")
    @PostMapping(value = "/grant_all_by_role_id_and_view_menu_id_list")
    public ResponseEntity<Integer> grantAllByRoleIdAndViewMenuCategoryIdList(@RequestParam Long roleId, @RequestParam("viewMenuIdList[]") List<Long> viewMenuIdList) {
        return new ResponseEntity<>(viewMenuService.grantAllByRoleIdAndViewMenuIdList(roleId, viewMenuIdList), HttpStatus.OK);
    }

    /**
     * 指定角色 id、视图菜单 id list，批量撤销授权
     * @param roleId 角色 id
     * @param viewMenuIdList 视图菜单 id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定角色 id、视图菜单 id list，批量撤销授权", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + VIEW_MENU + "/REVOKE_ALL_BY_ROLE_ID_AND_VIEW_MENU_ID_LIST')")
    @PostMapping(value = "/revoke_all_by_role_id_and_view_menu_id_list")
    public ResponseEntity<Integer> revokeAllByRoleIdAndViewMenuIdList(@RequestParam Long roleId, @RequestParam("viewMenuIdList[]") List<Long> viewMenuIdList) {
        return new ResponseEntity<>(viewMenuService.revokeAllByRoleIdAndViewMenuIdList(roleId, viewMenuIdList), HttpStatus.OK);
    }

}
