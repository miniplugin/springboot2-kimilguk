package com.edu.springboot2.config.auth.dto;

import com.edu.springboot2.config.auth.Role;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private Role role;

    @Builder
    public SessionUser(String name, Role role){
        this.name = name;
        this.role = role;
    }
}