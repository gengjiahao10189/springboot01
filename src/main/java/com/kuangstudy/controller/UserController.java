package com.kuangstudy.controller;

import com.kuangstudy.aop.limiter.AcessLimter;
import com.kuangstudy.aop.log.KsdLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/index")
    @KsdLog
    @AcessLimter(timeout = 1,limit = 5)
    public String index() {
        return "success";
    }

    @GetMapping("/index2")
    public String index2() {
        return "success";
    }
}
