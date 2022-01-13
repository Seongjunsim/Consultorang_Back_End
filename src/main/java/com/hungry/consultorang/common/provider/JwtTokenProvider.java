package com.hungry.consultorang.common.provider;

import com.hungry.consultorang.config.EnvSet;
import com.hungry.consultorang.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyStore;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    EnvSet envSet;

    public JwtTokenProvider(EnvSet envSet) {
        this.envSet = envSet;
    }

    public String createToken(UserModel userModel) throws Exception {
        Date validDate = new Date(new Date().getTime()
                + envSet.getExpireLength());

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("exp", validDate);
        payloads.put("userId", userModel.getUserId());
        payloads.put("userName", userModel.getUserName());

        byte[] keyByte = envSet.getSecretKey().getBytes(StandardCharsets.UTF_8);
        Key key = Keys.hmacShaKeyFor(keyByte);

        String token = Jwts.builder().setHeader(headers)
                .setClaims(payloads)
                .signWith(key)
                .compact();

        return token;
    }

    public boolean validateToken(String token){
        try{
            byte[] keyByte = envSet.getSecretKey().getBytes(StandardCharsets.UTF_8);
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(keyByte)
                    .build()
                    .parseClaimsJws(token);
            if(claims.getBody().getExpiration().before(new Date())) return false;
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
