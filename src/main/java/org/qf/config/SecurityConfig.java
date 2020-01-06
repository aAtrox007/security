package org.qf.config;

import org.qf.filter.ImageCodeFilter;
import org.qf.filter.PhoneNumAuthenticationFilter;
import org.qf.filter.SmsCodeFilter;
import org.qf.handler.CustomizeAuthencationFailureHandler;
import org.qf.handler.CustomizeAuthencationSuccessHandler;
import org.qf.pojo.SmsCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Component
 * @Configuration
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    private DataSource dataSource;

    @Resource
    private UserAuthencation userAuthencation;

    // 成功处理
    @Resource
    private CustomizeAuthencationSuccessHandler customizeAuthencationSuccessHandler;

    // 失败处理
    @Resource
    private CustomizeAuthencationFailureHandler customizeAuthencationFailureHandler;

    @Resource
    private SmsCodeAuthenticationConfig smsCodeAuthenticationConfig;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        //jdbcTokenRepository.setCreateTableOnStartup(true);

        return jdbcTokenRepository;
    }

    /** */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        ImageCodeFilter imageCodeFilter = new ImageCodeFilter();
        imageCodeFilter.setCustomizeAuthencationFailureHandler(customizeAuthencationFailureHandler);

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setCustomizeAuthencationFailureHandler(customizeAuthencationFailureHandler);

        // 意思是ImageCodeFilter在UsernamePasswordAuthenticationFilter这个过滤器之前运行
        http.addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class) //
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() //
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(customizeAuthencationSuccessHandler)  //用户登录成功的处理
                .failureHandler(customizeAuthencationFailureHandler)
//                .loginProcessingUrl("/authencation/form")  //用户在登录页面处理登录的时候 url
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userAuthencation)
                .tokenValiditySeconds(3600)
                .and()
                .authorizeRequests()  //认证处理
                .antMatchers("/login.html", "/image/code", "/smsCode", "/css/**", "/js/**").permitAll()  //登录的ProcessingUrl
                .anyRequest()  //所有的请求
                .authenticated()   //认证之后就可以访问
                .and()
                .csrf().disable()
                .apply(smsCodeAuthenticationConfig); //将短信验证的调用链路应用到springsecurity中
    }

}
