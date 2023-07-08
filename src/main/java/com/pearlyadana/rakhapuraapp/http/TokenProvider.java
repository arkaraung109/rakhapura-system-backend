package com.pearlyadana.rakhapuraapp.http;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TokenProvider {
    private static final long TOKEN_VALIDITY_IN_MILI_SECOND =1000*60*60*24;
    private static final String ROLE_PREFIX="ROLE_";
    private static final String AUTHORITIES_KEY = "auth";

    private Key key;

    @PostConstruct
    private void init() {
        byte[] keyBytes;
        String secret ="ZTY5ZjQ5YmEwNDQxMTk5NDAzOGFjNjQ2MDc3Y2Y1OTkwNGU2Y2I5MmI1NzllNmQ5YTkxYzcyZDhiOGRiYzhkZTk3NzVmOGFlMDZkNTdiYjU4MmE4YTg1ZjAxNDM1OTVkNTFhZjI0YWQ5NmM1MGExZDM1MzI2YTFmOGM3YzFlYjc=";
        keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        long now = (new Date()).getTime();
        Date validity = new Date(now + TOKEN_VALIDITY_IN_MILI_SECOND);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Stream.of(ROLE_PREFIX+claims.get(AUTHORITIES_KEY).toString())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) throws Exception {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        }
        catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        }
        catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

    }
}
