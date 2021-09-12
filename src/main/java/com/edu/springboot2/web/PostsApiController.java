package com.edu.springboot2.web;

import com.edu.springboot2.service.PostsService;
import com.edu.springboot2.web.dto.PostsDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor//@Autowired,Inject 대신에 final 클래스에 대해 생성자를 생성해 줍니다.
@RestController//뷰단에서 Json 데이터 처리 사용시 스프링에서 자동으로 빈 생성
public class PostsApiController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostsService postsService; // 생성자로 주입

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsDto requestDto){
        return postsService.update(id, requestDto);
    }
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsDto requestDto){
        return postsService.save(requestDto);
    }
}
