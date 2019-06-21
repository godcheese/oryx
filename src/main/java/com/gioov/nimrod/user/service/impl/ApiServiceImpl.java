package com.gioov.nimrod.user.service.impl;

import com.gioov.common.mybatis.Pageable;
import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.FailureMessage;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.user.entity.ApiEntity;
import com.gioov.nimrod.user.entity.RoleAuthorityEntity;
import com.gioov.nimrod.user.entity.RoleViewMenuEntity;
import com.gioov.nimrod.user.mapper.ApiMapper;
import com.gioov.nimrod.user.mapper.RoleAuthorityMapper;
import com.gioov.nimrod.user.service.ApiService;
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
        List<ApiEntity> apiEntityList = apiMapper.pageAllByApiCategoryId(apiCategoryId, new Pageable(page, rows));
        if (apiEntityList != null) {
            pagination.setRows(apiEntityList);
        }
        pagination.setTotal(apiMapper.countAllByApiCategoryId(apiCategoryId));
        return pagination;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiEntity insertOne(ApiEntity apiEntity) throws BaseResponseException {
        Date date = new Date();
        String authority = apiEntity.getAuthority().toUpperCase();
        if (apiMapper.getOneByAuthority(authority) != null) {
            throw new BaseResponseException(FailureMessage.ADD_API_AUTHORITY_FAIL);
        }
        apiEntity.setAuthority(apiEntity.getAuthority().toUpperCase());
        apiEntity.setGmtModified(date);
        apiEntity.setGmtCreated(date);
        apiMapper.insertOne(apiEntity);
        return apiEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiEntity updateOne(ApiEntity apiEntity) {
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
    public List<String> grantAllByRoleIdAndApiIdList(Long roleId, List<Long> apiIdList) {
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
        return authorityList;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<String> revokeAllByRoleIdAndApiIdList(Long roleId, List<Long> apiIdList) {
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
        return authorityList;
    }
}
