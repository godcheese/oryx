package com.gioov.oryx.user.service.impl;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.FailureMessage;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.ViewPageCategoryEntityAsAntdTable;
import com.gioov.oryx.system.service.DictionaryService;
import com.gioov.oryx.user.entity.ViewPageCategoryEntity;
import com.gioov.oryx.user.entity.ViewPageEntity;
import com.gioov.oryx.user.mapper.ViewPageCategoryMapper;
import com.gioov.oryx.user.mapper.ViewPageMapper;
import com.gioov.oryx.user.service.ViewPageCategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Service
public class ViewPageCategoryServiceImpl implements ViewPageCategoryService {

    @Autowired
    private ViewPageCategoryMapper viewPageCategoryMapper;

    @Autowired
    private ViewPageMapper viewPageMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @Override
    public Pagination<ViewPageCategoryEntity> pageAllParent(Integer page, Integer rows) {
        Pagination<ViewPageCategoryEntity> pagination = new Pagination<>();
        //        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<ViewPageCategoryEntity> viewPageCategoryEntityPage = viewPageCategoryMapper.pageAllByParentIdIsNull();
            pagination.setRows(viewPageCategoryEntityPage.getResult());
        pagination.setTotal(viewPageCategoryEntityPage.getTotal());
        return pagination;
    }

    @Override
    public List<ViewPageCategoryEntity> listAllByParentId(Long parentId) {
        return viewPageCategoryMapper.listAllByParentId(parentId);
    }

    @Override
    public List<ViewPageCategoryEntity> listAll() {
        return viewPageCategoryMapper.listAll();
    }

    @Override
    public ViewPageCategoryEntity addOne(ViewPageCategoryEntity viewPageCategoryEntity) {
        Date date = new Date();
        viewPageCategoryEntity.setGmtModified(date);
        viewPageCategoryEntity.setGmtCreated(date);
        viewPageCategoryMapper.insertOne(viewPageCategoryEntity);
        return viewPageCategoryEntity;
    }

    @Override
    public ViewPageCategoryEntity saveOne(ViewPageCategoryEntity viewPageCategoryEntity) {
        ViewPageCategoryEntity viewPageCategoryEntity1 = viewPageCategoryMapper.getOne(viewPageCategoryEntity.getId());
        viewPageCategoryEntity1.setName(viewPageCategoryEntity.getName());
        viewPageCategoryEntity1.setParentId(viewPageCategoryEntity.getParentId());
        viewPageCategoryEntity1.setSort(viewPageCategoryEntity.getSort());
        viewPageCategoryEntity1.setRemark(viewPageCategoryEntity.getRemark());
        viewPageCategoryEntity1.setGmtModified(new Date());
        viewPageCategoryMapper.updateOne(viewPageCategoryEntity1);
        return viewPageCategoryEntity1;
    }

    @Override
    public int deleteAll(List<Long> idList) throws BaseResponseException {
        int result = 0;
        for (Long id : idList) {
            ViewPageCategoryEntity viewPageCategoryEntity = viewPageCategoryMapper.getOneByParentId(id);
            if (viewPageCategoryEntity != null) {
                throw new BaseResponseException(FailureMessage.DELETE_VIEW_PAGE_CATEGORY_FAIL1);
            }
            ViewPageEntity viewPageEntity = viewPageMapper.getOneByViewPageCategoryId(id);
            if (viewPageEntity != null) {
                throw new BaseResponseException(FailureMessage.DELETE_VIEW_PAGE_CATEGORY_FAIL2);
            }
            viewPageCategoryMapper.deleteOne(id);
            result++;
        }
        return result;
    }

    @Override
    public ViewPageCategoryEntity getOne(Long id) {
        return viewPageCategoryMapper.getOne(id);
    }

    @Override
    public List<ViewPageCategoryEntityAsAntdTable> listAllViewPageCategoryEntityAsAntdTable() {
        List<ViewPageCategoryEntityAsAntdTable> viewPageCategoryEntityAsAntdTableList = new ArrayList<>(0);
        List<ViewPageCategoryEntity> viewPageCategoryEntityList = listAll();
        for(ViewPageCategoryEntity viewPageCategoryEntity : viewPageCategoryEntityList) {
            ViewPageCategoryEntityAsAntdTable viewPageCategoryEntityAsAntdTable = new ViewPageCategoryEntityAsAntdTable();
            viewPageCategoryEntityAsAntdTable.setId(viewPageCategoryEntity.getId());
            viewPageCategoryEntityAsAntdTable.setParentId(viewPageCategoryEntity.getParentId());
            viewPageCategoryEntityAsAntdTable.setName(viewPageCategoryEntity.getName());
            viewPageCategoryEntityAsAntdTable.setRemark(viewPageCategoryEntity.getRemark());
            viewPageCategoryEntityAsAntdTable.setGmtModified(viewPageCategoryEntity.getGmtModified());
            viewPageCategoryEntityAsAntdTable.setGmtCreated(viewPageCategoryEntity.getGmtCreated());
            viewPageCategoryEntityAsAntdTableList.add(viewPageCategoryEntityAsAntdTable);
        }
        return viewPageCategoryEntityAsAntdTableList;
    }
    @Override
    public List<ViewPageCategoryEntityAsAntdTable> getViewPageCategoryChildrenViewPageCategoryEntityAsAntdTable(long parentId, List<ViewPageCategoryEntityAsAntdTable> viewPageCategoryEntityAsAntdTableList) {
        List<ViewPageCategoryEntityAsAntdTable> children = new ArrayList<>(0);
        for(ViewPageCategoryEntityAsAntdTable viewPageCategoryEntityAsAntdTable : viewPageCategoryEntityAsAntdTableList) {
            if(viewPageCategoryEntityAsAntdTable.getParentId() != null && viewPageCategoryEntityAsAntdTable.getParentId().equals(parentId)) {
                children.add(viewPageCategoryEntityAsAntdTable);
            }
        }
        for(ViewPageCategoryEntityAsAntdTable child : children) {
            List<ViewPageCategoryEntityAsAntdTable> childChildren = getViewPageCategoryChildrenViewPageCategoryEntityAsAntdTable(child.getId(), viewPageCategoryEntityAsAntdTableList);
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
    public List<ViewPageCategoryEntityAsAntdTable> listAllViewPageCategoryEntityAsAntdTableByRoleId(long roleId) {
        Integer is = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "IS")));
        Integer not = Integer.valueOf(String.valueOf(dictionaryService.get("IS_OR_NOT", "NOT")));

        List<ViewPageCategoryEntityAsAntdTable> viewPageCategoryEntityAsAntdTableList = new ArrayList<>(0);
        List<ViewPageCategoryEntity> viewPageCategoryEntityList = listAll();
        for(ViewPageCategoryEntity viewPageCategoryEntity : viewPageCategoryEntityList) {
            ViewPageCategoryEntityAsAntdTable viewPageCategoryEntityAsAntdTable = new ViewPageCategoryEntityAsAntdTable();
            viewPageCategoryEntityAsAntdTable.setId(viewPageCategoryEntity.getId());
            viewPageCategoryEntityAsAntdTable.setName(viewPageCategoryEntity.getName());
            viewPageCategoryEntityAsAntdTable.setParentId(viewPageCategoryEntity.getParentId());
            viewPageCategoryEntityAsAntdTable.setSort(viewPageCategoryEntity.getSort());
            viewPageCategoryEntityAsAntdTable.setRemark(viewPageCategoryEntity.getRemark());
            viewPageCategoryEntityAsAntdTable.setGmtModified(viewPageCategoryEntity.getGmtModified());
            viewPageCategoryEntityAsAntdTable.setGmtCreated(viewPageCategoryEntity.getGmtCreated());
            viewPageCategoryEntityAsAntdTableList.add(viewPageCategoryEntityAsAntdTable);
        }
        return viewPageCategoryEntityAsAntdTableList;
    }

    @Override
    public List<AntdTreeNode> listAllViewPageCategoryAntdTreeNode() {
        List<AntdTreeNode> antdTreeNodeList = new ArrayList<>(0);
        List<ViewPageCategoryEntity> viewPageCategoryEntityList = listAll();
        for(ViewPageCategoryEntity viewPageCategoryEntity : viewPageCategoryEntityList) {
            AntdTreeNode antdTreeNode = new AntdTreeNode();
            antdTreeNode.setId(viewPageCategoryEntity.getId());
            antdTreeNode.setParentId(viewPageCategoryEntity.getParentId());
            antdTreeNode.setValue(String.valueOf(viewPageCategoryEntity.getId()));
            antdTreeNode.setLabel(viewPageCategoryEntity.getName());
            antdTreeNode.setSelectable(true);
            antdTreeNodeList.add(antdTreeNode);
        }
        return antdTreeNodeList;
    }
    @Override
    public List<AntdTreeNode> getViewPageCategoryChildrenAntdTreeNode(long parentId, List<AntdTreeNode> viewPageCategoryAntdTreeNodeList) {
        List<AntdTreeNode> children = new ArrayList<>(0);
        for(AntdTreeNode antdTreeNode : viewPageCategoryAntdTreeNodeList) {
            if(antdTreeNode.getParentId() != null && antdTreeNode.getParentId().equals(parentId)) {
                children.add(antdTreeNode);
            }
        }
        for(AntdTreeNode child : children) {
            List<AntdTreeNode> childChildren = getViewPageCategoryChildrenAntdTreeNode(child.getId(), viewPageCategoryAntdTreeNodeList);
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
    public ViewPageCategoryEntity getOneByViewPageId(Long viewPageId) {
        return viewPageCategoryMapper.getOneByViewPageId(viewPageId);
    }

}
