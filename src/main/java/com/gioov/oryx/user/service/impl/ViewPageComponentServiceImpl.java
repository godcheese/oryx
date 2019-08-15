package com.gioov.oryx.user.service.impl;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.FailureMessage;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.user.entity.ViewPageComponentEntity;
import com.gioov.oryx.user.mapper.RoleAuthorityMapper;
import com.gioov.oryx.user.mapper.ViewPageComponentMapper;
import com.gioov.oryx.user.service.ViewPageComponentService;
import com.github.pagehelper.Page;
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
public class ViewPageComponentServiceImpl implements ViewPageComponentService {

    @Autowired
    private ViewPageComponentMapper viewPageComponentMapper;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    @Override
    public Pagination<ViewPageComponentEntity> pageAllByViewPageId(Long viewPageId, Integer page, Integer rows) {
        Pagination<ViewPageComponentEntity> pagination = new Pagination<>();
        Page<ViewPageComponentEntity> viewPageComponentEntityPage = viewPageComponentMapper.pageAllByViewPageId(viewPageId);
        pagination.setRows(viewPageComponentEntityPage.getResult());
        pagination.setTotal(viewPageComponentEntityPage.getTotal());
        return pagination;
    }

    @Override
    public ViewPageComponentEntity addOne(ViewPageComponentEntity viewPageComponentEntity) throws BaseResponseException {
        Date date = new Date();
        String authority = viewPageComponentEntity.getAuthority().toUpperCase();
        if (viewPageComponentMapper.getOneByAuthority(authority) != null) {
            throw new BaseResponseException(FailureMessage.ADD_API_AUTHORITY_FAIL);
        }
        viewPageComponentEntity.setAuthority(authority);
        viewPageComponentEntity.setGmtModified(date);
        viewPageComponentEntity.setGmtCreated(date);
        viewPageComponentMapper.insertOne(viewPageComponentEntity);
        return viewPageComponentEntity;
    }

    @Override
    public ViewPageComponentEntity saveOne(ViewPageComponentEntity viewPageComponentEntity) throws BaseResponseException {
        ViewPageComponentEntity viewPageComponentEntity1 = viewPageComponentMapper.getOne(viewPageComponentEntity.getId());
        String authority = viewPageComponentEntity.getAuthority().toUpperCase();
        ViewPageComponentEntity viewPageComponentEntity2 = viewPageComponentMapper.getOneByAuthority(authority);
        if (viewPageComponentEntity2 != null && !viewPageComponentEntity2.getId().equals(viewPageComponentEntity.getId())) {
            throw new BaseResponseException(FailureMessage.ADD_API_AUTHORITY_FAIL);
        }
        viewPageComponentEntity1.setViewPageComponentType(viewPageComponentEntity.getViewPageComponentType());
        viewPageComponentEntity1.setName(viewPageComponentEntity.getName());
        viewPageComponentEntity1.setAuthority(authority);
        viewPageComponentEntity1.setSort(viewPageComponentEntity.getSort());
        viewPageComponentEntity1.setRemark(viewPageComponentEntity.getRemark());
        viewPageComponentEntity1.setGmtModified(new Date());
        viewPageComponentMapper.updateOne(viewPageComponentEntity1);
        return viewPageComponentEntity1;
    }

    @Override
    public int deleteAll(List<Long> idList) {
        return viewPageComponentMapper.deleteAll(idList);
    }

    @Override
    public ViewPageComponentEntity getOne(Long id) {
        return viewPageComponentMapper.getOne(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int grantAllByRoleIdAndViewPageComponentIdList(Long roleId, List<Long> viewPageComponentIdList) {
        // 最终被写入数据库的视图菜单分类 id list
        List<String> viewPageComponentAuthorityList = new ArrayList<>(0);
        ViewPageComponentEntity viewPageComponentEntity;
        for(Long viewPageComponentId : viewPageComponentIdList) {
            viewPageComponentEntity = viewPageComponentMapper.getOne(viewPageComponentId);
            if(viewPageComponentEntity != null) {
                if(roleAuthorityMapper.getOneByRoleIdAndAuthority(roleId, viewPageComponentEntity.getAuthority()) == null) {
                    viewPageComponentAuthorityList.add(viewPageComponentEntity.getAuthority());
                }
            }
        }

        // 过滤后的视图菜单分类 id list 全部写入数据库
        if(!viewPageComponentAuthorityList.isEmpty()) {
            roleAuthorityMapper.insertAllByRoleIdAndAuthorityList(roleId, viewPageComponentAuthorityList);
        }
        return viewPageComponentAuthorityList.size();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int revokeAllByRoleIdAndViewPageComponentIdList(Long roleId, List<Long> viewPageComponentIdList) {
        List<String> viewPageComponentAuthorityList = new ArrayList<>(0);
        ViewPageComponentEntity viewPageComponentEntity;
        for(Long viewPageComponentId : viewPageComponentIdList) {
            viewPageComponentEntity = viewPageComponentMapper.getOne(viewPageComponentId);
            if(viewPageComponentEntity != null) {
                viewPageComponentAuthorityList.add(viewPageComponentEntity.getAuthority());
            }
        }
        roleAuthorityMapper.deleteAllByRoleIdAndAuthorityList(roleId, viewPageComponentAuthorityList);
        return viewPageComponentAuthorityList.size();
    }

}
