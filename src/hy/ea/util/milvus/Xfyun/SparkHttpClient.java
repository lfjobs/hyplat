package hy.ea.util.milvus.Xfyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

public class SparkHttpClient {
	private static final Logger logger = LoggerFactory.getLogger(SparkHttpClient.class);

    /**
     * 向讯飞 Spark Chat 接口发送请求
     *
     * @param requestUrl 已带鉴权参数的 URL（通过 AuthUrlGenerator 生成）
     * @param appId      控制台中的 appid
     * @param text       要发送的文本内容
     */
    public static String sendSparkRequest(String requestUrl, String appId, String text) {
        HttpURLConnection connection = null;
        try {
            // 1️⃣ 创建连接
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("content-type", "application/json");

            String base64Text = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));

// 构造 payload.messages 对象
            JSONObject messages = new JSONObject();
            messages.put("text", base64Text);

// 构造 par
            JSONObject parameter = new JSONObject();
                JSONObject emb = new JSONObject();
                    emb.put("domain","query");
                    JSONObject feature=new JSONObject();
                    feature.put("encoding","utf8");
                    emb.put("feature",feature);


            parameter.put("emb",emb);
// 构造 header
            JSONObject header = new JSONObject();
            header.put("app_id", appId);
            header.put("status", 3);

// 最终 body
            JSONObject body = new JSONObject();
            body.put("header", header);
            body.put("parameter", parameter);
            body.put("payload", new JSONObject().put("messages",messages));
            logger.info("调试信息");
            // 3️⃣ 写出请求体
            try (OutputStream os = connection.getOutputStream()) {
                os.write(body.toString().getBytes(StandardCharsets.UTF_8));
            }

            // 4️⃣ 读取响应
            int responseCode = connection.getResponseCode();
            InputStream is = (responseCode == HttpURLConnection.HTTP_OK)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();

        } catch (Exception e) {
            logger.error("操作异常", e);
            return "{\"error\": \"" + e.getMessage() + "\"}";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public static float[] parserMessage(String result){
        JSONObject jsonObject=new JSONObject(result);
        Integer code = jsonObject.getJSONObject("header").getInt("code");
        if (code.equals(0)){
            //text_base = data["payload"]["feature"]["text"]
            String string = jsonObject.getJSONObject("payload").getJSONObject("feature").getString("text");


            // 1. Base64 解码
            byte[] bytes = Base64.getDecoder().decode(string);

            // 2. 创建 ByteBuffer 并设置为小端字节序
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            // 3. 按 float32 读取
            int floatCount = bytes.length / 4; // float32 占 4 字节
            float[] vector = new float[floatCount];
            for (int i = 0; i < floatCount; i++) {
                vector[i] = buffer.getFloat();
            }

            // 打印向量长度和内容

            return vector;
        }
        return null;
    }
    // ✅ 示例调用
    public static void main(String[] args) throws Exception {
        String appId = "1690d84f";
        String apiKey = "dc7ea49d5abe67227218ce8a44c04063";
        String apiSecret = "NDE1MjQzZWMzYTk2ODExMjRlODQ5N2Yw";
        String host = "emb-cn-huabei-1.xf-yun.com";
        String path = "/"; // embedding接口是根路径！

        // 1️⃣ 生成带鉴权参数的 URL
        String requestUrl = AuthUrlGenerator.generateAuthUrl(host, path, apiKey, apiSecret);
        JSONObject jsonObject=new JSONObject();

        JSONArray jsonArray=new JSONArray();
        JSONObject obj=new JSONObject();
        obj.put("content","你是不是人？");
        obj.put("role","user");
        jsonArray.put(obj);

        jsonObject.put("messages",jsonArray);
        // 2️⃣ 发送请求
        String result = sendSparkRequest(requestUrl, appId, jsonObject.toString());
//
        logger.info("✅ 响应结果：");
        logger.info("值：{}", result);
        float[] floats = parserMessage(result);
        logger.info("调试信息");
    }
}
