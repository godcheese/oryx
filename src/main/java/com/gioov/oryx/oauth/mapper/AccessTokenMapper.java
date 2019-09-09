package com.gioov.oryx.oauth.mapper;

import com.gioov.oryx.oauth.entity.AccessTokenEntity;
import com.gioov.oryx.oauth.entity.ClientEntity;
import com.gioov.tile.mybatis.CrudMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component("accessTokenMapper")
@Mapper
public interface AccessTokenMapper extends CrudMapper<AccessTokenEntity, String> {

    /**
     * 分页获取所有 OAuth Access Token
     * @return Page<AccessTokenEntity>
     */
    Page<AccessTokenEntity> pageAll();
}
