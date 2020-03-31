package org.qf.filter;

import org.apache.logging.log4j.util.Strings;
import org.qf.common.Constants;
import org.qf.handler.CustomizeAuthencationFailureHandler;
import org.qf.pojo.ImageCode;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter该过滤器的作用是只走filterChain之前的代码，
 * 当过滤如果通过验证，会将该过滤器从整个过滤器链中移除掉。
 */
public class ImageCodeFilter extends OncePerRequestFilter {
    //失败返回
    private CustomizeAuthencationFailureHandler customizeAuthencationFailureHandler;

    public void setCustomizeAuthencationFailureHandler(CustomizeAuthencationFailureHandler customizeAuthencationFailureHandler) {
        this.customizeAuthencationFailureHandler = customizeAuthencationFailureHandler;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI(); //获取请求的uri  /login
        if (request.getMethod().equals("POST") && Constants.LOGIN_URI.equals(uri)) {
            try {
                validateProcess(request, response);
            } catch (AuthenticationException ex) {
                customizeAuthencationFailureHandler.onAuthenticationFailure(request, response, ex);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateProcess(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException, ServletException {
        Object obj = request.getSession().getAttribute(Constants.IMAGE_CODE_SESSION_KEY);
        String code = request.getParameter("imageCode");

        if (Strings.isBlank(code)) {
            throw new InternalAuthenticationServiceException("验证码不能为空.");
        }
        if (null == obj) {
            throw new InternalAuthenticationServiceException("验证码不存在.");
        }
        ImageCode imageCode = (ImageCode) obj;
        if (imageCode.isExpire()) {
            throw new InternalAuthenticationServiceException("验证码已失效.");
        }
        if (!code.equals(imageCode.getCode())) {
            throw new InternalAuthenticationServiceException("验证码错误.");
        }
    }
}
