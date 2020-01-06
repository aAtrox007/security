package org.qf.controller;

import org.apache.commons.text.RandomStringGenerator;
import org.qf.common.Constants;
import org.qf.pojo.SmsCode;
import org.qf.service.SmsCodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping
@RestController
public class SmsCodeController {

    @Resource
    private SmsCodeService smsCodeService;

    @RequestMapping
    public Object getSmsCode(String phoneNum, HttpServletRequest request) {
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder()
                .withinRange(new char[]{'0', '9'})
                .build();

        String randomCode = randomStringGenerator.generate(6);  //生成随机码

        SmsCode smsCode = new SmsCode(randomCode, 120);
        request.getSession().setAttribute(Constants.SMS_CODE_SESSION_KEY, smsCode);

        smsCodeService.sendMsg(randomCode, phoneNum);

        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "短信发送成功");

        return map;
    }

    public static void main(String[] args) {
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder()
                .withinRange(new char[]{'a', 'z'}, new char[]{'A', 'Z'}, new char[]{'0', '9'}).build();

        System.out.println(randomStringGenerator.generate(10));

    }
}
