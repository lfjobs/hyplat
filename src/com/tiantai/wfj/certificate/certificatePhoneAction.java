package com.tiantai.wfj.certificate;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.certificate.service.certificateService;
import com.tiantai.wfj.front.ProductsLaunchAction;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CCode;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffCertificate;
import hy.ea.human.service.StaffService;
import hy.ea.service.CCodeService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
//import org.apache.struts2.convention.annotation.Action;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class certificatePhoneAction {
	private static final Logger logger = LoggerFactory.getLogger(certificatePhoneAction.class);
    private Logger logger = LoggerFactory.getLogger(certificatePhoneAction.class);
    @Resource
    private certificateService certificateService;
    @Resource
    private StaffService staffService;
    @Resource
    private ServerService serverService;
    @Resource
    private CCodeService codeService;
    private Object result;
    private StaffCertificate staffCertificate;
    private TEshopCusCom tcc;
    private Staff staff;
    private String companyID;


    // 列表查询
    //@Action(value = "add", results = @Result(type = "json", params = {"root", "result"}))
    public String list() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffID=request.getParameter("staffID");//人员id
        int pageNumber= Integer.parseInt(request.getParameter("pageNumber"));
        String credentialsType= request.getParameter("credentialsType");
        String credentialsNo= request.getParameter("credentialsNo");
        Map<String, Object> params = new HashMap<>();

        if (staffID ==null ||staffID.isEmpty()) {
            staffID=getTcc().getStaffid();
        }
        if (credentialsType !=null &&!credentialsType.isEmpty()) {
            params.put("credentialsType", credentialsType);
        }
        if (credentialsNo !=null &&!credentialsNo.isEmpty()) {
            params.put("credentialsNo", credentialsNo);
        }
        params.put("staffID", staffID);
        PageForm pageForm =null;
        pageForm = certificateService.getCertificateByPage(pageNumber, 15, params);

        List<CCode> credentialsTypelist = codeService.getCCodeListByPID(getCompanyID(), "scode20100423qr9eg4m2nx0000000006");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        map.put("credentialsTypelist", credentialsTypelist);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject json = new JSONObject();
        json.putAll(map, jsonConfig);
        this.result = json.toString();
        return "success";
    }

    public String lists() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffID=request.getParameter("staffID");//人员id
        //String credentialsType= request.getParameter("credentialsType");
        //String credentialsNo= request.getParameter("credentialsNo");
        Map<String, Object> params = new HashMap<>();

        if (staffID ==null ||staffID.isEmpty()) {
            staffID=getTcc().getStaffid();
        }
        /*if (credentialsType !=null &&!credentialsType.isEmpty()) {
            params.put("credentialsType", credentialsType);
        }
        if (credentialsNo !=null &&!credentialsNo.isEmpty()) {
            params.put("credentialsNo", credentialsNo);
        }*/
        params.put("staffID", staffID);
        List<BaseBean> cer =null;
        cer = certificateService.getCertificateList(params);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", cer);
        map.put("staffname",getStaff().getStaffName());
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject json = new JSONObject();
        json.putAll(map, jsonConfig);
        this.result = json.toString();
        return "success";
    }

    // 新增
    public String add() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (staffCertificate != null) {
                if (staffCertificate.getCompanyID() == null||staffCertificate.getCompanyID().isEmpty()) {
                    staffCertificate.setCompanyID(getCompanyID());
                }
                if (staffCertificate.getStaffID() == null||staffCertificate.getStaffID().isEmpty()) {
                    staff=getStaff();
                    if (staff != null) {
                        staffCertificate.setStaffID(staff.getStaffID());
                    }else {
                        map.put("success", false);
                        map.put("message", "新增失败: 人员信息获取失败！");
                    }
                }
                if (staffCertificate.getCredentialskey() == null||staffCertificate.getCredentialskey().isEmpty()) {
                    staffCertificate.setCredentialsID(serverService.getServerID("credentials"));
                    staffCertificate.setInvalidate(new Date());
                }
            }
            map=certificateService.saveOrUpdate(staffCertificate);
            //staffCertificate=certificateService.getCertificateById(staffCertificate.getCredentialsID());
            //map.put("id", staffCertificate.getCredentialskey());
            map.put("success", true);
            map.put("message", "新增成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "新增失败: " + e.getMessage());
            logger.error("操作异常", e);
        }
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }


    // 删除
    public String delete() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            certificateService.deleteCertificate(staffCertificate.getCredentialsID());
            map.put("success", true);
            map.put("message", "删除成功");
        } catch (Exception e) {
            map.put("success", false);
            logger.info("调试信息");
            map.put("message", "删除失败: " + e.getMessage());
        }
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }

    // 查看详情
    public String view() {
        StaffCertificate cert = certificateService.getCertificateByKey(staffCertificate.getCredentialskey());
        Staff staff=staffService.getStaffDataByStaffId(cert.getStaffID());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cert", cert);
        map.put("staff", staff);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject json = new JSONObject();
        json.putAll(map, jsonConfig);
        this.result = json.toString();
        return "success";
    }

    // 查看详情
    public String viewToPage() {
        HttpServletRequest request = ServletActionContext.getRequest();
        staffCertificate = certificateService.getCertificateByKey(staffCertificate.getCredentialskey());
        Staff staff=staffService.getStaffDataByStaffId(staffCertificate.getStaffID());
        request.setAttribute("staff", staff);
        return "view";
    }


    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public StaffCertificate getStaffCertificate() {
        return staffCertificate;
    }

    public void setStaffCertificate(StaffCertificate staffCertificate) {
        this.staffCertificate = staffCertificate;
    }

    public TEshopCusCom getTcc() {
        if (tcc == null) setTcc(null);
        return tcc;
    }

    public void setTcc(TEshopCusCom tcc) {
        if (tcc == null) {
            this.tcc = (TEshopCusCom) ActionContext.getContext().getSession().get("key_shop_cus_com");
        } else {
            this.tcc = tcc;
        }
    }
    public Staff getStaff() {
        if (staff == null) {
            setStaff(null);
        }
        return staff;
    }

    public void setStaff(Staff staff) {
        if (staff == null) {
            this.staff=(Staff) ActionContext.getContext().getSession().get(SessionWrap.KEY_STAFF);
            if (this.staff == null) {
                this.staff = staffService.getStaffDataByStaffId(getTcc().getStaffid());
                ActionContext.getContext().getSession().put(SessionWrap.KEY_STAFF, staff);
            }
        }else {
            this.staff = staff;
        }
    }
    public String getCompanyID() {
        return companyID==null||companyID.isEmpty()?"company201009046vxdyzy4wg0000000025":companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
}
