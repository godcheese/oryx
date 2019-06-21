package com.gioov.nimrod.user.service;

import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.user.entity.ViewPageComponentEntity;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface ViewPageComponentService {

    /**
     * 指定视图页面 id ，分页获取所有视图页面组件
     *
     * @param pageId 视图页面 id
     * @param page   页
     * @param rows   每页显示数量
     * @return Pagination<ViewPageComponentEntity>
     */
    Pagination<ViewPageComponentEntity> pageAllByPageId(Long pageId, Integer page, Integer rows);

    /**
     * 新增视图页面组件
     *
     * @param viewPageComponentEntity ViewPageComponentEntity
     * @return ViewPageComponentEntity
     */
    ViewPageComponentEntity insertOne(ViewPageComponentEntity viewPageComponentEntity) throws BaseResponseException;

    /**
     * 保存视图页面组件
     *
     * @param viewPageComponentEntity ViewPageComponentEntity
     * @return ViewPageComponentEntity
     */
    ViewPageComponentEntity updateOne(ViewPageComponentEntity viewPageComponentEntity) throws BaseResponseException;

    /**
     * 指定视图页面组件 id ，批量删除视图页面组件
     *
     * @param idList 视图页面组件 id list
     * @return 删除的视图页面组件个数
     */
    int deleteAll(List<Long> idList);

    /**
     * 指定视图组件 id ，获取视图组件信息
     *
     * @param id 视图页面组件 id
     * @return ViewPageComponentEntity
     */
    ViewPageComponentEntity getOne(Long id);

    /**
     * 指定角色 id、API 权限（authority），批量授权
     *
     * @param roleId        角色 id
     * @param viewPageComponentIdList 权限（authority） list
     * @return List<String>
     */
    List<String> grantAllByRoleIdAndViewPageComponentIdList(Long roleId, List<Long> viewPageComponentIdList);

    /**
     * 指定角色 id、API 权限（authority），批量撤销授权
     *
     * @param roleId        角色 id
     * @param viewPageComponentIdList 权限（authority） list
     * @return List<String>
     */
    List<String> revokeAllByRoleIdAndViewPageComponentIdList(Long roleId, List<Long> viewPageComponentIdList);

}
