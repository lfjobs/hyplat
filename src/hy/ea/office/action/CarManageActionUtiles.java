package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.math.BigInteger;
import java.util.Base64;

/**
 * 屏幕的显示和语音模块的播报，语音说明：语音不能进行自定义，详情请阅读相关资料
 * 1、语音块和语音块之间用中文的逗号进行间隔。如：临时车，川A000001，欢迎光临（共3个语音块）也可以是：临时车，川，A，0,0,0,0，0,1，欢迎光临
 * 2、其中：临时车是定值好的，不能改为其他文本，如：尊贵的临时车等文本。
 * 3、如下定义了几个常用的语音文本，如不够使用，请参考相关资料进行添加。
 */
@Controller
@Scope("prototype")
public class CarManageActionUtiles {
	private static final Logger logger = LoggerFactory.getLogger(CarManageActionUtiles.class);
    private Logger logger = LoggerFactory.getLogger(CarManageActionUtiles.class);
    /**
     * 组装显示器需要显示的内容，显示器显示的内容可以进行自定义
     * @param txtInfo 内容详情
     * @param LIDInfo 显示的第几行：为显示行号。0 表示第 1 行，1 表示第 2 行，以此类推。
     * @return
     */
    public static String AssemblyInformation(String txtInfo,String LIDInfo){
        //显示文本的组装
        String LID=LIDInfo;
        String DM="15";//为显示模式，00：立即显示，15：连续左移，其余见文档
        String DS="01";//为显示速度，建议取值为 0；
        String DT="05";//停留时间，单位为秒，最大为 255 秒；
        String DR="02";//为显示次数，0 为无限循环显示。
        String FINDEX="03";//为字体索引。注意:每种主板支持字体不一样 03：宋体 16 ，其余的见文档详情
        String FLAGS="00";//显示标志位,目前没有用到，作为保留值，固定取值为 0X00
        String TCRed="FF000000";//文本颜色为红色,占用8位
        String TCGreen="00FF0000";//文本颜色为绿色,占用8位
        //计算显示文本长度
        String TextInfo16 = get16TextInfo(txtInfo);
        Integer textLength=(TextInfo16.length()/2);//data长度
        String textLengthInfo=Integer.toHexString(textLength);
        if(textLengthInfo.length()<=1){
            textLengthInfo="0"+textLengthInfo;
        }
        String TL=textLengthInfo;//为文本长度。需要自行计算
        String TEXT=TextInfo16;//为文本内容，最大 32 字节。
        String info="";
        if(LIDInfo.equals("00")){
            info= LID+DM+DS+DT+DR+FINDEX+FLAGS+TCRed+TL+TEXT;
        }else {
            info= LID+DM+DS+DT+DR+FINDEX+FLAGS+TCGreen+TL+TEXT;
        }
        logger.info("调试信息");
        return info;
    }

    /**
     *
     * @param oneTxt 第一行显示
     * @param towTxt 第二缸显示
     * @param voideTxt  语音文本
     * @param TEXT_CONTEXT_NUMBER  为文本参数数量，即几行文本。目前版本最大支持 4 行。
     * @return
     */
    public static String screenDisplay(String oneTxt,String towTxt,String voideTxt,String TEXT_CONTEXT_NUMBER){
        String DL="";//数据长度，即将显示文本和语音文本组合的总的data数据位长度
        String separate="0D";//显示文本之间的分隔标识
        String end="00";//文本或语音的结束标识
        //开始组装语音和显示文字
        String SF="00";//取值为 0 时表示下载到临时区,取值为 1 时表示下载到存储区.
        String GST="00";//界面显示时间，单位为秒。取值为 0~5 时表示使用模板默认值，取值为 254~255表示一直显示当前界面，取值 6~253 表示用户自定义的显示时间。
        String info="0064FFFF6F";//单包固定格式
        //第一行显示的文本
        String oneInfo = AssemblyInformation(oneTxt, "00");
        //第二行显示的文本
        String towInfo = AssemblyInformation(towTxt, "01");
        //文本显示结束，语音开始
        String voideTxt16 = get16TextInfo(voideTxt);
        String VF="0A";//语音标志，固定取值为 0x0A。
        Integer textLength=(voideTxt16.length()/2);//data长度
        String textLengthInfo=Integer.toHexString(textLength);
        if(textLengthInfo.length()<=1){
            textLengthInfo="0"+textLengthInfo;
        }
        String VTL=textLengthInfo;//语音文本长度,需要自行计算
        String VOICE=voideTxt16;//语音文本内容。
        String voideInfo=VF+VTL+VOICE;
        //计算data数据位长度DL（计算之后才能计算校验位）
        String data=SF+GST+TEXT_CONTEXT_NUMBER+oneInfo+separate+towInfo+end+voideInfo+end;
        logger.info("调试信息");
        Integer DLLength=(data.length()/2);//data长度
        String DLLengthInfo=Integer.toHexString(DLLength);
        if(DLLengthInfo.length()<=1){
            DLLengthInfo="0"+DLLengthInfo;
        }
        logger.info("值：{}", DLLengthInfo);
        DL=DLLengthInfo;
        String infos=info+DL+SF+GST+TEXT_CONTEXT_NUMBER+oneInfo+separate+towInfo+end+voideInfo+end;
        //最后校验位的计算
        String checkCode = getCheckCode(infos);
        //完整数据
        infos=infos+checkCode;
        logger.info("调试信息");
        //base64处理
        byte[] byteArray = hexToByteArray(infos);
        byte base64_data[] = Base64.getEncoder().encode(byteArray);
        String base64_str = new String(base64_data);
        logger.info("调试信息");
        return base64_str;
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
            logger.info("调试信息");
        }
        String txtInfo = new BigInteger(1,utf8Bytes).toString(16);
        return txtInfo;
    }

    /**
     * 单语音模式（没有显示文本）
     * @param text 语音文本内容
     * @return
     */
    public static String voiceAnnouncements(String text){
        byte[] utf8Bytes = null;
        try {
            utf8Bytes=text.getBytes("GB2312");
        }catch (Exception e){
            logger.info("调试信息");
        }
        String txtInfo = new BigInteger(1,utf8Bytes).toString(16);
        logger.info("调试信息");
        String behavior="01";//立即执行

        Integer textLength=(txtInfo.length()/2)+1;//data长度（为什么要+1不清楚，反正+1计算出来时对的）
        String textLengthInfo=Integer.toHexString(textLength);
        if(textLengthInfo.length()<=1){
            textLengthInfo="0"+textLengthInfo;
        }
        String command="30";
        String txt="0064FFFF"+command+textLengthInfo+behavior+txtInfo;
        String checkCode=getCheckCode(txt);
        txt = txt+checkCode;
        logger.info("值：{}", txt);
        byte[] byteArray = hexToByteArray(txt);
        byte base64_data[] = Base64.getEncoder().encode(byteArray);
        String base64_str = new String(base64_data);
        logger.info("值：{}", base64_str);
        return base64_str;
    }

    /**
     * 将得到的16进制字符串转为bate数组
     * @param hexString 16进制的字符串
     * @return
     */
    public static byte[] hexToByteArray(String hexString) {
        int length = hexString.length();
        byte[] byteArray = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            String hex = hexString.substring(i, i + 2);
            int decimal = Integer.parseInt(hex, 16);
            byteArray[i / 2] = (byte) decimal;
        }
        return byteArray;
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
        logger.info("值：{}", crc);
        String hexString = Integer.toHexString(crc);
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
        return checkCode;
    }
}
