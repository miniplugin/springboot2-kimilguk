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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 이 클래스는 회원관리 웹 URL 매핑을 전송되는 data 와 함께 처리하는 기능
 */
@RequiredArgsConstructor
@Controller
public class JwtController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    //토큰사용예시(아래)
    public String getToken() throws IOException, ParseException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://kimilguk-springboot2.herokuapp.com/token")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        jsonObject = (JSONObject) parser.parse(response.body().string());
        String token = (String) jsonObject.get("accessToken");
        logger.info("토큰확인 : " + token);
        response.body().close();

        return token;
    }
    @GetMapping("/api")
    public String apiToken(Model model) throws IOException, ParseException {
        String token = getToken();
        model.addAttribute("token", token);
        return "api";
    }

    //쿠키로 토큰사용예시(아래)
    public String getCookie() throws IOException, ParseException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://kimilguk-springboot2.herokuapp.com/cookie")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        List<String> Cookielist = response.headers().values("Set-Cookie");
        String token = (Cookielist .get(0).split(";"))[0].split("=")[1];;
        logger.info("쿠키토큰확인 : " + token);
        response.body().close();

        return token;
    }
    @GetMapping("/api_cookie")
    public String apiCookie(Model model) throws IOException, ParseException {
        String token = getCookie();
        model.addAttribute("token", token);
        return "api";
    }

}
