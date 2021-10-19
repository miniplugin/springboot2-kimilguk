package com.edu.springboot2.jwt;

import com.edu.springboot2.auth.Role;
import com.edu.springboot2.jwt.config.JwtProvider;
import com.edu.springboot2.jwt.response.SingleResult;
import com.edu.springboot2.service.simple_users.SimpleUsersService;
import com.edu.springboot2.web.dto.SimpleUsersDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequiredArgsConstructor //이 어노테이션은 초기화 되지않은 final 필드에 대해 생성자를 생성해 줍니다
@RestController
public class JwtRestController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final JwtProvider jwtProvider;//생성자로 객체 주입
    private final SimpleUsersService simpleUsersService;
    //토큰발행 API
    @GetMapping("/token")
    public SingleResult<String> token() {
        String token = jwtProvider.createToken("jwt", new ArrayList<String>( Arrays.asList(String.valueOf(Role.ADMIN))));
        SingleResult<String> result = new SingleResult<>();
        result.setAccessToken(token);
        result.setSuccess(true);
        result.setCode(200);
        result.setMsg("SUCCESS");
        return result;
    }
    //사용자 목록조회 API
    @GetMapping("/api/v1/simple_users/list")
    public ResponseEntity<Map<String,Object>> apiSimpleUsersList(){
        ResponseEntity<Map<String,Object>> resultJson = null;
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<SimpleUsersDto> userList =  simpleUsersService.findAllDesc();
        SingleResult<String> result = new SingleResult<>();
        result.setSuccess(true);
        result.setCode(200);
        result.setMsg("SUCCESS");
        resultMap.put("info", result);
        resultMap.put("body", userList);
        resultJson = new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
        return resultJson;
    }
    //개별 사용자 정보 API
    @GetMapping("/api/v1/simple_users/{user_name}")
    public ResponseEntity<Map<String,Object>> apiSimpleUsersOne(@PathVariable("user_name")String user_name){
        ResponseEntity<Map<String, Object>> resultJson = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        SingleResult<String> result = new SingleResult<>();

        SimpleUsersDto userOne = simpleUsersService.findByName(user_name);
        result.setSuccess(true);
        result.setCode(200);
        result.setMsg("SUCCESS");
        resultMap.put("info", result);
        resultMap.put("body", userOne);
        resultJson = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

        return resultJson;
    }
}
