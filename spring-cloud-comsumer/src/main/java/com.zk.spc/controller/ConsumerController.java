package com.zk.spc.controller;

import com.zk.spc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return helloService.hello(name);
    }


}
