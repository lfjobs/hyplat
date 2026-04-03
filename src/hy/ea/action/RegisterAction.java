package hy.ea.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCustomer;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.DepotManage;
import hy.ea.service.CCodeService;
import hy.ea.service.RegisterService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBean4websiteService;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;


@Controller
@Scope("prototype")
public class RegisterAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private BaseBean4websiteService baseBean4websiteService;
	@Resource
	private RegisterService registerService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	private Company company;
	private TEshopCustomer customer;
	private CDetail companyDetail;
	private CAccount account;
	private String parameter;
	private String result;
	private List<CCode> typelist;
	private ContactCompany contactCompany;   //社会单位
	
	public ContactCompany getContactCompany() {
		return contactCompany;
	}
	public void setContactCompany(ContactCompany contactCompany) {
		
		this.contactCompany = contactCompany;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setCompanyDetail(CDetail companyDetail) {
		this.companyDetail = companyDetail;
	}
	public Company getCompany() {
		return company;
	}
	public CDetail getCompanyDetail() {
		return companyDetail;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public CAccount getAccount() {
		return account;
	}
	public void setAccount(CAccount account) {
		this.account = account;
	}
	public List<CCode> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
	public TEshopCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(TEshopCustomer customer) {
		this.customer = customer;
	}
	/**
	 * 判断一个公司标识是否已经注册
	 * @return
	 */
	public String isRegister(){
		//这里parameter即是公司标识
		boolean isR = registerService.isRegister(parameter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isR", isR);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 判断一个公司用户名是否已经注册
	 * @return
	 */
	public String isAccount(){
		boolean isR=false;
		String hql="from TEshopCustomer where  account = ? AND logOff=0";
		customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{parameter});
		if(customer==null){
			isR=false;
		}else{
			isR=true;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isR", isR);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 注册
	 * @return
	 */
	public String register() {
		//return "failed";
		String companyID=serverService.getServerID("company");
		company.setCompanyID(companyID);
		company.setCompanyPID(serverService.getServerID("pcompany"));
		company.setDistrictID(serverService.getServerID("district"));//这里要改，根据页面来
		company.setGroupCompanySn(serverService.getServerID("groupCompanySn"));
		//注册日期
		company.setCompanyRegisterDate(new Date());
		//默认可以使用5个License
		company.setCompanyLicenseCount(5);
		//指定其状态为正常状态
		company.setCompanyStatus("00");
        company.setComType(company.getComType());
        company.setCcomtype("0");
		
		companyDetail.setDetailID(serverService.getServerID("cdetail"));
		companyDetail.setCompanyID(company.getCompanyID());
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		account.setAccountID(serverService.getServerID("caccount"));
		account.setStaffID(company.getCompanyID());
		account.setCompanyID(company.getCompanyID());
		account.setAccountName("系统管理员");
		//用户登录名暂时为Email
		account.setAccountEmail("sa");
		//加密密码
		account.setAccountPassword(Utilities.MD5(account.getAccountPassword()));
		//指定管理员的状态为正常状态
		account.setAccountStatus("00");
		
		//给网站推送公司信息(这里应该使用jta来进行跨数据库事物，但是鉴于mysql对jta支持的问题，暂时不使用)
		List<BaseBean> beans = new ArrayList<BaseBean>();

		saveDepot(company.getCompanyID(),beans);
		
		///mysql农行项目实体。暂时没有对接所以注释
		/*GUID guid = new GUID();
		String id = guid.toString();
		SiteCompany sc = new SiteCompany();
		sc.setId(id);//id
		sc.setRegionId(378);//缺省为北京市同城区
		sc.setCompanyName(company.getCompanyName());//公司名称
		sc.setTel("010-");//电话
		sc.setFax("010-");//传真
		sc.setIntro("简介");//简介
		sc.setAddress("");//
		
		beans.add(sc);
		
		SiteSite ss = new SiteSite();
		ss.setId(id);//id	
		ss.setName(company.getCompanyName());
		ss.setShortName(company.getCompanyName());
		ss.setClosed(false);
		ss.setVip(false);		
		ss.setRegTime(new Timestamp(System.currentTimeMillis()));
		ss.setCompanyId(company.getCompanyID());
		ss.setRecommon(false);
		ss.setOpenShopping(false);
		
		beans.add(ss);*/
		
		try {
			baseBean4websiteService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//end
		
		if(contactCompany!=null){
			if (!"".equals(contactCompany.getCcompanyID())) {  //修改社会单位中的未注册单位为已注册单位
				String hql = "from ContactCompany where ccompanyID = ?";
				Object[] param = { contactCompany.getCcompanyID() };
				contactCompany = (ContactCompany) baseBeanService
						.getBeanByHqlAndParams(hql, param);
				contactCompany.setCustStatus("02");      //设置单位状态设为已注册单位
				
				return (registerService.register(company,contactCompany,companyDetail, account )==true)?"succcompany, contactComess":"failed";
			}
		}
		
		//注册
		return (registerService.register(company, companyDetail, account)==true)?"success":"failed";
		//return "success";
	
	}
	
	/**
	 * 判断公司能否注册企业
	 * @return
	 */
	public String isCompanyRegister() { 
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account1 = (CAccount) session.get("account");
		String hql = "from Company where companyID = ? and companyStatus = '00' and companyType = '00'";
		Company company1 = (Company)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account1.getCompanyID()});
		typelist = codeService.getCCodeListByPID(account1.getCompanyID(),"scode20110106hfjes5ucxp0000000003");
	   if(company1 != null){
		if("lovers".equals(company1.getCompanyIdentifier()) && "sa".equals(account1.getAccountEmail())){
				return "success";
		  }
	   }
		return "no_authority";
	}
	
	public String validate(){
		Object oValidateCode = ActionContext.getContext().getSession().get(
		"invalidImge");
		int success = 1;
		if(parameter.equalsIgnoreCase((String) oValidateCode)){
			success = 0;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 保存仓库信息
	 * @param companyID
	 * @param beans
	 */
	private void saveDepot(String companyID,List<BaseBean> beans){
		//新增库房
		String[] stra = {
				"股东库",
				"董事会库",
				"监事会库",
				"工会库",
				"顾问会库",
				"董事长室库",
				"总经理室库",
				"人事库",
				"财务库",
				"生产库",
				"营销库",
				"创收库"
		};
		String[] strb = {"实物仓库","资料仓库", "资金仓库", "金币仓库"};
		String[] strs = {"原材料库", "成品库", "销售库", "退货库", "物流库"};
		for (int j = 0; j < stra.length; j++) {
			String apid=saveDepot2(companyID,"001",j+1,stra[j],beans);
			for (int i = 0; i < strb.length; i++){
				if (!stra[j].equals("财务库")&&i>=2){
					break;
				}
				String bpid=saveDepot2(companyID,apid,i+1,strb[i],beans);
				if (stra[j].equals("营销库")&&strb[i].equals("实物仓库")){
					for (int n = 0; n < strs.length; n++) {
						saveDepot2(companyID,bpid,n+1,strs[n],beans);
					}
				}
			}
		}
	}

	/**
	 * 保存仓库信息
	 * @param comid 公司id
	 * @param pid 父级id
	 * @param i 序号
	 * @param name 仓库名
	 * @param beans 仓库集合
	 */
	private String saveDepot2(String comid,String pid,int i,String name,List<BaseBean> beans){
		DepotManage depot = new DepotManage();
		depot.setDepotID(serverService.getServerID("DepotManage"));
		depot.setCompanyID(comid);
		depot.setDepotPID(pid);
		depot.setDepotName(name);
		depot.setDepotNum(i);
		depot.setDepotState("02");
		depot.setDepotType("1");
		beans.add(depot);
		return depot.getDepotID();
	}
}
