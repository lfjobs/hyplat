package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffCertificate;
import hy.ea.service.CCodeService;
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
 * 
 * @author zg
 * @version 2011-04-18
 * 
 */
@Controller
@Scope("prototype")
public class CredentialsAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	private StaffCertificate credentials;
	private List<BaseBean> credentialsList;
	private List<CCode> credentialsTypelist;
	private String parameter;
	private List<BaseBean> beans;

	private Map<String, StaffCertificate> credentialsmap;// 添加或修改的时候 在前台要拿到值

	// 所以就把值放到Insurance里面 传到后台
	// 以便调用
	/**
	 * 添加或修改证件 参数：addressmap ，pageNumber（分页页数） 返回：getListAddress()
	 */
	public String saveCredentials() {
		ActionContext.getContext().put("tablesearch", credentials);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		credentials = new StaffCertificate();
		beans = new ArrayList<BaseBean>();
		if (credentialsmap != null) {
			for (StaffCertificate cre : credentialsmap.values()) {
				this.credentials.setStaffID(cre.getStaffID());

				if (cre.getPhotos() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService
							.savePhoto(path, cre.getPhotosFileName(), cre
									.getPhotos(), account.getCompanyID(),
									"/human/credentials/"
											+ Utilities.getDateString(
													new Date(), "yyyy-MM-dd"));
					cre.setPhoto(photoPath);
				}
				if (null == cre.getCredentialsID()
						|| "".equals(cre.getCredentialsID())) {
					cre.setCredentialsID(serverService
							.getServerID("credentials"));
					parameter = "添加证件";
				} else {
					parameter = "修改证件";

				}
				String[] hql2 = { "from Staff where staffID=?" };
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2[0], new Object[] { cre.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				cre.setCompanyID(account.getCompanyID());
				beans.add(cre);
			}
			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "success";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delCredentials() {
		ActionContext.getContext().put("tablesearch", credentials);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = "delete StaffCertificate where  staffID= ? and credentialsID = ?";
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { credentials.getStaffID() });
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "删除证件(人员名称："
				+ staff.getStaffName() + ")", account);
		beans.add(logBook);
		Object[] params = { credentials.getStaffID(),
				credentials.getCredentialsID() };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
	}

	public String getListCredentials() {
		if (null == credentials.getStaffID()
				|| "".equals(credentials.getStaffID())) {
			credentials = (StaffCertificate) ActionContext.getContext().get(
					"tablesearch");
		}
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		credentialsTypelist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423qr9eg4m2nx0000000006");
		Object[] params = { credentials.getStaffID() };
		credentialsList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffCertificate where staffID = ? order by credentialsID desc", params);
		return "list";
	}

	public String showExcel() {
		List<BaseBean> list;
		Object[] params = { credentials.getStaffID() };
		String hql = " from StaffCertificate where staffID = ? order by credentialsID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(StaffCertificate.columnHeadings(),
				list);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出证件", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public List<CCode> getCredentialsTypelist() {
		return credentialsTypelist;
	}

	public void setCredentialsTypelist(List<CCode> credentialsTypelist) {
		this.credentialsTypelist = credentialsTypelist;
	}

	public StaffCertificate getCredentials() {
		return credentials;
	}

	public void setCredentials(StaffCertificate credentials) {
		this.credentials = credentials;
	}

	public Map<String, StaffCertificate> getCredentialsmap() {
		return credentialsmap;
	}

	public void setCredentialsmap(Map<String, StaffCertificate> credentialsmap) {
		this.credentialsmap = credentialsmap;
	}

	public List<BaseBean> getCredentialsList() {
		return credentialsList;
	}

	public void setCredentialsList(List<BaseBean> credentialsList) {
		this.credentialsList = credentialsList;
	}
}