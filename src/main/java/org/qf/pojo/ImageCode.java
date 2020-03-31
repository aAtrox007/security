package org.qf.pojo;

import java.awt.image.BufferedImage;

public class ImageCode extends SmsCode {
    private BufferedImage bufferedImage; //图片


    /**
     * ImageCode imageCode = new ImageCode(image, "4589", 60);
     *
     * @param bufferedImage
     * @param code
     * @param ttl
     */
    public ImageCode(BufferedImage bufferedImage, String code, long ttl) {
        super(code, ttl);
        this.bufferedImage = bufferedImage;
    }


    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
