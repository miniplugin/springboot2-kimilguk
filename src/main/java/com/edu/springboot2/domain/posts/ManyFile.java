package com.edu.springboot2.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 이 클래스는 첨부파일 메타정보를 DB 저장용 @엔티티 객체(테이블)을 생성하는 기능
 * @ManyToOne 다대일 관계=제약조건 자동 생성됨
 */
@Getter
@Entity
@NoArgsConstructor
public class ManyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origFilename;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filePath;

    @ManyToOne
    private Posts posts;

    @Builder
    public ManyFile(Long id, String origFilename, String filename, String filePath, Posts posts) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.posts = posts;
    }
}