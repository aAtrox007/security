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

        SysUser user2 = new SysUser();
        user2.setId(2);
        user2.setUsername("13307981205");
        user2.setPassword("$2a$10$ODIws5e9r1LvPAGJWogJjuJEaoVGDsTIJLW3udUQ2yjRy2pn/HDIG");

        userMap.put("jack", sysUser);
        userMap.put("13307981205", user2);
    }

    public SysUser getSysUserByUsernameOrPhoneNum(String usernameOrPhoneNum) {
        return userMap.get(usernameOrPhoneNum);
    }
}
