package com.gioov.oryx.user.api;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.common.vue.antd.ApiEntityAsAntdTable;
import com.gioov.oryx.system.service.DictionaryService;
import com.gioov.oryx.user.User;
import com.gioov.oryx.user.entity.ApiEntity;
import com.gioov.oryx.user.mapper.ApiMapper;
import com.gioov.oryx.user.mapper.RoleAuthorityMapper;
import com.gioov.oryx.user.mapper.ViewPageApiMapper;
import com.gioov.oryx.user.mapper.ViewPageComponentApiMapper;
import com.gioov.oryx.user.service.ApiService;
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
@RequestMapping(value = User.Api.API, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApiRestController {

    private static final String API = "/API/USER/API";

    @Autowired
    private ApiService apiService;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    @Autowired
    private ViewPageApiMapper viewPageApiMapper;

    @Autowired
    private ViewPageComponentApiMapper viewPageComponentApiMapper;

    /**
     * 指定 API 分类 id，分页获取所有 API
     * @param page 页
     * @param rows 每页显示数量
     * @param apiCategoryId API 分类 id
     * @return ResponseEntity<Pagination<ApiEntity>>
     */
    @OperationLog(value = "指定 API 分类 id，分页获取所有 API", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API + "/PAGE_ALL_BY_API_CATEGORY_ID')")
    @GetMapping(value = "/page_all_by_api_category_id")
    public ResponseEntity<Pagination<ApiEntity>> pageAllByApiCategoryId(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long apiCategoryId) {
        return new ResponseEntity<>(apiService.pageAllByApiCategoryId(apiCategoryId, page, rows), HttpStatus.OK);
    }

    /**
     * 新增 API
     * @param name API 名称
     * @param url 请求地址（url）
     * @param authority 权限（authority）
     * @param apiCategoryId API 分类 id
     * @param sort 排序
     * @param remark 备注
     * @return ResponseEntity<ApiEntity>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "新增 API", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<ApiEntity> addOne(@RequestParam String name, @RequestParam String url, @RequestParam String authority, @RequestParam Long apiCategoryId, @RequestParam Long sort, @RequestParam String remark) throws BaseResponseException {
        ApiEntity apiEntity = new ApiEntity();
        apiEntity.setName(name);
        apiEntity.setUrl(url);
        apiEntity.setAuthority(authority);
        apiEntity.setApiCategoryId(apiCategoryId);
        apiEntity.setSort(sort);
        apiEntity.setRemark(remark);
        ApiEntity apiEntity1 = apiService.addOne(apiEntity);
        return new ResponseEntity<>(apiEntity1, HttpStatus.OK);
    }

    /**
     * 保存 API
     * @param id     API id
     * @param name   API 名称
     * @param url    请求地址（url）
     * @param sort   排序
     * @param remark 备注
     * @return ResponseEntity<ApiEntity>
     */
    @OperationLog(value = "保存 API", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<ApiEntity> saveOne(@RequestParam Long id, @RequestParam String name, @RequestParam String url, @RequestParam String authority, @RequestParam Long apiCategoryId, @RequestParam Long sort, @RequestParam String remark) {
        ApiEntity apiEntity = new ApiEntity();
        apiEntity.setId(id);
        apiEntity.setName(name);
        apiEntity.setUrl(url);
        apiEntity.setAuthority(authority);
        apiEntity.setApiCategoryId(apiCategoryId);
        apiEntity.setSort(sort);
        apiEntity.setRemark(remark);
        ApiEntity apiEntity1 = apiService.saveOne(apiEntity);
        return new ResponseEntity<>(apiEntity1, HttpStatus.OK);
    }

    /**
     * 指定 API id list，批量删除 API
     * @param idList API id list
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "指定 API id list，批量删除 API", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam("id[]") List<Long> idList) {
        return new ResponseEntity<>(apiService.deleteAll(idList), HttpStatus.OK);
    }

    /**
     * 指定 API id，获取所有 API
     * @param id API id
     * @return ResponseEntity<ApiEntity>
     */
    @OperationLog(value = "指定 API id，获取所有 API", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<ApiEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(apiService.getOne(id), HttpStatus.OK);
    }

    /**
     * 指定角色 id、API 分类 id list，分页获取所有 API，以 Antd Table 形式展示
     * @param page 页
     * @param rows 每页显示数量
     * @param roleId 角色 id
     * @param apiCategoryIdList API 分类 id list
     * @return ResponseEntity<Pagination<ApiEntityAsAntdTable>>
     */
    @OperationLog(value = "指定角色 id、API 分类 id list，分页获取所有 API，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API + "/PAGE_ALL_AS_ANTD_TABLE_BY_ROLE_ID_AND_API_CATEGORY_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_role_id_and_api_category_id_list")
    public ResponseEntity<Pagination<ApiEntityAsAntdTable>> pageAllAsAntdTableByRoleIdAndApiCategoryIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long roleId, @RequestParam List<Long> apiCategoryIdList) {
        Pagination<ApiEntityAsAntdTable> pagination = new Pagination<>();
        List<ApiEntityAsAntdTable> apiEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ApiEntity> apiEntityPage = apiMapper.pageAllByApiCategoryIdList(apiCategoryIdList);
        Integer isOrNotIs = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
        Integer isOrNotNot = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));
        for(ApiEntity apiEntity : apiEntityPage.getResult()) {
            ApiEntityAsAntdTable apiEntityAsAntdTable = new ApiEntityAsAntdTable();
            apiEntityAsAntdTable.setId(apiEntity.getId());
            apiEntityAsAntdTable.setName(apiEntity.getName());
            apiEntityAsAntdTable.setUrl(apiEntity.getUrl());
            apiEntityAsAntdTable.setAuthority(apiEntity.getAuthority());
            apiEntityAsAntdTable.setApiCategoryId(apiEntity.getApiCategoryId());
            apiEntityAsAntdTable.setSort(apiEntity.getSort());
            apiEntityAsAntdTable.setRemark(apiEntity.getRemark());
            apiEntityAsAntdTable.setGmtModified(apiEntity.getGmtModified());
            apiEntityAsAntdTable.setGmtCreated(apiEntity.getGmtCreated());
            // 判断当前角色是否被授权此视图菜单
            apiEntityAsAntdTable.setIsGranted(roleAuthorityMapper.getOneByRoleIdAndAuthority(roleId, apiEntity.getAuthority()) != null ? isOrNotIs : isOrNotNot);
            apiEntityAsAntdTableResultList.add(apiEntityAsAntdTable);
        }
        pagination.setRows(apiEntityAsAntdTableResultList);
        pagination.setTotal(apiEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    /**
     * 指定 API 分类 id list，分页获取所有 API，以 Antd Table 形式展示
     * @param page 页
     * @param rows 每页显示数量
     * @param apiCategoryIdList API 分类 id list
     * @return ResponseEntity<Pagination<ApiEntityAsAntdTable>>
     */
    @OperationLog(value = "指定 API 分类 id list，分页获取所有 API，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API + "/PAGE_ALL_AS_ANTD_TABLE_BY_API_CATEGORY_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_api_category_id_list")
    public ResponseEntity<Pagination<ApiEntityAsAntdTable>> pageAllAsAntdTableByApiCategoryIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam List<Long> apiCategoryIdList) {
        Pagination<ApiEntityAsAntdTable> pagination = new Pagination<>();
        List<ApiEntityAsAntdTable> apiEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ApiEntity> apiEntityPage = apiMapper.pageAllByApiCategoryIdList(apiCategoryIdList);
        for(ApiEntity apiEntity : apiEntityPage.getResult()) {
            ApiEntityAsAntdTable apiEntityAsAntdTable = new ApiEntityAsAntdTable();
            apiEntityAsAntdTable.setId(apiEntity.getId());
            apiEntityAsAntdTable.setName(apiEntity.getName());
            apiEntityAsAntdTable.setUrl(apiEntity.getUrl());
            apiEntityAsAntdTable.setAuthority(apiEntity.getAuthority());
            apiEntityAsAntdTable.setApiCategoryId(apiEntity.getApiCategoryId());
            apiEntityAsAntdTable.setSort(apiEntity.getSort());
            apiEntityAsAntdTable.setRemark(apiEntity.getRemark());
            apiEntityAsAntdTable.setGmtModified(apiEntity.getGmtModified());
            apiEntityAsAntdTable.setGmtCreated(apiEntity.getGmtCreated());
            apiEntityAsAntdTableResultList.add(apiEntityAsAntdTable);
        }
        pagination.setRows(apiEntityAsAntdTableResultList);
        pagination.setTotal(apiEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    /**
     * 指定角色 id、API id list，批量授权
     * @param roleId 角色 id
     * @param apiIdList API id list
     * @return ResponseEntity<List<String>>
     */
    @OperationLog(value = "指定角色 id、API id list，批量授权", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API + "/GRANT_ALL_BY_ROLE_ID_AND_API_ID_LIST')")
    @PostMapping(value = "/grant_all_by_role_id_and_api_id_list")
    public ResponseEntity<Integer> grantAllByRoleIdAndApiCategoryIdList(@RequestParam Long roleId, @RequestParam("apiIdList[]") List<Long> apiIdList) {
        return new ResponseEntity<>(apiService.grantAllByRoleIdAndApiIdList(roleId, apiIdList), HttpStatus.OK);
    }

    /**
     * 指定角色 id、API id list，批量撤销授权
     * @param roleId 角色 id
     * @param apiIdList API id list
     * @return ResponseEntity<List<String>>
     */
    @OperationLog(value = "指定角色 id、API id list，批量撤销授权", type = OperationLogType.API)

    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API + "/REVOKE_ALL_BY_ROLE_ID_AND_API_ID_LIST')")
    @PostMapping(value = "/revoke_all_by_role_id_and_api_id_list")
    public ResponseEntity<Integer> revokeAllByRoleIdAndApiIdList(@RequestParam Long roleId, @RequestParam("apiIdList[]") List<Long> apiIdList) {
        return new ResponseEntity<>(apiService.revokeAllByRoleIdAndApiIdList(roleId, apiIdList), HttpStatus.OK);
    }

    /**
     * 指定视图页面 id、API 分类 id list，分页获取所有 API，以 Antd Table 形式展示
     * @param page 页
     * @param rows 每页显示数量
     * @param viewPageId 视图页面 id
     * @param apiCategoryIdList API 分类 id list
     * @return ResponseEntity<Pagination<ApiEntityAsAntdTable>>
     */
    @OperationLog(value = "指定视图页面 id、API 分类 id list，分页获取所有 API，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API + "/PAGE_ALL_AS_ANTD_TABLE_BY_VIEW_PAGE_ID_AND_API_CATEGORY_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_view_page_id_and_api_category_id_list")
    public ResponseEntity<Pagination<ApiEntityAsAntdTable>> pageAllAsAntdTableByViewPageIdAndApiCategoryIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long viewPageId, @RequestParam List<Long> apiCategoryIdList) {
        Pagination<ApiEntityAsAntdTable> pagination = new Pagination<>();
        List<ApiEntityAsAntdTable> apiEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ApiEntity> apiEntityPage = apiMapper.pageAllByApiCategoryIdList(apiCategoryIdList);
        Integer isOrNotIs = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
        Integer isOrNotNot = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));
        for(ApiEntity apiEntity : apiEntityPage.getResult()) {
            ApiEntityAsAntdTable apiEntityAsAntdTable = new ApiEntityAsAntdTable();
            apiEntityAsAntdTable.setId(apiEntity.getId());
            apiEntityAsAntdTable.setName(apiEntity.getName());
            apiEntityAsAntdTable.setUrl(apiEntity.getUrl());
            apiEntityAsAntdTable.setAuthority(apiEntity.getAuthority());
            apiEntityAsAntdTable.setApiCategoryId(apiEntity.getApiCategoryId());
            apiEntityAsAntdTable.setSort(apiEntity.getSort());
            apiEntityAsAntdTable.setRemark(apiEntity.getRemark());
            apiEntityAsAntdTable.setGmtModified(apiEntity.getGmtModified());
            apiEntityAsAntdTable.setGmtCreated(apiEntity.getGmtCreated());
            // 判断当前角色是否被授权此视图菜单
            apiEntityAsAntdTable.setIsGranted(viewPageApiMapper.getOneByViewPageIdAndApiId(viewPageId, apiEntity.getId()) != null ? isOrNotIs : isOrNotNot);
            apiEntityAsAntdTableResultList.add(apiEntityAsAntdTable);
        }
        pagination.setRows(apiEntityAsAntdTableResultList);
        pagination.setTotal(apiEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    /**
     * 指定视图页面组件 id、API 分类 id list，分页获取 API，以 Antd Table 形式展示
     * @param page 页
     * @param rows 每页显示数量
     * @param viewPageComponentId 视图页面组件 id
     * @param apiCategoryIdList API 分类 id list
     * @return ResponseEntity<Pagination<ApiEntityAsAntdTable>>
     */
    @OperationLog(value = "指定视图页面组件 id、API 分类 id list，分页获取 API，以 Antd Table 形式展示", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + API + "/PAGE_ALL_AS_ANTD_TABLE_BY_VIEW_PAGE_COMPONENT_ID_AND_API_CATEGORY_ID_LIST')")
    @GetMapping(value = "/page_all_as_antd_table_by_view_page_component_id_and_api_category_id_list")
    public ResponseEntity<Pagination<ApiEntityAsAntdTable>> pageAllAsAntdTableByViewPageComponentIdAndApiCategoryIdList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long viewPageComponentId, @RequestParam List<Long> apiCategoryIdList) {
        Pagination<ApiEntityAsAntdTable> pagination = new Pagination<>();
        List<ApiEntityAsAntdTable> apiEntityAsAntdTableResultList = new ArrayList<>(0);

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ApiEntity> apiEntityPage = apiMapper.pageAllByApiCategoryIdList(apiCategoryIdList);
        Integer isOrNotIs = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
        Integer isOrNotNot = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));
        for(ApiEntity apiEntity : apiEntityPage.getResult()) {
            ApiEntityAsAntdTable apiEntityAsAntdTable = new ApiEntityAsAntdTable();
            apiEntityAsAntdTable.setId(apiEntity.getId());
            apiEntityAsAntdTable.setName(apiEntity.getName());
            apiEntityAsAntdTable.setUrl(apiEntity.getUrl());
            apiEntityAsAntdTable.setAuthority(apiEntity.getAuthority());
            apiEntityAsAntdTable.setApiCategoryId(apiEntity.getApiCategoryId());
            apiEntityAsAntdTable.setSort(apiEntity.getSort());
            apiEntityAsAntdTable.setRemark(apiEntity.getRemark());
            apiEntityAsAntdTable.setGmtModified(apiEntity.getGmtModified());
            apiEntityAsAntdTable.setGmtCreated(apiEntity.getGmtCreated());
            // 判断当前角色是否被授权此视图菜单
            apiEntityAsAntdTable.setIsGranted(viewPageComponentApiMapper.getOneByViewPageComponentIdAndApiId(viewPageComponentId, apiEntity.getId()) != null ? isOrNotIs : isOrNotNot);
            apiEntityAsAntdTableResultList.add(apiEntityAsAntdTable);
        }
        pagination.setRows(apiEntityAsAntdTableResultList);
        pagination.setTotal(apiEntityPage.getTotal());
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

}
