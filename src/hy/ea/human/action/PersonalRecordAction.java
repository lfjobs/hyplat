package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffPersonalFile;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
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
 * 人事档案
 * 
 * @author zgzg
 * 
 */
@Controller
@Scope("prototype")
public class PersonalRecordAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private StaffPersonalFile personalRecord;
	private List<BaseBean> personrecordList;
	
	private File photo;
	private String photoContentType;
	@Resource
	private UpLoadFileService fileService;
	private Map<String, StaffPersonalFile> personrecordmap;
	private List<BaseBean> beans;

	// 添加或修改人事档案
	public String savePersonalRecord() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		personalRecord = new StaffPersonalFile();
		List<BaseBean> jPersonalRecordList = new ArrayList<BaseBean>();
		if (personrecordmap != null) {
			List<String> parermiters = new ArrayList<String>();
			for (StaffPersonalFile p : personrecordmap.values()) {
				personalRecord.setStaffID(p.getStaffID());
				if (p.getFilePhoto() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService
							.savePhoto(path, p.getFilePhotoFileName(), p
									.getFilePhoto(), account.getCompanyID(),
									"/human/personalRecord/"
											+ Utilities.getDateString(
													new Date(), "yyyy-MM-dd"));
					p.setPhoto(photoPath);
				}
				if (null == p.getRecordID() || "".equals(p.getRecordID())) {
					p.setRecordID(serverService.getServerID("personalRecord"));
					parameter = "添加人事档案";
				} else {
					parameter = "修改个人覆历";
				}
				String hql2 = "from Staff where staffID=?";
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2, new Object[] { p.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				p.setCompanyID(account.getCompanyID());
				jPersonalRecordList.add(p);
				parermiters.add(parameter);

			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(
					jPersonalRecordList, null, null);
			logBookService.saveLogsListAndexecuteHqlsByParams(null,
					parermiters, account);
		}
		return "success";
	}

	// 删除某条人事档案
	public String delPersonalRecord() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String[] hql = { "delete StaffPersonalFile where staffID= ? and recordID = ?" };
		Object[] params = { personalRecord.getStaffID(),
				personalRecord.getRecordID() };
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { personalRecord.getStaffID() });
		CLogBook logBook = logBookService.saveCLogBook(null, "删除人事档案(人员名称："
				+ staff.getStaffName() + ")", account);
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		return "success";
	}

	// 根据单位和登录人查看人事档案列表
	public String getListPersonalRecord() {
		Object[] params = { personalRecord.getStaffID() };
		personrecordList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffPersonalFile where staffID = ? order by recordID desc", params);
		return "list";
	}

	public String showExcel() {
		List<BaseBean> list;
		Object[] params = { personalRecord.getStaffID() };
		String hql = " from StaffPersonalFile where  staffID = ? order by recordID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(
				StaffPersonalFile.columnHeadings(), list);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出人事档案", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}


	public List<BaseBean> getPersonrecordList() {
		return personrecordList;
	}

	public void setPersonrecordList(List<BaseBean> personrecordList) {
		this.personrecordList = personrecordList;
	}

	public Map<String, StaffPersonalFile> getPersonrecordmap() {
		return personrecordmap;
	}

	public void setPersonrecordmap(
			Map<String, StaffPersonalFile> personrecordmap) {
		this.personrecordmap = personrecordmap;
	}

	public StaffPersonalFile getPersonalRecord() {
		return personalRecord;
	}

	public void setPersonalRecord(StaffPersonalFile personalRecord) {
		this.personalRecord = personalRecord;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}
}