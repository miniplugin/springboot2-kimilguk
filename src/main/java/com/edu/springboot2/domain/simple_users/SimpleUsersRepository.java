package com.edu.springboot2.domain.simple_users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 이 클래스는 DAO 인터페이스로서 CRUD 매서드를 자동 생성하는 기능
 * 자동생성 매서드로 처리 못하는 것은 개발자가 추가(아래)
 */
public interface SimpleUsersRepository extends JpaRepository<SimpleUsers, Long> {

    @Query("SELECT p FROM SimpleUsers p ORDER BY p.id DESC")
    List<SimpleUsers> findAllDesc();

    @Query("SELECT p FROM SimpleUsers p where p.username = :username")
    SimpleUsers findByName(@Param("username") String username);

}