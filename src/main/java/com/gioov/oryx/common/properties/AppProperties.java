package com.gioov.oryx.common.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = true, ignoreInvalidFields = true)
public class AppProperties {

    private String name;
    private String secret;
    private String version;
    private String url;
    private List<String> systemAdminRole = new ArrayList<>(Collections.singletonList("SYSTEM_ADMIN"));
    private String fileUploadPath = "/upload";
    private String[] permitUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getSystemAdminRole() {
        return systemAdminRole;
    }

    public void setSystemAdminRole(List<String> systemAdminRole) {
        this.systemAdminRole = systemAdminRole;
    }

    public String getFileUploadPath() {
        return fileUploadPath;
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public String[] getPermitUrl() {
        return permitUrl;
    }

    public void setPermitUrl(String[] permitUrl) {
        this.permitUrl = permitUrl;
    }
}
