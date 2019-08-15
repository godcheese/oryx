package com.gioov.oryx.user.service.impl;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.ComboTree;
import com.gioov.oryx.common.easyui.TreeGrid;
import com.gioov.oryx.common.vue.antd.AntdTree;
import com.gioov.oryx.common.vue.antd.AntdTreeNode;
import com.gioov.oryx.common.vue.antd.DepartmentEntityAsAntdTable;
import com.gioov.oryx.user.entity.DepartmentEntity;
import com.gioov.oryx.user.entity.UserEntity;
import com.gioov.oryx.user.mapper.DepartmentMapper;
import com.gioov.oryx.user.mapper.UserMapper;
import com.gioov.oryx.user.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserMapper userMapper;

//    @Override
//    public Pagination<DepartmentEntity> pageAllParent(Integer page, Integer rows) {
//        Pagination<DepartmentEntity> pagination = new Pagination<>();
//        //        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
////            sorterField = StringUtil.camelToUnderline(sorterField);
////            String orderBy = sorterField + " " + sorterOrder;
////            PageHelper.startPage(page, rows, orderBy);
////        } else {
//        PageHelper.startPage(page, rows);
////        }
//        Page<DepartmentEntity> departmentEntityPage = departmentMapper.pageAllParentIdIsNull();
//        pagination.setRows(departmentEntityPage.getResult());
//        pagination.setTotal(departmentEntityPage.getTotal());
//        return pagination;
//    }

//    @Override
//    public List<DepartmentEntity> listAllParent() {
//        return departmentMapper.listAllParentIdIsNull();
//    }

    @Override
    public List<DepartmentEntity> listAllByParentId(Long parentId) {
        return departmentMapper.listAllByParentId(parentId);
    }

    //    /**
//     * 根据用户关联角色来获取所有角色
//     *
//     * @param userRoleEntityList 用户角色 list
//     * @return List<RoleEntity>
//     */
//    @Override
//    public List<RoleEntity> listAllByUserRoleList(List<UserRoleEntity> userRoleEntityList) {
//        List<RoleEntity> roleEntityList = null;
//        if (userRoleEntityList != null && !userRoleEntityList.isEmpty()) {
//            roleEntityList = new ArrayList<>();
//            for (UserRoleEntity userRoleEntity : userRoleEntityList) {
//                RoleEntity roleEntity = roleMapper.getOne(userRoleEntity.getRoleId());
//                roleEntityList.add(roleEntity);
//            }
//        }
//        return roleEntityList;
//    }

    @Override
    public List<DepartmentEntity> listAll() {
        return departmentMapper.listAll();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DepartmentEntity addOne(DepartmentEntity departmentEntity) {
        Date date = new Date();
        departmentEntity.setGmtModified(date);
        departmentEntity.setGmtCreated(date);
        departmentMapper.insertOne(departmentEntity);
        return departmentEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DepartmentEntity saveOne(DepartmentEntity departmentEntity) {
        departmentEntity.setGmtModified(new Date());
        departmentMapper.updateOne(departmentEntity);
        return departmentEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteAll(List<Long> idList) throws BaseResponseException {
        int result = 0;
        for (Long id : idList) {
            UserEntity userEntity = userMapper.getOneByDepartmentId(id);
            if (userEntity != null) {
                throw new BaseResponseException("删除失败，该部门下存在用户");
            }
            DepartmentEntity departmentEntity = departmentMapper.getOneByParentId(id);
            if (departmentEntity != null) {
                throw new BaseResponseException("删除失败，该部门下存在子部门");
            }
            departmentMapper.deleteOne(id);
            result++;
        }
        return result;
    }

    @Override
    public DepartmentEntity getOne(Long id) {
        return departmentMapper.getOne(id);
    }

    /**
     * 根据子级部门 id 获取所有父级部门
     * @param id
     * @return
     */
    @Override
    public List<DepartmentEntity> listAllByDepartmentId(Long id) {
        List<DepartmentEntity> departmentEntityResultList = new ArrayList<>(0);
        List<DepartmentEntity> departmentEntityList = listAll();
        DepartmentEntity departmentEntity = getOne(id);
        departmentEntityResultList.add(departmentEntity);
        forEachDepartmentParent(departmentEntity, departmentEntityList, departmentEntityResultList);
        Collections.reverse(departmentEntityResultList);
        return departmentEntityResultList;
    }
    public void forEachDepartmentParent(DepartmentEntity departmentEntity, List<DepartmentEntity> departmentEntityList, List<DepartmentEntity> departmentEntityResultList) {
        for(DepartmentEntity entity : departmentEntityList) {
            if(departmentEntity.getParentId() != null) {
                if(departmentEntity.getParentId().equals(entity.getId())){
                    departmentEntityResultList.add(entity);
                    forEachDepartmentParent(entity, departmentEntityList, departmentEntityResultList);
                }
            }
        }
    }

    @Override
    public List<ComboTree> listAllDepartmentComboTree() {
        List<ComboTree> comboTreeList = new ArrayList<>(0);
        List<DepartmentEntity> departmentEntityList = listAll();
        for(DepartmentEntity departmentEntity : departmentEntityList) {
            ComboTree comboTree = new ComboTree();
            comboTree.setId(departmentEntity.getId());
            comboTree.setText(departmentEntity.getName());
            comboTree.setParentId(departmentEntity.getParentId());
            comboTreeList.add(comboTree);
        }
        return comboTreeList;
    }
    @Override
    public List<ComboTree> getDepartmentChildrenComboTree(long parentId, List<ComboTree> departmentComboTreeList) {

        List<ComboTree> children = new ArrayList<>(0);

        for(ComboTree comboTree : departmentComboTreeList) {
            if(comboTree.getParentId() != null && comboTree.getParentId().equals(parentId)) {
                children.add(comboTree);
            }
        }

        for(ComboTree child : children) {

            List<ComboTree> childChildren = getDepartmentChildrenComboTree(child.getId(), departmentComboTreeList);
            if(childChildren == null) {
                childChildren = new ArrayList<>(0);
            }
            child.setChildren(childChildren);
        }

        return children;
    }

    @Override
    public List<TreeGrid> listAllDepartmentTreeGrid() {
        List<TreeGrid> treeGridList = new ArrayList<>(0);
        List<DepartmentEntity> departmentEntityList = listAll();
        for(DepartmentEntity departmentEntity : departmentEntityList) {
            TreeGrid treeGrid = new TreeGrid();
            treeGrid.setId(departmentEntity.getId());
            treeGrid.setName(departmentEntity.getName());
            treeGrid.setParentId(departmentEntity.getParentId());
            treeGridList.add(treeGrid);
        }
        return treeGridList;
    }
    @Override
    public List<TreeGrid> getDepartmentChildrenTreeGrid(long parentId, List<TreeGrid> departmentTreeGridList) {

        List<TreeGrid> children = new ArrayList<>(0);

        LOGGER.info("departmentTreeGridList={}", departmentTreeGridList);
        for(TreeGrid treeGrid : departmentTreeGridList) {

            if(treeGrid.getParentId() != null && treeGrid.getParentId().equals(parentId)) {
                children.add(treeGrid);
            }
        }

        for(TreeGrid child : children) {

            List<TreeGrid> childChildren = getDepartmentChildrenTreeGrid(child.getId(), departmentTreeGridList);
            if(childChildren == null) {
                childChildren = new ArrayList<>(0);
            }
            child.setChildren(childChildren);
        }
        return children;
    }

    @Override
    public List<AntdTreeNode> listAllDepartmentAntdTreeNode() {
        List<AntdTreeNode> antdTreeNodeList = new ArrayList<>(0);
        List<DepartmentEntity> departmentEntityList = listAll();
        for(DepartmentEntity departmentEntity : departmentEntityList) {
            AntdTreeNode antdTreeNode = new AntdTreeNode();
            antdTreeNode.setId(departmentEntity.getId());
            antdTreeNode.setParentId(departmentEntity.getParentId());
            antdTreeNode.setValue(String.valueOf(departmentEntity.getId()));
            antdTreeNode.setLabel(departmentEntity.getName());
            antdTreeNode.setSelectable(true);
            antdTreeNodeList.add(antdTreeNode);
        }
        return antdTreeNodeList;
    }
    @Override
    public List<AntdTreeNode> getDepartmentChildrenAntdTreeNode(long parentId, List<AntdTreeNode> departmentAntdTreeNodeList) {
        List<AntdTreeNode> children = new ArrayList<>(0);
        for(AntdTreeNode antdTreeNode : departmentAntdTreeNodeList) {
            if(antdTreeNode.getParentId() != null && antdTreeNode.getParentId().equals(parentId)) {
                children.add(antdTreeNode);
            }
        }
        for(AntdTreeNode child : children) {
            List<AntdTreeNode> childChildren = getDepartmentChildrenAntdTreeNode(child.getId(), departmentAntdTreeNodeList);
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
    public List<AntdTree> listAllDepartmentAntdTree() {
        List<AntdTree> antdTreeList = new ArrayList<>(0);
        List<DepartmentEntity> departmentEntityList = listAll();
        for(DepartmentEntity departmentEntity : departmentEntityList) {
            AntdTree antdTree = new AntdTree();
            antdTree.setId(departmentEntity.getId());
            antdTree.setParentId(departmentEntity.getParentId());
            antdTree.setKey(String.valueOf(departmentEntity.getId()));
            antdTree.setTitle(departmentEntity.getName());
            antdTreeList.add(antdTree);
        }
        return antdTreeList;
    }
    @Override
    public List<AntdTree> getDepartmentChildrenAntdTree(long parentId, List<AntdTree> departmentAntdTreeList) {
        List<AntdTree> children = new ArrayList<>(0);
        for(AntdTree antdTree : departmentAntdTreeList) {
            if(antdTree.getParentId() != null && antdTree.getParentId().equals(parentId)) {
                children.add(antdTree);
            }
        }
        for(AntdTree child : children) {
            List<AntdTree> childChildren = getDepartmentChildrenAntdTree(child.getId(), departmentAntdTreeList);
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
    public List<DepartmentEntityAsAntdTable> listAllDepartmentEntityAsAntdTable() {
        List<DepartmentEntityAsAntdTable> departmentEntityAsAntdTableList = new ArrayList<>(0);
        List<DepartmentEntity> departmentEntityList = listAll();
        for(DepartmentEntity departmentEntity : departmentEntityList) {
            DepartmentEntityAsAntdTable departmentEntityAsAntdTable = new DepartmentEntityAsAntdTable();
            departmentEntityAsAntdTable.setId(departmentEntity.getId());
            departmentEntityAsAntdTable.setParentId(departmentEntity.getParentId());
            departmentEntityAsAntdTable.setName(departmentEntity.getName());
            departmentEntityAsAntdTable.setRemark(departmentEntity.getRemark());
            departmentEntityAsAntdTable.setGmtModified(departmentEntity.getGmtModified());
            departmentEntityAsAntdTable.setGmtCreated(departmentEntity.getGmtCreated());
            departmentEntityAsAntdTableList.add(departmentEntityAsAntdTable);
        }
        return departmentEntityAsAntdTableList;
    }
    @Override
    public List<DepartmentEntityAsAntdTable> getDepartmentChildrenDepartmentEntityAsAntdTable(long parentId, List<DepartmentEntityAsAntdTable> departmentEntityAsAntdTableList) {
        List<DepartmentEntityAsAntdTable> children = new ArrayList<>(0);
        for(DepartmentEntityAsAntdTable departmentEntityAsAntdTable : departmentEntityAsAntdTableList) {
            if(departmentEntityAsAntdTable.getParentId() != null && departmentEntityAsAntdTable.getParentId().equals(parentId)) {
                children.add(departmentEntityAsAntdTable);
            }
        }
        for(DepartmentEntityAsAntdTable child : children) {
            List<DepartmentEntityAsAntdTable> childChildren = getDepartmentChildrenDepartmentEntityAsAntdTable(child.getId(), departmentEntityAsAntdTableList);
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
