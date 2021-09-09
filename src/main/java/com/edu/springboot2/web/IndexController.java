package com.edu.springboot2.web;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor//이 어노테이션은 초기화 되지않은 final 필드에 대해 생성자를 생성해 줍니다.
@Controller
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String index() {

        return "posts/posts-list";
    }

}
