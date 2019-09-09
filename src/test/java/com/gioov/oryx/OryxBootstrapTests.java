package com.gioov.oryx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Properties;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class OryxBootstrapTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testPassword() {

//        ClassPathResource classPathResource = new ClassPathResource("classpath:i18n/zh_cn.properties");
//        Properties properties = new Properties();
//        try {
//            properties.load(classPathResource.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(properties.get("user.login_fail_account_or_password_error.message"));


    }

}
