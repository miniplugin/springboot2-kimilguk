package com.edu.springboot2.web;

import com.edu.springboot2.domain.posts.ManyFile;
import com.edu.springboot2.domain.posts.Posts;
import com.edu.springboot2.service.posts.ManyFileService;
import com.edu.springboot2.service.posts.PostsService;
import com.edu.springboot2.web.dto.PostsDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor//@Autowired,Inject 대신에 final 클래스에 대해 생성자를 생성해 줍니다.
@Controller//스프링에서 자동으로 빈 생성
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostsService postsService;
    private final ManyFileService manyFileService;

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) throws Exception {
        PostsDto dto = postsService.findById(id);
        model.addAttribute("post",dto);
        //다중 파일 시작
        List<ManyFile> manyFileList = manyFileService.getManyFile(id);
        if(manyFileList.size() > 0) {
            model.addAttribute("manyFileList", manyFileList);
        }
        return "posts/posts-update";
    }
    @GetMapping("/posts/save")
    public String postsSave(Model model){

        return "posts/posts-save";
    }
    @GetMapping("/posts/read/{id}")
    public String postsRead(@PathVariable Long id, Model model){
        PostsDto dto = postsService.findById(id);
        model.addAttribute("post",dto);
        //다중 파일 시작
        List<ManyFile> manyFileList = manyFileService.getManyFile(id);
        if(manyFileList.size() > 0) {
            model.addAttribute("manyFileList", manyFileList);
        }
        return "posts/posts-read";
    }
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
        ArrayList<Integer> pageNumbers = new ArrayList<Integer>();
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
