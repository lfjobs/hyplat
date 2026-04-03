package com.tiantai.wfj.front;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.EarthIndexService;
import com.tiantai.wfj.service.MyCenterService;
import com.tiantai.wfj.service.Office5L5CService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.BuildPlatform.service.MobileOfficeService;
import hy.ea.bo.CAccount;
import hy.ea.bo.CRole;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@Scope("prototype")
public class Office5L5CAction {

	@Resource
	private EarthIndexService earthIndexService;

	@Resource
	private MyCenterService myCenterService;
	@Resource
	private Office5L5CService office5L5CService;
	@Resource
	private MobileOfficeService moservice;

	private static final String SELECT_TYPE_LOTTERY_DRAW = "lotteryDraw";
	@Resource
	private BaseBeanService baseBeanService;
	private String user;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = ServletActionContext.getRequest().getSession();
	SessionWrap sw = SessionWrap.getInstance();

	/**
	 * 
	 * 5l5c首页
	 * @return
	 */
	public String  index5L5C(){

		String user = request.getParameter("user");

		if(user!=null&&!user.equals("")){

			TEshopCusCom tc = earthIndexService.getCusCom(user);
			TEshopCustomer customer = earthIndexService.getCustomer(user);
			sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
			sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tc);


		}
		 TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
	             SessionWrap.KEY_SHOPCUSCOM);
		 if(tc!=null){
		
			 request.setAttribute("sccId",tc.getSccId());
		 }
		
		 return "index5l5c";
	}

	public String selectCompany(){
		String user = request.getParameter("user");

		if(user!=null&&!user.equals("")){

			TEshopCusCom tc = earthIndexService.getCusCom(user);
			TEshopCustomer customer = earthIndexService.getCustomer(user);
			sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
			sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tc);
		}
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		if(tc!=null){
			request.setAttribute("sccId",tc.getSccId());
			request.setAttribute("bd",SELECT_TYPE_LOTTERY_DRAW);
		}
		return "selectCompany";
	}
	
	/**
	 * 
	 * 5l5C办公首页
	 * @return
	 */
	public String  work5L5C(){


//		 TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
//	             SessionWrap.KEY_SHOPCUSCOM);
//		 if(tc!=null){
//
//                Object obj = myCenterService.getUserInfo(tc.getAccount());
//				request.setAttribute("userinfo", obj);//获取登录的用户信息
//
//		 }
		 String companyID = request.getParameter("companyID");
		String staffID = request.getParameter("staffID");

		ContactCompany concom = moservice.authentication(companyID);

		request.setAttribute("concom", concom);//公司认证信息
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
				new Object[]{staffID});
		StringBuffer sql = new StringBuffer(" from CRole  where roleID IN(SELECT roleId FROM StaffRole where staffId=? and companyId=?)");
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), new Object[]{staffID, companyID});
		String roleID = "";
        String roleName = "";
		CRole crole;
		for(BaseBean baseBean:list){
			crole = (CRole)baseBean;
			roleID += "," + crole.getRoleID();
            roleName += "," + crole.getRoleName();
		}
		if(!roleID.isEmpty()){
			roleID = roleID.substring(1);
            roleName = roleName.substring(1);
		}
		//把公司ID和staffID存在session中方便后续功能获取
		CAccount account = new CAccount();
		account.setCompanyID(companyID);
		account.setStaffID(staffID);
		account.setStaffName(staff.getStaffName());
		account.setRoleID(roleID);
        account.setRoleName(roleName);
		HttpSession session = ServletActionContext.getRequest().getSession();

		Company company = office5L5CService.getCompanyInfo(companyID);
		TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where companyId = ?",new Object[]{companyID});
        account.setCompanyName(company.getCompanyName());
        session.setAttribute("account",account);
      if(tc!=null){
		  request.setAttribute("companyID",companyID);
		  request.setAttribute("sccId",tc.getSccId());
		  request.setAttribute("staffID",staffID);
		  request.setAttribute("user",tc.getAccount());
		  request.setAttribute("companyName",company.getCompanyName());


	  }
	  return "work5l5c";
	  /*if(company!=null&&company.getCcomtype()!=null&&!company.getCcomtype().equals("")){
			return company.getCcomtype();
		}
		 return "1";*/
	}



	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}



    
}
