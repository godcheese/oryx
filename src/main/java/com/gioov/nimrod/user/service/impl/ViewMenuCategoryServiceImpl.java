package com.gioov.nimrod.user.service.impl;

import com.gioov.common.mybatis.Pageable;
import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.FailureMessage;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.common.vue.antd.AntdTreeNode;
import com.gioov.nimrod.common.vue.antd.DepartmentEntityAsAntdTable;
import com.gioov.nimrod.common.vue.antd.ViewMenuCategoryEntityAsAntdTable;
import com.gioov.nimrod.system.service.DictionaryService;
import com.gioov.nimrod.user.entity.*;
import com.gioov.nimrod.user.mapper.RoleViewMenuCategoryMapper;
import com.gioov.nimrod.user.mapper.UserRoleMapper;
import com.gioov.nimrod.user.mapper.ViewMenuCategoryMapper;
import com.gioov.nimrod.user.mapper.ViewMenuMapper;
import com.gioov.nimrod.user.service.RoleService;
import com.gioov.nimrod.user.service.ViewMenuCategoryService;
import com.gioov.nimrod.user.service.ViewMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Service
public class ViewMenuCategoryServiceImpl implements ViewMenuCategoryService {

    @Autowired
    private ViewMenuCategoryMapper viewMenuCategoryMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ViewMenuMapper viewMenuMapper;

    @Autowired
    private ViewMenuCategoryService viewMenuCategoryService;

    @Autowired
    private ViewMenuService viewMenuService;

    @Autowired
    private RoleViewMenuCategoryMapper roleViewMenuCategoryMapper;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 指定 用户id，获取所有视图菜单父级分类
     *
     * @param userId
     * @return
     */
    @Override
    public List<ViewMenuCategoryEntity> listAllParentByUserId(Long userId) {
        List<ViewMenuCategoryEntity> viewMenuCategoryEntityList = null;
        List<UserRoleEntity> userRoleEntityList;
        if ((userRoleEntityList = userRoleMapper.listAllByUserId(userId)) != null) {
            List<RoleEntity> roleEntityList;
            if ((roleEntityList = roleService.listAllByUserRoleList(userRoleEntityList)) != null) {
                viewMenuCategoryEntityList = new ArrayList<>();
                for (RoleEntity roleEntity : roleEntityList) {
                    viewMenuCategoryEntityList.addAll(viewMenuCategoryMapper.listAllByParentIdIsNullAndRoleId(roleEntity.getId()));
                }
            }
        }
        return viewMenuCategoryEntityList;
    }

    /**
     * 指定用户id 、视图菜单父级分类，获取所有视图菜单子级分类
     *
     * @param userId
     * @param parentId
     * @return
     */
    @Override
    public List<ViewMenuCategoryEntity> listAllChildByParentIdAndUserId(Long parentId, Long userId) {
        List<ViewMenuCategoryEntity> viewMenuCategoryEntityList = null;
        List<UserRoleEntity> userRoleEntityList;
        if ((userRoleEntityList = userRoleMapper.listAllByUserId(userId)) != null) {
            List<RoleEntity> roleEntityList;
            if ((roleEntityList = roleService.listAllByUserRoleList(userRoleEntityList)) != null) {
                viewMenuCategoryEntityList = new ArrayList<>();
                for (RoleEntity roleEntity : roleEntityList) {
                    // 根据父级视图菜单分类和角色 id ，获取每个角色所拥有的视图菜单子级分类
                    viewMenuCategoryEntityList.addAll(viewMenuCategoryMapper.listAllByParentIdAndRoleId(parentId, roleEntity.getId()));
                }
            }
        }
        return viewMenuCategoryEntityList;
    }

    @Override
    public List<ViewMenuCategoryEntity> listAllByParentIdAndRoleId(Long parentId, Long roleId) {
        return viewMenuCategoryMapper.listAllByParentIdAndRoleId(parentId, roleId);
    }

    @Override
    public List<ViewMenuCategoryEntity> listAllParentByRoleId(Long roleId) {
        return viewMenuCategoryMapper.listAllByParentIdIsNullAndRoleId(roleId);
    }

    @Override
    public List<Map<String, Object>> listAllChildViewMenuCategoryAndViewMenuByParentIdAndUserId(Long parentId, Long userId) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<ViewMenuCategoryEntity> viewMenuCategoryEntityList = null;
        viewMenuCategoryEntityList = viewMenuCategoryService.listAllChildByParentIdAndUserId(parentId, userId);
        if (viewMenuCategoryEntityList != null) {
            for (ViewMenuCategoryEntity viewMenuCategoryEntity : viewMenuCategoryEntityList) {
                Map<String, Object> map = new HashMap<>(3);
                map.put("id", viewMenuCategoryEntity.getId());
                map.put("name", viewMenuCategoryEntity.getName());
                map.put("icon", viewMenuCategoryEntity.getIcon());
                mapList.add(map);
            }
            List<ViewMenuEntity> viewMenuEntityList = null;
            viewMenuEntityList = viewMenuService.listAllByUserIdAndMenuCategoryId(userId, parentId);
            if (viewMenuEntityList != null) {
                for (ViewMenuEntity viewMenuEntity : viewMenuEntityList) {
                    Map<String, Object> map = new HashMap<>(4);
                    map.put("id", viewMenuEntity.getId());
                    map.put("name", viewMenuEntity.getName());
                    map.put("icon", viewMenuEntity.getIcon());
                    map.put("url", viewMenuEntity.getUrl());
                    mapList.add(map);
                }
            }
        }
        return mapList;
    }

    @Override
    public List<ViewMenuCategoryEntity> listAll() {
        return viewMenuCategoryMapper.listAll();
    }

    @Override
    public List<ViewMenuCategoryEntity> searchAllByName(String name) {
        return viewMenuCategoryMapper.searchAllByName(name);
    }

    @Override
    public Pagination<ViewMenuCategoryEntity> pageAllParent(Long roleId, Integer page, Integer rows) {
        Pagination<ViewMenuCategoryEntity> pagination = new Pagination<>();
        List<ViewMenuCategoryEntity> viewMenuCategoryEntityList = viewMenuCategoryMapper.pageAllByParentIdIsNullAndRoleId(roleId, new Pageable(page, rows));
        if (viewMenuCategoryEntityList != null) {
            pagination.setRows(viewMenuCategoryEntityList);
        }
        pagination.setTotal(viewMenuCategoryMapper.countAllByParentIdIsNullAndRoleId(roleId));
        return pagination;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ViewMenuCategoryEntity insertOne(ViewMenuCategoryEntity viewMenuCategoryEntity) {
        Date date = new Date();
        viewMenuCategoryEntity.setGmtModified(date);
        viewMenuCategoryEntity.setGmtCreated(date);
        viewMenuCategoryMapper.insertOne(viewMenuCategoryEntity);
        return viewMenuCategoryEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ViewMenuCategoryEntity updateOne(ViewMenuCategoryEntity viewMenuCategoryEntity) {
        ViewMenuCategoryEntity viewMenuCategoryEntity1 = viewMenuCategoryMapper.getOne(viewMenuCategoryEntity.getId());
        Date date = new Date();
        viewMenuCategoryEntity1.setName(viewMenuCategoryEntity.getName());
        viewMenuCategoryEntity1.setIcon(viewMenuCategoryEntity.getIcon());
        viewMenuCategoryEntity1.setSort(viewMenuCategoryEntity.getSort());
        viewMenuCategoryEntity1.setRemark(viewMenuCategoryEntity.getRemark());
        viewMenuCategoryEntity1.setGmtModified(date);
        viewMenuCategoryMapper.updateOne(viewMenuCategoryEntity1);
        return viewMenuCategoryEntity1;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteAll(List<Long> idList) throws BaseResponseException {
        int result = 0;
        for (Long id : idList) {
            // 有子视图菜单分类报错
            ViewMenuCategoryEntity viewMenuCategoryEntity = viewMenuCategoryMapper.getOneByParentId(id);
            if (viewMenuCategoryEntity != null) {
                throw new BaseResponseException(FailureMessage.DELETE_VIEW_MENU_CATEGORY_FAIL1);
            }
            // 有子视图菜单报错
            ViewMenuEntity viewMenuEntity = viewMenuMapper.getOneByMenuCategoryId(id);
            if (viewMenuEntity != null) {
                throw new BaseResponseException(FailureMessage.DELETE_VIEW_MENU_CATEGORY_FAIL2);
            }
            roleViewMenuCategoryMapper.deleteAllByViewMenuCategoryId(id);
            viewMenuCategoryMapper.deleteOne(id);
            result++;
        }
        return result;
    }

    @Override
    public ViewMenuCategoryEntity getOne(Long id) {
        return viewMenuCategoryMapper.getOne(id);
    }

    @Override
    public List<ViewMenuCategoryEntityAsAntdTable> listAllViewMenuCategoryEntityAsAntdTable() {
        List<ViewMenuCategoryEntityAsAntdTable> viewMenuCategoryEntityAsAntdTableList = new ArrayList<>(0);
        List<ViewMenuCategoryEntity> viewMenuCategoryEntityList = listAll();
        for(ViewMenuCategoryEntity viewMenuCategoryEntity : viewMenuCategoryEntityList) {
            ViewMenuCategoryEntityAsAntdTable viewMenuCategoryEntityAsAntdTable = new ViewMenuCategoryEntityAsAntdTable();
            viewMenuCategoryEntityAsAntdTable.setId(viewMenuCategoryEntity.getId());
            viewMenuCategoryEntityAsAntdTable.setName(viewMenuCategoryEntity.getName());
            viewMenuCategoryEntityAsAntdTable.setIcon(viewMenuCategoryEntity.getIcon());
            viewMenuCategoryEntityAsAntdTable.setParentId(viewMenuCategoryEntity.getParentId());
            viewMenuCategoryEntityAsAntdTable.setRemark(viewMenuCategoryEntity.getRemark());
            viewMenuCategoryEntityAsAntdTable.setGmtModified(viewMenuCategoryEntity.getGmtModified());
            viewMenuCategoryEntityAsAntdTable.setGmtCreated(viewMenuCategoryEntity.getGmtCreated());
            viewMenuCategoryEntityAsAntdTableList.add(viewMenuCategoryEntityAsAntdTable);
        }
        return viewMenuCategoryEntityAsAntdTableList;
    }
    @Override
    public List<ViewMenuCategoryEntityAsAntdTable> getViewMenuCategoryChildrenViewMenuCategoryEntityAsAntdTable(long parentId, List<ViewMenuCategoryEntityAsAntdTable> viewMenuCategoryEntityAsAntdTableList) {
        List<ViewMenuCategoryEntityAsAntdTable> children = new ArrayList<>(0);
        for(ViewMenuCategoryEntityAsAntdTable viewMenuCategoryEntityAsAntdTable : viewMenuCategoryEntityAsAntdTableList) {
            if(viewMenuCategoryEntityAsAntdTable.getParentId() != null && viewMenuCategoryEntityAsAntdTable.getParentId().equals(parentId)) {
                children.add(viewMenuCategoryEntityAsAntdTable);
            }
        }
        for(ViewMenuCategoryEntityAsAntdTable child : children) {
            List<ViewMenuCategoryEntityAsAntdTable> childChildren = getViewMenuCategoryChildrenViewMenuCategoryEntityAsAntdTable(child.getId(), viewMenuCategoryEntityAsAntdTableList);
            child.setChildren(childChildren);
        }
        if(children.size() == 0) {
            return null;
        }
        return children;
    }

    /**
     * 判断当前角色是否被授权此视图菜单分类
     * @param roleId
     * @return
     */
    @Override
    public List<ViewMenuCategoryEntityAsAntdTable> listAllViewMenuCategoryEntityAsAntdTableByRoleId(long roleId) {
        Integer is = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
        Integer not = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));

        List<ViewMenuCategoryEntityAsAntdTable> viewMenuCategoryEntityAsAntdTableList = new ArrayList<>(0);
        List<ViewMenuCategoryEntity> viewMenuCategoryEntityList = listAll();
        for(ViewMenuCategoryEntity viewMenuCategoryEntity : viewMenuCategoryEntityList) {
            ViewMenuCategoryEntityAsAntdTable viewMenuCategoryEntityAsAntdTable = new ViewMenuCategoryEntityAsAntdTable();
            viewMenuCategoryEntityAsAntdTable.setId(viewMenuCategoryEntity.getId());
            viewMenuCategoryEntityAsAntdTable.setName(viewMenuCategoryEntity.getName());
            viewMenuCategoryEntityAsAntdTable.setIcon(viewMenuCategoryEntity.getIcon());
            viewMenuCategoryEntityAsAntdTable.setParentId(viewMenuCategoryEntity.getParentId());
            viewMenuCategoryEntityAsAntdTable.setSort(viewMenuCategoryEntity.getSort());
            viewMenuCategoryEntityAsAntdTable.setRemark(viewMenuCategoryEntity.getRemark());
            viewMenuCategoryEntityAsAntdTable.setGmtModified(viewMenuCategoryEntity.getGmtModified());
            viewMenuCategoryEntityAsAntdTable.setGmtCreated(viewMenuCategoryEntity.getGmtCreated());

            // 判断当前角色是否被授权此视图菜单分类
            viewMenuCategoryEntityAsAntdTable.setIsGranted(roleViewMenuCategoryMapper.getOneByRoleIdAndViewMenuCategoryId(roleId, viewMenuCategoryEntityAsAntdTable.getId()) != null ? is: not);

            viewMenuCategoryEntityAsAntdTableList.add(viewMenuCategoryEntityAsAntdTable);
        }
        return viewMenuCategoryEntityAsAntdTableList;
    }

    @Override
    public List<AntdTreeNode> listAllViewMenuCategoryAntdTreeNode() {
        List<AntdTreeNode> antdTreeNodeList = new ArrayList<>(0);
        List<ViewMenuCategoryEntity> viewMenuCategoryEntityList = listAll();
        for(ViewMenuCategoryEntity viewMenuCategoryEntity : viewMenuCategoryEntityList) {
            AntdTreeNode antdTreeNode = new AntdTreeNode();
            antdTreeNode.setId(viewMenuCategoryEntity.getId());
            antdTreeNode.setParentId(viewMenuCategoryEntity.getParentId());
            antdTreeNode.setValue(String.valueOf(viewMenuCategoryEntity.getId()));
            antdTreeNode.setLabel(viewMenuCategoryEntity.getName());
            antdTreeNode.setSelectable(true);
            antdTreeNodeList.add(antdTreeNode);
        }
        return antdTreeNodeList;
    }
    @Override
    public List<AntdTreeNode> getViewMenuCategoryChildrenAntdTreeNode(long parentId, List<AntdTreeNode> viewMenuCategoryAntdTreeNodeList) {
        List<AntdTreeNode> children = new ArrayList<>(0);
        for(AntdTreeNode antdTreeNode : viewMenuCategoryAntdTreeNodeList) {
            if(antdTreeNode.getParentId() != null && antdTreeNode.getParentId().equals(parentId)) {
                children.add(antdTreeNode);
            }
        }
        for(AntdTreeNode child : children) {
            List<AntdTreeNode> childChildren = getViewMenuCategoryChildrenAntdTreeNode(child.getId(), viewMenuCategoryAntdTreeNodeList);
//            if(childChildren == null) {
//                childChildren = new ArrayList<>(0);
//            }
            child.setChildren(childChildren);
        }
        if(children.size() == 0) {
            return null;
        }
        return children;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<Long> grantAllByRoleIdAndViewMenuCategoryIdList(Long roleId, List<Long> viewMenuCategoryIdList) {
        // 最终被写入数据库的视图菜单分类 id list
        List<Long> viewMenuCategoryIdList2 = new ArrayList<>(0);
        RoleViewMenuCategoryEntity roleViewMenuCategoryEntity;
        for (Long viewMenuCategoryId : viewMenuCategoryIdList) {
            roleViewMenuCategoryEntity = roleViewMenuCategoryMapper.getOneByRoleIdAndViewMenuCategoryId(roleId, viewMenuCategoryId);
            if(roleViewMenuCategoryEntity == null) {
                viewMenuCategoryIdList2.add(viewMenuCategoryId);
            }
        }
        // 过滤后的视图菜单分类 id list 全部写入数据库
        if(!viewMenuCategoryIdList2.isEmpty()) {
            roleViewMenuCategoryMapper.insertAllByRoleIdAndViewMenuCategoryIdList(roleId, viewMenuCategoryIdList2);
        }
        return viewMenuCategoryIdList2;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<Long> revokeAllByRoleIdAndViewMenuCategoryIdList(Long roleId, List<Long> viewMenuCategoryIdList) {
            roleViewMenuCategoryMapper.deleteAllByRoleIdAndViewMenuCategoryIdList(roleId, viewMenuCategoryIdList);
        return viewMenuCategoryIdList;
    }

}
