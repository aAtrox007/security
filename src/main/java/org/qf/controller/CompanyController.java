package org.qf.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/company")
@RestController
public class CompanyController {

//    @PreAuthorize("hasAuthority('company:edit')")
    @RequestMapping("/edit")
    public Object edit() {
        return "edit";
    }

//    @PreAuthorize("hasAuthority('company:delete')")
    @RequestMapping("/delete")
    public Object delete() {
        return "delete";
    }

//    @PreAuthorize("hasAuthority('company:add')")
    @RequestMapping("/add")
    public Object add() {
        return "add";
    }
}
