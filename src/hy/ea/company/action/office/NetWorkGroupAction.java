package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.NetWork;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 网络加密集团汇总管理
 */
@Controller
@Scope("prototype")
public class NetWorkGroupAction {
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
	 * 根据条件查询网络加密集团汇总管理
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", network);
		return getNetWorkGroup();
	}

	/**
	 * 网络加密集团汇总列表
	 * @return
	 */
	public String getNetWorkGroup() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "networkList";	
	}
	
	/**
	 * 网络加密集团汇总列表、查询、导出调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getList(Map<String, Object> session,
			CAccount account){
		List<Object> results = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		
		String hql = "from NetWork t where exists ( select c.companyID from Company c"
				+ " where t.companyID = c.companyID and (c.companyID = ? or companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		
		if (search != null && search.equals("search")){
			network = (NetWork) session.get("tablesearch");
			if(null != network.getNetWorkAddress() 
					&& !"".equals(network.getNetWorkAddress().trim())) {
				hql += " and t.netWorkAddress like ?";
				parms.add("%" +network.getNetWorkAddress().trim() + "%");
			}
			if(null != network.getNetWorkName() 
					&& !"".equals(network.getNetWorkName().trim())) {
				hql += " and t.netWorkName like ?";
				parms.add("%" +network.getNetWorkName().trim() + "%");
			}
			if(null != network.getNetWorkCode() 
					&& !"".equals(network.getNetWorkCode().trim())) {
				hql += " and t.netWorkCode like ?";
				parms.add("%" +network.getNetWorkCode().trim() + "%");
			}
		}
		hql += " order by t.companyID desc";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}

	/**
	 * 导出网络加密集团汇总管理
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
		
		excelStream = excelService.showExcel(NetWork.columnHeadings(), baseBeanService.getListBeanByHqlAndParams(hql, parms));
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出网络加密集团汇总管理", account);
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