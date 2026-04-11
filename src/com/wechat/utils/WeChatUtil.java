package com.wechat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import okhttp3.HttpUrl;
import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.common.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.UUID;

/**
 * Created by Administrator on 2020-12-07.
 */
public class WeChatUtil {
	private static final Logger logger = LoggerFactory.getLogger(WeChatUtil.class);

    public static final String USER_AGENT = "用户代理(https://zh.wikipedia.org/wiki/User_agent)";
    // 证书url
    public static final String CA_LICENSE = "D:\\sftcert\\apiclient_cert.p12";
    // 商户证书路径
    public static final String apiclient_cert = "D:\\sftcert\\apiclient_cert.pem";
    // 平台证书路径
    public static final String publicKeyPath = "D:\\sftcert\\publicKey.pem";
    // 商户私钥路径
    public static final String privageKeyPath = "D:\\sftcert\\apiclient_key.pem";


    // 服务商平台证书路径
    public static final String publicsKeyPath = "D:\\sftcert\\service\\publicKey.pem";

//    static HttpServletRequest request = ServletActionContext.getRequest();
//    static String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
//    public static String notifyurl = basePath + "/ea/wfjshop/sajax_ea_notifyResult.jspa"; // Key

    //商户私钥（拷贝apiclient_key.pem文件里-----BEGIN PRIVATE KEY-----和-----END PRIVATE KEY-----之间的内容）
//    private static String rsaPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCr8V/5ZgDwGqQr" +
//            "DO+ekjm/ayW6TH7xM3cosUoq6Ffh0No5VU8objNGCK2ZWA+/EzrNaesKKs6+CZb+" +
//            "dp2I+NN0A+jBIWY8H9RemypEVmAVZsoGDLbDHO0TELjSf4N9pQv83xwydMQNkEvK" +
//            "+9qKGVJGtxyAonqn4YLEOhAyTOrAXTOwD57GdQ2O3YHP05oiLpveTsTuku5uIn4l" +
//            "3P/PqWQ/8QzkE9eEN4SvTxzL/1VVJYCA7AXbKqCo0JUSLVVadlPcJ/FDgHe8R2ar" +
//            "txHIG1u4uJ581u1cg0nA5GUdQLvX6JqBDQ0ULybggAFojjFmuiClDyqWb0aI17Mz" +
//            "pPEPw8F7AgMBAAECggEASF87t1zw7h84lIpIXaVMuz5dTfIeyGWSNUWSY0cSnpDr" +
//            "GZDH1cu0tzi14UKItjNg389CSDBGfwTIUmkY2+toDYALuybUwl/fYsbK3qLApKF0" +
//            "TWGSl8OYIhs6wBc+AGe1CQXb93zgfJkM3e5uSQxpbixqydYZ7InmIbQS1yk68iZY" +
//            "Gfu7xqPrKklt54g6be79QxdezTT1IrfWVv+rBdmQkLSrWhHwEpPob2lBjk0oMpCG" +
//            "7IKbgWiq/kyKLgTy1FE/ve1TSudhw3jPZ+24Kh5TFppm0JzMaVlafXdbSNliKcAr" +
//            "ftLHM2uABZe1weGdiWZO7n9N5zo4+T76svKObfb1YQKBgQDR4vMw4FdQQJDIhe5Q" +
//            "kq2jWhMZXQPDlF3HIcGIQpnC445Sb+/GC3w+zygrNzAhUjh331dvdW6VF9DcQQVp" +
//            "1OQkcy+/GRQhVM3GQUJaZ+kTbN66LU0VmlVdN61Ft1YbQvBkXktXzttXN8JyDpUh" +
//            "xMlecmJJdMBPqKZTaw2Vifin8QKBgQDRuEmP6HnqDdd97nkf49l55zinsXxvtHrh" +
//            "JC4oVCONLsnxyS8Qb8twdd3S26PeSnibQuDTbNncAS9Osx0z2cydNHN5jsjWogd1" +
//            "z9o72uW8VtNHwmfW1EAiNM1bLjpWKT1SA6CtzFnNXbObrfwlxoZ3jrT33Rc/FQ3z" +
//            "i/9B52RMKwKBgQCVzZ417GllZi/wb2dBKu+z/75SBAmDtomYb4dqcd69Mx2JyUsh" +
//            "12C+k6zf62v23HBhXo9Rhkd/MphoHCB5API08eHKS2QKxjR4pGtvi2rX6oSt7Vdv" +
//            "a9Ax0GNjtw9GNejOOl8cNRBol+u47J+UkpgHSSzNsP7x3DWapHX1nZxUgQKBgCe+" +
//            "iRJguFoG7YPMIeBNSfW0QCRnmnGA/piURrsV1vb2mwbbXbP7u85ASB2hylhiPKEP" +
//            "95OZ2V+8Sb7cavhcmk5gllG/IYirhEZrcaxOO6OJwNikmxE3gD/7Sb+x7xRNFHXq" +
//            "ghTt2Ub2Lwed3V87VFhsDxe6KqGl19MEAQf4ViWpAoGAcN8DUaa1Ff7N9IYWsdmA" +
//            "vkEfLZVP2J3FwSbPZfKtUFivmXxpE9YpVB451pg9ZYstxn7GOcuJxk4JqLFqv3eP" +
//            "vbuTQhT1XCdi9cUqBIL9/PamNLx5N4/0cOwljoU2c2sX8AGEhy/+P53EpgAK5tRh" +
//            "FxC0acZv4RWarbo32h6hT74=";
    private static String  rsaPrivateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDfTu+WUPRUXe0H" +
            "IoiP7AwBswrDE1h/zVUSv874n6UFIi3//Qu8geBF1tZTIq8MmoO5goR5+D1lBKjA" +
            "j/ERN0ZoVjn+G/RH3aFhFwXGzyXNm84nxQaWZwbB8XyLIyQscPgPHNOzgvqQSRvW" +
            "q/XGxlxjqH4b0crSLpsCMG82AA3KI0qTlmoPwo4e5R9VmhGDAk7S6SIgpO39VZAC" +
            "cGjJ6BY6DqbbYuQ0MMjtpnYM6fb40wd6PCuemVlDE9WJoAK4Lgt2PBfaDwtf7hdR" +
            "JtrUihQ8n25fK9w+DGOSPLissduPHrCqkllc816MLZ15nS51+GGOTJkMRYGW17OC" +
            "YlZEGYCPAgMBAAECggEAGdFNf+qCJRLq6Y1DjqgyxyuFDgK5k6UQtec5LaN/d7V4" +
            "7gS6i6PUYx6wjtIOvP1MR3FgkZ1ys7AzNngt3divKjb143wl5P0pwEjiIRcn/rqJ" +
            "vi1YUqE8tcWOqaN5di3lcBHcI//7qhzZvBsLglzCp77+PYcm1OAQidNBuFOPRJRS" +
            "k3xeka2rhvfte/gbd5OXTSPsUOQdevjKSek1TPfofDedYaSxbnJlds7d4yQ2Vbd1" +
            "qarWKZVfRSaLrqFp9NVNy9mf4fm05u5sD/txHIt/8mPzszojUP3hloW5LFbe7U6o" +
            "NVLpvHhPJ67QmccIPGKxfSgafMTlhq9WvcNE4j9VAQKBgQD9xS54LQwKrIyw/pGa" +
            "rzJff+sLe/wP3JkHzakfwMr7pYQhMyl+izGKqDCZnFweQq60ajTDarfmZCWglw+Z" +
            "OxyquNcOX00WJjQ7bVZWAus0la0pSMAqVDqcLZMawlvXOMMn4EOlcyNn+oHVEsBM" +
            "LE7ffk3Sz/9F3CJeQOI9K9MGDwKBgQDhRTwfObR6tUt5usAuIS5n6smaSBH2/eS6" +
            "qNFyp5CsBtZaO5klOYHuqdzRmAwOgH718V4kg6HmvZ+OvvgMkUNtm3V3XdmuHgKZ" +
            "9XfCNZL21VauH6ZdGt7m+EyC/EZusOtYizlZA8roaas7j1FPxooOnoUwXO7RWZOq" +
            "1WIkMXBdgQKBgQCfLVPcpcTr3RfS3ZtPP52FMaOpbHfLK0ACGQqAlFm+Ry/98y2P" +
            "5peItb7mW6GciY80M3otKX940FCZzqdGIkH1BERBC546ynPw4sBI60PFlC0tvCht" +
            "ZK/uztd67XbWJRCkrpM/8FzBF+zgTga4wQIoRf1iwn0IwO/91w0R+a9AGQKBgQDg" +
            "5xSNQDlVwSanU7GG3I3OJfQ82fNFmln/mYfyZiTj+reKwrG1HmMAegoCH0D6QYN/" +
            "3AhXgCvliC+Cflp7gZPGHEz8bXBki4WhXBbCiGY/lOLDWMnpbmhYRFFu/Jr87Bkq" +
            "/2Sz4Dt2KflQr1dIxDkVO2JR1JaB4GKzTfJ7m/hJAQKBgQCAvaVuWRH8xK8O3DW4" +
            "iZYBZsbeVK9JUHiYUS5CemJ/TMNjpwpXMp0VipAENeIdKn++KnNO+yDV4TONy21S" +
            "qc1Pp5zLVE+PlDbfq380lxc0+G329Xoh5zs3nDTS871L9ANBwtYT7Rb8Vwiw6V2c" +
            "eZACl7xc/2GizStMUNRuhtXqkw==";

    // 微信商户的id
    public static final String MCH_ID = "1604065060";  //  1561059921
    // 微信商户的key  签名使用
    public static final String KEY = "bjttstkjyxgsdszltpwfjwxhapi20202";  //  bjttstkjyxgsdszltpwfjwxhapi03092
    public static final String CHARSET = "UTF-8";// 编码格式

    //合单商户appid
    public static final String  appID = "wxf338ed62f8ec11cf";



    /**
     * 获取证书序列号
     *
     * @param certPath 获取商户证书序列号 传递商号证书路径 apiclient_cert
     * @return

     */
    public static String getSerialNo(String certPath) throws IOException {
        X509Certificate certificate = getCertificate(certPath);
        return certificate.getSerialNumber().toString(16).toUpperCase();
    }

    /**
     * 获取证书。
     *
     * @param filename 证书文件路径  (required)
     * @return X509证书
     */
    public static X509Certificate getCertificate(String filename) throws IOException {
        InputStream fis = new FileInputStream(filename);
        try (BufferedInputStream bis = new BufferedInputStream(fis)) {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(bis);
            cert.checkValidity();
            return cert;
        } catch (CertificateExpiredException e) {
            throw new RuntimeException("证书已过期", e);
        } catch (CertificateNotYetValidException e) {
            throw new RuntimeException("证书尚未生效", e);
        } catch (CertificateException e) {
            throw new RuntimeException("无效的证书文件", e);
        }
    }

    /**
     * 获取签名
     * method(请求类型GET、POST url(请求url)
     * body(请求body，GET请求时body传""，POST请求时body为请求参数的json串)
     * merchantId(商户号)
     * certSerialNo(API证书序列号)
     * keyPath(API证书路径)
     *
     * @param method       请求方式
     * @param url          请求路径
     * @param body         请求参数
     * @param merchantId   商户号
     * @param certSerialNo API证书序列号
     * @return
     * @throws Exception
     */
    public static String getToken(String method, String url, String body, String merchantId, String certSerialNo) throws Exception {
        String signStr = "";
        HttpUrl httpurl = HttpUrl.parse(url);
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        long timestamp = System.currentTimeMillis() / 1000;
        if (StringUtils.isEmpty(body)) {
            body = "";
        }
        assert httpurl != null;
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        String signature = sign(message.getBytes(StandardCharsets.UTF_8), privageKeyPath);
        signStr = "mchid=\"" + merchantId
                + "\",nonce_str=\"" + nonceStr
                + "\",timestamp=\"" + timestamp
                + "\",serial_no=\"" + certSerialNo
                + "\",signature=\"" + signature + "\"";
        return signStr;
    }

    public static String buildMessage(String method, String url, long timestamp, String nonceStr, String body) {
//        String canonicalUrl = url.encodedPath();
//        if (url.encodedQuery() != null) {
//            canonicalUrl += "?" + url.encodedQuery();
//        }
        return method + "\n"
                + url + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }


    public static String sign(byte[] message, String keyPath) throws Exception {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(getPrivateKey(keyPath));
        sign.update(message);
        return Base64.encodeBase64String(sign.sign());
    }

    /**
     * 获取私钥。
     *
     * @param filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }

    public static Cipher getCipher(X509Certificate certificate) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, certificate.getPublicKey());
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("当前Java环境不支持RSA v1.5/OAEP", e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("无效的证书", e);
        }
    }

    /**
     * 加密
     *
     * @param message
     * @param cipher  通过getcipher方法获取
     * @return
     * @throws IllegalBlockSizeException
     */
    public static String rsaEncryptOAEP(String message, Cipher cipher) throws IllegalBlockSizeException {
        logger.info("调试信息");
        try {
//            if (StringUtils.isBlank(message)) {
//                return null;
//            }
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            byte[] cipherdata = cipher.doFinal(data);
            return Base64.encodeBase64String(cipherdata);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalBlockSizeException("加密原串的长度不能超过214字节");
        }
    }



}
