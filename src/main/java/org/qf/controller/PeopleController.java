package org.qf.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @PreAuthorize("hasRole('teacher')")
    @RequestMapping("/edit")
    public Object edit() {
        return "edit";
    }

    @PreAuthorize("hasRole('system') or hasRole('teacher')")
    @RequestMapping("/delete")
    public Object delete() {
        return "delete";
    }

    @PreAuthorize("hasRole('system') and hasRole('teacher')")
    @RequestMapping("/add")
    public Object add() {
        return "add";
    }
}
