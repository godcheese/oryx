package com.gioov.nimrod.user.mapper;

import com.gioov.common.mybatis.CrudMapper;
import com.gioov.nimrod.user.entity.RoleAuthorityEntity;
import com.gioov.nimrod.user.entity.RoleViewMenuCategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component("roleViewMenuCategoryMapper")
@Mapper
public interface RoleViewMenuCategoryMapper extends CrudMapper<RoleViewMenuCategoryEntity, Long> {

    /**
     * 指定角色 id ，获取所有角色权限
     *
     * @param roleId 角色 id
     * @return List<RoleViewMenuCategoryEntity>
     */
    List<RoleViewMenuCategoryEntity> listAllByRoleId(@Param("roleId") Long roleId);

    /**
     *
     * @param roleId
     * @return
     */
    int deleteAllByRoleId(@Param("roleId") Long roleId);

    /**
     * 指定角色 id 、 视图菜单分类 id list ，批量插入
     *
     * @param roleId        角色 id
     * @param viewMenuCategoryIdList 视图菜单分类 id list
     * @return int
     */
    int insertAllByRoleIdAndViewMenuCategoryIdList(@Param("roleId") Long roleId, @Param("viewMenuCategoryIdList") List<Long> viewMenuCategoryIdList);

    /**
     * 指定角色 id 、authority ，获取角色权限
     *
     * @param roleId    角色 id
     * @param viewMenuCategoryId 视图菜单分类 id
     * @return RoleAuthorityEntity
     */
    RoleViewMenuCategoryEntity getOneByRoleIdAndViewMenuCategoryId(@Param("roleId") Long roleId, @Param("viewMenuCategoryId") Long viewMenuCategoryId);

    int deleteAllByRoleIdAndViewMenuCategoryIdList(@Param("roleId") Long roleId, @Param("viewMenuCategoryIdList") List<Long> viewMenuCategoryIdList);

    int deleteAllByViewMenuCategoryId(@Param("viewMenuCategoryId") Long viewMenuCategoryId);

}
