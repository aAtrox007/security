package org.qf.service;

import org.qf.pojo.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class SysUserService {

    private static Map<String, SysUser> userMap = new HashMap<>();

    // 在当前的这个bean构造完之后，执行该代码
    @PostConstruct
    public void init() {
        SysUser sysUser = new SysUser();
        sysUser.setId(1);
        sysUser.setUsername("jack");
        sysUser.setPassword("$2a$10$ODIws5e9r1LvPAGJWogJjuJEaoVGDsTIJLW3udUQ2yjRy2pn/HDIG");

        userMap.put("jack", sysUser);
    }

    public SysUser getSysUserByUsername(String username) {
        return userMap.get(username);
    }
}
