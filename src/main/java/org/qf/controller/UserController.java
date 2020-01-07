package org.qf.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.Arrays;

@RestController
@RequestMapping("/user")
public class UserController {


    /**
     * @RolesAllowed 注解的意思允许角色访问，在配置用户角色的时候  new SimpleGrantedAuthority("ROLE_teacher")
     *               一定要加上 ROLE_XXX;
     *               那么在使用@RolesAllowed 这个注解的时候，有两种形式：
     * @RolesAllowed({"admin"})   @RolesAllowed({"ROLE_admin"})
     */
    @RequestMapping
    @RolesAllowed({"admin"})
    public Object getUsers() {
        return Arrays.asList("abc", "xyz");
    }


    @RequestMapping("/{id}")
    @RolesAllowed({"system"})
    public Object delete(@PathVariable("id") Integer id) {
        return "delete";
    }

    @RequestMapping("/add")
    @RolesAllowed({"ROLE_teacher"})
    public Object add() {
        return "add";
    }

    @RequestMapping("/edit")
    @RolesAllowed({"system"})
    public Object edit() {
        return "edit";
    }
}
