package br.com.lucasatolini.attornatus.config;

import br.com.lucasatolini.attornatus.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.KeyPair;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenUtil implements Serializable {

    // APP KEY
    private final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer("localhost:8081")
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(
                                LocalDateTime.now().plusMinutes(15L)
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()))
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS512)
                .compact();
    }

    public Boolean validate(String token) {
        try {
            Jws<Claims> teste = Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPrivate())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(keyPair.getPrivate())
                .build()
                .parseClaimsJws(token);
        return claims.getBody().getSubject();
    }
}
