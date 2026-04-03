package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffPhysicalCondition;
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
 * 身体状况
 */
public class PConditionAction {
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
	private StaffPhysicalCondition condition;
	@Resource
	private CCodeService codeService;
	private List<CCode> codeExaminationContentList;
	@Resource
	private UpLoadFileService fileService;
	private Map<String, StaffPhysicalCondition> pconditionmap;
	private List<BaseBean> beans;
	private List<BaseBean> conditionList;
	
	public String savePCondition() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		condition = new StaffPhysicalCondition();
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		if (pconditionmap != null) {
			List<String> parermiters = new ArrayList<String>();
			for (StaffPhysicalCondition pCondition : pconditionmap.values()) {
				condition.setStaffID(pCondition.getStaffID());
				if (pCondition.getFilephoto() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService.savePhoto(path, pCondition
							.getFilephotoFileName(), pCondition.getFilephoto(),
							account.getCompanyID(), "/human/condition/"
									+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
					pCondition.setPhoto(photoPath);
				}
				if (null == pCondition.getConditionrID()
						|| "".equals(pCondition.getConditionrID())) {
					pCondition.setConditionrID(serverService
							.getServerID("condition"));
					parameter = "添加身体状况";
				} else {
					parameter = "修改身体状况";
				}
				String hql2 = "from Staff where staffID=?";
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2, new Object[] { pCondition.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				pCondition.setCompanyID(account.getCompanyID());
				baseBeanList.add(pCondition);
				parermiters.add(parameter);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
			logBookService.saveLogsListAndexecuteHqlsByParams(null,
					parermiters, account);
		}
		return "success";
	}

	public String delPCondition() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { condition.getStaffID(), condition.getConditionrID() };
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { condition.getStaffID() });
		CLogBook logBook = logBookService.saveCLogBook(null, "删除身体状况(人员名称："
				+ staff.getStaffName() + ")", account);
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		String[] hql1 = { "delete StaffPhysicalCondition where  staffID = ? and conditionrID = ? " };
		baseBeanService
				.saveBeansListAndexecuteHqlsByParams(beans, hql1, params);
		return "success";
	}

	public String getListPCondition() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { condition.getStaffID() };
		conditionList = baseBeanService.getListBeanByHqlAndParams(" from StaffPhysicalCondition where staffID = ? order by conditionrID desc", params);
		codeExaminationContentList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423qr9eg4m2nx0000000002");
		return "list";
	}

	public String showExcel() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { condition.getStaffID() };
		codeExaminationContentList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423qr9eg4m2nx0000000002");
		Map<String, String> map = new HashMap<String, String>();
		for (CCode b : codeExaminationContentList) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		StaffPhysicalCondition.setOMap(map);
		String hql = " from StaffPhysicalCondition where  staffID = ? order by conditionrID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(StaffPhysicalCondition
				.columnHeadings(), list);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出身体状况", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public List<CCode> getCodeExaminationContentList() {
		return codeExaminationContentList;
	}

	public void setCodeExaminationContentList(
			List<CCode> codeExaminationContentList) {
		this.codeExaminationContentList = codeExaminationContentList;
	}

	public Map<String, StaffPhysicalCondition> getPconditionmap() {
		return pconditionmap;
	}

	public void setPconditionmap(
			Map<String, StaffPhysicalCondition> pconditionmap) {
		this.pconditionmap = pconditionmap;
	}

	public List<BaseBean> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<BaseBean> conditionList) {
		this.conditionList = conditionList;
	}

	public StaffPhysicalCondition getCondition() {
		return condition;
	}

	public void setCondition(StaffPhysicalCondition condition) {
		this.condition = condition;
	}
}
