package hy.ea.ddsr.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffContact;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class NoticeAction {
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
	private List<CCode> contactTypelist;
	private StaffContact contact;
	private List<BaseBean> contactList;
	
	private String result;
	private String parameter;
	private int pageNumber;
	private Map<String,StaffContact> contactmap;
	private List<BaseBean> beans;

	/**
	 * @since 2010.10.28
	 * @author robinson
	 */
	public String showExcel() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Map<String, String> map = new HashMap<String, String>();
		contactTypelist = codeService.getCCodeListByPID(account.getCompanyID(),"scode201004233ern4m24yx0000000262");
		for (CCode b : contactTypelist) {
			map.put(b.getCodeID(),b.getCodeValue());
		}
		StaffContact.setOMap(map);
		Object[] params = {contact.getStaffID() };
		String hql = " from StaffContact where staffID = ? order by contactID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel( StaffContact
				.columnHeadings(), list);
		CLogBook logBook = logBookService.saveCLogBook(null,"导出联系人", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * @since 2010.10.28
	 * @author robinson
	 * info:添加或修改联系人
	 */
	public String saveContact() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		contact = new StaffContact();
		beans = new ArrayList<BaseBean>();
		if(null!=contactmap){
			for(StaffContact ct:contactmap.values()){
				this.contact.setStaffID(ct.getStaffID());
				if(null==ct.getContactID()||"".equals(ct.getContactID())){
					ct.setContactID(serverService.getServerID("contact"));
					parameter = "添加联系人";
				}else{
					parameter = "修改联系人";
				}  
				String[] hql2={"from Staff where staffID=?"};
				Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2[0], new Object[]{ct.getStaffID()});
				parameter += "(人员名称:"+staff.getStaffName()+")";
				ct.setCompanyID(account.getCompanyID());
				
				beans.add(ct);
				
			}
			
			CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		} 
		return "succ";
	}
	/**
	 * 删除联系人
	 * @return
	 */
	public String delContact() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = "delete StaffContact where staffID= ? and contactID = ?";
		Object[] params = { contact.getStaffID(),
				contact.getContactID() };
		String hql2="from Staff where staffID=?";
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{contact.getStaffID()});
		beans = new ArrayList<BaseBean>();
	    CLogBook logBook = logBookService.saveCLogBook(null,"删除联系人(人员名称："+ staff.getStaffName()+")", account);
	    beans.add(logBook);
	    baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		return "succ";
	}

	/**
	 * 根据电话号码获取个人信息
	 * @return
	 */
	public String getStaffByTelno(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "from Staff where staffID in (select staffID from StaffContact where contactWay = ?)";
		Object[] params = { parameter };
		List<BaseBean> staffList = baseBeanService.getListBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("staffList", staffList);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
	}
	
	// 根据单位和登录人查询联系人列表
	public String getListContact() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		contactTypelist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201004233ern4m24yx0000000262");
		Object[] params = { contact.getStaffID() };
		
		contactList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffContact where staffID = ? order by contactID desc", params);
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

	public StaffContact getContact() {
		return contact;
	}

	public void setContact(StaffContact contact) {
		this.contact = contact;
	}

	public List<CCode> getContactTypelist() {
		return contactTypelist;
	}

	public void setContactTypelist(List<CCode> contactTypelist) {
		this.contactTypelist = contactTypelist;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Map<String, StaffContact> getContactmap() {
		return contactmap;
	}

	public void setContactmap(Map<String, StaffContact> contactmap) {
		this.contactmap = contactmap;
	}

	public List<BaseBean> getContactList() {
		return contactList;
	}

	public void setContactList(List<BaseBean> contactList) {
		this.contactList = contactList;
	}
}
