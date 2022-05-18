package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.GenericUserDto;
import com.novavrbe.vrbe.repositories.impl.UserRepositoryService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {
    //TODO capire come prendere questo
    private String SECRET_KEY = "c5a9ebe34ae2a07f3ce3bed3f01c784205327421ff7353f10206964b6263d218";

    UserRepositoryService userRepositoryService;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date  extractIssuedAt(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        Date exp = extractExpiration(token);
        return exp.before(new Date());
    }

    public String generateToken(GenericUserDto userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, GenericUserDto subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject.getEmail()).setIssuedAt(new Date(subject.getLastlogin()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12 )) //TODO file di configurazione
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, GenericUserDto userDetails) {
        final String username = extractUsername(token);

        boolean expired = isTokenExpired(token);
        Date tokenIssuedAt = extractIssuedAt(token);
        long issuedAt = tokenIssuedAt.getTime();
        long lLog = userDetails.getLastlogin();
        Date lastLogin = new Date(lLog);
        return (username.equals(userDetails.getEmail()) && !expired && lastLogin.equals(tokenIssuedAt) );
    }
}
