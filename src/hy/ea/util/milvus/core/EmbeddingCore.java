package hy.ea.util.milvus.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.util.logging.Log4j;
import hy.ea.util.milvus.Xfyun.ParserFlow;
import hy.ea.util.milvus.Xfyun.XFflow;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Log4j
public class EmbeddingCore {
	private static final Logger logger = LoggerFactory.getLogger(EmbeddingCore.class);
    private final ParserFlow parserFlow;

    public EmbeddingCore(ParserFlow parserFlow) {
        this.parserFlow=parserFlow;

    }
    public float[] execute(String msg){
        String s = null;
        try {
            s = parserFlow.authUrlGenerator();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String result = parserFlow.sendSparkRequest(s, msg);
      return  parserFlow.parserMessage(result);
    }

    public static String parserString(String msg){

        JSONObject jsonObject=new JSONObject();

        JSONArray jsonArray=new JSONArray();
        JSONObject obj=new JSONObject();
        obj.put("content",msg);
        obj.put("role","user");
        jsonArray.put(obj);

        jsonObject.put("messages",jsonArray);
        return jsonObject.toString();
    }
    public static void main(String[] args) {
        EmbeddingCore embeddingCore=new EmbeddingCore(new XFflow());
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        XfMessages xfMessages=new XfMessages();
        xfMessages.setRole("user");
        xfMessages.setContent("这是测试的向量");
        jsonArray.put(xfMessages);
        jsonObject.put("messages",jsonArray);
        float[] execute = embeddingCore.execute(jsonObject.toString());
        logger.info("值：{}", execute);
    }

}
