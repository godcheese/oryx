package com.gioov.oryx.system.mapper;

import com.gioov.tile.mybatis.CrudMapper;
import com.gioov.oryx.system.entity.OperationLogEntity2;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component("operationLogMapper2")
@Mapper
public interface OperationLogMapper2 extends CrudMapper<OperationLogEntity2, Long> {

    /**
     * 分页获取所有操作日志
     * @return Page<OperationLogEntity2>
     */
    Page<OperationLogEntity2> pageAll();

}
