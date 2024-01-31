package com.online.pasaronlineapp.config;

import com.online.pasaronlineapp.domain.dao.PembeliDao;
import com.online.pasaronlineapp.domain.dao.PembeliDetailDao;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        PembeliDetailDao pembeliDetailDao = (PembeliDetailDao) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(pembeliDetailDao.getPembeliDao().getPhoneNumber())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Expired Jwt Token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported Jwt Token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid Jwt Token: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("Invalid Jwt Signature: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Jwt Claim is empty: {}", e.getMessage());
        }
        return false;
    }
}
