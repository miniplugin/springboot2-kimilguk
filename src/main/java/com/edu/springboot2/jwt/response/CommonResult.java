package com.edu.springboot2.jwt.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    //value = "응답 성공 여부: T/F"
    private boolean success;

    //value = "응답 코드: >= 0 정상, < 0 비정상"
    private int code;

    //value = "응답 메시지"
    private String msg;
}