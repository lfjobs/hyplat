package com.tiantai.nwa.tbank.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.nwa.tbank.bo.AbcDictBean;
import com.tiantai.nwa.tbank.bo.BankAccount;
import com.tiantai.nwa.tbank.bo.BankSX;
import com.tiantai.nwa.util.DockingBankInitUtil;

/**
 * 
 * @author zhb
 * 
 */
@Controller
@Scope("prototype")
public class BankAccountManagerAction {

	@Resource
	private BaseBeanService baseBeanService;

	@Resource
	private ServerService serverService;

	@Resource
	private CLogBookService logBookService;

	private List<BankSX> bankSXList;
	private List<AbcDictBean> provCodeList;
	private List<AbcDictBean> currencyList;

	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String parameter;

	private BankAccount bankAccount;
	private String innerTransCode;

	/**
	 * 显示银行账户列表
	 * 
	 * @return
	 */
	public String getBankAccountList() {
		bankSXList = DockingBankInitUtil.getBankSXList();
		provCodeList = DockingBankInitUtil.getAbcDictListByKey("provCode");
		currencyList = DockingBankInitUtil.getAbcDictListByKey("currency");

		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getList());
		return "bankAccountList";
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		// String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(BankAccount.class);
		dc.add(Restrictions.eq("companyid", companyID));
		// dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			bankAccount = (BankAccount) session.get("tablesearch");
			if (null != bankAccount.getAccount()
					&& !"".equals(bankAccount.getAccount())) {
				dc.add(Restrictions.like("account", bankAccount.getAccount(),
						MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	/**
	 * 添加/修改一个银行账户
	 * 
	 * @return
	 */
	public String saveBankAccount() {
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		//String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");

		if (null == bankAccount.getAccid() || "".equals(bankAccount.getAccid())) {
			bankAccount.setAccid(serverService.getServerID("bankaccount"));
			parameter = "添加银行账户信息(账户:" + bankAccount.getAccount() + ")";
		} else {
			String hql = "from BankAccount where companyID = ? and  accid = ? ";
			BankAccount _bankaccount = (BankAccount) baseBeanService
					.getBeanByHqlAndParams(
							hql,
							new Object[] { account.getCompanyID(),
									bankAccount.getAccid() });
			parameter = "修改银行账户信息(账户:" + _bankaccount.getAccount() + ")";
		}

		List<BaseBean> beans = new ArrayList<BaseBean>();
		//bankAccount.setOrgid(organizationID);
		//bankAccount.setCompanyid(companyID);
		bankAccount.setBindto("0");// 缺省0,绑定到公司
		bankAccount.setCdate(Calendar.getInstance().getTime());
		bankAccount.setMdate(Calendar.getInstance().getTime());
		beans.add(bankAccount);
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	
	/**
	 * 删除一个银行账户
	 * @return
	 */
	public String delBankAccount()
	{	
		Map<String, Object> session = ActionContext.getContext().getSession();
		String organizationID=(String) session.get("organizationID"); 
		CAccount account = (CAccount) session.get("account");
		Object[] params = {account.getCompanyID(),bankAccount.getAccid()};
		String hql2="from BankAccount where companyID= ?  and accid = ? ";
		BankAccount bankAccount = (BankAccount)baseBeanService.getBeanByHqlAndParams(hql2, params);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除银行账户信息(账户:"+bankAccount.getAccount()+")", account);
		String[] hql={"delete from BankAccount where companyID= ?  and accid= ? "};
		List<BaseBean> beans=new ArrayList<BaseBean>();
    	beans.add(logbook);
    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		return "success";
	}

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public List<BankSX> getBankSXList() {
		return bankSXList;
	}

	public void setBankSXList(List<BankSX> bankSXList) {
		this.bankSXList = bankSXList;
	}

	public List<AbcDictBean> getProvCodeList() {
		return provCodeList;
	}

	public void setProvCodeList(List<AbcDictBean> provCodeList) {
		this.provCodeList = provCodeList;
	}

	public List<AbcDictBean> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<AbcDictBean> currencyList) {
		this.currencyList = currencyList;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getInnerTransCode() {
		return innerTransCode;
	}

	public void setInnerTransCode(String innerTransCode) {
		this.innerTransCode = innerTransCode;
	}

}
