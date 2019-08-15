package com.gioov.oryx.system.service.impl;

import com.gioov.tile.util.DataSizeUtil;
import com.gioov.tile.util.FileUtil;
import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.properties.AppProperties;
import com.gioov.oryx.system.entity.FileEntity;
import com.gioov.oryx.system.mapper.FileMapper;
import com.gioov.oryx.system.service.DictionaryService;
import com.gioov.oryx.system.service.FileService;
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
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private AppProperties appProperties;

    @Override
    public Pagination<FileEntity> pageAll(Integer page, Integer rows) {
        Pagination<FileEntity> pagination = new Pagination<>();
        PageHelper.startPage(page, rows);
        Page<FileEntity> fileEntityPage = fileMapper.pageAll();
        pagination.setRows(fileEntityPage.getResult());
        pagination.setTotal(fileEntityPage.getTotal());
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
    private FileEntity upload(MultipartFile file) throws BaseResponseException, IOException {
        FileEntity fileEntity;
        Date date = new Date();
        fileEntity = new FileEntity();
        fileEntity.setName(file.getOriginalFilename());
        fileEntity.setSize(file.getSize());
        fileEntity.setPrettySize(DataSizeUtil.pretty(file.getSize()));
        fileEntity.setMimeType(file.getContentType());
        fileEntity.setGmtModified(date);
        fileEntity.setGmtCreated(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        LOGGER.info("month1={}", calendar.get(Calendar.MONTH));
        LOGGER.info("month2={}", month);

        String datePath = File.separator + year + File.separator + month + File.separator;
        String uploadPath = FileUtil.getCurrentRootPath() + appProperties.getFileUploadPath() + datePath;

        if (!FileUtil.createDirectory(uploadPath)) {
            throw new BaseResponseException("文件上传失败");
        }
        fileEntity.setPath(datePath);
        String guid = simpleDateFormat.format(date) + "_" + UUID.randomUUID() + "." + FileUtil.getSuffix(file.getOriginalFilename());
        fileEntity.setGuid(guid);
        uploadPath = File.separator + FileUtil.filterFileSeparator(uploadPath + guid);
        File outputFile = new File(uploadPath);
        file.transferTo(outputFile);
        fileMapper.insertOne(fileEntity);
        return fileEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public FileEntity uploadOne(MultipartFile file) throws BaseResponseException {
        FileEntity fileEntity;
        try {
            fileEntity = upload(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseResponseException("文件上传失败");
        }
        return fileEntity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<FileEntity> uploadAll(List<MultipartFile> fileList) throws BaseResponseException {
        if (fileList == null || fileList.isEmpty()) {
            throw new BaseResponseException("文件上传失败");
        }
        List<FileEntity> fileEntityList = new ArrayList<>();
        try {
            for (MultipartFile file : fileList) {
                fileEntityList.add(upload(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseResponseException("文件上传失败");
        }
        return fileEntityList.isEmpty() ? null : fileEntityList;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public FileEntity saveOne(FileEntity fileEntity) {
        FileEntity fileEntity1 = fileMapper.getOne(fileEntity.getId());
        fileEntity1.setName(fileEntity.getName());
        fileEntity1.setRemark(fileEntity.getRemark());
        fileEntity1.setGmtModified(new Date());
        fileMapper.updateOne(fileEntity1);
        return fileEntity1;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteAll(List<Long> idList) {
        int result = 0;

        for (Long id : idList) {
            FileEntity fileEntity = fileMapper.getOne(id);

            if (fileEntity != null) {
                String uploadPath = appProperties.getFileUploadPath();
                String filename = File.separator + FileUtil.filterFileSeparator(FileUtil.getCurrentRootPath() + uploadPath + fileEntity.getPath() + fileEntity.getGuid());
                FileUtil.delete(new File(filename));
                fileMapper.deleteOne(id);
            }
            result++;
        }
        return result;
    }

    @Override
    public FileEntity getOne(Long id) {
        return fileMapper.getOne(id);
    }

    @Override
    public void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String guid) throws BaseResponseException {
        FileEntity fileEntity = fileMapper.getOneByGuid(guid);
//        String uploadPath = (String) dictionaryService.get("FILE", "UPLOAD_PATH");
            if(fileEntity == null) {
                throw new BaseResponseException("文件不存在，文件下载失败");
            }
        String uploadPath = appProperties.getFileUploadPath();
        String absolutePath = File.separator + FileUtil.filterFileSeparator(FileUtil.getCurrentRootPath() + uploadPath + fileEntity.getPath() + fileEntity.getGuid());
        File absoluteFile = new File(absolutePath);
        try {
            FileUtil.download(httpServletRequest, httpServletResponse, fileEntity.getMimeType(), fileEntity.getName(), absoluteFile);
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
        httpServletResponse.setHeader("Content-Disposition", String.format("file; filename=\"%s\"", filename));
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
        String msie = "msie";
        if (agent != null && agent.toLowerCase().contains(msie)) {
            filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.name());
        } else {
            filename = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }

        filename = filename.replaceAll("\\+", "%20");
        return filename;
    }

    @Override
    public Pagination<FileEntity> pageAllImage(Integer page, Integer rows) {
        Pagination<FileEntity> pagination = new Pagination<>();
        PageHelper.startPage(page, rows);
        Page<FileEntity> fileEntityPage = fileMapper.pageAllImage();
        pagination.setRows(fileEntityPage.getResult());
        pagination.setTotal(fileEntityPage.getTotal());
        return pagination;
    }

}
