package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.NetDisk;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 网络硬盘管理公司汇总
 * @author zl
 *
 */
@Controller
@Scope("prototype")
public class NetDiskCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private NetDisk netDisk;
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	private InputStream excelStream;
	 
	/**
	 * 根据条件查询网络硬盘管理公司汇总
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", netDisk);
		return getNetDiskCompanyList();
	}
	
	/**
	 * 网络硬盘管理公司汇总列表
	 * @return
	 */
	public String getNetDiskCompanyList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "netdisklist";	
	}
	
	/**
	 * 网络硬盘管理公司汇总列表、查询、导出调用
	 * @return
	 */
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(NetDisk.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			netDisk = (NetDisk) session.get("tablesearch");
			if(null!=netDisk.getNetDiskNum()&&!"".equals(netDisk.getNetDiskNum().trim())){
				dc.add(Restrictions.like("netDiskNum", netDisk.getNetDiskNum().trim(), MatchMode.ANYWHERE));
			} 
			if(null!=netDisk.getNetDiskName()&&!"".equals(netDisk.getNetDiskName().trim())){
				dc.add(Restrictions.like("netDiskName", netDisk.getNetDiskName().trim(), MatchMode.ANYWHERE));
			} 
		} 
		return dc;
	}
		
	/**
	 * 导出网络硬盘管理公司汇总
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(NetDisk.columnHeadings(), baseBeanService.getListByDC(getList()));
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出网络硬盘管理公司汇总", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}	 

	public NetDisk getNetDisk() {
		return netDisk;
	}
	public void setNetDisk(NetDisk netDisk) {
		this.netDisk = netDisk;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
}