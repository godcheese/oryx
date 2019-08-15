package com.gioov.oryx.user.service;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.user.entity.ViewPageEntity;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface ViewPageService {

    /**
     * 指定视图页面分类 id，分页获取所有视图页面
     * @param viewPageCategoryId 视图页面分类 id
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<ViewPageEntity>
     */
    Pagination<ViewPageEntity> pageAllByViewPageCategoryId(Long viewPageCategoryId, Integer page, Integer rows);

    /**
     * 新增视图页面
     * @param viewPageEntity ViewPageEntity
     * @return ViewPageEntity
     * @throws BaseResponseException BaseResponseException
     */
    ViewPageEntity addOne(ViewPageEntity viewPageEntity) throws BaseResponseException;

    /**
     * 保存视图页面
     * @param viewPageEntity ViewPageEntity
     * @return ViewPageEntity
     * @throws BaseResponseException BaseResponseException
     */
    ViewPageEntity saveOne(ViewPageEntity viewPageEntity) throws BaseResponseException;

    /**
     * 指定视图页面 id，批量删除视图页面
     * @param idList 视图页面 id list
     * @return int
     */
    int deleteAll(List<Long> idList);

    /**
     * 指定视图页面 id，获取视图页面
     * @param id 视图页面 id
     * @return ViewPageEntity
     */
    ViewPageEntity getOne(Long id);

    /**
     * 指定角色 id、视图页面 id list，批量授权
     * @param roleId 角色 id
     * @param viewPageIdList 视图页面 id list
     * @return List<String>
     */
    int grantAllByRoleIdAndViewPageIdList(Long roleId, List<Long> viewPageIdList);

    /**
     * 指定角色 id、视图页面 id list，批量撤销授权
     * @param roleId 角色 id
     * @param viewPageIdList 视图页面 id list
     * @return List<String>
     */
    int revokeAllByRoleIdAndViewPageIdList(Long roleId, List<Long> viewPageIdList);

    /**
     * 指定视图页面分类 id，获取所有视图页面
     * @param viewPageCategoryId 视图页面分类 id
     * @return List<ViewPageEntity>
     */
    List<ViewPageEntity> listAllViewPageByViewPageCategoryId(Long viewPageCategoryId);

}
