package com.edu.springboot2.web;

import com.edu.springboot2.service.simple_users.SimpleUsersService;
import com.edu.springboot2.util.ScriptUtils;
import com.edu.springboot2.web.dto.SimpleUsersDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class SimpleUsersController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final SimpleUsersService simpleUsersService;

    @PostMapping("/simple_users/delete")
    public String simpleUsersDeletePost(HttpServletResponse response,SimpleUsersDto simpleUsersDto, Model model) throws Exception {
        simpleUsersService.delete(simpleUsersDto.getId());
        ScriptUtils.alertAndMovePage(response, "삭제 되었습니다.", "/simple_users/list");
        //return "redirect:/simple_users/list";
        return null;
    }
    @PostMapping("/simple_users/update")
    public String simpleUsersUpdatePost(HttpServletResponse response, SimpleUsersDto simpleUsersDto, Model model) throws Exception {
        simpleUsersService.update(simpleUsersDto.getId(), simpleUsersDto);
        ScriptUtils.alertAndMovePage(response, "수정 되었습니다.", "/simple_users/update/" + simpleUsersDto.getId());
        //return "redirect:/simple_users/update/" + simpleUsersDto.getId();
        return null;
    }
    @GetMapping("/simple_users/update/{id}")
    public String simpleUsersUpdate(HttpServletResponse response, @PathVariable Long id, Model model){
        model.addAttribute("simple_user", simpleUsersService.findById(id));
        return "simple_users/update";
    }
    @PostMapping("/simple_users/save")
    public String simpleUsersSavePost(HttpServletResponse response, SimpleUsersDto simpleUsersDto, Model model) throws Exception {
        simpleUsersService.save(simpleUsersDto);
        ScriptUtils.alertAndMovePage(response, "저장 되었습니다.", "/simple_users/list");
        //return "redirect:/simple_users/list";
        return null;
    }
    @GetMapping("/simple_users/save")
    public String simpleUsersSave(Model model){
        return "simple_users/save";
    }
    @GetMapping("/simple_users/list")
    public String simpleUsersList(Model model){
        model.addAttribute("simpleUsers", simpleUsersService.findAllDesc());
        return "simple_users/list";
    }
}
