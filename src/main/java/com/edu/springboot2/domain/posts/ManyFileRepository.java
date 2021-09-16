package com.edu.springboot2.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 이 클래스는 DAO 인터페이스로서 CRUD 매서드를 자동 생성하는 기능
 * 자동생성 매서드로 처리 못하는 것은 개발자가 추가(아래)
 */
public interface ManyFileRepository extends JpaRepository<ManyFile, Long> {
    @Query(value = "SELECT * FROM many_file p where p.posts_id = :posts_id ORDER BY p.id DESC", nativeQuery = true)
    List<ManyFile> fileAllDesc(@Param("posts_id") Long id);
}