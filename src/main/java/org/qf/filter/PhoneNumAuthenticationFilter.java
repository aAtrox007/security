package org.qf.filter;

import org.qf.authentication.PhoneNumAuthenticationToken;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * filter中就是获取phoneNum
 */
public class PhoneNumAuthenticationFilter extends
        AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_PHONENUM_KEY = "phoneNum";

    private String phoneNumParameter = SPRING_SECURITY_FORM_PHONENUM_KEY;
    private boolean postOnly = true;

    public PhoneNumAuthenticationFilter() {
        super(new AntPathRequestMatcher("/phoneLogin", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String phoneNum = obtainPhoneNum(request);

        if (phoneNum == null) {
            phoneNum = "";
        }
        phoneNum = phoneNum.trim();

        PhoneNumAuthenticationToken authRequest = new PhoneNumAuthenticationToken(phoneNum);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Nullable
    protected String obtainPhoneNum(HttpServletRequest request) {
        return request.getParameter(this.phoneNumParameter);
    }

    protected void setDetails(HttpServletRequest request,
                              PhoneNumAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setPhoneNumParameter(String phoneNumParameter) {
        Assert.hasText(phoneNumParameter, "PhoneNum parameter must not be empty or null");
        this.phoneNumParameter = phoneNumParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getPhoneNumParameter() {
        return phoneNumParameter;
    }
}
