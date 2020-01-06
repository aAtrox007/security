package org.qf.config;

import org.qf.filter.PhoneNumAuthenticationFilter;
import org.qf.handler.CustomizeAuthencationFailureHandler;
import org.qf.handler.CustomizeAuthencationSuccessHandler;
import org.qf.provider.DaoPhoneNumAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 该类的作用是将 自定义Filter与Provider串起来，然后加入spring security的整个过滤器链中
 */
@Component
public class SmsCodeAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Resource
    private CustomizeAuthencationFailureHandler customizeAuthencationFailureHandler;

    @Resource
    private CustomizeAuthencationSuccessHandler customizeAuthencationSuccessHandler;

    @Resource
    private UserAuthencation userAuthencation;

    // 将Filter与Provider串起来
    @Override
    public void configure(HttpSecurity http) throws Exception {
        PhoneNumAuthenticationFilter phoneNumAuthenticationFilter = new PhoneNumAuthenticationFilter();

        // 设置AuthenticationManager, 设置目的是 使用它来协调 Filter与Provider
        phoneNumAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        //使用手机号登录失败了如何处理
        phoneNumAuthenticationFilter.setAuthenticationFailureHandler(customizeAuthencationFailureHandler);

        // 使用手机号登录成功了如何处理
        phoneNumAuthenticationFilter.setAuthenticationSuccessHandler(customizeAuthencationSuccessHandler);

        DaoPhoneNumAuthenticationProvider daoPhoneNumAuthenticationProvider = new DaoPhoneNumAuthenticationProvider();
        daoPhoneNumAuthenticationProvider.setUserAuthencation(userAuthencation);

        // 设置Provider以及过滤器
        http.authenticationProvider(daoPhoneNumAuthenticationProvider)
                .addFilterAfter(phoneNumAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
