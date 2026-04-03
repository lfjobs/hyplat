package com.tiantai.wfj.service;




import java.util.List;


import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

public interface BudgetionService {

	PageForm budgetPageForm(String companyId, Integer pageNumber);

	boolean saveBudget(String companyId, String projectName, String content);

	String toBudget(String user);

	boolean delBudget(String cashierBillsID);
	
	boolean delChancePro(String goodsBillsID, String ppId);
	
	List<BaseBean> budgetDetail(String cashierBillsID);
	
	PageForm searchPro(String ppname, int pageNumber);
	
	boolean oneKeyJoinCart(String ppid, String user);
	/**
	 *选择产品 
	 */
	boolean chancePro(String goodsBillsID, String ppId);
	
	
}