package hy.ea.util.milvus;

import io.milvus.v2.service.vector.response.SearchResp;

public interface MilvusService {

    public void test();

    void insertEmbedding(String collName,String url,String des,String func,String action);

    SearchResp searchEmbedding(String text, String collName);
    void createCollection(String collName);
    void deleteCollection();

    void checkDataBase(String dbName) throws InterruptedException;

    void createDataBase(String dbName);

    void createIndex(String fieldName,String collName);
    void loadCollection(String collName);
}
