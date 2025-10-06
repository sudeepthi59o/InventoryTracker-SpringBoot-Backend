package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    private String secretKey = null;

    public String generateToken(User user) {
        log.info("Inside generateToken for user {}",user.getUserName());
        Map<String,Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUserName())
                .issuer("Inventory Tracker Application")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 10*60*1000))
                .and()
                .signWith(generateKey())
                .compact();
    }

    public SecretKey generateKey() {
        byte[] decode = Decoders.BASE64.decode(getSecretKey());

        return Keys.hmacShaKeyFor(decode);
    }

    public String getSecretKey() {
        return "c0a872e234f06a72de25b8965ed7db3b0aaa919639c6be663526a9b96bda85ec";
    }

    public String extractUsername(String token) {
        log.info("Inside extractUsername for token {}",token);
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        Claims claims = extractClaim(token);
        log.info("Extracted claims: {}",claims);
        return claimsResolver.apply(claims);

    }

    private Claims extractClaim(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        log.info("Check if token is valid: {}",token);
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
