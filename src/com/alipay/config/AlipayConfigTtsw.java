package com.alipay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.response.AlipayTradeRefundResponse;

/**
 * 支付宝
 * 账号：ttsw2010@163.com
 */
public class AlipayConfigTtsw {
    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static String PARTNER = "2021002107677293";

    // 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
    public static String SELLER_ID = PARTNER;

    // 调试用，创建TXT日志文件夹路径
    public static String LOG_PATH = "D:\\";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String CHARSET = "utf-8";

    // 签名方式 不需修改
    public static String SIGN_TYPE = "RSA2";

    public final static String GATEWAY = "https://openapi.alipay.com/gateway.do";
    //用户appid不能修改
    public final static String APP_ID = "2021002125682276";
    //应用私钥不能修改
    public final static String PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1W/olM4DMAIOYOvqvLyF0KSVPFVGy/IOhrcWc8TlhwdELxbOepAboHcZ9MzPtjcVb/ZFMt3pyyv7G4GF4kWFYXF+SiPb+3a8s0XL91zihxQ6aNwAuLzpUXk7Pw4yfNmsjOLNZOJVcp6lpTmKE4V4a67NmQZydAQzJUNlBRrbsoRstsWcOzI8PGgFtDQzZUBPpIxPrWzYD8CAFe+g6Xq1uNUZhiWhgsjZWHUd2TrGthQz3iZ4a9WhzqJe6/0uNtvvgL/irOpY1SZnK+FoO9nIFdNGizT7bCAbE4nSywUOjH5B2VzNVJ2O0+KTJZsFmzMXVnu6k6+hxzIdP+gpNRWHLAgMBAAECggEATVhysFbswRQfC1dr/jaNf+rkXHvtIfaLdPKVar9+B3EPZIPmfOvbfjbkphSmOtPRrc/QnfVKJZbpXYvoRfFKoNKUd/wtyrhksyqIbjXRkzxlY+O9EfVWXRDzb6woVuZwfs75vj1hheiDWcPyRFWROs327s9gqZz/BQSUA0DTVssNHidgWpYNHGLvWC7wq9W1svTgsKqfQ+BWWhli0sY7QRFKJj79QFu8tYWPi00rHSB4dZnaqaIHL+UAB1US2hzMNVoeO1tMnvv6NyyTeYVLteo96/YgjBGsxx4Xo8QrXmrNIqpaw7cvuqpGleRp8RU35F5j9KRrvY9AOgdmCwihYQKBgQD4ANW7V+mulMGJ0v0tcbcHAxQvmcfOi0fTm/1AeSeZkJgKoBA+YFmms0gY+4Re8VJwrUXJ6JyuD4myYsMo255gQY1cP2xPlUZ48IRHlkDRb0HnxASzT3PUKB9I+Ru8ugfc/4t7WqtufVNdeRWLwDj40DYm6vtqeUigw6D60mDF0wKBgQC7NQYJYHl5tSEFEJRhXednDkxoi5QHE0AQhnC03oiFZDHW9CZjQJ3CIOEdi6eykw7uFMpkxTen4+PWhaLdEyZ9hWhhbBpoT154qUw98LTYvWtH5ZjuTE4V2FzRnj51Br+hULwZKDUbd7SCDzRSq/PMfmGwH2uIjxoYZjZdXbShKQKBgGY4yeAXg21/SSrK+aoylfNg9IAOTco3pJp3UHKzvXvRmqm+ghOD2VAZ05zghxYvOADTmtF3fKVOKD6L8M81ftlEC8U7KyS+bbbJxGxRZVQuHKcwJ5PVKvQGuaZ1b1ZiA323I0HsBH9dTvBXSWc6CwGbqMZv0sN3TFVfbUoVTugbAoGAfExWd79gqJjFFdHOU1/AF0L9JKUYYiI79TzudIkU6EyAcxKksl3i4VRnY/CoJf9SeG3BfGsd6xF646LTYiom8kyz44uddQjN1xvBjZbW3NnX82LUydRMPGDuMBf7rEQQ1JEAc0upB1XRulFSElotg+eeAQy7n240i2LCUfe3yRECgYEAhvov6sLxsKKiqId7AINL9zBmP7SZgNGYLLuIuP517zw0MntD7fUWbVckJvQqSHEEjXthPhx6rxWIfkY0aaBh0tro8fFONJAXZeSTWsYJVFwdSwr7T+ERPk4m2Bt5YDU/LED/tYps4D0Eu1Jrd4x5tM6awij7lln069zN0wgqItM=";

    //支付宝公钥不能修改
    public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtVv6JTOAzACDmDr6ry8hdCklTxVRsvyDoa3FnPE5YcHRC8WznqQG6B3GfTMz7Y3FW/2RTLd6csr+xuBheJFhWFxfkoj2/t2vLNFy/dc4ocUOmjcALi86VF5Oz8OMnzZrIzizWTiVXKepaU5ihOFeGuuzZkGcnQEMyVDZQUa27KEbLbFnDsyPDxoBbQ0M2VAT6SMT61s2A/AgBXvoOl6tbjVGYYloYLI2Vh1Hdk6xrYUM94meGvVoc6iXuv9Ljbb74C/4qzqWNUmZyvhaDvZyBXTRos0+2wgGxOJ0ssFDox+QdlczVSdjtPikyWbBZszF1Z7upOvoccyHT/oKTUVhywIDAQAB";


    //固定格式无需修改
    public final static String JSON = "json";
    //应用公钥证书路径 文件绝对路径
    public final static String APP_CERT_PATH = "";
    //支付宝公钥证书路径 文件绝对路径
    public final static String ALIPAY_CERT_PATH = "";
    //支付宝根证书路径 文件绝对路径
    public final static String ALIPAY_ROOT_CERT_PATH = "";
    //封装好的直接静态调用无需修改
    public final static DefaultAlipayClient AlipayConfig (){
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(GATEWAY);
        certAlipayRequest.setAppId(APP_ID);
        certAlipayRequest.setPrivateKey(PRIVATE_KEY);
        certAlipayRequest.setFormat(JSON);
        certAlipayRequest.setCharset(CHARSET);
        certAlipayRequest.setSignType(SIGN_TYPE);
        certAlipayRequest.setCertPath(APP_CERT_PATH);
        certAlipayRequest.setAlipayPublicCertPath(ALIPAY_CERT_PATH);
        certAlipayRequest.setRootCertPath(ALIPAY_ROOT_CERT_PATH);
        DefaultAlipayClient alipayClient=null;
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return alipayClient;
    }

}
