package com.gioov;

import com.gioov.oryx.common.Common;
import com.gioov.tile.util.ClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

    private static final String ORYX_VERSION = "0.2.0";
    private static final String ORYX_HOMEPAGE = "https://github.com/godcheese/oryx";

    private static final Logger LOGGER = LoggerFactory.getLogger(OryxBootstrap.class);

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(OryxBootstrap.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        Environment environment = configurableApplicationContext.getEnvironment();
        String ip = null;
        try {
            InetAddress inetAddress = ClientUtil.getLocalHostLANAddress();
            ip = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String port = environment.getProperty("server.port");
        String path = environment.getProperty("server.servlet.context-path");
        port = port != null ?  ":" + port : "";
        path = path != null ? path : "";

        String local = "http://localhost" + port + path + "/";
        String network = "http://" + ip + port + path + "/";
        if(ip == null) {
            network = "unavailable";
        }
        LOGGER.info("\n\n" +
                "    ______   .______     ____    ____ ___   ___\n" +
                "   /  __  \\  |   _  \\    \\   \\  /   / \\  \\ /  /\n" +
                "  |  |  |  | |  |_)  |    \\   \\/   /   \\  V  /\n" +
                "  |  |  |  | |      /      \\_    _/     >   <\n" +
                "  |  `--'  | |  |\\  \\----.   |  |      /  .  \\\n" +
                "   \\______/  | _| `._____|   |__|     /__/ \\__\\\n" +
                "\n  -----------------------------------------------" +
                        "\n  | Oryx version: " + ORYX_VERSION + "                         |" +
                        "\n  | Homepage: " + ORYX_HOMEPAGE +" |" +
                        "\n  -----------------------------------------------"  +
                        "\n\n  App running at:" +
                        "\n  - Local:   "+ local +
                        "\n  - Network: " + network
        );
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OryxBootstrap.class);
    }

    @Order
    @Component
    public static class ApplicationStartupRunner implements CommandLineRunner {
        @Autowired
        private Common common;
        @Override
        public void run(String... strings) {
            common.initialize();
        }

    }
}
