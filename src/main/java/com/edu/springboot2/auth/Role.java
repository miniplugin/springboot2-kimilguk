package com.edu.springboot2.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 이 클래스는 회원 권한 데이터를 enum 열거형으로 구조화
 */
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
