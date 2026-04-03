package mobile.tiantai.android.action.storeProduction.budgetSheet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.plat.bo.PageForm;
import mobile.tiantai.android.bo.CRMShortMessagingTemplatePO;
import mobile.tiantai.android.bo.StaffSend;
import mobile.tiantai.android.service.storeProduction.budgetSheet.CRMShortMessagingTemplatePOServer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CRMShortMessagingTemplatePOAction
 *
 * @author zch
 */
@Controller
@Scope("prototype")
public class CRMShortMessagingTemplatePOAction extends HttpServlet {
    @Resource
    private CRMShortMessagingTemplatePOServer crmShortMessagingTemplatePOServer;
    private String result;
    private PageForm pageForm;
    private int pageNumber;
    private String templateHeadline;
    private int pageSize;
    private String id;
    private CRMShortMessagingTemplatePO crmShortMessagingTemplatePO;
    private String companyId;
    private String staffName;
    private String relation;
    private String isSend;
    private String typeValue;


    public String saveTemplate() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String date = request.getParameter("data");
            if (date != null) {
                //json字符串转实体
                ObjectMapper objectMapper = new ObjectMapper();
                TemplateDto templateRequest = objectMapper.readValue(date, TemplateDto.class);
                result = crmShortMessagingTemplatePOServer.saveTemplate(templateRequest);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "success";
    }

    public String getTemplate() {
        pageForm = crmShortMessagingTemplatePOServer.getTemplate(pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String deleteTemplate() {
        result = crmShortMessagingTemplatePOServer.deleteTemplate(id);
        return "success";
    }

    public String updateTemplate() {
        CRMShortMessagingTemplatePO old = this.crmShortMessagingTemplatePO;
        result = crmShortMessagingTemplatePOServer.updateTemplateDate(old);
        return "success";
    }

    public String selectTemplate() {
        pageForm = crmShortMessagingTemplatePOServer.selectTemplate(pageNumber, pageSize, templateHeadline);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String updateTemplate1() {
        crmShortMessagingTemplatePO = crmShortMessagingTemplatePOServer.selectById(id);
        return "crmShortMessagingTemplatePO";
    }

    //免费短信  积分余额
    public String getNum() {
        Map<String, String> map = new HashMap<>();
        map = crmShortMessagingTemplatePOServer.getNum();
        result = JSONObject.fromObject(map).toString();
        return "success";
    }

    //查询公司
    public String getCompanys() {
        pageForm = crmShortMessagingTemplatePOServer.getCompany(pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String companysList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        return type;
    }


    //员工列表
    public String getStaffList() {
        pageForm = crmShortMessagingTemplatePOServer.getStaffList(companyId, pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String getStaffListByName() {
        pageForm = crmShortMessagingTemplatePOServer.getStaffListByName(staffName, companyId, pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String getCategoryList() {
        List<String> list = new ArrayList<String>();
        list = crmShortMessagingTemplatePOServer.getCategoryList(companyId);
        result = JSONArray.fromObject(list).toString();
        return "success";
    }

    public String getStaffListByRelation() {
        pageForm = crmShortMessagingTemplatePOServer.getStaffListByRelation(relation, companyId, pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    //确认发送
    public String send() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String localTemplateText = request.getParameter("localTemplateText");
        String firstNumber = request.getParameter("firstNumber");
        String selectedData = request.getParameter("selectedData");
        ObjectMapper objectMapper = new ObjectMapper();
        List<StaffSend> StaffSendList = new ArrayList<StaffSend>();
        try {
            StaffSendList = objectMapper.readValue(selectedData, objectMapper.getTypeFactory().constructCollectionType(List.class, StaffSend.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        result = crmShortMessagingTemplatePOServer.send(localTemplateText, firstNumber, StaffSendList);
        return "success";
    }

    public String getSendStaffList() {
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1, SessionWrap.KEY_SHOPCUSCOM);
        pageForm = crmShortMessagingTemplatePOServer.getSendStaffList(isSend, tc.getCompanyId(), pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String saveIntendedCustomers() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String type1 = request.getParameter("type1");
        String selectedData = request.getParameter("selectedData");
        ObjectMapper objectMapper = new ObjectMapper();
        IntendedCustomersDto intendedCustomers;
        try {
            intendedCustomers = objectMapper.readValue(selectedData, IntendedCustomersDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        result = crmShortMessagingTemplatePOServer.saveIntendedCustomers(type1, intendedCustomers);
        return "success";
    }

    public String getIntendedCustomersList() {
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1, SessionWrap.KEY_SHOPCUSCOM);
        pageForm = crmShortMessagingTemplatePOServer.getIntendedCustomersList(typeValue, tc.getSccId(), pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    //get set
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CRMShortMessagingTemplatePO getCrmShortMessagingTemplatePO() {
        return crmShortMessagingTemplatePO;
    }

    public void setCrmShortMessagingTemplatePO(CRMShortMessagingTemplatePO crmShortMessagingTemplatePO) {
        this.crmShortMessagingTemplatePO = crmShortMessagingTemplatePO;
    }

    public String getTemplateHeadline() {
        return templateHeadline;
    }

    public void setTemplateHeadline(String templateHeadline) {
        this.templateHeadline = templateHeadline;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }
}
