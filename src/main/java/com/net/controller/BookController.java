package com.net.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/testDeploySpring")
public class BookController
{

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String hello() {
        System.out.println("in controller");
        return "testDeploySpring. Date:" + new Date();
    }

}