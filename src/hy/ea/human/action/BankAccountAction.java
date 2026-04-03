package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffBankAccount;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class BankAccountAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;	
	private StaffBankAccount bankAccount;
	private List<BaseBean> bankAccountList;
	
	private String result;
	private String parameter;
	private Map<String,StaffBankAccount> bankAccountmap;
	private List<BaseBean> beans;

	/**
	 * @since 2010.10.28
	 * @author robinson
	 */
	public String showExcel() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = {bankAccount.getStaffID() };
		String hql = " from StaffBankAccount where staffID = ? order by bankAccountID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel( StaffBankAccount
				.columnHeadings(), list);
		CLogBook logBook = logBookService.saveCLogBook(null,"导出银行帐号", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * @since 2010.10.28
	 * @author robinson
	 * info:添加或修改银行帐号
	 */
	public String saveBankAccount() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		bankAccount = new StaffBankAccount();
		beans = new ArrayList<BaseBean>();
		if(null!=bankAccountmap){
			for(StaffBankAccount ct:bankAccountmap.values()){
				this.bankAccount.setStaffID(ct.getStaffID());
				if(null==ct.getBankAccountID()||"".equals(ct.getBankAccountID())){
					ct.setBankAccountID(serverService.getServerID("bankAccount"));
					parameter = "添加银行帐号";
				}else{
					parameter = "修改银行帐号";
				}  
				String[] hql2={"from Staff where staffID=?"};
				Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2[0], new Object[]{ct.getStaffID()});
				parameter += "(人员名称:"+staff.getStaffName()+")";
				beans.add(ct);
			}
			
			CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		} 
		return "succ";
	}
	/**
	 * 删除银行帐号
	 * @return
	 */
	public String delBankAccount() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = "delete StaffBankAccount where staffID= ? and bankAccountID = ?";
		Object[] params = {  bankAccount.getStaffID(),
				bankAccount.getBankAccountID() };
		beans = new ArrayList<BaseBean>();
		String hql2="from Staff where staffID=?";
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{bankAccount.getStaffID()});
		CLogBook logBook = logBookService.saveCLogBook(null,"删除银行帐号(人员名称："+ staff.getStaffName()+")", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		return "succ";
	}

	
	// 根据单位和登录人查询帐号列表
	public String getListBankAccount() {
		Object[] params = { bankAccount.getStaffID() };
		bankAccountList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffBankAccount where staffID = ? order by bankAccountID desc", params);
		
		return "list";
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	

	public StaffBankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(StaffBankAccount bankAccount) {
		this.bankAccount = bankAccount;
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

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public CLogBookService getLogBookService() {
		return logBookService;
	}

	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}

	public CCodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
	}

	public Map<String, StaffBankAccount> getBankAccountmap() {
		return bankAccountmap;
	}

	public void setBankAccountmap(Map<String, StaffBankAccount> bankAccountmap) {
		this.bankAccountmap = bankAccountmap;
	}

	public List<BaseBean> getBankAccountList() {
		return bankAccountList;
	}

	public void setBankAccountList(List<BaseBean> bankAccountList) {
		this.bankAccountList = bankAccountList;
	}
}