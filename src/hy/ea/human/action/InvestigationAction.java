package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffInvestigation;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
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

/**
 * 调查情况
 * 
 * @author zgzg
 * 
 */
@Controller
@Scope("prototype")
public class InvestigationAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private StaffInvestigation investigation;
	private List<BaseBean> investigationList;
	
	private Map<String, StaffInvestigation> investigationmap;
	private List<BaseBean> beans;

	/**
	 * 添加或修改调查信息
	 * 参数:investigation.getInvestigationID(),serverService.getServerID()
	 * 
	 */
	public String saveInvestigation() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		investigation = new StaffInvestigation();
		beans = new ArrayList<BaseBean>();
		if (investigationmap != null) {
			for (StaffInvestigation invertigations : investigationmap.values()) {
				this.investigation.setStaffID(invertigations.getStaffID());
				if (invertigations.getPhotos() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService.savePhoto(path,
							invertigations.getPhotosFileName(), invertigations
									.getPhotos(), account.getCompanyID(),
							"/human/investigation/"
									+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
					invertigations.setPhoto(photoPath);
				}
				if (null == invertigations.getInvestigationID()
						|| "".equals(invertigations.getInvestigationID())) {
					invertigations.setInvestigationID(serverService
							.getServerID("investigation"));
					parameter = "添加调查信息";
				} else {
					parameter = "修改调查信息";

				}
				String hql2 = "from Staff where staffID=?";
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2, new Object[] { invertigations.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				invertigations.setCompanyID(account.getCompanyID());
				beans.add(invertigations);
			}

			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "success";
	}

	// 删除某条调查信息
	public String delInvestigation() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans = new ArrayList<BaseBean>();
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { investigation.getStaffID() });
		CLogBook logBook = logBookService.saveCLogBook(null, "删除调查信息(人员名称："
				+ staff.getStaffName() + ")", account);
		beans.add(logBook);
		String hql = "delete StaffInvestigation where staffID= ? and investigationID = ?";
		Object[] params = { investigation.getStaffID(),
				investigation.getInvestigationID() };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);

		return "success";
	}

	// 根据单位和登录人查看调查信息列表
	public String getListInvestigation() {
		Object[] params = { investigation.getStaffID() };
		investigationList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffInvestigation where staffID = ? order by investigationID desc", params);
		return "list";
	}

	public String showExcel() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { investigation.getStaffID() };
		String hql = " from StaffInvestigation where staffID = ? order by investigationID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(StaffInvestigation
				.columnHeadings(), list);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出调查信息", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public Map<String, StaffInvestigation> getInvestigationmap() {
		return investigationmap;
	}

	public void setInvestigationmap(
			Map<String, StaffInvestigation> investigationmap) {
		this.investigationmap = investigationmap;
	}

	public List<BaseBean> getInvestigationList() {
		return investigationList;
	}

	public void setInvestigationList(List<BaseBean> investigationList) {
		this.investigationList = investigationList;
	}

	public StaffInvestigation getInvestigation() {
		return investigation;
	}

	public void setInvestigation(StaffInvestigation investigation) {
		this.investigation = investigation;
	}
}