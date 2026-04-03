package hy.ea.human.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.*;
import hy.ea.bo.human.wage.PSHistory;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;

/*
 * 招聘登记。面试。入职管理 yjg
 */
@Controller
@Scope("prototype")
public class AuditionAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CompanyService companyService;

	private Audition audition;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private String result;
	private String status;
	private String parameter;
	private String staffID;
	private int pageNumber;
	private String organizations;
	private String deptposts;
	private CSP csp;
	private List<BaseBean> beans;
	private DepartmentPost departmentPost;
	private List<CCode> staffTypeList;
	private String codeID;
	private String codeValue;
	private String companyName;
	private String start; //口试1  笔试2结果3
	private List<Audition> audList;
	
	private List<BaseBean> listBasic; //个人信息
	private List<BaseBean> listDegree;//学历
	private List<BaseBean> listResume;//个人履历
	private List<BaseBean> listFamily;//家庭成员
	private List<BaseBean> listaud;//招聘
	
	private String trimType;//流程调整标识  3.面试页面
	
	
	
	/**
	 * 员工详细信息打印
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String printBIAud() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");// company201009046vxdyzy4wg0000000025
		HttpServletRequest request = ServletActionContext.getRequest();
		String staffID = request.getParameter("staffID");// staffid-cstaff20110307JVS37RIIDJ0000000023
		String sqlBasic = " select sa.recordcode,sa.staffName,sa.usednmae,sa.birthday,sa.nativePlace,"
				+ "sa.nation,sa.staffIdentityCard,sa.staffAddress,sa.Photo,"
				+ "sa.reference,sa.referenceorganization,sa.referencecode"
				+ " from dt_hr_staff sa where sa.staffid = ?";
		listBasic = baseBeanService.getListBeanBySqlAndParams(
				sqlBasic, new Object[] { staffID });
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
		listDegree = baseBeanService.getListBeanBySqlAndParams(
				degreeSql,
				new Object[] { account.getCompanyID(), account.getCompanyID(),
						account.getCompanyID(), account.getCompanyID(), staffID });
		//个人履历
		String resumeSql = " select	se.startTime, se.endTime,se.companyName,se.position,se.duties,se.postName,se.reference";
		resumeSql += " from dt_hr_staff_Resume se where se.staffid=? ";
		listResume = baseBeanService.getListBeanBySqlAndParams(
				resumeSql, new Object[] { staffID });
		//家庭成员
		String sqlFamily = "select se.memberName,cc.codeValue, se.memberBirthDay,se.memberCompanyName,se.memberPosition,se.memberAddress,se.memberPhone,se.memberDesc";
		sqlFamily += " from dt_hr_staff_familymember se left join dt_hr_staff sa on se.staffid=sa.staffid left join dtccode cc on cc.codeid=se.memberRelationship";
		sqlFamily += "  where cc.companyID=? and cc.codeStatus='00' and sa.staffid=? ";
		listFamily = baseBeanService.getListBeanBySqlAndParams(
				sqlFamily, new Object[] {  account.getCompanyID(),staffID});
		String sqlAution = "select a.auditionDirection,a.auditionPost,a.experience from dtAudition a where a.companyID = ? and a.staffID = ?  ";
		listaud = baseBeanService.getListBeanBySqlAndParams(sqlAution, new Object[]{account.getCompanyID(),staffID});
		return "printBIAud";
	}
	
	
	
	/**
	 * 面试 --笔试、口试
	 * 开始--->
	 * @return
	 */
	public String editaudition(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String hql = "select new  Audition(s.staffName,s.photo,s.staffIdentityCard,s.reference,s.referenceCode,"
				+ "a.auditionKey, a.auditionID,a.companyID, a.staffID,"
				+ "a.auditionDirection, a.auditionPost, a.experience,"
				+ "a.place, a.examiner, a.auditionDate, a.auditionDept,"
				+ "a.commention, a.auditionPoint, a.registerDate,"
				+ "a.auditionPlace,a.becomesDate, a.remark, a.status,a.staffCategoryID,a.categoryName,a.bsState,a.ksState,a.zpState,a.ztState) "
				+ " from Staff s, Audition a where s.staffID = a.staffID and a.companyID=? and a.auditionID = ? ";
		audition = (Audition)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyID,staffID});
		return "edit_audition";
	}
	/**
	 * 口试 笔试list
	 * @return
	 */
	public String getAuditionkb(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String hql = "select new  Audition(s.staffName,s.photo,s.staffIdentityCard,s.reference,s.referenceCode,"
				+ "a.auditionKey, a.auditionID,a.companyID, a.staffID,"
				+ "a.auditionDirection, a.auditionPost, a.experience,"
				+ "a.place, a.examiner, a.auditionDate, a.auditionDept,"
				+ "a.commention, a.auditionPoint, a.registerDate,"
				+ "a.auditionPlace,a.becomesDate, a.remark, a.status,a.staffCategoryID,a.categoryName,a.bsState,a.ksState,a.zpState,a.ztState) "
				+ " from Staff s, Audition a where s.staffID = a.staffID and a.companyID=?";
		List<Object> prarm = new ArrayList<Object>();
		prarm.add(companyID);
		if(start.equals("1")){
			hql+=" and a.staffID = ? and a.status = ?";
			prarm.add(staffID);
			prarm.add("10");
		}else if(start.equals("2")){
			hql+=" and a.staffID = ? and a.status = ?";
			prarm.add(staffID);
			prarm.add("11");
		}else if(start.equals("3")){
			hql+=" and a.status in (?,?)";
			prarm.add("21");
			prarm.add("33");
		}
		if(!start.equals("3")){
			audition = (Audition)baseBeanService.getBeanByHqlAndParams(hql, prarm.toArray());
		}else{
			String hql2 = "select count(*) from Staff s, Audition a where s.staffID = a.staffID and a.companyID=? and a.status in (?,?) ";
			hql = hql + " order by  a.becomesDate desc";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), hql,hql2, prarm.toArray());
			return "cstaff_audition_j";
		}
		return "cstaff_audition_k";
	}
	/**
	 * 面试 口试通过
	 * @return
	 */
	public String deitAuditionky(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String hql = "update Audition set status= ? where companyID=? and auditionID=?";
		List<Object> prarm = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		if(start.equals("1")){
			prarm.add("11");
			map.put("result", "11");
		}
		if(start.equals("2")){
			prarm.add("21");
			map.put("result", "21");
		}
		prarm.add(companyID);
		prarm.add(audition.getAuditionID());
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql},prarm.toArray());
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * 面试 口试未通过
	 * @return
	 */
	public String deitAuditionkn(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String hql = "update Audition set status= ? where companyID=? and auditionID=?";
		List<Object> prarm = new ArrayList<Object>();
		prarm.add("33");
		prarm.add(companyID);
		prarm.add(audition.getAuditionID());
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql},prarm.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "33");
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	
	/**
	 * 面试 --笔试、口试
	 * <---结束
	 */
	
	
	// ///////********************************招聘
	// 招聘登记管理列表 status 1:面试管理 2：入职管理 
	public String getauditionList() {
		pageForm = getlist((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber));
		if (status.equals("1")) {
			return "employement";
		} else if (status.equals("2")) {
			return "trainlist";
		} else if(status.equals("3")){
			return "registerlist";
		}else{
			return "auditionlist";
		}

	}

	public PageForm getlist(int pageNumber1, int pageSize1) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Company company = companyService.getCompanyByCompanyID(companyID);
		companyName = company.getCompanyName();
		
		String hql = "select new  Audition(s.staffName,s.photo,s.staffIdentityCard,s.reference,s.referenceCode,"
				+ "a.auditionKey, a.auditionID,a.companyID, a.staffID,"
				+ "a.auditionDirection, a.auditionPost, a.experience,"
				+ "a.place, a.examiner, a.auditionDate, a.auditionDept,"
				+ "a.commention, a.auditionPoint, a.registerDate,"
				+ "a.auditionPlace,a.becomesDate, a.remark, a.status,a.staffCategoryID,a.categoryName,a.bsState,a.ksState,a.zpState,a.ztState) "
				+ " from Staff s, Audition a where s.staffID = a.staffID and a.companyID=? and a.status like ? ";
		String hql2 = "select count(*) from Staff s, Audition a where s.staffID = a.staffID and a.companyID=? and a.status like ? ";
		if (search != null && !"".equals(search) && audition != null) {
			if (audition.getStaffName() != null
					&& !"".equals(audition.getStaffName())) {
				hql = hql + " and s.staffName like '%"
						+ audition.getStaffName() + "%' ";
				hql2 = hql2 + " and s.staffName like '%"
						+ audition.getStaffName() + "%' ";
			}
			if (audition.getStaffIdentityCard() != null
					&& !"".equals(audition.getStaffIdentityCard())) {
				hql = hql + " and s.staffIdentityCard like '%"
						+ audition.getStaffIdentityCard() + "%'";
				hql2 = hql2 + " and s.staffIdentityCard like '%"
						+ audition.getStaffIdentityCard() + "%'";
			}
		}
		hql = hql + " order by  a.becomesDate desc";
		Object[] params = { companyID, status + "%" };
		pageForm = baseBeanService.getPageForm(pageNumber1, pageSize1, hql,
				hql2, params);
		return pageForm;
	}

	// 导出招聘登记管理列表
	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("00", "待面试");
		map.put("10", "待口试");
		map.put("11", "待笔试");
		map.put("33", "面试未通过");
		map.put("21", "未入职");
		map.put("22", "已入职");
		map.put("99", "已离职");
		Audition.setOMap(map);
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String sql = " select s.staffname,s.staffidentitycard, d.auditiondirection,d.auditionpost,d.experience," +
				"d.place,d.examiner,d.auditiondate,d.auditiondept, d.commention,d.auditionpoint," +
				"d.registerdate,d.auditionplace,d.remark,d.status from  dtAudition d " +
				"left join dt_hr_Staff s on s.staffid = d.staffid where d.companyid = ? and d.status like ? ";
		if (search != null && !"".equals(search) && audition != null) {
			if (audition.getStaffName() != null
					&& !"".equals(audition.getStaffName())) {
				sql = sql + " and s.staffName like '%"
						+ audition.getStaffName() + "%' ";
			}
			if (audition.getStaffIdentityCard() != null
					&& !"".equals(audition.getStaffIdentityCard())) {
				sql = sql + " and s.staffIdentityCard like '%"
						+ audition.getStaffIdentityCard() + "%'";
			}
		}
		sql = sql + " order by s.staus, s.staffName";
		Object[] params = { companyID, status + "%" };
		excelStream = excelService.showExcel(Audition.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql, params));
		CLogBook logBook = logBookService.saveCLogBook(null, "导出招聘登记", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	// 删除招聘登记管理列表
	public String delAudition() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params = { companyID, audition.getAuditionID() };
		String hql1 = "from Audition where companyID=? and auditionID=?";
		Audition audition = (Audition) baseBeanService.getBeanByHqlAndParams(
				hql1, params);
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { audition.getStaffID() });
		if (!audition.getStatus().equals("22")) {
			String hql = "delete from Audition where companyID=? and auditionID=?";
			beans = new ArrayList<BaseBean>();
			CLogBook logBook = logBookService.saveCLogBook(null, "删除招聘登记(招聘人员："
					+ staff.getStaffName() + ")", account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
					new String[] { hql }, params);
		}
		return getauditionList();
	}

	// 招聘登记管理保存(AJAX)

	public String saveAudition() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		if (audition.getAuditionID() == null
				|| "".equals(audition.getAuditionID())) {
			audition.setAuditionID(serverService.getServerID("audition"));
			audition.setStatus("00");
			parameter = "添加招聘登记";
		} else {
			parameter = "修改招聘登记";
		}
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { audition.getStaffID() });
		parameter += "(招聘人员名称:" + staff.getStaffName() + ")";
		audition.setCompanyID(account.getCompanyID());
		beans.add(audition);
		CLogBook logBook = logBookService
				.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "1");
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}
	// 所有面试保存(AJAX)
	public String saveAllAudition() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		if (audition.getAuditionID() == null
				|| "".equals(audition.getAuditionID())) {
			audition.setAuditionID(serverService.getServerID("audition"));
			audition.setStatus("00");
			parameter = "添加面试记录";
		} else {
			parameter = "修改面试记录";

		}
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { audition.getStaffID() });
		parameter += "(招聘人员名称:" + staff.getStaffName() + ")";
		audition.setCompanyID(account.getCompanyID());
		beans.add(audition);
		CLogBook logBook = logBookService
				.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		if(start != null){
			if(!"".equals(start) && start.equals("3")){
				return "success";
			}
		}else{
			return getauditionList();
		}
		return "";
	}

	// 入职管理(跳转到人员分配页面)
	public String saveCos() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		List<Object[]> parmsList = new ArrayList<Object[]>();
		String hql1 = "update Audition set status= ?,staffCategoryID=?,categoryName=? where companyID=? and auditionID=?";
		String hql2 = "delete from COS where companyID = ?  and staffID = ? ";
		String hql3 = "delete from CSP where companyID = ?  and staffID = ? ";
		Object[] parm1 = { "22", codeID.trim(), codeValue,
				account.getCompanyID(), audition.getAuditionID() };
		Object[] parm2 = { account.getCompanyID(), staffID };
		String[] hqls = { hql1, hql2, hql3 };
		parmsList.add(parm1);
		parmsList.add(parm2);
		parmsList.add(parm2);

		String[] organization = organizations.split(",");
		String[] deptpost = deptposts.split(",");
		COS cos = null;

		// 保存工资级别
		if (null != csp.getPayScaleID() && !"".equals(csp.getPayScaleID())) {
			csp.setCompanyID(account.getCompanyID());
			csp.setStaffID(staffID);
			if (null == csp.getCspID() || "".equals(csp.getCspID())) {
				csp.setCspID(serverService.getServerID("csp"));
			}
			beans.add(csp);

			/********* 记录职务级别信息 ***********/
			String hql_audition = "from Audition where auditionID=? ";
			Audition entity = (Audition) baseBeanService.getBeanByHqlAndParams(
					hql_audition, new Object[] { audition.getAuditionID() });
			PSHistory psh = new PSHistory();
			psh.setPshID(serverService.getServerID("PSHistory"));
			psh.setCompanyID(account.getCompanyID());
			psh.setStaffID(staffID);
			psh.setPayScaleID(csp.getPayScaleID());
			psh.setApplyDate(new Date());
			psh.setEffectiveDate(entity.getRegisterDate());
			psh.setStatus("01");
			beans.add(psh);
		}
		for (int i = 0; i < organization.length; i++) {
			cos = new COS();
			if (i == 0) {
				cos.setStatus("01");
			} else {
				cos.setStatus("00");
			}
			if (!"".equals(organization[i])) {
				cos.setOrganizationID(organization[i]);
				cos.setDepPostID(deptpost[i]);
				cos.setCosID(serverService.getServerID("cos"));
				cos.setCompanyID(account.getCompanyID());
				cos.setStaffID(staffID);
				cos.setCosStatus("50");
				beans.add(cos);
			}
		}

		String hqlForStaff = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
				hqlForStaff, new Object[] { staffID });
		CLogBook logBook = logBookService.saveCLogBook(null, "入职管理(入职人员："
				+ staff.getStaffName() + ")", account);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, hqls, parmsList);
		return getauditionList();
	}

	/**
	 * 修改入职和转正时间
	 * 
	 * @return
	 */
	public String upDateStuffDate() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		Object[] params = { audition.getRegisterDate(),
				audition.getBecomesDate(), audition.getAuditionID(),
				account.getCompanyID() };
		String[] hqls = { "update Audition set registerDate=?,becomesDate=? where auditionID=? and companyID=?" };
		CLogBook logBook = logBookService.saveCLogBook(null, "修改入职和转正时间(入职人员："
				+ audition.getStaffName() + ")", account);
		List<BaseBean> bList = new ArrayList<BaseBean>();
		bList.add(logBook);
		baseBeanService
				.saveBeansListAndexecuteHqlsByParams(bList, hqls, params);
		audition = null;
		return "success";
	}

	/**
	 * JSON取得员工类别
	 */
	public String getBillID() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		staffTypeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20120912ngiqevnb760000000051");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffTypeList", staffTypeList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Audition getAudition() {
		return audition;
	}

	public void setAudition(Audition audition) {
		this.audition = audition;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public DepartmentPost getDepartmentPost() {
		return departmentPost;
	}

	public void setDepartmentPost(DepartmentPost departmentPost) {
		this.departmentPost = departmentPost;
	}

	public List<CCode> getStaffTypeList() {
		return staffTypeList;
	}

	public void setStaffTypeList(List<CCode> staffTypeList) {
		this.staffTypeList = staffTypeList;
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

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}
	public List<Audition> getAudList() {
		return audList;
	}
	public void setAudList(List<Audition> audList) {
		this.audList = audList;
	}

	public List<BaseBean> getListBasic() {
		return listBasic;
	}

	public void setListBasic(List<BaseBean> listBasic) {
		this.listBasic = listBasic;
	}

	public List<BaseBean> getListDegree() {
		return listDegree;
	}

	public void setListDegree(List<BaseBean> listDegree) {
		this.listDegree = listDegree;
	}

	public List<BaseBean> getListResume() {
		return listResume;
	}

	public void setListResume(List<BaseBean> listResume) {
		this.listResume = listResume;
	}

	public List<BaseBean> getListFamily() {
		return listFamily;
	}

	public void setListFamily(List<BaseBean> listFamily) {
		this.listFamily = listFamily;
	}

	public List<BaseBean> getListaud() {
		return listaud;
	}

	public void setListaud(List<BaseBean> listaud) {
		this.listaud = listaud;
	}

	public String getTrimType() {
		return trimType;
	}

	public void setTrimType(String trimType) {
		this.trimType = trimType;
	}
	
}