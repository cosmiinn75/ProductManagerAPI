package com.example.spring_security.config;

import com.example.spring_security.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    private final String SECRET_KEY = "salutaceastaestecheiasecreta31412";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username, Role role){
        return Jwts.builder()
                .setSubject(username)
                .claim("role" , role.name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    public Role extractRole(String token) {
        String role = extractAllClaims(token).get("role",String.class);
        return Role.valueOf(role);
    }

    public boolean validateToken(String token , String email){
        String real_email = extractUsername(token);
        return (real_email.equals(email) && !isExpired(token));
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }
    private Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }
    public boolean isExpired(String token){
        return extractExpiration(token).before(new Date());
    }


    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
