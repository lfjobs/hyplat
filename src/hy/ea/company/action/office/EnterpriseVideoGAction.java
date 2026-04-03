package hy.ea.company.action.office;

/*
 * 企业录像集團汇总
 * */
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseVideo;
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

@Controller
@Scope("prototype")
public class EnterpriseVideoGAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private EnterpriseVideo enterpriseVideo;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//根据条件查询企业集團录像
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", enterpriseVideo);
			return getEnterpriseVideoList();
			}
		
		// 企业形象列表
		private List<Object> getList(Map<String, Object> session,
				CAccount account) {
			List<Object> parms = new ArrayList<Object>();
			List<Object> results = new ArrayList<Object>();
			String companyID = account.getCompanyID();
			
			String hql = "from EnterpriseVideo t where exists ( select c.companyID from Company c"
					+ " where t.companyID = c.companyID and (c.companyID = ? or companyPID = ?))";
			parms.add(companyID);
			parms.add(companyID);
			if (search != null && search.equals("search")){
				if (search != null && search.equals("search")) {
					enterpriseVideo = (EnterpriseVideo) session.get("tablesearch");
					if(null != enterpriseVideo.getEnName()
						&&!"".equals(enterpriseVideo.getEnName().trim())){
						hql += " and t.enName like ?";
						parms.add("%" + enterpriseVideo.getEnName().trim() + "%");
					}if(null != enterpriseVideo.getEnSubject()
							&&!"".equals(enterpriseVideo.getEnSubject().trim())){
						hql += " and t.enSubject like ?";
						parms.add("%" + enterpriseVideo.getEnSubject().trim() + "%");
					}
				} 
			}
			hql += " order by t.companyID desc";
			results.add(hql);
			results.add(parms.toArray());
			return results;
		}

	// 企业集團录像列表
	public String getEnterpriseVideoList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "videolist";	
	}
	
	//导出excel
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "tologin";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList(session, account);
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(EnterpriseVideo.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, parms));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"导出视频集团汇总", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
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

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public EnterpriseVideo getEnterpriseVideo() {
		return enterpriseVideo;
	}

	public void setEnterpriseVideo(EnterpriseVideo enterpriseVideo) {
		this.enterpriseVideo = enterpriseVideo;
	}


	

}
