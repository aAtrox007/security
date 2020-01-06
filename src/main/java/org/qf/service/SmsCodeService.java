package org.qf.service;

import org.springframework.stereotype.Service;

@Service
public class SmsCodeService {

    public void sendMsg(String code, String phoneNum) {
        System.out.println("往手机号发送验证码: " + code);
        // 调用阿里短信接口
    }
}
