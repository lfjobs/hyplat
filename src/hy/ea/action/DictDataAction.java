package hy.ea.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.DictData;
import hy.ea.service.DictDataService;
import hy.plat.bo.BaseBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Scope("prototype")
public class DictDataAction {
    @Resource
    private DictDataService dictDataService;

    String result;
    /**
     * 根据类型获取字典数据
     * @return
     */
    public String getDictDataByDictType(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String type = request1.getParameter("type");
        List<BaseBean> list = dictDataService.getDictDataListByType(type,"");
        JSONArray obj = JSONArray.fromObject(list);
        result = obj.toString();
        return "success";
    }

    /**
     * 根据类型获取字典数据
     * @return
     */
    public String getDictDataByType(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        DictData data = dictDataService.getDictDataByType(type);
        JSONObject obj = JSONObject.fromObject(data);
        result = obj.toString();
        return "success";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}