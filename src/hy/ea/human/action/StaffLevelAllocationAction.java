package hy.ea.human.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.DictData;
import hy.ea.bo.ExamineProcessData;
import hy.ea.bo.human.StaffLevelAllocation;
import hy.ea.bo.human.salary.SalaryData;
import hy.ea.human.service.CSalaryLevelService;
import hy.ea.human.service.StaffLevelAllocationService;
import hy.ea.service.DictDataService;
import hy.ea.service.ExamineProcessService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工级别分配
 * @author wangshuangni
 *
 */
@SuppressWarnings("serial")
public class StaffLevelAllocationAction {
    @Resource
    private StaffLevelAllocationService staffLevelAllocationService;

    @Resource
    private CSalaryLevelService salaryLevelService;

    @Resource
    private ExamineProcessService examineProcessService;

    @Resource
    private DictDataService dictDataService;

    private String result;

    private PageForm pageForm;

    private StaffLevelAllocation staffLevelAllocation;

    private SalaryData salaryDataOld;

    private SalaryData salaryDataNow;

    private ExamineProcessData examineProcessData;

    private int pageNumber;

    private int pageSize;

    public String getLevelAllocationList(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        String staffID = account.getStaffID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String status = request.getParameter("status") == null ? "" : request.getParameter("status");
        String staffId = request.getParameter("staffId") == null ? "" : request.getParameter("staffId");
        Map<String,String> params = new HashMap<>();
        params.put("status",status);
        if ("".equals(staffId)){
            pageForm = staffLevelAllocationService.getLevelAllocationList(companyId,account.getStaffID(),params,pageNumber,pageSize);
        } else {
            // 查询人员级别升降
            params.put("status","1");
            pageForm = staffLevelAllocationService.getLevelAllocationList(companyId,staffId,params,pageNumber,pageSize);
        }
        DictData dictData=dictDataService.getDictDataByParam("levelExamine",staffID,companyId);
        JSONObject data = new JSONObject();
        data.put("pageForm", pageForm);
        if(dictData == null){
            data.put("levelExamine", false);
        } else {
            data.put("levelExamine", true);
        }
        result = JSONObject.fromObject(data).toString();
        return "success";
    }

    public String getLevelAllocationById(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String levelAllocationId = request.getParameter("levelAllocationId");
        String type = request.getParameter("type");
        staffLevelAllocation = staffLevelAllocationService.getLevelAllocationById(levelAllocationId,companyId);
        salaryDataOld = salaryLevelService.getSalaryLevelDataBySalaryLevelId(staffLevelAllocation.getSalaryLevelIdOld(),companyId);
        salaryDataOld.setSalaryLevelSerial(salaryDataOld.getSalaryLevelSerial() + "级");
        salaryDataNow = salaryLevelService.getSalaryLevelDataBySalaryLevelId(staffLevelAllocation.getSalaryLevelId(),companyId);
        salaryDataNow.setSalaryLevelSerial(salaryDataNow.getSalaryLevelSerial() + "级");
        return type;
    }

    public String getExamineProcessList(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String levelAllocationId = request.getParameter("levelAllocationId");
        List<BaseBean>  examineProcessList = staffLevelAllocationService.getExamineProcessList(levelAllocationId,companyId);
        JSONArray obj = JSONArray.fromObject(examineProcessList);
        result = obj.toString();
        return "success";
    }
    public String getStaffDataList(){
        pageForm = staffLevelAllocationService.getStaffDataList(pageNumber,pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }


    public String getSalaryLevelDataBySalaryLevelId(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String salaryLevelId = request.getParameter("salaryLevelId");
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        SalaryData data = salaryLevelService.getSalaryLevelDataBySalaryLevelId(salaryLevelId,companyId);
        result = JSONObject.fromObject(data).toString();
        return "success";
    }

    public String getSalaryLevelDataByStaffId(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffId = request.getParameter("staffId");
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        SalaryData data = salaryLevelService.getSalaryLevelDataByStaffId(staffId,companyId);
        result = JSONObject.fromObject(data).toString();
        return "success";
    }

    public String saveAllocationData(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        if ("examine".equals(type)){
            result = staffLevelAllocationService.examineLevelAllocation(examineProcessData,staffLevelAllocation);
        } else {
            result = staffLevelAllocationService.saveAllocationData(staffLevelAllocation);
        }

        return "success";
    }

    public String deleteLevelAllocationById(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String salaryLevelId = request.getParameter("salaryLevelId");
        result = staffLevelAllocationService.deleteLevelAllocationById(salaryLevelId,companyId);
        return "success";
    }

    public String examineLevelAllocation(){
        result = staffLevelAllocationService.examineLevelAllocation(examineProcessData,staffLevelAllocation);
        return "success";
    }

    public String saveTransmitPersonData(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String transmitId = request.getParameter("transmitId");
        String transmitName = request.getParameter("transmitName");
        String id = request.getParameter("id");
        result = staffLevelAllocationService.saveTransmitPersonData(id,transmitId,transmitName);
        return "success";
    }

    public String getRoleDataByQueryName(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String queryName = request.getParameter("queryName");
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        Map<String,String> param = new HashMap<>();
        param.put("roleName",queryName);
        pageForm = staffLevelAllocationService.getRoleDataByParam(param,companyId,pageNumber,pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String saveStaffLevelByAllocationId(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String allocationId = request.getParameter("allocationId");
        result = staffLevelAllocationService.saveStaffLevelByAllocationId(allocationId);
        return "success";
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public StaffLevelAllocation getStaffLevelAllocation() {
        return staffLevelAllocation;
    }

    public void setStaffLevelAllocation(StaffLevelAllocation staffLevelAllocation) {
        this.staffLevelAllocation = staffLevelAllocation;
    }

    public SalaryData getSalaryDataOld() {
        return salaryDataOld;
    }

    public void setSalaryDataOld(SalaryData salaryDataOld) {
        this.salaryDataOld = salaryDataOld;
    }

    public SalaryData getSalaryDataNow() {
        return salaryDataNow;
    }

    public void setSalaryDataNow(SalaryData salaryDataNow) {
        this.salaryDataNow = salaryDataNow;
    }

    public ExamineProcessData getExamineProcessData() {
        return examineProcessData;
    }

    public void setExamineProcessData(ExamineProcessData examineProcessData) {
        this.examineProcessData = examineProcessData;
    }
}
