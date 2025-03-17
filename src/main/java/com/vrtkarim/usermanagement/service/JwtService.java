package com.vrtkarim.usermanagement.service;


import com.vrtkarim.usermanagement.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }





    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims  = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails );
    }
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return  buildToken(claims, userDetails);
    }
    private String buildToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }
    public boolean isTokenValid(String token,UserDetails user) {
        final String username = extractUsername(token);
        return username.equals(user.getUsername());
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token).getBody();
    }


    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(
                Constants.api_key
        );
        return  Keys.hmacShaKeyFor(keyBytes);
    }
}
