package com.gioov.oryx.system.api;

import com.gioov.tile.util.ColorUtil;
import com.gioov.tile.util.ImageUtil;
import com.gioov.tile.util.RandomUtil;
import com.gioov.tile.util.ResourceUtil;
import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.system.System;
import com.gioov.oryx.system.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.gioov.oryx.user.service.UserService.SYSTEM_ADMIN;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@RestController
@RequestMapping(value = System.Api.SYSTEM, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SystemRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemRestController.class);

    private static final String SYSTEM = "/API/SYSTEM";

    public static final String VERIFY_CODE_NAME = "verifyCode";

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 获取验证码
     * @param httpServletResponse HttpServletResponse
     * @param httpServletRequest HttpServletRequest
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "获取验证码", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + SYSTEM + "/VERIFY_CODE')")
    @GetMapping(value = "/verify_code")
    public void verifyCode(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws BaseResponseException {
        long expireIn = Long.parseLong((String) dictionaryService.get("VERIFY_CODE", "EXPIRE_IN"));
        boolean yawp = Boolean.parseBoolean((String) dictionaryService.get("VERIFY_CODE", "YAWP"));
        int stringLength = Integer.parseInt((String) dictionaryService.get("VERIFY_CODE", "STRING_LENGTH"));
        int interLine = Integer.parseInt((String) dictionaryService.get("VERIFY_CODE", "INTER_LINE"));
        String hexBackgroundColor = String.valueOf(dictionaryService.get("VERIFY_CODE", "HEX_BACKGROUND_COLOR"));
        stringLength = (stringLength >= 3 && stringLength <= 8) ? stringLength : 4;
        interLine = (interLine >= 1 && interLine <= 8) ? interLine : 0;
        expireIn = (expireIn >= 20) ? expireIn : 60;
        hexBackgroundColor = hexBackgroundColor.length() == 7 ? hexBackgroundColor : "#147cd3";
        ImageUtil.VerifyCodeImage verifyCodeImage;
        try {
            URL url =  ResourceUtil.getResource("/fonts/Arial.ttf");
            File file = new File(url.getFile());
            if(!file.exists()) {
                throw new BaseResponseException("字体文件不存在");
            }
            verifyCodeImage = ImageUtil.createVerifyCodeImage(114, 40, ColorUtil.getRGBColorByHexString(hexBackgroundColor), RandomUtil.randomString(stringLength, RandomUtil.NUMBER_LETTER), Color.WHITE, file, yawp, interLine, expireIn);

            httpServletResponse.addHeader("Pragma", "no-cache");
            httpServletResponse.addHeader("Cache-Control", "no-cache");
            httpServletResponse.addHeader("Expires", "0");
            // 生成验证码，写入用户session
            httpServletRequest.getSession().setAttribute(VERIFY_CODE_NAME, verifyCodeImage);
            // 输出验证码给客户端
            httpServletResponse.setContentType("image/jpeg");
            ImageIO.write(verifyCodeImage.getBufferedImage(), "jpg", httpServletResponse.getOutputStream());

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            throw new BaseResponseException("验证码生成发生错误");
        }

    }

    /**
     * 获取系统信息
     * @param httpServletResponse HttpServletResponse
     * @param httpServletRequest HttpServletRequest
     * @return ResponseEntity<Map<String, String>>
     */

    @OperationLog(value = "获取系统信息", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + SYSTEM + "/SYSTEM_INFO')")
    @GetMapping(value = "/system_info")
    public ResponseEntity<Map<String, String>> systemInfo(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        Map<String, String> map = new HashMap<>(1);
        map.put("osName", java.lang.System.getProperty("os.name"));
        map.put("osVersion", java.lang.System.getProperty("os.version"));
        map.put("osArch", java.lang.System.getProperty("os.arch"));
        map.put("javaHome", java.lang.System.getProperty("java.home"));
        map.put("javaVersion", java.lang.System.getProperty("java.version"));

        /**
         *
         *  "user.timezone": "Asia/Shanghai",
         *     "os.name": "Mac OS X",
         *      "os.version": "10.14.5",
         *      "os.arch": "x86_64",
         *     "java.home": "/Library/Java/JavaVirtualMachines/jdk1.8.0_192.jdk/Contents/Home/jre",
         *           "java.version": "1.8.0_192",
         */

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
