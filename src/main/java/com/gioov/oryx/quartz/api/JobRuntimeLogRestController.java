package com.gioov.oryx.quartz.api;

import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.quartz.Quartz;
import com.gioov.oryx.quartz.entity.JobRuntimeLogEntity;
import com.gioov.oryx.quartz.service.JobRuntimeLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.gioov.oryx.user.service.UserService.SYSTEM_ADMIN;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-02-01
 */
@RestController
@RequestMapping(Quartz.Api.JOB_RUNTIME_LOG)
public class JobRuntimeLogRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobRuntimeLogRestController.class);

    private static final String JOB_RUNTIME_LOG = "/API/QUARTZ/JOB_RUNTIME_LOG";

    @Autowired
    private JobRuntimeLogService jobRuntimeLogService;

    /**
     * 指定任务运行日志 id，获取任务运行日志
     * @param id 任务运行日志 id
     * @return ResponseEntity<JobRuntimeLogEntity>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + JOB_RUNTIME_LOG + "/ONE')")
    @GetMapping(value = "/one/{id}")
    public ResponseEntity<JobRuntimeLogEntity> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(jobRuntimeLogService.getOne(id), HttpStatus.OK);
    }

    /**
     * 分页获取所有任务运行日志
     * @param page 页
     * @param rows 每页显示数量
     * @return ResponseEntity<Pagination<JobRuntimeLogEntity>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + JOB_RUNTIME_LOG + "/PAGE_ALL')")
    @GetMapping(value = "/page_all")
    public ResponseEntity<Pagination<JobRuntimeLogEntity>> pageAll(@RequestParam Integer page, @RequestParam Integer rows) {
        return new ResponseEntity<>(jobRuntimeLogService.pageAll(page, rows), HttpStatus.OK);
    }

    /**
     * 清空所有任务运行日志
     * @return ResponseEntity<HttpStatus>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + JOB_RUNTIME_LOG + "/CLEAR_ALL')")
    @PostMapping(value = "/clear_all")
    public ResponseEntity<HttpStatus> clearAll() {
        jobRuntimeLogService.clearAll();
        return new ResponseEntity<>(HttpStatus.OK, HttpStatus.OK);
    }

}
