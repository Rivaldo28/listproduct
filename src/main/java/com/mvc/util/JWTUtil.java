package com.mvc.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    private String secretKey = "e55bb076d62e266574838d0360cb88c7a26b98743561383347a6598d83f6ef95b7c25e96126de91088febe1c756a58930631f15d8faf6de9c45d96ecf6798124525c7360081c32729e9af05dd2c4bc5a22973dabb23481c1ce9324fc016979173afbbc4a0bfbfc20554259c171c8f7d44f1a3fd397eb9a34b89aa152e3bffc86fe56d95da2792de2143442f1603f5d00ff760a01a12e5836e6ccdacd5070583d0ce7b34fa71ffbe1a1f9643a7c97f3a5af2e76e70385437a704298afcd95137c32bee4cb4558896d2063cae3175a0cee911b863f6291574820b8ceb889b93e5b3e60b224399766b2deac898caeeaac7438ce257dbc230ac61c78032fd18f700a";
    private final String superRole = "ADMIN";

    public JWTUtil(){

    }

    public String generateToken(String role) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .and()
                .signWith(getKey())
                .compact();

    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractRole(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean verifyRole(String jwt, String roleRequest) {
        String role = extractRole(jwt);
        return role != null && (role.equals(roleRequest) || role.equals(superRole));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false; // Token is invalid if any exception occurs during parsing
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
