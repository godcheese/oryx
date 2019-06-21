package com.gioov.nimrodbackend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NimrodBackendApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testPassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       System.out.println(passwordEncoder.encode("123456"));
    }

}
