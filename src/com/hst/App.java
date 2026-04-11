package com.hst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hst.ces.util.SHAUtil;
import com.wechat.utils.WeChatUtil;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * JWT Token信息说明：
 * 第一部分：{"alg": "SHA256"},签名算法标注,支持，SHA256,SM3
 * 第二部分：{ "key": "12345678", "timestamp": 1621341963000}   key字符串，timestamp long类型
 * 第三部分：SHA256(BASE64(第一部分)+BASE64(第二部分)+secret)
 * <p>
 * header中 传参： Authorization：BASE64(第一部分).BASE64(第一部分).第三部分
 * <p>
 * 说明：用户保护好自己的key和secret,如有泄漏，联系管理员更新
 */
public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static  String wsUrl = "https://47.94.154.17:8443/api/v1/";
    public static void main(String[] args) {

        long timestamp = System.currentTimeMillis();

        String secret = "HpQi5csSMrufkM)b&#YWVlr7o*wWUG3G";

        String header = Base64.getEncoder().encodeToString("{\"alg\": \"SHA256\"}".getBytes(StandardCharsets.UTF_8));
        String load = Base64.getEncoder().encodeToString(("{ \"key\": \"4QY08Kyh\", \"timestamp\": " + timestamp + "}")
                .getBytes(StandardCharsets.UTF_8));

        // SHA256算法加密
        String signature = SHAUtil.SHA256(header + load + secret);

        // SM3算法加密
        //String signature = SM3.encrypt(header + load + secret);

        logger.info("调试信息");
    }

    /**
     *
     * 获取Token
     * @return
     */
    private  static String getAuthorization(){

        long timestamp = System.currentTimeMillis();

        String secret = "HpQi5csSMrufkM)b&#YWVlr7o*wWUG3G";

        String header = Base64.getEncoder().encodeToString("{\"alg\": \"SHA256\"}".getBytes(StandardCharsets.UTF_8));
        String load = Base64.getEncoder().encodeToString(("{ \"key\": \"4QY08Kyh\", \"timestamp\": " + timestamp + "}")
                .getBytes(StandardCharsets.UTF_8));

        // SHA256算法加密
        String signature = SHAUtil.SHA256(header + load + secret);

        // SM3算法加密
        //String signature = SM3.encrypt(header + load + secret);

        logger.info("调试信息");

        return header + "." + load + "." + signature;
    }


    /**
     * 好视通请求
     */
    public static JSONObject hstHttpV1(String path,String method, String requestParam) {
        JSONObject jsonObject = null;

        String authorization = getAuthorization();

        try {
              if(method.equals("POST")) {
                  HttpPost httpPost = new HttpPost(wsUrl + path);

                  httpPost.setHeader("Content-Type", "application/json");
                  httpPost.setHeader("Authorization", authorization);
                  StringEntity postingString = new StringEntity(requestParam,
                          "utf-8");
                  httpPost.setEntity(postingString);
                  HttpClientBuilder httpClientBuilder = HttpClients.custom();
                  httpClientBuilder.setSSLHostnameVerifier(new HostnameVerifier() {
                      @Override
                      public boolean verify(String s, SSLSession sslSession) {
                          return true;//证书校验通过
                      }
                  });
                  SSLContext ctx = SSLContexts.custom().useProtocol("TLSv1.2").build();


                  CloseableHttpClient httpClient = httpClientBuilder.setSslcontext(ctx).build();
                  CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
                  HttpEntity httpResponseEntity = httpResponse.getEntity();

                  String responseEntityStr = EntityUtils.toString(httpResponseEntity, "UTF-8");

                  httpResponse.close();
                  logger.info("值：{}", responseEntityStr);
                  jsonObject = JSONObject.fromObject(responseEntityStr);
              }else{

                  HttpGet httpGet = new HttpGet(wsUrl + path);
                  httpGet.setHeader("Content-Type", "application/json");
                  httpGet.setHeader("Authorization", authorization);

                  HttpClientBuilder httpClientBuilder = HttpClients.custom();

                  httpClientBuilder.setSSLHostnameVerifier(new HostnameVerifier() {
                      @Override
                      public boolean verify(String s, SSLSession sslSession) {
                          return true;//证书校验通过
                      }
                  });
                  SSLContext ctx = SSLContexts.custom().useProtocol("TLSv1.2").build();

                  CloseableHttpClient httpClient = httpClientBuilder.setSslcontext(ctx).build();
                  CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                  HttpEntity httpResponseEntity = httpResponse.getEntity();
                  String responseEntityStr = EntityUtils.toString(httpResponseEntity,"UTF-8");
                  httpResponse.close();
                  logger.info("值：{}", responseEntityStr);
                  jsonObject = JSONObject.fromObject(responseEntityStr);
              }

        }catch(Exception e){
            logger.error("操作异常", e);
        }


        return jsonObject;
    }

    /**
     *
     * 去掉值为空的
     * @param paramMap
     * @return
     */
    public static Map<String,Object> removeMapEmptyValue(Map<String,Object> paramMap){
        Set<String> set = paramMap.keySet();
        Iterator<String> it = set.iterator();
        List<String> listKey = new ArrayList<String>();
        while (it.hasNext()) {
            String str = it.next();
            if(paramMap.get(str)==null || "".equals(paramMap.get(str))){
                listKey.add(str) ;
            }
        }
        for (String key : listKey) {
            paramMap.remove(key);
        }
        return paramMap;
    }

}
