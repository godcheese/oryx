package com.gioov.nimrod.system.mapper;

import com.gioov.common.mybatis.CrudMapper;
import com.gioov.nimrod.system.entity.AttachmentEntity;
import com.gioov.nimrod.system.entity.OperationLogEntity;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component("operationLogMapper")
@Mapper
public interface OperationLogMapper extends CrudMapper<OperationLogEntity, Long> {
    Page<OperationLogEntity> pageAll();
}
