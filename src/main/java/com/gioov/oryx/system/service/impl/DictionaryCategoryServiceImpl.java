package com.gioov.oryx.system.service.impl;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.FailureMessage;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.DictionaryCategoryEntityAsAntdTable;
import com.gioov.oryx.system.entity.DictionaryCategoryEntity;
import com.gioov.oryx.system.entity.DictionaryEntity;
import com.gioov.oryx.system.mapper.DictionaryCategoryMapper;
import com.gioov.oryx.system.mapper.DictionaryMapper;
import com.gioov.oryx.system.service.DictionaryCategoryService;
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
public class DictionaryCategoryServiceImpl implements DictionaryCategoryService {

    @Autowired
    private DictionaryCategoryMapper dictionaryCategoryMapper;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private FailureMessage failureMessage;

//    @Override
//    public Pagination<DictionaryCategoryEntity> pageAllParent(Integer page, Integer rows) {
//        Pagination<DictionaryCategoryEntity> pagination = new Pagination<>();
//        //        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
////            sorterField = StringUtil.camelToUnderline(sorterField);
////            String orderBy = sorterField + " " + sorterOrder;
////            PageHelper.startPage(page, rows, orderBy);
////        } else {
//        PageHelper.startPage(page, rows);
////        }
//        Page<DictionaryCategoryEntity> dictionaryCategoryEntityPage = dictionaryCategoryMapper.pageAllByParentIdIsNull();
//            pagination.setRows(dictionaryCategoryEntityPage.getResult());
//        pagination.setTotal(dictionaryCategoryEntityPage.getTotal());
//        return pagination;
//    }

    @Override
    public List<DictionaryCategoryEntity> listAllByParentId(Long parentId) {
        return dictionaryCategoryMapper.listAllByParentId(parentId);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DictionaryCategoryEntity insertOne(DictionaryCategoryEntity dictionaryCategoryEntity) {
        Date date = new Date();
        dictionaryCategoryEntity.setGmtModified(date);
        dictionaryCategoryEntity.setGmtCreated(date);
        dictionaryCategoryMapper.insertOne(dictionaryCategoryEntity);
        return dictionaryCategoryEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DictionaryCategoryEntity updateOne(DictionaryCategoryEntity dictionaryCategoryEntity) {
        dictionaryCategoryEntity.setGmtModified(new Date());
        dictionaryCategoryMapper.updateOne(dictionaryCategoryEntity);
        return dictionaryCategoryEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteAll(List<Long> idList) throws BaseResponseException {
        int result = 0;
        for (Long id : idList) {
            DictionaryCategoryEntity dictionaryCategoryEntity = dictionaryCategoryMapper.getOneByParentId(id);
            if (dictionaryCategoryEntity != null) {
                throw new BaseResponseException(failureMessage.i18n("dictionary_category.delete_fail_has_children_category"));
            }
            DictionaryEntity dictionaryEntity = dictionaryMapper.getOneByDictionaryCategoryId(id);
            if (dictionaryEntity != null) {
                throw new BaseResponseException(failureMessage.i18n("dictionary_category.delete_fail_has_dictionary"));
            }
            dictionaryCategoryMapper.deleteOne(id);
            result++;
        }
        return result;
    }

    @Override
    public DictionaryCategoryEntity getOne(Long id) {
        return dictionaryCategoryMapper.getOne(id);
    }

    @Override
    public List<DictionaryCategoryEntity> listAll() {
        return dictionaryCategoryMapper.listAll();
    }

    @Override
    public List<DictionaryCategoryEntityAsAntdTable> listAllDictionaryCategoryEntityAsAntdTable() {
        List<DictionaryCategoryEntityAsAntdTable> dictionaryCategoryEntityAsAntdTableList = new ArrayList<>(0);
        List<DictionaryCategoryEntity> dictionaryCategoryEntityList = listAll();
        for(DictionaryCategoryEntity dictionaryCategoryEntity : dictionaryCategoryEntityList) {
            DictionaryCategoryEntityAsAntdTable dictionaryCategoryEntityAsAntdTable = new DictionaryCategoryEntityAsAntdTable();
            dictionaryCategoryEntityAsAntdTable.setId(dictionaryCategoryEntity.getId());
            dictionaryCategoryEntityAsAntdTable.setName(dictionaryCategoryEntity.getName());
            dictionaryCategoryEntityAsAntdTable.setParentId(dictionaryCategoryEntity.getParentId());
            dictionaryCategoryEntityAsAntdTable.setSort(dictionaryCategoryEntity.getSort());
            dictionaryCategoryEntityAsAntdTable.setRemark(dictionaryCategoryEntity.getRemark());
            dictionaryCategoryEntityAsAntdTable.setGmtModified(dictionaryCategoryEntity.getGmtModified());
            dictionaryCategoryEntityAsAntdTable.setGmtCreated(dictionaryCategoryEntity.getGmtCreated());
            dictionaryCategoryEntityAsAntdTableList.add(dictionaryCategoryEntityAsAntdTable);
        }
        return dictionaryCategoryEntityAsAntdTableList;
    }

    @Override
    public List<DictionaryCategoryEntityAsAntdTable> getDictionaryCategoryChildrenDictionaryCategoryEntityAsAntdTable(long parentId, List<DictionaryCategoryEntityAsAntdTable> dictionaryCategoryEntityAsAntdTableList) {
        List<DictionaryCategoryEntityAsAntdTable> children = new ArrayList<>(0);
        for(DictionaryCategoryEntityAsAntdTable dictionaryCategoryEntityAsAntdTable : dictionaryCategoryEntityAsAntdTableList) {
            if(dictionaryCategoryEntityAsAntdTable.getParentId() != null && dictionaryCategoryEntityAsAntdTable.getParentId().equals(parentId)) {
                children.add(dictionaryCategoryEntityAsAntdTable);
            }
        }
        for(DictionaryCategoryEntityAsAntdTable child : children) {
            List<DictionaryCategoryEntityAsAntdTable> childChildren = getDictionaryCategoryChildrenDictionaryCategoryEntityAsAntdTable(child.getId(), dictionaryCategoryEntityAsAntdTableList);
            child.setChildren(childChildren);
        }
        if(children.size() == 0) {
            return null;
        }
        return children;
    }

    @Override
    public List<AntdTreeNode> listAllDictionaryCategoryAntdTreeNode() {
        List<AntdTreeNode> antdTreeNodeList = new ArrayList<>(0);
        List<DictionaryCategoryEntity> dictionaryCategoryEntityList = listAll();
        for(DictionaryCategoryEntity dictionaryCategoryEntity : dictionaryCategoryEntityList) {
            AntdTreeNode antdTreeNode = new AntdTreeNode();
            antdTreeNode.setId(dictionaryCategoryEntity.getId());
            antdTreeNode.setParentId(dictionaryCategoryEntity.getParentId());
            antdTreeNode.setValue(String.valueOf(dictionaryCategoryEntity.getId()));
            antdTreeNode.setLabel(dictionaryCategoryEntity.getName());
            antdTreeNode.setSelectable(true);
            antdTreeNodeList.add(antdTreeNode);
        }
        return antdTreeNodeList;
    }
    @Override
    public List<AntdTreeNode> getDictionaryCategoryChildrenAntdTreeNode(long parentId, List<AntdTreeNode> dictionaryCategoryAntdTreeNodeList) {
        List<AntdTreeNode> children = new ArrayList<>(0);
        for(AntdTreeNode antdTreeNode : dictionaryCategoryAntdTreeNodeList) {
            if(antdTreeNode.getParentId() != null && antdTreeNode.getParentId().equals(parentId)) {
                children.add(antdTreeNode);
            }
        }
        for(AntdTreeNode child : children) {
            List<AntdTreeNode> childChildren = getDictionaryCategoryChildrenAntdTreeNode(child.getId(), dictionaryCategoryAntdTreeNodeList);
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

}
