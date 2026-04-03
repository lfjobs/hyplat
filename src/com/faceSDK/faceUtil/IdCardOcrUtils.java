package com.faceSDK.faceUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCardOcrUtils {

    private IdCardOcrUtils() {
    }
    /*public static void main(String[] args) throws IOException {
        File file=new File("C:\\Users\\Administrator\\Desktop\\sfz\\image\\123456.jpg");
        //处理当前图片
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage image = ImageIO.read(file);
        IdCardOcrUtils.lumAdjustment(image, -5);
        //File outputImage = new File("C:\\Users\\Administrator\\Desktop\\sfz\\image\\output1.png");
        //ImageIO.write(image, "png", outputImage);
        BufferedImage bufferedImage = IdCardOcrUtils.convertToGrayScale(image);
        //File outputImage2 = new File("C:\\Users\\Administrator\\Desktop\\sfz\\image\\output2.png");
        //ImageIO.write(bufferedImage, "png", outputImage2);
        *//*BufferedImage bufferedImage1 = convertToBinaryImage(bufferedImage);
        File outputImage3 = new File("C:\\Users\\Administrator\\Desktop\\sfz\\image\\output3.png");
        ImageIO.write(bufferedImage1, "png", outputImage3);*//*




        ImageIO.write(bufferedImage, "png", baos);
        byte[] bytes = baos.toByteArray();
        Map<String, String> userInfoMap = IdCardOcrUtils.getStringStringMap(bytes);
        //System.out.println(userInfoMap);
    }*/
    /**
     * 身份证完整信息识别
     *
     * @param bytes 输入流，的bytes数组
     * @return 身份证信息
     */
    public static Map<String, String> getStringStringMap(byte[] bytes) {

        StringBuilder result = new StringBuilder();

        HttpHeaders headers = new HttpHeaders();
        //设置请求头格式
        headers.setContentType(MediaType.APPLICATION_JSON);
        //构建请求参数
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        //添加请求参数images，并将Base64编码的图片传入
        String s1 = ImageToBase64(bytes);
        map.add("images",s1);
        //构建请求
        /*HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        //发送请求, springboot内置的restTemplate
        Map json = restTemplate.postForEntity("http://127.0.0.1:8868/predict/ocr_system", request, Map.class).getBody();*/
        String s = HttpUtil.doPostInfo("http://test.impf2010.com:8868/predict/ocr_system", JSONObject.fromObject(map).toString());
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> json = gson.fromJson(s, type);
        //System.out.println("============"+s);
        List<List<Map>> jsons = (List<List<Map>>) json.get("results");
        //System.out.println(jsons);

        for (int i = 0; i < jsons.get(0).size(); i++) {
            //System.out.println("当前的文字是：" + jsons.get(0).get(i).get("text"));
            // 这里光靠这个trim()有些空格是去除不掉的，所以还需要使用替换这个，双重保险
            result.append(jsons.get(0).get(i).get("text").toString().trim().replace(" ", ""));
        }
        String trim = result.toString().trim();
        //System.out.println("=================拼接后的文字是=========================");
        //System.out.println(trim);
        //System.out.println("=======================接下来就是使用正则表达提取文字信息了===============================");
        List<Map> maps = jsons.get(0);
        String name = predictName(maps);
        if (name.equals("") || name == null) {
            name = fullName(trim);
        }
        //System.out.println("姓名：" + name);
        String nation = national(maps);
        //System.out.println("民族：" + nation);
        String address = address(maps);
        //System.out.println("地址：" + address);
        String cardNumber = cardNumber(maps);
        //System.out.println("身份证号：" + cardNumber);
        String sex = sex(cardNumber);
        //System.out.println("性别：" + sex);
        String birthday = birthday(cardNumber);
        //System.out.println("出生：" + birthday);

        // return json1;

        Map<String, String> userInfoMap = new HashMap<>();
        userInfoMap.put("name", name);
        userInfoMap.put("nation", nation);
        userInfoMap.put("address", address);
        userInfoMap.put("cardNumber", cardNumber);
        userInfoMap.put("sex", sex);
        userInfoMap.put("birthday", birthday);
        return userInfoMap;
    }

    // 上面的方法，使用了static修饰，下面的方法，也需要使用static修饰，这里使用
    // private修饰的话，在其他类中直接通过IdCardOcrUtils.predictName()这个就访问不到了, 或者protected修饰，
    // 不然其他类访问不就行了吗？
    // 这里唯一能通过IdCardOcrUtils.方法名，访问的是public修饰的方法

    /**
     * 获取身份证姓名
     *
     * @param maps 识别的结果集合
     * @return 姓名
     */
    private static String predictName(List<Map> maps) {
        String name = "";
        for (Map map : maps) {
            String str = map.get("text").toString().trim().replace(" ", "");
            if (str.contains("姓名") || str.contains("名")) {
                String pattern = ".*名[\\u4e00-\\u9fa5]{1,4}";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(str);
                if (m.matches()) {
                    name = str.substring(str.indexOf("名") + 1);
                }
            }
        }
        return name;
    }

    /**
     * 为了防止第一次得到的名字为空，以后是遇到什么情况就解决什么情况就行了
     *
     * @param result panddleOCR扫描得到的结果拼接：
     *               如：姓名韦小宝性别男民族汉出生1654年12月20日住址北京市东城区景山前街4号紫禁城敬事房公民身份证号码11204416541220243X
     * @return
     */
    private static String fullName(String result) {
        String name = "";
        if (result.contains("性") || result.contains("性别")) {
            String str = result.substring(0, result.lastIndexOf("性"));
            String pattern = ".*名[\\u4e00-\\u9fa5]{1,4}";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(str);
            if (m.matches()) {
                name = str.substring(str.indexOf("名") + 1);
            }
        }
        if(name==""){
            if(result.contains("姓")){
                String str = result.substring(0, result.lastIndexOf("姓"));
                name=str;
            }
        }
        return name;
    }

    /**
     * 获取民族
     *
     * @param maps 识别的结果集合
     * @return 民族信息
     */
    private static String national(List<Map> maps) {
        String nation = "";
        for (Map map : maps) {
            String str = map.get("text").toString();
            String pattern = ".*民族[\u4e00-\u9fa5]{1,4}";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(str);
            if (m.matches()) {
                nation = str.substring(str.indexOf("族") + 1);
            }
        }
        return nation;
    }

    /**
     * 获取身份证地址
     *
     * @param maps 识别的结果集合
     * @return 身份证地址信息
     */
    private static String address(List<Map> maps) {
        String address = "";
        StringBuilder addressJoin = new StringBuilder();
        for (Map map : maps) {
            String str = map.get("text").toString().trim().replace(" ", "");
            if (str.contains("住址") || str.contains("址") || str.contains("省") || str.contains("市")
                    || str.contains("县") || str.contains("街") || str.contains("乡") || str.contains("村")
                    || str.contains("镇") || str.contains("区") || str.contains("城") || str.contains("组")
                    || str.contains("号") || str.contains("幢") || str.contains("室")
            ) {
                addressJoin.append(str);
            }
        }
        String s = addressJoin.toString();
        if (s.contains("省") || s.contains("县") || s.contains("住址") || s.contains("址") || s.contains("公民身份证")) {
            // 通过这里的截取可以知道，即使是名字中有上述的那些字段，也不要紧，因为这个ocr识别是一行一行来的，所以名字的会在地址这两个字
            // 前面，除非是名字中也有地址的”地“或者”址“字，这个还可以使用lastIndexOf()来从后往左找，也可以在一定程度上避免这个。
            // 具体看后面的截图，就知道了
            String sbInfo="";
            if(s.indexOf("公民身份证")!=-1){
                sbInfo="公民身份证";
            }else if(s.indexOf("公民身份")!=-1){
                sbInfo="公民身份";
            }else if(s.indexOf("公民身")!=-1){
                sbInfo="公民身";
            }else if(s.indexOf("公民")!=-1){
                sbInfo="公民";
            }else if(s.indexOf("公")!=-1){
                sbInfo="公";
            }else{
                return "未能解析";
            }
            address = s.substring(s.indexOf("址") + 1, s.indexOf(sbInfo));
        } else {
            address = s;
        }
        return address;
    }

    /**
     * 获取身份证号
     *
     * @param maps ocr识别的内容列表
     * @return 身份证号码
     */
    private static String cardNumber(List<Map> maps) {
        String cardNumber = "";
        for (Map map : maps) {
            String str = map.get("text").toString().trim().replace(" ", "");
            // 之里注意了，这里的双斜杆，是因为这里是java，\会转义，所以使用双鞋干\\，去掉试一试就知道了
            String pattern = "\\d{17}[\\d|X|x]";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(str);
            if (m.find()) {
                cardNumber = m.group(0);
            }
        }
        return cardNumber;
    }

    /**
     * 二代身份证18位
     * 这里之所以这样做，是因为如果直接从里面截取，也可以，但是从打印的内容中，有时候
     * 性别性别男，是在同一行，有些照片是
     * 性
     * 别
     * 男
     * 等，如果单纯是使用字符串的str.contains("男") ==》 然后返回性别男，
     * str.contains("女") ==> 然后返回性别女
     * 这个万姓名中有男字，地址中有男字，等。而这个人的性别是女。这是可能会按照识别顺序
     * 排序之后，识别的是地址的男字，所以这里直接从身份证倒数第二位的奇偶性判断男女更加准确一点
     * 从身份证号码中提取性别
     *
     * @param cardNumber 身份证号码，二代身份证18位
     * @return 性别
     */
    private static String sex(String cardNumber) {
        if(cardNumber==null || "".equals(cardNumber)){
            return "";
        }
        String sex = "";
        // 取倒身份证倒数第二位的数字的奇偶性判断性别，二代身份证18位
        String substring = cardNumber.substring(cardNumber.length() - 2, cardNumber.length() - 1);
        int parseInt = Integer.parseInt(substring);
        if (parseInt % 2 == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        return sex;
    }

    /**
     * 从身份证中获取出生信息
     *
     * @param cardNumber 二代身份证，18位
     * @return 出生日期
     */
    private static String birthday(String cardNumber) {
        if(cardNumber==null || "".equals(cardNumber)){
            return "";
        }
        String birthday = "";
        String date = cardNumber.substring(6, 14);
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        birthday = year + "年" + month + "月" + day + "日";
        return birthday;
    }


    static String ImageToBase64(byte[] data) {
        // 直接调用springboot内置的springframework内置的犯法
        String encodeToString = Base64.getEncoder().encodeToString(data);
        return encodeToString;
    }

    /*public static void main(String[] args) {
        String pattern = "\\d{17}[\\d|X|x]";
        Pattern r = Pattern.compile(pattern);
        String str="公民身份证号511527199405051834";
        Matcher m = r.matcher(str);
        if (m.find()) {
            //System.out.println(m.group(0));
        }
    }*/

    // 灰度化处理
    public static BufferedImage convertToGrayScale(BufferedImage image) {
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return grayImage;
    }
    // 二值化处理
    public static BufferedImage convertToBinaryImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = binaryImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return binaryImage;
    }

    /**
     * 图片亮度调整
     * @param image
     * @param param
     * @throws IOException
     */
    public static void lumAdjustment(BufferedImage image, int param) throws IOException {
        if (image == null) {
            return;
        } else {
            int rgb, R, G, B;
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    rgb = image.getRGB(i, j);
                    R = ((rgb >> 16) & 0xff) + param;
                    G = ((rgb >> 8) & 0xff) + param;
                    B = (rgb & 0xff) + param;

                    rgb = ((clamp(255) & 0xff) << 24) | ((clamp(R) & 0xff) << 16) | ((clamp(G) & 0xff) << 8)
                            | ((clamp(B) & 0xff));
                    image.setRGB(i, j, rgb);
                }
            }
        }
    }

    // 判断a,r,g,b值，大于256返回256，小于0则返回0,0到256之间则直接返回原始值
    private static int clamp(int rgb) {
        if (rgb > 255)
            return 255;
        if (rgb < 0)
            return 0;
        return rgb;
    }
    /**
     * 增强图片的对比度
     * @param image
     * @return
     */
    public static BufferedImage enhanceContrast(BufferedImage image) {
        RescaleOp op = new RescaleOp(1.2f, 0, null);
        return op.filter(image, null);
    }

    /**
     * 将图片逆时针旋转
     * @param file
     */
    public final static BufferedImage anticlockwise90(final File file) {
        try {
            // 原图片
            BufferedImage image = ImageIO.read(file);
            int width = image.getWidth();
            int height = image.getHeight();
            //System.out.println("width:"+width+",height:"+height);
            BufferedImage imageNew = new BufferedImage(height, width, image.getType());
            if(width>height){
                int maxX = width - 1;
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int rgb = image.getRGB(x, y);
                        imageNew.setRGB(y, maxX - x, rgb);
                    }
                }
            }
            long size = file.length()*1;
            if(size/1024>500){
                int height1 = imageNew.getHeight();
                int width1 = imageNew.getWidth();
                BufferedImage resizedImage = resizeImage(imageNew, (int) (width1/1.5), (int) (height1/1.5));
                /*File clockwise90File = new File("C:\\Users\\Administrator\\Desktop\\sfz\\image\\10101.jpg");
                ImageIO.write(resizedImage,"jpg", clockwise90File);*/
                return resizedImage;
            }
            return imageNew;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resizedImage, 0, 0, null);
        return outputImage;
    }

    public static BufferedImage setWH(BufferedImage originalImage) throws IOException {
        int newWidth = originalImage.getWidth()/2;
        int newHeight = originalImage.getHeight()/2;
        // 创建一个新的缓冲图片
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        // 绘制并调整图片大小
        resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
        return resizedImage;
    }
}


