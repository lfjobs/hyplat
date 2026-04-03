package hy.ea.websitemall.card.service;

import java.io.OutputStream;

public interface QRCodeService {
	 /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param imgPath 图片路径 
     */ 
    String encoderQRCode(String content, String imgPath);
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param output 输出流 
     */ 
    void encoderQRCode(String content, OutputStream output);
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param imgPath 图片路径 
     * @param imgType 图片类型 
     */ 
    String encoderQRCode(String content, String imgPath, String imgType);
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param output 输出流 
     * @param imgType 图片类型 
     */ 
    void encoderQRCode(String content, OutputStream output, String imgType);
    /** 
     * 生成带LOGO的二维码(QRCode)图片 
     * @param content 二维码图片的内容
     *  @param imgType 图片格式
     * @param imgPath 二维码图片路径 不是最终路径
     * @param ccbPath  二维码图片中间的logo路径
     */  
    String createQRCode(String content,String imgType, String imgPath,String ccbPath);
}
