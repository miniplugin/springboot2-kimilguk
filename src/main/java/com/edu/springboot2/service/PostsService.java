package com.edu.springboot2.service;

import com.edu.springboot2.domain.posts.Posts;
import com.edu.springboot2.domain.posts.PostsRepository;
import com.edu.springboot2.web.dto.PostsDto;
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
    public void delete (Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new
                IllegalArgumentException("해당 게시글이 없습니다. id="+ id));
        postsRepository.delete(posts);
    }
    @Transactional
    public Long update(Long id, PostsDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id="+ id));
        posts.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getFileId());
        return id;
    }
    @Transactional
    public PostsDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("헤당 게시글이 없습니다. id="+id));
        return new PostsDto(entity);
    }
    @Transactional
    public Long save(PostsDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Page<Posts> getPostsList(String keyword, Integer pageNum) {
        Page<Posts> page = postsRepository.findByTitleContaining(keyword, PageRequest.of(pageNum, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "id")));
        return page;
    }

}
