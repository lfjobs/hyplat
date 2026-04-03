package hy.ea.company.action.office;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.AddressIP;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;

/**
 * IP地址公司汇总
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class AddressIPCompanyAction {
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
	private List<BaseBean> staffList; //获得公司下所有活动状态正常的人员
	private int pageNumber;
	
	/**
	 * 根据条件查询IP地址管理公司汇总列表
	 * @return
	 */
	public String toSearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", addressIP);
		return getListIPaddress();
	}
	
	/**
	 * IP地址管理公司汇总列表
	 * @return
	 */
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(AddressIP.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
			if (search != null && search.equals("search")) {
				addressIP = (AddressIP) session.get("tablesearch");
			if (!"".equals(addressIP.getUserName())){
					dc.add(Restrictions.like("userName", addressIP.getUserName()));
			}
			if (!"".equals(addressIP.getIpAddress())){
				dc.add(Restrictions.like("ipAddress", addressIP.getIpAddress(),MatchMode.ANYWHERE));
			}
		}
			return dc;
	}
	
	/**
	 * IP地址管理公司汇总列表、查询、导出调用
	 * @return
	 */
	public String getListIPaddress(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = "from Staff where staffID in (select staffID from COS where companyID = ? and cosStatus='50')";
		Object[] params = { ((CAccount)session.get("account")).getCompanyID() };
		staffList = baseBeanService.getListBeanByHqlAndParams(hql, params);
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
			.getPageNumber() : 1), (pageNumber==0?5:pageNumber),getList());
		return "addressip";
	}
	
	/**
	 * 导出IP地址管理公司汇总列表
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		
		excelStream = excelService.showExcel(AddressIP.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,"导出IP地址管理公司汇总", account);
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
	public List<BaseBean> getStaffList() {
		return staffList;
	}
	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}