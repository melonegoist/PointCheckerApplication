package ru.melon_egoist.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.mockito.Mockito.*;

class JwtRequestFilterTest {

    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtRequestFilter = new JwtRequestFilter();
        jwtRequestFilter.jwtUtil = jwtUtil;
        jwtRequestFilter.customUserDetailsService = userDetailsService;
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_validToken_authenticationSet() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer valid.token");
        when(jwtUtil.extractUsername("valid.token")).thenReturn("testUser");
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtUtil.validateToken("valid.token", userDetails)).thenReturn(true);
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(userDetailsService).loadUserByUsername("testUser");
        verify(jwtUtil).validateToken("valid.token", userDetails);

        var auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth instanceof UsernamePasswordAuthenticationToken;
        assert auth.getPrincipal().equals(userDetails);
    }

    @Test
    void doFilterInternal_invalidToken_noAuthenticationSet() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid.token");
        when(jwtUtil.extractUsername("invalid.token")).thenReturn("testUser");
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtUtil.validateToken("invalid.token", userDetails)).thenReturn(false);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }

    @Test
    void doFilterInternal_noAuthorizationHeader_noAuthenticationSet() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
