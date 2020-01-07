package org.qf.pojo;

import java.util.List;

public class SysUser {
    private int id;
    private String username;
    private String password;
    private List<String> roles;
    private List<String> process;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getProcess() {
        return process;
    }

    public void setProcess(List<String> process) {
        this.process = process;
    }
}
