package com.edu.springboot2.config.auth.dto;

import com.edu.springboot2.config.auth.Role;
import com.edu.springboot2.domain.oauth_users.OAuthUsers;
import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private static final Logger logger = LoggerFactory.getLogger(OAuthAttributes.class);
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        logger.info("registration="+registrationId);
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }
        return null;
    }
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    public OAuthUsers toEntity(){
        return OAuthUsers.builder()
                .name(name)
                .email(email)
                .picture(picture)
                //.role(Role.GUEST)
                .role(Role.USER)
                .build();
    }
}