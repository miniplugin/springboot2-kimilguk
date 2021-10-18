package com.edu.springboot2.jwt;

import com.edu.springboot2.auth.Role;
import com.edu.springboot2.jwt.config.JwtProvider;
import com.edu.springboot2.jwt.response.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor //이 어노테이션은 초기화 되지않은 final 필드에 대해 생성자를 생성해 줍니다
@RestController
public class JwtRestController {

    private final JwtProvider jwtProvider;//생성자로 객체 주입

    @GetMapping("/token")
    public SingleResult<String> token() {
        String token = jwtProvider.createToken("jwt", Role.ADMIN);
        SingleResult<String> result = new SingleResult<>();
        result.setAccessToken(token);
        result.setSuccess(true);
        result.setCode(200);
        result.setMsg("SUCCESS");
        return result;
    }

    @GetMapping("/api/v1/simple_users/list")
    public String apiSimpleUsersList(){

        return "simple_users/list";
    }
}
