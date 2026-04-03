package com.alipay.config;

import com.alipay.api.*;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

/**
 * 支付宝
 * 账号：china_jxlm@outlook.com
 */
public class AlipayConfig {

    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static String partner = "2088011999101771";


    // 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
    public static String seller_id = partner;

    // 商户的私钥
    public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM5nbX+WGDmBdPf6OJWSuDJ3yiHhxC0kDPtJkdQLIJ3V0uoYqTdk6uDXp9aePQTqVAjVZpn2EG+2o+5qJQn2g2Wv6jjQnNKPzyThfMgvuiqbXT9/9UY6Maguvad966AN+KGEBw9VzpzTrWM7qo98Jdk4XQDeABt3w4yWf2isSt83AgMBAAECgYATNiVG/t16XMZaXnvt0B489FFoHe94lCZxLUPZjL+YcVKUX39AnLAQBYKlw0zCrFzuBY7rPbAB9eoEQvT3nG5WWeJPJfMA+nUrpD8R/cvD0eOWt2+VLQ65SNkFf0TnsNwzMmQTopqHCqw5Kke5XToRUE2ULrR7funlmAExWwpGoQJBAOfYvzUCp0DHhEXzJgJpP/aAfhlrZlMLtvIVjhPH3k57dA7CN6D2qs7AM9vM2bMgTS43SK2KsCJ1wTj9M+XPvhUCQQDj6CLPDDJ3OmdOCUmsfKmb/XboX2DMoQ9Nrrcy+lY85D8kuOw95tERCz2Jy8NXu+nyWz/Qvtp83Mwf86DfgkcbAkAtDxnyMobQ9zXE5oGvnEb7IHPhetcTMsame/v16jVRaXrf1206UlZWAlLR2BlDT/bQEZyFZdvfhqU77zHj6yEVAkBvy6tnjUAkJE4QhZXSOsSodEkj68V8G3V6Ub4kD5DGBeDDjsK0Csl7ZtwlAAMuwkscS46eQ0DCBz1KXyQG/80JAkEAs19EhOChTr+guH9+yq3sAaofytq4EH0hO+otPqjvIXXinFsbC1OaiogdVFBGJc3M/Q0zuhwFwAVB3bfBPCV66A==";

    // 支付宝的公钥，无需修改该值
    public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";


    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    // 调试用，创建TXT日志文件夹路径
    public static String log_path = "D:\\";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";

    // 签名方式 不需修改
    public static String sign_type = "RSA";

    //退款接口url
    //阿里接口统一URL
    public final static String URL = "https://openapi.alipay.com/gateway.do";
    //用户appid不能修改
    public final static String APP_ID = "2016042701340489";
    //私钥不能修改
    public final static String APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKPD1PK/k+0/xijdVsr3S7A21iGeuZx4HhGX3YyezvpmBLRyl0a5divL4ABaukn9bUWQj8G7s3DK8/xkOcIgulgthRfctNQo6X1wVNk/Tcxf+fBQxCrI9DaSUVWWtEQ1jY8J9mBiXgs28vSVi3EN4jICnTNZcXa1qnPzeCFFS6T5AgMBAAECgYEAnumKO6DRv9gxxze5YC0GglFECs3GWWJp8zKF5023lV3n1N6ZkySm2ir1DAL+YIwOPAHireEhaCNZDSKItasihp60RgLDyfGF0pl49Y6+kKaodn3BVxkjNnl7FnUT94+XB5g64C46xGN3LQMqx2ebhsu14V3M1WYwlUcHw8FEJjECQQDVd5oR2j1+FJrtJyn0/nxlyygB5cKszD27UHh6+BPS6u6XPDFYL8D9yCvTBA3sQX/XK9EAL2A6wlrjJns0mA8VAkEAxGUM9EXUDfW2SZFRsO9Lk5fUoyFzl72vWydEWFAQ9MgfkEKiMjG5pslbl3gdxmDpJQcRLBh2r09xJ507WpTXVQJAfoampqBBxIJ4FcsZ3mIc38Ub0Iwb6IoiqO0YjGKQdXrUsoV2BfxAGuxTtX54tilYmfQZrE9obUuXzX2Yl2LtxQJAEXATfWDx2id1/wQ7J6WnpTw28iEXPWLnPgW3iMvbJZDbVJjGBATsQgXg7wsIQcP7D4GYIb0uVJSOLIFGct2CXQJAMeiZLqrQOthG0L7cDgmWySzgm75Peizfyl0qDptEmq6Yp7N/HB54RsjijBJreUPY1Mrye5IzlLaa2NWP7SAb7w==/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    //公钥不能修改
    public final static String APP_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    //固定格式无需修改
    public final static String JSON = "json";
    //封装好的直接静态调用无需修改
    public final static AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, JSON, input_charset, APP_PUBLIC_KEY);
    //封装好的直接静态调用无需修改
    public final static AlipayClient alipayClientNew = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, JSON, input_charset, APP_PUBLIC_KEY, sign_type);
    //封装好的直接静态调用无需修改
    public static AlipayTradeRefundResponse response;

    /************************************************************转账***********************************************************/
    public final static String APPID = "2021002160636103";

    //应用私钥
    public final static String privatekey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCdfPv45Sjx19z81JviahdmhFYEXx5z8oJSQAq5xFOgwD5L8pTT1IwWjxsdiGErS9Zhioa7exjuyV03aDBvuBuLP8sX6oCh96kQRDno9PLzShink1yfqEX/QIB4GHQaXFQ6z+rnW5yhE3dM2wNpUROR6kVC/f6vVaZTnZhBCiFOgyNf0Ih9tuTw0uup8JhOIwqYdburx81iug90Khv8Z0xEFVhxFVWurpZcTWczA88KpV40pJr8slhjqF1kpNzgR6DEJmdxOeQU4p12f7gLh3NSdhRWNidZiNxlRy8F5x4AeFG5WZMROBxbplp44O3DrWuzfFhFaCQTrhp3vCgiINGbAgMBAAECggEAPTolH/u/sl5Q7P6faCQE2vdiFasBROSoY9/AC77XIGz9CzvRPpWorPCQAyhNVq7SNvPLM8JEzDj/A5Z/FkAGfVmc1Lx3rACD+u37caUhexJ4iVXSMcQTCoyCJa6kHMd+e6rv0WideUX/IKMZLW225QimSshCptnuox1m3wpYLpRSddP/tUItLMsPCZQlPPuThJJ0xg0d92UhEdMvAC510Uu+dvMKNYiGNKr55PIpUU6AjrXCRE4VuqZ972Rf7ThCQr/37s/SwbFXRmORVZlrGPXrzo8GW0tqds3CxVWIAM0eAu5rVjtb2n3eBOVdHJhON+xAFLLJC2FsIBwZfyy8yQKBgQDcQTX/N6z6ebdxas0h23M6NkTfwaUU8JQlixYn2/yUVr57cNnA3O2gqiJAUTNho0AY3ACiAVcPz9qNG6aJ5sEIt81/zrS6g5DHejEJfFyaRw3QLNT8ctuVS3hba/pOaxqML9dsWXnfZ1vtWENx8WHNhL5ggNTmD/1aRmI2zkTbRQKBgQC3DA0nERwWYhXsR3x0hIC591N1iR4/yl013rTV9dxyLgyvYNq40FHTu8g9HjLzTVWO1202yIhoDHttqP594a5jIMWC5qJx8dCV1cbc2KrQ0mheezKtvw/YlhVDhPFeebwHbZeXAxJ8RmljM+7B+8b7nKWXtE9DdV+C/4st6UNXXwKBgQCwTXaWew94U3OWUqIupsWeZBBPd2bX0xcS0jmC4GFWr/S7RILZET8Y6vaNuzHAxvcorvnGtAHngz5y0x5EpjP/8XiKBIDAB04djl6wNB6/035oE2rNRHcmEwodS9pJaQwQCL+MWDbor4hy/MLH8yqRYt8syhMgsM3gtBQrwIJ3bQKBgQCEECLRI8wC7iNpBC5rlkmVYjaJnzL+zQ6w1gvcLnUsv8KRKazEGDBhNaMDTW1vEkV/ODfohYe8ofzPIlTyF0gM5tD5X1KmKNPXtpOyNHQREy0PtYzbw/Q3zBhtTVYjVmrtZQkwf5vvo5FXbFl/BRsL33mtWP6y6dTOG3ab2iamuwKBgQC1gZ0Zrf5DS079LMGmyrohDnO8xhH56aBiX0ypuu0fRL1b566AsgGhMEkjthz+EJjwnN2+rvfTqRxjXXXESlfu87gkemKs0i4OTWST5QHBqlCrffCDsowC+jSQn+OWhm0kUsyhWcbkR9I0HRlYkxDX8vgGAqKaKtzrWVjN6zpbwA==";

    //应用公钥证书
    public final static String app_cert_path = "D:\\alipayCertificate\\appCertPublicKey_2021002160636103.crt";

    //支付宝公钥
    //public final static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAssCkAtp20ri8To0wTylUg9nUkAsg1GggAyPdixSKYmN4wv4z1AxWPKLK8kozf9OPRyDv52YF4VblB/I1LzebIOCGF8JvYv5NxfWKnHoVnX+udXo+7WMF0Zti3VXELbzpaTfkkud20f9b6f3ha7/BkSrZxNWk8NdNo4MbMS0rsXzfot66Z6mDLuovNmTROklqlqETP3hJvp5EdXz1DiuEiG8iiGBu76K28NLf8P/O8JaJp9MjNLAx4KG5h0j0fUl+DuCP8W32zd09XyWXdsI1Jp9A7QU2TkibxRO5K3OwEVAFeI0Ys3YpXkyXE9YgeSAh9SFpkkRBof5Vtjr6FbHhAwIDAQAB";

    public final static String alipay_cert_path = "D:\\alipayCertificate\\alipayCertPublicKey_RSA2.crt";

    //支付宝根证书
    public final static String alipay_root_cert_path = "D:\\alipayCertificate\\alipayRootCert.crt";

    /**
     * SDK 接入
     * 请求网关 (gateway)
     * 应用 id (app_id)
     * 应用私钥 (privatekey)
     * 应用公钥证书路径（app_cert_path 文件绝对路径）
     * 支付宝公钥证书文件路径（alipay_cert_path 文件绝对路径）
     * 支付宝 CA 根证书文件路径（alipay_root_cert_path 文件绝对路径）
     * 编码格式 （input_charset）
     * 签名类型 （sign_type）
     *
     * @return
     */
    public final static DefaultAlipayClient AlipayConfig() {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
        certAlipayRequest.setAppId(APPID);
        certAlipayRequest.setPrivateKey(privatekey);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset(input_charset);
        certAlipayRequest.setSignType("RSA2");
        certAlipayRequest.setCertPath(app_cert_path);
        certAlipayRequest.setAlipayPublicCertPath(alipay_cert_path);
        certAlipayRequest.setRootCertPath(alipay_root_cert_path);
        DefaultAlipayClient alipayClient = null;

        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return alipayClient;
    }


}
