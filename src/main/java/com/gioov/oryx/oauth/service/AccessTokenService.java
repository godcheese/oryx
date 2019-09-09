package com.gioov.oryx.oauth.service;

import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.oauth.entity.AccessTokenEntity;
import com.gioov.oryx.oauth.entity.ClientEntity;
import com.gioov.tile.web.exception.BaseResponseException;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-02-01
 */
public interface AccessTokenService {

    /**
     * 分页获取所有 OAuth Access Token
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<AccessTokenEntity>
     */
    Pagination<AccessTokenEntity> pageAll(Integer page, Integer rows);

    /**
     * 指定 OAuth AccessToken token id list，批量删除 OAuth Access Token
     * @param tokenIdList  OAuth Access Token token id list
     * @return int 已删除 OAuth Access Token 个数
     * @throws BaseResponseException BaseResponseException
     */
    int deleteAll(List<String> tokenIdList) throws BaseResponseException;

    /**
     * 指定 OAuth Access Token token id，获取 OAuth Access Token
     * @param tokenId OAuth Access Token token id
     * @return AccessTokenEntity
     */
    AccessTokenEntity getOne(String tokenId);

    void clearAll();
}