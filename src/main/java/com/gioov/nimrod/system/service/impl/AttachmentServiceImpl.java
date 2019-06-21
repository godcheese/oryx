package com.gioov.nimrod.system.service.impl;

import com.gioov.common.mybatis.Pageable;
import com.gioov.common.mybatis.Sort;
import com.gioov.common.util.DataSizeUtil;
import com.gioov.common.util.FileUtil;
import com.gioov.common.web.exception.BaseResponseException;
import com.gioov.nimrod.common.easyui.Pagination;
import com.gioov.nimrod.common.properties.AppProperties;
import com.gioov.nimrod.system.entity.AttachmentEntity;
import com.gioov.nimrod.system.mapper.AttachmentMapper;
import com.gioov.nimrod.system.service.AttachmentService;
import com.gioov.nimrod.system.service.DictionaryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private AppProperties appProperties;

    @Override
    public Pagination<AttachmentEntity> pageAll(Integer page, Integer rows) {
        Pagination<AttachmentEntity> pagination = new Pagination<>();
        PageHelper.startPage(page, rows);
        Page<AttachmentEntity> attachmentEntityPage = attachmentMapper.pageAll();
        pagination.setRows(attachmentEntityPage.getResult());
        pagination.setTotal(attachmentEntityPage.getTotal());
        return pagination;
    }

    /**
     * 文件上传方法
     *
     * @param file
     * @return
     * @throws BaseResponseException
     * @throws IOException
     */
    private AttachmentEntity upload(MultipartFile file) throws BaseResponseException, IOException {
        AttachmentEntity attachmentEntity;
        Date date = new Date();
        attachmentEntity = new AttachmentEntity();
        attachmentEntity.setName(file.getOriginalFilename());
        attachmentEntity.setSize(file.getSize());
        attachmentEntity.setPrettySize(DataSizeUtil.pretty(file.getSize()));
        attachmentEntity.setMimeType(file.getContentType());
        attachmentEntity.setGmtModified(date);
        attachmentEntity.setGmtCreated(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        LOGGER.info("month1={}", calendar.get(Calendar.MONTH));
        LOGGER.info("month2={}", month);

        String datePath = File.separator + year + File.separator + month + File.separator;
        String uploadPath = FileUtil.getCurrentRootPath() + appProperties.getAttachmentUploadPath() + datePath;

        if (!FileUtil.createDirectory(uploadPath)) {
            throw new BaseResponseException("附件上传失败");
        }
        attachmentEntity.setPath(datePath);
        String guid = simpleDateFormat.format(date) + "_" + UUID.randomUUID() + "." + FileUtil.getSuffix(file.getOriginalFilename());
        attachmentEntity.setGuid(guid);
        uploadPath = File.separator + FileUtil.filterFileSeparator(uploadPath + guid);
        File outputFile = new File(uploadPath);
        file.transferTo(outputFile);
        attachmentMapper.insertOne(attachmentEntity);
        return attachmentEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public AttachmentEntity insertOne(MultipartFile file) throws BaseResponseException {
        AttachmentEntity attachmentEntity;
        try {
            attachmentEntity = upload(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseResponseException("附件上传失败");
        }
        return attachmentEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<AttachmentEntity> insertAll(List<MultipartFile> fileList) throws BaseResponseException {
        if (fileList == null || fileList.isEmpty()) {
            throw new BaseResponseException("附件上传失败");
        }
        List<AttachmentEntity> attachmentEntityList = new ArrayList<>();
        try {
            for (MultipartFile file : fileList) {
                attachmentEntityList.add(upload(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseResponseException("附件上传失败");
        }
        return attachmentEntityList.isEmpty() ? null : attachmentEntityList;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public AttachmentEntity updateOne(AttachmentEntity attachmentEntity) {
        AttachmentEntity attachmentEntity1 = attachmentMapper.getOne(attachmentEntity.getId());
        attachmentEntity1.setName(attachmentEntity.getName());
        attachmentEntity1.setRemark(attachmentEntity.getRemark());
        attachmentEntity1.setGmtModified(new Date());
        attachmentMapper.updateOne(attachmentEntity1);
        return attachmentEntity1;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteAll(List<Long> idList) {
        int result = 0;

        for (Long id : idList) {
            AttachmentEntity attachmentEntity = attachmentMapper.getOne(id);

            if (attachmentEntity != null) {
                String uploadPath = appProperties.getAttachmentUploadPath();
                String filename = File.separator + FileUtil.filterFileSeparator(FileUtil.getCurrentRootPath() + uploadPath + attachmentEntity.getPath() + attachmentEntity.getGuid());
                FileUtil.delete(new File(filename));
                attachmentMapper.deleteOne(id);
            }
            result++;
        }
        return result;
    }

    @Override
    public AttachmentEntity getOne(Long id) {
        return attachmentMapper.getOne(id);
    }

    @Override
    public void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String guid) throws BaseResponseException {
        AttachmentEntity attachmentEntity = attachmentMapper.getOneByGuid(guid);
//        String uploadPath = (String) dictionaryService.get("ATTACHMENT", "UPLOAD_PATH");
        String uploadPath = appProperties.getAttachmentUploadPath();
        String absolutePath = File.separator + FileUtil.filterFileSeparator(FileUtil.getCurrentRootPath() + uploadPath + attachmentEntity.getPath() + attachmentEntity.getGuid());
        File absoluteFile = new File(absolutePath);
        try {
            FileUtil.download(httpServletRequest, httpServletResponse, attachmentEntity.getMimeType(), attachmentEntity.getName(), absoluteFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseResponseException("文件下载失败");
        }
    }

    public static void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String filename, File absoluteFile) throws IOException {
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0L);
//        httpServletResponse.setContentType("application/octet-stream;charset=UTF-8");
        httpServletResponse.setContentType("image/jpeg;charset=UTF-8");
        filename = avoidChineseMessyCode(httpServletRequest, filename);
        httpServletResponse.setContentLengthLong(absoluteFile.length());
        httpServletResponse.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));
        byte[] bytes = new byte[1024];

        try {
            FileInputStream fileInputStream = new FileInputStream(absoluteFile);
            Throwable var7 = null;

            try {
                OutputStream outputStream = httpServletResponse.getOutputStream();
                Throwable var9 = null;

                try {
                    int len;
                    try {
                        while((len = fileInputStream.read(bytes)) > 0) {
                            outputStream.write(bytes, 0, len);
                        }
                    } catch (Throwable var34) {
                        var9 = var34;
                        throw var34;
                    }
                } finally {
                    if (outputStream != null) {
                        if (var9 != null) {
                            try {
                                outputStream.close();
                            } catch (Throwable var33) {
                                var9.addSuppressed(var33);
                            }
                        } else {
                            outputStream.close();
                        }
                    }

                }
            } catch (Throwable var36) {
                var7 = var36;
                throw var36;
            } finally {
                if (fileInputStream != null) {
                    if (var7 != null) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable var32) {
                            var7.addSuppressed(var32);
                        }
                    } else {
                        fileInputStream.close();
                    }
                }

            }
        } catch (IOException var38) {
            var38.printStackTrace();
        }

    }

    public static String avoidChineseMessyCode(HttpServletRequest httpServletRequest, String filename) throws UnsupportedEncodingException {
        String agent = httpServletRequest.getHeader("User-Agent");
        if (agent != null && agent.toLowerCase().contains("msie")) {
            filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.name());
        } else {
            filename = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }

        filename = filename.replaceAll("\\+", "%20");
        return filename;
    }

    @Override
    public Pagination<AttachmentEntity> pageAllImage(Integer page, Integer rows) {
        Pagination<AttachmentEntity> pagination = new Pagination<>();
        PageHelper.startPage(page, rows);
        Page<AttachmentEntity> attachmentEntityPage = attachmentMapper.pageAllImage();
        pagination.setRows(attachmentEntityPage.getResult());
        pagination.setTotal(attachmentEntityPage.getTotal());
        return pagination;
    }

}
