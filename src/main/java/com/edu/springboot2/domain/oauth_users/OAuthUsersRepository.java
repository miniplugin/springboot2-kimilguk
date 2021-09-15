package com.edu.springboot2.domain.oauth_users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthUsersRepository extends JpaRepository<OAuthUsers, Long> {
    Optional<OAuthUsers> findByEmail(String email);//네아로 에서는 기준비교값으로 이메일을 아이디처럼 사용
}