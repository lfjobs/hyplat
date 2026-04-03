package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffEducation;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class EducationAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	private StaffEducation education;
	private List<BaseBean> educationList;
	private List<CCode> majorTypelist;
	private List<CCode> educationHistorylist;
	private List<CCode> educationStylelist;
	private List<CCode> educationTypelist;
	// private File photo;
	// private String photoContentType;
	@Resource
	private UpLoadFileService fileService;
	private String parameter;
	private Map<String, StaffEducation> educationmap;
	private List<BaseBean> beans;

	public String showExcel() {
		List<BaseBean> list;
		Object[] params = { education.getStaffID() };
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Map<String, String> map = new HashMap<String, String>();
		String hql = " from StaffEducation where  staffID = ? order by educationID desc";
		majorTypelist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201004233ern4m24yx0000000077");
		educationHistorylist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100331mk6yn5b5f60000000008");
		educationStylelist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423vw54xx7r4f0000000043");
		educationTypelist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423vw54xx7r4f0000000050");
		for (CCode b : majorTypelist) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		for (CCode b : educationHistorylist) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		for (CCode b : educationStylelist) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		for (CCode b : educationTypelist) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		StaffEducation.setOMap(map);
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(StaffEducation.columnHeadings(),
				list);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出教育经历", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public StaffEducation getEducation() {
		return education;
	}

	public void setEducation(StaffEducation education) {
		this.education = education;
	}

	public Map<String, StaffEducation> getEducationmap() {
		return educationmap;
	}

	public void setEducationmap(Map<String, StaffEducation> educationmap) {
		this.educationmap = educationmap;
	}

	public List<BaseBean> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<BaseBean> educationList) {
		this.educationList = educationList;
	}

	public List<CCode> getMajorTypelist() {
		return majorTypelist;
	}

	public void setMajorTypelist(List<CCode> majorTypelist) {
		this.majorTypelist = majorTypelist;
	}

	public List<CCode> getEducationHistorylist() {
		return educationHistorylist;
	}

	public void setEducationHistorylist(List<CCode> educationHistorylist) {
		this.educationHistorylist = educationHistorylist;
	}

	public List<CCode> getEducationStylelist() {
		return educationStylelist;
	}

	public void setEducationStylelist(List<CCode> educationStylelist) {
		this.educationStylelist = educationStylelist;
	}

	public List<CCode> getEducationTypelist() {
		return educationTypelist;
	}

	public void setEducationTypelist(List<CCode> educationTypelist) {
		this.educationTypelist = educationTypelist;
	}

	// 添加或修改教育学历
	public String saveEducation() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		education = new StaffEducation();
		beans = new ArrayList<BaseBean>();
		if (educationmap != null) {
			for (StaffEducation edu : educationmap.values()) {
				education.setStaffID(edu.getStaffID());
				if (edu.getFilePhoto() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService.savePhoto(path, edu
							.getFilePhotoFileName(), edu.getFilePhoto(),
							account.getCompanyID(), "/human/education/"
									+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
					edu.setPhoto(photoPath);
				}
				if (null == edu.getEducationID()
						|| "".equals(edu.getEducationID())) {
					edu.setEducationID(serverService.getServerID("education"));
					parameter = "添加教育学历";
				} else {
					parameter = "修改教育学历";
				}
				String hql2 = "from Staff where staffID=?";
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2, new Object[] { edu.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				edu.setCompanyID(account.getCompanyID());
				beans.add(edu);
			}
			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "succ";
	}

	// 删除某条教育学历
	public String delEducation() {
		ActionContext.getContext().put("education", education);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = "delete StaffEducation where  educationID= ? and staffID = ?";
		Object[] params = { education.getEducationID(), education.getStaffID() };
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { education.getStaffID() });
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "删除教育学历(人员名称："
				+ staff.getStaffName() + ")", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "succ";
	}

	// 根据单位和登录人查看教育学历列表
	public String getListEducation() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		majorTypelist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201004233ern4m24yx0000000077");
		educationHistorylist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100331mk6yn5b5f60000000008");
		educationStylelist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423vw54xx7r4f0000000043");
		educationTypelist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423vw54xx7r4f0000000050");
		Object[] params = { education.getStaffID() };
		educationList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffEducation where staffID = ? order by educationID desc", params);
		return "list";
	}
}
