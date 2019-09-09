package com.gioov.oryx.user.service.impl;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.FailureMessage;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.ApiCategoryEntityAsAntdTable;
import com.gioov.oryx.system.service.DictionaryService;
import com.gioov.oryx.user.entity.ApiCategoryEntity;
import com.gioov.oryx.user.entity.ApiEntity;
import com.gioov.oryx.user.mapper.ApiCategoryMapper;
import com.gioov.oryx.user.mapper.ApiMapper;
import com.gioov.oryx.user.service.ApiCategoryService;
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
public class ApiCategoryServiceImpl implements ApiCategoryService {

    @Autowired
    private ApiCategoryMapper apiCategoryMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private FailureMessage failureMessage;

//    @Override
//    public Pagination<ApiCategoryEntity> pageAllParent(Integer page, Integer rows) {
//        Pagination<ApiCategoryEntity> pagination = new Pagination<>();
//        //        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
////            sorterField = StringUtil.camelToUnderline(sorterField);
////            String orderBy = sorterField + " " + sorterOrder;
////            PageHelper.startPage(page, rows, orderBy);
////        } else {
//        PageHelper.startPage(page, rows);
////        }
//        Page<ApiCategoryEntity> apiCategoryEntityPage = apiCategoryMapper.pageAllByParentIdIsNull();
//        pagination.setRows(apiCategoryEntityPage);
//        pagination.setTotal(apiCategoryEntityPage.getTotal());
//        return pagination;
//    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiCategoryEntity addOne(ApiCategoryEntity apiCategoryEntity) {
        Date date = new Date();
        apiCategoryEntity.setGmtModified(date);
        apiCategoryEntity.setGmtCreated(date);
        apiCategoryMapper.insertOne(apiCategoryEntity);
        return apiCategoryEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiCategoryEntity saveOne(ApiCategoryEntity apiCategoryEntity) {
        ApiCategoryEntity apiCategoryEntity1 = apiCategoryMapper.getOne(apiCategoryEntity.getId());
        apiCategoryEntity1.setName(apiCategoryEntity.getName());
        apiCategoryEntity1.setParentId(apiCategoryEntity.getParentId());
        apiCategoryEntity1.setSort(apiCategoryEntity.getSort());
        apiCategoryEntity1.setRemark(apiCategoryEntity.getRemark());
        apiCategoryEntity1.setGmtModified(new Date());
        apiCategoryMapper.updateOne(apiCategoryEntity1);
        return apiCategoryEntity1;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteAll(List<Long> idList) throws BaseResponseException {
        int result = 0;
        for (Long id : idList) {
            ApiCategoryEntity viewPageCategoryEntity = apiCategoryMapper.getOneByParentId(id);
            if (viewPageCategoryEntity != null) {
                throw new BaseResponseException(failureMessage.i18n("api_category.delete_fail_has_category"));
            }
            ApiEntity apiEntity = apiMapper.getOneByApiCategoryId(id);
            if (apiEntity != null) {
                throw new BaseResponseException(failureMessage.i18n("api_category.delete_fail_has_api"));
            }
            apiCategoryMapper.deleteOne(id);
            result++;
        }
        return result;
    }

    @Override
    public ApiCategoryEntity getOne(Long id) {
        return apiCategoryMapper.getOne(id);
    }

    @Override
    public List<ApiCategoryEntity> listAllByParentId(Long parentId) {
        return apiCategoryMapper.listAllByParentId(parentId);
    }

    @Override
    public List<ApiCategoryEntity> listAll() {
        return apiCategoryMapper.listAll();
    }

    @Override
    public List<ApiCategoryEntityAsAntdTable> listAllApiCategoryEntityAsAntdTable() {
        List<ApiCategoryEntityAsAntdTable> apiCategoryEntityAsAntdTableList = new ArrayList<>(0);
        List<ApiCategoryEntity> apiCategoryEntityList = listAll();
        for(ApiCategoryEntity apiCategoryEntity : apiCategoryEntityList) {
            ApiCategoryEntityAsAntdTable apiCategoryEntityAsAntdTable = new ApiCategoryEntityAsAntdTable();
            apiCategoryEntityAsAntdTable.setId(apiCategoryEntity.getId());
            apiCategoryEntityAsAntdTable.setParentId(apiCategoryEntity.getParentId());
            apiCategoryEntityAsAntdTable.setName(apiCategoryEntity.getName());
            apiCategoryEntityAsAntdTable.setRemark(apiCategoryEntity.getRemark());
            apiCategoryEntityAsAntdTable.setGmtModified(apiCategoryEntity.getGmtModified());
            apiCategoryEntityAsAntdTable.setGmtCreated(apiCategoryEntity.getGmtCreated());
            apiCategoryEntityAsAntdTableList.add(apiCategoryEntityAsAntdTable);
        }
        return apiCategoryEntityAsAntdTableList;
    }
    @Override
    public List<ApiCategoryEntityAsAntdTable> getApiCategoryChildrenApiCategoryEntityAsAntdTable(long parentId, List<ApiCategoryEntityAsAntdTable> apiCategoryEntityAsAntdTableList) {
        List<ApiCategoryEntityAsAntdTable> children = new ArrayList<>(0);
        for(ApiCategoryEntityAsAntdTable apiCategoryEntityAsAntdTable : apiCategoryEntityAsAntdTableList) {
            if(apiCategoryEntityAsAntdTable.getParentId() != null && apiCategoryEntityAsAntdTable.getParentId().equals(parentId)) {
                children.add(apiCategoryEntityAsAntdTable);
            }
        }
        for(ApiCategoryEntityAsAntdTable child : children) {
            List<ApiCategoryEntityAsAntdTable> childChildren = getApiCategoryChildrenApiCategoryEntityAsAntdTable(child.getId(), apiCategoryEntityAsAntdTableList);
            child.setChildren(childChildren);
        }
        if(children.size() == 0) {
            return null;
        }
        return children;
    }

    @Override
    public List<AntdTreeNode> listAllApiCategoryAntdTreeNode() {
        List<AntdTreeNode> antdTreeNodeList = new ArrayList<>(0);
        List<ApiCategoryEntity> apiCategoryEntityList = listAll();
        for(ApiCategoryEntity apiCategoryEntity : apiCategoryEntityList) {
            AntdTreeNode antdTreeNode = new AntdTreeNode();
            antdTreeNode.setId(apiCategoryEntity.getId());
            antdTreeNode.setParentId(apiCategoryEntity.getParentId());
            antdTreeNode.setValue(String.valueOf(apiCategoryEntity.getId()));
            antdTreeNode.setLabel(apiCategoryEntity.getName());
            antdTreeNode.setSelectable(true);
            antdTreeNodeList.add(antdTreeNode);
        }
        return antdTreeNodeList;
    }
    @Override
    public List<AntdTreeNode> getApiCategoryChildrenAntdTreeNode(long parentId, List<AntdTreeNode> apiCategoryAntdTreeNodeList) {
        List<AntdTreeNode> children = new ArrayList<>(0);
        for(AntdTreeNode antdTreeNode : apiCategoryAntdTreeNodeList) {
            if(antdTreeNode.getParentId() != null && antdTreeNode.getParentId().equals(parentId)) {
                children.add(antdTreeNode);
            }
        }
        for(AntdTreeNode child : children) {
            List<AntdTreeNode> childChildren = getApiCategoryChildrenAntdTreeNode(child.getId(), apiCategoryAntdTreeNodeList);
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
