package com.wechat.utils;

import org.apache.http.impl.client.CloseableHttpClient;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class SFTUtil {
    private static String mchId = ""; // 商户号
    private static String mchSerialNo = ""; // 商户证书序列号
    private static String apiV3Key = ""; // api密钥
    // 你的商户私钥
    private static String privateKey = "-----BEGIN PRIVATE KEY-----\n"
            + "-----END PRIVATE KEY-----\n";

    //测试AutoUpdateCertificatesVerifier的verify方法参数
    private static String serialNumber = "";
    private static String message = "";
    private static String signature = "";
    private static CloseableHttpClient httpClient;
    private static String priKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCr8V/5ZgDwGqQr" +
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




    /**
     *
     *
     * 加密
     * @param message
     * @param certificate
     * @return
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public static String rsaEncryptOAEP(String message, X509Certificate certificate) throws IllegalBlockSizeException, IOException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, certificate.getPublicKey());

            byte[] data = message.getBytes("utf-8");
            byte[] cipherdata = cipher.doFinal(data);
            return Base64.getEncoder().encodeToString(cipherdata);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("当前Java环境不支持RSA v1.5/OAEP", e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("无效的证书", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalBlockSizeException("加密原串的长度不能超过214字节");
        }
    }

    /**
     *
     * 解密
     * @param ciphertext
     * @param privateKey
     * @return
     * @throws BadPaddingException
     * @throws IOException
     */
 public static String rsaDecryptOAEP(String ciphertext, PrivateKey privateKey)
        throws BadPaddingException, IOException {
    try {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] data = Base64.getDecoder().decode(ciphertext);
        return new String(cipher.doFinal(data), "utf-8");
    } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
        throw new RuntimeException("当前Java环境不支持RSA v1.5/OAEP", e);
    } catch (InvalidKeyException e) {
        throw new IllegalArgumentException("无效的私钥", e);
    } catch (BadPaddingException | IllegalBlockSizeException e) {
        throw new BadPaddingException("解密失败");
    }
}





    public static void main(String[] args) {

        try {
            CertificateFactory certificate_factory=CertificateFactory.getInstance("X.509");
            FileInputStream file_inputstream=new FileInputStream("E:\\wechatpay_591CA4D5D0A817528B5591DF4EF26EA9128AAA1C.pem");
            X509Certificate
                    certificate=(X509Certificate)certificate_factory.generateCertificate
                    (file_inputstream);
            String ciphertext =   rsaEncryptOAEP("210522198903092024", certificate);

            System.out.println(ciphertext);

            byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(priKey);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

           String  mtext = rsaDecryptOAEP(ciphertext, privateKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
