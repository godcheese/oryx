package com.gioov.oryx.oauth.service.impl;

import com.gioov.oryx.common.FailureMessage;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.oauth.entity.ClientEntity;
import com.gioov.oryx.oauth.mapper.ClientMapper;
import com.gioov.oryx.oauth.service.ClientService;
import com.gioov.oryx.quartz.job.BaseJob;
import com.gioov.oryx.user.entity.RoleEntity;
import com.gioov.oryx.user.entity.UserRoleEntity;
import com.gioov.tile.web.exception.BaseResponseException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-02-01
 */
@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private FailureMessage failureMessage;

    @Override
    public Pagination<ClientEntity> pageAll(Integer page, Integer rows) {
        Pagination<ClientEntity> pagination = new Pagination<>();
        PageHelper.startPage(page, rows);
        Page<ClientEntity> clientEntityPage = clientMapper.pageAll();
        pagination.setRows(clientEntityPage.getResult());
        pagination.setTotal(clientEntityPage.getTotal());
        return pagination;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ClientEntity addOne(ClientEntity clientEntity) throws BaseResponseException {
        if (clientMapper.getOne(clientEntity.getClientId()) != null) {
            throw new BaseResponseException(failureMessage.i18n("oauth_client.add_fail_oauth_client_exists"));
        }
        clientMapper.insertOne(clientEntity);
        return clientEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ClientEntity saveOne(ClientEntity clientEntity) throws BaseResponseException {
        String clientId = clientEntity.getClientId();
        ClientEntity clientEntity2 = clientMapper.getOne(clientId);
        if (clientEntity2 != null && !clientEntity2.getClientId().equals(clientEntity.getClientId())) {
            throw new BaseResponseException(failureMessage.i18n("oauth_client.save_fail_oauth_client_exists"));
        }
        clientMapper.updateOne(clientEntity);
        return clientEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteAll(List<String> clientIdList) throws BaseResponseException {
        return clientMapper.deleteAll(clientIdList);
    }

    @Override
    public ClientEntity getOne(String clientId) {
        return clientMapper.getOne(clientId);
    }

}
