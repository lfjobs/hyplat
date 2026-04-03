package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.NetWorkAntiVirus;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 网络杀毒公司汇总管理
 */
@Controller
@Scope("prototype")
public class NetWorkAnViCompanyAction {
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
	 * 根据条件查询网络杀毒公司汇总管理
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", netWorkAntiVirus);
		return getNetWorkAnViCompanyList();
	}

	/**
	 * 网络杀毒公司汇总列表
	 * @return
	 */
	public String getNetWorkAnViCompanyList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?10:pageNumber),getNAList());
		return "networkantivirusList";	
	}
	
	/**
	 * 网络杀毒公司汇总列表、查询、导出调用
	 * @return
	 */
	private DetachedCriteria getNAList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(NetWorkAntiVirus.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			netWorkAntiVirus = (NetWorkAntiVirus) session.get("tablesearch");
			if(null!= netWorkAntiVirus.getNetworkAddress()&&!"".equals( netWorkAntiVirus.getNetworkAddress()))
			{
				dc.add(Restrictions.like("networkAddress", netWorkAntiVirus.getNetworkAddress(),MatchMode.ANYWHERE));
			}
			if(null!= netWorkAntiVirus.getNetWorkName()&&!"".equals( netWorkAntiVirus.getNetWorkName()))
			{
				dc.add(Restrictions.like("netWorkName", netWorkAntiVirus.getNetWorkName(), MatchMode.ANYWHERE));
			}
			if(null!= netWorkAntiVirus.getNetWorkCode()&&!"".equals(netWorkAntiVirus.getNetWorkCode()))
			{
				dc.add(Restrictions.like("netWorkCode",netWorkAntiVirus.getNetWorkCode(),MatchMode.ANYWHERE));
			}
		}
		return  dc;
	}

	/**
	 * 导出网络杀毒公司汇总管理
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		Map<String, String> map = new HashMap<String, String>();
		map.put("00", "请选择");
		map.put("01", "已杀毒");
		map.put("02", "未杀毒");
		map.put("03", "正在杀毒");
		NetWorkAntiVirus.setOMap(map);
		excelStream = excelService.showExcel(NetWorkAntiVirus.columnHeadings(), baseBeanService.getListByDC(getNAList()));
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出网络杀毒公司汇总管理", account);
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