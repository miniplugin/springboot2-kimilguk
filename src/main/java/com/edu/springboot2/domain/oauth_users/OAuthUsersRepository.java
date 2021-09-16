package com.edu.springboot2.domain.oauth_users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 이 클래스는 DAO 인터페이스로서 CRUD 매서드를 자동 생성하는 기능
 * 자동생성 매서드로 처리 못하는 것은 개발자가 추가(아래)
 */
public interface OAuthUsersRepository extends JpaRepository<OAuthUsers, Long> {
    Optional<OAuthUsers> findByEmail(String email);//네아로 에서는 기준비교값으로 이메일을 아이디처럼 사용
}