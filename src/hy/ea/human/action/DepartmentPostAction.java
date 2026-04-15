package hy.ea.human.action;

import com.opensymphony.xwork2.ActionContext;
import hy.base.action.BaseAction;
import hy.ea.bo.CAccount;
import hy.ea.bo.CDetail;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.*;
import hy.ea.bo.human.wage.PSHistory;
import hy.ea.bo.office.archives.DtArchivesArchives.ArchiveTemp;
import hy.plat.bo.BaseBean;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 部门岗位职责汇总
 * 
 * @author lwz
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class DepartmentPostAction extends BaseAction<Object>{
	private DepartmentPost departmentPost;
	private String parameter;
	private String search;

	private String maxNum; // 岗位编号传值
	private String orgName; // 所属部门传值
	private String organizationID;
	private Map<String, DepartmentPost> depPostMap;
	private String star;// 状态
	private String staffid; // 在职员工id
	private COS cos;// 在职员工岗位信息
	private PSHistory psh;// 员工级别
	private CSP csp;// 员工级别确定
	private Audition entity;
	private ArchiveTemp archiveTemp;// 档案
	private String contracttype;// 合同类型
	private Staff staff;
	private String starPer; // 01专岗 00兼岗 
	Map<String, String> oMap1 ; //公司 部门maplist
	
	/**
	 * 人员配备查询
	 * @return
	 */
	public String getDeployList(){
		String hql = "from DepartmentPost d where d.companyID = ? ";
		List<Object> parms = new ArrayList<Object>();
		parms.add(this.getCurrentAccount().getCompanyID());
		if(departmentPost.getOrganizationID().contains("company") == false){
			hql += " and d.organizationID = ?";
			parms.add(departmentPost.getOrganizationID());
		}
		if(search != null && search.equals("search")) {
			if (departmentPost.getPostNum() != null
					&& !"".equals(departmentPost.getPostNum())) {
				hql += " and d.postNum = ?";
				parms.add(departmentPost.getPostNum());
			}
			if (departmentPost.getPostName() != null
					&& !"".equals(departmentPost.getPostName().trim())) {
				hql += " and d.postName like ?";
				parms.add("%" + departmentPost.getPostName().trim() + "%");
			}
		}
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber),hql+" order by d.organizationID", parms.toArray());
		List<BaseBean> clist= baseBeanService.getListBeanByHqlAndParams("from Company c where  (c.companyID = ? or c.companyPID = ?) and c.companyStatus = ?",new Object[]{ this.getCurrentAccount().getCompanyID(),this.getCurrentAccount().getCompanyID(),"00"});
		oMap1 =new HashMap<String, String>();
		for (int i = 0; i < clist.size(); i++) {
			oMap1.put(((Company)clist.get(i)).getCompanyID(), ((Company)clist.get(i)).getCompanyName());
		}
		DepartmentPost.setCMap(oMap1);
		oMap1 =new HashMap<String, String>();
		List<BaseBean> olist= baseBeanService.getListBeanByHqlAndParams("from COrganization o where o.companyID = ?  ",new Object[]{ this.getCurrentAccount().getCompanyID()});
		for (int i = 0; i < olist.size(); i++) {
			oMap1.put(((COrganization)olist.get(i)).getOrganizationID(), ((COrganization)olist.get(i)).getOrganizationName());
		}
		DepartmentPost.setOMap(oMap1);
		return "getDeployList";
	}
	
	
	
	public String toSearchPer() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", staff);
		return getListPerson();
	}
	/**
	 * 查询指定岗位人员列表
	 * @return
	 */
	public String getListPerson(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<Object> list = getlistPer(session, this.getCurrentAccount());
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);
		return "getListPerson";
	}
	private List<Object> getlistPer(Map<String, Object> session, CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String sql = "select c.deppostid,d.postName,s.staffcode,s.staffname,c.status,s.sex,s.nativeplace,s.nationality," +
				" s.staffidentitycard,s.reference from dtCos c left join dt_hr_Staff s on" +
				" c.staffid = s.staffid left join dt_hr_deptpost d on c.deppostid = d.deppostid where ";
		sql += " c.companyid = ? and c.cosStatus = ? ";
		parms.add(companyID);
		parms.add("50");
		sql += " and c.depPostID = ?";
		parms.add(departmentPost.getDepPostID());
		if(search != null && search.equals("search")) {
			staff = (Staff) session.get("tablesearch");
			if (staff.getStaffName()!= null
					&& !staff.getStaffName().trim().equals("")) {
				sql += " and s.staffName like ? ";
				parms.add("%" + staff.getStaffName().trim() + "%");
			}
			if (staff.getStaffCode()!= null
					&& !staff.getStaffCode().trim().equals("")) {
				sql += " and s.staffCode like ? ";
				parms.add("%" + staff.getStaffCode().trim() + "%");
			}
			if (starPer!= null
					&& !starPer.equals("")) {
				sql += " and c.status = ? ";
				parms.add(starPer);
			}
		}
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}
	
	/**
	 * 在职员工分配岗位
	 * 
	 * @return
	 */
	public String orgPostEntry() throws Exception{
		try{
		Map<String, Object> map = new HashMap<String, Object>();
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hql_audition = "from Audition where  companyID = ? and staffID = ? ";
		Audition entity1 = (Audition) baseBeanService.getBeanByHqlAndParams(
				hql_audition, new Object[] { this.getCurrentAccount().getCompanyID(), staffid });
		parameter = "人员分配岗位：" + departmentPost.getPostName() + ", 所属部门："
				+ orgName;
		String hql1 = "from DepartmentPost where companyID = ? and depPostID = ? ";
		DepartmentPost deppt = (DepartmentPost) baseBeanService
				.getBeanByHqlAndParams(hql1, new Object[] {
						this.getCurrentAccount().getCompanyID(), departmentPost.getDepPostID() });
		if (cos.getStatus().equals("01")) {
			String hql = "from COS where companyID = ? and staffID = ? and status = ?";
			COS cosl = (COS) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { this.getCurrentAccount().getCompanyID(), staffid,
							cos.getStatus() });
			if (cosl != null) {
				map.put("vals", "专岗已设置,请勿重复！");
			} else {
				// 专岗
				cos.setCompanyID(this.getCurrentAccount().getCompanyID());
				cos.setCosID(serverService.getServerID("cos"));
				cos.setCosStatus("50");
				cos.setDepPostID(departmentPost.getDepPostID());
				cos.setOrganizationID(departmentPost.getOrganizationID());
				cos.setStaffID(staffid);
				beans.add(cos);
				// 员工级别确定
				CSP csp1 = new CSP();
				csp1.setPayScaleID(csp.getPayScaleID());
				csp1.setCompanyID(this.getCurrentAccount().getCompanyID());
				csp1.setStaffID(staffid);
				csp1.setCspID(serverService.getServerID("csp"));
				beans.add(csp1);
				// 级别变更记录表
				psh = new PSHistory();
				psh.setPayScaleID(csp.getPayScaleID());
				psh.setPshID(serverService.getServerID("psh"));
				psh.setCompanyID(this.getCurrentAccount().getCompanyID());
				psh.setEffectiveDate(entity1.getRegisterDate());
				psh.setStaffID(staffid);
				psh.setApplyDate(new Date());
				psh.setStatus("01");
				beans.add(psh);
				// 职员状态
				entity1.setStaffCategoryID(entity.getStaffCategoryID());
				entity1.setCategoryName(entity.getCategoryName());
				entity1.setStatus("22");
				beans.add(entity1);
				// 设置岗位加一
				if (deppt.getAdminNum() == null) {
					deppt.setAdminNum("1");
				} else {
					int i = Integer.parseInt(deppt.getAdminNum());
					i = i + 1;
					deppt.setAdminNum(i + "");
				}
				if (deppt.getSpecialpostNum() == null) {
					deppt.setSpecialpostNum("1");
				} else {
					int i = Integer.parseInt(deppt.getSpecialpostNum());
					i = i + 1;
					deppt.setSpecialpostNum(i + "");
				}
				
				CLogBook logbook = logBookService.saveCLogBook(this.getOrganizationId(),
						parameter, this.getCurrentAccount());
				beans.add(deppt);
				beans.add(logbook);
				
				//同步到个人履历
				StaffResume staffResume = new StaffResume();
				staffResume.setStaffID(staffid);
				staffResume.setRecordID(serverService.getServerID("record"));
				staffResume.setCcompanyID(this.getCurrentAccount().getCompanyID());
				staffResume.setStartTime(entity1.getRegisterDate());
				Company com = (Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object []{this.getCurrentAccount().getCompanyID()});
				staffResume.setCompanyName(com.getCompanyName());
				staffResume.setCompanyID(com.getCompanyID());
				staffResume.setPosition(deppt.getPostName());
				staffResume.setDuties(deppt.getResponsibilityRequire());
				staffResume.setReference(this.getCurrentAccount().getAccountName());
				staffResume.setPostName(deppt.getPostName());
				staffResume.setPostCase("scode20100423vw54xx7r4f0000000054"); //在职
				staffResume.setReferencePhon("专岗");
				CDetail cdet = (CDetail)baseBeanService.getBeanByHqlAndParams("from CDetail where companyID = ?", new Object []{this.getCurrentAccount().getCompanyID()});
				if(cdet!=null){
					staffResume.setPostAddress(cdet.getCompanyAddress());
				}
				beans.add(staffResume);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{"delete CSP where companyID=? and staffID=?"},new String[]{this.getCurrentAccount().getCompanyID(),staffid});
				map.put("vals", "设置专岗成功！");
	
			}

		} else if (cos.getStatus().equals("00")) {
			String hql = "from COS where companyID = ? and staffID = ? and status = ? and depPostID = ?";
			COS cosl = (COS) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { this.getCurrentAccount().getCompanyID(), staffid,
							cos.getStatus() ,departmentPost.getDepPostID()});
			if (cosl != null) {
				map.put("vals", "兼岗已设置,请勿重复！");
			}else{
			// 兼岗
			cos.setCompanyID(this.getCurrentAccount().getCompanyID());
			cos.setCosID(serverService.getServerID("cos"));
			cos.setCosStatus("50");
			cos.setDepPostID(departmentPost.getDepPostID());
			cos.setOrganizationID(departmentPost.getOrganizationID());
			cos.setStaffID(staffid);
			beans.add(cos);
			// 岗位加一
			if (deppt.getAdminNum() == null) {
				deppt.setAdminNum("1");
			} else {
				int i = Integer.parseInt(deppt.getAdminNum());
				deppt.setAdminNum((i + 1) + "");
			}
			if (deppt.getOmppostNum() == null) {
				deppt.setOmppostNum("1");
			} else {
				int i = Integer.parseInt(deppt.getOmppostNum());
				i = i + 1;
				deppt.setOmppostNum(i + "");
			}
			CLogBook logbook = logBookService.saveCLogBook(this.getOrganizationId(), parameter,
					this.getCurrentAccount());
			beans.add(logbook);
			beans.add(deppt);
			
			//同步到个人履历
			StaffResume staffResume = new StaffResume();
			staffResume.setStaffID(staffid);
			staffResume.setRecordID(serverService.getServerID("record"));
			staffResume.setCcompanyID(this.getCurrentAccount().getCompanyID());
			staffResume.setStartTime(new Date());
			Company com = (Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object []{this.getCurrentAccount().getCompanyID()});
			staffResume.setCompanyName(com.getCompanyName());
			staffResume.setCompanyID(com.getCompanyID());
			staffResume.setPosition(deppt.getPostName());
			staffResume.setDuties(deppt.getResponsibilityRequire());
			staffResume.setReference(this.getCurrentAccount().getAccountName());
			staffResume.setPostName(deppt.getPostName());
			staffResume.setPostCase("scode20100423vw54xx7r4f0000000054"); //在职
			staffResume.setReferencePhon("兼岗");
			CDetail cdet = (CDetail)baseBeanService.getBeanByHqlAndParams("from CDetail where companyID = ?", new Object []{this.getCurrentAccount().getCompanyID()});
			if(cdet!=null){
				staffResume.setPostAddress(cdet.getCompanyAddress());
			}
			beans.add(staffResume);
			
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
			map.put("vals", "设置兼岗成功！");
			}
		}

		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		}catch(Exception e){
			e.printStackTrace();
		}

		return "success";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String deleteOrgPost() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String orgID = (String) session.get("organizationID");
		String hql = "delete from DepartmentPost where depPostID = ?";

		List<BaseBean> beans = new ArrayList<BaseBean>();
		List<String> hqls = new ArrayList<String>();
		List<Object[]> pars = new ArrayList<Object[]>();
		parameter = "删除岗位：" + departmentPost.getPostName() + ", 岗位所属部门："
				+ orgName;
		CLogBook logbook = logBookService.saveCLogBook(orgID, parameter,
				account);
		Object[] param = { departmentPost.getDepPostID() };

		beans.add(logbook);
		hqls.add(hql);
		pars.add(param);
		baseBeanService.executeHqlsByParamsList(beans, hqls
				.toArray(new String[0]), pars);
		return "success";
	}

	/**
	 * 修改/保存
	 * 
	 * @return
	 */

	public String sevaeOrgPostMap() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String orgID = (String) session.get("organizationID");
		List<BaseBean> beans = new ArrayList<BaseBean>();

		if (depPostMap != null) {
			for (DepartmentPost depPost : depPostMap.values()) {
				if (!depPost.getDepPostID().equals("")) {
					parameter = "修改岗位：" + depPost.getPostName() + ", 岗位所属部门："
							+ orgName;
				} else {
					depPost.setDepPostID(serverService.getServerID("departmentPost"));
					depPost.setCompanyID(account.getCompanyID());
					if(departmentPost.getLeveloneOrgID().contains("company")){
						depPost.setOrganizationID(orgID);
					}else{
						depPost.setOrganizationID(departmentPost.getLeveloneOrgID());
					}
					if(depPost.getPostSureNum().equals("")){
						depPost.setPostSureNum("1");
					}
					depPost.setPostNum(getOrgPostMaxNum());
					depPost.setAdminNum("0");
					depPost.setSpecialpostNum("0");
					depPost.setOmppostNum("0");
					if(departmentPost.getLeveloneOrgID().contains("company")){
						depPost.setLeveloneOrgID(orgID);
					}else{
						depPost.setLeveloneOrgID(getLeveloneOrgID());
					}
					parameter = "添加岗位：" + depPost.getPostName() + ", 岗位所属部门："
							+ orgName;
				}
				beans.add(depPost);
				CLogBook logbook = logBookService.saveCLogBook(orgID,
						parameter, account);
				beans.add(logbook);
			}
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	/**
	 * 自动职务编号
	 * 
	 * @return
	 */
	private String getOrgPostMaxNum() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "select max(d.postNum) from DepartmentPost d where companyID = ?";
		
		Object b = baseBeanService.getObjectByHqlAndParams(hql,
				new Object[] { account.getCompanyID() });
		int m = Integer.parseInt(b.toString()) + 1 ;
		String maxcount = "00" + m;
		return maxcount;
	}

	
	/**
	 * 获取公司下第一级部门调用
	 * 
	 * @return
	 */
	private String getLeveloneOrgID() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from COrganization  where companyID = ? and organizationID = ?";
		COrganization cor = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] {
						account.getCompanyID(),
						departmentPost.getLeveloneOrgID() });

		while (!cor.getOrganizationPID().equals(account.getCompanyID())) {
			cor = (COrganization) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { account.getCompanyID(),
							cor.getOrganizationPID() });
		}
		return cor.getOrganizationID();
	}

	/**
	 * 获取指定部门职务列表
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getOrgPostListByOrg() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		List<Object> list = getlists(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);
		if (star != null) {
			if (star.equals("00")) {
				return "org_Entry";
			}
		}
		//跳转到机构树下部门岗位列表
		if(request.getParameter("ogName") != 
				null && !request.getParameter("ogName").equals("")){
			return "orgsList";
		}
		return "OrgPostListByOrg";
	}

	private List<Object> getlists(Map<String, Object> session, CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String sql = "select d.depPostID, d.postName,d.postNum,"
				+ " d.adminNum,d.postSureNum,d.postresponsibility,d.responsibilityrequire"
				+ " ,d.deppostkey,d.leveloneorgid,d.remark,d.companyID,d.organizationID,d.SpecialpostNum"
				+ " from dt_hr_deptpost d";

		sql += " where d.companyID=?";
		parms.add(companyID);
		if (departmentPost.getOrganizationID().contains("company") == false) {
			sql += " and d.organizationid =?";
			parms.add(departmentPost.getOrganizationID());
		}
		if (search != null && search.equals("search")) {
			if (departmentPost.getPostNum() != null
					&& !"".equals(departmentPost.getPostNum())) {
				sql += " and d.postNum = ?";
				parms.add(departmentPost.getPostNum());
			}
			if (departmentPost.getPostName() != null
					&& !"".equals(departmentPost.getPostName().trim())) {
				sql += " and d.postName like ?";
				parms.add("%" + departmentPost.getPostName().trim() + "%");
			}
		}
		sql += " order by d.leveloneOrgID,d.postnum desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}

	/**
	 * 前台页面获取部门下的所有岗位
	 * 
	 * @return
	 */
	public String getOrganizationPost() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from DepartmentPost where companyID = ? and organizationID = ?";
		Object[] params = { account.getCompanyID(),
				departmentPost.getOrganizationID() };
		List<BaseBean> departmentPostlist = baseBeanService
				.getListBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("departmentPostlist", departmentPostlist);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 前台页面判断岗位是否被使用
	 * 
	 * @return
	 */
	public String isUseOfPost() {
		String hql = "select count(staffID) from COS where depPostID = ?";
		int count = baseBeanService.getConutByByHqlAndParams(hql,
				new Object[] { departmentPost.getDepPostID() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 前台获取人员岗位（专岗）
	 * @return
	 */
	public String getStaffPost(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String posthql = "from DepartmentPost where depPostID in (select depPostID from COS where companyID = ? and staffID = ? and cosStatus = ? and status = ?) )";
		DepartmentPost post = (DepartmentPost)baseBeanService.getBeanByHqlAndParams(posthql,new Object[]{account.getCompanyID(),staffid,"50","01"});
		Map<String, Object> map = new HashMap<String, Object>();
		if(post == null){
			map.put("postname","");
		}else{
			map.put("postname",post.getPostName());
		}
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 岗位职责——查询
	 * 
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", departmentPost);
		return getDepartmentPostList();
	}

	/**
	 * 岗位职责——列表
	 * 
	 * @return
	 */
	public String getDepartmentPostList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getlist(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);

		return "list";
	}

	/**
	 * 岗位职责——查询列表（可根据条件查询）被调用
	 * 
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getlist(Map<String, Object> session, CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String sql = "select d.depPostID,c.companyname,cr.organizationname,"
				+ " d.postName,d.postNum,d.adminNum,d.postSureNum"
				+ " from dt_hr_deptpost d"
				+ " left join dtcompany c on  c.companyid = d.companyid"
				+ " left join dtcorganization cr on  cr.organizationid = d.organizationID";
		sql += " where d.companyID=?";
		parms.add(companyID);
		if (search != null && search.equals("search")) {
			departmentPost = (DepartmentPost) session.get("tablesearch");
			if (departmentPost.getOrganizationID() != null
					&& !"".equals(departmentPost.getOrganizationID())) {
				sql += " and d.organizationID = ?";
				parms.add(departmentPost.getOrganizationID());
			}
			if (departmentPost.getPostName() != null
					&& !"".equals(departmentPost.getPostName().trim())) {
				sql += " and d.postName like ?";
				parms.add("%" + departmentPost.getPostName().trim() + "%");
			}
		}
		sql += " order by d.leveloneOrgID,d.postnum desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}

	/**
	 * 岗位职责——添加页面
	 * 
	 * @return
	 */
	public String toSaveDepartmentPost() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "select count(postNum) from DepartmentPost where companyID = ?";
		int count = baseBeanService.getConutByByHqlAndParams(hql,
				new Object[] { account.getCompanyID() });
		if (count == 0) {
			maxNum = "0001";
		} else {
			String maxhql = "select max(postNum) from DepartmentPost where companyID = ?";
			int maxcount = baseBeanService.getConutByByHqlAndParams(maxhql,
					new Object[] { account.getCompanyID() });
			// 判断maxcount+1是否为4位数字，若不足前补0
			if (Integer.toString(maxcount + 1).length() > 3) {
				maxNum = Integer.toString(maxcount + 1);
			} else {
				DecimalFormat myformat = new DecimalFormat(); // 格式化对象的类
				myformat.applyPattern("0000"); // 修改格式模板
				maxNum = myformat.format(maxcount + 1); // 格式化数字
			}
		}
		return "add";
	}

	/**
	 * 岗位职责——修改页面
	 * 
	 * @return
	 */
	public String toEditDepartmentPost() {
		editOrSee();
		return "add";
	}

	/**
	 * 岗位职责——查看页面
	 * 
	 * @return
	 */
	public String toSeeDepartmentPost() {
		editOrSee();
		return "edit";
	}

	/**
	 * 岗位职责——修改/查看调用
	 */
	private void editOrSee() {
		String hql = "from DepartmentPost where depPostID = ?";
		departmentPost = (DepartmentPost) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { departmentPost
						.getDepPostID() });
	}

	/**
	 * 岗位职责——保存/修改
	 * 
	 * @return
	 */
	public String saveDepartmentPost() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		if (departmentPost.getDepPostID() == null
				|| "".equals(departmentPost.getDepPostID())) {
			departmentPost.setDepPostID(serverService
					.getServerID("departmentPost"));
			departmentPost.setCompanyID(account.getCompanyID());
			parameter = "添加岗位：" + departmentPost.getPostName() + ", 岗位所属部门："
					+ orgName;
		} else {
			parameter = "修改岗位：" + departmentPost.getPostName() + ", 岗位所属部门："
					+ orgName;
		}
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(departmentPost);
		String organizationID = (String) session.get("organizationID");
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	/**
	 * 岗位职责——删除
	 * 
	 * @return
	 */
	public String deleteDepatPost() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");

		List<BaseBean> beans = new ArrayList<BaseBean>();
		List<String> hqls = new ArrayList<String>();
		List<Object[]> pars = new ArrayList<Object[]>();
		parameter = "删除岗位：" + departmentPost.getPostName() + ", 岗位所属部门："
				+ orgName;
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		String hql = "delete from DepartmentPost where depPostID = ?";
		Object[] param = { departmentPost.getDepPostID() };

		beans.add(logbook);
		hqls.add(hql);
		pars.add(param);
		baseBeanService.executeHqlsByParamsList(beans, hqls
				.toArray(new String[0]), pars);
		return getDepartmentPostList();
	}

	public DepartmentPost getDepartmentPost() {
		return departmentPost;
	}

	public void setDepartmentPost(DepartmentPost departmentPost) {
		this.departmentPost = departmentPost;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
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

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public Map<String, DepartmentPost> getDepPostMap() {
		return depPostMap;
	}

	public void setDepPostMap(Map<String, DepartmentPost> depPostMap) {
		this.depPostMap = depPostMap;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public COS getCos() {
		return cos;
	}

	public void setCos(COS cos) {
		this.cos = cos;
	}

	public PSHistory getPsh() {
		return psh;
	}

	public void setPsh(PSHistory psh) {
		this.psh = psh;
	}

	public CSP getCsp() {
		return csp;
	}

	public void setCsp(CSP csp) {
		this.csp = csp;
	}

	public Audition getEntity() {
		return entity;
	}

	public void setEntity(Audition entity) {
		this.entity = entity;
	}

	public ArchiveTemp getArchiveTemp() {
		return archiveTemp;
	}

	public void setArchiveTemp(ArchiveTemp archiveTemp) {
		this.archiveTemp = archiveTemp;
	}

	public String getContracttype() {
		return contracttype;
	}

	public void setContracttype(String contracttype) {
		this.contracttype = contracttype;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public String getStarPer() {
		return starPer;
	}
	public void setStarPer(String starPer) {
		this.starPer = starPer;
	}

}
