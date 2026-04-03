package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.ClientPositioning;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 产品定位展示
 */
@Controller
@Scope("prototype")
public class ClientPositioningAction {
	private int pageNumber;
	private InputStream excelStream;
	private PageForm pageForm;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;

	private String search;
	private String parameter;
	@Resource
	private BaseBeanService baseBeanService;
	private ClientPositioning clientPositioning;
	@Resource
	private UpLoadFileService fileService;
	private List<BaseBean> beans;

	// 根据条件查询产品定位展示
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", clientPositioning);
		return getClientPositioningList();
	}

	private DetachedCriteria getCAList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria
				.forClass(ClientPositioning.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			clientPositioning = (ClientPositioning) session.get("tablesearch");
			if (null != clientPositioning.getClientCode()
					&& !"".equals(clientPositioning.getClientCode())) {
				dc.add(Restrictions.like("clientCode", clientPositioning
						.getClientCode(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	/** ******************************************** */

	// 得到产品定位展示
	public String getClientPositioningList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 4 : pageNumber),
				getCAList());
		return "clientPositioning";
	}

	// 导出产品定位展示

	public String showClientPositioningExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(
				ClientPositioning.columnHeadings(), baseBeanService
						.getListByDC(getCAList()));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出产品定位展示", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	// 保存产品定位展示

	public String saveClientPositioning() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		if (clientPositioning.getPhoto() != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path, clientPositioning
					.getPhotoFileName(), clientPositioning.getPhoto(), account
					.getCompanyID(), "/human/logbook/"
					+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			clientPositioning.setPhotoFile(photoPath);
		}
		if (clientPositioning.getClientPositioningID() == null
				|| "".equals(clientPositioning.getClientPositioningID())) {
			clientPositioning.setClientPositioningID(serverService
					.getServerID("clientPositioningID"));
			parameter = "添加产品定位展示(图片编码:" + clientPositioning.getClientCode()
					+ ")";
		} else {
			String hql2 = "from ClientPositioning where companyID=?  and clientPositioningID=?";
			ClientPositioning aff = (ClientPositioning) baseBeanService
					.getBeanByHqlAndParams(hql2, new Object[] {
							account.getCompanyID(),
							clientPositioning.getClientPositioningID() });
			parameter = "修改产品定位展示(图片编码:" + aff.getClientCode() + ")";

		}
		clientPositioning.setCompanyID(account.getCompanyID());
		beans.add(clientPositioning);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	// 删除产品定位展示
	public String delClientPositioning() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(),
				clientPositioning.getClientPositioningID() };
		String hql1 = " from ClientPositioning where companyID=? and clientPositioningID=?";
		ClientPositioning cf = (ClientPositioning) baseBeanService
				.getBeanByHqlAndParams(hql1, params);
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"删除产品定位展示(产品编码:" + cf.getClientCode() + ")", account);
		beans.add(logBook);
		String hql = "delete from ClientPositioning where  companyID=? and clientPositioningID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
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

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public ClientPositioning getClientPositioning() {
		return clientPositioning;
	}

	public void setClientPositioning(ClientPositioning clientPositioning) {
		this.clientPositioning = clientPositioning;
	}

}
