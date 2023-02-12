package com.example.mediaservice.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor // данная анотация автоматически создает конструктор и инжектит объект
public class JwtTokenUtil {
    private static final ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper();
    }

    public static final long JWT_TOKEN_VALIDITY = 7 * 24 * 60 * 60; //1 неделя живет токен
    private final String secret = "zdtlD3JK56m6wTTgsNFhqzjqP";// пароль

    public String getUserNameFromToken(String token) {
        String subject = getClaimsFromToken(token, Claims::getSubject);
        JsonNode subjectJSON = null;
        try {
            subjectJSON = objectMapper.readTree(subject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if(subjectJSON != null) {
            return subjectJSON.get("username").asText();
        } else {
            return null;
        }
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails) {
        return doGenerateToken(userDetails.toString());
    }

    private String doGenerateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)//полезная информация
                .setIssuedAt(new Date(System.currentTimeMillis()))// дата создания токена
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))// жизнь токена
                .signWith(SignatureAlgorithm.HS512, secret)//пароль
                .compact();//компанует токен
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }
}



