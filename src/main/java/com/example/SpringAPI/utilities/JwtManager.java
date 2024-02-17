package com.example.SpringAPI.utilities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.util.Date;
public class JwtManager {
    private static final String SECRET_KEY = "sesamebyteswanttocryptthisjwtkeyforspringbootapisodoitrightnow!"; // Replace with your actual secret key
    private static final long EXPIRATION_TIME =  259_200_000;  // 3 days in milliseconds
    public static String generateJwtToken(Long id) {
        String token = Jwts.builder()
                .claim("id",id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        return token;
    }

    public static String validateJwtToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
            Claims claims = claimsJws.getBody();
            if(!claims.getExpiration().before(new java.util.Date())){
                return claims.get("id").toString();
            }
            return null;

        } catch (Exception e) {
            return null;
        }
    }
}
