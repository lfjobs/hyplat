package hy.ea.driving.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.driving.Identification;
import hy.ea.bo.human.vo.CStaffCos;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 教务处--科研--添加教师管理
 */
@Controller
@Scope("prototype")
public class AcademicAdminAction {
	@Resource
	private BaseBeanService baseBeanService;;
	@Resource
	private ServerService serverService;
	private String search;// 判断是否搜索人员
	private PageForm pageForm;
	private String result;
	private String staffID;
	private CStaffCos cos;
	private int pageNumber;
	
	
	
	
	/**
	 * 
	 * 
	 * 返回结果 公司在职员工 （专岗）
	 */
	public PageForm  getList(String result){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String sql = "select s.staffID,s.staffCode,s.recordCode,da.categoryName,s.staffName,o.organizationName,"
			+ " d.postName,s.usedNmae,s.sex,s.birthday,s.nationality,s.nativePlace,s.nation,s.staffIdentityCard,"
			+ " s.staffAddress,s.reference,s.referenceCode,s.referenceOrganization,s.verifyTime,s.politicsStatus,"
			+ " s.culturalDegree,s.marriage,s.health,s.bankNum,s.staffDesc,s.staffKey,s.address,s.photo"
			+ " from dtcos c"
			+ " left join dt_hr_deptpost d on c.depPostID = d.depPostID"
			+ " left join dtcorganization o on c.organizationID = o.organizationID"
			+ " right join dt_hr_staff s on c.staffID = s.staffID"
			+ " left join dtaudition da on c.staffID = da.staffID and da.companyID= ? and da.status = '22'"
			+ " where c.companyID = ? and c.cosStatus = '50' and c.status = '01'";
		if("result".equals(result)){
			sql+= " and exists (select d.staffID from DT_Identification d where d.staffID=c.staffID and  d.companyid=c.companyID and d.type='00')";
		}	
		List<Object> params = new ArrayList<Object>();
		params.add(account.getCompanyID());
		params.add(account.getCompanyID());
		
		if (search != null && search.equals("search")) {
			cos=(CStaffCos)session.get("tablesearch");
			if(cos.getStaffCode() != null
					&& !"".equals(cos.getStaffCode().trim())){
				sql += " and s.recordCode like ?";
				params.add("%" + cos.getStaffCode().trim() + "%");
			}
			if(cos.getStaffName() != null
					&& !"".equals(cos.getStaffName().trim())){
				sql += " and s.staffName like ?";
				params.add("%" + cos.getStaffName().trim() + "%");
			}
			if(cos.getStaffIdentityCard() != null
					&& !"".equals(cos.getStaffIdentityCard().trim())){
				sql += " and s.staffIdentityCard like ?";
				params.add("%" + cos.getStaffIdentityCard().trim() + "%");
			}
		}
		sql += " order by s.staffID desc";
		
		return pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), params.toArray()); 
	}
	
	/**
	 * ajax获取在职员工
	 */
	public String ajaxGetEmployees(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<BaseBean> employeesList=getList(null)!=null?getList(null).getList():null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("employeesList", employeesList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * 教师管理模糊查询
	 * 
	 * @return
	 */
	public String toSearchcompanyCStaff() {
		ActionContext.getContext().getSession().put("tablesearch", cos);
		return getCompanyListForIncumbent();
	}

	/**
	 * 教师管理汇总列表
	 * 
	 * @return
	 */
	public String getCompanyListForIncumbent() {
		pageForm=getList("result");
		return "academicadmin_list_teacher";
	}
	
	/**
	 * 
	 * 删除列表
	 */
	
	public  String delcompanyCStaff(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<String> hqls=new ArrayList<String>();
		List<Object[]> parmsList=new ArrayList<Object[]>();
		String hql=" delete from Identification where companyID=? and staffID=? and type=?";
		if (staffID != null && !"".equals(staffID)) {
			hqls.add(hql);
			parmsList.add(new Object[]{account.getCompanyID(),staffID,"00"});
			baseBeanService.executeHqlsByParamsList(null, hqls.toArray(new String[]{}), parmsList);
		}
		return getCompanyListForIncumbent();
	}
	/**
	 * 
	 * 教师管理列表 被调用
	 * 777777
	 */
	public String getCompanyListEmployeeReferral(){
		pageForm=getList(null);
		return "employeeReferral_list";
	}
	
	public String toSearchCompanyListEmployeeReferral(){
		ActionContext.getContext().getSession().put("tablesearch", cos);
		return getCompanyListEmployeeReferral();
	}
	/**
	 * 
	 * Ajax1从在职员工添加到教室管理
	 * @return
	 */
	public String getCompanybyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		int message=0;
		Identification tRelation=(Identification)baseBeanService.getBeanByHqlAndParams("from Identification where staffID=? and companyID=? and type=?", new Object[]{staffID,account.getCompanyID(),"00"});
		if(tRelation!=null){
			message=1;
		}else{
			Identification identific=new Identification();
			identific.setIdentificationid(serverService.getServerID("Identification"));
			identific.setCompanyid(account.getCompanyID());
			identific.setStaffID(staffID);
			identific.setType("00");
			baseBeanService.save(identific);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("co", message);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}



	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}	

	public CStaffCos getCos() {
		return cos;
	}

	public void setCos(CStaffCos cos) {
		this.cos = cos;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
