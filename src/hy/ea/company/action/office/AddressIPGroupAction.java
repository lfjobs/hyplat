package hy.ea.company.action.office;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.AddressIP;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;

/**
 * IP地址集团汇总
 * @author zl
 *
 */
@Controller
@Scope("prototype")
public class AddressIPGroupAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	
	private AddressIP addressIP;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	
	/**
	 * 根据条件查询IP地址管理集团汇总列表
	 * @return
	 */
	public String toSearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", addressIP);
		return getAddressIpGroupList();
	}
	
	/**
	 * IP地址管理集团汇总列表
	 * @return
	 */
	public String getAddressIpGroupList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), hql, params);
		return "addressip";
	}
	
	/**
	 * IP地址管理集团汇总列表、查询、导出调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getList(Map<String, Object> session,
			CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		
		String hql = "from AddressIP t where exists ( select c.companyID from Company c"
				+ " where t.companyID = c.companyID and (c.companyID = ? or companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			addressIP = (AddressIP) session.get("tablesearch");
			if (!"".equals(addressIP.getIpAddress().trim())){
				hql += " and t.ipAddress like ?";
				parms.add("%" + addressIP.getIpAddress().trim() + "%");
			}
		}
		hql += " order by t.companyID desc";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}
	
	/**
	 * 导出IP地址管理集团汇总列表
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList(session, account);
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		
		excelStream = excelService.showExcel(AddressIP.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, parms));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,"导出IP地址管理集团汇总", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}
	
	public AddressIP getAddressIP() {
		return addressIP;
	}
	public void setAddressIP(AddressIP addressIP) {
		this.addressIP = addressIP;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}