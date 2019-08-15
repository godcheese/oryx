package com.gioov.oryx.common.security;

import com.gioov.tile.web.http.FailureEntity;
import com.gioov.oryx.common.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
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
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationEntryPoint.class);

    @Autowired
    private Common common;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter printWriter = httpServletResponse.getWriter();
        e.printStackTrace();
        printWriter.write(common.objectToJson(new FailureEntity(e.getMessage(), httpServletResponse.getStatus())));
        printWriter.flush();
        printWriter.close();
    }

}
