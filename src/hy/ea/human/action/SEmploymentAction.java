package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionContext;
//import com.vpiaotong.openapi.util.UUIDUtil;
import hy.ea.bo.*;
import hy.ea.bo.company.ContactRelation;
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
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 招聘入职
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class SEmploymentAction {
	private static final Logger logger = LoggerFactory.getLogger(SEmploymentAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;

	@Resource
	private ServerService serverService;


	@Resource
	private CompanyService companyService;

	@Resource
	private CCodeService codeService;



	private Staff searchCStaff;// 保存人员搜索信息
	private String search;// 判断是否搜索人员
	private PageForm pageForm;
	private String result;
	private String sub;
	private InputStream excelStream;
	private String staffID;
	private int pageNumber;
	private String[] args;
	private String parameter;
	private List<BaseBean> beans;
	private String oid;//传过来的组织机构ID
	@SuppressWarnings("unused")
	private String message;
	
	private DepartmentPost departmentPost;
	private String searchValue;  //查询传值
	
	public String title1;
	public String checkOrgID; //选中部门ID传参

	private String start; //口试1  笔试2结果3
	private String companyName;
	private Audition audition;
	private String status;

	Map<String, String> oMap1 ; //公司 部门maplist

	/**
	 * 从招聘人员中删除
	 * 
	 * @return
	 */
	public String delCOS() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String obj = (String) session.get("session_value");
		if (sub != null && sub.equals(obj)) {
			CAccount account = (CAccount) session.get("account");
			String[] hql = { "update COS set cosStatus = ? where companyID = ? and organizationID = ? and staffID = ?" };
			Object[] params = { args[0], account.getCompanyID(),
					session.get("organizationID"), staffID };
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { staffID });
			CLogBook logBook = logBookService.saveCLogBook(null, "删除招聘人员(人员名称："
					+ staff.getStaffName() + ")", account);
			beans = new ArrayList<BaseBean>();
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql,
					params);
		}
		return getStaffList();
	}

	/**
	 * 邀请面试
	 * 
	 * @return
	 */
	public String updateCOS() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String obj = (String) session.get("session_value");
		if (sub != null && sub.equals(obj)) {
			CAccount account = (CAccount) session.get("account");
			String[] hql = { "update COS set cosStatus= ? where companyID = ? and organizationID = ? and staffID = ?" };
			Object[] params = { args[1], account.getCompanyID(),
					session.get("organizationID"), staffID };

			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hql,
					params);
		}
		return getStaffList();
	}

	/**
	 * 保存模糊查询
	 * 
	 * @return
	 */
	public String toSearchCStaff() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getStaffList();
	}

	/**
	 * 根据公司和机构ID查询本部门下的招聘信息
	 * 
	 * @return
	 */
	public String getStaffList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		CAccount account = (CAccount) session.get("account");

		String sql = "select s.staffID,s.staffCode,s.recordCode,da.categoryName,s.staffName,d.postName,s.usedNmae,"
					+ " s.sex,s.birthday,s.nationality,s.nativePlace,s.nation,s.staffIdentityCard,s.staffAddress,"
					+ " s.reference,s.referenceCode,s.referenceOrganization,s.verifyTime,s.politicsStatus,"
					+ " s.culturalDegree,s.marriage,s.health,s.bankNum,s.staffDesc,s.staffKey,s.address,s.photo"
					+ " from dtcos c"
					+ " left join dt_hr_deptpost d on c.depPostID = d.depPostID"
					+ " left join dtcorganization o on c.organizationID = o.organizationID"
					+ " right join dt_hr_staff s on c.staffID = s.staffID"
					+ " right join dtaudition da on c.staffID = da.staffID and da.companyID= ? and da.status = '22'"
					+ " where c.companyID = ? and (c.organizationID = ? or d.leveloneOrgID = ?) and c.cosStatus = '50'";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getCompanyID());
		params.add(account.getCompanyID());
		params.add(session.get("organizationID"));
		params.add(session.get("organizationID"));
		
		//组织机构查部门人员
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getParameter("ogID") != null 
				&& !"".equals(request.getParameter("ogID"))){
			String sqls = sql.substring(0,sql.indexOf("where"));
			sql = sqls + "where c.companyID = ? and c.organizationID = ? and c.cosStatus = '50'";
			params.remove(3);
			params.add(request.getParameter("ogID"));
			params.remove(2);
		}
		
		if (search != null && search.equals("search")) {
			if(searchValue != null && searchValue.equals("searchValue")){
				List<Object> list = searchValue(sql,params);
				sql = list.get(0).toString();
			}else{
				searchStaff(account.getCompanyID(),session.get("organizationID").toString());
				return "to_employment";
			}
		}
		sql += " order by s.staffID desc";
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), params.toArray()); 
		
		if(request.getParameter("ogID") != null 
				&& !"".equals(request.getParameter("ogID"))){
			return "to_incumbents";
		}
		return "to_employment";
	}



	public String getStaffListPc() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		CAccount account = (CAccount) session.get("account");


		String sql = "select s.staffID,d.postNum,nvl(s.headimage,'无'),s.staffName,s.sex,d.postName,nvl(s.usedNmae,'无'),"
				+ " com.companyName,nvl(s.staffIdentityCard,'无'),nvl(s.birthday,'无'),nvl(s.nation,'无'),nvl(s.source,'无')"
				+" ,s.reference,s.staffAddress,da.auditionPost,s.staffPost "
				+ " from dtcos c"
				+ " left join dt_hr_deptpost d on c.depPostID = d.depPostID"
				+ " left join dtcorganization o on c.organizationID = o.organizationID"
				+ " left join dtcompany com on c.companyID = com.companyID"
				+ " right join dt_hr_staff s on c.staffID = s.staffID"
				+ " right join dtaudition da on c.staffID = da.staffID and da.companyID= ? and da.status = '22'"
				+ " where c.companyID = ? and (c.organizationID = ? or d.leveloneOrgID = ?) and c.cosStatus = '50'";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getCompanyID());
		params.add(account.getCompanyID());
		params.add("organization20100909zijmxn7qze0000001402");
		params.add("organization20100909zijmxn7qze0000001402");
		if(null != account && StringUtils.isNotEmpty(account.getAfterStaffID())){
			sql = sql +"  and s.staffID = ?" ;
			params.add(account.getStaffID());
		}


		//组织机构查部门人员
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getParameter("ogID") != null
				&& !"".equals(request.getParameter("ogID"))){
			String sqls = sql.substring(0,sql.indexOf("where"));
			sql = sqls + "where c.companyID = ? and c.organizationID = ? and c.cosStatus = '50'";
			params.remove(3);
			params.add(request.getParameter("ogID"));
			params.remove(2);
		}

		if (search != null && search.equals("search")) {
			if(searchValue != null && searchValue.equals("searchValue")){
				List<Object> list = searchValue(sql,params);
				sql = list.get(0).toString();
			}else{
				searchStaff(account.getCompanyID(),session.get("organizationID").toString());
				return "to_employment";
			}
		}
		//sql += " order by s.staffID desc";
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), params.toArray());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);

		//JSONObject OB =

		//JSONObject oj = JSONObject.fromObject(map);
		result = JSONObject.toJSONString(map);
		return "success";
	}


	/**
	 * 社会人力资源模糊查询
	 *
	 * @return
	 */
	public String getListCStaffByCompanyID() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		CAccount account = (CAccount) session.get("account");
		String phql1 = "from Staff where staffID=?";


		Staff staffLogin = (Staff)baseBeanService.getBeanByHqlAndParams(phql1, new Object[]{account.getStaffID()});
		String sql = "select s.staffID,d.postNum,nvl(s.headimage,'无'),s.staffName,s.sex,d.postName,nvl(s.usedNmae,'无'),"
				+ " com.companyName,nvl(s.staffIdentityCard,'无'),nvl(s.birthday,'无'),nvl(s.nation,'无'),nvl(s.source,'无')"
				+" ,s.reference,s.staffAddress,da.auditionPost,s.staffPost "
				+ " from dt_hr_staff s"
				+ " left join dtcos c on c.staffID = s.staffID"
				+ " left join dt_hr_deptpost d on c.depPostID = d.depPostID"
				+ " left join dtcorganization o on c.organizationID = o.organizationID"
				+ " left join dtcompany com on c.companyID = com.companyID"
				+ " left join dtaudition da on c.staffID = da.staffID  and da.status = '22'"
				+ " where s.staffStatus = '00' and s.groupCompanySn = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(staffLogin.getGroupCompanySn());
		if(null != account && StringUtils.isNotEmpty(account.getAfterStaffID())){
			sql = sql +"  and s.staffID = ?" ;

			params.add(account.getStaffID());
		}
		sql = sql+"AND trunc( s.verifyTime, 'mm' ) BETWEEN trunc( add_months( SYSDATE,- 3 ), 'mm' )";
		sql = sql+"AND trunc( SYSDATE, 'mm' )";
		sql = sql +"order by s.verifyTime desc";
		//params.add(account.getCompanyID());
		//sql += " order by s.staffID desc";
		if(null == pageForm){
			pageForm = new PageForm();
			pageForm.setPageNumber(1);
			pageForm.setPageSize(25);
		}
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageForm.getPageSize() == 0 ? 5 : pageForm.getPageSize()), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), params.toArray());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		result = JSONObject.toJSONString(map);
		return "success";
	}






	public String saveCStaff() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Map<String, String[]> parameterMap = ServletActionContext.getRequest().getParameterMap();
		String hql = "select count(*) from Staff where  staffIdentityCard = ?";
		Object[] params = { parameterMap.get("staffIdentityCard")[0] };
		beans = new ArrayList<BaseBean>();
		searchCStaff = new Staff();
		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		if (!parameterMap.containsKey("staffId") || parameterMap.get("staffId")[0] == null || "".equals(parameterMap.get("staffId")[0])) {
			String phql = "select count(*) from Staff ";
			String phql1 = "from Staff where staffID=?";
			Staff staffLogin = (Staff)baseBeanService.getBeanByHqlAndParams(phql1, new Object[]{account.getStaffID()});
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			searchCStaff.setStaffCode("NO" + pcount);
			searchCStaff.setRecordCode("NO" + pcount);
			searchCStaff.setVerifyTime(new Date());
			searchCStaff.setStaffID(serverService.getServerID("cstaff"));
			searchCStaff.setEntryPersonnel(staffLogin.getStaffName());
			searchCStaff.setStaffName(parameterMap.get("name")[0]);
			searchCStaff.setSex(parameterMap.get("gender")[0]);
			searchCStaff.setNation(parameterMap.get("ethnicity")[0]);
			searchCStaff.setStaffIdentityCard(parameterMap.get("staffIdentityCard")[0]);
			searchCStaff.setReference(parameterMap.get("contact")[0]);
			searchCStaff.setReferenceCode(parameterMap.get("qq")[0]);
			searchCStaff.setBirthday(parameterMap.get("birthDate")[0]);
			searchCStaff.setGroupCompanySn(staffLogin.getGroupCompanySn());
			parameter = "添加员工（人员姓名：:" + staffLogin.getStaffName() + ")";
		} else {
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { parameterMap.get("staffId")[0] });
			searchCStaff.setVerifyTime(new Date());
			parameter = "修改员工(人员名称:" + staff.getStaffName() + ")";
			if (staff.getStaffIdentityCard() != null
					&& !staff.getStaffIdentityCard().equals(""))
				if (staff.getStaffIdentityCard().equals(
						parameterMap.get("staffIdentityCard")[0])) {
					count = 0;
				}
		}

		if (count > 0) {
			return "此处为社会人力存在人员";
		}
		searchCStaff.setStaffStatus("00");
		searchCStaff.setSource("系统");
		/**
		 * 快捷添加个人往来关系
		 */
		ContactRelation conRelation=(ContactRelation)saveRelation(searchCStaff);
		if(conRelation!=null){
			beans.add(conRelation);
		}
		/**
		 * 快捷添加地址信息
		 */
		StaffAddress staffAddress=(StaffAddress)saveAddress(searchCStaff);
		if(staffAddress!=null){
			beans.add(staffAddress);
		}

		/**/
		beans.add(searchCStaff);
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}



	// 招聘登记管理保存(AJAX)

	public String saveAudition() {
		ActionContext actionContext = ActionContext.getContext();
		ObjectMapper objectMapper = new ObjectMapper();
		Map map1 = objectMapper.convertValue(actionContext.getContextMap().get("parameters"),Map.class);
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		audition = new Audition();
		audition.setAuditionDirection(map1.get("auditionDirection").toString());
		audition.setAuditionPost(map1.get("auditionPost").toString());
		audition.setExperience(map1.get("experience").toString());
		audition.setStaffID(map1.get("staffID").toString());
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		if (audition.getAuditionID() == null
				|| "".equals(audition.getAuditionID())) {
			audition.setAuditionID(serverService.getServerID("audition"));
//			audition.setAuditionKey(UUIDUtil.getSerialNo("402880828b"));
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
		/*CLogBook logBook = logBookService
				.saveCLogBook(null, parameter, account);
		beans.add(logBook);*/
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "1");
		net.sf.json.JSONObject oj = net.sf.json.JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}



	// 所有面试保存(AJAX)
	public String saveAllAudition() throws ParseException {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();

		ObjectMapper objectMapper = new ObjectMapper();
		Map map1 = objectMapper.convertValue(actionContext.getContextMap().get("parameters"),Map.class);
		String auditionKey = map1.get("auditionKey").toString();
		String status = map1.get("status").toString();
		String type = map1.get("type").toString();
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Audition audition = (Audition)baseBeanService.getBeanByKey(Audition.class,auditionKey);
		if(null == audition){
			audition = new Audition();
		}
		if(type.equals("1")){
			String registerDate = map1.get("registerDate").toString();
			String becomesDate = map1.get("becomesDate").toString();
			String auditionPlace = map1.get("auditionPlace").toString();
			String remark = map1.get("remark").toString();
			if(StringUtils.isNotEmpty(registerDate)){
				audition.setRegisterDate(simpleDateFormat.parse(registerDate));
			}
			if(StringUtils.isNotEmpty(becomesDate)){
				audition.setBecomesDate(simpleDateFormat.parse(becomesDate));
			}
			audition.setAuditionPlace(auditionPlace);
			audition.setRemark(remark);

		}else{
			String place = map1.get("place").toString();
			String auditionDate = map1.get("auditionDate").toString();
			String examiner = map1.get("examiner").toString();
			String auditionDept = map1.get("auditionDept").toString();
			audition.setAuditionDept(auditionDept);
			audition.setExaminer(examiner);
			audition.setPlace(place);
			if(StringUtils.isNotEmpty(auditionDate)){
				audition.setAuditionDate(simpleDateFormat.parse(auditionDate));
			}
		}
		audition.setStatus(status);

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
		return "success";
	}

	// 招聘登记管理列表 status 1:面试管理 2：入职管理
	public String getauditionList() {
		pageForm = getlist((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		result = JSONObject.toJSONString(map);
		return "success";

	}



	/**
	 * 面试 口试通过
	 * @return
	 */
	public String deitAuditionky(){
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();

		ObjectMapper objectMapper = new ObjectMapper();
		Map map1 = objectMapper.convertValue(context.getContextMap().get("parameters"),Map.class);
		start = map1.get("start").toString();
		String auditionKey = map1.get("auditionKey").toString();
		String hql = "update Audition set status= ? where companyID=? and auditionKey=?";
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
		prarm.add(auditionKey);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql},prarm.toArray());
		net.sf.json.JSONObject oj = net.sf.json.JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * 面试 口试未通过
	 * @return
	 */
	public String deitAuditionkn(){
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		CAccount account = (CAccount) session.get("account");
		ObjectMapper objectMapper = new ObjectMapper();
		Map map1 = objectMapper.convertValue(context.getContextMap().get("parameters"),Map.class);
		start = map1.get("start").toString();
		String auditionKey = map1.get("auditionKey").toString();
		String companyID = account.getCompanyID();
		String hql = "update Audition set status= ? where companyID=? and auditionKey=?";
		List<Object> prarm = new ArrayList<Object>();
		prarm.add("33");
		prarm.add(companyID);
		prarm.add(auditionKey);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql},prarm.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "33");
		net.sf.json.JSONObject oj = net.sf.json.JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 口试 笔试list
	 * @return
	 */
	public String getAuditionkb(){
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		CAccount account = (CAccount) session.get("account");
		ObjectMapper objectMapper = new ObjectMapper();
		Map map1 = objectMapper.convertValue(context.getContextMap().get("parameters"),Map.class);
		start = map1.get("start").toString();
		int pageSize = Integer.valueOf(map1.get("pageSize").toString());
		int pageNumber = Integer.valueOf(map1.get("pageNumber").toString());
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
			pageForm = baseBeanService.getPageForm((0 == pageNumber ? pageNumber : 1),
					(pageSize == 0 ? 10 : pageSize), hql,hql2, prarm.toArray());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageForm", pageForm);
			result = JSONObject.toJSONString(map);
			return "success";
		}
		return "cstaff_audition_k";
	}

	/**
	 * 人员配备查询
	 * @return
	 */
	public String getDeployList(){
		String hql1 = "select d.depPostID,d.postName,d.postNum ,d.SpecialpostNum,d.adminNum,d.omppostNum,c.companyName from dt_hr_deptpost d " +
				"left join dtCompany c on d.companyID = c.companyID and c.companyStatus = ? " +
				"where d.companyID = ? ";
		List<Object> parms1 = new ArrayList<Object>();
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		CAccount account = (CAccount) session.get("account");
		ObjectMapper objectMapper = new ObjectMapper();
		Map map1 = objectMapper.convertValue(context.getContextMap().get("parameters"),Map.class);
		int pageSize = Integer.valueOf(map1.get("pageSize").toString());
		int pageNumber = Integer.valueOf(map1.get("pageNumber").toString());
		parms1.add("00");
		parms1.add(account.getCompanyID());
		if(account.getCompanyID().contains("company") == false){
			hql1 += " and d.organizationID = ?";
			parms1.add(account.getCompanyID());
		}
		pageForm = baseBeanService.getPageFormBySQL(
				pageNumber,
				pageSize,hql1+" order by d.organizationID","select count(*) "
						+ hql1.substring(hql1.indexOf("from")), parms1.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		result = JSONObject.toJSONString(map);
		return "success";
	}






	public PageForm getlist(int pageNumber1, int pageSize1) {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		ObjectMapper objectMapper = new ObjectMapper();
		Map map1 = objectMapper.convertValue(context.getContextMap().get("parameters"),Map.class);
		status = map1.get("status").toString();
		pageNumber1 = Integer.parseInt(map1.get("pageNumber").toString());
		pageSize1 = Integer.parseInt(map1.get("pageSize").toString());
		String afterStaffID = map1.get("afterStaffID").toString();
		String auditionKey = map1.get("auditionKey").toString();
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
		List<Object> prarm = new ArrayList<Object>();
		prarm.add(companyID);
		prarm.add(status+"%");
		if(afterStaffID != null && !"".equals(afterStaffID) && !afterStaffID.equals("0") ){
			hql = hql + " and s.staffID=?";
			hql2 = hql2 + " and s.staffID=?";
			prarm.add(afterStaffID);

		}
		if(auditionKey != null && !"".equals(auditionKey) && !auditionKey.equals("0") ){
			hql = hql + " and a.auditionKey=?";
			hql2 = hql2 + " and a.auditionKey=?";
			prarm.add(auditionKey);

		}


		hql = hql + " order by  a.becomesDate desc";

		pageForm = baseBeanService.getPageForm(pageNumber1, pageSize1, hql,
				hql2, prarm.toArray());
		return pageForm;
	}



	/**
	 * 保存往来关系
	 * @return
	 */
	public BaseBean saveRelation(BaseBean baseBean){
		Map<String, String[]> parameterMap = ServletActionContext.getRequest().getParameterMap();
		ContactRelation conRelation=null;
		if(parameterMap.get("relationship")!=null&&!"".equals(parameterMap.get("relationship"))){
			String hql = "select count(*) from ContactRelation where staffID = ? and companyID = ? and relation = ?";
			Object[] params = { ((Staff)baseBean).getStaffID(), parameterMap.get("companyid")[0],
					parameterMap.get("relationship")[0] };
			int count = baseBeanService.getConutByByHqlAndParams(hql, params);
			if(count==0){
				conRelation=new ContactRelation();
				conRelation.setRelationID(serverService
						.getServerID("contactrelation"));
				conRelation.setCompanyID(parameterMap.get("companyid")[0]);
				conRelation.setStaffID(((Staff)baseBean).getStaffID());
				conRelation.setRelation(parameterMap.get("relationship")[0] );
			}
		}
		return conRelation;
	}

	/**
	 * 保存地址信息
	 * @return
	 */
	public BaseBean saveAddress(BaseBean baseBean){
		Map<String, String[]> parameterMap = ServletActionContext.getRequest().getParameterMap();
		Staff staff=(Staff)baseBean;
		StaffAddress staffAddress=null;
		if(staff!=null&&staff.getStaffAddress()!=null&&!"".equals(staff.getStaffAddress().trim())){
			String hql = " from StaffAddress where staffID = ?  and addressID = ?";
			Object[] params = { ((Staff)baseBean).getStaffID(), staff.getAddress()};
			staffAddress = (StaffAddress)baseBeanService.getBeanByHqlAndParams(hql, params);
			if(staffAddress==null){
				staffAddress=new StaffAddress();
				staffAddress.setAddressID(serverService.getServerID("address"));
				staff.setAddress(staffAddress.getAddressID());
			}
			staffAddress.setStaffID(staff.getStaffID());
			staffAddress.setCompanyID(parameterMap.get("companyid")[0]);
			staffAddress.setAddressType("scode201004233ern4m24yx0000000260");//地址类别ID为系统预设
			staffAddress.setAddressDetailed(staff.getStaffAddress());
			staffAddress.setLivestartDate(new Date());
		}
		return staffAddress;
	}




	
	/**
	 * 列表/导出查询调用
	 * @param sql
	 * @param params
	 */
	private List<Object> searchValue(String sql,List<Object> params){
		List<Object> results = new ArrayList<Object>();
		searchCStaff = (Staff) ActionContext.getContext().getSession().get("tablesearch");
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
	 * 支持财务责任人的查询
	 * 
	 * @version zg 2011-6-1
	 * @return
	 */
	public String getStaffForCashier() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID =(String) session.get("companyID");
		String currentOID = session.get("organizationID").toString();
		
		String hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and organizationID = ? )";
		
		Object[] params = null;
		
		if(checkOrgID != null && !"".equals(checkOrgID)){
			currentOID = checkOrgID;
		}
		
		//区别部门
		if(currentOID==null||currentOID.equals("")){
			currentOID = oid;
		}
		params =new Object[]{ account.getCompanyID(), "50",currentOID };
		
		//区别子公司
		if(companyID!=null&&!companyID.equals(account.getCompanyID())){
			currentOID = oid;
			params =new Object[]{ companyID, "50",currentOID };
		}
		
		//查询教务处教练
		if(parameter!=null&&parameter.contains("-")){
			String[] arr = parameter.split("-");
			if(arr.length!=0){
				if (search != null && search.equals("search")) {
					searchStaff(arr[0],arr[1]);
					return "staffForCashier";
				}
				Object[] parm = { arr[0], "50",
						arr[1] };
				pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), hql,
						parm);
				return "staffForCashier";
			}
		}
		
		//财务单据查询部门及子部门下所有人员
		if(title1!=null&&"title1".equals(title1)){
			hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? " +
					"or exists(select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))";
			params =new Object[]{ account.getCompanyID(), "50",currentOID,session.get("organizationID").toString()};
		}
		//查询在职与需要入职
		if(checkOrgID!=null&&checkOrgID.indexOf("zm") != -1){
			if(checkOrgID.equals("zm01")){ //专岗选择责任人
				hql = "from Staff s where exists ( select a.staffID from Audition a"
					 + " where a.staffID = s.staffID and a.companyID = ? and a.status = ? and a.registerDate is not null)";
				params =new Object[]{account.getCompanyID(),"21"};
			}else if(checkOrgID.equals("zm00")){ //兼岗选择责任人
				hql = "from Staff s where exists ( select a.staffID from Audition a"
					 + " where a.staffID = s.staffID and a.companyID = ? and a.registerDate is not null"
					 + " and a.status = ? and exists ( select c.staffID from COS c"
					 + " where c.staffID = a.staffID and c.companyID = ?"
					 + " and c.status = ? and c.cosStatus = ?))";
				params =new Object[]{account.getCompanyID(),"22",account.getCompanyID(),"01","50"};
			}else{
				hql = "select s from Staff s,Audition a where a.staffID = s.staffID and" +
						" a.companyID = ? and a.status between ? and ? and a.registerDate is not null order by s.verifyTime desc";
				params =new Object[]{account.getCompanyID(),"21","22"};
			}
		}
		if (search != null && search.equals("search")) {
			searchStaff(account.getCompanyID(),currentOID);
			return "staffForCashier";
		}
		
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), 
				hql,"select count(s) "+hql.substring(hql.indexOf("from")),
				params);
		return "staffForCashier";
	}



	public String getStaffForCashierNew() {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID =(String) session.get("companyID");
		ObjectMapper objectMapper = new ObjectMapper();
		Map map1 = objectMapper.convertValue(context.getContextMap().get("parameters"),Map.class);
		String currentOID = "0";
		PageForm pageForm = new PageForm();
		String hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and organizationID = ? )";

		Object[] params = null;
		checkOrgID = "zm";
		currentOID = checkOrgID;

		params =new Object[]{ account.getCompanyID(), "50",currentOID };

		//区别子公司
		if(companyID!=null&&!companyID.equals(account.getCompanyID())){
			currentOID = oid;
			params =new Object[]{ companyID, "50",currentOID };
		}

		//查询教务处教练
		if(parameter!=null&&parameter.contains("-")){
			String[] arr = parameter.split("-");
			if(arr.length!=0){
				if (search != null && search.equals("search")) {
					searchStaff(arr[0],arr[1]);
					/*return "staffForCashier";*/
				}
				Object[] parm = { arr[0], "50",
						arr[1] };
				pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
								.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), hql,
						parm);
				return "staffForCashier";
			}
		}

		//财务单据查询部门及子部门下所有人员
		if(title1!=null&&"title1".equals(title1)){
			hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? " +
					"or exists(select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))";
			params =new Object[]{ account.getCompanyID(), "50",currentOID,session.get("organizationID").toString()};
		}
		//查询在职与需要入职
		if(checkOrgID!=null&&checkOrgID.indexOf("zm") != -1){
			if(checkOrgID.equals("zm01")){ //专岗选择责任人
				hql = "from Staff s where exists ( select a.staffID from Audition a"
						+ " where a.staffID = s.staffID and a.companyID = ? and a.status = ? and a.registerDate is not null)";
				params =new Object[]{account.getCompanyID(),"21"};
			}else if(checkOrgID.equals("zm00")){ //兼岗选择责任人
				hql = "from Staff s where exists ( select a.staffID from Audition a"
						+ " where a.staffID = s.staffID and a.companyID = ? and a.registerDate is not null"
						+ " and a.status = ? and exists ( select c.staffID from COS c"
						+ " where c.staffID = a.staffID and c.companyID = ?"
						+ " and c.status = ? and c.cosStatus = ?))";
				params =new Object[]{account.getCompanyID(),"22",account.getCompanyID(),"01","50"};
			}else{
				hql = "select s from Staff s,Audition a where a.staffID = s.staffID and" +
						" a.companyID = ? and a.status between ? and ? and a.registerDate is not null order by s.verifyTime desc";
				params =new Object[]{account.getCompanyID(),"21","22"};
			}
		}
		if (search != null && search.equals("search")) {
			searchStaff(account.getCompanyID(),currentOID);
			return "staffForCashier";
		}

		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
				hql,"select count(s) "+hql.substring(hql.indexOf("from")),
				params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		result = JSONObject.toJSONString(map);
		return "success";
	}



	/**
	 * 在职员工分配岗位
	 *
	 * @return
	 */
	public String orgPostEntry() throws Exception{
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID =account.getCompanyID();
		ObjectMapper objectMapper = new ObjectMapper();
		Map map1 = objectMapper.convertValue(context.getContextMap().get("parameters"),Map.class);
		String staffId = map1.get("staffId").toString();
		String status = map1.get("status").toString();
		if(status.equals("full-time")){
			//专岗
			status = "01";
		}else{
			//简直岗位
			status = "00";
		}
		String payScaleID = map1.get("payScaleID").toString();
		String staffCategoryID = map1.get("staffCategoryID").toString();
		String categoryName = map1.get("categoryName").toString();
		/*String organizationID = map1.get("organizationID").toString();*/
		String depPostID = map1.get("depPostID").toString();
		COS cos = new COS();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			List<BaseBean> beans = new ArrayList<BaseBean>();
			String hql_audition = "from Audition where  companyID = ? and staffID = ? ";
			Audition entity1 = (Audition) baseBeanService.getBeanByHqlAndParams(
					hql_audition, new Object[] { companyID, staffId });

			String hql1 = "from DepartmentPost where companyID = ? and depPostID = ? ";
			DepartmentPost deppt = (DepartmentPost) baseBeanService
					.getBeanByHqlAndParams(hql1, new Object[] {
							companyID, depPostID });
			parameter = "人员分配岗位：" + deppt.getPostName();
			if (status.equals("01")) {
				String hql = "from COS where companyID = ? and staffID = ? and status = ?";
				COS cosl = (COS) baseBeanService.getBeanByHqlAndParams(hql,
						new Object[] { companyID, staffId,
								status });
				if (cosl != null) {
					map.put("vals", "专岗已设置,请勿重复！");
				} else {
					// 专岗
					cos.setCompanyID(companyID);
					cos.setCosID(serverService.getServerID("cos"));
					cos.setCosStatus("50");
					cos.setDepPostID(depPostID);
					cos.setOrganizationID(deppt.getOrganizationID());
					cos.setStaffID(staffId);
					beans.add(cos);
					// 员工级别确定
					CSP csp1 = new CSP();
					csp1.setPayScaleID(payScaleID);
					csp1.setCompanyID(companyID);
					csp1.setStaffID(staffId);
					csp1.setCspID(serverService.getServerID("csp"));
					beans.add(csp1);
					// 级别变更记录表
					PSHistory psh = new PSHistory();
					psh.setPayScaleID(payScaleID);
					psh.setPshID(serverService.getServerID("psh"));
					psh.setCompanyID(companyID);
					psh.setEffectiveDate(entity1.getRegisterDate());
					psh.setStaffID(staffId);
					psh.setApplyDate(new Date());
					psh.setStatus("01");
					beans.add(psh);
					// 职员状态
					entity1.setStaffCategoryID(staffCategoryID);
					entity1.setCategoryName(categoryName);
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

					CLogBook logbook = logBookService.saveCLogBook(deppt.getOrganizationID(),
							parameter, account);
					beans.add(deppt);
					beans.add(logbook);

					//同步到个人履历
					StaffResume staffResume = new StaffResume();
					staffResume.setStaffID(staffId);
					staffResume.setRecordID(serverService.getServerID("record"));
					staffResume.setCcompanyID(companyID);
					staffResume.setStartTime(entity1.getRegisterDate());
					Company com = (Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object []{companyID});
					staffResume.setCompanyName(com.getCompanyName());
					staffResume.setCompanyID(com.getCompanyID());
					staffResume.setPosition(deppt.getPostName());
					staffResume.setDuties(deppt.getResponsibilityRequire());
					staffResume.setReference(account.getAccountName());
					staffResume.setPostName(deppt.getPostName());
					staffResume.setPostCase("scode20100423vw54xx7r4f0000000054"); //在职
					staffResume.setReferencePhon("专岗");
					CDetail cdet = (CDetail)baseBeanService.getBeanByHqlAndParams("from CDetail where companyID = ?", new Object []{companyID});
					if(cdet!=null){
						staffResume.setPostAddress(cdet.getCompanyAddress());
					}
					beans.add(staffResume);
					baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{"delete CSP where companyID=? and staffID=?"},new String[]{companyID,staffId});
					map.put("vals", "设置专岗成功！");

				}

			} else if (cos.getStatus().equals("00")) {
				String hql = "from COS where companyID = ? and staffID = ? and status = ? and depPostID = ?";
				COS cosl = (COS) baseBeanService.getBeanByHqlAndParams(hql,
						new Object[] { companyID, staffId,
								cos.getStatus() ,departmentPost.getDepPostID()});
				if (cosl != null) {
					map.put("vals", "兼岗已设置,请勿重复！");
				}else{
					// 兼岗
					cos.setCompanyID(companyID);
					cos.setCosID(serverService.getServerID("cos"));
					cos.setCosStatus("50");
					cos.setDepPostID(depPostID);
					cos.setOrganizationID(deppt.getOrganizationID());
					cos.setStaffID(staffId);
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
					CLogBook logbook = logBookService.saveCLogBook(deppt.getOrganizationID(), parameter,
							account);
					beans.add(logbook);
					beans.add(deppt);

					//同步到个人履历
					StaffResume staffResume = new StaffResume();
					staffResume.setStaffID(staffId);
					staffResume.setRecordID(serverService.getServerID("record"));
					staffResume.setCcompanyID(companyID);
					staffResume.setStartTime(new Date());
					Company com = (Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object []{companyID});
					staffResume.setCompanyName(com.getCompanyName());
					staffResume.setCompanyID(com.getCompanyID());
					staffResume.setPosition(deppt.getPostName());
					staffResume.setDuties(deppt.getResponsibilityRequire());
					staffResume.setReference(account.getAccountName());
					staffResume.setPostName(deppt.getPostName());
					staffResume.setPostCase("scode20100423vw54xx7r4f0000000054"); //在职
					staffResume.setReferencePhon("兼岗");
					CDetail cdet = (CDetail)baseBeanService.getBeanByHqlAndParams("from CDetail where companyID = ?", new Object []{companyID});
					if(cdet!=null){
						staffResume.setPostAddress(cdet.getCompanyAddress());
					}
					beans.add(staffResume);

					baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
					map.put("vals", "设置兼岗成功！");
				}
			}

			net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(map);
			result = obj.toString();
		}catch(Exception e){
			logger.error("操作异常", e);
		}

		return "success";
	}



	/**
	 * JSON取得员工类别
	 */
	public String getBillID() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<CCode> staffTypeList = new ArrayList<>();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		staffTypeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20120912ngiqevnb760000000051");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffTypeList", staffTypeList);
		net.sf.json.JSONObject oj = net.sf.json.JSONObject.fromObject(map);
		result = oj.toString();

		return "success";
	}

	/*
	 * 获取被选中人员的部门列表
	 */
	public String getStaffListForPost() {
		ActionContext context = ActionContext.getContext();
		CAccount account = (CAccount) context.getSession().get("account");
		ObjectMapper objectMapper = new ObjectMapper();
		Map map1 = objectMapper.convertValue(context.getContextMap().get("parameters"),Map.class);
		String staffId = map1.get("staffId").toString();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<BaseBean> resultList = new ArrayList<BaseBean>();
		String hql1 = " from CSP where companyID=? and staffID=?";
		Object[] params = { account.getCompanyID(), staffId };
		CSP csp = new CSP();
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
		net.sf.json.JSONObject oj = net.sf.json.JSONObject.fromObject(map);
		result = oj.toString();

		return "success";
	}


	/**
	 * 支持财务责任人条件查询
	 * 
	 * @version zg 2011-6-1
	 * @return
	 */
	public String toSearchStaffForCashier() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getStaffForCashier();
	}

	/**
	 * 按条件搜索人员输出列表 被调用
	 * 
	 * @param companyID
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void searchStaff(String companyID,String OID) {
		searchCStaff = (Staff) ActionContext.getContext().getSession().get(
				"tablesearch");
		String hql1 = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and cosStatus = ? and companyID = ? and organizationID = ? ) and ";
		String hql2 = "  staffName like ? and staffIdentityCard like ?  order by staffID desc ";
		String hql = "";
		
		if(title1!=null&&"title1".equals(title1)){
			hql1 = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? " +
					"or exists(select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?))) and ";
		}
		
		if (searchCStaff.getStaffCode() != null
				&& !"".equals(searchCStaff.getStaffCode())) {
			hql = hql1 + " staffCode like '%" + searchCStaff.getStaffCode()
					+ "%' and " + hql2;
		} else {
			hql = hql1 + hql2;
		}

		Object[] params = { args[3], companyID, OID,
				"%" + searchCStaff.getStaffName() + "%",
				"%" + searchCStaff.getStaffIdentityCard() + "%" };
		
		if(title1!=null&&"title1".equals(title1)){
			params = new Object[]{ companyID,args[3],OID,OID,
					"%" + searchCStaff.getStaffName() + "%",
					"%" + searchCStaff.getStaffIdentityCard() + "%" };
		}
		
		if(checkOrgID.equals("zm")){
			hql = "select s from Staff s,Audition a  where a.staffID = s.staffID and" +
			" a.companyID = ? and (a.status = ? or a.status = ?) and a.registerDate is not null";
			List lis =new ArrayList();
			lis.add(companyID);
			lis.add("21");
			lis.add("22");
			if(searchCStaff.getStaffCode() != null
					&& !"".equals(searchCStaff.getStaffCode())){
				hql+=" and s.staffCode like ?";
				lis.add("%"+searchCStaff.getStaffCode()+"%");
			}
			if(searchCStaff.getStaffName() != null
					&& !"".equals(searchCStaff.getStaffName())){
				hql+=" and s.staffName like ?";
				lis.add("%"+searchCStaff.getStaffName()+"%");
			}
			if(searchCStaff.getStaffIdentityCard() != null
					&& !"".equals(searchCStaff.getStaffIdentityCard())){
				hql+=" and s.staffIdentityCard = ?";
				lis.add(searchCStaff.getStaffIdentityCard());
			}
			params = lis.toArray();
			hql += " order by s.verifyTime desc";
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), 
				hql,"select count(s) "+hql.substring(hql.indexOf("from")),
				params);
	}

	/**
	 * 根据公司和机构ID导出本部门下的招聘信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		
	    String sql = "select s.staffCode,s.recordCode,(case when da.categoryname is null then '暂无分配' else da.categoryname end) categoryname,s.staffName,"
	    	+ " d.postName,s.usedNmae,s.sex,s.birthday,s.nationality,s.nativePlace,s.nation,s.staffIdentityCard"
			+ " from dtcos c"
			+ " left join dt_hr_deptpost d on c.depPostID = d.depPostID"
			+ " left join dtcorganization o on c.organizationID = o.organizationID"
			+ " right join dt_hr_staff s on c.staffID = s.staffID"
			+ " right join dtaudition da on c.staffID = da.staffID and da.companyID= ? and da.status = '22'"
			+ " where c.companyID = ? and (c.organizationID = ? or d.leveloneOrgID = ?) and c.cosStatus = '50'";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getCompanyID());
		params.add(account.getCompanyID());
		params.add(session.get("organizationID"));
		params.add(session.get("organizationID"));
		
		//组织机构查部门人员
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getParameter("ogID") != null 
				&& !"".equals(request.getParameter("ogID"))){
			String sqls = sql.substring(0,sql.indexOf("where"));
			sql = sqls + "where c.companyID = ? and c.organizationID = ? and c.cosStatus = '50'";
			params.remove(3);
			params.add(request.getParameter("ogID"));
			params.remove(2);
		}
		
		if (search != null && search.equals("search")) {
			if(searchValue != null && searchValue.equals("searchValue")){
				List<Object> list = searchValue(sql,params);
				sql = list.get(0).toString();
			}else{
				return showExcelForSearched();
			}
		}
		sql += " order by s.staffID desc";
		List<BaseBean> stafflist = baseBeanService.getListBeanBySqlAndParams(sql, params.toArray());
		excelStream = excelService.showExcel(Staff.columnHeadings2(), stafflist);
		return "showexcel";
	}
	
	private String showExcelForSearched() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		searchCStaff = (Staff) session.get("tablesearch");
		CAccount account = (CAccount) session.get("account");
		String hql1 = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and cosStatus = ? and companyID = ? and organizationID = ? ) and ";
		String hql2 = "  staffName like ? and staffIdentityCard like ?  order by staffID desc ";
		String hql = "";
		if (searchCStaff.getStaffCode() != null
				&& !"".equals(searchCStaff.getStaffCode())) {
			hql = hql1 + " staffCode like '%" + searchCStaff.getStaffCode()
					+ "%' and " + hql2;
		} else {
			hql = hql1 + hql2;
		}

		Object[] params = { args[3], account.getCompanyID(),
				session.get("organizationID"),
				"%" + searchCStaff.getStaffName() + "%",
				"%" + searchCStaff.getStaffIdentityCard() + "%" };
		List<BaseBean> stafflist = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		excelStream = excelService.showExcel(Staff.columnHeadings(), stafflist);
		return "showexcel";
	}

	public String toSersonnelSystem() {

		return "SersonnelSystem";
	}

	public String toFinancial() {
		return "toFinancial";
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

	public void setArgs(String[] args) {
		this.args = args[0].split("_");
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
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

	public String getCheckOrgID() {
		return checkOrgID;
	}
	public void setCheckOrgID(String checkOrgID) {
		this.checkOrgID = checkOrgID;
	}
	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}
}