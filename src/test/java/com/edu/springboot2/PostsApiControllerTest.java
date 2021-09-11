package com.edu.springboot2;

import com.edu.springboot2.domain.posts.Posts;
import com.edu.springboot2.domain.posts.PostsRepository;
import com.edu.springboot2.service.PostsService;
import com.edu.springboot2.web.dto.PostsDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;


@SpringBootTest//별다른 설정없이 SpringBootTest를 사용할 경우 H2 데이터 베이스를 자동으로 실행
public class PostsApiControllerTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private PostsService postsService;

    @Test
    public void posts_delete() {
        Long id = posts_save();
        logger.info("디버그: " + id);
        postsService.delete(id);
    }
    @Test
    public void posts_update() {
        Long id = posts_save();
        PostsDto postsDto = postsService.findById(id);
        postsDto.setTitle("제복_update");
        postsDto.setContent("내용_update");
        postsService.update(id, postsDto);
        posts_list();
    }
    @Test
    public void posts_read() {
        Long id = posts_save();
        PostsDto postsDto = postsService.findById(id);
        logger.info("디버그: " + postsDto.toString());
    }
    @Test
    public Long posts_save() {
        String title = "제목";
        String content = "내용";
        PostsDto requestDto = PostsDto.builder()
                .title(title)
                .content(content)
                .author("admin")
                .build();
        Long id = postsService.save(requestDto);
        posts_list();
        return id;
    }
    @Test
    public void posts_list() {
        List<Posts> postsList = postsRepository.findAllDesc();
        postsList.toString();//Jpa 쿼리 연습 용도
        Page<Posts> pagePostsList = postsService.getPostsList("",0);
        logger.info("디버그: " + pagePostsList.toString());//실제 사용 용도
    }


}
