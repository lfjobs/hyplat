package hy.ea.util.milvus.Xfyun;

public interface ParserFlow {
    String authUrlGenerator() throws Exception;


    String sendSparkRequest(String requestUrl, String text);



    float[] parserMessage(String result);
}
