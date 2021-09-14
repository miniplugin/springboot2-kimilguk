package com.edu.springboot2.config.auth;

import com.edu.springboot2.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@RequiredArgsConstructor//final 멤버객체를 인젝션 시킴
@Component//스프링빈으로 등록 시킴
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;//파라미터에 @LoginUser 인터페이스와 DB 로그인 세션이 있다면 true
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        //DB 로그인 일때 세션 저장
        if(httpSession.getAttribute("sessionUser") == null && !"anonymousUser".equals(userName)) {
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
            Role userAuthor = null;
            if(roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                userAuthor = Role.ADMIN;
            }else if(roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                userAuthor = Role.USER;
            }else{
                userAuthor = Role.GUEST;
            }
            SessionUser sessionUser = SessionUser.builder()
                    .name(userName)
                    .role(userAuthor)
                    .build();
            httpSession.setAttribute("sessionUser", sessionUser);//현재 세션은 DB 를 이용해서 생성됨
            logger.info("사용자권한1 " + userAuthor + " 세션사용자명1 " + sessionUser.getName() + " 로그인사용자명1 "+ sessionUser.getName());
        }
        return httpSession.getAttribute("sessionUser");
    }
}
