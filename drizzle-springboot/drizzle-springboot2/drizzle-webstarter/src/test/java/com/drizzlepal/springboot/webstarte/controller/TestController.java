package com.drizzlepal.springboot.webstarte.controller;

import org.springframework.web.bind.annotation.RestController;

import com.drizzlepal.rpc.exception.asserts.RpcParamAssert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TestController {

    @GetMapping("/test4xx")
    public String test4xx(@RequestParam("name") String name) {
        RpcParamAssert.isTrue(name.equals("name"), "非法参数");
        return "test hello:" + name;
    }

    @GetMapping("/test5xx")
    public String test5xx(@RequestParam("name") String name) {
        throw new RuntimeException("test exception");
    }

}
