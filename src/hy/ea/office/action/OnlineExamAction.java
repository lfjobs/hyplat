package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.AddressIP;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class OnlineExamAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private AddressIP addressIP;
	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;
	private List<BaseBean> staffList;
	private int pageNumber;
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", addressIP);
		return getListIPaddress();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(AddressIP.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationID", (String) session.get("organizationID")));
		if (search != null && search.equals("search")) {
			addressIP = (AddressIP) session.get("tablesearch");
		if (!"".equals(addressIP.getUserName())){
				dc.add(Restrictions.like("userName", addressIP.getUserName(),MatchMode.ANYWHERE));
		}
		if (!"".equals(addressIP.getIpAddress())){
			dc.add(Restrictions.eq("ipAddress", addressIP.getIpAddress()));
		}
	}
		return dc;
}

	public String getListIPaddress(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = " from Staff where staffID in (select staffID from COS where companyID = ? and cosStatus = '50' ) ";
		Object[] params = { ((CAccount)session.get("account")).getCompanyID() };
		staffList = baseBeanService.getListBeanByHqlAndParams(hql, params);
	   pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
			.getPageNumber() : 1), (pageNumber==0?5:pageNumber),getList());
		return "addressip";
	}

	public String showExcel() {
		excelStream = excelService.showExcel(AddressIP.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,"导出IP管理", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	public String addIPaddress() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (addressIP.getIpID() == null
				|| "".equals(addressIP.getIpID())) {
			addressIP.setIpID(serverService.getServerID("addressIP"));
			parameter = "添加IP管理(IP地址:"+addressIP.getIpAddress()+")";
		}
		else
		{
		 String hql2="from AddressIP where companyID=?  and ipID=?";
		 AddressIP aff=(AddressIP) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),addressIP.getIpID() });
		 parameter = "修改IP管理(IP地址:"+aff.getIpAddress()+")";
		
		}
		addressIP.setOrganizationID(organizationID);
		addressIP.setCompanyID(account.getCompanyID());
		//baseBeanService.update(addressIP);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(addressIP);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}

	public String delIPaddress() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), addressIP.getIpID()};
	    String hql2="from AddressIP where companyID=?  and ipID=?";
        AddressIP aff=(AddressIP) baseBeanService.getBeanByHqlAndParams(hql2, params);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除IP管理(IP地址:"+aff.getIpID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql = "delete from AddressIP where companyID=?  and ipID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql}, params);
		return "success";
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<BaseBean> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public AddressIP getAddressIP() {
		return addressIP;
	}

	public void setAddressIP(AddressIP addressIP) {
		this.addressIP = addressIP;
	}
	
}
