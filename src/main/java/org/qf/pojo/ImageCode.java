package org.qf.pojo;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode {
    private BufferedImage bufferedImage; //图片
    private String code;
    private LocalDateTime expireTime; //过期时间

    public ImageCode() {
    }

    /**
     * ImageCode imageCode = new ImageCode(image, "4589", 60);
     * @param bufferedImage
     * @param code
     * @param ttl
     */
    public ImageCode(BufferedImage bufferedImage, String code, long ttl) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(ttl);
    }

    // 判断图片验证是否过期
    public boolean isExpire() {
        return LocalDateTime.now().isBefore(this.expireTime);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
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
