package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Agencies;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 机构负责人
 */
@Controller
@Scope("prototype")
public class AgenciesAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private UpLoadFileService fileService;
	private Agencies agencies;

	private PageForm pageForm;
	private String parameter;
	private Map<String, Agencies> agenciesmap;
	private int pageNumber;
	private List<BaseBean> beans;

	/**
	 * 添加或修改机构负责人 参数：addressmap ，pageNumber（分页页数） 返回：getListAddress()
	 */
	public String saveAgencies() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		if (agenciesmap != null) {
			for (Agencies ag : agenciesmap.values()) {
				if (ag.getPhoto() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService
							.savePhoto(path, ag.getPhotoFileName(), ag
									.getPhoto(), account.getCompanyID(),
									"human/agencies/"
											+ Utilities.getDateString(
													new Date(), "yyyy-MM-dd"));
					ag.setPhotoPath(photoPath);
				}
				// 这里传过来 接收
				// agenciesID=ag.setOrganizationPID(agenciesID);
				if (ag.getAgenciesID() == null || "".equals(ag.getAgenciesID())) {
					ag.setAgenciesID(serverService.getServerID("agencies"));
					parameter = "添加机构负责人";
				} else {
					parameter = "修改机构负责人";

				}
				// String[] hql2={"from Agencies where agenciesID=?"};
				// Agencies as=(Agencies)
				// baseBeanService.getBeanByHqlAndParams(hql2[0], new
				// Object[]{agencies.getAgenciesID()});
				if (ag.getAgenciesName() != null
						&& !ag.getAgenciesName().equals("")) {
					parameter += "(人员名称:" + ag.getAgenciesName() + ")";
				} else {
					parameter += "(人员编号:" + ag.getAgenciesID() + ")";
				}
				beans.add(ag);

			}
			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "success";

	}

	// 删除某条机构负责人信息
	public String delAgencies() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		Object[] params = { agencies.getAgenciesID() };
		String hql2 = "from Agencies where agenciesID=?";
		Agencies age = (Agencies) baseBeanService.getBeanByHqlAndParams(hql2,
				params);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"删除机构机构负责人(机构负责人:" + age.getAgenciesName() + ")", account);
		beans.add(logBook);
		String[] hql = { "delete from Agencies where agenciesID=?" };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		return "success";
	}

	/**
	 * 根据单位和登录人查看机构负责人信息列表 参数：Agencies.getStaffID()｛人员ID｝，pageNumber（分页页数）
	 * 返回：pageForm
	 */
	public String getListAgencies() {
		Object[] params = { agencies.getOrganizationPID() };
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 5 : pageNumber),
						" from Agencies where organizationPID = ? order by agenciesID desc",
						params);
		return "list";
	}

	// 导出机构负责人
	public String showAgenciesExcel() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { agencies.getOrganizationPID() };
		list = baseBeanService
				.getListBeanByHqlAndParams(
						" from Agencies where organizationPID = ? order by agenciesID desc",
						params);
		excelStream = excelService.showExcel(Agencies.columnHeadings(), list);
		CLogBook logBook = logBookService
				.saveCLogBook(null, "导出机构负责人", account);
		baseBeanService.update(logBook);
		return "showexcel";
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Agencies getAgencies() {
		return agencies;
	}

	public void setAgencies(Agencies agencies) {
		this.agencies = agencies;
	}

	public Map<String, Agencies> getAgenciesmap() {
		return agenciesmap;
	}

	public void setAgenciesmap(Map<String, Agencies> agenciesmap) {
		this.agenciesmap = agenciesmap;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

}
