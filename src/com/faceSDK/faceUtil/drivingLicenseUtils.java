package com.faceSDK.faceUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class drivingLicenseUtils {

    private drivingLicenseUtils() {
    }
    public static void main(String[] args) throws IOException {
        File file=new File("E:\\work\\sfz\\image\\7d20d0321bce4206842b2bd450590aa0_th.jpg");
        //处理当前图片
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage image = ImageIO.read(file);
        IdCardOcrUtils.lumAdjustment(image, -5);
        BufferedImage bufferedImage = IdCardOcrUtils.convertToGrayScale(image);

        ImageIO.write(bufferedImage, "png", baos);
        byte[] bytes = baos.toByteArray();
        Map<String, String> userInfoMap = drivingLicenseUtils.getStringStringMap(bytes);
        System.out.println(userInfoMap);
    }
    /**
     * 行驶证完整信息识别
     *
     * @param bytes 输入流，的bytes数组
     * @return 行驶证信息
     */
    public static Map<String, String> getStringStringMap(byte[] bytes) {

        StringBuilder result = new StringBuilder();

        HttpHeaders headers = new HttpHeaders();
        //设置请求头格式
        headers.setContentType(MediaType.APPLICATION_JSON);
        //构建请求参数
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        //添加请求参数images，并将Base64编码的图片传入
        String s1 = IdCardOcrUtils.ImageToBase64(bytes);
        map.add("images",s1);
        //构建请求
        String s = HttpUtil.doPostInfo("http://192.168.124.5:8868/predict/ocr_system", JSONObject.fromObject(map).toString());
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> json = gson.fromJson(s, type);
        System.out.println("============"+s);
        List<List<Map>> jsons = (List<List<Map>>) json.get("results");
        System.out.println(jsons);

        for (int i = 0; i < jsons.get(0).size(); i++) {
            System.out.println("当前的文字是：" + jsons.get(0).get(i).get("text"));
            // 这里光靠这个trim()有些空格是去除不掉的，所以还需要使用替换这个，双重保险
            result.append(jsons.get(0).get(i).get("text").toString().trim().replace(" ", ""));
        }
        String trim = result.toString().trim();
        trim = trim.replace(".", "");
        System.out.println("=================拼接后的文字是=========================");
        System.out.println(trim);
        System.out.println("=======================接下来就是使用正则表达提取文字信息了===============================");
        String cp = extractLicensePlate(trim);
        System.out.println("车牌号："+cp);
        String sb = extractLicenseNumber(trim);
        System.out.println("车辆识别代号："+sb);
        String fd = engineNumber(trim);
        System.out.println("发动机号码："+fd);
        String owner = owner(trim);
        System.out.println("车辆的所有人："+owner);
        List<String> listTime=sueDate(trim);
        String time1=listTime!=null?listTime.get(0):"沒有获取到注册日期";
        String time2=listTime!=null&&listTime.size()>1?listTime.get(1):"沒有获取到发证日期";
        System.out.println("注册日期:"+time1);
        System.out.println("发证日期:"+time2);
        Map<String, String> userInfoMap = new HashMap<>();
        return userInfoMap;
    }

    /**
     * 获取发动机号
     * @param text
     * @return
     */
    public static String engineNumber(String text){
        //发动机号无法使用正则匹配
        if(text.indexOf("发动机号码")!=-1){
            String substring = text.substring(text.indexOf("发动机号码") + 5, text.indexOf("Engine"));
            if(substring!=null && substring.length()>17){
                //说明截取出问题，再次处理
            }else{
                return substring;
            }
        }
        return null;
    }

    /**
     * 获取车牌号
     * @param text
     * @return
     */
    public static String extractLicensePlate(String text) {
        // 车牌号的正则表达式，支持中国车牌号格式，包括汉字省份简称和数字
        String regex = "([\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1})|([A-Z]{2}[A-Z0-9]{2}[A-Z0-9挂学警港澳]{1})|([A-Z]{1}[A-Z0-9]{5})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    /**
     * 获取车辆识别代号
     * @param text
     * @return
     */
    public static String extractLicenseNumber(String text) {
        // 车牌号通常遵循特定的规则，例如中国车牌：XXYXXXX，其中XX代表字母，Y代表数字，XXXX代表数字
        // 这里使用的是简化的规则，根据实际情况可以调整正则表达式
        //String regex = "[A-Za-z]{1}[A-Za-z0-9]{4}[A-Za-z0-9]{2}";
        String regex = "([A-Z\\d]{17})"; // 正则表达式，匹配17位字母和数字的组合
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 获取车辆所有人
     * @param text
     * @return
     */
    private static String owner(String text) {
        int i = text.indexOf("所人");
        int i1 = text.indexOf("Owner");
        String substring="";
        if(i>0 && i1>0){
            substring = text.substring(i+2, i1);
        }else if(i>0){
            int i2 = text.indexOf("住");
            substring = text.substring(i+2, i2);
        }else if(i1>0){
            int i2 = text.indexOf("所有人");
            if(i2==-1){
                i2=text.indexOf("VehicleType");
            }
            substring = text.substring(i2+11,i1);
        }
        return substring;
    }

    /**
     * 获取字符串内的所有时间（注册日期，发证日期）
     * @param trim
     * @return
     */
    private static List<String> sueDate(String trim) {
        String pattern = "\\d{4}-\\d{2}-\\d{2}";
        Pattern PATTERN = Pattern.compile(pattern);
        Matcher matcher = PATTERN.matcher(trim);
        List<String> listTIme=new ArrayList<>();
        while (matcher.find()) {
            listTIme.add(matcher.group().toString());
        }
        return listTIme;
    }
}


