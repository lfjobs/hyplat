package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.bo.human.vo.SalaryIntegral;
import hy.ea.human.service.impl.LogBookServiceImp;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;

public class BPInformationAction {
	@Resource
	private BaseBeanService baseBeanService;
	
	private List<Object> add;
	private List<Object> cut;
	private Object obj;
	private String zg;
	private List<String> jg;
	private String gz;
	
	@SuppressWarnings("unchecked")
	public String getlist(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int year = 0;
        int month = cal.get(Calendar.MONTH); // 上个月月份
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数
        if (month == 0) {
            year = cal.get(Calendar.YEAR) - 1;
            month = 12;
        } else {
        	year = cal.get(Calendar.YEAR);
        }
        String sdate=year+"-"+month+"-01";
        String edate=year+"-"+month+"-"+day;
        
        //判断该帐号是否绑定员工
		if(account.getStaffID()==null){
			String hql = "from CStaffCos where companyID = ?  and staffID = ?";
			Object[] params = { account.getCompanyID(), account.getStaffID() };
			BaseBean baseBean = baseBeanService.getBeanByHqlAndParams(hql, params);
			if (baseBean != null) {
				CStaffCos cosstaff = (CStaffCos) baseBean;
				account.setStaffName(cosstaff.getStaffName());
			}
			return "to_edit";
		}
		
		/******************计算上月该员工工资*****************/
		List<Object> parms = new ArrayList<Object>();
		StringBuffer sb_search = null;
		SalaryIntegral.setArg("0");
		LogBookServiceImp.setTitle(0);
		sb_search= new StringBuffer(VOtool.getWagesHql());
		sb_search.append(" where j.companyID=? ");
		parms.add(account.getCompanyID());
		sb_search.append(" and todaydate between ? and ? ");
		parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		sb_search.append(" and j.staffID = ? ");
		parms.add(account.getStaffID());
		sb_search.append(" and j.bisect is not null "
				+ "group by j.staffName,j.staffID,j.categoryname");
		SalaryIntegral salaryIntegral =(SalaryIntegral)baseBeanService.getObjectByHqlAndParams(sb_search.toString(), parms.toArray());
		getDynamicWage(obj,account.getCompanyID(),sdate,edate);
		if(salaryIntegral!=null){
			gz=salaryIntegral.getObtainedMenoy();
		}else{
			gz="0.0";
		}
		
		/******************查询该员工岗位*****************/
		List<Object> params = new ArrayList<Object>();
		String sql = "select d.postName,c.status from dtCos c left join dt_hr_Staff s on" +
				" c.staffid = s.staffid left join dt_hr_deptpost d on c.deppostid = d.deppostid where ";
		sql += " c.companyid = ? and c.cosStatus = ? ";
		params.add(account.getCompanyID());
		params.add("50");
		sql += " and s.staffID=? ";
		params.add(account.getStaffID());
		List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql, params.toArray());
		if(list.size()>0){
			jg=new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[])list.get(i);
				if(objs[1].equals("01")){
					zg=objs[0].toString();
					continue;
				}
				jg.add(objs[0].toString());
			}
		}
		return "list"; 
	}
	
	/**
	 * 获取动态工资类别列表
	 * @param list
	 * @param comID
	 * @param str
	 */
	private void getDynamicWage(Object obj,String comID,String sdate,String edate) {
		String hql = " from CCode where companyID=? and codeStatus=? and wageStatus=? and codePID=?";
		String sqlTail = "";
		sqlTail = " from view_LogBookSummary j  where j.companyID=?  and j.todaydate between ? and ?  and j.staffID=?  and j.bisect is not null ";
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
		if(obj!=null){
				SalaryIntegral salaryIntegral = (SalaryIntegral) obj;
				parms1.add(index2, salaryIntegral.getStaffID());
				
				if (addCCodeList != null && addCCodeList.size() > 0) {
					List<Object> filedValueAdd = new ArrayList<Object>();
					Object sqlListAdd = baseBeanService.getObjectBySqlAndParams(
							sqlAdd.toString(), parms1.toArray());
					if (sqlListAdd instanceof Object[]) {
						Object[] obj2 = (Object[]) (sqlListAdd);
						for (int j = 0; j < obj2.length; j++) {
							filedValueAdd.add(obj2[j]);
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

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getZg() {
		return zg;
	}

	public void setZg(String zg) {
		this.zg = zg;
	}

	public List<String> getJg() {
		return jg;
	}

	public void setJg(List<String> jg) {
		this.jg = jg;
	}

	public String getGz() {
		return gz;
	}

	public void setGz(String gz) {
		this.gz = gz;
	}
}
