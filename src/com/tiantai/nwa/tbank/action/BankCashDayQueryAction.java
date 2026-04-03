package com.tiantai.nwa.tbank.action;

import hy.ea.bo.CAccount;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.nwa.tbank.bo.BankAccount;

/**
 * 
 * @author zhb
 * 
 */
@Controller
@Scope("prototype")
public class BankCashDayQueryAction {

	private List<BaseBean> accountList;
	private List<BaseBean> cashdayList;
	private BankAccount bankAccount;
	
	private String sdate;
	private String accountno;
	private String banksx;
	private String innerAction;
	
	@Resource
	private BaseBeanService baseBeanService;

	public String showAccountList() {
		if (null!=innerAction && "showAccountList".equals(innerAction)){		
			getAllBankAccountList();
			ServletActionContext.getRequest().setAttribute("accountList",
					accountList);
			return "showAccountList";
		}else if (null!=innerAction && "showCashDayList".equals(innerAction)){
			return showCashDayList();
		}
		return "showAccountList";
	}

	private String showCashDayList() {
		getAllCashDayList();
		ServletActionContext.getRequest().setAttribute("cashdayList",
				cashdayList);
		return "showCashDayList";
	}
	
	//获得银行日结
	private void getAllCashDayList() {		
		String hql = " from TAbcCashday where banksx=? and accountno=? and trdate=to_date(?,'yyyy-MM-dd')";
		Object[] params = {banksx,accountno,sdate};
		cashdayList = baseBeanService.getListBeanByHqlAndParams(hql, params);
		ServletActionContext.getRequest().setAttribute("cashdayList", cashdayList);
	}

	// 获取账户列表
	private void getAllBankAccountList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = " from BankAccount where companyid = ? and orgid = ? ";
		Object[] params = {
				((CAccount) (session.get("account"))).getCompanyID(),
				session.get("organizationID") };
		setAccountList(baseBeanService.getListBeanByHqlAndParams(hql, params));
	}

	public List<BaseBean> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<BaseBean> accountList) {
		this.accountList = accountList;
	}

	public List<BaseBean> getCashdayList() {
		return cashdayList;
	}

	public void setCashdayList(List<BaseBean> cashdayList) {
		this.cashdayList = cashdayList;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getBanksx() {
		return banksx;
	}

	public void setBanksx(String banksx) {
		this.banksx = banksx;
	}

	public String getInnerAction() {
		return innerAction;
	}

	public void setInnerAction(String innerAction) {
		this.innerAction = innerAction;
	}
	
	

}
