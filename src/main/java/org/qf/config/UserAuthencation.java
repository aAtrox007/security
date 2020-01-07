package org.qf.config;

import org.qf.pojo.SysUser;
import org.qf.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 彩虹表。md5
 * authentication  认证
 * authorization   授权
 * 当用户在页面中填写完用户名和密码会自动的传入到实现了UserDetailsService的实现类
 * 的loadUserByUsername这个方法中。
 *
 * 1.将用户原密码做md5.   c4ca4238a0b923820dcc509a6f75849b
 * 2.生成一个随机的字符串16位。 8a0b9509a75849bf
 * 3.每两位的密码 + 一位随机字符串。 c4 8 ca a 42 0 38 b ... 得到长度为48位字符串。
 * 4.将48位字符在做md5. 的到32位的字符。  在将盐值撒到字符串中。得到长度48
 */
@Component
public class UserAuthencation implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserAuthencation.class);

    @Resource
    private SysUserService sysUserService;

    /**
     * 1. 根据用户名查询用户的信息
     * 2. 返回UserDetails这样的一个类。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户名: " + username);
        SysUser sysUser = sysUserService.getSysUserByUsernameOrPhoneNum(username);

//        String password = "$2a$10$ODIws5e9r1LvPAGJWogJjuJEaoVGDsTIJLW3udUQ2yjRy2pn/HDIG";   //例如说密码是从数据库查询出来的
        // 用户如果不存在，就手动抛出异常
        if(null == sysUser) {
            logger.info("获取到用户信息为空.");
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        //List<String> roles = sysUser.getRoles(); //获取用户所有的角色
        List<String> process = sysUser.getProcess();

        return new User(username, sysUser.getPassword(), authorities(process));
    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("1"));
    }

    private List<GrantedAuthority> authorities(List<String> process) {
        List<GrantedAuthority> list = new ArrayList<>();
        process.forEach(pro -> {
            list.add(new SimpleGrantedAuthority(pro));
        });

        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_admin"), new SimpleGrantedAuthority("ROLE_teacher"));
        return list;
    }


    /** 基于角色
    private List<GrantedAuthority> authorities(List<String> roles) {
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(role -> {
            list.add(new SimpleGrantedAuthority("ROLE_" + role));
        });

        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_admin"), new SimpleGrantedAuthority("ROLE_teacher"));
        return list;
    }
     */
}
