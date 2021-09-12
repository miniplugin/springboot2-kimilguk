package com.edu.springboot2.domain.posts;

import com.edu.springboot2.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@ToString //Posts.toString() 자동생성
@Entity
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column( columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private Long fileId;

    @Builder
    public Posts(String title, String content, String author, Long fileId){
        this.title = title;
        this.content = content;
        this.author = author;
        this.fileId = fileId;
    }

    public void update(String title, String content, Long fileId){
        this.title = title;
        this.content = content;
        this.fileId = fileId;
    }
    /*
    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", fileId='" + fileId + '\'' +
                '}';
    }
    */
}
