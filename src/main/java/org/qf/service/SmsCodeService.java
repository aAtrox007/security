package org.qf.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class SmsCodeService {

    private static Logger logger = LoggerFactory.getLogger(SmsCodeService.class);

    @Value("${aliyun.dev.accessKeyID}")
    private String accessKeyID;

    @Value("${aliyun.dev.accessKeySecret}")
    private String accessKeySecret;

    public void sendMsg(String code, String phoneNum) {
        logger.info("手机号为：" + phoneNum + ", 短信验证码: " + code);
        // 调用阿里短信接口
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyID, accessKeySecret);
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumbers", phoneNum);
//        request.putQueryParameter("SignName", "正井猫");
//        request.putQueryParameter("TemplateCode", "SMS_181853118");
//        request.putQueryParameter("TemplateParam", "{\"code\": \"" + code + "\"}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//            // 发送完短信，要入库
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
    }
}
