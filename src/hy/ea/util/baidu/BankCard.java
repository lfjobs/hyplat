package hy.ea.util.baidu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.wechat.bo.sft.AccountInfo;
import net.sf.json.JSONObject;

import java.net.URLEncoder;

/**
 * 银行卡识别
 */
public class BankCard {
	private static final Logger logger = LoggerFactory.getLogger(BankCard.class);

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static AccountInfo bankCard(String imgStr) {
        AccountInfo accountInfo = new AccountInfo();
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/bankcard";
        try {
            // 本地文件路径
//            String filePath = "[本地文件路径]";
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
//            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            logger.info("值：{}", result);
            JSONObject jo = JSONObject.fromObject(result);
            JSONObject jsonObject = jo.getJSONObject("result");
            accountInfo.setAccount_number(jsonObject.getString("bank_card_number"));
            accountInfo.setAccount_bank(jsonObject.getString("bank_name"));

            return accountInfo;


        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return null;
    }

    public static void main(String[] args) {
        BankCard.bankCard("");
    }
}