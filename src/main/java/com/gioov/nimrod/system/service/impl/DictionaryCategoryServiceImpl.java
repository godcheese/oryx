package com.gioov.nimrod.system.service.impl;

import com.gioov.common.mybatis.Pageable;
import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.FailureMessage;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.common.vue.antd.AntdTreeNode;
import com.gioov.nimrod.common.vue.antd.DictionaryCategoryEntityAsAntdTable;
import com.gioov.nimrod.system.entity.DictionaryCategoryEntity;
import com.gioov.nimrod.system.entity.DictionaryEntity;
import com.gioov.nimrod.system.mapper.DictionaryCategoryMapper;
import com.gioov.nimrod.system.mapper.DictionaryMapper;
import com.gioov.nimrod.system.service.DictionaryCategoryService;
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

    @Override
    public Pagination<DictionaryCategoryEntity> pageAllParent(Integer page, Integer rows) {
        Pagination<DictionaryCategoryEntity> pagination = new Pagination<>();
        List<DictionaryCategoryEntity> dictionaryCategoryEntityList = dictionaryCategoryMapper.pageAllByParentIdIsNull(new Pageable(page, rows));
        if (dictionaryCategoryEntityList != null) {
            pagination.setRows(dictionaryCategoryEntityList);
        }
        pagination.setTotal(dictionaryCategoryMapper.countAllByParentIdIsNull());
        return pagination;
    }

    @Override
    public List<DictionaryCategoryEntity> listAllByParentId(Long parentId) {
        return dictionaryCategoryMapper.listAllByParentId(parentId);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DictionaryCategoryEntity insertOne(DictionaryCategoryEntity dictionaryCategoryEntity) {
        Date date = new Date();
        dictionaryCategoryEntity.setGmtCreated(date);
        dictionaryCategoryMapper.insertOne(dictionaryCategoryEntity);
        return dictionaryCategoryEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DictionaryCategoryEntity updateOne(DictionaryCategoryEntity dictionaryCategoryEntity) {
        Date date = new Date();
        dictionaryCategoryEntity.setGmtModified(date);
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
                throw new BaseResponseException(FailureMessage.DELETE_DICTIONARY_CATEGORY_FAIL1);
            }
            DictionaryEntity dictionaryEntity = dictionaryMapper.getOneByDictionaryCategoryId(id);
            if (dictionaryEntity != null) {
                throw new BaseResponseException(FailureMessage.DELETE_DICTIONARY_CATEGORY_FAIL2);
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
