package com.gioov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@EnableAsync
@SpringBootApplication
public class NimrodBootstrap extends SpringBootServletInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NimrodBootstrap.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(NimrodBootstrap.class, args);
        Environment environment = configurableApplicationContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port");
        String path = environment.getProperty("server.servlet.context-path");
        LOGGER.info("Nimrod Backend is running here: {}", "http://" + ip + ":" + port + path);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(NimrodBootstrap.class);
    }

}
