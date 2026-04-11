package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseAgreement;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 企业合同管理
 * 
 * @author Administrator
 * 
 */
@Controller
@Scope("prototype")
public class EnterpriseAgreementAction {
	private static final Logger logger = LoggerFactory.getLogger(EnterpriseAgreementAction.class);

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;

	private EnterpriseAgreement enterpriseAgreement;
	private String parameter;
	/**
	 * 分页
	 */
	private PageForm pageForm;
	private String search;
	private int pageNumber;

	private InputStream excelStream;

	@Resource
	private UpLoadFileService fileService;

	private String downLoadPath;
	private String result;

	// 保存合同管理信息
	public String saveEnterpriseAgreement() {
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		// 上传扫描件
		if (enterpriseAgreement.getPhoto() != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path, enterpriseAgreement
					.getPhotoFileName(), enterpriseAgreement.getPhoto(),
					account.getCompanyID(), "/office/en/pic/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			enterpriseAgreement.setEnScan(photoPath);
		}
		// 上传源文件
		if (enterpriseAgreement.getSourcePhoto() != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String sorucePath = fileService.savePhoto(path, enterpriseAgreement
					.getSourcePhotoFileName(), enterpriseAgreement
					.getSourcePhoto(), account.getCompanyID(),
					"file/ea/office/en/doc/");
			enterpriseAgreement.setEnSource(sorucePath);
		}
		if (null == enterpriseAgreement.getEnterpriseAgreementID()
				|| "".equals(enterpriseAgreement.getEnterpriseAgreementID())) {
			enterpriseAgreement.setEnterpriseAgreementID(serverService
					.getServerID("enterpriseagreement"));
			parameter = "添加合同管理信息(编号:" + enterpriseAgreement.getSerialNumber()
					+ ")";
		} else {
			String hql = "from EnterpriseAgreement where companyID = ? and  enterpriseAgreementID = ? ";
			EnterpriseAgreement enterpriseAgreement0 = (EnterpriseAgreement) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] {
							account.getCompanyID(),
							enterpriseAgreement.getEnterpriseAgreementID() });
			parameter = "修改合同管理信息(编号:" + enterpriseAgreement0.getSerialNumber()
					+ ")";
		}
		enterpriseAgreement.setCompanyID(companyID);
		enterpriseAgreement.setOrganizationID(organizationID);
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(enterpriseAgreement);
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	/**
	 * 保存编辑器内容
	 * 
	 * @return
	 */
	public String saveEdit() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String organizationID = (String) session.get("organizationID");
		CAccount account = (CAccount) session.get("account");
		String hql2 = "from EnterpriseAgreement where enterpriseAgreementID = ? ";
		Object[] params = { enterpriseAgreement.getEnterpriseAgreementID() };
		String photoPath = null;
		if (enterpriseAgreement.getFileContent() != null
				&& !enterpriseAgreement.getFileContent().equals("")) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			photoPath = fileService.saveFile(path, "doc", enterpriseAgreement
					.getFileContent(), account.getCompanyID(),
					"upfile/ea/office/enterpriseAgreement/",
					enterpriseAgreement.getEnEdit());
		}
		EnterpriseAgreement enterpriseAgreement0 = (EnterpriseAgreement) baseBeanService
				.getBeanByHqlAndParams(hql2, params);
		enterpriseAgreement0.setEnEdit(photoPath);
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				"修改合同编辑(编号:" + enterpriseAgreement0.getSerialNumber() + ")",
				account);
		beans.add(logbook);
		beans.add(enterpriseAgreement0);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	/**
	 * 动态根据文件路径获取文件内容
	 * @return
	 */
	public String getAjaxFileContent() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String htmlContent = fileService.getFile(path, enterpriseAgreement
				.getEnEdit());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("htmlContent", htmlContent);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}

	/**
	 * 文件下载
	 * 
	 * @return
	 */
	public void downFile() {
		FileUtil fu = new FileUtil();
		try {
			fu.downFile(downLoadPath);
		} catch (IOException e) {
			logger.error("操作异常", e);
		}
	}

	/**
	 * 根据ID查询然后添加到文件盒中
	 * 
	 * @return
	 */
	public String saveToFileBoxByID() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");

		String hql = "from EnterpriseAgreement where enterpriseAgreementID=?";
		EnterpriseAgreement aff = (EnterpriseAgreement) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { enterpriseAgreement
						.getEnterpriseAgreementID() });

		aff.setManagesStartDate(enterpriseAgreement.getManagesStartDate());
		aff.setManagesEndDate(enterpriseAgreement.getManagesEndDate());
		aff.setResponsibler(enterpriseAgreement.getResponsibler());
		aff.setFileBoxName(enterpriseAgreement.getFileBoxName());
		aff.setFileFrame(enterpriseAgreement.getFileFrame());
		aff.setNumbers(enterpriseAgreement.getNumbers());

		parameter = "添加文件盒行业法规列表(法规文件编号:" + aff.getSerialNumber() + ")";

		List<BaseBean> beans = new ArrayList<BaseBean>();
		enterpriseAgreement.setCompanyID(account.getCompanyID());
		enterpriseAgreement.setOrganizationID(organizationID);
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(aff);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	// 删除合同管理信息
	public String delEnterpriseAgreement() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String organizationID = (String) session.get("organizationID");

		CAccount account = (CAccount) session.get("account");
		Object[] params = { account.getCompanyID(),
				enterpriseAgreement.getEnterpriseAgreementID() };
		String hql2 = "from EnterpriseAgreement where companyID=?  and enterpriseAgreementID = ? ";
		EnterpriseAgreement enterpriseAgreement0 = (EnterpriseAgreement) baseBeanService
				.getBeanByHqlAndParams(hql2, params);
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				"删除合同管理信息(编号:" + enterpriseAgreement0.getSerialNumber() + ")",
				account);
		String hql = "delete from EnterpriseAgreement where companyID=?  and enterpriseAgreementID=?";
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);

		return "success";
	}

	// 根据条件查询合同管理列表
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseAgreement);
		return getEnterpriseAgreementList();
	}

	// 合同管理列表
	public String getEnterpriseAgreementList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "enterpriseagreementlist";
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria
				.forClass(EnterpriseAgreement.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			enterpriseAgreement = (EnterpriseAgreement) session
					.get("tablesearch");
			if (null != enterpriseAgreement.getSerialNumber()
					&& !"".equals(enterpriseAgreement.getSerialNumber())) {
				dc.add(Restrictions.like("serialNumber", enterpriseAgreement
						.getSerialNumber(), MatchMode.ANYWHERE));
			}
			if (null != enterpriseAgreement.getEnName()
					&& !"".equals(enterpriseAgreement.getEnName())) {
				dc.add(Restrictions.like("enName", enterpriseAgreement
						.getEnName(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	// 导出合同管理
	public String showEnterpriseAgreementExcel() {
		excelStream = excelService.showExcel(EnterpriseAgreement
				.columnHeadings(), baseBeanService.getListByDC(getList()));
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		CLogBook logbook = logBookService.saveCLogBook(null, "导出合同管理列表",
				account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
	
	
	/**
	 * 
	 * 合同台账列表
	 * @return
	 */
	public String getContractParamList(){
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "enterpriseagreementlist";
		
	}

	public EnterpriseAgreement getEnterpriseAgreement() {
		return enterpriseAgreement;
	}

	public void setEnterpriseAgreement(EnterpriseAgreement enterpriseAgreement) {
		this.enterpriseAgreement = enterpriseAgreement;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public String getDownLoadPath() {
		return downLoadPath;
	}

	public void setDownLoadPath(String downLoadPath) {
		this.downLoadPath = downLoadPath;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
