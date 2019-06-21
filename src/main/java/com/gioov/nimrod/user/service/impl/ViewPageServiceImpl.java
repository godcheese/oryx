package com.gioov.nimrod.user.service.impl;

import com.gioov.common.mybatis.Pageable;
import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.FailureMessage;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.common.vue.antd.AntdTreeNode;
import com.gioov.nimrod.user.entity.RoleAuthorityEntity;
import com.gioov.nimrod.user.entity.ViewPageCategoryEntity;
import com.gioov.nimrod.user.entity.ViewPageEntity;
import com.gioov.nimrod.user.mapper.RoleAuthorityMapper;
import com.gioov.nimrod.user.mapper.ViewPageMapper;
import com.gioov.nimrod.user.service.ViewPageService;
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
public class ViewPageServiceImpl implements ViewPageService {

    @Autowired
    private ViewPageMapper viewPageMapper;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    @Override
    public Pagination<ViewPageEntity> pageAllByPageCategoryId(Long pageCategoryId, Integer page, Integer rows) {
        Pagination<ViewPageEntity> pagination = new Pagination<>();
        List<ViewPageEntity> viewPageEntityList = viewPageMapper.pageAllByPageCategoryId(pageCategoryId, new Pageable(page, rows));
        if (viewPageEntityList != null) {
            pagination.setRows(viewPageEntityList);
        }
        pagination.setTotal(viewPageMapper.countAllByPageCategoryId(pageCategoryId));
        return pagination;
    }

    @Override
    public ViewPageEntity insertOne(ViewPageEntity viewPageEntity) throws BaseResponseException {
        Date date = new Date();
        String authority = viewPageEntity.getAuthority().toUpperCase();
        ViewPageEntity viewPageEntity2 = viewPageMapper.getOneByAuthority(authority);
        if (viewPageEntity2 != null) {
            throw new BaseResponseException(FailureMessage.ADD_VIEW_PAGE_AUTHORITY_FAIL);
        }
        viewPageEntity.setAuthority(authority);
        viewPageEntity.setGmtModified(date);
        viewPageEntity.setGmtCreated(date);
        viewPageMapper.insertOne(viewPageEntity);
        return viewPageEntity;
    }

    @Override
    public ViewPageEntity updateOne(ViewPageEntity viewPageEntity) throws BaseResponseException {
        ViewPageEntity viewPageEntity1 = viewPageMapper.getOne(viewPageEntity.getId());
        Date date = new Date();
        String authority = viewPageEntity.getAuthority().toUpperCase();
        ViewPageEntity viewPageEntity2 = viewPageMapper.getOneByAuthority(authority);
        if (viewPageEntity2 != null && !viewPageEntity2.getId().equals(viewPageEntity.getId())) {
            throw new BaseResponseException(FailureMessage.ADD_VIEW_PAGE_AUTHORITY_FAIL);
        }
        viewPageEntity1.setName(viewPageEntity.getName());
        viewPageEntity1.setUrl(viewPageEntity.getUrl());
        viewPageEntity1.setAuthority(authority);
        viewPageEntity1.setPageCategoryId(viewPageEntity.getPageCategoryId());
        viewPageEntity1.setSort(viewPageEntity.getSort());
        viewPageEntity1.setRemark(viewPageEntity.getRemark());
        viewPageEntity1.setGmtModified(date);
        viewPageMapper.updateOne(viewPageEntity1);
        return viewPageEntity1;
    }

    @Override
    public int deleteAll(List<Long> idList) {
        return viewPageMapper.deleteAll(idList);
    }

    @Override
    public ViewPageEntity getOne(Long id) {
        return viewPageMapper.getOne(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<String> grantAllByRoleIdAndViewPageIdList(Long roleId, List<Long> viewPageIdList) {
        // 最终被写入数据库的视图菜单分类 id list
        List<String> viewPageAuthorityList = new ArrayList<>(0);
        ViewPageEntity viewPageEntity;
        for(Long viewPageId : viewPageIdList) {
            viewPageEntity = viewPageMapper.getOne(viewPageId);
            if(viewPageEntity != null) {
                if(roleAuthorityMapper.getOneByRoleIdAndAuthority(roleId, viewPageEntity.getAuthority()) == null) {
                    viewPageAuthorityList.add(viewPageEntity.getAuthority());
                }
            }
        }

        // 过滤后的视图菜单分类 id list 全部写入数据库
        if(!viewPageAuthorityList.isEmpty()) {
            roleAuthorityMapper.insertAllByRoleIdAndAuthorityList(roleId, viewPageAuthorityList);
        }
        return viewPageAuthorityList;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<String> revokeAllByRoleIdAndViewPageIdList(Long roleId, List<Long> viewPageIdList) {
        List<String> viewPageAuthorityList = new ArrayList<>(0);
        ViewPageEntity viewPageEntity;
        for(Long viewPageId : viewPageIdList) {
            viewPageEntity = viewPageMapper.getOne(viewPageId);
            if(viewPageEntity != null) {
                viewPageAuthorityList.add(viewPageEntity.getAuthority());
            }
        }
        roleAuthorityMapper.deleteAllByRoleIdAndAuthorityList(roleId, viewPageAuthorityList);
        return viewPageAuthorityList;
    }

    @Override
    public List<ViewPageEntity> listAllViewPageByViewPageCategoryId(Long viewPageCategoryId) {
        return viewPageMapper.listAllByPageCategoryId(viewPageCategoryId);
    }

}
