package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.NetWork;
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
 * 网络加密公司汇总管理
 */
@Controller
@Scope("prototype")
public class NetWorkCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;

	private NetWork network;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private InputStream excelStream;

	/**
	 * 根据条件查询网络加密公司汇总管理
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", network);
		return getNetWorkCompany();
	}

	/**
	 * 网络加密公司汇总列表
	 * @return
	 */
	public String getNetWorkCompany() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?10:pageNumber),getNList());
		return "networkList";	
	}
	
	/**
	 * 网络加密公司汇总列表、查询、导出调用
	 * @return
	 */
	private DetachedCriteria getNList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(NetWork.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			network = (NetWork) session.get("tablesearch");
			if(null!= network.getNetWorkAddress()&&!"".equals(network.getNetWorkAddress()))
			{
			dc.add(Restrictions.like("netWorkAddress", network.getNetWorkAddress(), MatchMode.ANYWHERE));
			}
			if(null!= network.getNetWorkName()&&!"".equals( network.getNetWorkName()))
			{
			dc.add(Restrictions.like("netWorkName", network.getNetWorkName(),MatchMode.ANYWHERE));
			}
			if(null!=network.getNetWorkCode()&&!"".equals(network.getNetWorkCode()))
			{
			dc.add(Restrictions.like("netWorkCode", network.getNetWorkCode(),MatchMode.ANYWHERE));
			}
		}
		return  dc;
	}

	/**
	 * 导出网络加密公司汇总管理
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(NetWork.columnHeadings(), baseBeanService.getListByDC(getNList()));
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出网络加密公司汇总管理", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}

    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public NetWork getNetwork() {
		return network;
	}

	public void setNetwork(NetWork network) {
		this.network = network;
	}
}