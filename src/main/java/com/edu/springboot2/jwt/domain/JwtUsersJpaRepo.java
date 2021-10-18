package com.edu.springboot2.jwt.domain;

import com.edu.springboot2.jwt.domain.JwtUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 이 클래스는 CustomUserDetailsService 에서 jwt_users 테이블에 DAO 역할을 한다.
 */
@Repository
public interface JwtUsersJpaRepo  extends JpaRepository<JwtUsers, Long> {

    List<JwtUsers> findByName(String name);

    Optional<JwtUsers> findByEmail(String email);

    Optional<JwtUsers> findByEmailAndProvider(String email, String provider);
}