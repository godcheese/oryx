package com.gioov.nimrod.common.security;

import com.gioov.common.web.http.FailureEntity;
import com.gioov.nimrod.common.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-04-25
 */
@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(AccessDeniedHandler.class);

    @Autowired
    private Common common;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter printWriter = httpServletResponse.getWriter();
        e.printStackTrace();
        printWriter.write(common.objectToJson(new FailureEntity(e.getMessage(), httpServletResponse.getStatus())));
        printWriter.flush();
        printWriter.close();
    }
}
