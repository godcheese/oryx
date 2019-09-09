package com.gioov.oryx.common.jwt;

import com.gioov.tile.web.exception.BaseResponseException;
import com.gioov.tile.web.http.FailureEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-08-28
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String bearerToken = httpServletRequest.getHeader(jwtProperties.getHeader());
        String username = null;
        String token = null;
        String bearer = jwtProperties.getBearerType() + " ";
        if (bearerToken != null && bearerToken.startsWith(bearer)) {
            token = bearerToken.substring(bearer.length());
            username = jwtUtils.getUsernameFromToken(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            JwtUserDetails userDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(username);
            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
            // the database compellingly. Again it's up to you ;)
            if (jwtUtils.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        LOGGER.info("getRequestURL={}", httpServletRequest.getRequestURL());
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @ExceptionHandler({BaseResponseException.class})
    public ResponseEntity<FailureEntity> defaultExceptionHandler(HttpServletRequest httpServletRequest, Throwable throwable) {
        HttpStatus httpStatus = getStatus(httpServletRequest);
        throwable.printStackTrace();
        String message = throwable.getMessage();
        int code = 0;
//        if(throwable instanceof BaseResponseException) {
//            code = ((BaseResponseException) throwable).getCode();
//        }
        LOGGER.info("throwable={}", throwable.getMessage());
        return new ResponseEntity<>(new FailureEntity(message, code), httpStatus);
    }

    public static HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }

}
