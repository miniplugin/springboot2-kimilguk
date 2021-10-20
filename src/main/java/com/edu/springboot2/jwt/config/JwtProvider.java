package com.edu.springboot2.jwt.config;

import com.edu.springboot2.service.simple_users.SimpleUsersService;
import com.edu.springboot2.web.dto.SimpleUsersDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 이 클래스는 Json Web Token 생성 및 유효성 검증을 하는 컴포넌트이다.
 */
@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("spring.jwt.secret")
    private String secretKey;

    private Long tokenValidMillisecond = 60 * 60 * 1000L * 24 * 365;//토큰 유지시간 1시간 -> 1일 -> 1년

    private final SimpleUsersService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 생성
    public String createToken(String userPk, List<String> roles) {
        // user 구분을 위해 Claims에 User Pk값 넣어줌
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        // 생성날짜, 만료날짜를 위한 Date
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Jwt 로 인증정보를 조회
    public Authentication getAuthentication (String token) {
        //UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        //return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SimpleUsersDto userDetails = userDetailsService.findByName("admin");
        return new UsernamePasswordAuthenticationToken(userDetails, "",  Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    // jwt 에서 회원 구분 Pk 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // HTTP Request 의 Header 에서 Token Parsing -> "Authorization: jwt"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // jwt 의 유효성 및 만료일자 확인
    public boolean validationToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date()); // 만료날짜가 현재보다 이전이면 false
        } catch (Exception e) {
            return false;
        }
    }
}