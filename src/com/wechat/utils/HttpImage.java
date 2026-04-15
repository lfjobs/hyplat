package com.wechat.utils;

import com.wechatpay.utils.WeChatUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 *
 *
 * 媒体图片只支持JPG、BMP、PNG格式，文件大小不能超过2M。
 */
public class HttpImage {
    /**
     * 正确的做法   HttpClient
     *
     * @param args
     */
    public static void main(String[] args) {
        try {

            //商户号
            String mchid = "1604065060";
            //证书序列号
            String serial_no = "401D98B320A9CEB1961D503FFBAE061A3545C8A4";
            //商户私钥（拷贝apiclient_key.pem文件里-----BEGIN PRIVATE KEY-----和-----END PRIVATE KEY-----之间的内容）
            String rsaPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCr8V/5ZgDwGqQr" +
                    "DO+ekjm/ayW6TH7xM3cosUoq6Ffh0No5VU8objNGCK2ZWA+/EzrNaesKKs6+CZb+" +
                    "dp2I+NN0A+jBIWY8H9RemypEVmAVZsoGDLbDHO0TELjSf4N9pQv83xwydMQNkEvK" +
                    "+9qKGVJGtxyAonqn4YLEOhAyTOrAXTOwD57GdQ2O3YHP05oiLpveTsTuku5uIn4l" +
                    "3P/PqWQ/8QzkE9eEN4SvTxzL/1VVJYCA7AXbKqCo0JUSLVVadlPcJ/FDgHe8R2ar" +
                    "txHIG1u4uJ581u1cg0nA5GUdQLvX6JqBDQ0ULybggAFojjFmuiClDyqWb0aI17Mz" +
                    "pPEPw8F7AgMBAAECggEASF87t1zw7h84lIpIXaVMuz5dTfIeyGWSNUWSY0cSnpDr" +
                    "GZDH1cu0tzi14UKItjNg389CSDBGfwTIUmkY2+toDYALuybUwl/fYsbK3qLApKF0" +
                    "TWGSl8OYIhs6wBc+AGe1CQXb93zgfJkM3e5uSQxpbixqydYZ7InmIbQS1yk68iZY" +
                    "Gfu7xqPrKklt54g6be79QxdezTT1IrfWVv+rBdmQkLSrWhHwEpPob2lBjk0oMpCG" +
                    "7IKbgWiq/kyKLgTy1FE/ve1TSudhw3jPZ+24Kh5TFppm0JzMaVlafXdbSNliKcAr" +
                    "ftLHM2uABZe1weGdiWZO7n9N5zo4+T76svKObfb1YQKBgQDR4vMw4FdQQJDIhe5Q" +
                    "kq2jWhMZXQPDlF3HIcGIQpnC445Sb+/GC3w+zygrNzAhUjh331dvdW6VF9DcQQVp" +
                    "1OQkcy+/GRQhVM3GQUJaZ+kTbN66LU0VmlVdN61Ft1YbQvBkXktXzttXN8JyDpUh" +
                    "xMlecmJJdMBPqKZTaw2Vifin8QKBgQDRuEmP6HnqDdd97nkf49l55zinsXxvtHrh" +
                    "JC4oVCONLsnxyS8Qb8twdd3S26PeSnibQuDTbNncAS9Osx0z2cydNHN5jsjWogd1" +
                    "z9o72uW8VtNHwmfW1EAiNM1bLjpWKT1SA6CtzFnNXbObrfwlxoZ3jrT33Rc/FQ3z" +
                    "i/9B52RMKwKBgQCVzZ417GllZi/wb2dBKu+z/75SBAmDtomYb4dqcd69Mx2JyUsh" +
                    "12C+k6zf62v23HBhXo9Rhkd/MphoHCB5API08eHKS2QKxjR4pGtvi2rX6oSt7Vdv" +
                    "a9Ax0GNjtw9GNejOOl8cNRBol+u47J+UkpgHSSzNsP7x3DWapHX1nZxUgQKBgCe+" +
                    "iRJguFoG7YPMIeBNSfW0QCRnmnGA/piURrsV1vb2mwbbXbP7u85ASB2hylhiPKEP" +
                    "95OZ2V+8Sb7cavhcmk5gllG/IYirhEZrcaxOO6OJwNikmxE3gD/7Sb+x7xRNFHXq" +
                    "ghTt2Ub2Lwed3V87VFhsDxe6KqGl19MEAQf4ViWpAoGAcN8DUaa1Ff7N9IYWsdmA" +
                    "vkEfLZVP2J3FwSbPZfKtUFivmXxpE9YpVB451pg9ZYstxn7GOcuJxk4JqLFqv3eP" +
                    "vbuTQhT1XCdi9cUqBIL9/PamNLx5N4/0cOwljoU2c2sX8AGEhy/+P53EpgAK5tRh" +
                    "FxC0acZv4RWarbo32h6hT74=";
            //微信支付平台公钥
            String rsaPublicKeyFile = "E:\\wechatpay_591CA4D5D0A817528B5591DF4EF26EA9128AAA1C.pem";
            //时间戳
            String timestamp = Long.toString(System.currentTimeMillis() / 1000);
            //随机数
            String nonce_str = WeChatUtils.getNonceStr();

            //图片文件
            String filePath = "E:\\1.png";//文件路径
            File file = new File(filePath);
            String filename = file.getName();//文件名
            String fileSha256 = DigestUtils.sha256Hex(new FileInputStream(file));//文件sha256

            //拼签名串
            StringBuffer sb = new StringBuffer();
            sb.append("POST").append("\n");
            sb.append("/v3/merchant/media/upload").append("\n");
            sb.append(timestamp).append("\n");
            sb.append(nonce_str).append("\n");
            sb.append("{\"filename\":\"" + filename + "\",\"sha256\":\"" + fileSha256 + "\"}").append("\n");
            System.out.println("签名原串:" + sb.toString());

            //计算签名
            String sign = new String(Base64.encodeBase64(signRSA(sb.toString(), rsaPrivateKey)));
            System.out.println("签名sign值:" + sign);


            //拼装http头的Authorization内容
            String authorization = "WECHATPAY2-SHA256-RSA2048 mchid=\"" + mchid + "\",nonce_str=\"" + nonce_str + "\",signature=\"" + sign + "\",timestamp=\"" + timestamp + "\",serial_no=\"" + serial_no + "\"";
            System.out.println("authorization值:" + authorization);
            //接口URL
            String url = "https://api.mch.weixin.qq.com/v3/merchant/media/upload";
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

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
            String rescontent = new String(InputStreamTOByte(httpEntity.getContent()));
            System.out.println("返回内容:" + rescontent);
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
            System.out.println("发送POST请求异常！" + e);
            e.printStackTrace();
        }

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


    public static byte[] signRSA(String data, String priKey) throws Exception {


//签名的类型

        Signature sign = Signature.getInstance("SHA256withRSA");

        //读取商户私钥,该方法传入商户私钥证书的内容即可

        byte[] keyBytes = Base64.decodeBase64(priKey);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        sign.initSign(privateKey);

        sign.update(data.getBytes("UTF-8"));

        return sign.sign();

    }


    public static boolean verifyRSA(String data, byte[] sign, String pubKey) throws Exception {

        if (data == null || sign == null || pubKey == null) {

            return false;

        }


        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        FileInputStream in = new FileInputStream(pubKey);

        Certificate c = cf.generateCertificate(in);

        in.close();

        PublicKey publicKey = c.getPublicKey();

        Signature signature = Signature.getInstance("SHA256WithRSA");

        signature.initVerify(publicKey);

        signature.update(data.getBytes("UTF-8"));

        return signature.verify(sign);


    }
}