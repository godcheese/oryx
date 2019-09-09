package com.gioov.oryx.common.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-04-03
 */
@Configuration
public class CorsConfiguration {

    /**
     * 使用了 interceptor，只有用此方法或 @Cors 注解才能有效配置 CORS
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final org.springframework.web.cors.CorsConfiguration corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
        // 是否允许请求带有验证信息
        corsConfiguration.setAllowCredentials(true);
        // 允许访问的客户端域名
        corsConfiguration.addAllowedOrigin("*");
        // 允许服务端访问的客户端请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许访问的方法名 GET、POST 等
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

// 此方法在使用 interceptor 时无效
//    /**
//     * 跨域配置
//     * @param registry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")	// 允许跨域访问的路径
//                .allowedOrigins("*")	// 允许跨域访问的源
//                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")	// 允许请求方法
//                .maxAge(168000)	// 预检间隔时间
//                .allowedHeaders("*")  // 允许头部设置
//                .allowCredentials(true);	// 是否发送cookie
//    }

}
