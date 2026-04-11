package com.tiantai.wfj.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.util.CookieUtil;
import hy.ea.util.Utilities;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.bo.TEshopExtinfo;
import com.tiantai.wfj.util.SessionWrap;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.DepartmentPost;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.service.RegisterService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBean4websiteService;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;


@Controller
@Scope("prototype")
public class WfjEcusRegisterAction {
	private static final Logger logger = LoggerFactory.getLogger(WfjEcusRegisterAction.class);
	@Resource
	private  BaseBeanService baseBeanService;
	@Resource
	private  ServerService serverService;
	@Resource
	private BaseBean4websiteService baseBean4websiteService;
	@Resource
	private RegisterService registerService;
	private Staff staff;
	private Staff staffInf;
	private List<String> comInfor;
	private List<String> shopInfor;
	private CCode cCode;
	private TEshopCustomer customer;
	private StaffAddress staffAddress;
	private TEshopCusCom shopCusCom;
	private TEshopExtinfo shopinfo;
	private COrganization organization;
	private Company company;
	private CDetail companyDetail;
	private CAccount account;
	private List<BaseBean> listAddress;
	private List<BaseBean> beans;
	private String parameter;
	private String result;
	private String validate;
	private String tradename;
	private String tradeId;
	private String zhuan;
	private String topdl;//注册时候选择上级代理商

	private String user;
	private String ccompanyId;
	private String loginWay;
	/**
	 * 消费者注册
	 * @return
	 */
	public String register() {

		beans = new ArrayList<BaseBean>();
		String shql="from Staff where staffIdentityCard=? and status='00' ";
		Staff s=(Staff) baseBeanService.getBeanByHqlAndParams(shql, new Object[]{staffInf.getStaffIdentityCard().toUpperCase()});
		String staffid=serverService.getServerID("cstaff");
		if(s==null){
			staff=new Staff();
			String phql = "select count(*) from Staff ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			staff.setStaffCode("NO" + pcount);
			staff.setRecordCode("NO" + pcount);
			staff.setStaffStatus("00");
			staff.setVerifyTime(new Date());
			staff.setStaffID(staffid);
			staff.setStaffIdentityCard(staffInf.getStaffIdentityCard().toUpperCase());
			staff.setReference(staffInf.getReference());
			staff.setStaffName(staffInf.getStaffName());
			staff.setSource("微分金");
			staff.setStaus("00");
			staff.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");
			beans.add(staff);
			customer.setStaffid(staffid);
			customer.setPassword(customer.getPassword());
			beans.add(customer);
			if(staffAddress!=null&&staffAddress.getAddressDetailed()!=null&&!"".equals(staffAddress.getAddressDetailed().trim())){
				StaffAddress addr=new StaffAddress();
				addr.setAddressID(serverService.getServerID("address"));
				addr.setStaffID(staffid);
				addr.setAddressDetailed(staffAddress.getAddressDetailed());
				addr.setCompanyID("company201009046vxdyzy4wg0000000025");
				addr.setIsDefault("是");
				beans.add(addr);
			}
		}else{
			staffid=s.getStaffID();
			s.setStaffName(staffInf.getStaffName());
			s.setStaffIdentityCard(staffInf.getStaffIdentityCard().toUpperCase());
			s.setReference(staffInf.getReference());
			s.setStaus("00");
			beans.add(s);
			String hql2="from TEshopCustomer where staffid=?";
			TEshopCustomer cus=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{s.getStaffID()});
			if(cus!=null){
				cus.setAccount(customer.getAccount());
				cus.setPassword(customer.getPassword());
				beans.add(cus);
			}else{
				customer.setStaffid(s.getStaffID());
				customer.setPassword(Utilities.MD5(customer.getPassword()));
				beans.add(customer);
			}
			String hql3="from StaffAddress where staffID=?";
			StaffAddress ad=(StaffAddress) baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{s.getStaffID()});
			if(ad!=null){
				String hql="update StaffAddress set isDefault='否' where staffID=? and isDefault='是'";
				baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{s.getStaffID()});
			}
			StaffAddress addr=new StaffAddress();
			addr.setAddressID(serverService.getServerID("address"));
			addr.setStaffID(s.getStaffID());
			addr.setAddressDetailed(staffAddress.getAddressDetailed());
			addr.setCompanyID("company201009046vxdyzy4wg0000000025");
			addr.setIsDefault("是");

			beans.add(addr);
		}
		//为了分利加上 消费者注册增加一个
		TEshopCusCom tcc=new TEshopCusCom();
		tcc.setAccount(customer.getAccount());
		tcc.setStaffid(customer.getStaffid());
		tcc.setCusType("6");
	    tcc.setSuperioragent(topdl);
        tcc.setSccId(serverService.getServerID("TEshopCusCom"));
		//待定 tcc.Superioragent("a");
		beans.add(tcc);
		ContactRelation re=(ContactRelation) baseBeanService.getBeanByHqlAndParams("from ContactRelation where relation=? and staffID=?", new Object[]{"联合联盟研讨会会员",staffid});
		if(re==null || "".equals(re)){
			ContactRelation relation=new ContactRelation();
			relation.setRelationID(serverService.getServerID("contactrelation"));
			relation.setRelation("联合联盟研讨会会员");
			relation.setStaffID(staffid);
			relation.setCompanyID("company201009046vxdyzy4wg0000000025");
			beans.add(relation);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "tianyang";
	}


	/**
	 * 判断登陆帐号是否已被注册
	 * @return
	 */
	public String isRegister(){

		boolean isR=false;
		String recoveryquestion = null;
		String hql="from TEshopCustomer where  account = ? AND logOff=0";
		customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{parameter});
		if(customer==null){
			isR=false;
		}else{
			isR=true;
			recoveryquestion=customer.getRecoveryquestion();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isR", isR);
		map.put("recoveryquestion", recoveryquestion);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 消费者登陆
	 * @return
	 */
	public void customerLogin(){
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();

		HttpSession session = ServletActionContext.getRequest().getSession();
		String account=customer.getAccount();
		String password=customer.getPassword();
		customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{account});
		try {
			if(customer!=null && account.equals(customer.getAccount()) &&password.equals(customer.getPassword())){
				staff=(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{customer.getStaffid()});
				staffAddress=(StaffAddress) baseBeanService.getBeanByHqlAndParams("from StaffAddress where staffID=? and isDefault='是'", new Object[]{customer.getStaffid()});
				shopCusCom=(TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = ?", new Object[]{customer.getAccount(),"01"});
				if(shopCusCom==null){
					beans = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 order by cusType", new Object[]{customer.getAccount()});
					if(beans.size()>0){ //这个地方只能用size来判断，如果判断是否为null的话会报错的，因为list集合本身是不会为null的
						shopCusCom=(TEshopCusCom)beans.get(0);
					}
					shopCusCom.setAcquiesce("01");
					baseBeanService.update(shopCusCom);
				}

				SessionWrap sw = SessionWrap.getInstance();
				sw.setObject(session, SessionWrap.KEY_STAFF, staff);
				sw.setAddress(session,staffAddress==null?"":staffAddress.getAddressDetailed());
				sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
				sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, shopCusCom);
				String url=(String) session.getAttribute("url");//跳转到登陆前的页面
				//xgb 判断如果跳转为空那么跳转到主页
				if(url==null || url.equals("")){
					String path = request.getContextPath();
					String basePath = request.getScheme() + "://"
							+ request.getServerName() + ":" + request.getServerPort()
							+ path + "/";
					url = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
				}
				response.sendRedirect(url);
			}else{
				if(loginWay!=null && "pcLogin".equals(loginWay)){
					String basePath = request.getScheme() + "://"
							+ request.getServerName() + ":" + request.getServerPort()
							+ request.getContextPath() + "/";
					response.sendRedirect(basePath + "page/newMyapp/login.jsp?error=''");
					return;
				}
				response.sendRedirect(request.getContextPath()+"/ea/wfjshop/ea_register.jspa?user="+user+"&ccompanyId="+ccompanyId+"&paramter=error");
			}
		} catch (IOException e) {
			logger.error("操作异常", e);
		}
	}


	/**
	 * pc登陆
	 * @return
	 */
	public String pcLogin(){
		Map<String,Object> map = new HashMap<String,Object>();

		String account=customer.getAccount();
		String password=customer.getPassword();
		customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{account});
		try {
			if(customer!=null && account.equals(customer.getAccount()) &&password.equals(customer.getPassword())){
				staff=(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{customer.getStaffid()});
				staffAddress=(StaffAddress) baseBeanService.getBeanByHqlAndParams("from StaffAddress where staffID=? and isDefault='是'", new Object[]{customer.getStaffid()});
				shopCusCom=(TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0  and acquiesce = ?", new Object[]{customer.getAccount(),"01"});
				if(shopCusCom==null){
					beans = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 order by cusType", new Object[]{customer.getAccount()});
					if(beans.size()>0){ //这个地方只能用size来判断，如果判断是否为null的话会报错的，因为list集合本身是不会为null的
						shopCusCom=(TEshopCusCom)beans.get(0);
					}
					shopCusCom.setAcquiesce("01");
					baseBeanService.update(shopCusCom);
				}
				HttpSession session = ServletActionContext.getRequest().getSession();
				SessionWrap sw = SessionWrap.getInstance();
				sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
				sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, shopCusCom);

			    //账号正确
				map.put("result", "suc");
			}else{
				map.put("result", "fail");
			}

			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}
	/**
	 * 找回密码
	 * @return
	 */
	public String getPassword(){
		String []hql={"update TEshopCustomer set password=? where account=? and logOff=0"};
		Object []parms={customer.getPassword(),customer.getAccount()};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, parms);
		return "login";
	}

	//购买公司后注册新的公司
	public String companyReg(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if(cus==null){
			zhuan = "1";
			return "companyReg";
		}
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hql = "from Staff where staffId = ?";
		Staff cb = (Staff) baseBeanService.getBeanByHqlAndParams(hql, new Object[] { cus.getStaffid() });

		company.setCompanyID(serverService.getServerID("company"));
		company.setCompanyPID("company201007133na5ggn2u30000000001");
		company.setDistrictID(serverService.getServerID("district"));
		company.setGroupCompanySn(serverService.getServerID("groupCompanySn"));
		company.setCompanyRegisterDate(new Date());
		company.setCompanyLicenseCount(5);
		company.setIsst(0);
		company.setCompanyType("1");
		company.setShowwechat("01");
		company.setCompanyStatus("00");

		companyDetail.setDetailID(serverService.getServerID("cdetail"));
		companyDetail.setCompanyID(company.getCompanyID());

		account.setStaffID(cb.getStaffID());
		account.setAccountID(serverService.getServerID("caccount"));
		account.setCompanyID(company.getCompanyID());
		account.setAccountName("系统管理员");
		// 加密密码
		account.setAccountPassword(Utilities.MD5(account.getAccountPassword()));
		// 指定管理员的状态为正常状态
		account.setAccountStatus("00");
		account.setCompany(company);
		// 给网站推送公司信息(这里应该使用jta来进行跨数据库事物，但是鉴于mysql对jta支持的问题，暂时不使用)
		/*GUID guid = new GUID();
		String id = guid.toString();
		SiteCompany sc = new SiteCompany();

		sc.setId(id);// id
		sc.setRegionId(378);// 缺省为北京市同城区
		sc.setCompanyName(company.getCompanyName());// 公司名称
		sc.setTel("010-");// 电话
		sc.setFax("010-");// 传真
		sc.setIntro("简介");// 简介
		sc.setAddress("");
		beans.add(sc);*/
		
		/*SiteSite ss = new SiteSite();
		ss.setId(id);// id
		ss.setName(company.getCompanyName());
		ss.setShortName(company.getCompanyName());
		ss.setClosed(false);
		ss.setVip(false);
		ss.setRegTime(new Timestamp(System.currentTimeMillis()));
		ss.setCompanyId(company.getCompanyID());
		ss.setRecommon(false);
		ss.setOpenShopping(false);
		beans.add(ss);*/

		TEshopCusCom scc = new TEshopCusCom();
		scc.setSccId(serverService.getServerID("shopcuscom"));
		scc.setCusType("01");
		scc.setCompanyId(account.getCompanyID());
		scc.setStaffid(cus.getStaffid());
		scc.setAccount(account.getAccountEmail());

		TEshopCustomer custom = new TEshopCustomer();
		custom.setStaffid(cus.getStaffid());
		custom.setPassword(account.getAccountPassword());
		custom.setAccount(account.getAccountEmail());
		beans.add(custom);
		beans.add(scc);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
		registerService.register(company, companyDetail, account);
		zhuan = "2";
		return "companyReg";
	}


	//购买店铺后注册新的店铺
	@SuppressWarnings("unused")
	public String shopReg(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if(cus==null){
			zhuan="1";
			return "shopReg";
		}
		zhuan="2";
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hql = "from Staff where staffId = ?";
		Staff cb = (Staff) baseBeanService.getBeanByHqlAndParams(hql,new Object[] { cus.getStaffid() });
		String hql1 = "from COrganization where storageWFJ='00' and companyID = ? ";
		COrganization or = (COrganization) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{organization.getCompanyID()});

		String orgid=serverService.getServerID("oraganization");
		organization.setOrganizationID(orgid);// 部门ID
		/*if(or!=null){
			organization.setOrganizationPID(or.getOrganizationID());// 上级机构ID
		}*/

		organization.setOrganizationCreateDate(new Date());
		if("wu".equals(organization.getCompanyID())){
			organization.setOrganizationPID("organization20150228VQZJI7ZPSV0000003607");
			organization.setCompanyID("company201009046vxdyzy4wg0000000025");
			organization.setOdutiesID(getOrgPostMaxNum("company201009046vxdyzy4wg0000000025"));
			organization.setOcode(getOrgPostMaxNum("company201009046vxdyzy4wg0000000025"));//机构编号
			if(shopinfo.getTradeid()==null||"wu".equals(shopinfo.getTradeid())){
				shopinfo.setTradeid("");//没选行业的时候暂时为空，后续再改
				shopinfo.setTradename("");
			}
		}else{
			organization.setOrganizationPID(or.getOrganizationID());// 上级机构ID
			organization.setCompanyID(organization.getCompanyID());
			shopinfo.setTradename(tradename);
			organization.setOdutiesID(getOrgPostMaxNum(organization.getCompanyID()));
			organization.setOcode(getOrgPostMaxNum(organization.getCompanyID()));//机构编号
		}
		organization.setStatus("00");
		organization.setOpostCode("01");
		organization.setIsWfj("是");
		organization.setOpostName("微分金店");
		organization.setOdutiesName("微分金店");
		organization.setOrganizationUrl("/ea/office/ea_toIncomeDepartment");//创收平台创收
		DepartmentPost departmentPost = new DepartmentPost();// 部门岗位
		departmentPost.setDepPostID(serverService.getServerID("departmentPost"));// 岗位ID
		departmentPost.setCompanyID(organization.getCompanyID());
		departmentPost.setOrganizationID(organization.getOrganizationID());
		departmentPost.setPostNum(organization.getOdutiesID());// 岗位编号
		departmentPost.setPostName(organization.getOdutiesName()); // 岗位名称
		departmentPost.setPostResponsibility(organization.getOrganizationDesc());// 岗位职责
		departmentPost.setResponsibilityRequire(organization.getOpostRequirements());// 任务要求
		departmentPost.setPostSureNum("1");
		departmentPost.setAdminNum("0");
		departmentPost.setSpecialpostNum("0");
		departmentPost.setOmppostNum("0");

		// 微分金 机构负责人
		shopinfo.setOrganizationID(organization.getOrganizationID());
		shopinfo.setRegdate(new Date());
		shopinfo.setEshopstatus("0");
		shopinfo.setInused("1");

		TEshopCusCom scc = new TEshopCusCom();
		scc.setSccId(serverService.getServerID("shopcuscom"));
		scc.setCusType("02");
		scc.setOrganizationID(orgid);
		scc.setStaffid(cus.getStaffid());
		scc.setAccount(customer.getAccount());
		customer.setStaffid(cus.getStaffid());
		customer.setPassword(Utilities.MD5(customer.getPassword()));
		beans.add(customer);
		beans.add(shopinfo);
		beans.add(organization);
		beans.add(departmentPost);
		beans.add(scc);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "shopReg";
	}
	private String getOrgPostMaxNum(String companyID) {

		String hql = "select max(d.postNum) from DepartmentPost d where companyID = ?";
		Object b = baseBeanService.getObjectByHqlAndParams(hql,
				new Object[] { companyID });
		String maxcount = "00";
		if (b != null) {
			int m = Integer.parseInt(b.toString()) + 1;
			maxcount = "00" + m;
		} else {
			maxcount = "000";
		}
		return maxcount;
	}

	//店铺选中行业类别后搜索所有公司
	@SuppressWarnings("unchecked")
	public String companyList(){
		/*String hql = " from SCode where codeID= ?";
		cCode = (CCode) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{tradename});*/
		String hql1="select distinct companyid from dtCCode where codePID= ?";
		beans = baseBeanService.getListBeanBySqlAndParams(hql1, new Object[]{tradeId});
		List<Object> list = new ArrayList<Object>();
		for(Object ob : beans){
			String ob1 = ob.toString();
			String hql3=" from COrganization where  storageWFJ='00' and companyID= ? ";
			COrganization list1 = (COrganization) baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{ob1});
			if(list1!=null){
				String hql2 = " from Company where companyID= ? ";
				company= (Company) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ob1});
				list.add(company);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyList",list==null?"":list);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public List<BaseBean> getBeans() {
		return beans;
	}
	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
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
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public TEshopCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(TEshopCustomer customer) {
		this.customer = customer;
	}
	public List<BaseBean> getListAddress() {
		return listAddress;
	}
	public void setListAddress(List<BaseBean> listAddress) {
		this.listAddress = listAddress;
	}
	public StaffAddress getStaffAddress() {
		return staffAddress;
	}
	public void setStaffAddress(StaffAddress staffAddress) {
		this.staffAddress = staffAddress;
	}
	public Staff getStaffInf() {
		return staffInf;
	}
	public void setStaffInf(Staff staffInf) {
		this.staffInf = staffInf;
	}
	public TEshopCusCom getShopCusCom() {
		return shopCusCom;
	}
	public void setShopCusCom(TEshopCusCom shopCusCom) {
		this.shopCusCom = shopCusCom;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public CDetail getCompanyDetail() {
		return companyDetail;
	}
	public void setCompanyDetail(CDetail companyDetail) {
		this.companyDetail = companyDetail;
	}
	public CAccount getAccount() {
		return account;
	}
	public void setAccount(CAccount account) {
		this.account = account;
	}
	public List<String> getComInfor() {
		return comInfor;
	}
	public void setComInfor(List<String> comInfor) {
		this.comInfor = comInfor;
	}
	public String getTopdl() {
		return topdl;
	}
	public void setTopdl(String topdl) {
		this.topdl = topdl;
	}
	public List<String> getShopInfor() {
		return shopInfor;
	}
	public void setShopInfor(List<String> shopInfor) {
		this.shopInfor = shopInfor;
	}
	public COrganization getOrganization() {
		return organization;
	}
	public void setOrganization(COrganization organization) {
		this.organization = organization;
	}
	public TEshopExtinfo getShopinfo() {
		return shopinfo;
	}
	public void setShopinfo(TEshopExtinfo shopinfo) {
		this.shopinfo = shopinfo;
	}
	public String getTradename() {
		return tradename;
	}
	public void setTradename(String tradename) {
		this.tradename = tradename;
	}
	public CCode getcCode() {
		return cCode;
	}
	public void setcCode(CCode cCode) {
		this.cCode = cCode;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getZhuan() {
		return zhuan;
	}
	public void setZhuan(String zhuan) {
		this.zhuan = zhuan;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCcompanyId() {
		return ccompanyId;
	}
	public void setCcompanyId(String ccompanyId) {
		this.ccompanyId = ccompanyId;
	}
	public String getLoginWay() {
		return loginWay;
	}
	public void setLoginWay(String loginWay) {
		this.loginWay = loginWay;
	}
}
