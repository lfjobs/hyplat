package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffPoliticalStatus;
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
/**
 * 政治面貌
 */
public class PoliticalAction {
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
	private List<BaseBean> politicalList;
	
	private StaffPoliticalStatus political;
	private List<CCode> politicalTpyelist;
	private String photoContentType;
	private Map<String, StaffPoliticalStatus> politicalmap;
	private List<BaseBean> beans;
	@Resource
	private UpLoadFileService fileService;

	public String savePolitical() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		political = new StaffPoliticalStatus();
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		if (politicalmap != null) {
			List<String> parermiters = new ArrayList<String>();
			for (StaffPoliticalStatus politicals : politicalmap.values()) {
				political.setStaffID(politicals.getStaffID());
				if (politicals.getFilephoto() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService.savePhoto(path, politicals
							.getFilephotoFileName(), politicals.getFilephoto(),
							account.getCompanyID(), "/human/political/"
									+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
					politicals.setPhoto(photoPath);
				}
				if (null == politicals.getPoliticalID()
						|| "".equals(politicals.getPoliticalID())) {
					politicals.setPoliticalID(serverService
							.getServerID("political"));
					parameter = "添加政治面貌";
				} else {
					parameter = "修改政治面貌";
				}
				String hql2 = "from Staff where staffID=?";
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2, new Object[] { politicals.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				politicals.setCompanyID(account.getCompanyID());
				baseBeanList.add(politicals);
				parermiters.add(parameter);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
			logBookService.saveLogsListAndexecuteHqlsByParams(null,
					parermiters, account);
		}
		return "success";
	}

	public String delPolitical() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { political.getStaffID(), political.getPoliticalID() };
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { political.getStaffID() });
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "删除政治面貌(人员名称："
				+ staff.getStaffName() + ")", account);
		beans.add(logBook);
		String[] hql1 = { "delete StaffPoliticalStatus where staffID = ? and politicalID = ? " };
		baseBeanService
				.saveBeansListAndexecuteHqlsByParams(beans, hql1, params);
		return "success";
	}

	public String getListPolitical() {
		if (null == political.getStaffID() || "".equals(political.getStaffID())) {
			political = (StaffPoliticalStatus) ActionContext.getContext().get(
					"tablesearch");
		}
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		politicalTpyelist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423qr9eg4m2nx0000000001");
		Object[] params = { political.getStaffID() };
		politicalList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffPoliticalStatus where  staffID = ? order by politicalID desc", params);
		return "list";
	}

	public String showExcel() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Map<String, String> map = new HashMap<String, String>();
		politicalTpyelist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423qr9eg4m2nx0000000001");
		for (CCode b : politicalTpyelist) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		StaffPoliticalStatus.setOMap(map);
		Object[] params = { political.getStaffID() };
		String hql = " from StaffPoliticalStatus where staffID = ? order by politicalID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(StaffPoliticalStatus
				.columnHeadings(), list);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出政治面貌", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public Map<String, StaffPoliticalStatus> getPoliticalmap() {
		return politicalmap;
	}

	public void setPoliticalmap(Map<String, StaffPoliticalStatus> politicalmap) {
		this.politicalmap = politicalmap;
	}

	public List<CCode> getPoliticalTpyelist() {
		return politicalTpyelist;
	}

	public void setPoliticalTpyelist(List<CCode> politicalTpyelist) {
		this.politicalTpyelist = politicalTpyelist;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public StaffPoliticalStatus getPolitical() {
		return political;
	}

	public List<BaseBean> getPoliticalList() {
		return politicalList;
	}

	public void setPoliticalList(List<BaseBean> politicalList) {
		this.politicalList = politicalList;
	}

	public void setPolitical(StaffPoliticalStatus political) {
		this.political = political;
	}
}