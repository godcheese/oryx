package com.gioov.oryx.system.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gioov.tile.util.ClientUtil;
import com.gioov.oryx.common.Common;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.system.entity.OperationLogEntity;
import com.gioov.oryx.system.entity.OperationLogEntity2;
import com.gioov.oryx.system.mapper.OperationLogMapper;
import com.gioov.oryx.system.mapper.OperationLogMapper2;
import com.gioov.oryx.system.service.OperationLogService;
import com.gioov.oryx.user.entity.UserEntity;
import com.gioov.oryx.user.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationLogServiceImpl.class);
    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private Common common;

    @Autowired
    private OperationLogMapper2 operationLogMapper2;

    @Override
    public Pagination<OperationLogEntity2> pageAll(Integer page, Integer rows) {
        Pagination<OperationLogEntity2> pagination = new Pagination<>();

//        if(sorterField != null && !"".equals(sorterField) && sorterOrder != null && !"".equals(sorterOrder)) {
//            sorterField = StringUtil.camelToUnderline(sorterField);
//            String orderBy = sorterField + " " + sorterOrder;
//            PageHelper.startPage(page, rows, orderBy);
//        } else {
        PageHelper.startPage(page, rows);
//        }
        Page<OperationLogEntity2> dictionaryEntity2Page = operationLogMapper2.pageAll();
        pagination.setRows(dictionaryEntity2Page.getResult());
        pagination.setTotal(dictionaryEntity2Page.getTotal());
        return pagination;
    }

    @Override
    public OperationLogEntity addOne(OperationLogEntity operationLogEntity) {
       operationLogEntity.setGmtCreated(new Date());
        operationLogMapper.insertOne(operationLogEntity);
        return operationLogEntity;
    }

    @Override
    public int deleteAll(List<Long> idList) {
        return operationLogMapper.deleteAll(idList);
    }

    @Override
    public OperationLogEntity2 getOne(Long id) {
        return operationLogMapper2.getOne(id);
    }

    @Override
    public void log(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, long requestTime) throws JsonProcessingException {
        LOGGER.info("{},{}", httpServletRequest, httpServletResponse);
        if (httpServletRequest != null && httpServletResponse != null) {
            OperationLogEntity operationLogEntity = new OperationLogEntity();
            UserEntity userEntity = userService.getCurrentUser();
            if (userEntity != null) {
                operationLogEntity.setUserId(userEntity.getId());
            }
            operationLogEntity.setIpAddress(ClientUtil.getClientIp(httpServletRequest));
            String operation = "";
            if (handler instanceof HandlerMethod) {
                OperationLog operationLog = ((HandlerMethod) handler).getMethod().getAnnotation(OperationLog.class);
                if (operationLog != null) {
                    operationLogEntity.setOperationType(operationLog.type().value());
                    operation = operationLog.value();
                    if ("".equals(operation)) {
                        operation = operationLog.operation();
                    }
                }
            }
            operationLogEntity.setOperation(operation);
            operationLogEntity.setConsumingTime(requestTime);
            StringBuffer requestUrl = httpServletRequest.getRequestURL();
            if(requestUrl != null) {
                operationLogEntity.setRequestUrl(requestUrl.toString());
            }
            operationLogEntity.setRequestMethod(httpServletRequest.getMethod());
            Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
            Map<String, Object> map = new HashMap<>(0);
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                Object parameter = httpServletRequest.getParameter(name);
                map.put(name, parameter);
            }
            operationLogEntity.setRequestParameter(common.objectToJson(map));
            operationLogEntity.setAcceptLanguage(httpServletRequest.getHeader("Accept-Language"));
            operationLogEntity.setReferer(httpServletRequest.getHeader("Referer"));
            operationLogEntity.setUserAgent(httpServletRequest.getHeader("User-Agent"));
            operationLogEntity.setHandler(handler.toString());
            HttpSession httpSession = httpServletRequest.getSession();
            if (httpSession != null) {
                operationLogEntity.setSessionId(httpSession.getId());
            }
            Cookie[] cookies = httpServletRequest.getCookies();
            map = new HashMap<>(0);
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    map.put(cookie.getName(), cookie.getValue());
                }
            }
            operationLogEntity.setCookie(common.objectToJson(map));
            operationLogEntity.setStatus(String.valueOf(httpServletResponse.getStatus()));
            operationLogEntity.setGmtCreated(new Date());
        }
    }

    @Override
    public void clearAll() {
        operationLogMapper.truncate();
    }

}