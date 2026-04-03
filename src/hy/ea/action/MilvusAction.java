package hy.ea.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CRole;
import hy.ea.util.milvus.MilvusService;
import hy.plat.service.ServerService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Map;


@Controller
@Scope("prototype")
public class MilvusAction {

    @Resource
    private MilvusService milvusService;

    public String dbName;

    public String collName;
    public String fieldName;
    public String url;
    public String des;
    public String func;
    public String action;
    public String embeddingText;

    public String test() {
        milvusService.test();
        return "list";
    }

    public String checkDataBase(){
        try {
            milvusService.checkDataBase(dbName);
        } catch (InterruptedException e) {
           return "error";
        }
        return "success";
    }

    public String createDataBase(){
        milvusService.createDataBase(dbName);
        return "success";
    }

    public String dropDataBase(){
        return "success";
    }


    public String createCollection(){
        milvusService.createCollection(collName);
        return "success";
    }



    public String insetEmbedding(){
        milvusService.insertEmbedding( collName, url, des, func, action);
        return "success";
    }

    public String searchEmbedding(){
        milvusService.searchEmbedding(embeddingText,collName);
        return "success";
    }

    public String createIndex(){
        milvusService.searchEmbedding(fieldName,collName);
        return "success";
    }

    public String loadCollection(){
        milvusService.loadCollection(collName);
        return "success";
    }
}
