package com.gioov.oryx.oauth.service;

import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.oauth.entity.ClientEntity;
import com.gioov.tile.web.exception.BaseResponseException;

import java.util.Date;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-02-01
 */
public interface ClientService {

    /**
     * 分页获取所有 OAuth 客户端
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<ClientEntity>
     */
    Pagination<ClientEntity> pageAll(Integer page, Integer rows);

    /**
     * 新增 OAuth 客户端
     * @param clientEntity ClientEntity
     * @return ClientEntity
     * @throws BaseResponseException BaseResponseException
     */
    ClientEntity addOne(ClientEntity clientEntity) throws BaseResponseException;

    /**
     * 保存 OAuth 客户端
     * @param clientEntity ClientEntity
     * @return ClientEntity
     * @throws BaseResponseException BaseResponseException
     */
    ClientEntity saveOne(ClientEntity clientEntity) throws BaseResponseException;

    /**
     * 指定 OAuth 客户端 client id list，批量删除 OAuth 客户端
     * @param clientIdList  OAuth 客户端 client id list
     * @return int 已删除 OAuth 客户端个数
     * @throws BaseResponseException BaseResponseException
     */
    int deleteAll(List<String> clientIdList) throws BaseResponseException;

    /**
     * 指定 OAuth 客户端 client id，获取 OAuth 客户端
     * @param clientId OAuth 客户端 client id
     * @return ClientEntity
     */
    ClientEntity getOne(String clientId);

}