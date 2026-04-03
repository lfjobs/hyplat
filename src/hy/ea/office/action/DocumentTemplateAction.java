package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.Document;
import hy.ea.bo.office.DocumentTemplate;
import hy.ea.office.service.ZOfficeService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
/**
 * 公文流转管理
 */
public class DocumentTemplateAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ZOfficeService zOfficeService;
	private Document document;
	private DocumentTemplate documentTemplate;
	private PageForm pageForm;
	private String result;
	private String search;
	private int pageNumber;
	
	private String receiptType;
	private String type;

	/**
	 * 
	 * 
	 * 新建更新提交
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String confirmSubmit() throws UnsupportedEncodingException  {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		receiptType=new String(documentTemplate.getReceiptType().getBytes("iso8859-1"),"UTF-8");
		if (documentTemplate.getTemplateId() != null
				&& !documentTemplate.getTemplateId().equals("")) {
			String hql = "from DocumentTemplate where templateId = ?";
			DocumentTemplate dt = (DocumentTemplate) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { documentTemplate
							.getTemplateId() });
			dt.setFileShowName(documentTemplate.getFileShowName());
			dt.setReceiptType(documentTemplate.getReceiptType());
			dt.setTime(new Date());
			baseBeanService.update(dt);
		} else {
			documentTemplate.setReceiptType(receiptType);
			documentTemplate.setTemplateId(serverService.getServerID("temp"));
			documentTemplate.setCreaterID(account.getStaffID());
			documentTemplate.setTime(new Date());
			documentTemplate.setCompanyId(account.getCompanyID());
			String templatePath = documentTemplate.getTemplatePath();
			documentTemplate.setExt(templatePath.substring(templatePath
					.lastIndexOf(".") + 1));
			
			documentTemplate.setFileSaveName(templatePath
					.substring(templatePath.lastIndexOf("/") + 1));
			baseBeanService.save(documentTemplate);
		}

		return "success";
	}

	public String toSearch() throws UnsupportedEncodingException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", documentTemplate);
		return getDocTemplateList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(DocumentTemplate.class);
		dc.add(Restrictions.eq("companyId", companyID));
		if(receiptType!=null){
		dc.add(Restrictions.eq("receiptType",receiptType));
		}
		dc.addOrder(Order.desc("time"));

		if (search != null && search.equals("search")) {
			documentTemplate = (DocumentTemplate) session.get("tablesearch");
			if (!"".equals(documentTemplate.getFileShowName().trim())) {
				dc.add(Restrictions.like("fileShowName", documentTemplate
						.getFileShowName().trim(), MatchMode.ANYWHERE));
			}

			if (!"".equals(documentTemplate.getFileType())) {
				dc.add(Restrictions.like("fileType", documentTemplate
						.getFileType(), MatchMode.ANYWHERE));
			}
		}

		return dc;
	}

	/**
	 * 根据公司id获得该公司所有的模板；
	 * @throws UnsupportedEncodingException 
	 * 
	 * 
	 * 
	 * 
	 */
	public String getDocTemplateList() throws UnsupportedEncodingException {
		if(receiptType!=null){
		       receiptType = new String(receiptType.getBytes("iso8859-1"),"UTF-8");
		     }
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		if (pageForm != null) {
			String hql = "from Staff where staffID = ?";
			Staff staff = null;
			for (BaseBean b : pageForm.getList()) {
				DocumentTemplate dt = (DocumentTemplate) b;
				if (dt.getCreaterID() == null) {
					continue;
				}
				staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
						new Object[] { dt.getCreaterID() });
				dt.setCreaterName(staff.getStaffName() + "("
						+ staff.getStaffCode() + ")");

			}
		}
		return "templatelist";
	}

	public String delDocumentTemplate() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String templateId = (String) request.getParameter("templateId");
		String hqldoc = "from Document where specificTemplate = ?";
		List<BaseBean> list = (List<BaseBean>) baseBeanService
				.getListBeanByHqlAndParams(hqldoc, new Object[] { templateId });
		try {
			if (list.size() == 0) {
				String hql = "from DocumentTemplate where templateId = ?";
				documentTemplate = (DocumentTemplate) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { templateId });
				String path = ServletActionContext.getRequest().getSession()
						.getServletContext().getRealPath("/");
				String templatePath = documentTemplate.getTemplatePath();
				String templateFullPath = path + templatePath;
				// 删除具体文件
				zOfficeService.deleteOffice(templateFullPath);
				// 删除 documentTemplate表记录

				baseBeanService.deleteBeanByKey(DocumentTemplate.class,
						documentTemplate.getKey());
				map.put("result", "suc");

			} else {
				map.put("result", "fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 判断模板是否存在；
	 * 
	 * 
	 * 
	 * 
	 */
	public String templateNameIsExist() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String companyId = account.getCompanyID();
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileShowName = request.getParameter("fileShowName");
		try {
			fileShowName = java.net.URLDecoder.decode(fileShowName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String hql = "from DocumentTemplate where fileShowName=? and companyId = ?";

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<BaseBean> templateList = (List<BaseBean>) baseBeanService
					.getListBeanByHqlAndParams(hql, new Object[] {
							fileShowName, companyId });
			if (templateList.size() == 0) {
				map.put("result", "suc");// 不重复
			} else {
				map.put("result", "fail");// 重复
			}

			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 
	 * 
	 * 新建页面根据类型，查模板
	 * 
	 * @return
	 */
	public String getTemplateListByType() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileType = request.getParameter("fileType");
		String hql = "from DocumentTemplate where fileType = ? and companyId = ?";
		List<BaseBean> templatelist = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{fileType,account.getCompanyID()});
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("templatelist", templatelist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public DocumentTemplate getDocumentTemplate() {
		return documentTemplate;
	}

	public void setDocumentTemplate(DocumentTemplate documentTemplate) {
		this.documentTemplate = documentTemplate;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
   
}