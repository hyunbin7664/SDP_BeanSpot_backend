package com.beanspot.backend.security;

import com.beanspot.backend.entity.User;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.token-validity-in-seconds}")
    private long validityInSeconds;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityInSeconds;

    @Value("${jwt.allowed-clock-skew-seconds}")
    private long allowedClockSkewSeconds;

    private JwtParser parser;

    @PostConstruct
    void init(){
        this.parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .setAllowedClockSkewSeconds(allowedClockSkewSeconds)
                .requireIssuer(issuer)
                .build();
    }

    public String createAccessToken(User user) {
        Date expiryDate = Date.from(Instant.now().plusSeconds(validityInSeconds));
        return Jwts.builder()
                .signWith( SignatureAlgorithm.HS512, secretKey)
                .setIssuer("beanspot")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate) // exp
                .setSubject(user.getId().toString())
                .claim("userId", user.getUserId())
                .claim("name", user.getNickname())
                .claim("type", "access")
                .compact();
    }

    public String createRefreshToken(User user) {
        Date expiryDate = Date.from(Instant.now().plusSeconds(validityInSeconds));
        return Jwts.builder()
                .signWith( SignatureAlgorithm.HS512, secretKey)
                .setIssuer("beanspot")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate) // exp
                .setSubject(user.getId().toString())
                .claim("type", "refresh")
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getAllClaims(token);
        return Long.parseLong(claims.getSubject());

    }

    public String getTokenType(String token) {
        Claims claims = getAllClaims(token);
        Object t = claims.get("type");
        return t != null ? t.toString() : null;
    }

    public String getEmailIfPresent(String token) {
        Claims claims = getAllClaims(token);
        Object v = claims.get("email");
        return v != null ? v.toString() : null;
    }

    private Claims getAllClaims(String token) {
        return parser.parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰 없거나 잘못되었습니다.");
        }
        return false;
    }

}
