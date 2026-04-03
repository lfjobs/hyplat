package hy.ea.util.milvus.Xfyun;

public class XFflow implements ParserFlow {

    private String appId = "1690d84f";
    private String apiKey = "dc7ea49d5abe67227218ce8a44c04063";
    private String apiSecret = "NDE1MjQzZWMzYTk2ODExMjRlODQ5N2Yw";
    private String host = "emb-cn-huabei-1.xf-yun.com";
    private String path = "/"; // embedding接口是根路径！
    private SparkHttpClient sparkHttpClient = new SparkHttpClient();

    @Override
    public String authUrlGenerator() throws Exception {
        String requestUrl = AuthUrlGenerator.generateAuthUrl(host, path, apiKey, apiSecret);

        return requestUrl;
    }


    @Override
    public String sendSparkRequest(String requestUrl, String text) {
        return    sparkHttpClient.sendSparkRequest(requestUrl,appId,text);

    }

    @Override
    public float[] parserMessage(String result) {

        return sparkHttpClient.parserMessage(result);

    }
}
