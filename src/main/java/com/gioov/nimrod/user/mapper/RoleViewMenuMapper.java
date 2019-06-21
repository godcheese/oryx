package com.gioov.nimrod.user.mapper;

import com.gioov.common.mybatis.CrudMapper;
import com.gioov.nimrod.user.entity.RoleViewMenuCategoryEntity;
import com.gioov.nimrod.user.entity.RoleViewMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component("roleViewMenuEntity")
@Mapper
public interface RoleViewMenuMapper extends CrudMapper<RoleViewMenuEntity, Long> {

    /**
     * 指定角色 id ，获取所有角色权限
     *
     * @param roleId 角色 id
     * @return List<RoleViewMenuEntity>
     */
    List<RoleViewMenuEntity> listAllByRoleId(@Param("roleId") Long roleId);

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
     * @param viewMenuIdList 视图菜单 id list
     * @return int
     */
    int insertAllByRoleIdAndViewMenuIdList(@Param("roleId") Long roleId, @Param("viewMenuIdList") List<Long> viewMenuIdList);

    /**
     * 指定角色 id 、authority ，获取角色权限
     *
     * @param roleId    角色 id
     * @param viewMenuId 视图菜单 id
     * @return RoleAuthorityEntity
     */
    RoleViewMenuEntity getOneByRoleIdAndViewMenuId(@Param("roleId") Long roleId, @Param("viewMenuId") Long viewMenuId);

    int deleteAllByRoleIdAndViewMenuIdList(@Param("roleId") Long roleId, @Param("viewMenuIdList") List<Long> viewMenuIdList);

    int deleteAllByViewMenuIdList(@Param("viewMenuIdList") List<Long> viewMenuIdList);

}
