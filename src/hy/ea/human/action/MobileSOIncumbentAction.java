package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COSDimissionStaff;
import hy.ea.bo.human.CSP;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.COSDimissionStaffVO;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

/*
 * 正式员工汇总（人事）yjg
 * 人员岗位调动
 * 员工离职
 */
@Controller
@Scope("prototype")
public class MobileSOIncumbentAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private Company company;
	private Staff searchCStaff;// 保存人员搜索信息
	private String search;// 判断是否搜索人员
	private PageForm pageForm;
	private String result;
	private String sub;
	private InputStream excelStream;
	private String staffID;
	private String organizations;
	private List<BaseBean> comlist;
	private CStaffCos cos;
	private int pageNumber;
	private CSP csp;
	private COSDimissionStaff codimission; // 保存离职原因记录
	private List<BaseBean> beans;
	/**
	 * 要设为专岗的兼岗ID
	 */
	private String oid;

	/*
	 * 获取被选中人员的部门列表
	 */
	public String getStaffListForPost() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<BaseBean> resultList = new ArrayList<BaseBean>();
		String hql1 = " from CSP where companyID=? and staffID=?";
		Object[] params = { account.getCompanyID(), staffID };
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
				hql2, new Object[] { account.getCompanyID() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationList", resultList);
		map.put("paylist", paylist);
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

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String[] organization = organizations.split(",");
		if (organization.length != 0) {
			String hql = "delete from COS where companyID = ?  and staffID = ?";
			Object[] params = { account.getCompanyID(), staffID };

			beans = new ArrayList<BaseBean>();
			// 保存工资级别
			if (null != csp.getPayScaleID() && !"".equals(csp.getPayScaleID())) {
				csp.setCompanyID(account.getCompanyID());
				csp.setStaffID(staffID);
				if (null == csp.getCspID() || "".equals(csp.getCspID())) {
					csp.setCspID(serverService.getServerID("csp"));
				}
				beans.add(csp);
			}
			for (int i = 0; i < organization.length; i++) {
				COS cos = new COS();
				if (i == 0) {
					cos.setStatus("01");
				} else {
					cos.setStatus("00");
				}
				cos.setCompanyID(account.getCompanyID());
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
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
					new String[] { hql }, params);
		}
		return getStaffListForIncumbent();
	}

	/**
	 * 员工离职
	 * 
	 * @return
	 */
	public String delStaffListForIncumbent() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String obj = (String) session.get("session_value");
		if ((sub != null && sub.equals(obj)) || sub.equals("1")) {
			CAccount account = (CAccount) session.get("account");
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { staffID });
			beans = new ArrayList<BaseBean>();
			codimission.setCompanyID(account.getCompanyID());
			codimission.setStaffID(staffID);
			codimission.setDimissionStaffID(serverService.getServerID("csdID"));
			codimission.setDimissionStaffKey(serverService
					.getServerID("csdKey"));
			beans.add(codimission);
			String hql = "delete COS  where companyID = ?  and staffID = ? ";
			String hqls = "update Audition set status='99' where companyID = ?  and staffID = ? ";
			Object[] params = { account.getCompanyID(), staffID };
			CLogBook logBook = logBookService.saveCLogBook(null, "员工离职(人员名称："
					+ staff.getStaffName() + ")", account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
					new String[] { hql, hqls }, params);
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
	 * 设专岗方法
	 * 
	 * @return
	 */
	public String changePost() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String[] organization = organizations.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "faild");
		if (organization.length != 0) {
			String hql = "delete from COS where companyID = ?  and staffID = ?";
			Object[] params = { account.getCompanyID(), staffID };

			beans = new ArrayList<BaseBean>();
			for (int i = 0; i < organization.length; i++) {
				COS cos = new COS();
				if (organization[i].equals(oid)) {
					cos.setStatus("01");
				} else {
					cos.setStatus("00");
				}
				cos.setCompanyID(account.getCompanyID());
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
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
					new String[] { hql }, params);
			map.put("result", "success");
		}
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
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
	 * 人事（生产）正式员工管理列表
	 * 
	 * @return
	 */
	public String getStaffListForIncumbent() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		CAccount account = (CAccount) session.get("account");
		if (search != null && search.equals("search")) {
			return searchStaff(account.getCompanyID());
		}
		String hql = " from Staff where  staffID in (select staffID from COS where companyID = ?  and cosStatus = '50' ) order by staffID desc ";
		Object[] params = { account.getCompanyID() };
		pageForm = baseBeanService.getPageForm((pageNumber), (1), hql, params);
		HttpServletResponse response = ServletActionContext.getResponse();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
		String outString = jsonArray.toString();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(outString);
			//System.out.println(outString);
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		// return "to_incumbent";
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
		String hql1 = "from Staff where staffID in (select staffID from COS where companyID = ?  and cosStatus = '50' ) and ";
		String hql2 = "  staffName like ? and staffIdentityCard like ?  order by staffID desc ";
		String hql = "";
		if (searchCStaff.getStaffCode() != null
				&& !"".equals(searchCStaff.getStaffCode())) {
			hql = hql1 + " staffCode like '%" + searchCStaff.getStaffCode()
					+ "%' and " + hql2;
		} else {
			hql = hql1 + hql2;
		}
		Object[] params = { companyID, "%" + searchCStaff.getStaffName() + "%",
				"%" + searchCStaff.getStaffIdentityCard() + "%" };
		pageForm = baseBeanService.getPageForm((pageNumber), (1), hql, params);
		HttpServletResponse response = ServletActionContext.getResponse();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
		String outString = jsonArray.toString();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(outString);
			//System.out.println(outString);
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		// return "to_incumbent";
	}

	/**
	 * 导出正式员工管理汇总
	 * 
	 * @return
	 */
	public String showStaffExcelForIncumbent() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		if (search != null && search.equals("search")) {
			return getSearchInfoList(account.getCompanyID());
		}
		String hql = " from Staff where  staffID in (select staffID from COS where companyID = ? and cosStatus = '50' ) order by staffID desc ";
		Object[] params = { account.getCompanyID() };
		List<BaseBean> stafflist = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		excelStream = excelService.showExcel(Staff.columnHeadings(), stafflist);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出正式员工", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	private String getSearchInfoList(String companyID) {
		searchCStaff = (Staff) ActionContext.getContext().getSession().get(
				"tablesearch");
		String hql1 = "from Staff where staffID in (select staffID from COS where companyID = ?  and cosStatus = '50' ) and ";
		String hql2 = "  staffName like ? and staffIdentityCard like ?  order by staffID desc ";
		String hql = "";
		if (searchCStaff.getStaffCode() != null
				&& !"".equals(searchCStaff.getStaffCode())) {
			hql = hql1 + " staffCode like '%" + searchCStaff.getStaffCode()
					+ "%' and " + hql2;
		} else {
			hql = hql1 + hql2;
		}
		Object[] params = { companyID, "%" + searchCStaff.getStaffName() + "%",
				"%" + searchCStaff.getStaffIdentityCard() + "%" };
		List<BaseBean> stafflist = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		excelStream = excelService.showExcel(Staff.columnHeadings(), stafflist);
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

	@SuppressWarnings("unchecked")
	public String getBasicInformation() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");// company201009046vxdyzy4wg0000000025
		HttpServletRequest request = ServletActionContext.getRequest();
		String staffID = request.getParameter("staffID");// staffid-cstaff20100910z2wswpqgbd0000000218
		String sqlBasic = " select sa.recordcode,sa.staffName,sa.usednmae,sa.birthday,sa.nativePlace,sa.nation,sa.staffIdentityCard,sa.staffAddress,ad.addressdetailed,sa.Photo,sa.reference,sa.referenceorganization,sa.referencecode"
				+ " from dt_hr_staff_address ad, dt_hr_staff sa where sa.staffid = ad.staffid and sa.staffid=? and ad.addresskey in (select  max(t.addresskey) from dt_hr_staff_address t group by  t.staffid)"
				+ " and sa.staffID in (select staffID from dtcos where companyID = ? and cosStatus =?)";
		List<BaseBean> listBasic = baseBeanService.getListBeanBySqlAndParams(
				sqlBasic,
				new Object[] { staffID, account.getCompanyID(), "50" });
		request.setAttribute("basic", listBasic);
		String degreeSql = "select se.educationStart,se.educationEnd,se.educationName,cc.codeValue,cc1.codeValue codeValue1,cc2.codeValue codeValue2,cc3.codeValue codeValue3,se.provePerson ";
		degreeSql += " from dt_hr_staff_education se left join dt_hr_staff sa on sa.staffid=se.staffid left join dtccode cc on cc.codeid=se.majortype";
		degreeSql += " left join dtccode cc1 on cc1.codeid=se.educationHistory left join dtccode cc2 on cc2.codeid=se.educationStyle left join dtccode cc3 on cc3.codeid=se.educationType";
		degreeSql += " where cc.companyID=? and cc.codeStatus='00' and cc1.companyID=? and cc1.codeStatus='00' and cc2.companyID=? and cc2.codeStatus='00'";
		degreeSql += " and cc3.companyID=? and cc3.codeStatus='00' and sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus ='50' ) and sa.staffid=?";
		List<BaseBean> listDegree = baseBeanService.getListBeanBySqlAndParams(
				degreeSql,
				new Object[] { account.getCompanyID(), account.getCompanyID(),
						account.getCompanyID(), account.getCompanyID(),
						account.getCompanyID(), staffID });
		request.setAttribute("listDegree", listDegree);
		String resumeSql = " select	se.startTime, se.endTime,se.companyName,se.position,se.duties,se.postName,se.reference";
		resumeSql += " from dt_hr_staff_Resume se where se.staffid=? ";
		List<BaseBean> listResume = baseBeanService.getListBeanBySqlAndParams(
				resumeSql, new Object[] { staffID });
		request.setAttribute("listResume", listResume);
		String sqlFamily = "select se.memberName,cc.codeValue, se.memberBirthDay,se.memberCompanyName,se.memberPosition,se.memberAddress,se.memberPhone,se.memberDesc";
		sqlFamily += " from dt_hr_staff_familymember se left join dt_hr_staff sa on se.staffid=sa.staffid and sa.staffid=? left join dtccode cc on cc.codeid=se.memberRelationship";
		sqlFamily += "  where cc.companyID=? and cc.codeStatus='00' ";
		sqlFamily += " and sa.staffID in (select staffID from dtcos  where companyID = ?  and cosStatus = '50' ) ";
		List<BaseBean> listFamily = baseBeanService.getListBeanBySqlAndParams(
				sqlFamily, new Object[] { staffID, account.getCompanyID(),
						account.getCompanyID() });
		request.setAttribute("listFamily", listFamily);
		String hqlHealth = "select to_char(se.examinationTime,'yyyy-mm-dd'), se.examinationHospital,cc.codeValue,se.conditionDesc,se.auditor,se.verifyTime ";
		hqlHealth += " from dt_hr_staff  sa,dt_hr_staff_PhysicalCondition se,dtccode cc where sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and se.details=cc.codeID and cc.companyID=? and cc.codeStatus=? and sa.staffid=?";
		List<BaseBean> listHealth = baseBeanService.getListBeanBySqlAndParams(
				hqlHealth, new Object[] { account.getCompanyID(), "50",
						account.getCompanyID(), "00", staffID });
		request.setAttribute("listHealth", listHealth);
		String hqlPolitical = "select cc.codeValue, to_char(se.joinDate,'yyyy-mm-dd'),se.unit,se.introducer,to_char(se.probatePassDate,'yyyy-mm-dd'),se.partyStand ,se.auditor";
		hqlPolitical += " from dt_hr_staff  sa,dt_hr_staff_PoliticalStatus se,dtccode cc where sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and se.politicalStatus=cc.codeID and cc.companyID=? and cc.codeStatus=? and sa.staffid=?";
		List<BaseBean> listPolitical = baseBeanService
				.getListBeanBySqlAndParams(hqlPolitical, new Object[] {
						account.getCompanyID(), "50", account.getCompanyID(),
						"00", staffID });
		request.setAttribute("listPolitical", listPolitical);
		String sqlEncourage = "select cc.codeValue,se.encourageName,se.encourageReason,se.encourageDate,se.encourageOrgan,se.honoraryTitle,se.auditor";
		sqlEncourage += " from dt_hr_staff  sa,dt_hr_staff_Encourage se,dtccode cc where sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and se.encourageType=cc.codeID and cc.companyID=? and cc.codeStatus=? and sa.staffid=?";
		List<BaseBean> listEncourage = baseBeanService
				.getListBeanBySqlAndParams(sqlEncourage, new Object[] {
						account.getCompanyID(), "50", account.getCompanyID(),
						"00", staffID });
		request.setAttribute("listEncourage", listEncourage);
		String hqlPunishment = "select cc.codeValue,se.punishmentName,se.punishmentReason,to_char(se.punishmentDate,'yyyy-mm-dd'),se.punishmentOrgan,to_char(se.dischargeDate,'yyyy-mm-dd'),se.auditor ";
		hqlPunishment += " from dt_hr_staff  sa,dt_hr_staff_Punishment se,dtccode cc where sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and se.punishmentType=cc.codeID and cc.companyID=? and cc.codeStatus=? and sa.staffid=?";
		List<BaseBean> listPunishment = baseBeanService
				.getListBeanBySqlAndParams(hqlPunishment, new Object[] {
						account.getCompanyID(), "50", account.getCompanyID(),
						"00", staffID });
		request.setAttribute("listPunishment", listPunishment);
		String sqlInvestigation = "select to_char(se.investigationDate,'yyyy-mm-dd'),se.investigationItem,se.investigationPeroration,se.conductPrincipal,se.conductIdea,to_char(se.conductDate,'yyyy-mm-dd'),se.assessor ";
		sqlInvestigation += " from dt_hr_staff  sa,dt_hr_staff_Investigation se where sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and sa.staffid=?";
		List<BaseBean> listInvestigation = baseBeanService
				.getListBeanBySqlAndParams(sqlInvestigation, new Object[] {
						account.getCompanyID(), "50", staffID });
		request.setAttribute("listInvestigation", listInvestigation);
		String sqlPersonalFile = "select se.recordCode,se.recordName,se.assessorDate,se.controlEndDate,se.recordBoxCode,se.dutyOfficer,se.recordBoxName ";
		sqlPersonalFile += " from dt_hr_staff  sa,dt_hr_staff_PersonalFile se where sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and sa.staffid=?";
		List<BaseBean> listPersonalFile = baseBeanService
				.getListBeanBySqlAndParams(sqlPersonalFile, new Object[] {
						account.getCompanyID(), "50", staffID });
		request.setAttribute("listPersonalFile", listPersonalFile);
		String sqlCertificate = "select se.credentialsName,se.invalidateStart,se.invalidateEnd,se.organ ,se.credentialsNo,se.credentialsrecordNo,se.invalidate,se.auditorNumber,se.auditor";
		sqlCertificate += " from dt_hr_staff  sa,dt_hr_staff_Certificate se where sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and sa.staffid=?";
		List<BaseBean> listCertificate = baseBeanService
				.getListBeanBySqlAndParams(sqlCertificate, new Object[] {
						account.getCompanyID(), "50", staffID });
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
}
