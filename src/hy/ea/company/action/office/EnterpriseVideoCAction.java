package hy.ea.company.action.office;

/*
 * 企业录像公司汇总
 * */
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseVideo;
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

@Controller
@Scope("prototype")
public class EnterpriseVideoCAction {
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

	//根据条件查询企业录像
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseVideo);
		return getEnterpriseVideoList();
	}
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseVideo.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			enterpriseVideo = (EnterpriseVideo) session.get("tablesearch");
			if(null != enterpriseVideo.getEnName()
				&&!"".equals(enterpriseVideo.getEnName().trim())){
				dc.add(Restrictions.like("enName", enterpriseVideo.getEnName(), MatchMode.ANYWHERE));
			}if(null != enterpriseVideo.getEnSubject()
					&&!"".equals(enterpriseVideo.getEnSubject().trim())){
				dc.add(Restrictions.like("enSubject", enterpriseVideo.getEnSubject(), MatchMode.ANYWHERE));
			}
		} 
		return dc;
	}

	// 企业录像列表
	public String getEnterpriseVideoList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "videolist";	
	}
	
	//导出excel
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "tologin";
		}
		String companyID = account.getCompanyID();
		excelStream = excelService.showExcel(EnterpriseVideo.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		CLogBook cLogBook = logBookService.saveCLogBook(companyID,
				"导出企业视频公司汇总", account);
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
