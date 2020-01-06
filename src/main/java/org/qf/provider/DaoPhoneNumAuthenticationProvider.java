package org.qf.provider;

import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.protocol.Protocol;
import com.mysql.cj.protocol.ServerSession;
import org.qf.authentication.PhoneNumAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class DaoPhoneNumAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        return null;
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

    public static void main(String[] args) {
        String s = new String("abc");
        System.out.println(String.class.isAssignableFrom(s.getClass()));
    }
}
