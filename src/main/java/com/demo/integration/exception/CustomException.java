package com.demo.integration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="用户名或密码错误")
public class CustomException extends RuntimeException{

    private static final long serialVersionUID = -2924484061807603761L;

}
