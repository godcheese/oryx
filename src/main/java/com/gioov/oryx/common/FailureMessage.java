package com.gioov.oryx.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component
public class FailureMessage implements com.gioov.tile.web.http.FailureMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailureMessage.class);

    private String message;
    private int code;

    @Autowired
    private Common common;

    public FailureMessage i18n(String key) {
        String message = String.valueOf(common.i18n(key + ".message"));
        String code = String.valueOf(common.i18n(key + ".code"));
        int code2 = 0;
        if(code != null && !"".equals(code) && !"null".equals(code)) {
            code2 = Integer.parseInt(code);
        }
        return new FailureMessage(message, code2);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public long getTimestamp() {
        return Instant.now().toEpochMilli();
    }

    public FailureMessage() {
    }

    public FailureMessage(String message, int code) {
        this.message = message;
        this.code = code;
    }

}