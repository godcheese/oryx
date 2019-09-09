package com.gioov.oryx.common.jwt;

import com.alibaba.druid.sql.parser.Lexer;
import com.gioov.tile.util.DateUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-08-28
 */
@Component
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    private static final String REFRESH_DATE_CLAIM_NAME = "refreshDate";

    @Autowired
    private JwtProperties jwtProperties;

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public String getUsernameFromToken(String token) {
       String subject = null;
        try {
            subject = getClaimsFromToken(token).getSubject();
        } catch (ExpiredJwtException e) {
            LOGGER.info("{}", e.getClaims());
            subject = e.getClaims().getSubject();
        }
        return subject;
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    /**
     * 校验 token 是否过期，token 有效时间小于当前时间则返回 true
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        final Date expirationDate = getClaimsFromToken(token).getExpiration();
        return expirationDate.before(new Date());
    }

    /**
     * 校验刷新 token 是否过期，刷新 token 有效时间小于当前时间则返回 true
     * @param token
     * @return
     */
    private boolean isRefreshExpired(String token) {
        Date expirationDate;
        try {
            expirationDate = new Date((Long) getClaimsFromToken(token).get(REFRESH_DATE_CLAIM_NAME));
        } catch (ExpiredJwtException e) {
            expirationDate = new Date((Long) e.getClaims().get(REFRESH_DATE_CLAIM_NAME));
        }
        return expirationDate.before(new Date());
    }

    /**
     * 判断 token 创建时间小于密码最后修改时间则返回 true
     * @param createdDate
     * @param passwordLastModified
     * @return
     */
    private boolean isCreatedBeforePasswordLastModified(Date createdDate, Date passwordLastModified) {
        return passwordLastModified != null && createdDate != null && createdDate.before(passwordLastModified);
    }

    /**
     * 忽略例外 token
     * @param token
     * @return
     */
    private boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    /**
     * 生成 token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(6);
        final Date createdDate = new Date();
        claims.put(REFRESH_DATE_CLAIM_NAME, calculateRefreshExpirationDate(createdDate));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(createdDate)
                .setExpiration(calculateExpirationDate(createdDate))
                .signWith(SIGNATURE_ALGORITHM, jwtProperties.getSecret())
                .compact();
    }

    /**
     * 校验 token 是否可以刷新，token 创建时间大于密码最后修改时间且刷新 token 的有效时间大于当前时间，则返回 true
     * @param token
     * @param passwordLastModified
     * @return
     */
    public boolean canTokenBeRefreshed(String token, Date passwordLastModified) {
        Date createdDate = null;
        try {
            createdDate = getClaimsFromToken(token).getIssuedAt();
        } catch (ExpiredJwtException e) {
            createdDate = e.getClaims().getIssuedAt();
        }
        return !isCreatedBeforePasswordLastModified(createdDate, passwordLastModified) && (!isRefreshExpired(token) || ignoreTokenExpiration(token));
    }

    /**
     * 根据旧 token 来刷新获取新 token
     * @param token 旧 token
     * @return 新 token
     */
    public String refreshToken(String token) {
        final Date createdDate = new Date();
        Claims claims;
        try {
            claims = getClaimsFromToken(token);
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        claims.setIssuedAt(createdDate);
        claims.setExpiration(calculateExpirationDate(createdDate));
        claims.put(REFRESH_DATE_CLAIM_NAME, calculateRefreshExpirationDate(createdDate));
        return Jwts.builder().setClaims(claims).signWith(SIGNATURE_ALGORITHM, jwtProperties.getSecret()).compact();
    }

    /**
     * 校验 token 是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            JwtUserDetails user = (JwtUserDetails) userDetails;
            final Date createdDate = getClaimsFromToken(token).getIssuedAt();
            // 如果 token 存在，且 token 创建日期 > 最后修改密码的日期 则代表 token 有效
            return !isTokenExpired(token) && !isCreatedBeforePasswordLastModified(createdDate, user.getGmtPasswordLastModified());
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 计算得出 token 有效时间
     * @param createdDate
     * @return
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + jwtProperties.getExpiration().toMillis());
    }

    /**
     * 计算得出刷新 token 有效时间
     * @param createdDate
     * @return
     */
    private Date calculateRefreshExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + jwtProperties.getRefreshExpiration().toMillis());
    }

}
