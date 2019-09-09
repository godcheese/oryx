package com.gioov.oryx.common.controller;

import com.gioov.tile.web.exception.BaseResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-07-18
 */
@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", 0);
        if(e instanceof BaseResponseException) {
            map.put("code", ((BaseResponseException) e).getCode());
        }
        map.put("message", e.getMessage());
        map.put("timestamp", Instant.now().toEpochMilli());
        mv.addAllObjects(map);
        e.printStackTrace();
        return mv;
    }

}
