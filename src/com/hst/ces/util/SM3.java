package com.hst.ces.util;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Arrays;

/**
 * @author SM3算法工具
 */
public class SM3 {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }


    /**
     * SM3加密
     *
     * @param paramStr 待加密字符串
     * @return 返回加密后，固定长度=64的16进制字符串
     */
    public static String encrypt(String paramStr) {
        //将字符串转换成byte数组
        byte[] srcData = paramStr.getBytes(StandardCharsets.UTF_8);
        byte[] resultHash = hash(srcData);
        //将返回的hash值转换成16进制字符串
        return ByteUtils.toHexString(resultHash);
    }

    /**
     * 返回长度为32的byte数组
     * 生成对应的hash值
     */
    public static byte[] hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 通过指定密钥进行加密
     *
     * @param key     密钥
     * @param srcData 被加密的byte数组
     */
    public static byte[] hmac(byte[] key, byte[] srcData) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }

    /**
     * 判断数据源与加密数据是否一致，通过验证原数组和生成是hash数组是否为同一数组，验证二者是否为同一数据
     */
    public static boolean verify(String srcStr, String sm3HexString) {
        byte[] srcData = srcStr.getBytes(StandardCharsets.UTF_8);
        byte[] sm3Hash = ByteUtils.fromHexString(sm3HexString);
        byte[] newHash = hash(srcData);
        return Arrays.equals(newHash, sm3Hash);
    }
}
