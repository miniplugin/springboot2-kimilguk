package com.edu.springboot2.domain.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 이 클래스는 DAO 인터페이스로서 CRUD 매서드를 자동 생성하는 기능
 * 자동생성 매서드로 처리 못하는 것은 개발자가 추가(아래)
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findByTitleContaining(String keyword, Pageable pageable);

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
