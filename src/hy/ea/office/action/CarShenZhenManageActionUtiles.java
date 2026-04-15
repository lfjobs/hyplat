package hy.ea.office.action;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 屏幕的显示和语音模块的播报，语音说明：语音不能进行自定义，详情请阅读相关资料
 * 1、语音块和语音块之间用中文的逗号进行间隔。如：临时车，川A000001，欢迎光临（共3个语音块）也可以是：临时车，川，A，0,0,0,0，0,1，欢迎光临
 * 2、其中：临时车是定值好的，不能改为其他文本，如：尊贵的临时车等文本。
 * 3、如下定义了几个常用的语音文本，如不够使用，请参考相关资料进行添加。
 */
@Controller
@Scope("prototype")
public class CarShenZhenManageActionUtiles {
    private Logger logger = LoggerFactory.getLogger(CarShenZhenManageActionUtiles.class);


    /**
     * 语音的处理
     */
    public static String pronunciationg(LinkedHashMap<String,String> data){
        try {
            //System.out.println(data);
            //包头：固定  0xAA,0X55
            String begin="AA55";
            //流水号:00
            String serialNumber="00";
            //地址：出厂默认 100 = 0X64
            String ip="64";
            //保留：保留
            String reserve="00";
            //命令：参考文档
            String command="22";
            //数据内容:
            String info="";

            for (int i=1;i<=data.size();i++) {
                String[] split = data.get(i + "").split("_");
                if("1".equals(split[1])){
                    //自定义
                    info+=get16TextInfo(split[0]);
                }else if("2".equals(split[1])){
                    //ASCII码
                    info+=split[0];
                }
            }
            //长度：2 字节，指定数据内容的长度，高字节在前，低字节在后，比如 255 个字节的长度应表述为 0x00 0xff
            String length="";
            int size=info.length()/2;
            String hexString = Integer.toHexString(size);
            if(hexString.length()==1){
                length="000"+hexString;
            }else if(hexString.length()==2){
                length="00"+hexString;
            }else if(hexString.length()==3){
                length="0"+hexString;
            }else if(hexString.length()==4){
                length=hexString;
            }
            //校验：去掉包头和结束，校验位补0进行计算
            String aa=serialNumber+ip+reserve+command+length+info+"0000";
            //System.out.println(aa);
            String check=getCheckCode1(aa);
            if(check.length()%2!=0){
                check="0"+check;
            }
            //结束：固定 0xAF
            String end="AF";
            String infos=begin+serialNumber+ip+reserve+command+length+info+check+end;
            //System.out.println("完整组装好的数据："+infos);
            //base64处理
            byte[] byteArray = hexToByteArray(infos);
            byte base64_data[] = Base64.getEncoder().encode(byteArray);
            String base64_str = new String(base64_data);
            //System.out.println("base64之后的数据："+base64_str);
            return base64_str;
        }catch (Exception e){
            //System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理一到四行的文字显示
     * @param number 行数字
     * @param data 显示内容
     * @return
     */
    public static String displayFourLines(String number,String data){
        //包头：固定  0xAA,0X55
        String begin="AA55";
        //流水号:00
        String serialNumber="00";
        //地址：出厂默认 100 = 0X64
        String ip="64";
        //保留：保留
        String reserve="00";
        //命令：参考文档
        String command="27";
        //数据内容:
        String info="";
        //行：1到4，
        info+=number;
        // 时间：0为长期，单位秒。
        info+="15";
        //颜色：01红色，02绿色，03,黄色。
        if("01".equals(number) || "03".equals(number)){
            info+="01";
        }else{
            info+="02";
        }
        // 保留。
        info+="00";
        // 内容。
        String data16TextInfo = get16TextInfo(data);
        if(data16TextInfo.length()%2!=0){
            info+="0"+info;
        }
        info+=data16TextInfo;
        //System.out.println(info);
        //长度：2 字节，指定数据内容的长度，高字节在前，低字节在后，比如 255 个字节的长度应表述为 0x00 0xff
        String length="";
        int size=info.length()/2;
        String hexString = Integer.toHexString(size);
        if(hexString.length()==1){
            length="000"+hexString;
        }else if(hexString.length()==2){
            length="00"+hexString;
        }else if(hexString.length()==3){
            length="0"+hexString;
        }else if(hexString.length()==4){
            length=hexString;
        }
        //校验：去掉包头和结束，校验位补0进行计算
        String aa=serialNumber+ip+reserve+command+length+info+"0000";
        //System.out.println(aa);
        String check=getCheckCode(aa);
        if(check.length()%2!=0){
            check="0"+check;
        }
        //结束：固定 0xAF
        String end="AF";
        String infos=begin+serialNumber+ip+reserve+command+length+info+check+end;
        //System.out.println("完整组装好的数据："+infos);
        //base64处理
        byte[] byteArray = hexToByteArray(infos);
        byte base64_data[] = Base64.getEncoder().encode(byteArray);
        String base64_str = new String(base64_data);
        //System.out.println("base64之后的数据："+base64_str);
        return base64_str;
    }

    /**
     * 红绿灯数据:绿灯超时时间8秒
     * @return
     */
    public static Map<String,Object> trafficLight(){
        Map<String,Object> serialMap=new HashMap<>();
        String trafficLight="AA5522640013000108170FAF";
        byte[] byteArray = hexToByteArray(trafficLight);
        byte base64_data[] = Base64.getEncoder().encode(byteArray);
        String base64_str = new String(base64_data);
        serialMap.put("serialChannel",0);
        serialMap.put("data",base64_str);
        serialMap.put("dataLen",base64_str.length());
        return serialMap;
    }


    /**
     * 将显示文本或者语音文本转为相对于的格式
     * @param text 文本信息
     * @return
     */
    public static   String get16TextInfo(String text){
        byte[] utf8Bytes = null;
        try {
            utf8Bytes=text.getBytes("GB2312");
        }catch (Exception e){
            //System.out.println(e.getMessage());
        }
        String txtInfo = new BigInteger(1,utf8Bytes).toString(16);
        return txtInfo;
    }
    /**
     * 将得到的16进制字符串转为bate数组
     * @param hexString 16进制的字符串
     * @return
     */
    public static byte[] hexToByteArray(String hexString) {
        /*int length = hexString.length();
        //System.out.println(length / 2);
        byte[] byteArray;
        if(length%2==0){
            byteArray = new byte[length/2];
        }else{
           byteArray = new byte[length/2+1];
        }
        int number=0;
        for (int i = 0; i < length; i += 2) {
            if(i+2>length){
                //长度为单数
                String hex = hexString.substring(i, length);
                int decimal = Integer.parseInt(hex, 16);
                byteArray[number] = (byte) decimal;
            }else{
                String hex = hexString.substring(i, i + 2);
                int decimal = Integer.parseInt(hex, 16);
                byteArray[number] = (byte) decimal;
            }
            number++;
        }*/
        return DatatypeConverter.parseHexBinary(hexString);
        //return byteArray;
    }

    /**
     * 计算byte数组
     * @param data
     * @return
     */
    public static int calculate(byte[] data) {
        int crc = 0xFFFF;

        for (byte b : data) {
            crc ^= b & 0xFF;
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x0001) == 0x0001) {
                    crc >>= 1;
                    crc ^= 0xA001;
                } else {
                    crc >>= 1;
                }
            }
        }

        return crc;
    }

    /**
     * 计算校验码：计算出的结果进行字节比较低字节位在前高字节位在后
     * @param info 参与计算校验码的数据（从DA开始位到Data结束位）
     * @return
     */
    public static String getCheckCode(String info) {
        byte[] byteArray = hexToByteArray(info);
        int crc = calculate(byteArray);
        String hexString = Integer.toHexString(crc);
        if(hexString.length()%2!=0){
            hexString="0"+hexString;
        }
        //System.out.println("调换前："+hexString);
        /*byte[] x = HexUtil.decodeHex(hexString);
        ByteBuffer buffer = ByteBuffer.wrap(x);
        buffer.order(ByteOrder.BIG_ENDIAN);
        int y = buffer.getChar();
        String checkSum = Integer.toHexString(y);
        //System.out.println("调换后："+checkSum);*/
        return hexString;
        /*//System.out.println(hexString+"====================");
        //为校验码补0
        if(hexString.length()<=3){
            hexString="0"+hexString;
        }
        //高低位调换，高位在前低位在后
        String substring1 = hexString.substring(0, 2);
        String substring2 = hexString.substring(2, 4);
        byte b1 = (byte) Integer.parseInt(substring1, 16);
        byte b2 = (byte) Integer.parseInt(substring2, 16);
        String checkCode="";
        // 字节比较
        if (b1 > b2) {
            checkCode=substring1+substring2;
        } else if (b1 < b2) {
            checkCode=substring2+substring1;
        } else {
            checkCode=substring1+substring2;
        }
        return checkCode;*/
    }


    public static String getCheckCode1(String info) {
        byte[] byteArray = hexToByteArray(info);
        int crc = calculate(byteArray);
        String hexString = Integer.toHexString(crc);
       /* //System.out.println("调换前："+hexString);
        byte[] x = HexUtil.decodeHex(hexString);
        ByteBuffer buffer = ByteBuffer.wrap(x);
        buffer.order(ByteOrder.BIG_ENDIAN);
        int y = buffer.getInt();
        String checkSum = Integer.toHexString(y, 16);
        //System.out.println("调换后："+checkSum);*/
        return hexString;
/*        //System.out.println(hexString+"====================");
        //为校验码补0
        if(hexString.length()<=3){
            hexString="0"+hexString;
        }
        //高低位调换，低位在前高位在后
        String substring1 = hexString.substring(0, 2);
        String substring2 = hexString.substring(2, 4);
        byte b1 = (byte) Integer.parseInt(substring1, 16);
        byte b2 = (byte) Integer.parseInt(substring2, 16);
        String checkCode="";
        // 字节比较
        if (b1 < b2) {
            checkCode=substring1+substring2;
        } else if (b1 > b2) {
            checkCode=substring2+substring1;
        } else {
            checkCode=substring1+substring2;
        }
        return checkCode;*/
    }

    public static void main(String[] args) {
        try {//川A12345.2十3天十5/5十6分
           /* String text = "请通行"; // 示例文本，使用GB2312编码
            byte[] bytesGB2312 = text.getBytes("GB2312"); // 将字符串转换为GB2312字节数组
            String hexString = bytesToHex(bytesGB2312); // 转换为16进制字符串
            //System.out.println("16进制字符串: " + hexString);
            //B4A8413132333435 2E 32CAAE33CCECCAAE35 2F 35CAAE36B7D6
            //B4A84131323334352E32CAAE33CCECCAAE352F35CAAE36B7D6
            // 接下来，如果你想将这个16进制字符串转换回原来的字符串，你需要先将其转换回字节数组，然后使用UTF-8进行解码
            byte[] bytesHex = hexStringToBytes("bea94e48314e3130"); // 将16进制字符串转换回字节数组
            String originalText = new String(bytesHex, "GB2312"); // 使用GB2312解码字节数组为字符串
            //System.out.println("原始文本: " + originalText);
            //displayFourLines("03","请通行");*/



            /*String aa="AA5500640027000c02150200c1d9cab1b3b5c1be460AF";
            byte[] bytes = hexToByteArray(aa);
            //System.out.println(bytes.toString());*/

            String aa="临时车辆";
            String s = get16TextInfo(aa);
            //System.out.println(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int counter = 0;

    public static String generateOrderedString() {
        if (counter>=255){
            counter = 0;
        }
        String string = BigInteger.valueOf(counter++).toString(16);
        return StringUtils.leftPad(string, 2,"0");
    }

    // 将字节数组转换为16进制字符串的方法
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // 将16进制字符串转换为字节数组的方法
    public static byte[] hexStringToBytes(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }
}
