package com.edu.springboot2.jwt.response;

import com.edu.springboot2.jwt.response.CommonResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResult {
    private T accessToken;
}