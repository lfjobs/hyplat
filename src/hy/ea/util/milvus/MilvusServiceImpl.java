package hy.ea.util.milvus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.*;
import hy.ea.util.milvus.Xfyun.XFflow;
import hy.ea.util.milvus.core.EmbeddingCore;
import hy.ea.util.milvus.db.MilvusDBContorller;
import io.milvus.v2.common.DataType;
import io.milvus.v2.common.IndexParam;
import io.milvus.v2.service.collection.request.AddFieldReq;
import io.milvus.v2.service.vector.request.InsertReq;
import io.milvus.v2.service.vector.request.SearchReq;
import io.milvus.v2.service.vector.request.data.FloatVec;
import io.milvus.v2.service.vector.response.InsertResp;
import io.milvus.v2.service.vector.response.SearchResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MilvusServiceImpl implements MilvusService {

    @Resource
    private MilvusDBContorller milvusDBContorller;


    @Override
    public void test() {


        EmbeddingCore embeddingCore=new EmbeddingCore(new XFflow());
        embeddingCore.execute("宁夏红酒");
    }

    private Gson gson=new Gson();
    @Override
    public void insertEmbedding(String collName,String url,String des,String func,String action) {
        JsonObject obj = new JsonObject();

        obj.addProperty("url", url);
        obj.addProperty("description", des);
        obj.addProperty("function", func);
        obj.addProperty("action", action);

        EmbeddingCore embeddingCore = new EmbeddingCore(new XFflow());
        String s = EmbeddingCore.parserString(des);
        float[] list = embeddingCore.execute(s);
        obj.add("embedding", gson.toJsonTree(list));
        // 多条记录放入 List<JsonObject>
        List<JsonObject> listOfJsonObjects = new ArrayList<>();
        listOfJsonObjects.add(obj);

        InsertReq insertReq= InsertReq.builder().collectionName(collName).data(listOfJsonObjects).build();
        InsertResp insertResp = milvusDBContorller.insertData(insertReq);
        logger.info("调试信息");
    }

    @Override
    public SearchResp searchEmbedding(String text,String collName) {
        EmbeddingCore embeddingCore=new EmbeddingCore(new XFflow());
        text = EmbeddingCore.parserString(text);
        float[] vetors = embeddingCore.execute(text);
        FloatVec floatVec=new FloatVec(vetors);

        SearchReq searchReq = SearchReq.builder()
                .collectionName(collName)
                .annsField("embedding")                // 向量字段
                .metricType(IndexParam.MetricType.COSINE)        // 相似度
                .limit(5)                             // topK
                .outputFields(Arrays.asList("id", "url", "description", "action"))
                .data(Collections.singletonList(floatVec))
                .build();


        SearchResp search = milvusDBContorller.search(searchReq);
        logger.info("调试信息");
        return search;

    }

    @Override
    public void createCollection(String collName) {
        AddFieldReq req=AddFieldReq.builder().fieldName("id").isPrimaryKey(true).autoID(true).dataType(DataType.VarChar).build();

        AddFieldReq req1=AddFieldReq.builder().fieldName("url").dataType(DataType.VarChar).maxLength(512).build();
        AddFieldReq req2=AddFieldReq.builder().fieldName("description").dataType(DataType.VarChar).maxLength(1024).build();
        AddFieldReq req3=AddFieldReq.builder().fieldName("function").dataType(DataType.VarChar).maxLength(512).build();
        AddFieldReq req4=AddFieldReq.builder().fieldName("action").dataType(DataType.VarChar).maxLength(256).build();
        AddFieldReq req5=AddFieldReq.builder().fieldName("embedding").dataType(DataType.FloatVector).dimension(2560).build();

        milvusDBContorller.createCollection(collName,req,req1,req2,req3,req4,req5);
    }

    @Override
    public void deleteCollection() {

    }

    @Override
    public void checkDataBase(String dbName) throws InterruptedException {
        milvusDBContorller.checkDataBase(dbName);
    }

    @Override
    public void createDataBase(String dbName) {
        milvusDBContorller.createDataBase(dbName);
    }

    @Override
    public void createIndex(String fieldName, String collName) {
        milvusDBContorller.createIndex(fieldName,collName);
    }

    @Override
    public void loadCollection(String collName) {
        milvusDBContorller.loadCollection(collName);
    }


}
