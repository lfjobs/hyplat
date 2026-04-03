package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffEncourage;
import hy.ea.service.CCodeService;
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
/**
 * 奖励情况
 */
public class EncourageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	private List<BaseBean> encourageList;
	
	private StaffEncourage encourage;
	private List<CCode> encourageTypelist;
	private File photo;
	private String photoContentType;
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	private String parameter;
	private Map<String, StaffEncourage> encouragemap;
	private List<BaseBean> beans;

	public String saveEncourage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		encourage = new StaffEncourage();
		beans = new ArrayList<BaseBean>();
		if (encouragemap != null) {
			for (StaffEncourage Encourage : encouragemap.values()) {
				encourage.setStaffID(Encourage.getStaffID());
				if (Encourage.getFilephoto() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService.savePhoto(path, Encourage
							.getFilephotoFileName(), Encourage.getFilephoto(),
							account.getCompanyID(), "/human/encourage/"
									+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
					Encourage.setPhoto(photoPath);
				}
				if (null == Encourage.getEncourageID()
						|| "".equals(Encourage.getEncourageID())) {
					Encourage.setEncourageID(serverService
							.getServerID("encourage"));
					parameter = "添加奖励情况";
				} else {
					parameter = "修改奖励情况";
				}
				String hql2 = "from Staff where staffID=?";
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2, new Object[] { Encourage.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				Encourage.setCompanyID(account.getCompanyID());
				beans.add(Encourage);
			}
			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "success";
	}

	public String delEncourage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans = new ArrayList<BaseBean>();
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { encourage.getStaffID() });
		CLogBook logBook = logBookService.saveCLogBook(null, "删除奖励情况(人员名称："
				+ staff.getStaffName() + ")", account);
		beans.add(logBook);
		String hql1 = "delete StaffEncourage where staffID = ? and encourageID = ? ";
		Object[] params = { encourage.getStaffID(), encourage.getEncourageID() };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql1 }, params);
		return "success";
	}

	public String getListEncourage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		encourageTypelist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423qr9eg4m2nx0000000003");
		Object[] params = { encourage.getStaffID() };
		encourageList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffEncourage where  staffID = ? order by encourageID desc", params);
		return "list";
	}

	public String showExcel() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		encourageTypelist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423qr9eg4m2nx0000000003");
		Map<String, String> map = new HashMap<String, String>();
		for (CCode b : encourageTypelist) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		StaffEncourage.setOMap(map);
		Object[] params = { encourage.getStaffID() };
		String hql = " from StaffEncourage where staffID = ? order by encourageID desc";
		excelStream = excelService.showExcel(StaffEncourage.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, params));
		CLogBook logBook = logBookService.saveCLogBook(null, "导出教育经历", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public Map<String, StaffEncourage> getEncouragemap() {
		return encouragemap;
	}

	public void setEncouragemap(Map<String, StaffEncourage> encouragemap) {
		this.encouragemap = encouragemap;
	}

	public List<CCode> getEncourageTypelist() {
		return encourageTypelist;
	}

	public void setEncourageTypelist(List<CCode> encourageTypelist) {
		this.encourageTypelist = encourageTypelist;
	}

	public StaffEncourage getEncourage() {
		return encourage;
	}

	public void setEncourage(StaffEncourage encourage) {
		this.encourage = encourage;
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

	public List<BaseBean> getEncourageList() {
		return encourageList;
	}

	public void setEncourageList(List<BaseBean> encourageList) {
		this.encourageList = encourageList;
	}
}