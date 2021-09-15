package com.edu.springboot2.web;

import com.edu.springboot2.config.auth.LoginUser;
import com.edu.springboot2.config.auth.dto.SessionUser;
import com.edu.springboot2.domain.posts.ManyFile;
import com.edu.springboot2.domain.posts.Posts;
import com.edu.springboot2.domain.simple_users.SimpleUsers;
import com.edu.springboot2.domain.simple_users.SimpleUsersRepository;
import com.edu.springboot2.service.posts.ManyFileService;
import com.edu.springboot2.service.posts.PostsService;
import com.edu.springboot2.service.simple_users.SimpleUsersService;
import com.edu.springboot2.util.ScriptUtils;
import com.edu.springboot2.web.dto.PostsDto;
import com.edu.springboot2.web.dto.SimpleUsersDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor//@Autowired,Inject 대신에 final 클래스에 대해 생성자를 생성해 줍니다.
@Controller//스프링에서 자동으로 빈 생성
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostsService postsService;
    private final ManyFileService manyFileService;
    private final SimpleUsersRepository simpleUsersRepository;
    private final SimpleUsersService simpleUsersService;

    @PostMapping("/mypage/signout")
    public String simpleUsersDeletePost(HttpServletResponse response,SimpleUsersDto simpleUsersDto) throws Exception {
        simpleUsersService.delete(simpleUsersDto.getId());
        ScriptUtils.alertAndMovePage(response, "회원 탈퇴 되었습니다.", "/logout");
        //return "redirect:/simple_users/list";
        return null;
    }
    @PostMapping("/mypage/mypage")
    public String simpleUsersUpdatePost(HttpServletResponse response, SimpleUsersDto simpleUsersDto) throws Exception {
        simpleUsersDto.setRole("USER");//해킹 위험 때문에 강제로 추가
        simpleUsersService.update(simpleUsersDto.getId(), simpleUsersDto);
        ScriptUtils.alertAndMovePage(response, "수정 되었습니다.", "/mypage/mypage/" + simpleUsersDto.getId());
        //return "redirect:/simple_users/update/" + simpleUsersDto.getId();
        return null;
    }
    @GetMapping("/mypage/mypage/{id}")
    public String simpleUsersUpdate(HttpServletResponse response,@PathVariable Long id, Model model){
        model.addAttribute("simple_user", simpleUsersService.findById(id));
        return "mypage/mypage";
    }
    @PostMapping("/signup")
    public String signUpPost(HttpServletResponse response, SimpleUsersDto simpleUsersDto, Model model) throws Exception {
        logger.info("디버그 :" + simpleUsersDto.toString());
        simpleUsersDto.setRole("USER");//해킹 위험 때문에 강제로 추가
        SimpleUsers simpleUsers = simpleUsersRepository.findByName(simpleUsersDto.getUsername());
        if(simpleUsers == null) {
            simpleUsersService.save(simpleUsersDto);
            ScriptUtils.alertAndMovePage(response, "회원가입 되었습니다. 로그인해 주세요", "/");
        }else{
            ScriptUtils.alertAndBackPage(response, "중복 아이디가 존재 합니다 아이디를 다시 입력해 주세요.");
        }
        //return "redirect:/simple_users/list";
        return null;
    }
    @GetMapping("/signup")
    public String signupGet(){
        return "signup";
    }
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String message, Model model){
        model.addAttribute("message", message);
        return "login";
    }
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@LoginUser SessionUser sessionUser, HttpServletResponse response, @PathVariable Long id, Model model) throws Exception {
        PostsDto dto = postsService.findById(id);
        if( !sessionUser.getName().equals(dto.getAuthor()) && !"ROLE_ADMIN".equals(sessionUser.getRole()) ) {
            ScriptUtils.alertAndBackPage(response, "본인 글만 수정 가능합니다.");
        }
        model.addAttribute("post",dto);
        //다중 파일 시작
        List<ManyFile> manyFileList = manyFileService.getManyFile(id);
        if(manyFileList.size() > 0) {
            model.addAttribute("manyFileList", manyFileList);
        }
        return "posts/posts-update";
    }
    @GetMapping("/posts/save")
    public String postsSave(@LoginUser SessionUser sessionUser,Model model){
        if(sessionUser != null){
            model.addAttribute("sessionUserName", sessionUser.getName());
        }
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
    public String index(@LoginUser SessionUser sessionUser, HttpServletRequest request, String search_type, @RequestParam(value="keyword", defaultValue = "")String keyword, @RequestParam(value = "page",required=false,defaultValue="0")Integer page, Model model) {
        if(sessionUser != null){
            model.addAttribute("sessionUserName", sessionUser.getName());
            if(simpleUsersRepository.findByName(sessionUser.getName()) != null) {//DB 로그인인지 체크
                SimpleUsersDto simpleUsers = simpleUsersService.findByName(sessionUser.getName());
                model.addAttribute("sessionUserId", simpleUsers.getId());
            } else {
                model.addAttribute("sessionUserId", null);
            }
            //권한확인(아래)
            model.addAttribute("sessionRoleAdmin", ("ROLE_ADMIN".equals(sessionUser.getRole())?"admin":null));
        }
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
