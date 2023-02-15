package com.hcc.utils;

import com.hcc.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {
    public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 24;

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromTokey(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJwt(token)
                .getBody();
    }

    //check if the token is expired...
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims:: getExpiration);
    }

    public boolean isTokenExpired (String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate the token
    public String generateToken(User user) {
        return doGenerateToken(user.getUsername());
    }

    //TODO: admin can be added here later, possibly need to make if statements here checking what claims should be given based on role
    private String doGenerateToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("LEARNER_ROLE")
                , new SimpleGrantedAuthority("CODE_REVIEWER_ROLE"))
        );

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // validate the token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = getUsernameFromTokey(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
