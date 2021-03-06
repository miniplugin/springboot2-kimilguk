package com.edu.springboot2.web.dto;

import com.edu.springboot2.domain.posts.Posts;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 이 클래스는 서비스와 컨트롤러에서 전송되는 data 를 임시 저장하는 기능
 */
@NoArgsConstructor
@ToString //PostsDto.toString() 자동생성
@Getter
@Setter
public class PostsDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private Long fileId;
    private LocalDateTime modifiedDate;

    //조회
    public PostsDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.fileId = entity.getFileId();
        this.modifiedDate = entity.getModifiedDate();
    }
    //수정 및 임시저장
    @Builder
    public PostsDto(Long id, String title, String content, String author, Long fileId){
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.fileId = fileId;
    }
    //DB 신규 저장
    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .fileId(fileId)
                .build();
    }
    /*
    @Override
    public String toString() {
        return "PostsDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", fileId=" + fileId +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
    */
}