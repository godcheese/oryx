package com.gioov.oryx.user.service.impl;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.FailureMessage;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.user.entity.ApiEntity;
import com.gioov.oryx.user.entity.RoleAuthorityEntity;
import com.gioov.oryx.user.mapper.ApiMapper;
import com.gioov.oryx.user.mapper.RoleAuthorityMapper;
import com.gioov.oryx.user.service.ApiService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    @Override
    public Pagination<ApiEntity> pageAllByApiCategoryId(Long apiCategoryId, Integer page, Integer rows) {
        Pagination<ApiEntity> pagination = new Pagination<>();
        //        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ApiEntity> apiEntityPage = apiMapper.pageAllByApiCategoryId(apiCategoryId);
        pagination.setRows(apiEntityPage.getResult());
        pagination.setTotal(apiEntityPage.getTotal());
        return pagination;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiEntity addOne(ApiEntity apiEntity) throws BaseResponseException {
        Date date = new Date();
        String authority = apiEntity.getAuthority().toUpperCase();
        if (apiMapper.getOneByAuthority(authority) != null) {
            throw new BaseResponseException(FailureMessage.ADD_API_AUTHORITY_FAIL);
        }
        apiEntity.setAuthority(authority);
        apiEntity.setGmtModified(date);
        apiEntity.setGmtCreated(date);
        apiMapper.insertOne(apiEntity);
        return apiEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiEntity saveOne(ApiEntity apiEntity) {
        ApiEntity apiEntity1 = apiMapper.getOne(apiEntity.getId());
        apiEntity1.setName(apiEntity.getName());
        apiEntity1.setUrl(apiEntity.getUrl());
        apiEntity1.setAuthority(apiEntity.getAuthority().toUpperCase());
        apiEntity1.setApiCategoryId(apiEntity.getApiCategoryId());
        apiEntity1.setSort(apiEntity.getSort());
        apiEntity1.setRemark(apiEntity.getRemark());
        apiEntity1.setGmtModified(new Date());
        apiMapper.updateOne(apiEntity1);
        return apiEntity1;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteAll(List<Long> idList) {
        return apiMapper.deleteAll(idList);
    }

    @Override
    public ApiEntity getOne(Long id) {
        return apiMapper.getOne(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int grantAllByRoleIdAndApiIdList(Long roleId, List<Long> apiIdList) {
        // 最终被写入数据库的视图菜单分类 id list
        List<String> authorityList = new ArrayList<>(0);
        ApiEntity apiEntity;
        RoleAuthorityEntity roleAuthorityEntity;
        for (Long apiId : apiIdList) {
            apiEntity = apiMapper.getOne(apiId);
            if(apiEntity != null) {
                roleAuthorityEntity = roleAuthorityMapper.getOneByRoleIdAndAuthority(roleId, apiEntity.getAuthority());
                if (roleAuthorityEntity == null) {
                    authorityList.add(apiEntity.getAuthority());
                }
            }
        }
        // 过滤后的视图菜单分类 id list 全部写入数据库
        if(!authorityList.isEmpty()) {
            roleAuthorityMapper.insertAllByRoleIdAndAuthorityList(roleId, authorityList);
        }
        return authorityList.size();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int revokeAllByRoleIdAndApiIdList(Long roleId, List<Long> apiIdList) {
        List<String> authorityList = new ArrayList<>(0);
        ApiEntity apiEntity;
        RoleAuthorityEntity roleAuthorityEntity;
        for (Long apiId : apiIdList) {
            apiEntity = apiMapper.getOne(apiId);
            if(apiEntity != null) {
                roleAuthorityEntity = roleAuthorityMapper.getOneByRoleIdAndAuthority(roleId, apiEntity.getAuthority());
                if (roleAuthorityEntity != null) {
                    authorityList.add(apiEntity.getAuthority());
                }
            }
        }
        // 过滤后的视图菜单分类 id list 全部写入数据库
        if(!authorityList.isEmpty()) {
            roleAuthorityMapper.deleteAllByRoleIdAndAuthorityList(roleId, authorityList);
        }
        return authorityList.size();
    }
}
