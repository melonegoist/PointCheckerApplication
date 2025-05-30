package ru.melon_egoist.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void securityFilterChain_isLoaded() {
        SecurityFilterChain chain = context.getBean(SecurityFilterChain.class);
        assertNotNull(chain);
    }

    @Test
    void passwordEncoder_isBCrypt() {
        PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
        assertNotNull(encoder);
        assertTrue(encoder instanceof org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder);
    }

    @Test
    void authenticationManager_isLoaded() {
        AuthenticationManager manager = context.getBean(AuthenticationManager.class);
        assertNotNull(manager);
    }
}
