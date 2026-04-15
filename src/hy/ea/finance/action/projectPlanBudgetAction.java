package hy.ea.finance.action;
import hy.ea.bo.finance.Projectplanbudget;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateJsonValueProcessor;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class projectPlanBudgetAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	public InputStream excelStream;
	private PageForm pageForm;
	private int pageNumber;
	private String csbid;
	private String staffid;
	private Projectplanbudget projectplanbudget;
	private List<BaseBean> billlist;
	private String result;
	private String xmtypename;
	private String xmtype;
	private String jumptype;
	
	public String saveprojectPlanBudget(){
		String[] staffIDs= projectplanbudget.getStaffid().split(",");
		HttpSession session = ServletActionContext.getRequest()
				.getSession();
		String groupcompanysn = (String) session.getAttribute("groupCompanySn");
		if (projectplanbudget.getProjectplanbudgetid() == null || "".equals(projectplanbudget.getProjectplanbudgetid())) {
			if(staffIDs.length>=1){
				
				for(int i = 0 ;i<staffIDs.length;i++){
					List<BaseBean> List =new ArrayList<BaseBean>();
					String[] array = staffIDs[i].split("-");
					projectplanbudget.setProjectplanbudgetid(serverService.getServerID("ProjectPlanBudget"));
					projectplanbudget.setStaffid(array[2]);
					projectplanbudget.setGroupcompanysn(groupcompanysn);   
					projectplanbudget.setCompanyid(array[0]);
					projectplanbudget.setOrganizationid(array[1]);
					List.add(projectplanbudget);
					baseBeanService.saveBeansListAndexecuteHqlsByParams(List, null, null);
				}
			}
		}	
		return "success";
	}
	
	
	//公司项目工作计划预算获得列表
	public String getprojectPlanbudget(){
		String sql = "select budget.csbid,budget.staffid,budget.projectname,com.companyname ,org.organizationname,staff.staffname,budget.projectplanbudgetid,projectm.content,projectm.startDate,projectm.endDate from"+
" dtprojectplanbudget budget left join dtcompany com on budget.companyid = com.companyid "+
"left join dt_projectmanage projectm on budget.csbid = projectm.proID "+
"left join dt_hr_staff staff on staff.staffid =budget.staffid  left join dtCOrganization org on org.organizationid = budget.organizationid";
		//projectbudget = baseBeanService.getListBeanBySqlAndParams(sql, null);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql,"select count(*) "
						+ sql.substring(sql.indexOf("from")),null);
		return "PlanbudgetList";
	}   
	
	//项目预算单据查看详情
	@SuppressWarnings("unchecked")
	public String seedetail(){
	String sql = "select company.companyname,org.organizationname, projectm.staffname,projectm.projectname,projectm.content,"+
"to_char(projectm.startDate,'YYYY-MM-DD'),to_char(projectm.endDate,'YYYY-MM-DD'),to_char(projectm.createDate,'YYYY-MM-DD'), to_char(projectm.updateDate,'YYYY-MM-DD')," +
"projectm.xmtypename,projectm.staffName,projectm.content,projectm.startDate,projectm.endDate,projectm.projectCode,to_char(projectm.updateDate,'YYYY-MM-DD')" +
" from dtprojectplanbudget budget left join  dt_projectmanage projectm on budget.csbid = projectm.proid left join "+
"dtcompany company on  company.companyid =budget.companyid  left join dtcorganization org on org.organizationid ="+
"budget.organizationid left join dt_hr_staff staff on staff.staffid = budget.staffid where budget.csbid = ?  and budget.staffid = ? ";
		//String hql  = "from CostSheetBill where csbID = ?";
		 billlist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{csbid,staffid});
		//List<BaseBean> billlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{csbid});
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("billlist", billlist);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "detailInfo";
	}
	public Projectplanbudget getProjectplanbudget() {
		return projectplanbudget;
	}
	public void setProjectplanbudget(Projectplanbudget projectplanbudget) {
		this.projectplanbudget = projectplanbudget;
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


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getCsbid() {
		return csbid;
	}


	public void setCsbid(String csbid) {
		this.csbid = csbid;
	}


	public String getStaffid() {
		return staffid;
	}


	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}


	public String getXmtypename() {
		return xmtypename;
	}


	public void setXmtypename(String xmtypename) {
		this.xmtypename = xmtypename;
	}


	public String getXmtype() {
		return xmtype;
	}


	public void setXmtype(String xmtype) {
		this.xmtype = xmtype;
	}


	public String getJumptype() {
		return jumptype;
	}


	public void setJumptype(String jumptype) {
		this.jumptype = jumptype;
	}


	public List<BaseBean> getBilllist() {
		return billlist;
	}


	public void setBilllist(List<BaseBean> billlist) {
		this.billlist = billlist;
	}






	
}
