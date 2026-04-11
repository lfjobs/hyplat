package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.base.action.BaseAction;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.Audition;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COSDimissionStaff;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.CSP;
import hy.ea.bo.human.DepartmentPost;
import hy.ea.bo.human.PayScale;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.COSDimissionStaffVO;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.JsonConfigFactory;
import hy.plat.bo.BaseBean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 正式员工汇总（人事）yjg
 * 人员岗位调动
 * 员工离职
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class SOIncumbentAction extends BaseAction<Object>{
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private CompanyService companyService;
	
	private String parameter;
	private Company company;
	private Staff searchCStaff;// 保存人员搜索信息
	private String search;// 判断是否搜索人员
	private List<BaseBean> rosterlist;
	private String sub;
	private InputStream excelStream;
	private String staffID;
	private String organizations;
	private String deptposts;
	private List<BaseBean> comlist;
	private CStaffCos cos;
	private CSP csp;
	private String serachType;
	private COSDimissionStaff codimission; // 保存离职原因记录
	private List<BaseBean> beans;
	private String codeID;
	private String codeValue;
	private String aa;  //工作日志,岗位分配，月综合考评，基本信息
	/**
	 * 要设为专岗的兼岗ID
	 */
	private String oid;
	
	private DepartmentPost departmentPost;
	private String searchValue;  //查询传值
	private String companyName;
	private Audition audition;

	/*
	 * 获取被选中人员的部门列表
	 */
	public String getStaffListForPost() {
		if (this.getCurrentAccount() == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj;
			return "success";
		}
		List<BaseBean> resultList = new ArrayList<BaseBean>();
		String hql1 = " from CSP where companyID=? and staffID=?";
		Object[] params = { this.getCurrentAccount().getCompanyID(), staffID };
		csp = (CSP) baseBeanService.getBeanByHqlAndParams(hql1, params);
		/** ***查询专岗的部门（状态为01）的部门***** */
		String hqlFor = " from COrganization where organizationID in (select organizationID from COS where  companyID = ? and cosStatus = '50' and staffID=? and status = '01') ";
		List<BaseBean> temp = baseBeanService.getListBeanByHqlAndParams(hqlFor,
				params);
		if (temp.size() > 0) {
			/** ***查询兼岗岗的部门（状态为00）并添加到list中***** */
			String hql = " from COrganization where organizationID in (select organizationID from COS where  companyID = ? and cosStatus = '50' and staffID=? and status = '00') ";
			List<BaseBean> organizationlist = baseBeanService
					.getListBeanByHqlAndParams(hql, params);
			resultList = organizationlist;
			// 将唯一的专岗添加到list的索引为0的位置上
			resultList.add(0, temp.get(0));
		}
		/** ****职务级别查询**** */
		String hql2 = " from PayScale where companyID=? and status = '00' order by scale";
		List<BaseBean> paylist = baseBeanService.getListBeanByHqlAndParams(
				hql2, new Object[] { this.getCurrentAccount().getCompanyID() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationList", resultList);
		map.put("paylist", paylist);
		if(csp!=null){
			map.put("csp", csp);
		}
		JSONObject oj = new JSONObject();
		try {
			oj.putAll(map,JsonConfigFactory.getInstance());
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		result = oj;
		return "success";
	}

	/**
	 * 岗位变动
	 * 
	 * @return
	 */
	public String changeOrgByStaff() {
		if (this.getCurrentAccount() == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj;
			return "success";
		}
		List<BaseBean> resultList = new ArrayList<BaseBean>();
		List<BaseBean> postList = new ArrayList<BaseBean>();
		
		String hql1 = " from CSP where companyID=? and staffID=?";
		Object[] params = { this.getCurrentAccount().getCompanyID(), staffID };
		csp = (CSP) baseBeanService.getBeanByHqlAndParams(hql1, params);
		/** ***查询专岗的部门（状态为01）的部门***** */
		String hqlFor = " from COrganization where organizationID in (select organizationID from COS where companyID = ? and cosStatus = '50' and staffID = ? and status = '01') ";
		List<BaseBean> temp = baseBeanService.getListBeanByHqlAndParams(hqlFor,
				params);
		if (temp.size() > 0) {
			/** ***查询兼岗岗的部门（状态为00）并添加到list中***** */
			String hql = " from COrganization where organizationID in (select organizationID from COS where companyID = ? and cosStatus = '50' and staffID = ? and status = '00') ";
			List<BaseBean> organizationlist = baseBeanService
					.getListBeanByHqlAndParams(hql, params);
			resultList = organizationlist;
			// 将唯一的专岗添加到list的索引为0的位置上
			resultList.add(0, temp.get(0));
		}
		
		/** ***查询人员所在部门的专岗***** */
		String posthql = "from DepartmentPost where depPostID in (select depPostID from COS where companyID = ? and cosStatus = '50' and staffID = ? and status = '01') )";
		List<BaseBean> post = baseBeanService.getListBeanByHqlAndParams(posthql,
				params);
		if(post.size() > 0){
			/** ***查询人员所在部门的兼岗***** */
			String posthql2 = "from DepartmentPost where depPostID in (select depPostID from COS where companyID = ? and cosStatus = '50' and staffID = ? and status = '00') )";
			List<BaseBean> deptpostList = baseBeanService.getListBeanByHqlAndParams(posthql2,
					params);
			postList = deptpostList;
			postList.add(0, post.get(0));
		}
		
		String hql3=" from Audition where staffID=? and companyID=? and status=?";
		Audition audition = (Audition) baseBeanService.getBeanByHqlAndParams(hql3,
				new Object[] { staffID ,this.getCurrentAccount().getCompanyID(),"22"});
		/** ****职务级别查询**** */
		PayScale payscale=null;
		if(csp!=null){
			String hql2 = " from PayScale where payScaleID = ? ";
			payscale = (PayScale) baseBeanService.getBeanByHqlAndParams(
					hql2, new Object[] { csp.getPayScaleID() });
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffCategoryID", audition.getStaffCategoryID());
		map.put("organizationList", resultList);
		map.put("postList", postList);
		map.put("payscale", payscale);
		map.put("csp", csp);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 *  页面修改岗位及工种类别
	 * @return
	 */
	public String setOrgByStaff(){
		if (this.getCurrentAccount() == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj;
			return "success";
		}
		List<BaseBean> resultList = new ArrayList<BaseBean>();
		List<BaseBean> postList = new ArrayList<BaseBean>();
		
		String hql1 = " from CSP where companyID=? and staffID=?";
		Object[] params = { this.getCurrentAccount().getCompanyID(), staffID };
		csp = (CSP) baseBeanService.getBeanByHqlAndParams(hql1, params);
		/** ***查询专岗的部门（状态为01）的部门***** */
		String hqlFor = " from COrganization where organizationID in (select organizationID from COS where companyID = ? and cosStatus = '50' and staffID = ? and status = '01') ";
		List<BaseBean> temp = baseBeanService.getListBeanByHqlAndParams(hqlFor,
				params);
		if (temp.size() > 0) {
			/** ***查询兼岗岗的部门（状态为00）并添加到list中***** */
			String hql = " from COrganization where organizationID in (select organizationID from COS where companyID = ? and cosStatus = '50' and staffID = ? and status = '00') ";
			List<BaseBean> organizationlist = baseBeanService
					.getListBeanByHqlAndParams(hql, params);
			resultList = organizationlist;
			// 将唯一的专岗添加到list的索引为0的位置上
			resultList.add(0, temp.get(0));
		}
		
		/** ***查询人员所在部门的专岗***** */
		String posthql = "from DepartmentPost where depPostID in (select depPostID from COS where companyID = ? and cosStatus = '50' and staffID = ? and status = '01') )";
		List<BaseBean> post = baseBeanService.getListBeanByHqlAndParams(posthql,
				params);
		if(post.size() > 0){
			/** ***查询人员所在部门的兼岗***** */
			String posthql2 = "from DepartmentPost where depPostID in (select depPostID from COS where companyID = ? and cosStatus = '50' and staffID = ? and status = '00') )";
			List<BaseBean> deptpostList = baseBeanService.getListBeanByHqlAndParams(posthql2,
					params);
			postList = deptpostList;
			postList.add(0, post.get(0));
		}
		
		String hql3=" from Audition where staffID=? and companyID=? and status=?";
		Audition audition = (Audition) baseBeanService.getBeanByHqlAndParams(hql3,
				new Object[] { staffID ,this.getCurrentAccount().getCompanyID(),"22"});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffCategoryID", audition.getStaffCategoryID());
		map.put("organizationList", resultList);
		map.put("postList", postList);
		map.put("csp", csp);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		
		return "success";
	}
	
	/**
	 * 人员岗位调动保存
	 * 
	 * @return
	 */
	public String staffRedeployPost() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BaseBean> beans = new ArrayList<BaseBean>();
		List<String> hqls = new ArrayList<String>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String[] organization = organizations.split(",");
		String[] deptpost = deptposts.split(",");
		if (organization.length != 0) {
			hqls.add("delete from COS where companyID = ?  and staffID = ?");
			Object[] params = { account.getCompanyID(), staffID };
			for (int i = 0; i < organization.length; i++) {
				COS cos = new COS();
				if (i == 0) {
					cos.setStatus("01");
				} else {
					cos.setStatus("00");
				}
				if(!"".equals(organization[i])){
					cos.setOrganizationID(organization[i]);
					cos.setDepPostID(deptpost[i]);
					cos.setCompanyID(account.getCompanyID());
					cos.setStaffID(staffID);
					cos.setCosStatus("50");
					cos.setCosID(serverService.getServerID("cos"));
					beans.add(cos);
				}
			}
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { staffID });
			parameter = "岗位调动(人员名称:" + staff.getStaffName() + ")";
			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					account);
			String hql3=" from Audition where staffID=? and companyID=? and status=?";
			Audition audition = (Audition) baseBeanService.getBeanByHqlAndParams(hql3,
					new Object[] { staffID ,account.getCompanyID(),"22"});
			audition.setStaffCategoryID(codeID);
			audition.setCategoryName(codeValue);
			beans.add(audition);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,hqls.toArray(new String[]{}), params);
		}
		map.put("result", "success");
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}
	
	/**
	 * 查询人员岗位历史数据
	 * @return
	 */
/*	public String historyOfPost(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		String sql = "select s.staffID,s.staffCode,s.recordCode,s.staffName,o.organizationName,d.postName,"
			+ " s.usedNmae,s.sex,s.birthday,s.nationality,s.nativePlace,s.nation,s.staffIdentityCard,"
			+ " s.staffAddress,s.reference,s.referenceCode,s.referenceOrganization,s.verifyTime,"
			+ " s.politicsStatus,s.culturalDegree,s.marriage,s.health,s.bankNum,s.staffDesc"
			+ " from dtcos c"
			+ " left join dt_hr_deptpost d on c.depPostID = d.depPostID"
			+ " left join dtcorganization o on c.organizationID = o.organizationID"
			+ " left join dt_hr_staff s on c.staffID = s.staffID"
			+ " where c.companyID = ? and c.staffID = ? and c.cosStatus = '99'";
		Object[] param = {account.getCompanyID(), staffID};
		
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), param);
		return "to_postincumbent";
	}*/

	/**
	 * 员工离职
	 * 
	 * @return
	 */
	public String delStaffListForIncumbent() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String obj = (String) session.get("session_value");
		if ((sub != null && sub.equals(obj)) || sub.equals("1")) {
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { staffID });
			beans = new ArrayList<BaseBean>();
			codimission.setCompanyID(this.getCurrentAccount().getCompanyID());
			codimission.setStaffID(staffID);
			codimission.setDimissionStaffID(serverService.getServerID("csdID"));
			codimission.setDimissionStaffKey(serverService
					.getServerID("csdKey"));
			beans.add(codimission);
			// 在职员工中删除人员信息
			String hql_cos = "delete COS  where companyID = ?  and staffID = ? ";
			Object[] par1 = new Object[] { this.getCurrentAccount().getCompanyID(), staffID };
			// 将员工标记为离职(标记出问题，直接删除)
			String hql_audition = "delete Audition where companyID = ?  and staffID = ? ";
			Object[] par2 = new Object[] { this.getCurrentAccount().getCompanyID(),
					staffID };
			// 将职务级别记录标记为历史
			String hql_pshistory = "update PSHistory set status = ? where companyID = ? and staffID = ? ";
			Object[] par3 = new Object[] { "02", this.getCurrentAccount().getCompanyID(),
					staffID };
			//删除离职人员表中如果有当前离职人员。
			String hql_oldMan="delete COSDimissionStaff where companyID = ? and staffID=?";
			Object[] par4=new Object[]{this.getCurrentAccount().getCompanyID(),staffID};
			
			// 专岗、兼岗 人员数减一
			List<String> strL = new ArrayList<String>() ;
			List<DepartmentPost> depList = new ArrayList<DepartmentPost>();
			List<BaseBean> cosList = baseBeanService.getListBeanByHqlAndParams("from COS c where c.companyID = ? and c.staffID = ?", new Object[] { this.getCurrentAccount().getCompanyID(), staffID });
			for (int i = 0; i < cosList.size(); i++) {
				COS co = (COS)(cosList.get(i));
				
				DepartmentPost dp = (DepartmentPost)baseBeanService.getBeanByHqlAndParams("from DepartmentPost p where p.companyID = ? and p.depPostID = ?",  new Object[] { this.getCurrentAccount().getCompanyID(), co.getDepPostID()});
				
				if(dp != null){
					if(co.getStatus().equals("01")){	// 专岗
						
						if (dp .getAdminNum() == null || Integer.parseInt(dp.getAdminNum()) == 1) {
							dp .setAdminNum("0");
						} else if( Integer.parseInt(dp.getAdminNum()) > 1){
							int n = Integer.parseInt(dp.getAdminNum());
							n = n - 1;
							dp.setAdminNum(n + "");
						}
						if (dp.getSpecialpostNum() == null || Integer.parseInt(dp.getSpecialpostNum()) == 1) {
							dp.setSpecialpostNum("0");
						} else if(Integer.parseInt(dp.getSpecialpostNum()) > 1){
							int m = Integer.parseInt(dp.getSpecialpostNum());
							m = m - 1;
							dp.setSpecialpostNum(m + "");
						}
						depList.add(dp);
					}else if(co.getStatus().equals("00")){
						int x = 0;
						if(strL.contains(co.getDepPostID())){
							continue;
						}
						for (int j = 0; j < cosList.size(); j++) {
							COS coo = (COS)(cosList.get(j));
							if(coo.getStatus().equals("00") && coo.getDepPostID().equals(co.getDepPostID())){
								x++;
							}
							
						}
						
						strL.add(co.getDepPostID());
						
						if (dp .getAdminNum() == null || Integer.parseInt(dp.getAdminNum()) == x) {
							dp .setAdminNum("0");
						} else if( Integer.parseInt(dp.getAdminNum()) > x){
							int n = Integer.parseInt(dp.getAdminNum());
							n = n - x;
							dp.setAdminNum(n + "");
						}
						if (dp.getOmppostNum() == null || Integer.parseInt(dp.getOmppostNum()) == x) {
							dp.setOmppostNum("0");
						} else if(Integer.parseInt(dp.getOmppostNum()) > x){
							int m = Integer.parseInt(dp.getOmppostNum());
							m = m - x;
							dp.setOmppostNum(m + "");
						}
						depList.add(dp);
					}
				}
			}
			List<Integer> num = new ArrayList<Integer>(); 
			if(depList.size()>0){
				for (int i = 0; i < depList.size(); i++) {
					for (int j = 0; j < depList.size(); j++) {
						if(depList.get(i).getDepPostID().equals(depList.get(j).getDepPostID()) && j!=i){
							num.add(i);
							num.add(j);
							break;
						}
					}
					if(num.size() > 0){
						break;
					}
				}
			}
			if(num.size() >0){
				DepartmentPost d1 = depList.get(num.get(0));
				DepartmentPost d2 = depList.get(num.get(1));
				// 辖员人数
				if( d1.getAdminNum() == null || Integer.parseInt(d1.getAdminNum()) == 1 || Integer.parseInt(d1.getAdminNum()) == 0){
					d1.setAdminNum("0");
				}else{
					int n = Integer.parseInt(d1.getAdminNum());
					n = n - 1;
					d1.setAdminNum(n + "");
				}
				// 专岗人数
				if(d1.getSpecialpostNum() == null || Integer.parseInt(d1.getSpecialpostNum()) == 0){
					d1.setSpecialpostNum("0");
				}else{
					d1.setSpecialpostNum(Integer.parseInt(d1.getSpecialpostNum()) < Integer.parseInt(d2.getSpecialpostNum()) ? d1.getSpecialpostNum() : d2.getSpecialpostNum());
				}
				// 兼岗人数
				if(d1.getOmppostNum() == null || Integer.parseInt(d1.getOmppostNum()) == 0){
					d1.setOmppostNum("0");
				}else{
					d1.setOmppostNum(Integer.parseInt(d1.getOmppostNum()) < Integer.parseInt(d2.getOmppostNum()) ? d1.getOmppostNum() : d2.getOmppostNum());
				}
				depList.add(d1);
			}
			
			for (int i = 0; i < depList.size(); i++) {
				if(num.size()>0){
					if(i == num.get(0) || i == num.get(1)){
						continue;
					}
				}	
				beans.add(depList.get(i));
			}
			
			String[] hqls = new String[] { hql_cos, hql_audition, hql_pshistory,hql_oldMan };
			List<Object[]> parms = new ArrayList<Object[]>();
			parms.add(par1);
			parms.add(par2);
			parms.add(par3);
			parms.add(par4);
			CLogBook logBook = logBookService.saveCLogBook(null, "员工离职(人员名称："
					+ staff.getStaffName() + ")", this.getCurrentAccount());
			beans.add(logBook);
			baseBeanService.executeHqlsByParamsList(beans, hqls, parms);
		}
		DetachedCriteria dc = DetachedCriteria
				.forClass(COSDimissionStaffVO.class);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
		return "dimission_list";
	}

	/**
	 * 人员调离
	 * 
	 * @return
	 */
	public String changeStaffPost() {
		Map<String, Object> map = new HashMap<String, Object>();
		beans = new ArrayList<BaseBean>();
		String hql = "from COS  where companyID = ?  and staffID = ? and organizationID = ? and cosStatus = ? ";
		Object[] params = { this.getCurrentAccount(), staffID, this.getOrganizationId(),
				"50" };
		COS co = (COS) baseBeanService.getBeanByHqlAndParams(hql, params);
		if (co != null && co.getStatus().equals("00")) {
			co.setCosStatus("98");
			beans.add(co);

			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { staffID });
			String hqlForOrg = "from COrganization where organizationID=?";
			COrganization og = (COrganization) baseBeanService
					.getBeanByHqlAndParams(hqlForOrg,
							new Object[] { this.getOrganizationId() });
			parameter = "人员从" + og.getOrganizationName() + "调离(人员名称:"
					+ staff.getStaffName() + ")";
			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					 this.getCurrentAccount());
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
			map.put("result", "success");
		} else {

			map.put("result", "changejsp");
		}
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}

	/**
	 * 设专岗方法
	 * 
	 * @return
	 */
	public String changePost() {
		String[] organization = organizations.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "faild");
		if (organization.length != 0) {
			String hql = "delete from COS where companyID = ?  and staffID = ?";
			Object[] params = {  this.getCurrentAccount().getCompanyID(), staffID };

			beans = new ArrayList<BaseBean>();
			for (int i = 0; i < organization.length; i++) {
				COS cos = new COS();
				if (organization[i].equals(oid)) {
					cos.setStatus("01");
				} else {
					cos.setStatus("00");
				}
				cos.setCompanyID( this.getCurrentAccount().getCompanyID());
				cos.setStaffID(staffID);
				cos.setCosStatus("50");
				cos.setOrganizationID(organization[i]);
				cos.setCosID(serverService.getServerID("cos"));
				beans.add(cos);
			}
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { staffID });
			parameter = "岗位调动(人员名称:" + staff.getStaffName() + ")";
			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					 this.getCurrentAccount());
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
					new String[] { hql }, params);
			map.put("result", "success");
		}
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}

	/**
	 * 人事（生产）正式员工模糊查询
	 * 
	 * @return
	 */
	public String toSearchCStaff() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getStaffListForIncumbent();
	}

	
	/**
	 * 
	 * 在职员工工种转换
	 * 
	 */
	public String saveWorkIncumbent(){
		String  hql = " from Audition a where a.companyID = ? and a.staffID = ? and a.status = ?";
		baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ this.getCurrentAccount().getCompanyID(),audition.getStaffID(),"22"});
		
		 
		return getStaffListForIncumbent();
	}
	
	/**
	 * 人事（生产）正式员工管理列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getStaffListForIncumbent() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		Company company = companyService.getCompanyByCompanyID( this.getCurrentAccount().getCompanyID());
		companyName = company.getCompanyName();
	/*	if (search != null && search.equals("search")) {
			return searchStaff(account.getCompanyID());
		}
		
		String sql="select sx.*,da.categoryname from (select s.* from dt_hr_staff s where  exists (select t.staffID from dtcos  t where t.companyID = ?  and t.cosStatus = '50' and t.staffid = s.staffid)) sx  left join dtaudition da on da.staffid=sx.staffid and da.companyid=? and da.status='22' where 1=1";
		Object[] params = { account.getCompanyID(),account.getCompanyID() };
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), sql, "select count(1) "+sql.substring(sql.indexOf("from")), params);
	*/	
		/*String hql = " from Staff where  staffID in (select staffID from COS where companyID = ?  and cosStatus = '50' ) order by staffID desc ";
		Object[] params = { account.getCompanyID() };
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), hql,
				params);*/
		
		String sql = "select s.staffID,s.staffCode,s.recordCode,da.categoryName,s.staffName,o.organizationName,"
					+ " d.postName,s.usedNmae,s.sex,s.birthday,s.nationality,s.nativePlace,s.nation,s.staffIdentityCard,"
					+ " s.staffAddress,s.reference,s.referenceCode,s.referenceOrganization,s.verifyTime,s.politicsStatus,"
					+ " s.culturalDegree,s.marriage,s.health,s.bankNum,s.staffDesc,s.staffKey,s.address,s.photo,da.registerDate,da.becomesDate"
					+ " from dtcos c"
					+ " left join dt_hr_deptpost d on c.depPostID = d.depPostID"
					+ " left join dtcorganization o on c.organizationID = o.organizationID"
					+ " right join dt_hr_staff s on c.staffID = s.staffID"
					+ " left join dtaudition da on c.staffID = da.staffID "
					+ " where da.companyID= ? and da.status = '22' and c.companyID = ? and c.cosStatus = '50' and c.status = '01'";
		List<Object> params = new ArrayList<Object>();
		params.add( this.getCurrentAccount().getCompanyID());
		params.add( this.getCurrentAccount().getCompanyID());
		
		if (search != null && search.equals("search")) {
			if(searchValue != null && searchValue.equals("searchValue")){
				List<Object> list = searchValue(sql,params);
				sql = list.get(0).toString();
			}else{
				return searchStaff( this.getCurrentAccount().getCompanyID());
			}
		}
		sql += " order by s.staffID desc";
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), params.toArray()); 
		comlist = baseBeanService.getListBeanBySqlAndParams(sql, params.toArray());
		if("websuit".equals(serachType)){
			return "wensuitMinicontact";
		}
		return "to_incumbent";
	}
	
	/**
	 * 查询花名册汇总信息
	 */
	 @SuppressWarnings("unchecked")
	public String getrosterAll(){
		 Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
			Company company = companyService.getCompanyByCompanyID( this.getCurrentAccount().getCompanyID());
			companyName = company.getCompanyName();
			String sql = "select s.staffID,s.staffName,s.sex,o.organizationName,d.postName," +
					"s.reference,s.referenceOrganization,s.staffKey " +
					"from dtcos c  " +
					"left join dt_hr_deptpost d on c.depPostID = d.depPostID " +
					"left join dtcorganization o on c.organizationID = o.organizationID " +
					"right join dt_hr_staff s on c.staffID = s.staffID " +
					"left join dtaudition da on c.staffID = da.staffID where da.companyID= ?" +
					" and da.status = '22' and c.companyID = ? " +
					"and c.cosStatus = '50' and c.status = '01'";
		List<Object> params = new ArrayList<Object>();
		params.add( this.getCurrentAccount().getCompanyID());
		params.add( this.getCurrentAccount().getCompanyID());
		rosterlist = baseBeanService.getListBeanBySqlAndParams(sql, params.toArray());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rosterlist", rosterlist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	 }
	/**
	 * 列表/导出查询调用
	 * @param sql
	 * @param params
	 */
	private List<Object> searchValue(String sql,List<Object> params){
		List<Object> results = new ArrayList<Object>();
		searchCStaff = (Staff) ActionContext.getContext().getSession().get("tablesearch");
		if(searchCStaff.getStaffOrgName() != null
				&& !"".equals(searchCStaff.getStaffOrgName())){
			sql += " and c.organizationID like ?";
			params.add("%" + searchCStaff.getStaffOrgName() + "%");
		}
		if(searchCStaff.getStaffPost() != null
				&& !"".equals(searchCStaff.getStaffPost())){
			sql += " and c.depPostID like ?";
			params.add("%" + searchCStaff.getStaffPost() + "%");
		}
		if(searchCStaff.getStaffCode() != null
				&& !"".equals(searchCStaff.getStaffCode().trim())){
			sql += " and s.recordCode like ?";
			params.add("%" + searchCStaff.getStaffCode().trim() + "%");
		}
		if(searchCStaff.getStaffName() != null
				&& !"".equals(searchCStaff.getStaffName().trim())){
			sql += " and s.staffName like ?";
			params.add("%" + searchCStaff.getStaffName().trim() + "%");
		}
		if(searchCStaff.getStaffIdentityCard() != null
				&& !"".equals(searchCStaff.getStaffIdentityCard().trim())){
			sql += " and s.staffIdentityCard like ?";
			params.add("%" + searchCStaff.getStaffIdentityCard().trim() + "%");
		}
		results.add(sql);
		return results;
	}

	/**
	 * 按条件搜索正式员工并输出列表 被调用
	 * 
	 * @param companyID
	 * @return
	 */
	private String searchStaff(String companyID) {
		searchCStaff = (Staff) ActionContext.getContext().getSession().get(
				"tablesearch");
		List<String> arrys=new ArrayList<String>();
		arrys.add(companyID);
		arrys.add(companyID);
		String sql="select sx.*,da.categoryname from (select s.* from dt_hr_staff s where  exists (select t.staffID from dtcos  t where t.companyID = ?  and t.cosStatus = '50' and t.staffid = s.staffid)) sx  left join dtaudition da on da.staffid=sx.staffid and da.companyid=? and da.status='22' where 1=1";
		if (searchCStaff.getStaffCode() != null
				&& !"".equals(searchCStaff.getStaffCode())) {
			 sql+= " and sx.staffCode like '%" + searchCStaff.getStaffCode()+ "%'";
		}
		if (searchCStaff.getStaffName() != null
				&& !"".equals(searchCStaff.getStaffName())) {
			 sql+= " and sx.staffName like '%" + searchCStaff.getStaffName()+ "%'";
		}
		if (searchCStaff.getStaffIdentityCard() != null
				&& !"".equals(searchCStaff.getStaffIdentityCard())) {
			 sql+= " and sx.staffIdentityCard like '%" + searchCStaff.getStaffIdentityCard()+ "%'";
		}
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), sql, "select count(1) "+sql.substring(sql.indexOf("from")), arrys.toArray());
		return "to_incumbent";
	}

	/**
	 * 导出正式员工管理汇总
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showStaffExcelForIncumbent() {
	/*	if (search != null && search.equals("search")) {
			return getSearchInfoList(account.getCompanyID());
		}
		String sql="select sx.staffCode,sx.recordCode, (case when da.categoryname is null then '暂无分配' else da.categoryname end) categoryname," +
				"sx.staffName,sx.usedNmae,sx.sex,sx.birthday,sx.nativePlace,sx.nationality,sx.nation,sx.staffIdentityCard," +
				" sx.staffAddress,sx.reference,sx.referenceCode,sx.referenceOrganization,sx.verifyTime,sx.politicsStatus,sx.staffAddress,sx.culturalDegree," +
				" sx.marriage,sx.health,sx.bankNum from (select s.* from dt_hr_staff s where  exists (select t.staffID from dtcos  t where t.companyID = ?  and t.cosStatus = '50' and t.staffid = s.staffid)) sx  " +
				"left join dtaudition da on da.staffid=sx.staffid and da.companyid=? and da.status='22' where 1=1";
		Object[] params = { account.getCompanyID(),account.getCompanyID() };
		List<BaseBean> stafflist = baseBeanService.getListBeanBySqlAndParams(sql, params);
		excelStream = excelService.showExcel(Staff.columnHeadings0(), stafflist);
	*/	
		String sql = "select s.staffCode,s.recordCode,(case when da.categoryname is null then '暂无分配' else da.categoryname end) categoryname,"
			+ " s.staffName,o.organizationName,d.postName,s.usedNmae,s.sex,s.birthday,s.nationality,s.nativePlace,s.nation,s.staffIdentityCard,"
			+ " s.staffAddress,s.reference,s.referenceCode,s.referenceOrganization,s.verifyTime,s.politicsStatus,s.culturalDegree,s.marriage,"
			+ " s.health,s.bankNum,s.staffDesc"
			+ " from dtcos c"
			+ " left join dt_hr_deptpost d on c.depPostID = d.depPostID"
			+ " left join dtcorganization o on c.organizationID = o.organizationID"
			+ " right join dt_hr_staff s on c.staffID = s.staffID"
			+ " left join dtaudition da on c.staffID = da.staffID where da.companyID= ? and da.status = '22'"
			+ " and c.companyID = ? and c.cosStatus = '50' and c.status = '01'";
		List<Object> params = new ArrayList<Object>();
		params.add( this.getCurrentAccount().getCompanyID());
		params.add( this.getCurrentAccount().getCompanyID());
		if (search != null && search.equals("search")) {
			if(searchValue != null && searchValue.equals("searchValue")){
				List<Object> list = searchValue(sql,params);
				sql = list.get(0).toString();
			}else{
				return getSearchInfoList( this.getCurrentAccount().getCompanyID());
			}
		}
		sql	+= " order by s.staffID desc";
		List<BaseBean> stafflist = baseBeanService.getListBeanBySqlAndParams(sql, params.toArray());
		excelStream = excelService.showExcel(Staff.columnHeadings1(), stafflist); 
		
		CLogBook logBook = logBookService.saveCLogBook(null, "导出正式员工",  this.getCurrentAccount());
		baseBeanService.update(logBook);
		return "showexcel";
	}

	@SuppressWarnings("unchecked")
	private String getSearchInfoList(String companyID) {
		searchCStaff = (Staff) ActionContext.getContext().getSession().get(
				"tablesearch");
		List<String> arrys=new ArrayList<String>();
		arrys.add(companyID);
		arrys.add(companyID);
		String sql="select sx.staffCode,sx.recordCode, (case when da.categoryname is null then '暂无分配' else da.categoryname end) categoryname," +
				"sx.staffName,sx.usedNmae,sx.sex,sx.birthday,sx.nativePlace,sx.nationality,sx.nation,sx.staffIdentityCard," +
				" sx.staffAddress,sx.reference,sx.referenceCode,sx.referenceOrganization,sx.verifyTime,sx.politicsStatus,sx.staffAddress," +
				"sx.culturalDegree, sx.marriage,sx.health,sx.bankNum " +
				"from (select s.* from dt_hr_staff s where  exists (select t.staffID from dtcos  t where t.companyID = ?  and t.cosStatus = '50' and t.staffid = s.staffid)) sx   left join dtaudition da on da.staffid=sx.staffid and da.companyid=? and da.status='22' where 1=1";
		if (searchCStaff.getStaffCode() != null
				&& !"".equals(searchCStaff.getStaffCode())) {
			 sql+= " and sx.staffCode like '%" + searchCStaff.getStaffCode()+ "%'";
		}
		if (searchCStaff.getStaffName() != null
				&& !"".equals(searchCStaff.getStaffName())) {
			 sql+= " and sx.staffName like '%" + searchCStaff.getStaffName()+ "%'";
		}
		if (searchCStaff.getStaffIdentityCard() != null
				&& !"".equals(searchCStaff.getStaffIdentityCard())) {
			 sql+= " and sx.staffIdentityCard like '%" + searchCStaff.getStaffIdentityCard()+ "%'";
		}
		List<BaseBean> stafflist = baseBeanService.getListBeanBySqlAndParams(sql, arrys.toArray());
		excelStream = excelService.showExcel(Staff.columnHeadings0(), stafflist);
		return "showexcel";
	}

	// ////////****************************公司正式员工汇总************************************/
	/**
	 * 公司正式员工汇总模糊查询
	 * 
	 * @return
	 */
	public String toSearchcompanyCStaff() {
		ActionContext.getContext().getSession().put("tablesearch", cos);
		return getCompanyListForIncumbent();
	}

	/**
	 * 公司正式员工汇总列表
	 * 
	 * @return
	 */
	public String getCompanyListForIncumbent() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (search != null && search.equals("search")) {
			return searchcompanyStaff();
		}
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs((String) session.get("companyID"));
		DetachedCriteria dcc = DetachedCriteria.forClass(CStaffCos.class);
		dcc.add(Restrictions.in("companyID", cids));
		dcc.addOrder(Order.asc("companyID"));
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), dcc);
		return "to_companyincumbent";
	}

	/**
	 * 按条件搜索正式员工并输出列表 被调用
	 * 
	 * @param companyID
	 * @return
	 */
	private String searchcompanyStaff() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs((String) session.get("companyID"));
		cos = (CStaffCos) ActionContext.getContext().getSession().get(
				"tablesearch");
		DetachedCriteria dcc = DetachedCriteria.forClass(CStaffCos.class);
		if (!cos.getCompanyID().equals("")) {
			dcc.add(Restrictions.eq("companyID", cos.getCompanyID()));
		} else {
			dcc.add(Restrictions.in("companyID", cids));
		}
		if (!cos.getStaffCode().equals(""))
			dcc.add(Restrictions.like("staffCode", cos.getStaffCode(),
					MatchMode.ANYWHERE));
		if (!cos.getStaffName().equals(""))
			dcc.add(Restrictions.like("staffName", cos.getStaffName(),
					MatchMode.ANYWHERE));
		if (!cos.getStaffIdentityCard().equals(""))
			dcc.add(Restrictions.like("staffIdentityCard", cos
					.getStaffIdentityCard(), MatchMode.ANYWHERE));
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), dcc);
		return "to_companyincumbent";
	}

	/**
	 * 导出公司正式员工汇总
	 * 
	 * @return
	 */
	public String showcompanyExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (search != null && search.equals("search")) {
			return getSearchcompany();
		}
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs((String) session.get("companyID"));
		DetachedCriteria dcc = DetachedCriteria.forClass(CStaffCos.class);
		dcc.add(Restrictions.in("companyID", cids));
		dcc.addOrder(Order.asc("companyID"));
		List<BaseBean> stafflists = baseBeanService.getListByDC(dcc);
		excelStream = excelService.showExcel(CStaffCos.columnHeadings(),
				stafflists);
		CAccount account = (CAccount) session.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出公司正式员工",
				account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	private String getSearchcompany() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs((String) session.get("companyID"));
		cos = (CStaffCos) ActionContext.getContext().getSession().get(
				"tablesearch");
		DetachedCriteria dcc = DetachedCriteria.forClass(CStaffCos.class);
		if (!cos.getCompanyID().equals("")) {
			dcc.add(Restrictions.eq("companyID", cos.getCompanyID()));
		} else {
			dcc.add(Restrictions.in("companyID", cids));
		}
		if (!cos.getStaffCode().equals(""))
			dcc.add(Restrictions.like("staffCode", cos.getStaffCode(),
					MatchMode.ANYWHERE));
		if (!cos.getStaffName().equals(""))
			dcc.add(Restrictions.like("staffName", cos.getStaffName(),
					MatchMode.ANYWHERE));
		if (!cos.getStaffIdentityCard().equals(""))
			dcc.add(Restrictions.like("staffIdentityCard", cos
					.getStaffIdentityCard(), MatchMode.ANYWHERE));
		dcc.addOrder(Order.asc("companyID"));
		List<BaseBean> stafflists = baseBeanService.getListByDC(dcc);
		excelStream = excelService.showExcel(CStaffCos.columnHeadings(),
				stafflists);
		return "showexcel";
	}

	/**
	 * 员工详细信息打印
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getBasicInformation() {
		String staffID = request.getParameter("staffID");// staffid-cstaff20110307JVS37RIIDJ0000000023
		String sqlBasic = " select sa.recordcode,sa.staffName,sa.usednmae,sa.birthday,sa.nativePlace,"
				+ "sa.nation,sa.staffIdentityCard,sa.staffAddress,sa.Photo,"
				+ "sa.reference,sa.referenceorganization,sa.referencecode"
				+ " from dt_hr_staff sa where sa.staffid = ?";
		List<BaseBean> listBasic = baseBeanService.getListBeanBySqlAndParams(
				sqlBasic, new Object[] { staffID });
		request.setAttribute("basic", listBasic);
		//地址
		String sql2 = " select ad.addressdetailed from dt_hr_staff_address ad "
				+ " where ad.addresskey = (select  max(t.addresskey) "
				+ "from dt_hr_staff_address t where t.staffid = ?)";
		List<BaseBean> data2 = baseBeanService.getListBeanBySqlAndParams(sql2,
				new Object[] { staffID });
		request.setAttribute("address", data2);
		//学历
		String degreeSql = "select se.educationStart,se.educationEnd,se.educationName,cc.codeValue,cc1.codeValue codeValue1,cc2.codeValue codeValue2,cc3.codeValue codeValue3,se.provePerson ";
		degreeSql += " from dt_hr_staff_education se left join dt_hr_staff sa on sa.staffid=se.staffid left join dtccode cc on cc.codeid=se.majortype";
		degreeSql += " left join dtccode cc1 on cc1.codeid=se.educationHistory left join dtccode cc2 on cc2.codeid=se.educationStyle left join dtccode cc3 on cc3.codeid=se.educationType";
		degreeSql += " where cc.companyID=? and cc.codeStatus='00' and cc1.companyID=? and cc1.codeStatus='00' and cc2.companyID=? and cc2.codeStatus='00'";
		degreeSql += " and cc3.companyID=? and cc3.codeStatus='00' and sa.staffid=?";
		List<BaseBean> listDegree = baseBeanService.getListBeanBySqlAndParams(
				degreeSql,
				new Object[] {this.getCurrentAccount().getCompanyID(),this.getCurrentAccount().getCompanyID(),
						this.getCurrentAccount().getCompanyID(), this.getCurrentAccount().getCompanyID(), staffID });
		request.setAttribute("listDegree", listDegree);
		//个人履历
		String resumeSql = " select	se.startTime, se.endTime,se.companyName,se.position,se.duties,se.postName,se.reference";
		resumeSql += " from dt_hr_staff_Resume se where se.staffid=? ";
		List<BaseBean> listResume = baseBeanService.getListBeanBySqlAndParams(
				resumeSql, new Object[] { staffID });
		request.setAttribute("listResume", listResume);
		//家庭成员
		String sqlFamily = "select se.memberName,cc.codeValue, se.memberBirthDay,se.memberCompanyName,se.memberPosition,se.memberAddress,se.memberPhone,se.memberDesc";
		sqlFamily += " from dt_hr_staff_familymember se left join dt_hr_staff sa on se.staffid=sa.staffid left join dtccode cc on cc.codeid=se.memberRelationship";
		sqlFamily += "  where cc.companyID=? and cc.codeStatus='00' and sa.staffid=? ";
		List<BaseBean> listFamily = baseBeanService.getListBeanBySqlAndParams(
				sqlFamily, new Object[] {  this.getCurrentAccount().getCompanyID(),staffID});
		request.setAttribute("listFamily", listFamily);
		//体检
		String hqlHealth = "select to_char(se.examinationTime,'yyyy-mm-dd'), se.examinationHospital,cc.codeValue,se.conditionDesc,se.auditor,se.verifyTime ";
		hqlHealth += " from dt_hr_staff  sa,dt_hr_staff_PhysicalCondition se,dtccode cc where sa.staffID=se.staffID and se.details=cc.codeID and cc.companyID=? and cc.codeStatus=? and sa.staffid=?";
		List<BaseBean> listHealth = baseBeanService.getListBeanBySqlAndParams(
				hqlHealth, new Object[] {this.getCurrentAccount().getCompanyID(), "00", staffID });
		request.setAttribute("listHealth", listHealth);
		//政治
		String hqlPolitical = "select cc.codeValue, to_char(se.joinDate,'yyyy-mm-dd'),se.unit,se.introducer,to_char(se.probatePassDate,'yyyy-mm-dd'),se.partyStand ,se.auditor";
		hqlPolitical += " from dt_hr_staff  sa,dt_hr_staff_PoliticalStatus se,dtccode cc where sa.staffID=se.staffID and se.politicalStatus=cc.codeID and cc.companyID=? and cc.codeStatus=? and sa.staffid=?";
		List<BaseBean> listPolitical = baseBeanService
				.getListBeanBySqlAndParams(hqlPolitical, new Object[] {this.getCurrentAccount().getCompanyID(),
						"00", staffID });
		request.setAttribute("listPolitical", listPolitical);
		String sqlEncourage = "select cc.codeValue,se.encourageName,se.encourageReason,se.encourageDate,se.encourageOrgan,se.honoraryTitle,se.auditor";
		sqlEncourage += " from dt_hr_staff  sa,dt_hr_staff_Encourage se,dtccode cc where sa.staffID=se.staffID and se.encourageType=cc.codeID and cc.companyID=? and cc.codeStatus=? and sa.staffid=?";
		List<BaseBean> listEncourage = baseBeanService
				.getListBeanBySqlAndParams(sqlEncourage, new Object[] { this.getCurrentAccount().getCompanyID(),
						"00", staffID });
		request.setAttribute("listEncourage", listEncourage);
		String hqlPunishment = "select cc.codeValue,se.punishmentName,se.punishmentReason,to_char(se.punishmentDate,'yyyy-mm-dd'),se.punishmentOrgan,to_char(se.dischargeDate,'yyyy-mm-dd'),se.auditor ";
		hqlPunishment += " from dt_hr_staff  sa,dt_hr_staff_Punishment se,dtccode cc where sa.staffID=se.staffID and se.punishmentType=cc.codeID and cc.companyID=? and cc.codeStatus=? and sa.staffid=?";
		List<BaseBean> listPunishment = baseBeanService
				.getListBeanBySqlAndParams(hqlPunishment, new Object[] {this.getCurrentAccount().getCompanyID(),
						"00", staffID });
		request.setAttribute("listPunishment", listPunishment);
		String sqlInvestigation = "select to_char(se.investigationDate,'yyyy-mm-dd'),se.investigationItem,se.investigationPeroration,se.conductPrincipal,se.conductIdea,to_char(se.conductDate,'yyyy-mm-dd'),se.assessor ";
		sqlInvestigation += " from dt_hr_staff  sa,dt_hr_staff_Investigation se where sa.staffID=se.staffID and sa.staffid=?";
		List<BaseBean> listInvestigation = baseBeanService
				.getListBeanBySqlAndParams(sqlInvestigation, new Object[] {staffID });
		request.setAttribute("listInvestigation", listInvestigation);
		String sqlPersonalFile = "select se.recordCode,se.recordName,se.assessorDate,se.controlEndDate,se.recordBoxCode,se.dutyOfficer,se.recordBoxName ";
		sqlPersonalFile += " from dt_hr_staff  sa,dt_hr_staff_PersonalFile se where sa.staffID=se.staffID and sa.staffid=?";
		List<BaseBean> listPersonalFile = baseBeanService
				.getListBeanBySqlAndParams(sqlPersonalFile, new Object[] { staffID });
		request.setAttribute("listPersonalFile", listPersonalFile);
		String sqlCertificate = "select se.credentialsName,se.invalidateStart,se.invalidateEnd,se.organ ,se.credentialsNo,se.credentialsrecordNo,se.invalidate,se.auditorNumber,se.auditor";
		sqlCertificate += " from dt_hr_staff  sa,dt_hr_staff_Certificate se where  sa.staffID=se.staffID and sa.staffid=?";
		List<BaseBean> listCertificate = baseBeanService
				.getListBeanBySqlAndParams(sqlCertificate, new Object[] {staffID });
		request.setAttribute("listCertificate", listCertificate);
		return "information";
	}

	public Staff getSearchCStaff() {
		return searchCStaff;
	}

	public void setSearchCStaff(Staff searchCStaff) {
		this.searchCStaff = searchCStaff;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getOrganizations() {
		return organizations;
	}

	public void setOrganizations(String organizations) {
		this.organizations = organizations;
	}

	public String getDeptposts() {
		return deptposts;
	}

	public void setDeptposts(String deptposts) {
		this.deptposts = deptposts;
	}

	public List<BaseBean> getComlist() {
		return comlist;
	}

	public void setComlist(List<BaseBean> comlist) {
		this.comlist = comlist;
	}

	public CStaffCos getCos() {
		return cos;
	}

	public void setCos(CStaffCos cos) {
		this.cos = cos;
	}

	public CSP getCsp() {
		return csp;
	}

	public void setCsp(CSP csp) {
		this.csp = csp;
	}

	public COSDimissionStaff getCodimission() {
		return codimission;
	}

	public void setCodimission(COSDimissionStaff codimission) {
		this.codimission = codimission;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public DepartmentPost getDepartmentPost() {
		return departmentPost;
	}

	public void setDepartmentPost(DepartmentPost departmentPost) {
		this.departmentPost = departmentPost;
	}
	
	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getCodeID() {
		return codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAa() {
		return aa;
	}

	public void setAa(String aa) {
		this.aa = aa;
	}

	public List<BaseBean> getRosterlist() {
		return rosterlist;
	}

	public void setRosterlist(List<BaseBean> rosterlist) {
		this.rosterlist = rosterlist;
	}

	public Audition getAudition() {
		return audition;
	}

	public void setAudition(Audition audition) {
		this.audition = audition;
	}

	public String getSerachType() {
		return serachType;
	}

	public void setSerachType(String serachType) {
		this.serachType = serachType;
	}
	
}
