package com.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.wechat.bo.sft.*;
import com.wechatpay.bo.AppPackage;
import com.wechatpay.bo.FinalPackage;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.bo.WxPayResult;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.util.Constant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import okhttp3.HttpUrl;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HTTPV3 {

    private static String schema = "WECHATPAY2-SHA256-RSA2048";
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

//    public static String WXSP_rsaPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDP5zt1EHJNSh5s\n" +
//            "EfoQNTEP12tY1zQO2MILXJ/hdMK+jGllHuwxj5wfODaDUXf6mn1GmiS9u2x6DcSz\n" +
//            "FlsKfKvsWttZbuJnCBVGAJk9XhHhO+kN+xff1p5rRJRLk7ZK/G+JSxCoy8nknDU7\n" +
//            "yj81tYDoKjcIUlGkEDEdMOCnJmS8aOtqBUBLB7f7GM2LOS/BPf4XIgFUlDPhsnhs\n" +
//            "O0vOmXHx4e9ANUKE9auXMBWTe4TJTeL0UZPSn1Qbd8O2cVmaT9hZCSh7W8SYM2VY\n" +
//            "I4aEmXDmf/OcoizA+aZnFWnN+AnSheTZ/cOQ1W2MH3wQToWdvAS7cweIrQS/58cM\n" +
//            "LZD6iFIdAgMBAAECggEAfWhR6S9eg1iuOSou2Q/85D1hN1m4wSvnAMuCdZDk1+QC\n" +
//            "v0iD2PFzjzFtUJEX+kauQqRAfWJM8OLVP+HAUPcbDZtQrQXB87xgeLVruiTATEjD\n" +
//            "L/fCUvc24CWh/mS/lUPoU7y/89HZmtjPE53Puyoc8+5i+9YMARbzSVRubuUt5qec\n" +
//            "PB3qAR9tO8R/F0rPT7Dx7MT4fAiiv1Yo2gQo8Ohvuj5EpLBw5jEFluuasm8ed5AK\n" +
//            "ynNdJoY/LW9nM1LcVnIFkB7MXaCti0QPM7H5qQX2I8t8Q7X6CVO8QXSnQ3s3IxPK\n" +
//            "KX8iG6YUgjw3I82X7r0NzWXOf3j4QdX9wUAUit7VAQKBgQD3OqKh7QAr4dj695gc\n" +
//            "CUzPJzzTGL7V+WoXS4ofgAetL/wfu/w9A6mgi5h6rRVEuQYQyOP/kLWN8B+bb1/h\n" +
//            "r0ALREtXI58O58aTnNAKLXVPz5u8ynf/GaNcV1QyZIg9mF1GD9z6iB8FkuYOyQHv\n" +
//            "+jOt7+urK5eqN69uq5BSFiF0UQKBgQDXR29tSYfoWDv/DblU0pg/ZnyTbjUqDLJS\n" +
//            "XKAO0oK9neEnv8RRYk32HbDS/iqq2DXG9p+m/8K6JwB4qb7doY833ZDLiaVF1w/j\n" +
//            "lJ6LRF0ltvY+Bvdy5aX7l+vZJlTrWQrFnCRI5kKeUO1rGj+9AmIaRQREHCx8oNbf\n" +
//            "6DsWXFZKDQKBgQCTdxsYwNgQVLdrAxJs7rMtAPeL5r+1u/6nBHzPyztI901I1IVh\n" +
//            "E3UzePCrg+AizMYsbtdoUjWpAyinOyiS4kiuL79Mq8etSVyuIaQJ3hGSWIBJfO9U\n" +
//            "b0N8ye5D3KlTLDW+m6mwmk7+sR+ehBEuxivbPTdyMx9U/cAboCQGgoLT8QKBgDS0\n" +
//            "k44xG2oB+CZYiNBAgu+6urMpnzB9TUcm0DTwyvt7ZL1jkL/QDoA8AqeVfIK5Tmk4\n" +
//            "xhuPsG/8znu8KzN9s1+dCi8asvDnV0eDglFcWmW6Zo0HDZv+p2Vya1aNsOqxsRZy\n" +
//            "RNvoK9UwwP+gvIwcwkHmNcYrE6Urh4/n/ldaIfIdAoGAATmwQosHCylUqOjqCku6\n" +
//            "IVjEinfy5KNq738OR8GnqPPneWvz5TgdSHQmwPTTBcvuRsh/Mq1ie29EmBfGxmVQ\n" +
//            "IEeSI+de05Z4IBZFvsF7baN3i+1/WmDN2mmbnwNqsyZk3SW4QpcZqWvTok65LkRt\n" +
//            "fjQNaqlQem/fv7Z6bhvXnjw=";
//



    private static String WXSP_rsaPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDCqWYNeVlEyq2Fur3RoBw7szSO9OkLKiZ1rbgvR7J/c8jhSOfd9QsDxZnuYDH7TxjHyMRNxjgRA63KWDY4qRJEyfpDnT97rbpvnksdoDVDLWIqP6shHuzZZP6Ch4j4BXqfwdfUta6ou/TQygWoxb2aFxaQqUsma9tscPu8COnWPNaSf7Hqg2gxHzxwL8/OpVPhfGrSu6UH337zPFssqFR+T6XIXYJNVUTTpMkxRB+/cEknxsgm7yBlSINz8zbHkYe39kNsn+QM7FX274JdI0XtP8TrICB1CSwHWHB7kSYugEU36/HwcGHramlqiwgFHJRw2rzo/g/seS7gh/1Ih00RAgMBAAECggEAKv9X92nyeet64Ksa+o6vLdCrr1gRkdHBpQS2RltD5jshCAIelTxnLnIbSjgdUjFqe9aw5az+/WMepC9P6rwNuPwUN5MoTp1VLQp99RFKMoh3wnVFy48VKf/Q5+wgKQisyVT745Qt/D9oGCZHkbv74WlwJov+eYIheZaLREGDxTpQPjX9MS7gPgrWV6T/UccYz1bYQWc4s68Xs+3z5dXZrDO1H3GOCuwuRpbCERjmYucjMR6Sf6eteItNkS7H9ZdlN7rq4TGP/GiU6J6LEAsChvDa7woSGNpjN7qelYWOjGZQBjuSCnuruXs30YSe2/+cAmCRFdy3GHe7M7OJ+q1QsQKBgQDqevAjEi4IOASqLu8jDFkDOFVIYg6qpBUwTUDdCFrpEff+/xrL9dH0CxKagstqmvNSg4w/CsInftHaPJoFwe+wxqKGRwx2nlPfnbjP1jrMqefnOH3N6GjBWm5vs1B72nuQTuI4vRRSe3FwE4QyCfZVgPmIl5AzjKPEH/ikSqOkFwKBgQDUhu7sTeYCKJXKKiIQTwYG1pKXpfNTL2CQqfw0AyqN+knnqu/+WC5WmxF/rchL9X3GduzfTM4Fsyo6FB1l20WMQlHIZGIaNaRAkQkTvuVQ1ljk2Bt55gfVhdWW9L6BsKttmNQZcDwMO9uGKnRCsa4P7pijDOxSsp4bHIqUFt9JFwKBgCZSd2LT+s//D8wcpZrNztJ254FP/WOoM4pUZfB6CBmlhSmY2fFRuMGw+ZZlIAfJUVFADLDU+kxoLrYDR4z/WERFApT9kazlTYHzjhvxwGRotG0Wz4DSpiRLulbpMJM9bwpzygdJ58NfR3fwyJ9A/5CT5rSLSNcT+kP4kbOQO1R7AoGBAM/mR2cmNPC7nvwrBzOI+k5KLRDoKAWh/QbHIo4G4uQMiat0zlyzCnFyNEYATEXyGl3BDKrMSWXOBGrYLoHrZDd20OYFPOqCuvDmcEi+nzja1g7XUNmIKvW3fF+1f38Cwk/uBt3zquoUzASNFzn+8lVnhC1MgQnM7Vl8LBv92+N1AoGAcZHq8GuW33LrkOkB6+yGw+83tc06tnpZSG+pGiKc+Nbb/BZQi3gtgbcm+sh/Tk4fDAzgSbagHIi/DKpvyAsbDoX1oa874pvJujMRGak6DvQWMylj9x3CBFvPticiDjSMiCX8tMetGXCKdpLCuRIWm7Wc8B56Age/scqerNFI8iY=";

    private static String yourMerchantId = "1604065060";
   // private static String yourCertificateSerialNo = "401D98B320A9CEB1961D503FFBAE061A3545C8A4";  //商户API证书
   private static String yourCertificateSerialNo = "41D9EC7F742C6988D794DED83CE6455E6B2C93D3";  //新的商户API证书


    private static String rsaPublicKeyFile = "D:\\sftcert\\publicKey.pem";
    private static String v3key = "bjttstkjyxgsdszltpwfjwxhapi20202";

    //15 V3 key
    private static String v315key = "bjttstkjyxgsdszltpwfjwxhapi20212";

    private static String privateKey = "D:\\sftcerts\\apiclient_key.pem";

    /**
     * 设置HTTP头  Authorization: 认证类型 签名信息(token)
     *
     * @return
     */
    private static String getAuthorizationV(String method, String url, String body) {
        long timestamp = (System.currentTimeMillis() / 1000);
        //随机数
        String nonceStr = WeChatUtils.getNonceStr();
        String message = buildMessage(method, HttpUrl.parse(url), timestamp, nonceStr, body);
        String signature = null;
        try {
            signature = signRSA(message, WXSP_rsaPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return schema + " mchid=\"" + Constant.WXSP_Mchid + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + Constant.WXSP_SerialNo + "\","
                + "signature=\"" + signature + "\"";
    }

    private static String getAuthorization(String method, String url, String body) {
        long timestamp = (System.currentTimeMillis() / 1000);
        //随机数
        String nonceStr = WeChatUtils.getNonceStr();
        String message = buildMessage(method, HttpUrl.parse(url), timestamp, nonceStr, body);
        String signature = null;
        try {
            signature = signRSA(message, rsaPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return schema + " mchid=\"" + yourMerchantId + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + yourCertificateSerialNo + "\","
                + "signature=\"" + signature + "\"";
    }


    /**
     * 商户私钥进行签名
     *
     * @param data   待签名数据
     * @param priKey 商户私钥字符串
     * @return
     * @throws Exception
     */
    public static String signRSA(String data, String priKey) throws Exception {


        Signature sign = Signature.getInstance("SHA256withRSA");

        //读取商户私钥,该方法传入商户私钥证书的内容即可

        byte[] keyBytes = Base64.decodeBase64(priKey);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        sign.initSign(privateKey);

        sign.update(data.getBytes("UTF-8"));
        return new String(Base64.encodeBase64(sign.sign()));

    }

    /**
     * 微信平台公钥验签
     *
     * @param data
     * @param sign
     * @param pubKey 平台证书文件
     * @return
     * @throws Exception
     */
    public static boolean verifyRSA(String data, byte[] sign, String pubKey) throws Exception {

        if (data == null || sign == null || pubKey == null) {

            return false;

        }

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        FileInputStream in = new FileInputStream(pubKey);

        Certificate c = cf.generateCertificate(in);


        PublicKey publicKey = c.getPublicKey();

        Signature signature = Signature.getInstance("SHA256withRSA");

        signature.initVerify(publicKey);

        signature.update(data.getBytes("UTF-8"));


        return signature.verify(sign);


    }

    /**
     * 构造签名串
     *
     * @param method
     * @param url
     * @param timestamp
     * @param nonceStr
     * @param body
     * @return
     */
    private static String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url!=null?url.encodedPath():"";
        if (url!=null&&url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }

        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    /**
     * 请求
     */
    private static JSONObject httpV3(String method, String url, String requestParam) {
        JSONObject jsonObject = null;

        String authorization = getAuthorization(method, url, requestParam);

        try {

            // 平台证书路径 是必须的
            String serialNo = WeChatUtil.getSerialNo(WeChatUtil.publicKeyPath);
            System.out.println(serialNo);
            System.out.println(authorization);


            if("POST".equalsIgnoreCase(method)) {
                HttpPost httpGet = new HttpPost(url);
                httpGet.setHeader("Wechatpay-Serial", serialNo);
                httpGet.setHeader("Accept", "application/json");
                httpGet.setHeader("Content-Type", "application/json");
                httpGet.setHeader("user-agent", "用户代理(https://zh.wikipedia.org/wiki/User_agent)");
                httpGet.setHeader("Authorization", authorization);
                StringEntity postingString = new StringEntity(requestParam,
                        "utf-8");
                httpGet.setEntity(postingString);
                HttpClientBuilder httpClientBuilder = HttpClients.custom();
                CloseableHttpClient httpClient = httpClientBuilder.build();
                CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpResponseEntity = httpResponse.getEntity();
                String responseEntityStr = EntityUtils.toString(httpResponseEntity);
                httpResponse.close();
                System.out.println(responseEntityStr);
                jsonObject = JSONObject.fromObject(responseEntityStr);

            }else{
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("Wechatpay-Serial", serialNo);
                httpGet.setHeader("Accept", "application/json");
                httpGet.setHeader("Content-Type", "application/json");
                httpGet.setHeader("user-agent", "用户代理(https://zh.wikipedia.org/wiki/User_agent)");
                httpGet.setHeader("Authorization", authorization);

                HttpClientBuilder httpClientBuilder = HttpClients.custom();
                CloseableHttpClient httpClient = httpClientBuilder.build();
                CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpResponseEntity = httpResponse.getEntity();
                String responseEntityStr = EntityUtils.toString(httpResponseEntity);
                httpResponse.close();
                System.out.println(responseEntityStr);
                jsonObject = JSONObject.fromObject(responseEntityStr);

            }


        }catch(Exception e){
            e.printStackTrace();
        }


        return jsonObject;
    }


    /**
     * 请求服务商
     */
    private static JSONObject httpV315(String method, String url, String requestParam) {
        JSONObject jsonObject = null;

        String authorization = getAuthorizationV(method, url, requestParam);

        try {

            // 平台证书路径 是必须的
            String serialNo = WeChatUtil.getSerialNo(WeChatUtil.publicsKeyPath);
            System.out.println(serialNo);
            System.out.println(authorization);


            if("POST".equalsIgnoreCase(method)) {
                HttpPost httpGet = new HttpPost(url);
                httpGet.setHeader("Wechatpay-Serial", serialNo);
                httpGet.setHeader("Accept", "application/json");
                httpGet.setHeader("Content-Type", "application/json");
                httpGet.setHeader("user-agent", "用户代理(https://zh.wikipedia.org/wiki/User_agent)");
                httpGet.setHeader("Authorization", authorization);
                StringEntity postingString = new StringEntity(requestParam,
                        "utf-8");
                httpGet.setEntity(postingString);
                HttpClientBuilder httpClientBuilder = HttpClients.custom();
                CloseableHttpClient httpClient = httpClientBuilder.build();
                CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpResponseEntity = httpResponse.getEntity();
                String responseEntityStr = EntityUtils.toString(httpResponseEntity);
                httpResponse.close();
                System.out.println(responseEntityStr);
                jsonObject = JSONObject.fromObject(responseEntityStr);

            }else{
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("Wechatpay-Serial", serialNo);
                httpGet.setHeader("Accept", "application/json");
                httpGet.setHeader("Content-Type", "application/json");
                httpGet.setHeader("user-agent", "用户代理(https://zh.wikipedia.org/wiki/User_agent)");
                httpGet.setHeader("Authorization", authorization);

                HttpClientBuilder httpClientBuilder = HttpClients.custom();
                CloseableHttpClient httpClient = httpClientBuilder.build();
                CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpResponseEntity = httpResponse.getEntity();
                String responseEntityStr = EntityUtils.toString(httpResponseEntity);
                httpResponse.close();
                System.out.println(responseEntityStr);
                jsonObject = JSONObject.fromObject(responseEntityStr);

            }


        }catch(Exception e){
            e.printStackTrace();
        }


        return jsonObject;
    }

    /**
     * 图片请求
     */
    public static String httpImage(String filePath) {

        String mediaID = "";
        try {

            File file = new File(filePath);
            String filename = file.getName();//文件名
            String fileSha256 = DigestUtils.sha256Hex(new FileInputStream(file));//文件sha256


            //时间戳
            long timestamp = (System.currentTimeMillis() / 1000);
            //随机数
            String nonce_str = WeChatUtils.getNonceStr();
            String authorization = getAuthorization("POST", ConstantURL.upload, "{\"filename\":\"" + filename + "\",\"sha256\":\"" + fileSha256 + "\"}");

            //接口URL
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(ConstantURL.upload);

            //设置头部
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "multipart/form-data");
            httpPost.addHeader("Authorization", authorization);


            //创建MultipartEntityBuilder
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
            //设置boundary
            multipartEntityBuilder.setBoundary("11111111");
            multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));
            //设置meta内容
            multipartEntityBuilder.addTextBody("meta", "{\"filename\":\"" + filename + "\",\"sha256\":\"" + fileSha256 + "\"}", ContentType.APPLICATION_JSON);
            //设置图片内容
            multipartEntityBuilder.addBinaryBody("file", file, ContentType.create("image/jpg"), filename);
            //放入内容
            httpPost.setEntity(multipartEntityBuilder.build());


            //获取返回内容
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            String rescontent = new String(InputStreamTOByte(httpEntity.getContent()),"utf-8");
            JSONObject jo = JSONObject.fromObject(rescontent);
            System.out.println(jo.toString());
            mediaID = jo.get("media_id").toString();
            System.out.println("返回内容:" + jo.get("media_id"));
            //获取返回的http header
            Header headers[] = response.getAllHeaders();
            int i = 0;
            while (i < headers.length) {
                System.out.println(headers[i].getName() + ":  " + headers[i].getValue());
                i++;
            }

            //验证微信支付返回签名
            String Wtimestamp = response.getHeaders("Wechatpay-Timestamp")[0].getValue();
            String Wnonce = response.getHeaders("Wechatpay-Nonce")[0].getValue();
            String Wsign = response.getHeaders("Wechatpay-Signature")[0].getValue();
            //拼装待签名串
            StringBuffer ss = new StringBuffer();
            ss.append(Wtimestamp).append("\n");
            ss.append(Wnonce).append("\n");
            ss.append(rescontent).append("\n");
            //验证签名
            if (verifyRSA(ss.toString(), Base64.decodeBase64(Wsign.getBytes()), rsaPublicKeyFile)) {
                System.out.println("签名验证成功");
            } else {
                System.out.println("签名验证失败");
            }

            EntityUtils.consume(httpEntity);
            response.close();
        } catch (Exception e) {
e.printStackTrace();
        }

        return mediaID;
    }


    /**
     * 获取证书
     */
    private static String httpCert(String authorization) {
        // 初始化一个HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(ConstantURL.cert);

        //设置头部
        httpGet.addHeader("Authorization", authorization);
        httpGet.addHeader("Accept", "application/json");
        httpGet.addHeader("User-Agent", "用户代理(https://zh.wikipedia.org/wiki/User_agent)");

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println("获取平台证书响应");
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
                String responseEntity = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject = JSONObject.fromObject(responseEntity);

                System.out.println(jsonObject.toString());
                AesUtil aesUtil = new AesUtil(v3key.getBytes(StandardCharsets.UTF_8));
                List<X509Certificate> x509Certs = new ArrayList<>();
                JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                for (Object o : jsonArray) {
                    System.out.println(o);
                    JSONObject bo = JSONObject.fromObject(o);
                    System.out.println(o);
//                    //加密内容
                    JSONObject encryptBo = JSONObject.fromObject(bo.get("encrypt_certificate").toString());
//
//                    //证书解密
                    String plainCert = aesUtil.decryptToString(encryptBo.get("associated_data").toString().getBytes(StandardCharsets.UTF_8),
                            encryptBo.get("nonce").toString().getBytes(StandardCharsets.UTF_8),
                            encryptBo.get("ciphertext").toString());
                    System.out.println("证书明文：\n" + plainCert + "\n\n");

                    //反序列化证书
                    X509Certificate x509Cert = PemUtil.loadCertificate(new ByteArrayInputStream(plainCert.getBytes("utf-8")));
                    x509Certs.add(x509Cert);
                    //保存证书
                    String outputAbsoluteFilename = "G:\\publicKey.pem";
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputAbsoluteFilename), StandardCharsets.UTF_8))) {
                        writer.write(plainCert);
                    }
                    System.out.println("输出证书文件目录：" + outputAbsoluteFilename);
                }

            }
        } catch (Exception e) {
            System.out.println("执行httpclient请求平台证书序号错误" + e);
        }

        return null;
    }


    public static byte[] InputStreamTOByte(InputStream in) throws IOException {

        int BUFFER_SIZE = 4096;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;

        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        byte[] outByte = outStream.toByteArray();
        outStream.close();

        return outByte;
    }

    /////////////////////////////////////////////////////////////////////////////////////应用//////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取平台证书
     *
     * @return
     */
    public static String getPublicKey() {
        //时间戳
        long timestamp = (System.currentTimeMillis() / 1000);
        //随机数
        String nonce_str = WeChatUtils.getNonceStr();
        String authorization = getAuthorization("GET", ConstantURL.cert, "");
        String rescontent = httpCert(authorization);

        return rescontent;
    }

    /**
     * 二级商户进件
     *
     * @return
     */
    public static JSONObject applyments(ApplyParam param) {

        String requestParam = JSON.toJSONString(param);


        JSONObject body = httpV3("POST", ConstantURL.apply, requestParam);

        System.out.print(body);


        return body;

    }


    /**
     * 查询二级商户进件
     *
     * @throws Exception
     */
    public static ApplyResult applymentsQuery(String out_request_no,ApplyResult applyResult)  throws Exception {
        // "https://api.mch.weixin.qq.com/v3/ecommerce/applyments/out-request-no/"

        String url = ConstantURL.applyout+out_request_no;

        String authorization = getAuthorization("GET", url, "");
        String serialNo = WeChatUtil.getSerialNo(WeChatUtil.publicKeyPath); //平台证书
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Wechatpay-Serial", serialNo);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("user-agent", "用户代理(https://zh.wikipedia.org/wiki/User_agent)");
        httpGet.setHeader("Authorization", authorization);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        CloseableHttpClient httpClient = httpClientBuilder.build();
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        HttpEntity httpResponseEntity = httpResponse.getEntity();

        String responseEntityStr = EntityUtils.toString(httpResponseEntity);
        httpResponse.close();
        System.out.println(responseEntityStr);

        JSONObject jo = JSONObject.fromObject(responseEntityStr);
        if(applyResult==null){
            applyResult = new ApplyResult();
        }

        String applyment_state = jo.get("applyment_state").toString();

        //CHECKING：资料校验中   ACCOUNT_NEED_VERIFY：待账户验证  AUDITING：审核中 REJECTED：已驳回 NEED_SIGN：待签约 FINISH：完成 FROZEN：已冻结
        applyResult.setApplyment_state(applyment_state); //申请状态
        applyResult.setApplyment_state_desc(jo.get("applyment_state_desc").toString());  //申请状态描述
        applyResult.setOut_request_no(jo.get("out_request_no").toString());  //业务申请编号
        applyResult.setApplyment_id(jo.get("applyment_id").toString());   //微信支付申请单号

        System.out.println(applyment_state);

//        //待签约，返回签约链接二维码图片的base64数据
        if (applyment_state.equals("NEED_SIGN")) {
            String sign_url = jo.get("sign_url").toString();
            applyResult.setSign_url(sign_url);
//            byte[] generatePngByte = cn.hutool.extra.qrcode.QrCodeUtil.generatePng(sign_url, 230, 230);
//            String base64Png = Base64.encode(generatePngByte);
        }
        //当申请状态为NEED_SIGN或FINISH时才返回。 电商平台二级商户号
        if (applyment_state.equals("NEED_SIGN")||applyment_state.equals("FINISH")) {
            String sub_mchid = jo.get("sub_mchid").toString();
            applyResult.setSub_mchid(sub_mchid);

        }
        //当申请状态为ACCOUNT_NEED_VERIFY 时有返回，可根据指引汇款，完成账户验证。 汇款账户验证信息
        if (applyment_state.equals("ACCOUNT_NEED_VERIFY")) {
            JSONObject account_validation = jo.getJSONObject("account_validation");
            AccountValidation  accountValidation = new AccountValidation();
            accountValidation.setAccount_name(RsaCryptoUtil.decryptOAEP(account_validation.getString("account_name")));   //  付款户名	account_name  解密
            if(account_validation.getString("account_no")!=null&&!account_validation.getString("account_no").equals("")) {
                //结算账户为对私时会返回，商户需使用该付款卡号进行汇款。
                accountValidation.setAccount_no(RsaCryptoUtil.decryptOAEP(account_validation.getString("account_no")));    // 付款卡号	account_no 解密
            }
            accountValidation.setPay_amount(account_validation.getString("pay_amount"));    //  汇款金额	pay_amount
            accountValidation.setDestination_account_number(account_validation.getString("destination_account_number"));   //收款卡号	destination_account_number
            accountValidation.setDestination_account_name(account_validation.getString("destination_account_name")); //收款户名	destination_account_name
            accountValidation.setDestination_account_bank(account_validation.getString("destination_account_bank"));  //开户银行	destination_account_bank
            accountValidation.setCity(account_validation.getString("city"));   //省市信息	city
            accountValidation.setRemark(account_validation.getString("remark"));      //备注信息	remark  商户汇款时，需要填写的备注信息。 示例值：入驻账户验证
            accountValidation.setDeadline(account_validation.getString("deadline"));  //汇款截止时间	deadline	string[1,20]  v请在此时间前完成汇款。示例值：2018-12-10 17:09:01
            applyResult.setAccount_validation(accountValidation);

            /**
             *
             * 当申请状态为
             ACCOUNT_NEED_VERIFY，且通过系统校验的申请单，将返回链接。
             2、建议将链接转为二维码展示，让商户法人用微信扫码打开，完成账户验证。
             */
            applyResult.setSign_url(jo.getString("legal_validation_url").toString());   //法人验证链接
        }
        //
        if (applyment_state.equals("REJECTED")||applyment_state.equals("FROZEN")) {
            List<AuditDetail>  auditDetaillist = new ArrayList<AuditDetail>();
            JSONArray audit_detail  = jo.getJSONArray("audit_detail");

            for (int i = 0;i<audit_detail.size();i++){
                AuditDetail auditDetail = new AuditDetail();
                JSONObject jsonObject = (JSONObject)audit_detail.get(i);
                auditDetail.setParam_name(jsonObject.getString("param_name"));
                auditDetail.setReject_reason(jsonObject.getString("reject_reason"));
                auditDetaillist.add(auditDetail);
            }

            applyResult.setAudit_detail(auditDetaillist);
        }

        return applyResult;



    }



    /**
     *
     *
     * 查询余额
     * @return
     */
    public static JSONObject balanceResult(String sub_mchid){




        JSONObject body = httpV3("GET", ConstantURL.balance+sub_mchid,"");

        System.out.print(body);

        return body;
    }


    public static void main(String[] args) {
       // System.out.println("11111111");
       /// balanceResult("1627898266");
//        Withdraw withdraw = new Withdraw();
//        withdraw.setSub_mchid("1627898266");
//        withdraw.setOut_request_no("tx2022090705151200000");
//        searchWithdraw(withdraw);
      // withdraw("1627898266",5,"2022080102040300002");
//        try {
//            //searchOrder("2021010510072200001");
//            //  refunds();
//            // withdraw();
////            //balanceResult();
//profitsharingResult();
     //   profitsharingResult2();
//            //  receiversAdd();
//            //  profitsharing();
//            // apply();
////applymentsQuery("APPLYWFJ_00000000004");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
       getPublicKey();

        // httpImage("E:\\1.png");
//        ApplyParam param = new ApplyParam();
//      //  param.setOut_request_no(System.currentTimeMillis()+"");  //商户唯一编号。
//
//        param.setOut_request_no("APPLYWFJ_00000000002");
//        param.setOrganization_type("2401");//小微商户，指无营业执照的个人商家。
//
//        param.setId_doc_type("IDENTIFICATION_TYPE_MAINLAND_IDCARD");//身份证
//
//        IdCardInfo idCardInfo = new IdCardInfo();//身份证信息
//
//
//        idCardInfo.setId_card_copy(httpImage("E:\\22.jpg"));  //身份证人像面照片
//        idCardInfo.setId_card_national(httpImage("E:\\11.jpg"));//身份证国徽面照片
//
//
//       //身份证
//        //该字段需进行加密处理  孟竹
//        idCardInfo.setId_card_name("LOCEEYJhxkKIs8mQiGekfjpTlYFVSZDbxTW1t3lq4v8yUYamX5slPRmp7nKmN0UXbgkbiRHr2guH52GzZWvfQ3/wkvQvOsXMUDu11FrQVcAKbY5+0gK8M3i1YjBh9WtpHGeP8sD2cQ2xCJDtM6vbNOyUA0si3lVt05YZxqb+F/P1xq6s1Ju6wCzl9en+oem0MrAfzspfUw6pAllYytRSFp4daGkpS64hRzMZY+YhElpE7PVZUEBYTQ2nnZ7zMQEOYM8GtEhXYEI1w2TbkKDd8waojodQ3QMRhAivqWeG/EDmpHx5EEYiHv1QL1QJspqgR2kFAHRxljgoRV7zP9jGDQ==");
//        //该字段需进行加密处理
//        idCardInfo.setId_card_number("XzY5oGtZBTTc6nH7WboakmaG77/veQu7MyWjuskEckWxT2oFwyUFoDzCcWwA2lXrKs/Y2T4i6Cs9ScrzTAgbfVY20kIFI+wlUcAvMGmYstmTsFRKXfXUoQZ6SxjYxyiVysr1LsdhpBqlVanD0wSeKPfYFEIpnmvst+nb6ov9pOnGXDGdmHJNxP28oezI6evNi2WekXlB13dpjSFpbYdYrrz5YcvbXwAttPmeG3Qz6Ki4D813CnmRUMwNvKqSn0JINw5KTKmyqxHgcJO8Ws0Qf3tvi4s/S+52red70XRi8WOT58QT2WeNJkK+HRUqWg/sVgQ79FeqvSXnf/QlEWL5RQ==");
//        idCardInfo.setId_card_valid_time("2037-02-03");
//
//        param.setId_card_info(idCardInfo);
//
//        param.setNeed_account_info(true);
//        //银行卡
//        AccountInfo accountInfo = new AccountInfo();
//        accountInfo.setBank_account_type("75");
//        accountInfo.setAccount_bank("农业银行");
//        //该字段需进行加密处理
//        accountInfo.setAccount_name("ri2CzfN4yGulzFkV/7/Z+sQPhwQE3fEGEEtMcH9Zo5QharRP5QbKPN6M137PjHXUanb1EWZpDz8hf+//pBBU4S0qCmbTrXkJSzkiK7KPtWXs2ehdrGZ9rmrz6s0a7U9D0MmzsWkiMI20hpCqasDF73Owzt3l9wt1+IfZKQtdFiqUdmWiY3Qxi7MzdAtf5BA9+YIWiWqE7cf7plc8zuW7+smi/ySo6gT75augf+B3cleVdPlwSvZcokWmWRel7Jur+6mXAI9/csC6WH6mdvO/ehW6jxtTzKnDq9DvsGgUHdpN4DefU8UZB8phspYU+i7GoFGlMrW/Bxn67cSxlvoTow==");
//        accountInfo.setBank_address_code("110108");
//        //该字段需进行加密处理
//        accountInfo.setAccount_number("s+hj1p4/OB4HVNO7cJhQnsIh6V18QbXSOVqIr2nHRIaz3+EJ0NX040PTJvBPFrkF2c4tPTj6mSTFA9h0UU/Wvox96BJwTGurGyyJoh9ZSqXBG54/O4KHhSAIQ49Ngyr6hQ1UY0rwIubn243l9YOJ4HlyqlFCktn3jdlwkoSGr65xaLCJ7vgLwmj0f7L+MvYQ1OvMcyueraGYuN5dD0PG595lDP3LR8cg8cywElrY6/3gDE8ruicvWCPepPIX75SyNqu4e+c64hS0UQvBQNytqgs9WrTSa76YMLZMfShuJvpacCow8nvvnaVMP+9RvIUw1217LkYC0sMBgJETGjaEeA==");
//
//        param.setAccount_info(accountInfo);
//
//        ContactInfo contactInfo = new ContactInfo();
//        contactInfo.setContact_type("65");
//        //该字段需进行加密处理
//        contactInfo.setContact_name("ri2CzfN4yGulzFkV/7/Z+sQPhwQE3fEGEEtMcH9Zo5QharRP5QbKPN6M137PjHXUanb1EWZpDz8hf+//pBBU4S0qCmbTrXkJSzkiK7KPtWXs2ehdrGZ9rmrz6s0a7U9D0MmzsWkiMI20hpCqasDF73Owzt3l9wt1+IfZKQtdFiqUdmWiY3Qxi7MzdAtf5BA9+YIWiWqE7cf7plc8zuW7+smi/ySo6gT75augf+B3cleVdPlwSvZcokWmWRel7Jur+6mXAI9/csC6WH6mdvO/ehW6jxtTzKnDq9DvsGgUHdpN4DefU8UZB8phspYU+i7GoFGlMrW/Bxn67cSxlvoTow==");
//        //该字段需进行加密处理
//        contactInfo.setContact_id_card_number("NL3jaVlkCjnbF2N1/FZ6Zm32iMuk7mYgwrucHnUceVJBjy0cy4ztxyK+CC/vESWgiNhugk7V0h6Jgdnw4Dd6rBTPX1O48ddg03aRrqRlZC8VM0pXFkGXX15JzqDaRg4BIbaLrAu2mwHiHlL8V638YE5xVENsVYmr1PzB1NxntLXOC9qEP5erKdAjAL/bF0KtkxtCIsa7S9xH8swZX42PkxOXJIffx515tj3rZOzmROG8nhTDLQfHfosfPyIen6AW9AlqbAAXWdF0nh4wlfR60rSPTSDGIBdLTATyCz3gLDydejEmcpfI4VEmgUqwoNFAYdtZC+EfSKtP/EzmjNYOSQ==");
//        //该字段需进行加密处理
//        contactInfo.setMobile_phone("aNDRNbB+/gzuyncZsUnxs+XV4Lkx1BzcaAoza5I87uZC8DqBVEi47T7GAkpHHnKferWoFFRXWLP23/FXlXbVJS69M4fNoA/KNdABzgEz7c0k3kPuwmOEu1HUp/GEvjAJzJ5sScr+ue0LLBe4l2O85MnYvaD0g48wL0mLDutb4dpCXFi+HAtpUkQ4TKaFtBrxUl34azjOdwrs6dE9triHyhqj/1aNLbBEtwrhzwC6dVW+7p8t6q9IqRICkuVvzaGQO7KdFKqybMpagEAcxYfiJUpuD3PT2Cwd0lsMc+7cGKpGGkxwUTPhMkCTNOQkinsI/lN/zuVn/eGoplXBMDHcFQ==");
//        //该字段需进行加密处理
//   //     contactInfo.setContact_email("TT++JXKKwFLscUIfpSFniSP5uc3ITGcmV0oP1RnFZNdrYm42C8tXIw3RinsFwfEsvTdM1fc7TrKXVJK7w7eZ8fbAdkUHwhJ9DyLxwxtGz21mSiOpIgaoRqrOuQ623jx4myGXK4O02ejmUVgSuuSGTCXcebtS+12m5MaC6PGqYjbVfExz/83CEifmu57HNnde/YfQCSB4gbmFi2cILNF5Eoa4oVVwYlvbYW4ZHCq7cdZVKx2yREu5qwWUnQWChT5EFtR5Q+CCmbBZyX64g8VHtZPMOjbbj6d4sl/bn6NjKsBKIfroFuH1W/8aofKPZkqVG/A0thXkhDGsHFX6aKFUuQ==");
//
//        param.setContact_info(contactInfo);
//
//        SalesSceneInfo salesSceneInfo = new SalesSceneInfo();
//        salesSceneInfo.setStore_name("数字地球");
//       // salesSceneInfo.setStore_qr_code("gCVSq6j0dKRaup7VRA3aCfDX5pxgObgXhlOlmUdpskWnlXBmdLyOUNSRzFK1HT1y_9tPWGTr_mxBiD8ICwUvNVr2Pq-JdH6UsZ4Ayo7eVDo");
//        salesSceneInfo.setStore_url("http://www.impf2010.com");
//        param.setSales_scene_info(salesSceneInfo);
//
//        param.setMerchant_shortname("微分金");
//
//   applyments(param);

//        PayParam  app_PayParam = new PayParam();
//        app_PayParam.setCombine_appid("wxf338ed62f8ec11cf");
//        app_PayParam.setCombine_mchid("1604065060");
//        app_PayParam.setCombine_out_trade_no("P20200806125341");
//        List<SubOrders> sub_orderslist = new ArrayList<SubOrders>();
//        SubOrders subOrders = new SubOrders();
//        subOrders.setMchid("1604065060");
//        subOrders.setAttach("测试");
//        subOrders.setOut_trade_no("20200806125346");
//        subOrders.setSub_mchid("1604771870");
//        subOrders.setDescription("数字地球-测试商品");
//
//        Amount amount = new Amount();
//        amount.setTotal_amount(1);
//        amount.setCurrency("CNY");
//        subOrders.setAmount(amount);
//
//
//        SettleInfo settle_info = new SettleInfo();
//        settle_info.setProfit_sharing(true);
//        settle_info.setSubsidy_amount(0);
//        subOrders.setSettle_info(settle_info);
//        sub_orderslist.add(subOrders);
//        app_PayParam.setSub_orders(sub_orderslist);
//
//
//        CombinePayerInfo combine_payer_info = new CombinePayerInfo();
//        combine_payer_info.setOpenid("");
//        app_PayParam.setCombine_payer_info(combine_payer_info);
//
//        app_PayParam.setTime_start(Utilities.getDateString(new Date(),"YYYY-MM-DDTHH:mm:ss+TIMEZONE"));
//        app_PayParam.setTime_expire(Utilities.getDateString(new Date(),"YYYY-MM-DDTHH:mm:ss+TIMEZONE"));
//        app_PayParam.setNotify_url("https://test.impf2010.com/ea/wechat/sajax_ea_notifyResult.jspa");
//
//
//
//
//        combineppPay(app_PayParam);
//        WxPayDto tpWxPayDto = new WxPayDto();
//        tpWxPayDto.setWechatbz("apppay");
//        AppPackage  ss = getAppPackageV3(tpWxPayDto,null,null,ConstantURL.APP_PAY);
    }


    /**
     * 从文件中读取公钥
     *
     * @param publicKeyPath 公钥文件路径  (required)
     * @return 公钥内容
     */
    public static PublicKey getPublicKey(String publicKeyPath) {
        if (publicKeyPath == null) {
            return null;
        }
        PublicKey pk = null;
        InputStream is = null;
        try {
            is = new ClassPathResource(publicKeyPath).getInputStream();
            //is = new FileInputStream(publicKeyPath);

            CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(is);
            pk = cert.getPublicKey();
        } catch (CertificateException | IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pk;
    }

    public static void test() throws Exception {

        String url = "https://api.mch.weixin.qq.com/v3/ecommerce/applyments/out-request-no/APPLYWFJ_00000000001";

        String authorization = getAuthorization("GET", url, "");

//        String serialNo = WeChatUtil.getSerialNo(WeChatUtil.apiclient_cert);
//        String token = WeChatUtil.getToken("GET", url, null, yourMerchantId, serialNo);
//        String authorization = "WECHATPAY2-SHA256-RSA2048 " + token;
        // 平台证书路径 是必须的
        String serialNo = WeChatUtil.getSerialNo(WeChatUtil.publicKeyPath);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Wechatpay-Serial", serialNo);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("user-agent", "用户代理(https://zh.wikipedia.org/wiki/User_agent)");
        httpGet.setHeader("Authorization", authorization);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        CloseableHttpClient httpClient = httpClientBuilder.build();
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpResponseEntity = httpResponse.getEntity();
        String responseEntityStr = EntityUtils.toString(httpResponseEntity);
        httpResponse.close();
        System.out.println(responseEntityStr);


    }



    /**
     * 合并支付获取预订单号
     *
     * @return
     */
    public static JSONObject combineppPay(PayParam param,String url) {

        String requestParam = JSON.toJSONString(param);


        JSONObject body = httpV3("POST", url, requestParam);

        System.out.print(body);


        return body;

    }


    /**
     * 服务商h5
     *
     * @return
     */
    public static JSONObject serverh5Pay(ServicePayParam param,String url) {

        String requestParam = JSON.toJSONString(param);


        JSONObject body = httpV315("POST", url, requestParam);

        System.out.print(body);


        return body;

    }


    /**
     * APP微信支付 获取请求预支付id报文以及最终调起支付请求包V3
     *
     * @return
     */
    @SuppressWarnings("static-access")
    public static AppPackage getAppPackageV3(WxPayDto tpWxPayDto,List<SubOrders>  sub_orderslist,CombinePayerInfo combine_payer_info ,String url) {

        String appid = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appID");

        PayParam  app_PayParam = new PayParam();
        app_PayParam.setCombine_appid(appid);
        app_PayParam.setCombine_mchid(WeChatUtil.MCH_ID);
        app_PayParam.setCombine_out_trade_no(tpWxPayDto.getOrderId());

        List<SubOrders> newSublist  = new ArrayList<SubOrders>();
        for(int i=0;i<sub_orderslist.size();i++){
            SubOrders subOrders =  sub_orderslist.get(i);
            subOrders.setMoney(null);
            subOrders.setSoId(null);
            subOrders.setSokey(null);
            newSublist.add(subOrders);
        }

        app_PayParam.setSub_orders(newSublist);



        app_PayParam.setCombine_payer_info(combine_payer_info);

//        app_PayParam.setTime_start(Utilities.getDateString(new Date(),"YYYY-MM-DDTHH:mm:ss+TIMEZONE"));
//        app_PayParam.setTime_expire(Utilities.getDateString(new Date(),"YYYY-MM-DDTHH:mm:ss+TIMEZONE"));
        String notifyurl = getNoticeURL();

        app_PayParam.setNotify_url(notifyurl);


        String  prepayid = combineppPay(app_PayParam,url).getString("prepay_id");



//        String appsecret = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appSecret");
//        String partnerkey = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("partnerkey");
        //   String mch_id = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("mchid");


        // 随机字符串
        String nonce_str = WeChatUtils.getNonceStr();
        long timestamp = (System.currentTimeMillis() / 1000);
        AppPackage appPackage = new AppPackage();


        appPackage.setAppid(appid);//签名

        appPackage.setPrepayid(prepayid);//签名

        appPackage.setNoncestr(nonce_str);//签名
        appPackage.setTimestamp(timestamp+"");//签名

        String  crt = appid + "\n"+
                + timestamp + "\n"
                + nonce_str + "\n"
                + prepayid + "\n";

        String signature = null;
        try {
            signature = signRSA(crt, rsaPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        appPackage.setPackages("Sign=WXPay");
        appPackage.setPartnerid(WeChatUtil.MCH_ID);
        appPackage.setSign(signature);







        return appPackage;
    }




    /**
     * 合单支付h5
     *
     * @return
     */
    @SuppressWarnings("static-access")
    public static String getH5_url(WxPayDto tpWxPayDto,List<SubOrders>  sub_orderslist,String url) {

        String appid = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appID");

        PayParam  app_PayParam = new PayParam();
        app_PayParam.setCombine_appid(appid);
        app_PayParam.setCombine_mchid(WeChatUtil.MCH_ID);
        app_PayParam.setCombine_out_trade_no(tpWxPayDto.getOrderId());
        List<SubOrders> newSublist  = new ArrayList<SubOrders>();
        for(int i=0;i<sub_orderslist.size();i++){
            SubOrders subOrders =  sub_orderslist.get(i);
            subOrders.setMoney(null);
            subOrders.setSoId(null);
            subOrders.setSokey(null);
            newSublist.add(subOrders);
        }

        app_PayParam.setSub_orders(newSublist);



        SceneInfo scene_info= new SceneInfo();
        scene_info.setDevice_id(WeChatUtils.getNonceStr());
        scene_info.setPayer_client_ip(tpWxPayDto.getSpbillCreateIp());
        H5info h5_info = new H5info();
        h5_info.setType("Wap");

        scene_info.setH5_info(h5_info);

        app_PayParam.setScene_info(scene_info);

        String notifyurl = getNoticeURL();

        app_PayParam.setNotify_url(notifyurl);


        String  h5_url = combineppPay(app_PayParam,url).getString("h5_url");



        return h5_url;
    }



    /**
     * 服务商h5
     *
     * @return
     */

    public static String getH5Service_url(WxPayDto tpWxPayDto,String url) {


        ServicePayParam  sapp_PayParam = new ServicePayParam();
        sapp_PayParam.setSp_appid(Constant.WXSP_APPID);
        sapp_PayParam.setSp_mchid(Constant.WXSP_Mchid);
        sapp_PayParam.setSub_appid(Constant.WXSP_SUBAPPID);
        sapp_PayParam.setSub_mchid(Constant.WXSP_SUBMchid);
        sapp_PayParam.setDescription(tpWxPayDto.getBody());
        sapp_PayParam.setAttach(tpWxPayDto.getAttach());
        String notifyurl = getNoticeURL();

        sapp_PayParam.setNotify_url(notifyurl);

        sapp_PayParam.setOut_trade_no(tpWxPayDto.getOrderId());


        SceneInfo scene_info= new SceneInfo();
        scene_info.setDevice_id(WeChatUtils.getNonceStr());
        scene_info.setPayer_client_ip(tpWxPayDto.getSpbillCreateIp());
        H5info h5_info = new H5info();
        h5_info.setType("Wap");

        scene_info.setH5_info(h5_info);

        sapp_PayParam.setScene_info(scene_info);

        AmountH5 amount = new AmountH5();
        amount.setCurrency("CNY");
        amount.setTotal(Integer.parseInt(WeChatUtils.getMoney(tpWxPayDto.getTotalFee())));

        sapp_PayParam.setAmount(amount);
        String  h5_url = serverh5Pay(sapp_PayParam,url).getString("h5_url");



        return h5_url;
    }


    /**
     * 服务商JSAPI支付
     *
     * @return
     */

    public static FinalPackage getPackage(WxPayDto tpWxPayDto) {


        ServicePayParam  sapp_PayParam = new ServicePayParam();
        sapp_PayParam.setSp_appid(Constant.WXSP_APPID);
        sapp_PayParam.setSp_mchid(Constant.WXSP_Mchid);
        sapp_PayParam.setSub_appid(Constant.WXSP_SUBAPPID);
        sapp_PayParam.setSub_mchid(Constant.WXSP_SUBMchid);
        sapp_PayParam.setDescription(tpWxPayDto.getBody());
        sapp_PayParam.setAttach(tpWxPayDto.getAttach());
        String notifyurl = getNoticeURL();

        sapp_PayParam.setNotify_url(notifyurl);

        sapp_PayParam.setOut_trade_no(tpWxPayDto.getOrderId());


        AmountH5 amount = new AmountH5();
        amount.setCurrency("CNY");
        amount.setTotal(Integer.parseInt(WeChatUtils.getMoney(tpWxPayDto.getTotalFee())));

        sapp_PayParam.setAmount(amount);



        Payer payer = new Payer();
        payer.setSp_openid(tpWxPayDto.getOpenId());

        sapp_PayParam.setPayer(payer);

        String  prepay_id = serverh5Pay(sapp_PayParam,ConstantURL.JSapiServer_PAY).getString("prepay_id");


        System.out.println("获取到的预支付IDServer：" +prepay_id);



        // 随机字符串
        String nonce_str = WeChatUtils.getNonceStr();
        long timestamp = (System.currentTimeMillis() / 1000);



        String  crt =  Constant.WXSP_APPID + "\n"+
                + timestamp + "\n"
                + nonce_str + "\n"
                + "prepay_id="+prepay_id + "\n";

        String signature = null;
        try {
            signature = signRSA(crt, WXSP_rsaPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FinalPackage finalPackages = new FinalPackage();
        finalPackages.setAppId(Constant.WXSP_APPID);
        finalPackages.setTimeStamp(timestamp+"");
        finalPackages.setNonceStr(nonce_str);
        finalPackages.setPackages("prepay_id="+prepay_id);
        finalPackages.setSignType("RSA");
        finalPackages.setPaySign(signature);


        return finalPackages;
    }
    
    
    
     /**
      * 
      * 服务商app支付
      * @param tpWxPayDto
      * @return
      */
	public static AppPackage getAppPackage(WxPayDto tpWxPayDto) {
		
		
		    ServicePayParam  sapp_PayParam = new ServicePayParam();
	        sapp_PayParam.setSp_appid(Constant.WXSPAPP_APPID);
	        sapp_PayParam.setSp_mchid(Constant.WXSP_Mchid);
	        sapp_PayParam.setSub_appid(Constant.WXSPAPP_APPID);
	        sapp_PayParam.setSub_mchid(Constant.WXSP_SUBMchid);
	        sapp_PayParam.setDescription("数字地球微分金-" + tpWxPayDto.getBody());
	        sapp_PayParam.setAttach(tpWxPayDto.getAttach());
            String notifyurl = getNoticeURL();

	        sapp_PayParam.setNotify_url(notifyurl);

	        sapp_PayParam.setOut_trade_no(tpWxPayDto.getOrderId());


	        AmountH5 amount = new AmountH5();
	        amount.setCurrency("CNY");
	        amount.setTotal(Integer.parseInt(WeChatUtils.getMoney(tpWxPayDto.getTotalFee())));

	        sapp_PayParam.setAmount(amount);

	        String  prepay_id = serverh5Pay(sapp_PayParam,ConstantURL.App_PAY).getString("prepay_id");


	        System.out.println("获取到的预支付APPIDServer：" +prepay_id);



	        // 随机字符串
	        String nonce_str = WeChatUtils.getNonceStr();
	        long timestamp = (System.currentTimeMillis() / 1000);



	        String  crt =  Constant.WXSPAPP_APPID + "\n"+
	                + timestamp + "\n"
	                + nonce_str + "\n"
	                + prepay_id + "\n";

	        String signature = null;
	        try {
	        	
	        	
	            signature = signRSA(crt, WXSP_rsaPrivateKey);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

		
		AppPackage appPackage = new AppPackage();
		appPackage.setAppid(Constant.WXSPAPP_APPID);
		appPackage.setPartnerid(Constant.WXSP_Mchid);
		appPackage.setPrepayid(prepay_id);
		appPackage.setPackages("Sign=WXPay");
		appPackage.setNoncestr(nonce_str);
		appPackage.setTimestamp(timestamp+"");
		appPackage.setSign(signature);
		appPackage.setErr_code("");
		
		
		
		
		

		return appPackage;
	}


    public static String getNoticeURL(){


         HttpServletRequest request = ServletActionContext.getRequest();
         String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
         String notifyurl = basePath + "/ea/wfjshop/sajax_ea_notifyResult.jspa";

        return notifyurl;
    }

    /**
     * JSAPI微信支付 获取请求预支付id报文以及最终调起支付请求包V3
     *
     * @return
     */
    @SuppressWarnings("static-access")
    public static FinalPackage getPackageV3(WxPayDto tpWxPayDto,List<SubOrders>  sub_orderslist,CombinePayerInfo combine_payer_info ,String url) {

        String appid = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appID");

        PayParam  app_PayParam = new PayParam();
        app_PayParam.setCombine_appid(appid);
        app_PayParam.setCombine_mchid(WeChatUtil.MCH_ID);
        app_PayParam.setCombine_out_trade_no(tpWxPayDto.getOrderId());

        List<SubOrders> newSublist  = new ArrayList<SubOrders>();
        for(int i=0;i<sub_orderslist.size();i++){
            SubOrders subOrders =  sub_orderslist.get(i);
            subOrders.setMoney(null);
            subOrders.setSoId(null);
            subOrders.setSokey(null);
            newSublist.add(subOrders);
        }
        app_PayParam.setSub_orders(newSublist);



        app_PayParam.setCombine_payer_info(combine_payer_info);

        String notifyurl = getNoticeURL();

        app_PayParam.setNotify_url(notifyurl);


        String  prepay_id = combineppPay(app_PayParam,url).getString("prepay_id");



        // 随机字符串
        String nonce_str = WeChatUtils.getNonceStr();
        long timestamp = (System.currentTimeMillis() / 1000);

        String  crt = appid + "\n"+
                + timestamp + "\n"
                + nonce_str + "\n"
                + "prepay_id="+prepay_id + "\n";

        String signature = null;
        try {
            signature = signRSA(crt, rsaPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FinalPackage finalPackages = new FinalPackage();
        finalPackages.setAppId(appid);
        finalPackages.setTimeStamp(timestamp+"");
        finalPackages.setNonceStr(nonce_str);
        finalPackages.setPackages("prepay_id="+prepay_id);
        finalPackages.setSignType("RSA");
        finalPackages.setPaySign(signature);

        return finalPackages;
    }

    /**
     *
     *
     *  企业商户申请
     */
    public static void apply(){
        ApplyParam param = new ApplyParam();
        //  param.setOut_request_no(System.currentTimeMillis()+"");  //商户唯一编号。

        param.setOut_request_no("APPLYWFJ_00000000004");
        param.setOrganization_type("2");//企业

        //营业执照
        BusinessLicenseInfo  business_license_info = new BusinessLicenseInfo();
        business_license_info.setBusiness_license_copy(httpImage("E:\\mysw.jpg"));  //证件扫描件
        business_license_info.setBusiness_license_number("915107067847102028");
        business_license_info.setMerchant_name("绵阳市胜威驾驶培训有限公司");
        business_license_info.setLegal_person("刘太平");
        // business_license_info.setCompany_address("");//主体为“党政、机关及事业单位/其他组织”时必填
        // business_license_info.setBusiness_time("");  //主体为“党政、机关及事业单位/其他组织”时必填
        param.setBusiness_license_info(business_license_info);


//        //组织机构代码
//        OrganizationCertInfo organization_cert_info = new OrganizationCertInfo();
//
//        organization_cert_info.setOrganization_copy(httpImage("E:\\myswzj.jpg"));//主体为“企业/党政、机关及事业单位/其他组织”，且营业执照/登记证书号码不是18位时必填
//        organization_cert_info.setOrganization_number("78471020-2");
//        organization_cert_info.setOrganization_time("[\"2015-03-31\",\"2021-03-30\"]");
//
//        param.setOrganization_cert_info(organization_cert_info);

        //经营者/法人身份证信息 身份证
        param.setId_doc_type("IDENTIFICATION_TYPE_MAINLAND_IDCARD");

        IdCardInfo idCardInfo = new IdCardInfo();//身份证信息
        idCardInfo.setId_card_copy(httpImage("E:\\ltprt.jpg"));  //身份证人像面照片
        idCardInfo.setId_card_national(httpImage("E:\\ltpgh.jpg"));//身份证国徽面照片
        //该字段需进行加密处理
        idCardInfo.setId_card_name(RsaCryptoUtil.ncrypt("刘太平"));//刘太平
        //该字段需进行加密处理
        idCardInfo.setId_card_number(RsaCryptoUtil.ncrypt("511022196311157416"));        //身份证
        idCardInfo.setId_card_valid_time("长期");
        param.setId_card_info(idCardInfo);


        //银行卡结算
       // param.setNeed_account_info(true);//是否填写结算账户信息
        //银行卡
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setBank_account_type("74");//对公账户
        accountInfo.setAccount_bank("其他银行");
        //该字段需进行加密处理
        accountInfo.setAccount_name(RsaCryptoUtil.ncrypt("绵阳市胜威驾驶培训有限公司"));
        accountInfo.setBank_address_code("510700");
        accountInfo.setBank_name("绵阳市商业银行股份有限公司绵州支行");
        //该字段需进行加密处理
        accountInfo.setAccount_number(RsaCryptoUtil.ncrypt("07010140900002253"));
        param.setAccount_info(accountInfo);


        //超级管理员信息
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setContact_type("66");
        //该字段需进行加密处理
        contactInfo.setContact_name(RsaCryptoUtil.ncrypt("孟竹"));
        //该字段需进行加密处理
        contactInfo.setContact_id_card_number(RsaCryptoUtil.ncrypt("210522198903092024"));
        //该字段需进行加密处理
        contactInfo.setMobile_phone(RsaCryptoUtil.ncrypt("15210904250"));
        //该字段需进行加密处理
        contactInfo.setContact_email(RsaCryptoUtil.ncrypt("495396980@qq.com"));
        param.setContact_info(contactInfo);

        SalesSceneInfo salesSceneInfo = new SalesSceneInfo();
        salesSceneInfo.setStore_name("绵阳市胜威驾驶培训有限公司");
        // salesSceneInfo.setStore_qr_code("gCVSq6j0dKRaup7VRA3aCfDX5pxgObgXhlOlmUdpskWnlXBmdLyOUNSRzFK1HT1y_9tPWGTr_mxBiD8ICwUvNVr2Pq-JdH6UsZ4Ayo7eVDo");
        salesSceneInfo.setStore_url("http://www.impf2010.com");
        param.setSales_scene_info(salesSceneInfo);

        param.setMerchant_shortname("绵阳胜威驾校");

        applyments(param);



    }




    /**
     *  修改结算帐号API
     *
     *
     * @return
     */
    public static JSONObject modifySettlement(){
        ModifySettlement modifySettlement = new  ModifySettlement();
        modifySettlement.setAccount_type("");
        modifySettlement.setAccount_bank("");

        String requestParam = JSON.toJSONString(modifySettlement);
        String sub_mchid = "1604771870";


        JSONObject body = httpV3("POST", ConstantURL.modifysettlement.replace("sub_mchid",sub_mchid), requestParam);

        System.out.print(body);

        return body;

    }

    /**
     *  查询结算账户API
     *
     *
     * @return
     */
    public static AccountInfo searchSettlement(String sub_mchid){

        JSONObject body = httpV3("GET", ConstantURL.searchsettlement
                .replace("sub_mchid",sub_mchid), "");

        System.out.print(body);

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccount_bank(body.getString("account_bank"));
        accountInfo.setAccount_number(body.getString("account_number"));
        return accountInfo;

    }





    /**
     *
     * 请求分账
     * @return
     */
    public static  JSONObject  SubOrders(WxPayDto wxPayDto){

        ProfitSharing   profitsharing = new ProfitSharing();
        profitsharing.setAppid("wxf17107b0e9021507");
        profitsharing.setSub_mchid(wxPayDto.getSub_mchid());
        profitsharing.setTransaction_id(wxPayDto.getTransaction_id());
        profitsharing.setOut_order_no(wxPayDto.getOut_trade_no());
        profitsharing.setFinish(true);
//        List<Receivers> receiverslist =  new ArrayList<Receivers>();
//        Receivers receivers = new Receivers();
//        receivers.setType("PERSONAL_OPENID");
//        receivers.setReceiver_account("oGNIJwVBMDPOKWrzsKAVfmg2rCwg");
//        receivers.setAmount(3);
//        receivers.setDescription("分账给个人");
//        receiverslist.add(receivers);
//        profitsharing.setReceivers(receiverslist);



        String requestParam = JSON.toJSONString(profitsharing);


        JSONObject body = httpV3("POST", ConstantURL.profitsharing, requestParam);

        System.out.print(body);


        return body;
    }

    /**
     *  添加分账接收方API
     *
     *
     * @return
     */
    public static JSONObject receiversAdd(){
        ReceiversAdd  receiversAdd = new ReceiversAdd();
        receiversAdd.setAppid("wxf17107b0e9021507");
        receiversAdd.setType("PERSONAL_OPENID");
        receiversAdd.setAccount("oGNIJwVBMDPOKWrzsKAVfmg2rCwg");
        receiversAdd.setRelation_type("OTHERS");

        String requestParam = JSON.toJSONString(receiversAdd);


        JSONObject body = httpV3("POST", ConstantURL.receivers, requestParam);

        System.out.print(body);

        return body;

    }

    /**
     *
     *
     * 查询分账结果
     * @return
     */
    public static JSONObject profitsharingResult(WxPayDto wxPayDto){



        String sub_mchid = wxPayDto.getSub_mchid();
        String transaction_id = wxPayDto.getTransaction_id();
        String out_order_no = wxPayDto.getOut_order_no();

           System.out.println(sub_mchid);
        System.out.println(transaction_id);
        System.out.println(out_order_no);
        JSONObject body = httpV3("GET", ConstantURL.profitsharing+"?sub_mchid="+sub_mchid+"&transaction_id="+transaction_id+"&out_order_no="+out_order_no, "");

        System.out.print(body);

        return body;
    }

    /**
     *
     *
     * 查询分账结果
     * @return
     */
    public static JSONObject profitsharingResult2(){



        String sub_mchid = "1633069352";
        String transaction_id = "4309500954202210181945204233";
        String out_order_no = "fz2022101804175700014";



        JSONObject body = httpV3("GET", ConstantURL.profitsharing+"?sub_mchid="+sub_mchid+"&transaction_id="+transaction_id+"&out_order_no="+out_order_no, "");

        System.out.print(body);

        return body;
    }

    /**
     *完结分账API
     *不需要进行分账的订单，可直接调用本接口将订单的金额全部解冻给二级商户。
     * @param wxPayDto
     * @return
     */
    public  static String  finishOrder(WxPayDto wxPayDto){
        String order_id = "";
      try {
          String requestParam = JSON.toJSONString(wxPayDto);
          JSONObject body = httpV3("POST", ConstantURL.finishOrder, requestParam);
           order_id = body.getString("order_id");
      }catch (Exception e){
          e.printStackTrace();
      }

        return order_id;

    }







    /**
     *  二级商户余额提现API
     *
     *
     * @return
     */
    public static Withdraw withdraw(String sub_mchid,int mount,String out_request_no){
        Withdraw  withdraw = new Withdraw();

        withdraw.setSub_mchid(sub_mchid);
        withdraw.setAmount(mount);
        withdraw.setOut_request_no("tx"+out_request_no);
        withdraw.setRemark("供应商成本");
        withdraw.setBank_memo("供应商成本");
        String requestParam = JSON.toJSONString(withdraw);


        JSONObject body = httpV3("POST", ConstantURL.withdraw, requestParam);

        System.out.print(body);
        withdraw.setWithdraw_id(body.getString("withdraw_id"));

        return withdraw;

    }

    /**
     *
     *
     * 查询提现结果
     * @param withdraw
     * @return
     */
    public static Withdraw searchWithdraw(Withdraw withdraw){
        System.out.println(ConstantURL.searchWithdraw
                .replace("out_request_no",withdraw.getOut_request_no())+"?sub_mchid="+withdraw.getSub_mchid());

        JSONObject body = httpV3("GET", ConstantURL.searchWithdraw
                .replace("out_request_no",withdraw.getOut_request_no())+"?sub_mchid="+withdraw.getSub_mchid(), "");
        System.out.print(body);
        if(withdraw.getWdkey()==null||withdraw.getWdkey().equals("")){
            withdraw.setAccount_number(body.getString("account_number"));
            withdraw.setAccount_bank(body.getString("account_bank"));
            withdraw.setCreate_time(body.getString("create_time"));

        }
        withdraw.setUpdate_time(body.getString("update_time"));
        withdraw.setReason(body.getString("reason"));
        withdraw.setStatus(body.getString("status"));

        return withdraw;

    }



    /**
     *  退款申请API
     *
     *
     * @return
     */
    public static  String refunds(List<WxPayDto> tpWxPayDtoList){
        String refund_id = "";
        for (int i = 0;i<tpWxPayDtoList.size();i++) {
            Refunds  refunds = new Refunds();
            WxPayDto tpWxPayDto = tpWxPayDtoList.get(i);
            refunds.setSub_mchid(tpWxPayDto.getSub_mchid());
            refunds.setSp_appid(tpWxPayDto.getSp_appid()); //电商平台在微信公众平台申请服务号对应的APPID，申请商户功能的时候微信支付会配置绑定关系。

            refunds.setOut_trade_no(tpWxPayDto.getOrderId());
            refunds.setOut_refund_no(tpWxPayDto.getRefundno());


            RefundsAmount refundsAmount = new RefundsAmount();
            refundsAmount.setRefund(Integer.parseInt(tpWxPayDto.getRefundfee()));
            refundsAmount.setTotal(Integer.parseInt(tpWxPayDto.getTotalFee()));
            refundsAmount.setCurrency("CNY");
            refunds.setAmount(refundsAmount);
            String requestParam = JSON.toJSONString(refunds);


            JSONObject body = httpV3("POST", ConstantURL.refunds, requestParam);
            refund_id = body.getString("refund_id");
        }




        return refund_id;

    }
    public static WxPayResult noticeResult(String notity, HttpServletRequest request , Map<String,String>  submap){
        WxPayResult wxPayResult = new WxPayResult();
        try {
            String Wtimestamp = request.getHeader("Wechatpay-Timestamp");
            String Wnonce = request.getHeader("Wechatpay-Nonce");
            String Wsign = request.getHeader("Wechatpay-Signature");
            //      String WSERIAL = request.getHeader("WECHATPAY-SERIAL");

            System.out.println("Wtimestamp"+Wtimestamp);
            System.out.println("Wnonce"+Wnonce);
            System.out.println("Wsign"+Wsign);
            //      System.out.println("WSERIAL"+WSERIAL);

            //拼装待签名串
            StringBuffer ss = new StringBuffer();
            ss.append(Wtimestamp).append("\n");
            ss.append(Wnonce).append("\n");
            ss.append(notity).append("\n");
            //验证签名
            if (verifyRSA(ss.toString(), Base64.decodeBase64(Wsign.getBytes()), WeChatUtil.publicKeyPath)) {
                System.out.println("签名验证成功");
                JSONObject  jsonObject = JSONObject.fromObject(notity);
                System.out.println(jsonObject.toString());

                String event_type = jsonObject.getString("event_type");
                wxPayResult.setResultCode(event_type);


//
//               String journalNum = wxPayResult.getOutTradeNo();//商户订单
//               String trade_no = wxPayResult.getTransactionId().toString();//第三方
//               morre = wxPayResult.getTotalFee().toString();
//

//               String attach = wxPayResult.getAttach();

                String resource = jsonObject.getString("resource");

                JSONObject  re = JSONObject.fromObject(resource);

                String algorithm = re.getString("algorithm");
                String ciphertext = re.getString("ciphertext");
                String associated_data = re.getString("associated_data");
                String nonce = re.getString("nonce");
                String original_type = re.getString("original_type");

                System.out.println("algorithm________"+algorithm);
                System.out.println("ciphertext————————————"+ciphertext);
                System.out.println("associated_data__"+associated_data);
                System.out.println("nonce__"+nonce);
                System.out.println("original_type__"+original_type);

                System.out.println("resource________"+resource);





                AesUtil aesUtil = new AesUtil(v3key.getBytes(StandardCharsets.UTF_8));

                String plainCert = aesUtil.decryptToString(associated_data.getBytes(StandardCharsets.UTF_8),
                        nonce.getBytes(StandardCharsets.UTF_8),
                        ciphertext);

                System.out.println("plainCert___________"+plainCert);

                JSONObject plainjo = JSONObject.fromObject(plainCert);




                String combine_appid = plainjo.getString("combine_appid");
                String combine_out_trade_no = plainjo.getString("combine_out_trade_no");

                wxPayResult.setOutTradeNo(combine_out_trade_no);




                System.out.println("combine_appid________"+combine_appid);
                System.out.println("combine_out_trade_no————————————"+combine_out_trade_no);




                JSONArray joArray = plainjo.getJSONArray("sub_orders");

                int zmoney  = 0;

                for (int i = 0;i<joArray.size();i++){
                    System.out.println("size=--"+joArray.size());
                    JSONObject jo =  JSONObject.fromObject(joArray.get(i));
                    String  mchid = jo.getString("mchid");
                    String  trade_type = jo.getString("trade_type");
                    wxPayResult.setTradeType(trade_type);
                    String  trade_state = jo.getString("trade_state");
                    String  transaction_id = jo.getString("transaction_id");
                    String  out_trade_no = jo.getString("out_trade_no");
                    String  sub_mchid = jo.getString("sub_mchid");
                    String attach = jo.getString("attach");

                    if(out_trade_no!=null&&out_trade_no.equals(combine_out_trade_no)) {
                        wxPayResult.setTransactionId(transaction_id);
                    }
                    wxPayResult.setAttach(attach);
                    submap.put(out_trade_no,transaction_id);

                    System.out.println("mchid________"+mchid);
                    System.out.println("trade_type————————————"+trade_type);
                    System.out.println("trade_state————————————"+trade_state);
                    System.out.println("transaction_id————————————"+transaction_id);
                    System.out.println("out_trade_no————————————"+out_trade_no);
                    System.out.println("sub_mchid————————————"+sub_mchid);
                    System.out.println("attach————————————"+attach);
                    JSONObject  amount = jo.getJSONObject("amount");
                    int total_amount = amount.getInt("total_amount");
                    int payer_amount = amount.getInt("payer_amount");
                    zmoney+=total_amount;
                    System.out.println("total_amount————————————"+total_amount);
                    System.out.println("payer_amount————————————"+payer_amount);


                }

                wxPayResult.setTotalFee(zmoney+"");
                wxPayResult.setSublist(submap);
            } else {
                System.out.println("签名验证失败");
                JSONObject  jsonObject = JSONObject.fromObject(notity);
                System.out.println(jsonObject.toString());
                String resource = jsonObject.getString("resource");
                System.out.println(resource);

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return wxPayResult;
    }




    public static WxPayResult noticeServerResult(String notity, HttpServletRequest request){
        WxPayResult wxPayResult = new WxPayResult();
        try {
            String Wtimestamp = request.getHeader("Wechatpay-Timestamp");
            String Wnonce = request.getHeader("Wechatpay-Nonce");
            String Wsign = request.getHeader("Wechatpay-Signature");


            System.out.println("Wtimestamp"+Wtimestamp);
            System.out.println("Wnonce"+Wnonce);
            System.out.println("Wsign"+Wsign);


            //拼装待签名串
            StringBuffer ss = new StringBuffer();
            ss.append(Wtimestamp).append("\n");
            ss.append(Wnonce).append("\n");
            ss.append(notity).append("\n");
            //验证签名
            if (verifyRSA(ss.toString(), Base64.decodeBase64(Wsign.getBytes()), WeChatUtil.publicsKeyPath)) {
                System.out.println("签名验证成功");
                JSONObject  jsonObject = JSONObject.fromObject(notity);
                System.out.println(jsonObject.toString());

                String event_type = jsonObject.getString("event_type");
                wxPayResult.setResultCode(event_type);

                String resource = jsonObject.getString("resource");

                JSONObject  re = JSONObject.fromObject(resource);


                String ciphertext = re.getString("ciphertext");
                String associated_data = re.getString("associated_data");
                String nonce = re.getString("nonce");



                AesUtil aesUtil = new AesUtil(v315key.getBytes(StandardCharsets.UTF_8));

                String plainCert = aesUtil.decryptToString(associated_data.getBytes(StandardCharsets.UTF_8),
                        nonce.getBytes(StandardCharsets.UTF_8),
                        ciphertext);

                System.out.println("plainCert___________"+plainCert);

                JSONObject plainjo = JSONObject.fromObject(plainCert);

                String transaction_id = plainjo.getString("transaction_id");
                String out_trade_no = plainjo.getString("out_trade_no");
                String attach = plainjo.getString("attach");
                String trade_type = plainjo.getString("trade_type");
                String trade_state = plainjo.getString("trade_state");
                wxPayResult.setOutTradeNo(out_trade_no);

                wxPayResult.setTransactionId(transaction_id);
                wxPayResult.setAttach(attach);
                wxPayResult.setTradeType(trade_type);
                wxPayResult.setResultCode(trade_state);
                System.out.println("out_trade_no————————————"+out_trade_no);
                JSONObject  amount = plainjo.getJSONObject("amount");


                int total = amount.getInt("total");
                wxPayResult.setTotalFee(total+"");

            } else {
                System.out.println("签名验证失败");
                JSONObject  jsonObject = JSONObject.fromObject(notity);
                System.out.println(jsonObject.toString());
                String resource = jsonObject.getString("resource");
                System.out.println(resource);

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return wxPayResult;
    }

    /**
     * 合单查询订单
     * @param combine_out_trade_no
     * @return
     */
    public static  WxPayDto searchOrder(String combine_out_trade_no) {
        WxPayDto payDto = new WxPayDto();
        JSONObject body = httpV3("GET", ConstantURL.searchOrder + combine_out_trade_no, "");


        JSONArray jsonArray = body.getJSONArray("sub_orders");


        payDto.setOut_trade_no(combine_out_trade_no);
        int totalm = 0;
        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject jo = JSONObject.fromObject(jsonArray.get(i));

            String trade_state = jo.getString("trade_state");
            String success_time = jo.getString("success_time");

            payDto.setTrade_state(trade_state);
            payDto.setTime_end(success_time);

            JSONObject amount = jo.getJSONObject("amount");
            int total_amount = amount.getInt("total_amount");
            totalm+=total_amount;

        }
        payDto.setTotalFee(totalm+"");

        return payDto;
    }

    /**
     *
     * 服务商查询订单V3
     * @return
     */
    public static WxPayDto serverQuery(String 	out_trade_no){
        WxPayDto payDto = new WxPayDto();
        JSONObject body = httpV315("GET", ConstantURL.Server_Query +out_trade_no+"?sp_mchid="+Constant.WXSP_Mchid+"&sub_mchid="+Constant.WXSP_SUBMchid, "");
        String trade_state = body.getString("trade_state");
        String success_time = body.getString("success_time");
        JSONObject amount = body.getJSONObject("amount");
        int total = amount.getInt("total");
        payDto.setTrade_state(trade_state);
        payDto.setTime_end(success_time);
        payDto.setTotalFee(total+"");
        return payDto;


    }


}