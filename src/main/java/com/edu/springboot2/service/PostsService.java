package com.edu.springboot2.service;

import com.edu.springboot2.domain.posts.Posts;
import com.edu.springboot2.domain.posts.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor//이 어노테이션은 초기화 되지않은 final 객체에 대해 생성자를 생성해 줍니다.
@Service//이 어노테이션은 클래스를 스프링빈으로 사용가능하게 함.
public class PostsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostsRepository postsRepository;

    private static final int PAGE_POST_COUNT = 5; // 한 페이지에 존재하는 게시글 수

    @Transactional
    public Page<Posts> getPostsList(String keyword, Integer pageNum) {
        Page<Posts> page = postsRepository.findByTitleContaining(keyword, PageRequest.of(pageNum, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "id")));
        return page;
    }
}
