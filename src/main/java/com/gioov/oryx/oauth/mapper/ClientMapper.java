package com.gioov.oryx.oauth.mapper;

import com.gioov.oryx.oauth.entity.ClientEntity;
import com.gioov.oryx.user.entity.RoleEntity;
import com.gioov.tile.mybatis.CrudMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component("clientMapper")
@Mapper
public interface ClientMapper extends CrudMapper<ClientEntity, String> {

    /**
     * 分页获取所有 OAuth 客户端
     * @return Page<ClientEntity>
     */
    Page<ClientEntity> pageAll();
}
