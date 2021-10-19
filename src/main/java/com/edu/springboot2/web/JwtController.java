package com.edu.springboot2.web;

import com.edu.springboot2.auth.LoginUser;
import com.edu.springboot2.auth.Role;
import com.edu.springboot2.auth.dto.SessionUser;
import com.edu.springboot2.domain.simple_users.SimpleUsersRepository;
import com.edu.springboot2.jwt.config.JwtProvider;
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
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 이 클래스는 회원관리 웹 URL 매핑을 전송되는 data 와 함께 처리하는 기능
 */
@RequiredArgsConstructor
@Controller
public class JwtController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final SimpleUsersService simpleUsersService;
    private final JwtProvider jwtProvider;//생성자로 객체 주입

    @GetMapping("/api")
    public String simpleUsersUpdate(Model model){
        String token = jwtProvider.createToken("jwt", new ArrayList<String>( Arrays.asList(String.valueOf(Role.ADMIN))));
        model.addAttribute("token", token);
        return "api";
    }

}
