package com.gioov.oryx.user.service;

import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.vue.antd.AntdVueMenu;
import com.gioov.oryx.user.entity.ViewMenuEntity;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface ViewMenuService {

    /**
     * 指定用户 id、视图菜单分类 id ，获取所有视图菜单
     * @param userId 用户 id
     * @param viewMenuCategoryId 视图菜单分类 id
     * @return List<ViewMenuEntity>
     */
    List<ViewMenuEntity> listAllByUserIdAndMenuCategoryId(Long userId, Long viewMenuCategoryId);

    /**
     * 指定视图菜单分类 id、角色 id，分页获取所有视图菜单
     * @param viewMenuCategoryId 视图菜单分类 id
     * @param roleId 角色 id
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<ViewMenuEntity>
     */
    Pagination<ViewMenuEntity> pageAllByViewMenuCategoryIdAndRoleId(Long viewMenuCategoryId, Long roleId, Integer page, Integer rows);

    /**
     * 指定视图菜单名称，模糊搜索获取所有视图菜单
     * @param name 视图菜单名称
     * @return List<ViewMenuEntity>
     */
    List<ViewMenuEntity> searchAllByName(String name);

    /**
     * 新增视图菜单
     * @param viewMenuEntity ViewMenuEntity
     * @return ViewMenuEntity
     */
    ViewMenuEntity addOne(ViewMenuEntity viewMenuEntity);

    /**
     * 保存视图菜单
     * @param viewMenuEntity ViewMenuEntity
     * @return ViewMenuEntity
     */
    ViewMenuEntity saveOne(ViewMenuEntity viewMenuEntity);

    /**
     * 指定视图菜单 id list，批量删除视图菜单
     * @param idList 视图菜单 id list
     * @return int
     */
    int deleteAll(List<Long> idList);

    /**
     * 指定视图菜单 id，获取视图菜单
      * @param id 视图菜单 id
     * @return ViewMenuEntity
     */
    ViewMenuEntity getOne(Long id);

    /**
     * 指定父级 VueMenu id、VueMenu list，遍历 VueMenu
     * @param parentId 父级 VueMenu id
     * @param vueMenuList VueMenu list
     * @return List<AntdVueMenu>
     */
    List<AntdVueMenu> forEachVueMenuByVueMenuParentId(long parentId, List<AntdVueMenu> vueMenuList);

    /**
     * 指定视图菜单分类 id、角色 id list、VueMenu list，遍历 VueMenu
     * @param viewMenuCategoryId 视图菜单分类 id
     * @param roleIdList 角色 id list
     * @param vueMenuList VueMenu list
     */
    void forEachViewMenuAndViewMenuCategoryByViewMenuCategoryId(long viewMenuCategoryId, List<Long> roleIdList, List<AntdVueMenu> vueMenuList);

    /**
     * 指定角色 id、视图菜单 id list，批量授权
     * @param roleId 角色 id
     * @param viewMenuIdList 视图菜单 id list
     * @return int
     */
    int grantAllByRoleIdAndViewMenuIdList(Long roleId, List<Long> viewMenuIdList);

    /**
     * 指定角色 id、视图菜单 id list，批量撤销授权
     * @param roleId 角色 id
     * @param viewMenuIdList 视图菜单 id list
     * @return int
     */
    int revokeAllByRoleIdAndViewMenuIdList(Long roleId, List<Long> viewMenuIdList);


    /**
     * 指定用户 id，获取用户的树形视图菜单
     * @param userId
     * @return
     */
    List<AntdVueMenu> listAllAsAntdVueMenuByUserId(Long userId);

}
