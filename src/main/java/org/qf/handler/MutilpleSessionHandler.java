package org.qf.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class MutilpleSessionHandler implements SessionInformationExpiredStrategy {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event)
            throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();

        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>();
        map.put("code", -1);
        map.put("msg", "您已经在其他设备登录, 强制下线.");

        PrintWriter writer = response.getWriter();

        writer.write(objectMapper.writeValueAsString(map));
        writer.flush();
        writer.close();
    }
}
