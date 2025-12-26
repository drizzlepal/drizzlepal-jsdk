package com.drizzlepal.springboot.webstarte.controller;

import org.springframework.web.bind.annotation.RestController;

import com.drizzlepal.springboot.webstarter.CommonRpcErrorCode;
import com.drizzlepal.springboot.webstarter.RpcAssert;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TestController {

    @GetMapping("/test4xx")
    public String test4xx(@RequestParam("name") String name) {
        RpcAssert.isTrue(name.equals("name"), CommonRpcErrorCode.ParamInvalid, "非法参数");
        return "test hello:" + name;
    }

    @GetMapping("/test5xx")
    public String test5xx(@RequestParam("name") String name) {
        throw new RuntimeException("test exception");
    }

}
