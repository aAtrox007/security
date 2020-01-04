package org.qf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping
    public Object getUsers() {
        return Arrays.asList("abc", "xyz");
    }
}
