package com.edu.springboot2.service.oauth2;

import com.edu.springboot2.auth.dto.OAuthAttributes;
import com.edu.springboot2.auth.dto.SessionUser;
import com.edu.springboot2.domain.oauth_users.OAuthUsers;
import com.edu.springboot2.domain.oauth_users.OAuthUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * 이 클래스는 네이버 아이디 로그인 인증 정보를 받아서 DAO 레포지토리를 호출 및 세션 처리 하는 기능
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final HttpSession httpSession;
    private final OAuthUsersRepository oAuthUsersRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        OAuthUsers oAuthUsers = saveOrUpdate(attributes);//DB OAuthUsers 테이블에 네이버 인증 값 저장 메서드 호출
        SessionUser sessionUser = SessionUser.builder()
                .name(oAuthUsers.getName())
                .role(oAuthUsers.getRoleKey())
                .build();
        httpSession.setAttribute("sessionUser", sessionUser);//기존 DB 로그인 세션로직 참여
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(oAuthUsers.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }
    //DB OAuthUsers 테이블에 네이버 인증 값 저장 매서드(아래)
    private OAuthUsers saveOrUpdate(OAuthAttributes attributes){
        OAuthUsers oAuthUsers = oAuthUsersRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());//외부 API 로그인 인증한 권한은 이 메서드에서 발생됨.
        return oAuthUsersRepository.save(oAuthUsers);
    }
}
