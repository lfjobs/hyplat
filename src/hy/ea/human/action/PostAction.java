package hy.ea.human.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.Audition;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.DepartmentPost;
import hy.ea.bo.human.Staff;
import hy.ea.human.service.PostService;
import hy.plat.bo.PageForm;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 岗位管理
 * @author wangshuangni
 *
 */
@SuppressWarnings("serial")
public class PostAction {
    @Resource
    private PostService postService;

    private String result;

    private PageForm pageForm;

    private int pageNumber;

    private int pageSize;

    private DepartmentPost departmentPost;

    private COS cos;

    private Audition entity;

    private String maxNum;

    private String orgName;

    private String search;

    private Staff staff;

    public  String getDeployList(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String search = request.getParameter("search") == null ? "" : request.getParameter("search");
        result = postService.getDeployList(departmentPost,search,pageNumber,pageSize);
        return "success";
    }

    public String getPostListByDeptIds(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String deptIds = request.getParameter("deptIds") == null ? "" : request.getParameter("deptIds");
        pageForm = postService.getPostListByDeptIds(deptIds,pageNumber,pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String getNeedJoinStaffData(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        pageForm = postService.getNeedJoinStaffData(companyId,pageNumber,pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";

    }

    public String savePersonPostAllocation(){
        result = postService.savePersonPostAllocation(cos,departmentPost,entity);
        return "success";
    }

    public String getDepartmentPostById(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String postId = request.getParameter("postId");
        String type = request.getParameter("type");
        departmentPost = postService.getDepartmentPostById(postId,companyId);
        return type;
    }
   /**
     * 岗位职责——添加页面
     *
     * @return
     */
    public String toSaveDepartmentPost() {
        maxNum = postService.toSaveDepartmentPost();
        return "add";
    }
    /**
     * 查询指定岗位人员列表
     * @return
     */
    public String saveDepartmentPost(){
        result = postService.saveDepartmentPost( departmentPost,orgName);
        return "success";
    }

    /**
     * 查询指定岗位人员列表
     * @return
     */
    public String getListPerson(){
        pageForm = postService.getListPerson(pageNumber,pageSize,departmentPost, search);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }
    public String delPostDataById(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String postId = request.getParameter("postId");
        postService.delPostDataById(postId,companyId);
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

    public DepartmentPost getDepartmentPost() {
        return departmentPost;
    }

    public void setDepartmentPost(DepartmentPost departmentPost) {
        this.departmentPost = departmentPost;
    }

    public COS getCos() {
        return cos;
    }

    public void setCos(COS cos) {
        this.cos = cos;
    }

    public Audition getEntity() {
        return entity;
    }

    public void setEntity(Audition entity) {
        this.entity = entity;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

}
