package org.qf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stu")
public class StudentController {

    @RequestMapping("/add")
    public Object add() {
        return "add";
    }

    @RequestMapping("/delete")
    public Object delete() {
        return "delete";
    }

    @RequestMapping("/edit")
    public Object edit() {
        return "edit";
    }

    @RequestMapping("/export")
    public Object export() {
        return "export";
    }
}
