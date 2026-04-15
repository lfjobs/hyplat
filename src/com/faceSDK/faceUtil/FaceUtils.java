package com.faceSDK.faceUtil;

import hy.ea.service.UpLoadFileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class FaceUtils{
    private static final Logger logger = LoggerFactory.getLogger(FaceUtils.class);
    /**
     * md5加密二（apache提供了一个加密包commons-codec）
     * 组装请求接口签名sign：请求签名，32 位小写字母，生成方式：
     * md5(appId+requestId+type +appSecret+timestamp).toLowerCase()
     * @param appId:平台id
     * @param requestId 推荐用UUID值
     * @param type:请求类型 如添加或更新人脸（PERSON_CREATE_OR_UPDATE）
     * @param appSecret 平台秘钥
     * @param timestamp 时间戳对应请求体里面的时间：timestamp
     * @return
     */
    public static String getFaceMd5Info(String appId,String requestId,String type,String appSecret,String timestamp) {
        try {
            // md5加密方法使用规则
            String plainText=appId+requestId+type+appSecret+timestamp;
            //logger.info("md5加密的字符串：{}",plainText);
            return DigestUtils.md5Hex(plainText);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用md5的时候出现错误：{}",e.getMessage());
            return null;
        }
    }

    /**
     * 将所有图片包括JPG格式转为JPG并获取base64编码
     * @param imgUrl 图片地址
     */
    public static String getFaceIMGBase64(String imgUrl){
        String imgBase64=null;
        try {
            //logger.info("imgUrl："+imgUrl);
            BufferedImage image = null;
            if(imgUrl.startsWith("http://") || imgUrl.startsWith("https://")){
                image=ImageIO.read(new URL(imgUrl));
            }else{
                image=ImageIO.read(new File(imgUrl));
            }
            // 转换为JPG格式（如果原图不是JPG）
            String jpgExtension = "jpg";
            BufferedImage jpgImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = jpgImage.createGraphics();
            graphics.drawImage(image, null, null);
            graphics.dispose();

            // 将BufferedImage转换为Base64编码的字符串
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(jpgImage, jpgExtension, baos);
            byte[] imageBytes = baos.toByteArray();
            imgBase64 = Base64.getEncoder().encodeToString(imageBytes);
        }catch (IOException e){
            logger.error("将图片转换为jpg时出现IO错误：{}",e);
        }finally {
            return imgBase64;
        }
    }
    public static String setFaceIMGBase64(String base64Data,String fileName, UpLoadFileService fileService,String path){
        String companyID="trafficeResultsImage";
        String storedpath="staff/trafficeResultsImage";
        String type="jpg";
        String filePath=path+"upload_files/" + companyID + "/" + storedpath + "/";
        try {
           //解码
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            // 将字节写入文件
            File dFile = new File(filePath);
            if (!dFile.exists()) {
                // 如果文件夹不存在则建一个
                dFile.mkdirs();
            }
            filePath=filePath+ fileName + "." + type;
            File imageFile = new File(filePath);
            FileOutputStream imageOutFile = new FileOutputStream(imageFile);
            imageOutFile.write(imageBytes);
            imageOutFile.close();
        }catch (Exception e){
            logger.error("发生错误：{}",e.getMessage());
        }finally {
            return "upload_files/" + companyID + "/" + storedpath + "/" + fileName + "." + type;
        }
    }
    public static Date updateTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public static File compressImage(File imageFile){
       try {
           //logger.info("压缩前："+imageFile.length());
           if (imageFile.length() > 200 * 1024) {
               BufferedImage originalImage = ImageIO.read(imageFile);
               File compressedFile = File.createTempFile("compressed_", ".jpg");
               ImageIO.write(originalImage, "jpg", compressedFile);
               //logger.info("压缩后："+compressedFile.length());
               return compressedFile;
           }
           return imageFile;
       }catch (IOException ie){
           //logger.info("压缩图片出现错误："+ie.getMessage());
           return null;
       }
    }


    public static String scaleImage(String path, String fileType,File photo, String companyID, String storedpath){
       try {
           // 读取图片
           BufferedImage originalImage = ImageIO.read(photo);
           // 计算缩放比例
           double widthRatio = (double) 800 / originalImage.getWidth();
           double heightRatio = (double) 800 / originalImage.getHeight();
           double scaleRatio = Math.min(widthRatio, heightRatio);

           // 计算新的宽度和高度
           int newWidth = (int) (originalImage.getWidth() * scaleRatio);
           int newHeight = (int) (originalImage.getHeight() * scaleRatio);

           // 创建缩放后的图片
           BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

           // 使用AffineTransformOp进行缩放
           AffineTransform transform = new AffineTransform();
           transform.scale(scaleRatio, scaleRatio);
           AffineTransformOp scaleOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
           scaledImage = scaleOp.filter(originalImage, scaledImage);
           String savePath = path + "upload_files/" + companyID + "/" + storedpath;
           String type = fileType.substring(fileType.lastIndexOf(".") + 1);
           String fileName="" ;
           if(type.equals("apk"))

           {
               fileName=fileType.substring(0,fileType.lastIndexOf("."));

           }
           else if(type.equals("ipa"))
           {
               fileName=fileType.substring(0,fileType.lastIndexOf("."));
           }else
           {
               fileName	= UUID.randomUUID().toString().replaceAll("-", "");
           }

           String photoPath = savePath + "/" + fileName + "." + type;
           File dFile = new File(savePath);
           if (!dFile.exists()) {
               // 如果文件夹不存在则建一个
               dFile.mkdirs();
           }
           ImageIO.write(scaledImage, "jpg",new File(photoPath));
           return "upload_files/" + companyID + "/" + storedpath + "/" + fileName + "." + type;
       }catch (Exception e){
           logger.error("上传图片出现错误："+e.getMessage());
           return null;
       }
    }
}
