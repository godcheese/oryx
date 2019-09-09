package com.gioov.oryx.common.jwt;

import com.alibaba.druid.sql.parser.Lexer;
import com.gioov.oryx.common.FailureMessage;
import com.gioov.oryx.system.service.DictionaryService;
import com.gioov.oryx.user.entity.UserEntity;
import com.gioov.oryx.user.service.DepartmentService;
import com.gioov.oryx.user.service.UserService;
import com.gioov.oryx.user.service.ViewMenuService;
import com.gioov.tile.web.exception.BaseResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-08-29
 */
@RestController
public class JwtRestController {

    private Logger LOGGER = LoggerFactory.getLogger(JwtRestController.class);

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @Autowired
    private FailureMessage failureMessage;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ViewMenuService viewMenuService;

    @Autowired
    private JwtProperties jwtProperties;

    @RequestMapping("${jwt.generate-token-path}")
    public ResponseEntity<Map<String, Object>> generateToken(@RequestParam String username, @RequestParam String password) throws BaseResponseException {
        final JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(username);
        final UserEntity userEntity = userDetailsService.getUserEntity();
        if(jwtUserDetails == null) {
            throw new BaseResponseException(failureMessage.i18n("jwt.login_fail_account_or_password_error"));
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(password, jwtUserDetails.getPassword())) {
            throw new BaseResponseException(failureMessage.i18n("jwt.login_fail_account_or_password_error"));
        }

        if(userEntity.getGmtDeleted() != null) {
            throw new BaseResponseException(failureMessage.i18n("jwt.login_fail_account_deleted"));
        }

        Integer isOrNotIs = Integer.parseInt((String) dictionaryService.get("IS_OR_NOT", "IS"));
        if(!userEntity.getEnabled().equals(isOrNotIs)){
            throw new BaseResponseException(failureMessage.i18n("jwt.login_fail_account_is_not_enable"));
        }
        // 生成 token
        final String token = jwtUtils.generateToken(jwtUserDetails);
        Map<String, Object> map = new HashMap<>(1);
        map.put("token", token);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping("${jwt.refresh-token-path}")
    public ResponseEntity<Map<String, Object>> refreshToken(HttpServletRequest httpServletRequest) throws BaseResponseException {
        final String bearerToken = httpServletRequest.getHeader(jwtProperties.getHeader());
        String token = null;
        String username = null;
        String bearer = jwtProperties.getBearerType() + " ";
        if (bearerToken != null && bearerToken.startsWith(bearer)) {
            token = bearerToken.substring(bearer.length());
            username = jwtUtils.getUsernameFromToken(token);
        }
        JwtUserDetails userDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(username);
        Map<String, Object> map = new HashMap<>(1);
        map.put("token", null);
        if(jwtUtils.canTokenBeRefreshed(token, userDetails.getGmtPasswordLastModified())) {
            // 生成 token
            token = jwtUtils.refreshToken(token);
            map.put("token", token);
        }
        LOGGER.info("{}", map);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
