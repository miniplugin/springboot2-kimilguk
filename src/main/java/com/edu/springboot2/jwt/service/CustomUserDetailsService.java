package com.edu.springboot2.jwt.service;

import com.edu.springboot2.jwt.domain.JwtUsersJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 이 클래스는 토큰에 세팅된 유저 정보로 회원정보를 조회하는 UserDetailsService(인터페이스)를 재정의한다.
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JwtUsersJpaRepo userJpaRepo;

    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return userJpaRepo.findById(Long.parseLong(userPk)).orElseThrow(() -> new
                IllegalArgumentException("해당 인증 토큰이 없습니다."));
    }
}