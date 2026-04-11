package com.tiantai.nwa.tbank.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.nwa.tbank.bean.AccountBalanceBean;
import com.tiantai.nwa.tbank.bean.CallBankRequestBean;
import com.tiantai.nwa.tbank.bean.CallBankReturnBean;
import com.tiantai.nwa.tbank.bo.BankAccount;
import com.tiantai.nwa.tbank.service.CallBankClientService;
import com.tiantai.nwa.tbank.service.impl.CallBankClientServiceImpl;
import com.tiantai.nwa.util.CallBankServiceClientUtil;
import com.tiantai.nwa.util.DockingBankInitUtil;

/**
 * 
 * @author zhb
 * 
 */
@Controller
@Scope("prototype")
public class BankAccountBalanceAction {
	private static final Logger logger = LoggerFactory.getLogger(BankAccountBalanceAction.class);

	private String innerAction;
	private List<BaseBean> accountList;

	@Resource
	private BaseBeanService baseBeanService;
	private BankAccount bankAccount;
	private CallBankRequestBean requestBean;
	private String innerTransCode;
	
	private String account;
	private String banksx;
	private String currency;
	
	private AccountBalanceBean accountBalanceBean;

	/**
	 * 
	 * @return
	 */
	public String getAccountBalance() {
		CallBankServiceClientUtil.setBaseBeanService(baseBeanService);
		if ("accountList".equals(innerAction)) {
			getAllBankAccountList();
			ServletActionContext.getRequest().setAttribute("accountList", accountList);
			return "balance_showAccountList";

		} else if ("accountBalanceQuery".equals(innerAction)) {
			getAccountBalanceQuery();
			return null;
		}

		return "balance_showAccountList";

	}

	// 获取账户列表
	private void getAllBankAccountList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = " from BankAccount where companyid = ? and orgid = ? ";
		Object[] params = {
				((CAccount) (session.get("account"))).getCompanyID(),
				session.get("organizationID") };
		accountList = baseBeanService.getListBeanByHqlAndParams(hql, params);
		
		ServletActionContext.getRequest().setAttribute("accountName", ((CAccount) (session.get("account"))).getAccountName());
	}

	// 查询账户余额
	private void getAccountBalanceQuery() {
		//自动接收交易请求数据  bankAccount  innerTransCode
		CallBankRequestBean reqBean = CallBankServiceClientUtil.makeCallBankRequestBean(bankAccount, innerTransCode);
		try {
			CallBankServiceClientUtil.makeBankParamBean(reqBean);
			CallBankServiceClientUtil.makeRequestDatagramWithBean(reqBean);						
			CallBankClientService cbcs = new CallBankClientServiceImpl();
			CallBankReturnBean returnBean = cbcs.CallBankClientNIOSocket(reqBean);
			
			//returnBean包含了返回的结果，视不同交易返回的结果报文不同，需要分别处理
			makeAccountBalanceBean(returnBean);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("accountBalanceBean", accountBalanceBean);
			
			JSONObject json = JSONObject.fromObject(map);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");			
			response.getWriter().print(json.toString());			
			
		} catch (Exception e) {			
			logger.error("操作异常", e);
		}
	}
	
	private void makeAccountBalanceBean(CallBankReturnBean returnBean)
	{
		accountBalanceBean = new AccountBalanceBean();
		Element acc = returnBean.getAcc();
		
		accountBalanceBean.setAccType(DockingBankInitUtil.getAbcDictBeanByCode("AccType", acc.getChildText("AccType")).getName());
		accountBalanceBean.setAccSts(DockingBankInitUtil.getAbcDictBeanByCode("AccSts", acc.getChildText("AccSts")).getName());
		accountBalanceBean.setFrzAmt(acc.getChildText("FrzAmt"));
		accountBalanceBean.setFrzBal(acc.getChildText("FrzBal"));
		accountBalanceBean.setValUDLmt(acc.getChildText("ValUDLmt"));
		accountBalanceBean.setValMonthLmt(acc.getChildText("ValMonthLmt"));
		accountBalanceBean.setValDayLmt(acc.getChildText("ValDayLmt"));
		accountBalanceBean.setLastAvailBal(acc.getChildText("LastAvailBal"));
		accountBalanceBean.setLastBal(acc.getChildText("LastBal"));
		accountBalanceBean.setAvailBal(acc.getChildText("AvailBal"));
		accountBalanceBean.setBal(acc.getChildText("Bal"));
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

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getInnerTransCode() {
		return innerTransCode;
	}

	public void setInnerTransCode(String innerTransCode) {
		this.innerTransCode = innerTransCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBanksx() {
		return banksx;
	}

	public void setBanksx(String banksx) {
		this.banksx = banksx;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public CallBankRequestBean getRequestBean() {
		return requestBean;
	}

	public void setRequestBean(CallBankRequestBean requestBean) {
		this.requestBean = requestBean;
	}

	public AccountBalanceBean getAccountBalanceBean() {
		return accountBalanceBean;
	}

	public void setAccountBalanceBean(AccountBalanceBean accountBalanceBean) {
		this.accountBalanceBean = accountBalanceBean;
	}
}
