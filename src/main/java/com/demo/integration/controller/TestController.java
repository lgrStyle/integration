package com.demo.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.integration.exception.CustomException;

@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        throw new CustomException();
    }
}
