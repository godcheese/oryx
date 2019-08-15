package com.gioov.oryx.user.service;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.user.entity.ViewPageComponentEntity;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface ViewPageComponentService {

    /**
     * 指定视图页面 id，分页获取所有视图页面组件
     * @param viewPageId 视图页面 id
     * @param page   页
     * @param rows   每页显示数量
     * @return Pagination<ViewPageComponentEntity>
     */
    Pagination<ViewPageComponentEntity> pageAllByViewPageId(Long viewPageId, Integer page, Integer rows);

    /**
     * 新增视图页面组件
     * @param viewPageComponentEntity ViewPageComponentEntity
     * @return ViewPageComponentEntity
     * @throws BaseResponseException BaseResponseException
     */
    ViewPageComponentEntity addOne(ViewPageComponentEntity viewPageComponentEntity) throws BaseResponseException;

    /**
     * 保存视图页面组件
     * @param viewPageComponentEntity ViewPageComponentEntity
     * @return ViewPageComponentEntity
     * @throws BaseResponseException BaseResponseException
     */
    ViewPageComponentEntity saveOne(ViewPageComponentEntity viewPageComponentEntity) throws BaseResponseException;

    /**
     * 指定视图页面组件 id，批量删除视图页面组件
     * @param idList 视图页面组件 id list
     * @return int
     */
    int deleteAll(List<Long> idList);

    /**
     * 指定视图页面组件 id，获取视图页面组件
     * @param id 视图页面组件 id
     * @return ViewPageComponentEntity
     */
    ViewPageComponentEntity getOne(Long id);

    /**
     * 指定角色 id、视图页面组件 id list，批量授权
     * @param roleId 角色 id
     * @param viewPageComponentIdList 视图页面组件 id list
     * @return int
     */
    int grantAllByRoleIdAndViewPageComponentIdList(Long roleId, List<Long> viewPageComponentIdList);

    /**
     * 指定角色 id、视图页面组件 id list，批量撤销授权
     * @param roleId 角色 id
     * @param viewPageComponentIdList 视图页面组件 id list
     * @return int
     */
    int revokeAllByRoleIdAndViewPageComponentIdList(Long roleId, List<Long> viewPageComponentIdList);

}
