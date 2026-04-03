package hy.ea.util.layercache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class IndustryCache {

    @Resource
    private BaseBeanDao beandao;
    private List<BaseBean> industryList;
    private String xhy="scode20170714cnjcrn5jm20000000067"; //scode表中新行业id

    private final SearchEngine engine=new SearchEngine();

    public List<BaseBean> getIndustryRootList() {
        return industryRootList;
    }

    private List<BaseBean> industryRootList;
    @PostConstruct
    public void init() {
        // 项目启动时加载
        this.industryList = loadIndustryList();
        loadIndustryRootList();
    }

    private void loadIndustryRootList() {
        String sql = "select * from DT_Business_Type o where o.status=1 and o.type_Level=2  order by sort_Num";
        industryRootList = beandao.getListBeanBySqlAndParams(sql, null);

    }

    private List<BaseBean> loadIndustryList() {
        List<BaseBean> beanList = new ArrayList<>();

        beanList = beandao.getListBeanBySqlAndParams("select o.type_id,o.type_pid,o.type_num,o.parent_num,o.type_name,o.type_level from DT_Business_Type o where o.status=1  order by o.sort_num", null);

        JSONArray jsonArray = (JSONArray) JSONObject.toJSON(beanList);

        for (int i=0; i<jsonArray.size();i++){
            JSONArray array = jsonArray.getJSONArray(i);
            Record r1 = new Record(
                    array.getString(3),
                    array.getString(2),
                    array.getString(4),
                    array.getInteger(5)
            );
            engine.insert(r1);

        }


        return beanList;
    }
    public List<Record> searchByName(String name){
        return engine.search(name);
    }

    public List<Record> searchByCodeId(String id){
        return engine.searchByCodeId(id);
    }
    // 提供给其他类查询
    public List<BaseBean> getIndustryList() {
        return industryList;
    }

    public void insertEngin(Record record){
        engine.insert(record);
    }

}
