package com.demo.integration.login.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.integration.comom.exception.CustomException;


@RestController
public class LoginController {

    @RequestMapping("/hello")
    public String hello() {
        throw new CustomException();
    }
}
