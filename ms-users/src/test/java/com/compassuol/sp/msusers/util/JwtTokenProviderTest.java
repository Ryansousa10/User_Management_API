package com.compassuol.sp.msusers.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.Assert.*;

public class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(jwtTokenProvider, "secret", "your_secret_key");
        ReflectionTestUtils.setField(jwtTokenProvider, "expiration", 3600L); // 1 hour expiration
    }

    @Test
    public void testGenerateToken() {
        String token = jwtTokenProvider.generateToken("testuser");
        assertTrue(token != null && !token.isEmpty());
    }

    @Test
    public void testExtractUsername() {
        String token = jwtTokenProvider.generateToken("testuser");
        String username = jwtTokenProvider.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testIsTokenExpired() {
        String validToken = jwtTokenProvider.generateToken("testuser");
        String expiredToken = generateExpiredToken();

        assertFalse(jwtTokenProvider.isTokenExpired(validToken));
        assertTrue(jwtTokenProvider.isTokenExpired(expiredToken));
    }

    private String generateExpiredToken() {
        Date expirationDate = new Date(System.currentTimeMillis() - 1000); // Set expiration in the past
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withSubject("testuser")
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }
}
