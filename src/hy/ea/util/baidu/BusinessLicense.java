package hy.ea.util.baidu;

import com.wechat.bo.sft.BusinessLicenseInfo;
import net.sf.json.JSONObject;

import java.net.URLEncoder;

/**
 * 营业执照识别
 */
public class BusinessLicense {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static BusinessLicenseInfo businessLicense(String image) {
        BusinessLicenseInfo businessLicenseInfo = new BusinessLicenseInfo();
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/business_license";
        try {
            // 本地文件路径
        //    String filePath = "E:\\1111.jpg";
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
//            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(image, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);

            JSONObject jo = JSONObject.fromObject(result);
            JSONObject jsonObject = jo.getJSONObject("words_result");
            businessLicenseInfo.setMerchant_name(jsonObject.getJSONObject("单位名称").getString("words"));
            businessLicenseInfo.setBusiness_time(jsonObject.getJSONObject("有效期").getString("words"));
            businessLicenseInfo.setBusiness_license_number(jsonObject.getJSONObject("社会信用代码").getString("words"));
            businessLicenseInfo.setCompany_address(jsonObject.getJSONObject("地址").getString("words"));
            businessLicenseInfo.setLegal_person(jsonObject.getJSONObject("法人").getString("words"));
            return businessLicenseInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        BusinessLicense.businessLicense("");
    }
}