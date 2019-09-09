package com.gioov.oryx.oauth.service.impl;

import com.gioov.oryx.common.FailureMessage;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.oauth.entity.AccessTokenEntity;
import com.gioov.oryx.oauth.entity.AccessTokenEntity;
import com.gioov.oryx.oauth.mapper.AccessTokenMapper;
import com.gioov.oryx.oauth.mapper.ClientMapper;
import com.gioov.oryx.oauth.service.AccessTokenService;
import com.gioov.oryx.oauth.service.ClientService;
import com.gioov.tile.web.exception.BaseResponseException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-02-01
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

    @Autowired
    private AccessTokenMapper accessTokenMapper;

    @Autowired
    private FailureMessage failureMessage;

    @Override
    public Pagination<AccessTokenEntity> pageAll(Integer page, Integer rows) {
        Pagination<AccessTokenEntity> pagination = new Pagination<>();
        PageHelper.startPage(page, rows);
        Page<AccessTokenEntity> accessTokenEntityPage = accessTokenMapper.pageAll();
        pagination.setRows(accessTokenEntityPage.getResult());
        pagination.setTotal(accessTokenEntityPage.getTotal());
        return pagination;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteAll(List<String> tokenIdList) throws BaseResponseException {
        return accessTokenMapper.deleteAll(tokenIdList);
    }

    @Override
    public AccessTokenEntity getOne(String tokenId) {
        return accessTokenMapper.getOne(tokenId);
    }

    @Override
    public void clearAll() {
        accessTokenMapper.truncate();
    }
}
