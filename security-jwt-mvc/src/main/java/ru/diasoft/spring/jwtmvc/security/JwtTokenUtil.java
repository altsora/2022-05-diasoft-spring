package ru.diasoft.spring.jwtmvc.security;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.diasoft.spring.jwtmvc.domain.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.lang.String.format;

@Log4j2
@Component
public class JwtTokenUtil {
    private final String jwtSecret;
    private final String jwtIssuer = "dias";
    private final long expSeconds;
    private final long expMinutes;

    public JwtTokenUtil(@Value("${spring.jwt.secret}") String jwtSecret,
                        @Value("${spring.jwt.expiration.seconds}") long seconds,
                        @Value("${spring.jwt.expiration.minutes}") long minutes) {
        this.jwtSecret = jwtSecret;
        this.expSeconds = seconds;
        this.expMinutes = minutes;
    }

    public String generateAccessToken(User user) {
        final Date now = Date.from(
                LocalDateTime.now()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
        final Date exp = Date.from(
                LocalDateTime.now()
                        .plusSeconds(expSeconds)
                        .plusMinutes(expMinutes)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        return Jwts.builder()
                .setSubject(format("%s,%s", user.getId(), user.getUsername()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserId(String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
