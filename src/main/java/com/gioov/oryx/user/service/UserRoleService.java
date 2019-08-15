package com.gioov.oryx.user.service;

import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.user.entity.UserRoleEntity;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface UserRoleService {

    /**
     * 分页获取所有用户角色
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<UserRoleEntity>
     */
    Pagination<UserRoleEntity> pageAll(Integer page, Integer rows);

    /**
     * 新增用户角色
     * @param userRoleEntity UserRoleEntity
     * @return UserRoleEntity
     */
    UserRoleEntity addOne(UserRoleEntity userRoleEntity);

    /**
     * 指定用户 id、角色 id list，批量删除用户角色
     * @param userId 用户 id
     * @param roleIdList 角色 id list
     * @return 已删除角色个数
     */
    int deleteAllByUserIdAndRoleIdList(Long userId, List<Long> roleIdList);

}
