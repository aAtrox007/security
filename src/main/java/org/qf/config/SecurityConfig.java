package org.qf.config;

import org.qf.filter.ImageCodeFilter;
import org.qf.filter.SmsCodeFilter;
import org.qf.handler.CustomizeAuthencationFailureHandler;
import org.qf.handler.CustomizeAuthencationSuccessHandler;
import org.qf.handler.MutilpleSessionHandler;
import org.qf.handler.NoPermitAccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * A. 基于角色的权限控制
 * 1).使用@RoleAllowed, 在查询用户的权限的时候，构建角色的时候必须是 ROLE_admin
 * 使用的时候可以：@RoleAllowed("admin") 或者 @RoleAllowed("ROLE_admin")
 * 2). @Secured  在查询用户的权限的时候，构建的时候必须是 ROLE_admin
 * 使用的时候只能是 @Secured({"ROLE_admin"})
 * 3). @PreAuthorize, 构建的时候必须是 ROLE_admin, ROLE_system
 * 使用的时候只能是 @PreAuthorize("hasRole('ROLE_admin') or|and hasRole('ROLE_system')")  @PreAuthorize("hasRole('admin')")
 * 使用的时候只能是 @PreAuthorize("hasRole('ROLE_admin') or|and hasRole('ROLE_system')")  @PreAuthorize("hasRole('admin')")
 * 4). antMatchers("/stu/add").hasRole("admin")
 * B. 基于操作的权限控制
 * 1) 在构建操作权限的时候的时候不需要任何的前缀。例如：user:list  user:add
 * 在使用 @PreAuthorize的时候，形式是：@PreAuthorize("hasAuthority('user:list')")
 * 2) 编码的方式：antMatchers("/company/delete").hasAuthority("company:delete")
 *
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

    @Resource
    private MutilpleSessionHandler mutilpleSessionHandler;

    @Resource
    private NoPermitAccessHandler noPermitAccessHandler;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * @param http
     * @throws Exception
     */
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
//                .antMatchers("/stu/add").hasRole("admin")  //基于角色
//                .antMatchers("/stu/delete").hasRole("system")
//                .antMatchers("/stu/edit", "/stu/export").hasRole("teacher")
                .antMatchers("/company/delete").hasAuthority("company:delete")  //基于操作
                .antMatchers("/company/add").hasAuthority("company:add")
                .antMatchers("/company/edit").hasAuthority("company:edit")
                .anyRequest()  //所有的请求
                .authenticated()   //认证之后就可以访问

                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredSessionStrategy(mutilpleSessionHandler)

                .and()
                .and()
                .logout()
                .logoutSuccessUrl("/login.html")

                .and()
                .exceptionHandling()
                .accessDeniedHandler(noPermitAccessHandler)   //无权限访问处理

                .and()
                .csrf().disable()
                .apply(smsCodeAuthenticationConfig); //将短信验证的调用链路应用到springsecurity中
    }

}
