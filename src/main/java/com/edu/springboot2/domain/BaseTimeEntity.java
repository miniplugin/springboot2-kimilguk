package com.edu.springboot2.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 이 클래스는 공통으로 사용하는 Auditing 정보를 DB 저장용 @엔티티 객체(테이블)을 생성하는 기능
 * 등록일과 수정일은 게시판, 회원관리 등에서 공통 사용하게 됨
 */
@Getter //겟터 매서드 자동생성 명시
@MappedSuperclass //Jpa 객체 매핑을 부모클래스로 명시
@EntityListeners(AuditingEntityListener.class) //공통으로 사용하는 Auditing 엔티티로 명시
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}