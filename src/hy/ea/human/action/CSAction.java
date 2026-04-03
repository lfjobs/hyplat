package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.CS;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

/**
 * 社会单位与社会人员关系表
 * 
 * @author zgzg
 * 
 */
@Controller
@Scope("prototype")
public class CSAction {
	@Resource
	private ServerService serverService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	private List<BaseBean> beans;
	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private Staff staff;
	private String companyID;
	private String companyName;

	/**
	 * 需要删除的人员id staffID
	 */
	private String personID;

	private String search;
	/**
	 * 被添加人员的集合
	 */
	private Map<String, Staff> staffmap;
	private String verifyTime;//录入时间

	/**
	 * 查询社会人员以供添加使用
	 * 
	 * @return
	 */
	public String getStaffList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from Staff s where 1=1 ";
		Object[] parm = null;
		if (staff != null) {
			if (staff.getStaffName() != null
					&& !"".equals(staff.getStaffName().trim())) {
				hql += " and s.staffName like '%"+staff.getStaffName().trim()+"%'";
			}
			if (staff.getStaffIdentityCard() != null
					&& !"".equals(staff.getStaffIdentityCard().trim())) {
				hql += " and s.staffIdentityCard = '"+staff.getStaffIdentityCard().trim()+"'";
			}
			if (verifyTime != null
					&& !"".equals(verifyTime)) {
				hql += " and s.verifyTime = ? ";
				parm= new Object[]{Utilities.getDateFromString(verifyTime, "yyyy-MM-dd")};
			}
		}
		hql += " and s.staffStatus = '00' and s.staffID not in (select cs.staffID from CS cs where cs.companyID = '"+companyID+"' )";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),hql,parm);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JsonConfig jsc =new JsonConfig();
		jsc.registerJsonValueProcessor(java.util.Date.class,new JsonDateValueProcessor());
		JSONObject obj = JSONObject.fromObject(map,jsc);
		result = obj.toString();
		return "success";
	}

	/**
	 * 获取社会单位下的人员
	 * 
	 * @return
	 */
	public String getPerson() {
		String hql = " from Staff sta, CS cs1 where cs1.staffID = sta.staffID and cs1.companyID = ? ";
		if (search != null && search.equals("search")) {
			staff = (Staff) ActionContext.getContext().getSession().get(
			"tablesearch");
			if(staff.getStaffName()!=null&&!staff.getStaffName().trim().equals("")){
				hql +=" and sta.staffName like '%" + staff.getStaffName().trim()+ "%'";
			}
			if(staff.getStaffIdentityCard()!=null&&!staff.getStaffIdentityCard().trim().equals("")){
				hql +=" and sta.staffIdentityCard = '" + staff.getStaffIdentityCard().trim()+ "'";
			}
		}
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 5 : pageNumber),
						"select sta.staffID,sta.recordCode,sta.staffName,sta.sex,sta.staffIdentityCard,sta.verifyTime "+hql,
						"select count(sta) "+hql,
						new Object[] { companyID });
		return "list";
	}

	/**
	 * 条件查询
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", staff);
		return getPerson();
	}

	/**
	 * 添加社会人员
	 * 
	 * @return
	 */
	public String addPerson() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		CS entity = null;
		if (staffmap.size() > 0) {
			beans = new ArrayList<BaseBean>();
			for (Staff st : staffmap.values()) {
				if (st != null) {
					entity = new CS();
					entity.setCsID(serverService.getServerID("cs"));
					entity.setCompanyID(companyID);
					entity.setStaffID(st.getStaffID());
					beans.add(entity);
				}
			}
		}
		//System.out.println(staffmap.size());
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "社会单位("
				+ companyName + ")添加了" + staffmap.size() + "个成员", account);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return getPerson();
	}

	/**
	 * 删除社会人员
	 * 
	 * @return
	 */
	public String delPerson() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hql = "delete CS where staffID = ? ";
		String hqlForStaff = "from Staff where staffID = ? ";
		Staff entity = (Staff) baseBeanService.getBeanByHqlAndParams(
				hqlForStaff, new Object[] { personID });
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除社会单位("
				+ companyName + ")下的社会人员(人员名称：" + entity.getStaffName() + ")",
				account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, new Object[] { personID });
		return "success";
	}

	/**
	 * 导出
	 * 
	 * @return
	 */
	public String showExcel() {
		List<BaseBean> list;
		Object[] params = { companyID,companyID };
		String hql = " select (select c.companyName from ContactCompany c where c.ccompanyID = ?),sta.recordCode,sta.staffName,sta.sex,sta.staffIdentityCard,sta.verifyTime from Staff sta, CS cs1 where cs1.staffID = sta.staffID and cs1.companyID = ?  ";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(new String[] { "序号","单位名称","档案编号",
				"人员姓名", "性别", "身份证", " 录入时间" }, list);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出社会人员", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
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

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Map<String, Staff> getStaffmap() {
		return staffmap;
	}

	public void setStaffmap(Map<String, Staff> staffmap) {
		this.staffmap = staffmap;
	}

	public String getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

}
