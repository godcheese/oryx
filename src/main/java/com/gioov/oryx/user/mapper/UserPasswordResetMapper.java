package com.gioov.oryx.user.mapper;

import com.gioov.tile.mybatis.CrudMapper;
import com.gioov.oryx.user.entity.UserPasswordResetEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component("userPasswordResetMapper")
@Mapper
public interface UserPasswordResetMapper extends CrudMapper<UserPasswordResetEntity, Long> {

}
