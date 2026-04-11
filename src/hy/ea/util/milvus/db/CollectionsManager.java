package hy.ea.util.milvus.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import io.milvus.param.MetricType;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.common.IndexParam;
import io.milvus.v2.service.collection.request.AddFieldReq;
import io.milvus.v2.service.collection.request.CreateCollectionReq;
import io.milvus.v2.service.collection.request.DropCollectionReq;
import io.milvus.v2.service.collection.request.LoadCollectionReq;
import io.milvus.v2.service.index.request.CreateIndexReq;
import io.milvus.v2.service.vector.request.InsertReq;
import io.milvus.v2.service.vector.request.SearchReq;
import io.milvus.v2.service.vector.response.InsertResp;
import io.milvus.v2.service.vector.response.SearchResp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionsManager {
	private static final Logger logger = LoggerFactory.getLogger(CollectionsManager.class);

    private MilvusClientV2 clientV2;

    public CollectionsManager( MilvusClientV2 clientV2) {

        this.clientV2 = clientV2;
    }





    public void createCollection(String collName,AddFieldReq[] addFieldReqs) {
        logger.info("创建成功");

        CreateCollectionReq.CollectionSchema build = CreateCollectionReq.CollectionSchema.builder().build();
        for (AddFieldReq fieldType:addFieldReqs){
            build.addField(fieldType);
        }
        CreateCollectionReq createCollectionReq = CreateCollectionReq.builder()
                .collectionName(collName).collectionSchema(build).build();
        clientV2.createCollection(createCollectionReq);

    }

    public void deleteCollection(String collName){
       clientV2.dropCollection(DropCollectionReq.builder().collectionName(collName).build());
   }


    public InsertResp insertData(InsertReq insertReq) {


        return clientV2.insert(insertReq);
    }

    public SearchResp search(SearchReq search) {
       return clientV2.search(search);
    }

    public void loadCollection(String collName){
        clientV2.loadCollection(LoadCollectionReq.builder()
                .collectionName(collName)
                .build());
    }


    public void createIndex(String fieldName,String collName){

        Map<String,Object> params=new HashMap<>();
        params.put("M",8);
        params.put("efConstruction",64);
        IndexParam indexParam=IndexParam.builder()
                .fieldName(fieldName)
                .indexType(IndexParam.IndexType.HNSW)
                .metricType(IndexParam.MetricType.COSINE)
                .extraParams(params).build();
        List<IndexParam> list=new ArrayList<>();
        list.add(indexParam);
        clientV2.createIndex(CreateIndexReq.builder()
                .collectionName(collName).indexParams(list)
                .build());
    }
}
