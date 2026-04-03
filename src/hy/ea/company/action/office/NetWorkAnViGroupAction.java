package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.NetWorkAntiVirus;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 网络杀毒集团汇总管理
 */
@Controller
@Scope("prototype")
public class NetWorkAnViGroupAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;

	private NetWorkAntiVirus netWorkAntiVirus;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private InputStream excelStream;
	
	/**
	 * 根据条件查询网络杀毒集团汇总管理
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", netWorkAntiVirus);
		return getNetWorkAnViGroupList();
	}

	/**
	 * 网络杀毒集团汇总列表
	 * @return
	 */
	public String getNetWorkAnViGroupList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "networkantivirusList";	
	}
	
	/**
	 * 网络杀毒集团汇总列表、查询、导出调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getList(Map<String, Object> session,
			CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		
		String hql = "from NetWorkAntiVirus t where exists ( select c.companyID from Company c"
				+ " where t.companyID = c.companyID and (c.companyID = ? or companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		
		if (search != null && search.equals("search")){
			netWorkAntiVirus = (NetWorkAntiVirus) session.get("tablesearch");
			if(null!= netWorkAntiVirus.getNetworkAddress() 
					&& !"".equals( netWorkAntiVirus.getNetworkAddress().trim())){
				hql += " and t.networkAddress like ?";
				parms.add("%" + netWorkAntiVirus.getNetworkAddress().trim() + "%");
			}
			if(null!= netWorkAntiVirus.getNetWorkName() 
					&& !"".equals( netWorkAntiVirus.getNetWorkName().trim())){
				hql += " and t.netWorkName like ?";
				parms.add("%" + netWorkAntiVirus.getNetWorkName().trim() + "%");
			}
			if(null!= netWorkAntiVirus.getNetWorkCode() 
					&& !"".equals( netWorkAntiVirus.getNetWorkCode().trim())){
				hql += " and t.netWorkCode like ?";
				parms.add("%" + netWorkAntiVirus.getNetWorkCode().trim() + "%");
			}
		}
		hql += " order by t.companyID desc";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}

	/**
	 * 导出网络杀毒集团汇总管理
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
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("00", "请选择");
		map.put("01", "已杀毒");
		map.put("02", "未杀毒");
		map.put("03", "正在杀毒");
		NetWorkAntiVirus.setOMap(map);
		
		excelStream = excelService.showExcel(NetWorkAntiVirus.columnHeadings(), baseBeanService.getListBeanByHqlAndParams(hql, parms));
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出网络杀毒集团汇总管理", account);
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


	public NetWorkAntiVirus getNetWorkAntiVirus() {
		return netWorkAntiVirus;
	}

	public void setNetWorkAntiVirus(NetWorkAntiVirus netWorkAntiVirus) {
		this.netWorkAntiVirus = netWorkAntiVirus;
	}
}