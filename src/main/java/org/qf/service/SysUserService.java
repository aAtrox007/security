package org.qf.service;

import org.qf.pojo.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        //List<String> sysUserRoles = Arrays.asList("system", "admin");
        // sysUser.setRoles(sysUserRoles);
        List<String> sysUserProcess = Arrays.asList("company:delete");
        sysUser.setProcess(sysUserProcess);

        SysUser sysUser2 = new SysUser();
        sysUser2.setId(3);
        sysUser2.setUsername("smith");
        sysUser2.setPassword("$2a$10$ODIws5e9r1LvPAGJWogJjuJEaoVGDsTIJLW3udUQ2yjRy2pn/HDIG");
//        List<String> sysUser2Roles = Arrays.asList("teacher");
//        sysUser2.setRoles(sysUser2Roles);
        List<String> sysUser2Process = Arrays.asList("company:edit");
        sysUser2.setProcess(sysUser2Process);

        SysUser user2 = new SysUser();
        user2.setId(2);
        user2.setUsername("13307981205");
        user2.setPassword("$2a$10$ODIws5e9r1LvPAGJWogJjuJEaoVGDsTIJLW3udUQ2yjRy2pn/HDIG");

        userMap.put("jack", sysUser);
        userMap.put("13307981205", user2);
        userMap.put("smith", sysUser2);
    }

    public SysUser getSysUserByUsernameOrPhoneNum(String usernameOrPhoneNum) {
        return userMap.get(usernameOrPhoneNum);
    }
}
