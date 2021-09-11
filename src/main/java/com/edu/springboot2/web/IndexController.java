package com.edu.springboot2.web;

import com.edu.springboot2.domain.posts.Posts;
import com.edu.springboot2.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RequiredArgsConstructor//이 어노테이션은 초기화 되지않은 final 필드에 대해 생성자를 생성해 줍니다.
@Controller
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostsService postsService;

    @GetMapping("/")
    public String index(HttpServletRequest request, String search_type, @RequestParam(value="keyword", defaultValue = "")String keyword, @RequestParam(value = "page",required=false,defaultValue="0")Integer page, Model model) {
        String sessionKeyword = (String) request.getSession().getAttribute("sessionKeyword");
        if(keyword.isEmpty() && sessionKeyword == null) {
            sessionKeyword = "";
        }
        if(!keyword.isEmpty()) {
            request.getSession().setAttribute("sessionKeyword", keyword);
            sessionKeyword = (String) request.getSession().getAttribute("sessionKeyword");
        }
        if(keyword.isEmpty() && search_type != null && sessionKeyword != null) {
            request.getSession().removeAttribute("sessionKeyword");
            sessionKeyword = "";
        }
        model.addAttribute("sessionKeyword", sessionKeyword);
        model.addAttribute("page", page);
        Page<Posts> postsList = postsService.getPostsList(sessionKeyword, page);
        model.addAttribute("postsList",postsList);
        model.addAttribute("totalPageSize",postsList.getTotalPages());
                Integer[] pageList = postsService.getPageList(postsList.getTotalElements(), postsList.getTotalPages(), page);
        ArrayList pageNumbers = new ArrayList();
        if(!postsList.isEmpty()) {
            for (Integer pageNum : pageList) {
                pageNumbers.add(pageNum);
            }
        } else {
            for (Integer pageNum : pageList) {
                pageNumbers.add(null);
            }
        }
        model.addAttribute("pageList", pageNumbers);
        return "posts/posts-list";
    }

}
