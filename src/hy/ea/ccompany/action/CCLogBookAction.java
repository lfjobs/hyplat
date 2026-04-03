package hy.ea.ccompany.action;

import hy.ea.bo.CLogBook;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CCLogBookAction {
	@Resource
	private BaseBeanService baseBeanService;
	
	private PageForm pageForm;
	private int pageNumber;
	private CLogBook clogbook;
	private String search;
	private String sdate;
	private String edate;
	public PageForm getPageForm() {
		return pageForm;
	}


	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				clogbook);
		return getListCLogBook();
	}

	
	public String getListCLogBook() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?20:pageNumber), getList());
		return "list";
	}
	
	
	/**
	 * 根据conpanyID查询所有的日志
	 * @return
	 */
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		//CAccount account = (CAccount) session.get("account");
		String companyId = session.get("companyID").toString();
		DetachedCriteria dc = DetachedCriteria.forClass(CLogBook.class);
		dc.add(Restrictions.eq("companyID",companyId));
		if (search != null && search.equals("search")) {
			clogbook = (CLogBook) session.get("tablesearch");
			if(clogbook.getAccountEmail()!=null&&!"".equals(clogbook.getAccountEmail()))
			{
				dc.add(Restrictions.like("accountEmail",clogbook.getAccountEmail(),
						MatchMode.ANYWHERE));
			}
			if(clogbook.getClogbookCounect()!=null&&!"".equals(clogbook.getClogbookCounect()))
			{
				dc.add(Restrictions.like("clogbookCounect",clogbook.getClogbookCounect(),
						MatchMode.ANYWHERE));
			}
			if(sdate != null && edate != null && !("").equals(sdate)&& !("").equals(edate))
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
				try {
					dc.add(Restrictions.between("clogbookDay",dateFormat.parse(sdate+" 00:00:00:000"),dateFormat.parse(edate+" 23:59:59:999")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(clogbook.getClogbookUrl()!=null&&!"".equals(clogbook.getClogbookUrl()))
			{
				dc.add(Restrictions.like("clogbookUrl",clogbook.getClogbookUrl(),
						MatchMode.ANYWHERE));
			}
		}
		dc.addOrder(Order.desc("clogbookDay"));
		return dc;
	}

	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public String getSdate() {
		return sdate;
	}


	public void setSdate(String sdate) {
		this.sdate = sdate;
	}


	public String getEdate() {
		return edate;
	}


	public void setEdate(String edate) {
		this.edate = edate;
	}


	public CLogBook getClogbook() {
		return clogbook;
	}


	public void setClogbook(CLogBook clogbook) {
		this.clogbook = clogbook;
	}


	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}
	
}