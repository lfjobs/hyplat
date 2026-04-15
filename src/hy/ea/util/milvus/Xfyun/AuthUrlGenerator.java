package hy.ea.util.milvus.Xfyun;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Base64;

public class AuthUrlGenerator {

    public static String generateAuthUrl(String host, String path, String apiKey, String apiSecret) throws Exception {
        // 1️⃣ 获取 RFC1123 格式的 UTC 时间
        String date = getGMTDate();

        // 2️⃣ 拼接 tmp（注意：严格使用 \n，不是 \r\n，也不能多空格）
        String tmp = "host: " + host + "\n" +
                "date: " + date + "\n" +
                "POST " + path + " HTTP/1.1";

        // 3️⃣ 使用 HMAC-SHA256 生成签名
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(tmp.getBytes(StandardCharsets.UTF_8));

        // 4️⃣ Base64 编码签名
        String signature = Base64.getEncoder().encodeToString(hash);

        // 5️⃣ 生成 authorization_origin
        String authorizationOrigin = String.format(
                "api_key=\"%s\", algorithm=\"hmac-sha256\", headers=\"host date request-line\", signature=\"%s\"",
                apiKey, signature
        );

        // 6️⃣ Base64 编码 authorization_origin
        String authorization = Base64.getEncoder().encodeToString(authorizationOrigin.getBytes(StandardCharsets.UTF_8));

        // 7️⃣ 拼接最终 URL
        String url = String.format(
                "https://%s%s?authorization=%s&date=%s&host=%s",
                host,
                path,
                URLEncoder.encode(authorization, StandardCharsets.UTF_8.toString()),
                URLEncoder.encode(date, StandardCharsets.UTF_8.toString()),
                URLEncoder.encode(host, StandardCharsets.UTF_8.toString())
        );

        // ✅ 打印调试信息（方便对照 Python 版本）
        System.out.println("----- 调试信息 -----");
        System.out.println("tmp = \n" + tmp);
        System.out.println("signature = " + signature);
        System.out.println("authorization_origin = " + authorizationOrigin);
        System.out.println("final URL = " + url);
        System.out.println("-------------------");

        return url;
    }

    private static String getGMTDate() {
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(new Date());
    }


}
