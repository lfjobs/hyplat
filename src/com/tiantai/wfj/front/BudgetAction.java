package com.tiantai.wfj.front;



import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.BudgetionService;
import com.tiantai.wfj.util.SessionWrap;
/**
 * 项目预算
 * */
@Controller
@Scope("prototype")
public class BudgetAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private BudgetionService budgetService;
	
	private String result;
	private String user;//用户
	private String companyId;//公司id
	private PageForm pageForm;
	private String projectName;//项目预算名称
	private String content;//项目预算内容
	private String cashierBillsID;//项目Id
	private String goodsBillsID;//物品单据id
	private String ppname;
	private String ppId;//产品id
	private String state;//状态
	/**
	 * 项目预算地址
	 */
	public String toBudget(){		
			HttpSession session =ServletActionContext.getRequest().getSession();
			SessionWrap sw = SessionWrap.getInstance();
			TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
					SessionWrap.KEY_SHOPCUSCOM);			
			if(cus==null){
				String url = ServletActionContext.getRequest().getRequestURL()+"?";
				session.setAttribute("url", url);
				return "login";		
			}else{
				companyId=budgetService.toBudget(cus.getAccount());
				user=cus.getAccount();
				state=cus.getState();
			}		
		return "toBudget";
	}
	/**
	 * 添加跳转
	 */
	public String toAddBudget(){		
		return "toAddBudget";
	}
	/**
	 * 预算列表
	 */
	public String ajaxBudget(){
		if(companyId!=null&&companyId.length()>0){
			pageForm=budgetService.budgetPageForm(companyId.trim(),null!=pageForm?pageForm.getPageNumber():1);		
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("pageForm", pageForm);
			JSONObject json=JSONObject.fromObject(map);
			result=json.toString();
		}		
		return "success";
	}
	/**
	 * 保存
	 */
	public String saveBudget(){
		if (companyId!=null&&!companyId.equals("")) {
			if(user!=null&&!user.equals("")){
				budgetService.saveBudget(companyId.trim(), projectName.trim(), content.trim());		
			}		
			}				
		return toBudget();
	}
	/**
	 * 删除项目预算 
	 */
	public String delBudget(){
		JSONObject json;
		if(cashierBillsID!=null&&cashierBillsID.length()>0){
				if (budgetService.delBudget(cashierBillsID.trim())) {
					json = new JSONObject();
					json.accumulate("flag", "1");
					result = json.toString();
				} 			
		}
		return "success";
	}
	/**
	 * 产品详情 
	 */
	public String budgetDetail(){	
		return "budgetDetail";
	}
	public String ajaxbudgetdetail(){
		JSONObject json =new JSONObject();
		if(cashierBillsID!=null&&cashierBillsID.length()>0){
			List<BaseBean> list= budgetService.budgetDetail(cashierBillsID.trim());		
			json.accumulate("list", list);
			result=json.toString();
		}
		return "success";
	}
	/**
	 * 搜索产品
	 */
	public String searchPro(){
		pageForm=budgetService.searchPro(ppname.trim(), pageForm!=null?pageForm.getPageNumber():1);
		return "searchPro";
	}
	/**
	 * 选择产品
	 */	
	public String chancePro(){
		if(goodsBillsID!=null&&goodsBillsID.length()>0){
			if(ppId!=null&&ppId.length()>0){
				budgetService.chancePro(goodsBillsID.trim(), ppId.trim());
			}
		}
		return budgetDetail();
	}
	/**
	 * 加入购物车
	 */
	public String oneKeyJoinCart(){
		if(ppId!=null&&ppId.length()>0){
			if (user!=null&&user.length()>0) {
				if(budgetService.oneKeyJoinCart(ppId.trim(), user.trim())){
					JSONObject json=new JSONObject();
					json.accumulate("flag", "1");
					result=json.toString();
				}
			}
		}
		return "success";
	}
	/**
	 * 删除已选择产品
	 */
	public String delChancePro(){
		if(goodsBillsID!=null&&goodsBillsID.length()>0){
			if(ppId!=null&&ppId.length()>0){
				budgetService.delChancePro(goodsBillsID.trim(), ppId.trim());
			}
		}
		return budgetDetail();
	}
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}	
	public String getGoodsBillsID() {
		return goodsBillsID;
	}
	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}
	public String getPpname() {
		return ppname;
	}
	public void setPpname(String ppname) {
		this.ppname = ppname;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
