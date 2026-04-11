package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 离职员工
 *@author 陈小丰
 */
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.human.vo.COSDimissionStaffVO;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

@Controller
@Scope("prototype")
public class MobileCOSDimissionAction {
	private static final Logger logger = LoggerFactory.getLogger(MobileCOSDimissionAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	private COSDimissionStaffVO codi;
	private List<CCode> typelist;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String result;
	private String search;
	private List<CCode> connectionlist;
	
	public InputStream excelStream;
	 //根据条件查询离职员工 
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", codi);
		return getListCOSDimission();
	}
	
	public DetachedCriteria getListBYDC()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc=DetachedCriteria.forClass(COSDimissionStaffVO.class);
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			this.codi = (COSDimissionStaffVO) session.get("tablesearch");
			if(null != codi.getStaffCode() && !"".equals(codi.getStaffCode()))
			{
				dc.add(Restrictions.like("staffCode", codi.getStaffCode(),MatchMode.ANYWHERE));
			}
			if(null != codi.getStaffName() && !"".equals(codi.getStaffName()))
			{
				dc.add(Restrictions.like("staffName", codi.getStaffName(),MatchMode.ANYWHERE));
			}
			if(null != codi.getStaffIdentityCard() && !"".equals(codi.getStaffIdentityCard()))
			{
				dc.add(Restrictions.like("staffIdentityCard", codi.getStaffIdentityCard(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	
	
	//得到离职员工列表
	public String getListCOSDimission() {
			pageForm = baseBeanService.getPageFormByDC((pageNumber), (1),getListBYDC());
			HttpServletResponse response = ServletActionContext.getResponse();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class,
					new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
			String outString = jsonArray.toString();
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(outString);
				//logger.info("值：{}", outString);
				response.flushBuffer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("操作异常", e);
			}
			return null;
	}
	/**
	 * 导出离职员工列表
	 * @param : account organizationID
	 * @return : showexcel
	 */
	
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(COSDimissionStaffVO.columnHeadings(), baseBeanService.getListByDC(getListBYDC()));
		logBookService.saveCLogBook(organizationID,"导出离职员工列表", account);
		return "showexcel";
	}

	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public List<CCode> getConnectionlist() {
		return connectionlist;
	}
	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}
	public List<CCode> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
	public ShowExcelService getExcelService() {
		return excelService;
	}
	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public CLogBookService getLogBookService() {
		return logBookService;
	}
	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}
	public COSDimissionStaffVO getCodi() {
		return codi;
	}
	public void setCodi(COSDimissionStaffVO codi) {
		this.codi = codi;
	}
	public CCodeService getCodeService() {
		return codeService;
	}
	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
	}
	
}
