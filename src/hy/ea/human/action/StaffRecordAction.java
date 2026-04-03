package hy.ea.human.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.human.service.StaffRecordService;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 人员档案
 * @author wangshuangni
 *
 */
@SuppressWarnings("serial")
public class StaffRecordAction {
    @Resource
    private StaffRecordService staffRecordService;
    private String result;

    public String getResumeDataByStaffID(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffID = request.getParameter("staffID");
        staffRecordService.getResumeDataByStaffID(staffID);
        return "resumedetail";
    }

    /**
     * 获取面试结果
     * @return
     */
    public String getInterviewResultByStaffID(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffID = request.getParameter("staffID");
        result = staffRecordService.getInterviewResultByStaffID(companyId,staffID);
        return "success";

    }

    public String getInterviewRegistrationByStaffId(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffID = request.getParameter("staffID");
        result = staffRecordService.getInterviewRegistrationByStaffId(companyId,staffID);
        return "success";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
