package com.compassuol.sp.msusers.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration * 1000);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public <T> T extractClaim(String token, Function<Claim, T> claimsResolver) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token);
        return claimsResolver.apply(decodedJWT.getClaim("sub"));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claim::asString);
    }

    public Boolean isTokenExpired(String token) {
        Date expirationDate = JWT.decode(token).getExpiresAt();
        return expirationDate != null && expirationDate.before(new Date());
    }
}