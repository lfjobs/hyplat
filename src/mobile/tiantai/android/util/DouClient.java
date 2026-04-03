package mobile.tiantai.android.util;

import com.squareup.okhttp.*;

import java.util.concurrent.TimeUnit;

public class DouClient {
    private static final String API_KEY = "Bearer 97894268-353a-4592-9242-b11df7944f4f";
    private static final String URL = "https://ark.cn-beijing.volces.com/api/v3/responses";
 
    public static String sendRequest(String message) throws Exception {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(100, TimeUnit.SECONDS);
        client.setReadTimeout(100, TimeUnit.SECONDS);
        client.setWriteTimeout(100, TimeUnit.SECONDS);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType,message);
        Request request = new Request.Builder()
                .url(URL)
                .addHeader("Authorization", API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }
}