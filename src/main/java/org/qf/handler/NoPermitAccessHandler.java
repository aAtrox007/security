package org.qf.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 无权限异常访问处理
 */
@Component
public class NoPermitAccessHandler implements AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>();
        map.put("code", -1);
        map.put("msg", "您没有访问权限");

        PrintWriter writer = response.getWriter();

        writer.write(objectMapper.writeValueAsString(map));
        writer.flush();
        writer.close();
    }
}
