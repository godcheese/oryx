package com.gioov.oryx.user.mapper;

import com.gioov.tile.mybatis.CrudMapper;
import com.gioov.oryx.user.entity.DepartmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component("departmentMapper")
@Mapper
public interface DepartmentMapper extends CrudMapper<DepartmentEntity, Long> {

//    /**
//     * 分页获取所有父级 id 为 null 的部门
//     * @return List<DepartmentEntity>
//     */
//    Page<DepartmentEntity> pageAllParentIdIsNull();

    /**
     * 获取所有父级 id 为 null 的部门
     * @return List<DepartmentEntity>
     */
    List<DepartmentEntity> listAllParentIdIsNull();

    /**
     * 指定父级部门 id，获取所有部门
     * @param parentId 父级部门 id
     * @return List<ApiCategoryEntity>
     */
    List<DepartmentEntity> listAllByParentId(@Param("parentId") Long parentId);

    /**
     * 指定父级部门 id，获取部门
     * @param parentId 父级部门 id
     * @return DepartmentEntity
     */
    DepartmentEntity getOneByParentId(@Param("parentId") Long parentId);

}
