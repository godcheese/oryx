package com.gioov.oryx.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.system.entity.OperationLogEntity;
import com.gioov.oryx.system.entity.OperationLogEntity2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
public interface OperationLogService {

    /**
     * 分页获取所有操作日志
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<OperationLogEntity2>
     */
    Pagination<OperationLogEntity2> pageAll(Integer page, Integer rows);

    /**
     * 新增操作日志
     * @param operationLogEntity OperationLogEntity
     * @return OperationLogEntity
     */
    OperationLogEntity addOne(OperationLogEntity operationLogEntity);

    /**
     * 指定操作日志 id，批量删除操作日志
     * @param idList 操作日志 id list
     * @return 已删除操作日志个数
     */
    int deleteAll(List<Long> idList);

    /**
     * 指定操作日志 id，获取操作日志
     * @param id 操作日志 id
     * @return OperationLogEntity2
     */
    OperationLogEntity2 getOne(Long id);

    /**
     * 记录操作日志
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param handler Handler
     * @param requestTime requestTime
     * @throws JsonProcessingException JsonProcessingException
     */
    void log(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, long requestTime) throws JsonProcessingException;

    /**
     * 清空所有操作日志
     */
    void clearAll();

}
