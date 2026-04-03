package hy.ea.util.milvus.db;

import com.google.common.util.concurrent.ListenableFuture;
import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.service.collection.request.AddFieldReq;
import io.milvus.v2.service.collection.request.CreateCollectionReq;
import io.milvus.v2.service.database.request.CreateDatabaseReq;
import io.milvus.v2.service.database.request.DropDatabaseReq;
import io.milvus.v2.service.vector.request.InsertReq;
import io.milvus.v2.service.vector.request.SearchReq;
import io.milvus.v2.service.vector.response.InsertResp;
import io.milvus.v2.service.vector.response.SearchResp;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MilvusDBContorller {
    private MilvusClientV2 client;

    private CollectionsManager collectionsManager;
    @PostConstruct
    public  void initDataBase() {

//        ConnectConfig config = ConnectConfig.builder()
//                .uri("http://8.156.65.136:19530")
//                .username("hyplate").password("hyplate")
//                .build();
//        client = new MilvusClientV2(config);
//        collectionsManager=new CollectionsManager(client);
//        System.out.println("启动结束");
    }

    @PreDestroy
    public void destroy() {

        try {
            if (client != null) {
                client.close();  // ✅ 关闭 gRPC 连接、释放线程
                System.out.println("Milvus 连接已关闭");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        initDataBase();
    }

    /**
     * 数据库
     * @param dbName
     * @throws InterruptedException
     */
    public void createDataBase(String dbName){
        CreateDatabaseReq req = CreateDatabaseReq.builder()
                .databaseName(dbName)
                .build();
        client.createDatabase(req);

    }
    public void checkDataBase(String dbName) throws InterruptedException {
        client.useDatabase(dbName);
    }
    public void dropDataBase(String dbName) {
        client.dropDatabase(DropDatabaseReq.builder().databaseName(dbName).build());
    }

    /**
     * 表、集合
     * @param collName
     * @return
     * @throws Exception
     */

    public void createCollection(String collName,AddFieldReq... addFieldReqs)  {

         collectionsManager.createCollection(collName,addFieldReqs);
    }


    public InsertResp insertData(InsertReq insertParam){
      return  collectionsManager.insertData(insertParam);
    }

    public void loadCollection(String collName){
        collectionsManager.loadCollection(collName);
    }
    public void createIndex(String fieldName,String collName){
        collectionsManager.createIndex(fieldName,collName);
    }
    public SearchResp search(SearchReq searchReq) {
      return  collectionsManager.search(searchReq);
    }
}
