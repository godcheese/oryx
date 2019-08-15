package com.gioov;

import com.gioov.tile.util.ClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *  Oryx 启动类
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-22
 */
@EnableAsync
@SpringBootApplication
public class OryxBootstrap extends SpringBootServletInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OryxBootstrap.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(OryxBootstrap.class, args);
        Environment environment = configurableApplicationContext.getEnvironment();
        String ip = ClientUtil.getLocalHostLANAddress().getHostAddress();
        String port = environment.getProperty("server.port");
        String path = environment.getProperty("server.servlet.context-path");
        port = port != null ?  ":" + port : "";
        path = path != null ? path : "";
        LOGGER.info(
                "\n\n  App running at:\n" +
                        "  - Local:   http://localhost" + port + path + "/\n" +
                        "  - Network: http://" + ip + port + path + "/\n"
        );
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OryxBootstrap.class);
    }

    @Order
    @Component
    public class ApplicationStartupRunner implements CommandLineRunner {
        @Autowired
        private com.gioov.oryx.common.Common common;
        @Override
        public void run(String... strings) {
            common.initialize();
        }
    }

}
