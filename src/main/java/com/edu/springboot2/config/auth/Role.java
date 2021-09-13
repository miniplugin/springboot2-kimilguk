package com.edu.springboot2.config.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    //권한에서 사용할 데이터 enum 으로 구조화 key, value 형태
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");
    private final String key;
    private final String title;
}
