package hy.ea.company.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 往来单位
 *@author 王汝明
 */
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.ContactConnection;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

@Controller
@Scope("prototype")
public class MobileContactCompanyAction {
	private static final Logger logger = LoggerFactory.getLogger(MobileContactCompanyAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	private ContactCompany contactCompany;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String result;
	private String search;
	private ContactConnection cconnection;

	@Resource
	private CCodeService codeService;
	private List<CCode> typelist;
	private List<CCode> connectionlist;
	private String onlyCompany;
	
	// 添加或修改往来单位
	public String saveContactCompany() {

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (null == contactCompany.getCcompanyID()
				|| "".equals(contactCompany.getCcompanyID())) {
			contactCompany.setCcompanyID(serverService
					.getServerID("contactCompany"));
			parameter = "添加联系单位：（单位名称" + contactCompany.getCompanyName() + ")";
		} else {
			String hql = "from ContactCompany where ccompanyID = ?";
			Object[] param = { contactCompany.getCcompanyID() };
			ContactCompany cc = (ContactCompany) baseBeanService
					.getBeanByHqlAndParams(hql, param);
			parameter = "修改往来单位（单位名称" + cc.getCompanyName() + "）";
		}
		//baseBeanService.update(contactCompany);
		CLogBook cLogBook =logBookService.saveCLogBook(null, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(contactCompany);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", contactCompany);
		return getListContactCompany();
	}

	public DetachedCriteria getListBYDC() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria.forClass(ContactCompany.class);
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		if (search != null && search.equals("search")) {
			this.contactCompany = (ContactCompany) session.get("tablesearch");
			if (null != contactCompany.getCompanyName()
					&& !"".equals(contactCompany.getCompanyName())) {
				dc.add(Restrictions.like("companyName", contactCompany
						.getCompanyName(), MatchMode.ANYWHERE));
			}
			if (null != contactCompany.getCresponsible()
					&& !"".equals(contactCompany.getCresponsible())) {
				dc.add(Restrictions.like("cresponsible", contactCompany
						.getCresponsible(), MatchMode.ANYWHERE));
			}
			if (null != contactCompany.getCompanyAddr()
					&& !"".equals(contactCompany.getCompanyAddr())) {
				dc.add(Restrictions.like("companyAddr", contactCompany
						.getCompanyAddr(), MatchMode.ANYWHERE));
			}
			if (null != contactCompany.getIndustryType()
					&& !"".equals(contactCompany.getIndustryType())) {
				dc.add(Restrictions.eq("industryType", contactCompany
						.getIndustryType()));
			}
		}
		return dc;
	}

	// 往来单位列表
	public String getListContactCompany() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110106hfjes5ucxp0000000003");
		/*pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
				getListBYDC());*/
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

	// 往来单位行业列表
	public String getContactCompanyList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110106hfjes5ucxp0000000003");
		/*pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
				getListBYDC());*/
		pageForm = baseBeanService.getPageFormByDC((pageNumber), (1),getListBYDC());
		return "list";
	}
	
	/**
	 * 判断往来单位是否已添加到往来单位
	 * 判断添加社会单位是否存在
	 * @return
	 */
	public String isContactConnection() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (onlyCompany!=null&&!"".equals(onlyCompany)) {
		String hql = "select count(*) from ContactCompany where companyName = ? ";
		Object[] params = {onlyCompany};
		count=baseBeanService.getConutByByHqlAndParams(hql, params);
		} else {
		String hql = "select count(*) from ContactConnection where ccompanyID = ? and companyID = ? and contactConnections = ?";
		Object[] params = {cconnection.getCcompanyID(),account.getCompanyID() ,cconnection.getContactConnections()};
		count=baseBeanService.getConutByByHqlAndParams(hql, params);
			if(count<=0)
			{
				cconnection.setCompanyID(account.getCompanyID());
				
				if(null == cconnection.getContactConnectionID()|| "".equals(cconnection.getContactConnectionID())){
					cconnection.setContactConnectionID(serverService.getServerID("contactConnection"));
				}
				baseBeanService.update(cconnection);
			}
		}
		map.put("c", count);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 导出往来单位
	 */
	public String showExcel() {
		List<BaseBean> contactCompanyList = baseBeanService
				.getListByDC(getListBYDC());
		excelStream = excelService.showExcel(ContactCompany.columnHeadings(),
				contactCompanyList);
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook cLogBook =logBookService.saveCLogBook(null, "导出往来单位", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	/**
	 * 根据往来单位名称模糊查询列表
	 * 
	 * @return
	 */
	public String getListContactCompanyByCompanyName() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		DetachedCriteria dc = DetachedCriteria
				.forClass(ContactCompanyView.class);
		dc.add(Restrictions.like("companyName",
				contactCompany.getCompanyName(), MatchMode.ANYWHERE));
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.like("contactConnections", cconnection
				.getContactConnections(), MatchMode.ANYWHERE));
		dc.addOrder(Order.desc("companyName"));
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		map.put("connectionlist", connectionlist);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 根据往来单位查询银行账户
	 * 
	 * @return
	 */
	public String getListRegistration() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { contactCompany.getCcompanyID() };
		// 查询银行账户
		String hql = "from Registration where ccompanyID= ?";
		List<BaseBean> bankList = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bankList", bankList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public ContactCompany getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(ContactCompany contactCompany) {
		this.contactCompany = contactCompany;
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

	public List<CCode> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}

	public CCodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
	}

	public ContactConnection getCconnection() {
		return cconnection;
	}

	public void setCconnection(ContactConnection cconnection) {
		this.cconnection = cconnection;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public String getOnlyCompany() {
		return onlyCompany;
	}

	public void setOnlyCompany(String onlyCompany) {
		this.onlyCompany = onlyCompany;
	}

}
