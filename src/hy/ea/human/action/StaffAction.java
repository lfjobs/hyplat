package hy.ea.human.action;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CRole;
import hy.ea.bo.human.*;
import hy.ea.bo.office.DocStaff;
import hy.ea.bo.office.Document;
import hy.ea.human.service.StaffService;
import hy.ea.office.service.DocCommonService;
import hy.ea.service.CRoleService;
import hy.plat.bo.PageForm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工信息
 * @author wangshuangni
 *
 */
@SuppressWarnings("serial")
public class StaffAction {
    @Resource
    private CRoleService roleService;

    @Resource
    private StaffService staffService;
    @Resource
    private DocCommonService docCommonService;
    private Staff staff;

    private COS cos;

    private COSDimissionStaff cOSDimissionStaff;

    private Audition audition;

    private DocStaff docStaff;

    private Document document;

    private String result;

    private PageForm pageForm;

    private int pageNumber;

    private int pageSize;

    private String companyId;

    private  List<Object>  lists;
    public  String getRoleList(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        List<CRole> roleList = roleService.getRoleList(companyId);
        JSONArray obj = JSONArray.fromObject(roleList);
        result = obj.toString();
        return "success";
    }

    public String saveStaffData() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String fileData = request.getParameter("fileData");
        String postIds = request.getParameter("postIds");
        String type = request.getParameter("type");
        String deleteFileIds = request.getParameter("deleteFileIds");
        ObjectMapper mapper = new ObjectMapper();
        List<UpLoadFile> upLoadFileList = mapper.readValue(fileData, new TypeReference<List<UpLoadFile>>() {});
        result = staffService.saveStaffData(staff,cos,upLoadFileList,postIds,type,deleteFileIds);
        String realpath = ServletActionContext.getRequest().getSession()
                .getServletContext().getRealPath("/");
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if(account!=null) {
            docCommonService.createDoc(realpath, account.getCompanyID(), account.getStaffID(), cos.getContractType(), staff,"01");
        }
        return "success";
    }

    public String getEntryStaffData(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String queryName = request.getParameter("queryName");
        pageForm = staffService.getEntryStaffDataList(companyId,pageNumber,pageSize,queryName,cos);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public  String getFileDataByStaffId(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffId = request.getParameter("staffId");
        List<UpLoadFile> upLoadFileList = staffService.getFileDataByStaffId(staffId,companyId);
        JSONArray obj = JSONArray.fromObject(upLoadFileList);
        result = obj.toString();
        return "success";
    }


    public String getStaffDataByStaffId(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffId = request.getParameter("staffId");
        String type = request.getParameter("type");
        staff = staffService.getStaffDataByStaffId(staffId);
        cos = staffService.getCosDataByParam(staffId,companyId);
        lists = staffService.getDocStaffInfo(cos.getStaffID(),cos.getCompanyID(),"01");
        request.setAttribute("curstaffID",account.getStaffID());

        if (cos.getEntryDate() == null){
            audition = staffService.getAuditionDataByParam(staffId,companyId);
            if (audition.getRegisterDate() != null){
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                cos.setEntryDate(sf.format(audition.getRegisterDate()));
            }

        }

        return type;
    }
    public String deleteStaffByStaffId(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffId = request.getParameter("staffId");
        String cosId = request.getParameter("cosId");
        result = staffService.deleteStaffByStaffId(companyId,staffId,cosId);
        return "success";
    }

    public String getSignData(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String account = request.getParameter("account");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        pageForm = staffService.getSignData(account,beginDate,endDate,pageNumber,pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String saveSignData(){
        result = staffService.saveSignData();
        return "success";

    }
    public String getNeedJoinStaffData(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        pageForm = staffService.getNeedJoinStaffData(companyId,pageNumber,pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";

    }
    public String getEntryStatisticsData(){
        result = staffService.getEntryStatisticsData();
        return "success";
    }

    /**
     *
     * 保存合同和入职关系
     * @return
     */
    public String saveRelateDoc(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        HttpServletRequest request = ServletActionContext.getRequest();
        String templateId = request.getParameter("templateId");
        String contractType = request.getParameter("contractType");
        String contractTypeName = request.getParameter("contractTypeName");
        String temptId = request.getParameter("temptId");
        staffService.saveRelateDoc(templateId,contractType,contractTypeName,account.getCompanyID(),temptId,account.getStaffID(),"入职合同");
        return "success";
    }
    /**
     *
     * 刪除公司合同模板
     * @return
     */
    public String deleteRelateDoc(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        HttpServletRequest request = ServletActionContext.getRequest();
        String templateId = request.getParameter("templateId");
        result = staffService.deleteRelateDoc(templateId,account.getCompanyID());
        return "success";
    }


    /**
     * 保存公司合同模板
     * @return
     */
    public String saveContractTemp(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        HttpServletRequest request = ServletActionContext.getRequest();
        String templateId = request.getParameter("templateId");
        String contractType = request.getParameter("contractType");
        String contractTypeName = request.getParameter("contractTypeName");
        String temptId = request.getParameter("temptId");
        result = staffService.saveContractTemp(templateId,contractType,contractTypeName,account.getCompanyID(),temptId,account.getStaffID());
        return "success";
    }
    public String getStaffDocList(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String queryName = request.getParameter("queryName") == null ? "" : request.getParameter("queryName");
        String staffID = request.getParameter("staffID") == null ? "" : request.getParameter("staffID");
        Map<String,String> params = new HashMap<>();
        params.put("queryName",queryName);
        params.put("staffID",staffID);
        pageForm = staffService.getStaffDocList(companyId,pageNumber,pageSize,params);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";

    }

    public String getStaffContractByStaffId(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffId = request.getParameter("staffId");
        String docId = request.getParameter("docId");
        staff = staffService.getStaffDataByStaffId(staffId);
        cos = staffService.getCosDataByParam(staffId,companyId);
        docStaff = staffService.getStaffContractInfo(staffId,companyId,docId);
        document = staffService.getDocumentByDocId(docId);
        if (cos.getEntryDate() == null){
            audition = staffService.getAuditionDataByParam(staffId,companyId);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            cos.setEntryDate(sf.format(audition.getRegisterDate()));
        }

        return "staffContract";
    }
    public String getContractDataByDocId(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String docId = request.getParameter("docId");
        result = staffService.getStaffContractByDocId(docId);
        return "success";
    }
    public String getEntryStaffRecordByStaffId(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffId = request.getParameter("staffId");
        companyId = request.getParameter("companyId");
        staff = staffService.getStaffDataByStaffId(staffId);
        return "entryStaffRecord";
    }

    public String getResignStaffData(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String queryName = request.getParameter("queryName");
        pageForm = staffService.getResignStaffData(companyId,pageNumber,pageSize,queryName);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String getResignStaffDataByStaffId(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffId = request.getParameter("staffId");
        String type = request.getParameter("type");

        if ("add".equals(type)){
            staffId = account.getStaffID();
            cOSDimissionStaff = new COSDimissionStaff();
        } else {
            cOSDimissionStaff = staffService.getCOSDimissionStaffDataByParam(staffId,companyId);
            lists = staffService.getDocStaffInfo(staffId,companyId,"02");
        }
        staff = staffService.getStaffDataByStaffId(staffId);
        cos = staffService.getCosDataByStaffIdAndCompanyId(staffId,companyId);
        if (cos == null || cos.getEntryDate() == null){
            audition = staffService.getAuditionDataByParam(staffId,companyId);
            if (audition != null && audition.getRegisterDate() != null){
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                cos.setEntryDate(sf.format(audition.getRegisterDate()));
            }
        }

        return type +"_resign";
    }
    public String saveResignStaffData() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        result = staffService.saveResignStaffData(cOSDimissionStaff,staff,type);
        return "success";
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public COS getCos() {
        return cos;
    }

    public void setCos(COS cos) {
        this.cos = cos;
    }

    public COSDimissionStaff getcOSDimissionStaff() {
        return cOSDimissionStaff;
    }

    public void setcOSDimissionStaff(COSDimissionStaff cOSDimissionStaff) {
        this.cOSDimissionStaff = cOSDimissionStaff;
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

    public List<Object> getLists() {
        return lists;
    }

    public void setLists(List<Object> lists) {
        this.lists = lists;
    }

    public DocStaff getDocStaff() {
        return docStaff;
    }

    public void setDocStaff(DocStaff docStaff) {
        this.docStaff = docStaff;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
