package ru.melon_egoist.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String secretKey = "test-secret";
    private final int expirationTime = 1000 * 60 * 60; // 1 hour

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        jwtUtil.SECRET_KEY = secretKey;
        jwtUtil.EXPIRATION_TIME = expirationTime;
    }

    @Test
    void generateTokenAndExtractUsername_success() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        String token = jwtUtil.generateToken(userDetails);

        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals("testuser", extractedUsername);
    }

    @Test
    void validateToken_validToken_returnsTrue() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        String token = jwtUtil.generateToken(userDetails);

        boolean isValid = jwtUtil.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void validateToken_expiredToken_returnsFalse() {
        String username = "expireduser";
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() - 10000); // already expired

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);

        boolean isValid = jwtUtil.validateToken(token, userDetails);
        assertFalse(isValid);
    }

    @Test
    void extractExpiration_returnsCorrectExpiration() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        String token = jwtUtil.generateToken(userDetails);
        Date expiration = jwtUtil.extractExpiration(token);

        assertTrue(expiration.after(new Date()));
    }
}
