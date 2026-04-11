package com.wechat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 *开发者应当使用微信支付平台证书中的公钥，对上送的敏感信息进行加密。
 * 敏感信息加解密
 */
public class RsaCryptoUtil {
	private static final Logger logger = LoggerFactory.getLogger(RsaCryptoUtil.class);

  public static String rsaEncryptOAEP(String message, X509Certificate certificate)
          throws IllegalBlockSizeException, IOException {
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
   * 微信支付使用商户证书中的公钥对下行的敏感信息进行加密。开发者应使用商户私钥对下行的敏感信息的密文进行解密。
   * @param ciphertext
   * @param privateKey
   * @return
   * @throws BadPaddingException
   */
  private static String rsaDecryptOAEP(String ciphertext, PrivateKey privateKey)
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
      logger.error("操作异常", e);
      throw new BadPaddingException("解密失败");
    }
  }


  /**
   *
   * 解密
   * @param ciphertext
   * @return
   */
  public static String decryptOAEP(String ciphertext){
    String  mtext = "";
    try {


      PrivateKey privateKey = WeChatUtil.getPrivateKey(WeChatUtil.privageKeyPath);

      mtext = rsaDecryptOAEP(ciphertext, privateKey);


      logger.info("值：{}", mtext);

    } catch (Exception e) {
      logger.error("操作异常", e);
    }

    return mtext;
  }

  /**
   *
   *加密
   * @param mtext
   * @return
   */
  public static String  ncrypt(String mtext){
    String ciphertext = "";
    try {
      CertificateFactory certificate_factory = CertificateFactory.getInstance("X.509");
      FileInputStream file_inputstream=new FileInputStream(WeChatUtil.publicKeyPath);
      X509Certificate
              certificate=(X509Certificate)certificate_factory.generateCertificate
              (file_inputstream);
       ciphertext =  rsaEncryptOAEP(mtext, certificate);

      logger.info("值：{}", ciphertext);


    } catch (Exception e) {
      logger.error("操作异常", e);
    }
    return ciphertext;

  }



  public static void main(String[] args) {

    try {
      CertificateFactory certificate_factory = CertificateFactory.getInstance("X.509");
      FileInputStream file_inputstream=new FileInputStream(WeChatUtil.publicKeyPath);
      X509Certificate
              certificate=(X509Certificate)certificate_factory.generateCertificate
              (file_inputstream);
      String ciphertext =  rsaEncryptOAEP("210522198903092024", certificate);

      logger.info("值：{}", ciphertext);

      PrivateKey privateKey = WeChatUtil.getPrivateKey(WeChatUtil.privageKeyPath);

      String  mtext = rsaDecryptOAEP(ciphertext, privateKey);


      logger.info("值：{}", mtext);

    } catch (Exception e) {
      logger.error("操作异常", e);
    }
  }
}
