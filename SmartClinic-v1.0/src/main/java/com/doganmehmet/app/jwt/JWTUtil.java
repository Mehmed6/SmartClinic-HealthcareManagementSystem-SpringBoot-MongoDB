package com.doganmehmet.app.jwt;

import com.doganmehmet.app.entity.RefreshToken;
import com.doganmehmet.app.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    @Value("${jwt.app.secret.key}")
    private String jwtKey;
    @Value("${jwt.app.key.expiration}")
    private long jwtExpirationInMs;

    private Key getKey()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
    }
    private String createToken(Map<String, Object> claims, String subject)
    {
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token)
    {
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch (Exception ignored) {
            return true;
        }

    }

    public String generateToken(UserDetails userDetails)
    {
        var claims = new HashMap<String, Object>();
        var roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        claims.put("roles", roles);
        return createToken(claims, userDetails.getUsername());
    }

    public RefreshToken generateRefreshToken(User user)
    {
        var refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setRefreshToken(java.util.UUID.randomUUID().toString());
        refreshToken.setExpiresDate(new Date(System.currentTimeMillis() + jwtExpirationInMs * 2));

        return refreshToken;
    }

    public String getUsernameFromToken(String token)
    {
        return getClaims(token).getSubject();
    }

    public boolean isValidToken(String token, UserDetails userDetails)
    {
        try {
            var username = getUsernameFromToken(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        }
        catch (Exception ignored) {
            return false;
        }

    }
}
