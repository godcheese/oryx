package com.gioov.oryx.system.api;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.system.System;
import com.gioov.oryx.system.entity.OperationLogEntity2;
import com.gioov.oryx.system.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gioov.oryx.user.service.UserService.SYSTEM_ADMIN;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@RestController
@RequestMapping(value = System.Api.OPERATION_LOG, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OperationLogRestController {

    private static final String OPERATION_LOG = "/API/SYSTEM/OPERATION_LOG";

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 分页获取所有操作日志
     * @param page 页
     * @param rows 每页显示数量
     * @return ResponseEntity<Pagination<OperationLogEntity2>>
     */
    @OperationLog(value = "分页获取所有操作日志", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + OPERATION_LOG + "/PAGE_ALL')")
    @GetMapping(value = "/page_all")
    public ResponseEntity<Pagination<OperationLogEntity2>> pageAll(@RequestParam Integer page, @RequestParam Integer rows) throws BaseResponseException {
        return new ResponseEntity<>(operationLogService.pageAll(page, rows), HttpStatus.OK);
    }

    /**
     * 指定操作日志 id，获取操作日志
     * @param id 操作日志 id
     * @return ResponseEntity<OperationLogEntity2>
     */
    @OperationLog(value = "指定操作日志 id，获取操作日志", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + OPERATION_LOG + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<OperationLogEntity2> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(operationLogService.getOne(id), HttpStatus.OK);
    }

    /**
     * 清空所有操作日志
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "清空所有操作日志", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + OPERATION_LOG + "/CLEAR_ALL')")
    @PostMapping(value = "/clear_all")
    public ResponseEntity<HttpStatus> clearAll() {
        operationLogService.clearAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
