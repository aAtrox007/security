package org.qf.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomizeAuthencationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "登录成功");

        PrintWriter writer = response.getWriter();

        writer.write(objectMapper.writeValueAsString(map));
        writer.flush();
        writer.close();
    }
}
