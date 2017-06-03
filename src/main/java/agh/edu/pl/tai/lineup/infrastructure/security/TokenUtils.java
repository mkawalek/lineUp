package agh.edu.pl.tai.lineup.infrastructure.security;

import agh.edu.pl.tai.lineup.domain.user.TokenAuthenticator;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class TokenUtils implements TokenAuthenticator {

    @Autowired
    private Environment env;

    private final String TOKEN_PREFIX = "Bearer";
    private final long EXPIRATION_TIME = 86400000;
    private String SECRET;

    public String provideToken(UserId userId) {
        return Jwts.builder()
                .setSubject(userId.getValue())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, getSecret())
                .compact();
    }

    public Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSecret())
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
    }

    private String getSecret() {
        return Optional.ofNullable(SECRET).orElseGet(() -> {
            SECRET = env.getProperty("application.secret");
            return SECRET;
        });
    }

}
