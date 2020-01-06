package org.qf.pojo;

import java.time.LocalDateTime;

public class SmsCode {
    private String code;
    private LocalDateTime expireTime; //过期时间

    public SmsCode() {
    }

    public SmsCode(String code, long ttl) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(ttl);
    }

    // 判断图片验证是否过期
    public boolean isExpire() {
        return LocalDateTime.now().isAfter(this.expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
