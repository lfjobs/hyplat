package com.tiantai.nwa.tbank.action;

import hy.ea.bo.CAccount;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author zhb
 * 
 */
@Controller
@Scope("prototype")
public class BankaccountInOutManager {
		
	@Resource
	private BaseBeanService baseBeanService;	
	
	private List<BaseBean> accountList;
	
	private String banksx;
	private String accountno;
	private String startDate;
	private String endDate;

	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String innerAction;
	private String inout;
	private String pkey;
	private String actionFlag;
	
	// 刚进来显示账户列表
	public String showBankAccountList() {
		if (null!=actionFlag && "showAccountList".equals(actionFlag)){
			getAllBankAccountList();
			ServletActionContext.getRequest().setAttribute("accountList",accountList);
			
			if (null!=innerAction && !"".equals(innerAction))
			{
				if ("in".equals(innerAction)) setInout("收入");
				if ("out".equals(innerAction)) setInout("支出");
			}
			return "showAccountList";
		}else if (null!=actionFlag && "queryInoutList".equals(actionFlag)){
			return queryBankAccountInOutList();
		}
		return "showAccountList";
	}	

	// 获取账户列表
	private void getAllBankAccountList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = " from BankAccount where companyid = ? and orgid = ? ";
		Object[] params = {
				((CAccount) (session.get("account"))).getCompanyID(),
				session.get("organizationID") };
		accountList = baseBeanService.getListBeanByHqlAndParams(hql, params);
	}

	private String queryBankAccountInOutList() {
		getAllBankAccountList();
		ServletActionContext.getRequest().setAttribute("accountList",accountList);
		ServletActionContext.getRequest().setAttribute("pkey",pkey);
		List<Object> list = getInOutList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.lastIndexOf("from")), parms);

		return "queryResultList";
	}

	private List<Object> getInOutList() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (search != null && search.equals("search")) {// 点击查询按钮过来的
			Map<String, String> map = new HashMap<String, String>();
			map.put("banksx", banksx);
			map.put("accountno", accountno);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			pageForm.setPageNumber(0);
			session.put("mapsearch", map);// 将查询条件保存在session中
		} else {// 翻页过来的
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) session
					.get("mapsearch");
			banksx = map.get("banksx");
			accountno = map.get("accountno");
			startDate = map.get("startDate");
			endDate = map.get("endDate");

		}
		StringBuffer sql = new StringBuffer(
				"select rownum as xuhao, pid, banksx, accountno, getdatadate, prov, accno, cur, trdate, "
						+ "timestab, trjrn, trtype, trbankno, accname, amtindex, oppprov, oppaccno, oppcur, oppname, oppbkname, "
						+ "cshindex, errdate, errvchno, amt, bal, preamt, totchg, vouchertype, voucherprov, voucherbat, voucherno, "
						+ "custref, transcode, teller, vchno, abs, postscript, trfrom from t_"
						+ banksx.trim().toLowerCase() + "_cashday where 1=1 ");
		
		if (null!=innerAction && !"".equals(innerAction))
		{
			if ("in".equals(innerAction)) sql.append(" and amt>=0 ");
			if ("out".equals(innerAction)) sql.append(" and amt<0 ");
		}
		
		if (null != accountno && !"".equals(accountno)) {
			sql.append(" and accountno = ? ");
			parms.add(accountno);
		}
		if (null != startDate && !"".equals(startDate) && null != endDate
				&& !"".equals(endDate)) {
			sql.append(" and to_char(trdate,'yyyy-MM-dd')>= ? and  to_char(trdate,'yyyy-MM-dd')<= ?");
			parms.add(startDate);
			parms.add(endDate);
		}
		result.add(sql.toString());
		result.add(parms.toArray());
		return result;
	}

	public String getInnerAction() {
		return innerAction;
	}

	public void setInnerAction(String innerAction) {
		this.innerAction = innerAction;
	}

	public List<BaseBean> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<BaseBean> accountList) {
		this.accountList = accountList;
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

	public String getBanksx() {
		return banksx;
	}

	public void setBanksx(String banksx) {
		this.banksx = banksx;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	
	public String getInout() {
		return inout;
	}

	public void setInout(String inout) {
		this.inout = inout;
	}

	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

}
