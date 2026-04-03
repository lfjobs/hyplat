package hy.ea.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.COA;
import hy.ea.bo.Company;
import hy.ea.bo.human.Audition;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.CLoginService;
import hy.ea.service.CREMIService;
import hy.ea.util.Constant;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCustomer;

@Controller
@Scope("prototype")
public class VLoginAction {
	
	//集团公司id 
	public static final String GROUPCOMPANY_ID = "groupcompany20120523G3VR9PXHZD0000000021";
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private List<BaseBean> beans;
	private Staff staff;
	private Audition audition;
	private COS cos;
	private COA coa;
	private CAccount caccount;
	private String result;
	private String ret1 ;
	private String ret2 ;
	private String ret3 ;
	private String ret4 ;
	private String ret5 ;
	
	private TEshopCustomer customer;
	/**
	 * 注册(微信活动注册改注册到微分金数据库)
	 * @return
	 */
	public String toSave(){
		beans = new ArrayList<BaseBean>();
		//保存社会人力
		staff = findStaff();
		beans.add(staff);
		customer=findCustomer();
		beans.add(customer);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "suc";
		
	}
	
	/**
	 * 查询社会人力信息
	 * @return
	 **/
	private Staff findStaff( ){
		String hql = " from Staff s where s.reference = ? and s.groupCompanySn = ?";
		Object[] obj = new Object[]{staff.getReference(),GROUPCOMPANY_ID};
		Staff s = new Staff();
		s = (Staff)baseBeanService.getBeanByHqlAndParams(hql, obj);
		
		if(s == null){
			staff.setStaffID(serverService.getServerID("staffID"));
			String phql = "select count(*) from Staff ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			staff.setStaffCode("NO" + pcount);
			staff.setRecordCode("NO" + pcount);
			staff.setGroupCompanySn(GROUPCOMPANY_ID);
			staff.setVerifyTime(new Date());
			staff.setStaffStatus("00");
			staff.setStatus("00");
			staff.setStaus("01");
			staff.setSource("微信活动注册");
			return staff;
		}else{
			return s;
		}
	}
	/**
	 * 保存用户名和密码
	 * @return
	 */
	private TEshopCustomer findCustomer(){
		String hql="from TEshopCustomer c where c.staffid=?";
		TEshopCustomer cus=new TEshopCustomer();
		cus=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{staff.getStaffID()});
		if(cus==null){
			customer.setStaffid(staff.getStaffID());
			customer.setAccount(customer.getAccount());
			customer.setPassword(Utilities.MD5(customer.getPassword()));
			ret3 = customer.getAccount();
			ret4 = customer.getPassword();
			return customer;
		}else{
			cus.setAccount(customer.getAccount());
			cus.setPassword(Utilities.MD5(customer.getPassword()));
			ret3 = customer.getAccount();
			ret4 = customer.getPassword();
			return cus;
		}
	}
	
	/**
	 * 
	 * 查询招聘登记.面试.入职管理信息
	 * @return
	 **/
	private int findAudition(){
		
		String hql = "select count(*) from Audition a where a.companyID = ? and a.staffID = ? and a.status = ?";
		Object[] obj = new Object[]{Constant.COMPAYN_ID,staff.getStaffID(),"22"};
		
		return baseBeanService.getConutByByHqlAndParams(hql, obj);
		
	}
	
	/**
	 * 创建COA表数据
	 * @return
	 */
	private COA findCOA(CAccount caccount){
		coa = new COA();
		coa.setCoaID(serverService.getServerID("coaID"));
		coa.setCompanyID(Constant.COMPAYN_ID);
		coa.setOrganizationID(Constant.ORGANIZATION_ID);
		coa.setAccountID(caccount.getAccountID());
		return coa;
	} 
	/**
	 * 创建COS表数据
	 * @return
	 */
	private COS findCOS(){
		cos = new COS();
		cos.setCosID(serverService.getServerID("cosID"));
		cos.setCompanyID(Constant.COMPAYN_ID);
		cos.setOrganizationID(Constant.ORGANIZATION_ID);
		cos.setDepPostID(Constant.POSTNAME_ID);
		cos.setCosStatus("50");
		cos.setStatus("01");
		cos.setStaffID(staff.getStaffID());
		return cos;
	}
	/**
	 * 创建CAccount表数据
	 * @return
	 */
	private CAccount findCAccount(){
		caccount = new CAccount();
		caccount.setAccountID(serverService.getServerID("accountID"));
		caccount.setStaffID(staff.getStaffID());
		caccount.setCompanyID(Constant.COMPAYN_ID);
		caccount.setRoleID(Constant.CROLE_ID);
		caccount.setAccountEmail(staff.getStaffName());
		caccount.setAccountName(staff.getStaffName());
		caccount.setAccountStatus("00");
		caccount.setAccountOnLine("00");
		caccount.setAccountPassword(Utilities.MD5("123456"));
		return caccount;
	}
	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Audition getAudition() {
		return audition;
	}

	public void setAudition(Audition audition) {
		this.audition = audition;
	}

	public COS getCos() {
		return cos;
	}

	public void setCos(COS cos) {
		this.cos = cos;
	}

	public CAccount getCaccount() {
		return caccount;
	}

	public void setCaccount(CAccount caccount) {
		this.caccount = caccount;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public COA getCoa() {
		return coa;
	}

	public void setCoa(COA coa) {
		this.coa = coa;
	}
	public String getRet1() {
		return ret1;
	}
	public void setRet1(String ret1) {
		this.ret1 = ret1;
	}
	public String getRet2() {
		return ret2;
	}
	public void setRet2(String ret2) {
		this.ret2 = ret2;
	}
	public String getRet3() {
		return ret3;
	}
	public void setRet3(String ret3) {
		this.ret3 = ret3;
	}
	public String getRet4() {
		return ret4;
	}
	public void setRet4(String ret4) {
		this.ret4 = ret4;
	}
	public String getRet5() {
		return ret5;
	}
	public void setRet5(String ret5) {
		this.ret5 = ret5;
	}

	public TEshopCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(TEshopCustomer customer) {
		this.customer = customer;
	}

	
}