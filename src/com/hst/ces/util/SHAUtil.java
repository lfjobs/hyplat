package com.hst.ces.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256算法工具
 */
public class SHAUtil {
	private static final Logger logger = LoggerFactory.getLogger(SHAUtil.class);


    /**
     * SHA256
     */
    public static String SHA256(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException ex) {
            logger.error("操作异常", ex);
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes bytes
     * @return sb
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                sb.append("0");
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    /**
     * HMACSHA256 加密算法
     * @param data 加密字符串
     * @param key 秘钥
     * @return 加密结果字符串
     */
    public static String HMACSHA256(String data, String key){

        StringBuilder sb = new StringBuilder();

        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");

            sha256_HMAC.init(secret_key);

            byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));

            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
        } catch (Exception ex) {
            logger.error("操作异常", ex);
        }
        return sb.toString();
    }
}
