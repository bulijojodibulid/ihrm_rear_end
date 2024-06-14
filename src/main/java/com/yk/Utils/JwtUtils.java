package com.yk.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    public static String getJwt(Integer id){
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", id);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, "yangking")
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
                .compact();
        return jwt;
    }

    public static Integer parseJwt(String token) {
        // 如果校验失败，会直接报错
        Claims body = Jwts.parser()
                .setSigningKey("yangking")
                .parseClaimsJws(token)
                .getBody();
        return (Integer)body.get("userId");
    }
}
