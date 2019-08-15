package com.gioov.oryx.user.service.impl;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.FailureMessage;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.user.entity.ViewPageEntity;
import com.gioov.oryx.user.mapper.RoleAuthorityMapper;
import com.gioov.oryx.user.mapper.ViewPageMapper;
import com.gioov.oryx.user.service.ViewPageService;
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
public class ViewPageServiceImpl implements ViewPageService {

    @Autowired
    private ViewPageMapper viewPageMapper;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    @Override
    public Pagination<ViewPageEntity> pageAllByViewPageCategoryId(Long viewPageCategoryId, Integer page, Integer rows) {
        Pagination<ViewPageEntity> pagination = new Pagination<>();
        //        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ViewPageEntity> viewPageEntityPage = viewPageMapper.pageAllByViewPageCategoryId(viewPageCategoryId);
            pagination.setRows(viewPageEntityPage.getResult());
        pagination.setTotal(viewPageEntityPage.getTotal());
        return pagination;
    }

    @Override
    public ViewPageEntity addOne(ViewPageEntity viewPageEntity) throws BaseResponseException {
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
    public ViewPageEntity saveOne(ViewPageEntity viewPageEntity) throws BaseResponseException {
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
        viewPageEntity1.setViewPageCategoryId(viewPageEntity.getViewPageCategoryId());
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
    public int grantAllByRoleIdAndViewPageIdList(Long roleId, List<Long> viewPageIdList) {
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
        return viewPageAuthorityList.size();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int revokeAllByRoleIdAndViewPageIdList(Long roleId, List<Long> viewPageIdList) {
        List<String> viewPageAuthorityList = new ArrayList<>(0);
        ViewPageEntity viewPageEntity;
        for(Long viewPageId : viewPageIdList) {
            viewPageEntity = viewPageMapper.getOne(viewPageId);
            if(viewPageEntity != null) {
                viewPageAuthorityList.add(viewPageEntity.getAuthority());
            }
        }
        roleAuthorityMapper.deleteAllByRoleIdAndAuthorityList(roleId, viewPageAuthorityList);
        return viewPageAuthorityList.size();
    }

    @Override
    public List<ViewPageEntity> listAllViewPageByViewPageCategoryId(Long viewPageCategoryId) {
        return viewPageMapper.listAllByViewPageCategoryId(viewPageCategoryId);
    }

}
