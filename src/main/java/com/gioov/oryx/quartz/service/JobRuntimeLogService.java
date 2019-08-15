package com.gioov.oryx.quartz.service;

import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.quartz.entity.JobRuntimeLogEntity;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-02-13
 */
public interface JobRuntimeLogService {

    /**
     * 记录任务运行日志
     * @param jobExecutionContext JobExecutionContext
     * @param jobExecutionException JobExecutionException
     * @param log 日志
     * @return JobRuntimeLogEntity
     */
    JobRuntimeLogEntity log(JobExecutionContext jobExecutionContext, JobExecutionException jobExecutionException, String log);

    /**
     * 分页获取所有任务运行日志
     * @param page 页
     * @param rows 每页显示数量
     * @return Pagination<JobRuntimeLogEntity>
     */
    Pagination<JobRuntimeLogEntity> pageAll(Integer page, Integer rows);

    /**
     * 清空所有任务运行日志
     */
    void clearAll();

    /**
     * 指定任务运行日志 id，获取任务运行日志
     * @param id 任务运行日志 id
     * @return JobRuntimeLogEntity
     */
    JobRuntimeLogEntity getOne(Long id);

}
