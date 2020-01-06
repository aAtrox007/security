package org.qf.filter;

import com.sun.org.apache.bcel.internal.classfile.Code;
import org.apache.logging.log4j.util.Strings;
import org.qf.common.Constants;
import org.qf.handler.CustomizeAuthencationFailureHandler;
import org.qf.pojo.ImageCode;
import org.qf.pojo.SmsCode;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 短信验证码验证过滤器
public class SmsCodeFilter extends OncePerRequestFilter {

    private CustomizeAuthencationFailureHandler customizeAuthencationFailureHandler;

    public void setCustomizeAuthencationFailureHandler(CustomizeAuthencationFailureHandler customizeAuthencationFailureHandler) {
        this.customizeAuthencationFailureHandler = customizeAuthencationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI(); //获取请求的uri

        if (request.getMethod().equals("POST") && Constants.SMS_LOGIN_URI.equals(uri)) {

            try{
                validateProcess(request, response);
            }catch (AuthenticationException ex) {
                customizeAuthencationFailureHandler.onAuthenticationFailure(request, response, ex);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateProcess(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException, ServletException {
        Object obj = request.getSession().getAttribute(Constants.SMS_CODE_SESSION_KEY);

        String code = request.getParameter("msgCode");

        if(Strings.isBlank(code)) {
            throw new InternalAuthenticationServiceException("短信验证码不能为空.");
        }
        if(null == obj) {
            throw new InternalAuthenticationServiceException("短信验证码不存在.");
        }
        SmsCode imageCode = (SmsCode)obj;
        if(imageCode.isExpire()) {
            throw new InternalAuthenticationServiceException("短信验证码已失效.");
        }
        if(!code.equals(imageCode.getCode())) {
            throw new InternalAuthenticationServiceException("短信验证码错误.");
        }
    }
}
