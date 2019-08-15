package com.gioov.oryx.user.service.impl;

import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.vue.antd.AntdVueMenu;
import com.gioov.oryx.user.entity.*;
import com.gioov.oryx.user.mapper.RoleViewMenuMapper;
import com.gioov.oryx.user.mapper.UserRoleMapper;
import com.gioov.oryx.user.mapper.ViewMenuCategoryMapper;
import com.gioov.oryx.user.mapper.ViewMenuMapper;
import com.gioov.oryx.user.service.RoleService;
import com.gioov.oryx.user.service.ViewMenuService;
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
public class ViewMenuServiceImpl implements ViewMenuService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private ViewMenuMapper viewMenuMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ViewMenuCategoryMapper viewMenuCategoryMapper;

    @Autowired
    private RoleViewMenuMapper roleViewMenuMapper;

    /**
     * 指定用户 id 、视图菜单分类 id 获取视图菜单
     *
     * @param userId         用户 id
     * @param viewMenuCategoryId 视图菜单分类 id
     * @return
     */
    @Override
    public List<ViewMenuEntity> listAllByUserIdAndMenuCategoryId(Long userId, Long viewMenuCategoryId) {
        List<ViewMenuEntity> viewMenuCategoryEntityList = null;
        List<UserRoleEntity> userRoleEntityList;
        if ((userRoleEntityList = userRoleMapper.listAllByUserId(userId)) != null) {
            List<RoleEntity> roleEntityList;
            if ((roleEntityList = roleService.listAllByUserRoleList(userRoleEntityList)) != null) {
                viewMenuCategoryEntityList = new ArrayList<>();
                for (RoleEntity roleEntity : roleEntityList) {
                    viewMenuCategoryEntityList.addAll(viewMenuMapper.listAllByViewMenuCategoryIdAndRoleId(viewMenuCategoryId, roleEntity.getId()));
                }
            }
        }
        return viewMenuCategoryEntityList;
    }

    @Override
    public Pagination<ViewMenuEntity> pageAllByViewMenuCategoryIdAndRoleId(Long viewMenuCategoryId, Long roleId, Integer page, Integer rows) {
        Pagination<ViewMenuEntity> pagination = new Pagination<>();
        Page<ViewMenuEntity> viewMenuEntityPage = viewMenuMapper.pageAllByViewMenuCategoryIdAndRoleId(viewMenuCategoryId, roleId);
        pagination.setRows(viewMenuEntityPage.getResult());
        pagination.setTotal(viewMenuEntityPage.getTotal());
        return pagination;
    }

    @Override
    public List<ViewMenuEntity> searchAllByName(String name) {
        return viewMenuMapper.searchAllByName(name);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ViewMenuEntity addOne(ViewMenuEntity viewMenuEntity) {
        Date date = new Date();
        viewMenuEntity.setGmtModified(date);
        viewMenuEntity.setGmtCreated(date);
        viewMenuMapper.insertOne(viewMenuEntity);
        return viewMenuEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ViewMenuEntity saveOne(ViewMenuEntity viewMenuEntity) {
        ViewMenuEntity viewMenuEntity1 = viewMenuMapper.getOne(viewMenuEntity.getId());
        viewMenuEntity1.setName(viewMenuEntity.getName());
        viewMenuEntity1.setIcon(viewMenuEntity.getIcon());
        viewMenuEntity1.setUrl(viewMenuEntity.getUrl());
        viewMenuEntity1.setViewMenuCategoryId(viewMenuEntity.getViewMenuCategoryId());
        viewMenuEntity1.setSort(viewMenuEntity.getSort());
        viewMenuEntity1.setRemark(viewMenuEntity.getRemark());
        viewMenuEntity1.setGmtModified(new Date());
        viewMenuMapper.updateOne(viewMenuEntity1);
        return viewMenuEntity1;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteAll(List<Long> idList) {
        roleViewMenuMapper.deleteAllByViewMenuIdList(idList);
        return viewMenuMapper.deleteAll(idList);
    }

    @Override
    public ViewMenuEntity getOne(Long id) {
        return viewMenuMapper.getOne(id);
    }

    /**
     * Vue 菜单目录遍历出子菜单或子菜单目录（VueMenu 即为 Vue 菜单目录或菜单）
     * @param parentId 父级视图菜单分类 id
     * @param vueMenuList VuewMenu list
     * @return List<AntdVueMenu>
     */
    @Override
    public List<AntdVueMenu> forEachVueMenuByVueMenuParentId(long parentId, List<AntdVueMenu> vueMenuList) {
        List<AntdVueMenu> children = new ArrayList<>();
        for(AntdVueMenu vueMenu : vueMenuList) {
            if(vueMenu.getParentId() != null && vueMenu.getParentId().equals(parentId)) {
                children.add(vueMenu);
            }
        }
        for(AntdVueMenu child : children) {
            if(child.getIsCategory()) {
                child.setChildren(forEachVueMenuByVueMenuParentId(child.getId(), vueMenuList));
            }
        }
        if(children.size() == 0) {
            return null;
        }
        return children;
    }

    /**
     * 菜单目录遍历出子菜单或子菜单目录
     * @param viewMenuCategoryId 视图菜单分类 id
     * @param roleIdList 角色 id list
     * @param vueMenuList VueMenu list
     */
    @Override
    public void forEachViewMenuAndViewMenuCategoryByViewMenuCategoryId(long viewMenuCategoryId, List<Long> roleIdList, List<AntdVueMenu> vueMenuList) {

        List<ViewMenuEntity> viewMenuEntityList = viewMenuMapper.listAllByViewMenuCategoryIdAndRoleIdList(viewMenuCategoryId, roleIdList);

        for(ViewMenuEntity viewMenuEntity : viewMenuEntityList) {
            AntdVueMenu vueMenu = new AntdVueMenu();
            vueMenu.setId(viewMenuEntity.getId());
            vueMenu.setName(viewMenuEntity.getName());
            vueMenu.setUrl(viewMenuEntity.getUrl());
            vueMenu.setIcon(viewMenuEntity.getIcon());
            vueMenu.setParentId(viewMenuEntity.getViewMenuCategoryId());
            vueMenu.setIsCategory(false);
            vueMenuList.add(vueMenu);
        }

        List<ViewMenuCategoryEntity> viewMenuCategoryEntityList = viewMenuCategoryMapper.listAllByParentIdAndRoleIdList(viewMenuCategoryId, roleIdList);

        for(ViewMenuCategoryEntity viewMenuCategoryEntity : viewMenuCategoryEntityList) {
            AntdVueMenu vueMenu = new AntdVueMenu();
            vueMenu.setId(viewMenuCategoryEntity.getId());
            vueMenu.setName(viewMenuCategoryEntity.getName());
            vueMenu.setIcon(viewMenuCategoryEntity.getIcon());
            vueMenu.setParentId(viewMenuCategoryEntity.getParentId());
            vueMenu.setIsCategory(true);
            vueMenuList.add(vueMenu);
            forEachViewMenuAndViewMenuCategoryByViewMenuCategoryId(viewMenuCategoryEntity.getId(), roleIdList, vueMenuList);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int grantAllByRoleIdAndViewMenuIdList(Long roleId, List<Long> viewMenuIdList) {
        // 最终被写入数据库的视图菜单分类 id list
        List<Long> viewMenuIdList2 = new ArrayList<>(0);
        RoleViewMenuEntity roleViewMenuEntity;
        for (Long viewMenuId : viewMenuIdList) {
            roleViewMenuEntity = roleViewMenuMapper.getOneByRoleIdAndViewMenuId(roleId, viewMenuId);
            if(roleViewMenuEntity == null) {
                viewMenuIdList2.add(viewMenuId);
            }
        }
        // 过滤后的视图菜单分类 id list 全部写入数据库
        if(!viewMenuIdList2.isEmpty()) {
            roleViewMenuMapper.insertAllByRoleIdAndViewMenuIdList(roleId, viewMenuIdList2);
        }
        return viewMenuIdList2.size();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int revokeAllByRoleIdAndViewMenuIdList(Long roleId, List<Long> viewMenuIdList) {
        roleViewMenuMapper.deleteAllByRoleIdAndViewMenuIdList(roleId, viewMenuIdList);
        return viewMenuIdList.size();
    }

}
