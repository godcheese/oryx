package com.gioov.oryx.common.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@Component
@ConfigurationProperties(prefix = "jwt", ignoreUnknownFields = true, ignoreInvalidFields = true)
public class JwtProperties {

    private String header = "Authorization";

    private String secret = "oryx";

//    private long expiration = 86400;
    private Duration expiration = Duration.ofMinutes(1);

    private String bearerType = "Bearer";

    private String generateTokenPath = "/generate_token";

    private String refreshTokenPath = "/refresh_token";

    private Duration refreshExpiration = Duration.ofMinutes(5);

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Duration getExpiration() {
        return expiration;
    }

    public void setExpiration(Duration expiration) {
        this.expiration = expiration;
    }

    public String getBearerType() {
        return bearerType;
    }

    public void setBearerType(String bearerType) {
        this.bearerType = bearerType;
    }

    public String getGenerateTokenPath() {
        return generateTokenPath;
    }

    public void setGenerateTokenPath(String generateTokenPath) {
        this.generateTokenPath = generateTokenPath;
    }

    public String getRefreshTokenPath() {
        return refreshTokenPath;
    }

    public void setRefreshTokenPath(String refreshTokenPath) {
        this.refreshTokenPath = refreshTokenPath;
    }

    public Duration getRefreshExpiration() {
        return refreshExpiration;
    }

    public void setRefreshExpiration(Duration refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }
}
