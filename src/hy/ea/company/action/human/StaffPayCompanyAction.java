package hy.ea.company.action.human;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.bo.human.vo.SalaryIntegral;
import hy.ea.human.service.impl.LogBookServiceImp;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 集团 --在职员工工资汇总
 * 
 * @author lwz
 * 
 */
@Controller
@Scope("prototype")
public class StaffPayCompanyAction {
	private static final Logger logger = LoggerFactory.getLogger(StaffPayCompanyAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private CStaffCos cosStaff ;
	private InputStream excelStream;
	private String sdate;
	private String edate;
	private String staffName;
	private String arg;
	private List<Object> add;
	private List<Object> cut;
	private String staffcategoryid;
	private String comID;
	
	/**
	 * 查询
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				cosStaff);
		return getStaffPayList();
	}
	
	/**
	 * 加载
	 * @return
	 */
	
	public String getStaffPayList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		// SalaryIntegral 考评表
		List<Object> parms = new ArrayList<Object>();
		StringBuffer sb_search = null;
		if(arg!=null&&arg.equals("1")){
			SalaryIntegral.setArg("1");
			LogBookServiceImp.setTitle(1);
			 sb_search= new StringBuffer(VOtool.getWagesHqlX());
		}else{
			SalaryIntegral.setArg("0");
			LogBookServiceImp.setTitle(0);
			 sb_search= new StringBuffer(VOtool.getWagesHql());
			
		}
		StringBuffer sb_count = new StringBuffer(
				"select count(*) from LogBookSummary j where j.logBookKey in "
						+ "( select max(j.logBookKey) from LogBookSummary j ");		
		sb_search.append(" where exists ( select c.companyID from Company c"
				+ " where j.companyID = c.companyID and (c.companyID = ? or c.companyPID = ?))");
		sb_count.append(" where exists ( select c.companyID from Company c"
				+ " where j.companyID = c.companyID and (c.companyID = ? or c.companyPID = ?))");
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		sb_search.append(" and todaydate between ? and ? ");
		sb_count.append(" and todaydate between ? and ? ");
		parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		if (staffName != null && !"".equals(staffName)) {
			sb_search.append(" and j.staffName like ? ");
			sb_count.append(" and j.staffName like ? ");
			parms.add("%" + staffName.trim() + "%");
		}
		if(staffcategoryid!=null&&!"".equals(staffcategoryid)){
			sb_search.append(" and j.staffcategoryid= ? ");
			sb_count.append(" and j.staffcategoryid= ? ");
			parms.add(staffcategoryid);
		}
		if(comID!=null&&!"".equals(comID)){
			sb_search.append(" and j.companyID= ? ");
			sb_count.append(" and j.companyID= ? ");
			parms.add(comID);
		}
		if(arg!=null&&arg.equals("1")){
			sb_search.append(" and j.bisect is not null "
					+ "group by to_char(j.todaydate,'yyyy-MM'),j.staffName,j.staffID,j.categoryname,j.companyID order by j.companyID,to_char(j.todaydate, 'yyyy-MM') ");
			sb_count.append(" and j.bisect is not null group by to_char(j.todaydate, 'yyyy-MM'),j.staffID,j.categoryname) ");
		}else{	
			sb_search.append(" and j.bisect is not null "
					+ "group by j.staffName,j.staffID,j.categoryname,j.companyID order by j.companyID ");
			sb_count.append(" and j.bisect is not null group by j.staffID,j.categoryname) ");
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				sb_search.toString(), sb_count.toString(), parms.toArray());
		getDynamicWage(pageForm==null?null:pageForm.getList(),account.getCompanyID());
		return "getStaffPayList";
	}
	/**
	 * 获取动态工资类别列表
	 * 
	 * @param list
	 */
	private void getDynamicWage(List<BaseBean> list,String comID) {

		String hql = " from CCode where companyID=? and codeStatus=? and wageStatus=? and codePID=?";
		String sqlTail = " from view_LogBookSummary j  where j.companyID=?  and j.todaydate between ? and ?  and j.staffID=?  and j.bisect is not null ";
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
								// TODO Auto-generated catch block
								logger.error("操作异常", e);
							}
						}
						if(salaryIntegral.getLogBookKey().equals(edate.substring(0, 7))){
							try {
								parms1.set(1, Utilities.getDateFromString(DateUtil.getDateOfMonthBegin(edate,"yyyy-MM-dd"), "yyyy-MM-dd"));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								logger.error("操作异常", e);
							}
							parms1.set(2, Utilities.getDateFromString(edate, "yyyy-MM-dd"));
						}
						if(!salaryIntegral.getLogBookKey().equals(sdate.substring(0, 7))&&!salaryIntegral.getLogBookKey().equals(edate.substring(0, 7))){
							try {
								parms1.set(1, Utilities.getDateFromString(DateUtil.getDateOfMonthBegin(salaryIntegral.getLogBookKey()+"-01","yyyy-MM-dd"), "yyyy-MM-dd"));
								parms1.set(2, Utilities.getDateFromString(DateUtil.getDateOfMonthEnd(salaryIntegral.getLogBookKey()+"-01" ,"yyyy-MM-dd"), "yyyy-MM-dd"));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
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
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public CStaffCos getCosStaff() {
		return cosStaff;
	}

	public void setCosStaff(CStaffCos cosStaff) {
		this.cosStaff = cosStaff;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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

	public String getStaffcategoryid() {
		return staffcategoryid;
	}

	public void setStaffcategoryid(String staffcategoryid) {
		this.staffcategoryid = staffcategoryid;
	}

	public String getComID() {
		return comID;
	}

	public void setComID(String comID) {
		this.comID = comID;
	}

	
}
