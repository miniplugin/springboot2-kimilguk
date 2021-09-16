package com.edu.springboot2.web.dto;

import com.edu.springboot2.domain.posts.ManyFile;
import com.edu.springboot2.domain.posts.Posts;
import lombok.*;
/**
 * 이 클래스는 서비스와 컨트롤러에서 전송되는 data 를 임시 저장하는 기능
 */
@Getter
@Setter
@ToString //ManyFileDto.toString() 자동생성
@NoArgsConstructor
public class ManyFileDto {
    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;
    private Posts posts;

    public ManyFile toEntity() {
        ManyFile build = ManyFile.builder()
                .id(id)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .posts(posts)
                .build();
        return build;
    }

    @Builder
    public ManyFileDto(Long id, String origFilename, String filename, String filePath, Posts posts) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.posts = posts;
    }
}