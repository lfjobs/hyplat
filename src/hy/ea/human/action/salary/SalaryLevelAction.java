package hy.ea.human.action.salary;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.human.service.CSalaryLevelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 工资级别
 * @author wangshuangni
 *
 */
@SuppressWarnings("serial")
public class SalaryLevelAction {
    @Resource
    private CSalaryLevelService salaryLevelService;

    private PageForm pageForm;

    private int pageNumber;

    private int pageSize;

    private String result;
    /**
     * 保存级别
     */
    public String saveSalaryLevel(){
        result = salaryLevelService.saveSalaryLevel();
        return "success";
    }

    /**
     * 获取级别列表
     * @return
     */
    public String getSalaryLevelList(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        pageForm = salaryLevelService.getSalaryLevelList(companyId,pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    /**
     * 获取级别数据
     * @return
     */
    public String getSalaryLevelByNum(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String levelListId = request1.getParameter("levelListId");
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        pageForm = salaryLevelService.getSalaryLevelByNum(levelListId,companyId,pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    /**
     * 保存保障工资设置
     * @return
     */
    public String saveDictData(){
        result = salaryLevelService.saveDictData();
        return "success";
    }

    /**
     * 根据类型查询授权数据
     */
    public String getDictDataListByType() {
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String type = request1.getParameter("type");
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        List<BaseBean> list = salaryLevelService.getDictDataListByType(type,companyId);
        JSONArray obj = JSONArray.fromObject(list);
        result = obj.toString();
        return "success";
    }
    /**
     * 根据公司查询数据
     */
    public String getDictDataListByCompanyId() {
        HttpServletRequest request1 = ServletActionContext.getRequest();
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        List<BaseBean> list = salaryLevelService.getDictDataListByCompanyId(companyId);
        JSONArray obj = JSONArray.fromObject(list);
        result = obj.toString();
        return "success";
    }


    public String getLevelDataPageForm(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        pageForm = salaryLevelService.getLevelDataPageFormByCompanyId(companyId,pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String saveLevelSalaryData(){
        salaryLevelService.saveLevelSalaryData("",null,new ArrayList<>());
        result= "success";
        return "success";
    }

    /**
     * 获取级别工资列表
     */
    public String getLevelSalaryDataList(){
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        pageForm = salaryLevelService.getLevelSalaryDataListByCompanyId(companyId,pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String saveGuaranteeSalary(){
        result = salaryLevelService.saveGuaranteeSalary();
        return "success";
    }

    /**
     * 保存日期
     * @return
     */
    public String saveDate(){
        result = salaryLevelService.saveDate();
        return "success";
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
