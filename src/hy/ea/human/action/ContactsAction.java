package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.ContactConnection;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.company.Contactresource;
import hy.ea.bo.human.Staff;
import hy.ea.service.CCodeService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class ContactsAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ServerService serverService;
	
	private ContactCompany contact;//单位
	private Staff astaff;
	private String result;
	private List<CCode> typelist;
	private Contactresource contactresource ; //显示隐藏
	private String showType;
	private String pid;
	
	public String toSaveJsp(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110106hfjes5ucxp0000000003");
		String hql = "from Contactresource where companyid = ?";
		contactresource = (Contactresource)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
		return null;
	}
	//公司粉丝
	public String addUnit(){
		List<BaseBean> list=new ArrayList<BaseBean>();
		String sql = "select t.CUSTID,te.COMPANYID,s.GROUPCOMPANYSN,s.STAFFID,s.STAFFNAME from T_ESHOP_CUSTOMER t,"
				   +" T_ESHOP_CUSCOM te,DT_HR_STAFF s where t.CUSTID=?" 
				   +"and t.STAFFID=s.STAFFID "
				   +"AND t.ACCOUNT=te.ACCOUNT";

		List<Object> lists = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{pid});
		Object jo = lists.get(0);
		Object[] b = (Object[])jo;
		ContactConnection conts = new ContactConnection();
		//自动生成的
		contact.setCcompanyID(serverService.getServerID("contactCompany"));
		contact.setAccountID(pid);
		contact.setAccountName(b[4].toString());
		conts.setCompanyID(b[1].toString());
		conts.setCcompanyID(contact.getCcompanyID()); 
		conts.setContactConnections("公司粉丝");
		conts.setContactConnectionID(serverService.getServerID("ContactConnection"));
		list.add(conts);
		list.add(contact);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null,null);
		
		return "addUnit";
	}
	//个人粉丝
	public String addPersonal(){
		try {
			List<BaseBean> list=new ArrayList<BaseBean>();
			String sql = "select t.CUSTID,te.COMPANYID,s.GROUPCOMPANYSN,s.STAFFID,s.STAFFNAME from T_ESHOP_CUSTOMER t,"
					   +" T_ESHOP_CUSCOM te,DT_HR_STAFF s where t.CUSTID=?" 
					   +"and t.STAFFID=s.STAFFID "
					   +"AND t.ACCOUNT=te.ACCOUNT";

			List<Object> lists = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{pid});
			Object jo = lists.get(0);
			Object[] b = (Object[])jo;
	
			
			astaff.setStaffID(serverService.getServerID("cstaff"));
			astaff.setGroupCompanySn(b[2].toString());
			astaff.setAccountID(pid);
			astaff.setAccountName(b[4].toString());
			ContactRelation conta = new ContactRelation();
			conta.setRelationID((serverService.getServerID("ContactRelation")));
			conta.setCompanyID(serverService.getServerID("company"));
			conta.setStaffID(astaff.getStaffID());
			conta.setRelation("个人粉丝");
			list.add(conta);
			list.add(astaff);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null,null);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "addPersonal";
	}
	
	public String juem(){
		
		
		return "juem";
	}
	
	
	/**
	 * 
	 * 判断手机号是否已经是粉丝
	 * @return
	 */
	public String ajaxIsFans(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String gsrtype = request.getParameter("gsrtype");
		String phone = request.getParameter("phone");
		String hql  = "";
		List<BaseBean> list = null;
		String fans = "";
		Map<String,Object> map = new HashMap<String,Object>();
		if(gsrtype.equals("phonegeren")){
		    hql = "from Staff s where s.reference = ? and s.staffID in(select c.staffID from ContactRelation c where c.relation = ?)";
		    fans ="个人粉丝";
		}else{
			hql = "from ContactCompany s where s.responsibleTel = ? and s.ccompanyID in(select c.ccompanyID from ContactConnection c where c.contactConnections = ?)";
			fans="公司粉丝";
		}
		
		list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{phone,fans});
		if(list.size()==0){
			map.put("result",true);
		}else{
			map.put("result",false);
		}
		
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
		
		
	}
	
	/**
	 * 
	 * 获取行业
	 * @return
	 */
	public String ajaxIndustryList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String codeID = request.getParameter("codeID");
		List<CCode> codeList = codeService.getCCodeListByPID("company201009046vxdyzy4wg0000000025", codeID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeList", codeList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	
	public List<CCode> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
	public Contactresource getContactresource() {
		return contactresource;
	}
	public void setContactresource(Contactresource contactresource) {
		this.contactresource = contactresource;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public ContactCompany getContact() {
		return contact;
	}
	public void setContact(ContactCompany contact) {
		this.contact = contact;
	}
	public Staff getAstaff() {
		return astaff;
	}
	public void setAstaff(Staff astaff) {
		this.astaff = astaff;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}

}
