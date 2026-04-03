package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffPunishment;
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
 * 处分情况
 */
public class PunishmentAction {
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
	private String parameter;
	private List<BaseBean> punishmentList;
	
	private StaffPunishment punishment;
	private List<CCode> punishmentTypelist;
	private File photo;
	private String photoContentType;
	@Resource
	private UpLoadFileService fileService;

	private Map<String, StaffPunishment> punishmentmap;
	private List<BaseBean> beans;

	public String savePunishment() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		punishment = new StaffPunishment();
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		if (punishmentmap != null) {
			List<String> parermiters = new ArrayList<String>();
			for (StaffPunishment p : punishmentmap.values()) {
				punishment.setStaffID(p.getStaffID());
				if (p.getFilePhoto() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService
							.savePhoto(path, p.getFilePhotoFileName(), p
									.getFilePhoto(), account.getCompanyID(),
									"/human/punishment/"
											+ Utilities.getDateString(
													new Date(), "yyyy-MM-dd"));
					p.setPhoto(photoPath);
				}
				if (null == p.getPunishmentID()
						|| "".equals(p.getPunishmentID())) {
					p.setPunishmentID(serverService.getServerID("punishment"));
					parameter = "添加处分情况";
				} else {
					parameter = "修改处分情况";
				}
				String hql2 = "from Staff where staffID=?";
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2, new Object[] { p.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				p.setCompanyID(account.getCompanyID());
				baseBeanList.add(p);
				parermiters.add(parameter);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
			logBookService.saveLogsListAndexecuteHqlsByParams(null,
					parermiters, account);
		}
		return "success";
	}

	public String delPunishment() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String[] hql = { "delete StaffPunishment where staffID= ? and punishmentID = ?" };
		Object[] params = { punishment.getStaffID(),
				punishment.getPunishmentID() };
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { punishment.getStaffID() });
		CLogBook logBook = logBookService.saveCLogBook(null, "删除处分情况(人员名称："
				+ staff.getStaffName() + ")", account);
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		return "success";
	}

	public String getListPunishment() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		punishmentTypelist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423qr9eg4m2nx0000000004");
		Object[] params = { punishment.getStaffID() };
		punishmentList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffPunishment where staffID = ? order by punishmentID desc", params);
		return "list";
	}

	public String showExcel() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		punishmentTypelist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423qr9eg4m2nx0000000004");
		Map<String, String> map = new HashMap<String, String>();
		for (CCode b : punishmentTypelist) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		StaffPunishment.setOMap(map);
		Object[] params = { punishment.getStaffID() };
		String hql = " from StaffPunishment where  staffID = ? order by punishmentID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(StaffPunishment.columnHeadings(),
				list);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出处分情况", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public List<CCode> getPunishmentTypelist() {
		return punishmentTypelist;
	}

	public void setPunishmentTypelist(List<CCode> punishmentTypelist) {
		this.punishmentTypelist = punishmentTypelist;
	}

	public StaffPunishment getPunishment() {
		return punishment;
	}

	public void setPunishment(StaffPunishment punishment) {
		this.punishment = punishment;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public List<BaseBean> getPunishmentList() {
		return punishmentList;
	}

	public void setPunishmentList(List<BaseBean> punishmentList) {
		this.punishmentList = punishmentList;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public Map<String, StaffPunishment> getPunishmentmap() {
		return punishmentmap;
	}

	public void setPunishmentmap(Map<String, StaffPunishment> punishmentmap) {
		this.punishmentmap = punishmentmap;
	}

}
