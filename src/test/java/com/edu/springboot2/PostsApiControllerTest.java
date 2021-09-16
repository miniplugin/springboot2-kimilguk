package com.edu.springboot2;

import com.edu.springboot2.domain.posts.Posts;
import com.edu.springboot2.domain.posts.PostsRepository;
import com.edu.springboot2.service.posts.PostsService;
import com.edu.springboot2.web.dto.PostsDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 이 클래스틑 게시판 도메인과 DAO 생성 후 JUnit 으로 CRUD 테스트를 확인하는 기능
 * 위 테스트 후 컨트롤러와 뷰단(머스태치) 작업진행
 */
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
        logger.info("디버그: " + pagePostsList.toString());//실제 사용: 검색 결과 리스트

        Integer[] pageList = postsService.getPageList(pagePostsList.getTotalElements(), pagePostsList.getTotalPages(), 0);
        ArrayList<Integer> pageNumbers = new ArrayList<Integer>();
        logger.info("게시물전체개수: " + pagePostsList.getTotalElements() + ", 페이지전체개수: " + pagePostsList.getTotalPages());//실제사용: 검색결과 하단 페이지 개수로 사용
        if(!pagePostsList.isEmpty()) {
            for (Integer pageNum : pageList) {
                pageNumbers.add(pageNum);
            }
        } else {
            for (Integer pageNum : pageList) {
                pageNumbers.add(null);
            }
        }
        logger.info("페이지하단리스트: " + pageNumbers);//실제사용: 검색결과 하단 페이지 개수로 사용
    }


}
