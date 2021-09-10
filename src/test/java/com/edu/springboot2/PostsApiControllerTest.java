package com.edu.springboot2;

import com.edu.springboot2.domain.posts.Posts;
import com.edu.springboot2.domain.posts.PostsRepository;
import com.edu.springboot2.service.PostsService;
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
    public void posts_list() {
        List<Posts> postsList = postsRepository.findAllDesc();
        postsList.toString();//Jpa 쿼리 연습 용도
        Page<Posts> pagePostsList = postsService.getPostsList("",0);
        pagePostsList.toString();//실제 사용 용도
    }


}
