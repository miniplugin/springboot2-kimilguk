package com.edu.springboot2.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * 이 클래스는 세션으로 저장될 임시값을 저장 하는 역할
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String role;

    @Builder
    public SessionUser(String name, String role){
        this.name = name;
        this.role = role;
    }
}