package com.gioov.oryx.quartz.api;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.quartz.Quartz;
import com.gioov.oryx.quartz.entity.JobEntity;
import com.gioov.oryx.quartz.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.gioov.oryx.user.service.UserService.SYSTEM_ADMIN;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-02-01
 */
@RestController
@RequestMapping(Quartz.Api.JOB)
public class JobRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobRestController.class);

    private static final String JOB = "/API/QUARTZ/JOB";

    @Autowired
    private JobService jobService;

    /**
     * 新增任务
     * @param jobClassName JobClassName
     * @param jobGroup JobGroup
     * @param cronExpression Cron 表达式
     * @param description 描述
     * @return ResponseEntity<Date>
     * @throws BaseResponseException BaseResponseException
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + JOB + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<Date> addJob(@RequestParam String jobClassName, @RequestParam String jobGroup, @RequestParam String cronExpression, @RequestParam String description) throws BaseResponseException {
        return new ResponseEntity<>(jobService.addOne(jobClassName, jobGroup, cronExpression, description), HttpStatus.OK);
    }

    /**
     * 指定 JobClassName、JobGroup，获取任务
     * @param jobClassName JobClassName
     * @param jobGroup JobGroup
     * @return ResponseEntity<JobEntity>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + JOB + "/ONE')")
    @GetMapping(value = "/one")
    public ResponseEntity<JobEntity> getOne(@RequestParam String jobClassName, @RequestParam String jobGroup) {
        return new ResponseEntity<>(jobService.getOne(jobClassName, jobGroup), HttpStatus.OK);
    }

    /**
     * 分页获取所有任务
     * @param page 页
     * @param rows 每页显示数量
     * @return ResponseEntity<Pagination<JobEntity>>
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + JOB + "/PAGE_ALL')")
    @GetMapping(value = "/page_all")
    public ResponseEntity<Pagination<JobEntity>> pageAll(@RequestParam Integer page, @RequestParam Integer rows) {
        return new ResponseEntity<>(jobService.pageAll(page, rows), HttpStatus.OK);
    }

    /**
     * 指定 JobClassName list、JobGroup list，暂停所有任务
     * @param jobClassNameList JobClassName list
     * @param jobGroupList JobGroup list
     * @return ResponseEntity<HttpStatus>
     * @throws BaseResponseException BaseResponseException
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + JOB + "/PAUSE_ALL')")
    @PostMapping(value = "/pause_all")
    public ResponseEntity<Integer> pauseAll(@RequestParam(required = false) List<String> jobClassNameList, @RequestParam(required = false) List<String> jobGroupList) throws BaseResponseException {
        int i = 0;
        for(String j : jobClassNameList) {
            LOGGER.info("jobClassNameList={}={}", j, jobGroupList.get(i));
            i++;
        }
        return new ResponseEntity<>(jobService.pauseAll(jobClassNameList, jobGroupList),HttpStatus.OK);
    }

    /**
     * 指定 JobClassName list、JobGroup list，恢复所有任务
     * @param jobClassNameList JobClassName list
     * @param jobGroupList JobGroup list
     * @return ResponseEntity<Integer>
     * @throws BaseResponseException BaseResponseException
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + JOB + "/RESUME_ALL')")
    @PostMapping(value = "/resume_all")
    public ResponseEntity<Integer> resumeAll(@RequestParam(required = false) List<String> jobClassNameList, @RequestParam(required = false) List<String> jobGroupList) throws BaseResponseException {
        return new ResponseEntity<>(jobService.resumeAll(jobClassNameList, jobGroupList), HttpStatus.OK);
    }

    /**
     * 指定 JobClassName list、JobGroup list，删除所有任务
     * @param jobClassNameList JobClassName list
     * @param jobGroupList JobGroup list
     * @return ResponseEntity<Integer>
     * @throws BaseResponseException BaseResponseException
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + JOB + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam(name = "jobClassName[]") List<String> jobClassNameList, @RequestParam(name = "jobGroup[]") List<String> jobGroupList) throws BaseResponseException {
        return new ResponseEntity<>(jobService.deleteAll(jobClassNameList, jobGroupList),HttpStatus.OK);
    }

    /**
     * 保存任务
     * @param jobClassName JobClassName
     * @param jobGroup JobGroup
     * @param cronExpression Cron 表达式
     * @param description 描述
     * @return ResponseEntity<Date>
     * @throws BaseResponseException BaseResponseException
     */
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + JOB + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<Date> saveOne(@RequestParam String jobClassName, @RequestParam String jobGroup, @RequestParam String cronExpression, @RequestParam String description) throws BaseResponseException {
       return new ResponseEntity<>(jobService.updateCronExpressionByJobClassNameAndJobGroup(jobClassName, jobGroup, cronExpression, description), HttpStatus.OK);
    }

}
