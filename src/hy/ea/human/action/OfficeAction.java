package hy.ea.human.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CompanyService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class OfficeAction {
	@Resource
	private COrganizationService organizationService;
	@Resource
	private CompanyService companyService;
	@Resource
	private BaseBeanService baseService;
	private Company company;
	/*private List<Company> companylist;*/
	private String organizationID;
	private Staff searchCStaff;// 保存人员搜索信息
	private String search;// 判断是否搜索人员
	private String result;
	
	private List<COrganization> orgList;//部门列表
	
	private String curOrganizationID;//当前的二级(包含二级)以下部门
	
	private static int gdLevel=1;//0大型1，中型  2.小型  大于1的固定变量展示
	
	private boolean choice(){
		Company c=(Company) ActionContext.getContext().getSession().get("currentcompany");
		if(c.getCcomtype()!=null){
			if(Integer.parseInt(c.getCcomtype())>gdLevel){//返回自定义导航页面
				return true;
			}
		}
		return false;
	}
	               
	/**
	 * 部门入口
	 * 
	 * @return
	 */
	public String getCompanyMessage() {
		if("result".equals(result)){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		company = companyService.getCompanyByCompanyID(account.getCompanyID());
		return "to_office";
		}
		return "to_office_body";
	}
	/**
	 * 查询机构树,根据机构ID和对应单位ID查询
	 */
	public String getOrganizationList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<COrganization> organizationList=new ArrayList<COrganization>();
		map.put("InterfaceUrl", "/ea/office/ea_toSersonnelSystemsum");
		if("0".equals(organizationID)){
			organizationID = account.getCompanyID();
		}
		organizationService.getOrganizationByID(organizationID, account.getCompanyID(), organizationList);
		if(null != organizationList){
		map.put("organizationList", organizationList);
		}
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	//根据登录帐号查询展示组织机构名称
	public String findOrgByAcc(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("InterfaceUrl", "/ea/office/ea_toSersonnelSystemsum");
		if("0".equals(organizationID)){
			organizationID = account.getCompanyID();
		}
		List<COrganization> organizationList=new ArrayList<COrganization>();
		organizationService.findOrgByAcc(account.getAccountID(),organizationList);
		if(null != organizationList){
		map.put("organizationList", organizationList);
		}
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return Action.SUCCESS;
	}
	
	/**
	 * 根据当前机构，获得机构下的直接子机构
	 * @return
	 * @author zhb 2015-1-20 上午11:49:54
	 */
	public String getOrganizationChild(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");				
		String oID = (String)ActionContext.getContext().getSession().get("organizationID");	
		if (null != getCurOrganizationID() && !"".equals(getCurOrganizationID())){//如果是从二级部门点击过来的
			oID = getCurOrganizationID();
		}
		orgList = organizationService.getOrganizationList(oID, account.getCompanyID());	
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != orgList){
			map.put("organizationList", orgList);
		}
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();		
		return "success";
	}
	
	/**
	 * 公司入口
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toSersonnelSystemsum(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		ActionContext.getContext().getSession().put("organizationID", "");
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("companyID", organizationID);
			if(choice()){
				return "fiveMenu";
			}
			return "SersonnelSystemsum";
		}
		if(organizationID.equals(account.getCompanyID())){
			ActionContext.getContext().getSession().put("companyID", organizationID);
			if(choice()){
				return "fiveMenu";
			}
			return "SersonnelSystemsum";
		}
		return "orror";
	}
	/**
	 * 人事部门办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toSersonnelSystem(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "SersonnelSystem";
		}
		return "orror";
	}
	/**
	 * 财务部门办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toFinancial(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_Finance";
		}
		return "orror";
	}

	/**
	 * 办公室部门办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toOffice(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_Office";
		}
		return "orror";
	}
	
	/**
	 * 监事会部门办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toBoarding(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_Boarding";
		}
		return "orror";
	}
	/**
	 * 常委会部门办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toCommittee(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_Committee";
		}
		return "orror";
	}
	/**
	 * 职代会部门办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toCongress(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_Congress";
		}
		return "orror";
	}
	/**
	 * 董事会部门办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toDirector(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_Director";
		}
		return "orror";
	}
	/**
	 * 总经理部门办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toExecutiveoffice(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_Executiveoffice";
		}
		return "orror";
	}
	/**
	 * 招生处部门办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toJobDepartment(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_JobDepartment";
		}
		return "orror";
	} 
	/**
	 * 董事长办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toPresidentroom(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_Presidentroom";
		}
		return "orror";
	}
	/**
	 * 股东会办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toShareholder(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_Shareholder";
		}
		return "orror";
	}
	/**
	 * 教务处办公入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toTeachingAffairsDepartment(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_TeachingAffairsDepartment";
		}
		return "orror";
	}
	/**
	 * 创收平台入口
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String toIncomeDepartment(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		HttpSession session =  ServletActionContext.getRequest().getSession();
		HashMap<String, String> coa = (HashMap)ActionContext.getContext().getSession().get("coa");
		if(null != coa.get(organizationID) && coa.get(organizationID).equals(account.getAccountID())){
			ActionContext.getContext().getSession().put("organizationID", organizationID);
			getOrganization();
			if(choice()){
				return "fiveMenu";
			}
			return "to_IncomeDepartment";
		}
		return "orror";
	}
	
	/**
	 * 个人办公室入口
	 * @return
	 */
	public String toHomeOffice(){
		return "to_Home_office";
	}
	
	private void getOrganization(){
		HttpSession session =  ServletActionContext.getRequest().getSession();
		COrganization cOrganization=organizationService.getCoranizationForOTree(organizationID);
		session.setAttribute("organizationName", cOrganization.getOrganizationName());
	}
	
	/**
	 * 获取不同入口的二级菜单
	 */
	public String getCSec() {
		List<Object> parms = new ArrayList<Object>();
		HttpServletRequest req = ServletActionContext.getRequest();
		String menuType =req.getParameter("menuType");
		String secID =req.getParameter("secID");//一级菜单的id，用于查询二级菜单
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Object soid= ActionContext.getContext().getSession().get("organizationID");
		if(soid==null||soid.equals("")){
			soid=account.getCompanyID();
		}
		String hql="select c from Cmodal c,OrgModal om where om.companyId=c.companyid and om.modalId=c.modalid and om.orgnizationId=? and c.companyid = ? ";
		
		parms.add(soid);
		parms.add(account.getCompanyID());
		if(isroot(secID)){
			String[] arr=finddaohang(secID);
			hql+=" and c.pmodalid in (?,?,?)";
			parms.add(arr[0]);
			parms.add(arr[1]);
			parms.add(arr[2]);
		}else{
			hql+=" and c.pmodalid=?";
			parms.add(secID);
		}
		hql+=" and status = ? order by sortsn";
		parms.add("01");
		List<BaseBean> menus= baseService.getListBeanByHqlAndParams(hql, parms.toArray());
		
		ActionContext.getContext().put("menus", menus);
		if(menuType!=null&&menuType.equals("1")){
			return "newsecTree";
		}else{
			return "newsec";
		}
	}
	private boolean isroot(String menuid){
		List<String> lst=new ArrayList<String>();
		lst.add("Smodal201510212dgi44rx470000000015");//人事
		lst.add("Smodal201510212dgi44rx470000000016");//办公室
		lst.add("Smodal201510212dgi44rx470000000017");//财务
		lst.add("Smodal201510212dgi44rx470000000018");//生产
		lst.add("Smodal201510212dgi44rx470000000019");//营销
		if(lst.contains(menuid)){
			return true;
		}
		return false;
	}
	private String[] finddaohang(String menuid){
		Map<String,String[]> map=new HashMap<String,String[]>();
		//人事
		map.put("Smodal201510212dgi44rx470000000015",new String[]{"Smodal20151023jwfyupetd80000000003","Smodal20151023jwfyupetd80000000002","Smodal20151023jwfyupetd80000000004"});//人事公司导航
		map.put("Smodal201510212dgi44rx470000000016",new String[]{"Smodal20151029y7zus4uurr0000000002","Smodal20151029y7zus4uurr0000000003","Smodal20151029y7zus4uurr0000000004"});
		map.put("Smodal201510212dgi44rx470000000017",new String[]{"Smodal20151029y7zus4uurr0000000005","Smodal20151029y7zus4uurr0000000006","Smodal20151029y7zus4uurr0000000007"});
		map.put("Smodal201510212dgi44rx470000000018",new String[]{"Smodal20151029y7zus4uurr0000000008","Smodal20151029y7zus4uurr0000000009","Smodal20151029y7zus4uurr0000000010"});
		map.put("Smodal201510212dgi44rx470000000019",new String[]{"Smodal20151029y7zus4uurr0000000011","Smodal20151029y7zus4uurr0000000012","Smodal20151029y7zus4uurr0000000013"});
		return map.get(menuid);
	}
	
	//查询单独的五大部门
	public String sajaxGetThenFiveDepartmentsList(){
		CAccount account=(CAccount)ActionContext.getContext().getSession().get("account");
		Map<String, Object> map=new HashMap<String, Object>();
		List<BaseBean> orgList=new ArrayList<BaseBean>();
		orgList=organizationService.getThenFiveDepartments(account.getCompanyID());
		map.put("list",orgList);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	
	public Staff getSearchCStaff() {
		return searchCStaff;
	}

	public void setSearchCStaff(Staff searchCStaff) {
		this.searchCStaff = searchCStaff;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	public List<COrganization> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<COrganization> orgList) {
		this.orgList = orgList;
	}
	public String getCurOrganizationID() {
		return curOrganizationID;
	}
	public void setCurOrganizationID(String curOrganizationID) {
		this.curOrganizationID = curOrganizationID;
	}
}
