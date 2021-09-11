package com.edu.springboot2.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor//@Autowired,Inject 대신에 final 클래스에 대해 생성자를 생성해 줍니다.
@RestController//뷰단에서 Json 데이터 처리 사용시 스프링에서 자동으로 빈 생성
public class PostsApiController {

}
