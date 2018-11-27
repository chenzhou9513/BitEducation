package com.bitedu.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {


    @Value("${jwt.config.key}")
    private String key;

    //过期时间
    @Value("${jwt.config.ttl}")
    private String ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }


    public String createJWT(String id,String subject, String roles){


        long nowMillis = System.currentTimeMillis();

        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key)
                .claim("roles", roles);
        long TTL = Long.valueOf(ttl);
        if(TTL >0){
            builder.setExpiration(new Date(nowMillis+TTL));
        }

        return builder.compact();
    }



    public Claims paraseJWT(String jwtStr){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwtStr).getBody();
    }



}
