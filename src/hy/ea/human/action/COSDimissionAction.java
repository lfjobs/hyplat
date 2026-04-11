package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 离职员工
 *@author 陈小丰
 */
import hy.base.action.BaseAction;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.Audition;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.DepartmentPost;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.COSDimissionStaffVO;
import hy.ea.bo.human.vo.SalaryIntegral;
import hy.ea.human.service.impl.LogBookServiceImp;
import hy.ea.service.CCodeService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class COSDimissionAction extends BaseAction<Object>{
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CCodeService codeService;
	
	private COSDimissionStaffVO codi;
	private List<CCode> typelist;
	private String parameter;
	private String search;
	private List<CCode> connectionlist;
	
	public InputStream excelStream;
	
	/****** 离职员工工资 ******/
	private String sdate;
	private String edate;
	private String staffName;
	private String arg;
	private String staffcategoryid;
	private List<Object> add;
	private List<Object> cut;
	
	private List<BaseBean> wages;
	public String titleVar;
	private String logoStatusVar;
	
	
	 //根据条件查询离职员工 
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", codi);
		return getListCOSDimission();
	}
	
	public DetachedCriteria getListBYDC()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		Company entity = (Company) baseBeanService.getBeanByHqlAndParams(
				" from Company where companyID = ? ", new Object[] { this.getCurrentAccount()
						.getCompanyID() });
		request.setAttribute("companyName", entity.getCompanyName());
		
		DetachedCriteria dc=DetachedCriteria.forClass(COSDimissionStaffVO.class);
		dc.add(Restrictions.eq("companyID", this.getCurrentAccount().getCompanyID()));
		if (search != null && search.equals("search")) {
			this.codi = (COSDimissionStaffVO) session.get("tablesearch");
			if(null != codi.getStaffCode() && !"".equals(codi.getStaffCode()))
			{
				dc.add(Restrictions.like("staffCode", codi.getStaffCode(),MatchMode.ANYWHERE));
			}
			if(null != codi.getStaffName() && !"".equals(codi.getStaffName()))
			{
				dc.add(Restrictions.like("staffName", codi.getStaffName(),MatchMode.ANYWHERE));
			}
			if(null != codi.getStaffIdentityCard() && !"".equals(codi.getStaffIdentityCard()))
			{
				dc.add(Restrictions.like("staffIdentityCard", codi.getStaffIdentityCard(),MatchMode.ANYWHERE));
			}
		}
		dc.addOrder(Order.desc("dimissionDate"));
		return dc;
	}
	
	
	//得到离职员工列表
	public String getListCOSDimission() {
		try {
			pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber),getListBYDC());
		} catch (RuntimeException e) {
			logger.error("操作异常", e);
		}
		return "list";
	}
	/**
	 * 导出离职员工列表
	 * @param : account organizationID
	 * @return : showexcel
	 */
	
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(COSDimissionStaffVO.columnHeadings(), baseBeanService.getListByDC(getListBYDC()));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出离职员工列表", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/************************** 离职员工工资  *********************************/
	/**
	 * 积分汇总（员工工资管理）
	 * 
	 * @return
	 */
	public String getListDimissionSalary() {
		// SalaryIntegral 考评表
		List<Object> parms = new ArrayList<Object>();
		StringBuffer sb_search = null;
		if(arg!=null&&arg.equals("1")){
			SalaryIntegral.setArg("1");
			LogBookServiceImp.setTitle(1);
			sb_search= new StringBuffer(VOtool.getDimissionWagesHqlX());
		}else{
			SalaryIntegral.setArg("0");
			LogBookServiceImp.setTitle(0);
			sb_search= new StringBuffer(VOtool.getDimissionWagesHql());
			
		}
		StringBuffer sb_count = new StringBuffer(
				"select count(*) from DimissionLogBookSummary j where j.logBookKey in "
						+ "( select max(j.logBookKey) from DimissionLogBookSummary j ");		
		sb_search.append(" where j.companyID=? ");
		sb_count.append(" where j.companyID=? ");
		parms.add(this.getCurrentAccount().getCompanyID());
		sb_search.append(" and todaydate between ? and ? ");
		sb_count.append(" and todaydate between ? and ? ");
		parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		if (staffName != null && !"".equals(staffName)) {
			sb_search.append(" and j.staffName like ? ");
			sb_count.append(" and j.staffName like ? ");
			parms.add("%" + staffName.trim() + "%");
		}
		
		if(arg!=null&&arg.equals("1")){
			sb_search.append(" and j.bisect is not null "
					+ "group by to_char(j.todaydate,'yyyy-MM'),j.staffName,j.staffID,j.categoryname order by j.staffName,to_char(j.todaydate, 'yyyy-MM') ");
			sb_count.append(" and j.bisect is not null group by to_char(j.todaydate, 'yyyy-MM'),j.staffID,j.categoryname) ");
		}else{	
		sb_search.append(" and j.bisect is not null "
				+ "group by j.staffName,j.staffID,j.categoryname order by j.staffName ");
		sb_count.append(" and j.bisect is not null group by j.staffID,j.categoryname) ");
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				sb_search.toString(), sb_count.toString(), parms.toArray());
		getDynamicWage(pageForm==null?null:pageForm.getList(),this.getCurrentAccount().getCompanyID());
		return "dimissionSalarylist";
	}
	
	/**
	 * 获取动态工资类别列表
	 * 
	 * @param list
	 */
	private void getDynamicWage(List<BaseBean> list,String comID) {
		String hql = " from CCode where companyID=? and codeStatus=? and wageStatus=? and codePID=?";
		String sqlTail = " from view_DimissionLogBookSummary j  where j.companyID=?  and j.todaydate between ? and ?  and j.staffID=?  and j.bisect is not null ";
		List<BaseBean> addCCodeList = baseBeanService
				.getListBeanByHqlAndParams(hql, new Object[] {
						comID, "01", "00",
						"scode201007306kdf8m76me0000000001" });
		List<BaseBean> cutCCodeList = baseBeanService
				.getListBeanByHqlAndParams(hql, new Object[] {
						comID, "01", "01",
						"scode201007306kdf8m76me0000000001" });
		StringBuffer sqlAdd = new StringBuffer();
		if (addCCodeList != null && addCCodeList.size() > 0) {
			List<Object> filedAdd = new ArrayList<Object>();
			sqlAdd.append("select ");
			for (int j = 0; j < addCCodeList.size(); j++) {
				CCode ccode = (CCode) addCCodeList.get(j);
				filedAdd.add(ccode.getCodeValue());
				sqlAdd.append("sum(case when j.scoreSort = '"
						+ ccode.getCodeID() + "' then j.bisect else '0' end),");
			}
			SalaryIntegral.setWageFiledAdd(filedAdd);
			setAdd(filedAdd);
			sqlAdd.deleteCharAt(sqlAdd.lastIndexOf(","));
			sqlAdd.append(sqlTail);
		}else {
			SalaryIntegral.setWageFiledAdd(null);
		}
		StringBuffer sqlCut = new StringBuffer();
		if (cutCCodeList != null && cutCCodeList.size() > 0) {
			List<Object> filedCut = new ArrayList<Object>();
			sqlCut.append("select ");
			for (int j = 0; j < cutCCodeList.size(); j++) {
				CCode ccode = (CCode) cutCCodeList.get(j);
				filedCut.add(ccode.getCodeValue());
				sqlCut.append("sum(case when j.scoreSort = '"
						+ ccode.getCodeID() + "' then j.bisect else '0' end),");
			}
			SalaryIntegral.setWageFiledCut(filedCut);
			setCut(filedCut);
			sqlCut.deleteCharAt(sqlCut.lastIndexOf(","));
			sqlCut.append(sqlTail);
		}else {
			SalaryIntegral.setWageFiledCut(null);
		}

		List<Object> parms1 = new ArrayList<Object>();
		parms1.add(comID);
		parms1.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		parms1.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));

		int index2 = parms1.size();
		/**
		 * 查询间隔月数  一个月之内为1,俩个月为2
		 */
		int monthNum=DateUtil.getMonthNum(Utilities.getDateFromString(sdate, "yyyy-MM-dd"), Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				SalaryIntegral salaryIntegral = (SalaryIntegral) list.get(i);
				parms1.add(index2, salaryIntegral.getStaffID());
				if(arg!=null&&arg.equals("1")){
					/*if(monthNum==1){
						logger.info("无操作");
					}*/
					if(monthNum>=2){
						if(salaryIntegral.getLogBookKey().equals(sdate.substring(0, 7))){
							parms1.set(1, Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
							try {
								parms1.set(2, Utilities.getDateFromString(DateUtil.getDateOfMonthEnd(sdate,"yyyy-MM-dd"), "yyyy-MM-dd"));
							} catch (ParseException e) {
								logger.error("操作异常", e);
							}
						}
						if(salaryIntegral.getLogBookKey().equals(edate.substring(0, 7))){
							try {
								parms1.set(1, Utilities.getDateFromString(DateUtil.getDateOfMonthBegin(edate,"yyyy-MM-dd"), "yyyy-MM-dd"));
							} catch (ParseException e) {
								logger.error("操作异常", e);
							}
							parms1.set(2, Utilities.getDateFromString(edate, "yyyy-MM-dd"));
						}
						if(!salaryIntegral.getLogBookKey().equals(sdate.substring(0, 7))&&!salaryIntegral.getLogBookKey().equals(edate.substring(0, 7))){
							try {
								parms1.set(1, Utilities.getDateFromString(DateUtil.getDateOfMonthBegin(salaryIntegral.getLogBookKey()+"-01","yyyy-MM-dd"), "yyyy-MM-dd"));
								parms1.set(2, Utilities.getDateFromString(DateUtil.getDateOfMonthEnd(salaryIntegral.getLogBookKey()+"-01" ,"yyyy-MM-dd"), "yyyy-MM-dd"));
							} catch (ParseException e) {
								logger.error("操作异常", e);
							}
						}
					}
				}
				if (addCCodeList != null && addCCodeList.size() > 0) {
					List<Object> filedValueAdd = new ArrayList<Object>();
					Object sqlListAdd = baseBeanService.getObjectBySqlAndParams(
							sqlAdd.toString(), parms1.toArray());
					if (sqlListAdd instanceof Object[]) {
						Object[] obj = (Object[]) (sqlListAdd);
						for (int j = 0; j < obj.length; j++) {
							filedValueAdd.add(obj[j]);
						}
					} else {
						filedValueAdd.add(sqlListAdd);
					}
					salaryIntegral.setCustomWageAdd(filedValueAdd);
				}
				if (cutCCodeList != null && cutCCodeList.size() > 0) {
					List<Object> filedValueCut = new ArrayList<Object>();
					Object sqlListCut = baseBeanService.getObjectBySqlAndParams(
							sqlCut.toString(), parms1.toArray());
					if (sqlListCut instanceof Object[]) {
						Object[] obj1 = (Object[]) (sqlListCut);
						for (int j = 0; j < obj1.length; j++) {
							filedValueCut.add(obj1[j]);
						}
					} else {
						filedValueCut.add(sqlListCut);
					}
					salaryIntegral.setCustomWageCut(filedValueCut);
				}
				parms1.remove(index2);
			}
		}
	}
	
	/**
	 * 打印全部离职员工工资
	 * @return
	 */
	public String printDimissionWages() {
		wages = salaryIntegralList();
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		CAccount account = (CAccount) session.get("account");
		getDynamicWage(wages,account.getCompanyID());
		Company entity = (Company) baseBeanService.getBeanByHqlAndParams(
				" from Company where companyID = ? ", new Object[] { account
						.getCompanyID() });
		request.setAttribute("companyname", entity.getCompanyName());
		titleVar = sdate
				+ "至"
				+ edate
				+ (logoStatusVar != null && !"".equals(logoStatusVar) ? "01"
						.equals(logoStatusVar) ? "工资表" : "培训补助费" : "");
		String organizationID = (String) session.get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"打印全部离职员工工资", account);
		baseBeanService.update(logBook);
		return "printdimissionwage";
	}
	
	/**
	 * 查询全部离职员工工资，供打印调用
	 * @return
	 */
	private List<BaseBean> salaryIntegralList() {
		List<Object> parms = new ArrayList<Object>();
		StringBuffer sb_search = null;
		if(arg!=null&&arg.equals("1")){
			SalaryIntegral.setArg("1");
			LogBookServiceImp.setTitle(1);
			sb_search= new StringBuffer(VOtool.getDimissionWagesHqlX());
		}else{
			SalaryIntegral.setArg("0");
			LogBookServiceImp.setTitle(0);
			sb_search= new StringBuffer(VOtool.getDimissionWagesHql());
			
		}
		StringBuffer sb_count = new StringBuffer(
				"select count(*) from DimissionLogBookSummary j where j.logBookKey in "
						+ "( select max(j.logBookKey) from DimissionLogBookSummary j ");		
		sb_search.append(" where j.companyID=? ");
		sb_count.append(" where j.companyID=? ");
		parms.add(this.getCurrentAccount().getCompanyID());
		sb_search.append(" and todaydate between ? and ? ");
		sb_count.append(" and todaydate between ? and ? ");
		parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		if (staffName != null && !"".equals(staffName)) {
			sb_search.append(" and j.staffName like ? ");
			sb_count.append(" and j.staffName like ? ");
			parms.add("%" + staffName.trim() + "%");
		}
		
		if(arg!=null&&arg.equals("1")){
			sb_search.append(" and j.bisect is not null "
					+ "group by to_char(j.todaydate,'yyyy-MM'),j.staffName,j.staffID,j.categoryname order by j.staffName,to_char(j.todaydate, 'yyyy-MM') ");
			sb_count.append(" and j.bisect is not null group by to_char(j.todaydate, 'yyyy-MM'),j.staffID,j.categoryname) ");
		}else{	
		sb_search.append(" and j.bisect is not null "
				+ "group by j.staffName,j.staffID,j.categoryname order by j.staffName ");
		sb_count.append(" and j.bisect is not null group by j.staffID,j.categoryname) ");
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				sb_search.toString(), sb_count.toString(), parms.toArray());
		return pageForm!=null?pageForm.getList():null;

	}
	
	/**
	 * 单条打印离职员工工资
	 * @return
	 */
	public String printEveryoneDimWages() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		wages = salaryIntegralList();
		getDynamicWage(wages,this.getCurrentAccount().getCompanyID());
		Company entity = (Company) baseBeanService.getBeanByHqlAndParams(
				" from Company where companyID = ? ", new Object[] { this.getCurrentAccount()
						.getCompanyID() });
		request.setAttribute("companyname", entity.getCompanyName());
		if (edate != null && !edate.equals(""))
			request.setAttribute("today", edate);
		String organizationID = (String) session.get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"单个打印离职员工工资", this.getCurrentAccount());
		baseBeanService.update(logBook);
		return "printEveryoneDimWages";
	}
	
	/**
	 * 查询离职人员
	 * @return
	 */
	public String dimissonToCos(){
		String staffID= request.getParameter("staffID");
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { staffID });
		List<CCode> staffTypeList = codeService.getCCodeListByPID(this.getCurrentAccount().getCompanyID(),
				"scode20120912ngiqevnb760000000051");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffName", staff.getStaffName());
		map.put("staffTypeList", staffTypeList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj;
		return "success";
	}
	
	/**
	 * 转在职员工分配专岗
	 * @return
	 */
	public String saveDimToCosDept(){
		String staffID=request.getParameter("staffID");
		String staffName= request.getParameter("staName");
		String orgName=request.getParameter("orgName");
		String deptName=request.getParameter("deptName");
		String companyId=this.getCurrentAccount().getCompanyID();
		String staffTypeId=request.getParameter("staffTypeId");
		String staffTypeName=request.getParameter("staffTypeName");
		//删除离职员工记录
		String hql_dimission = "delete COSDimissionStaff where companyID = ? and staffID=?";
		Object[] parm1 = new Object[]{companyId,staffID};
		//将员工标记为在职(新增加)
		Audition entity=new Audition();
		entity.setAuditionID(serverService.getServerID("audition"));
		entity.setCompanyID(companyId);
		entity.setStaffID(staffID);
		entity.setStaffCategoryID(staffTypeId);
		entity.setCategoryName(staffTypeName);
		entity.setStatus("22");
		
		//将职务级别记录标记为使用状态
		String hql_pshistory = "update PSHistory set status = ? where companyID = ? and staffID = ? ";
		Object[] parm3 = new Object[] { "01", companyId,staffID};
		
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String[] hqls = new String[] { hql_dimission, hql_pshistory };
		List<Object[]> parms = new ArrayList<Object[]>();
		parms.add(parm1);
		parms.add(parm3);
		
		beans.add(entity);
		
		COS cos = new COS();
		cos.setCosID(serverService.getServerID("cos"));
		cos.setCompanyID(companyId);
		cos.setOrganizationID(request.getParameter("orgID"));
		cos.setDepPostID(request.getParameter("deptID"));
		cos.setStaffID(staffID);
		cos.setCosStatus("50");
		cos.setStatus("01");
		
		
		DepartmentPost dp = (DepartmentPost)baseBeanService.getBeanByHqlAndParams("from DepartmentPost p where p.companyID = ? and p.depPostID = ?",  new Object[] { companyId, request.getParameter("deptID")});
		// 设置岗位加一
		if (dp.getAdminNum() == null) {
			dp.setAdminNum("1");
		} else {
			int i = Integer.parseInt(dp.getAdminNum());
			i = i + 1;
			dp.setAdminNum(i + "");
		}
		if (dp.getSpecialpostNum() == null) {
			dp.setSpecialpostNum("1");
		} else {
			int i = Integer.parseInt(dp.getSpecialpostNum());
			i = i + 1;
			dp.setSpecialpostNum(i + "");
		}
		
		parameter = "转在职人员：" + staffName+ "，添加专岗为："+ orgName + " 的 " + deptName;
		CLogBook logBook = logBookService.saveCLogBook(null, parameter,this.getCurrentAccount());
		beans.add(cos);
		beans.add(logBook);
		beans.add(dp);
		baseBeanService.executeHqlsByParamsList(beans,hqls,parms);
		return "success";
	}

	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public List<CCode> getConnectionlist() {
		return connectionlist;
	}
	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}
	public List<CCode> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
	public ShowExcelService getExcelService() {
		return excelService;
	}
	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public COSDimissionStaffVO getCodi() {
		return codi;
	}
	public void setCodi(COSDimissionStaffVO codi) {
		this.codi = codi;
	}
	public CCodeService getCodeService() {
		return codeService;
	}
	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
	}

	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getArg() {
		return arg;
	}
	public void setArg(String arg) {
		this.arg = arg;
	}
	public String getStaffcategoryid() {
		return staffcategoryid;
	}
	public void setStaffcategoryid(String staffcategoryid) {
		this.staffcategoryid = staffcategoryid;
	}
	public List<Object> getAdd() {
		return add;
	}
	public void setAdd(List<Object> add) {
		this.add = add;
	}
	public List<Object> getCut() {
		return cut;
	}
	public void setCut(List<Object> cut) {
		this.cut = cut;
	}
	public List<BaseBean> getWages() {
		return wages;
	}
	public void setWages(List<BaseBean> wages) {
		this.wages = wages;
	}
	public String getLogoStatusVar() {
		return logoStatusVar;
	}
	public void setLogoStatusVar(String logoStatusVar) {
		this.logoStatusVar = logoStatusVar;
	}
}
