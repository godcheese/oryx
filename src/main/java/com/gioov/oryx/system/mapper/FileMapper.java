package com.gioov.oryx.system.mapper;

import com.gioov.tile.mybatis.CrudMapper;
import com.gioov.oryx.system.entity.FileEntity;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component("fileMapper")
@Mapper
public interface FileMapper extends CrudMapper<FileEntity, Long> {

    /**
     * 指定 guid，获取文件
     * @param guid guid
     * @return FileEntity
     */
    FileEntity getOneByGuid(@Param("guid") String guid);

    /**
     * 分页获取所有文件
     * @return Page<FileEntity>
     */
    Page<FileEntity> pageAll();

    /**
     * 分页获取所有图片文件
     * @return Page<FileEntity>
     */
    Page<FileEntity> pageAllImage();

}
