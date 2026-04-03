package com.wattOpen;

/*import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;*/
import com.alibaba.fastjson.JSONObject;

import java.util.Optional;

/**
 * @author author wangdaigang
 * @create 2024 - 06 - 01 9:57
 */
public class WattConfig {
    /**
     * 测试环境clientId
     */
    private static final String testClientId = "2021030910401811162";
    /**
     * 测试环境clientSecret
     */
    private static final String testSecret = "f78ff7";
    /**
     * 测试环境
     */
    static final String TEST_API_URL = "https://test-openapi.elecwatt.com:8043";
    /**
     * 生产环境
     */
    private final String PROD_API_URL = "https://openapi.elecwatt.com";


    /**
     * 获取token
     */
    private static final String getTokenUrl = "/oauth/token";

    /**
     * token post
     *
     * @param authStr
     * @return
     */
    public static String handleTokenPost(String authStr) {
        try {
            /*HttpRequest request = HttpRequest.post(TEST_API_URL + getTokenUrl);
            request.body("{}", "application/json");
            request.header("Authorization", "Basic " + authStr);
            request.header("Content-Type", "application/json");

            HttpResponse response = request.execute();

            return Optional.ofNullable(response.body())
                    .orElseThrow(() -> new RuntimeException("响应体为空"));*/
            return "";
        } catch (Exception e) {
            throw new RuntimeException("请求第三方接口失败", e);
        }
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {

        /*String authStr = Base64.encode(testClientId + ":" + testSecret);
        String r = handleTokenPost(authStr);
        JSONObject rJson = JSONObject.parseObject(r);

        JSONObject dataJson = rJson.getJSONObject("data");

        String token = dataJson.getString("access_token");

        // TODO: 2024/6/1 redis 缓存token

        return token;*/
        return "";
    }


    /**
     * post 请求
     *
     * @param bodyStr
     * @param serverUrl 服务地址
     * @return
     */
    public static String handlePost(String bodyStr, String serverUrl) {
        try {
            /*HttpRequest request = HttpRequest.post(TEST_API_URL + serverUrl);
            request.body(bodyStr, "application/json");
            request.header("Authorization", "Bearer " + getToken());
            request.header("Content-Type", "application/json");

            HttpResponse response = request.execute();

            return Optional.ofNullable(response.body())
                    .orElseThrow(() -> new RuntimeException("响应体为空"));*/
            return "";
        } catch (Exception e) {
            throw new RuntimeException("请求第三方接口失败", e);
        }
    }

    /**
     * get 请求
     *
     * @param
     * @param serverUrl
     * @return
     */
    public static String handleGet(String serverUrl) {
        try {
            /*HttpRequest request = HttpRequest.get(TEST_API_URL + serverUrl);
            request.header("Authorization", "Bearer " + getToken());

            HttpResponse response = request.execute();

            return Optional.ofNullable(response.body())
                    .orElseThrow(() -> new RuntimeException("响应体为空"));*/
            return "";
        } catch (Exception e) {
            throw new RuntimeException("请求第三方接口失败", e);
        }
    }


    /**
     * put 请求
     *
     * @param bodyStr
     * @param serverUrl
     * @return
     */
    public static String handlePut(String bodyStr, String serverUrl) {
        try {
            /*HttpRequest request = HttpRequest.put(TEST_API_URL + serverUrl);
            request.body(bodyStr, "application/json");
            request.header("Authorization", "Bearer " + getToken());
            request.header("Content-Type", "application/json");

            HttpResponse response = request.execute();

            return Optional.ofNullable(response.body())
                    .orElseThrow(() -> new RuntimeException("响应体为空"));*/
            return "";
        } catch (Exception e) {
            throw new RuntimeException("请求第三方接口失败", e);
        }
    }

    /**
     * delete 请求
     *
     * @param bodyStr
     * @param serverUrl
     * @return
     */
    public static String handleDelete(String bodyStr, String serverUrl) {
        try {
            /*HttpRequest request = HttpRequest.delete(TEST_API_URL + serverUrl);
            request.body(bodyStr, "application/json");
            request.header("Authorization", "Bearer " + getToken());
            request.header("Content-Type", "application/json");

            HttpResponse response = request.execute();

            return Optional.ofNullable(response.body())
                    .orElseThrow(() -> new RuntimeException("响应体为空"));*/
            return "";
        } catch (Exception e) {
            throw new RuntimeException("请求第三方接口失败", e);
        }
    }

}
