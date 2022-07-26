package com.kob.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("pyxr"));
        System.out.println(passwordEncoder.matches("pyxr","$2a$10$EG/1EFzxpgLNqk9Z.WTVXeXOpPGcY8TBtpRlXY4HExbfQWxMm96R6"));
        System.out.println(passwordEncoder.encode("pa"));
        System.out.println(passwordEncoder.encode("pc"));
    }

}
