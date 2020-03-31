package org.qf.provider;

import org.qf.authentication.PhoneNumAuthenticationToken;
import org.qf.config.UserAuthencation;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Resource;

public class DaoPhoneNumAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private UserAuthencation userAuthencation;

    public UserAuthencation getUserAuthencation() {
        return userAuthencation;
    }

    public void setUserAuthencation(UserAuthencation userAuthencation) {
        this.userAuthencation = userAuthencation;
    }


    // 对于手机号验证不用校验密码
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PhoneNumAuthenticationToken token = (PhoneNumAuthenticationToken) authentication;

        String phoneNum = (String) token.getPrincipal(); //获取手机号

        System.out.println("用户手机号:" + phoneNum);

        UserDetails userDetails = userAuthencation.loadUserByUsername(phoneNum);  //获取用户信息

        // 对于手机号登录，不用校验用户的密码，只用判断用户在不在
        if (null == userDetails) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        // 这是一个新的封装了用户的信息的token
        PhoneNumAuthenticationToken phoneNumAuthenticationToken
                = new PhoneNumAuthenticationToken(userDetails, userDetails.getAuthorities());

        //将老的详情信息设置到新的token中
        phoneNumAuthenticationToken.setDetails(token.getDetails());

        return phoneNumAuthenticationToken;
    }

    /**
     * AuthencationManager负责协调由那个Provider来处理对应的Filter, 具体协调的过程就是通过该方法来协调的.
     * 判断传入的参数 是否和 PhoneNumAuthenticationToken.class 为同一个类型。如果是同一个类型，就执行authenticate方法。
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        /**
         * isAssignableFrom() 该方法是判断两个Class类型的对象是否为同一个。
         */
        return PhoneNumAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
