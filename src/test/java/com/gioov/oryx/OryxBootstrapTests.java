package com.gioov.oryx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OryxBootstrapTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testPassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       System.out.println(passwordEncoder.encode("123456"));
        long begin = Instant.now().toEpochMilli();

        System.out.println(begin);
        System.out.println(Instant.now().toEpochMilli() - begin);

    }

}
