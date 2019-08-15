package com.gioov.oryx.common.security;

import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.gioov.oryx.common.properties.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-03-13
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServerConfiguration.class);

    private static final String RESOURCE_ID = "resource_api";

    @Autowired
    private DruidStatProperties druidStatProperties;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AppProperties appProperties;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true).authenticationEntryPoint(new AuthenticationEntryPoint()).accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        // Since we want the protected resources to be accessible in the UI as well we need
        // session creation to be allowed (it's disabled by default in 2.0.6)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 解决 Refused to display 'http://127.0.0.1:8083/oryx/druid/index.html' in a frame because it set 'X-Frame-Options' to 'deny'.
        http.headers().frameOptions().disable();

        // Druid 需要权限或者系统管理员角色才能访问
        String druidUrlPattern = druidStatProperties.getStatViewServlet().getUrlPattern();
//        http.authorizeRequests().antMatchers(druidStatProperties.getStatViewServlet().getUrlPattern()).hasAnyAuthority(SimpleUserDetailsServiceImpl.ROLE_PREFIX + SYSTEM_ADMIN, druidUrlPattern.substring(0, druidUrlPattern.length() - 2).toUpperCase());
        http.authorizeRequests().antMatchers(druidStatProperties.getStatViewServlet().getUrlPattern()).anonymous();

//        http.authorizeRequests().antMatchers("/api/system/system_info").hasAnyAuthority("ROLE_ANONYMOUS");

        http.csrf().disable().anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/*").permitAll()
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);
        // @formatter:on

    }

}
