package com.HNGInternship.HNGAuth;

import com.HNGInternship.HNGAuth.config.JwtService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {
    private final JwtService jwtService = new JwtService();

    @Test
    void testGenerateToken() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername()).thenReturn("testuser");

        String token = jwtService.generateToken(userDetails);
        Claims claims = jwtService.extractAllClaims(token);

        assertEquals("testuser", claims.getSubject());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void testTokenExpiration() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername()).thenReturn("testuser");

        String token = jwtService.generateToken(userDetails);
        Claims claims = jwtService.extractAllClaims(token);

        Date expiration = claims.getExpiration();
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void testExtractUsername() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername()).thenReturn("testuser");

        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);

        assertEquals("testuser", username);
    }
}
