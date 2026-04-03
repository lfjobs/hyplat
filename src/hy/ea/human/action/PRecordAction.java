package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.CS;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffResume;
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
public class PRecordAction {
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
	private StaffResume record;
	@Resource
	private CCodeService codeService;
	private List<CCode> codePostSituationList;
	private List<CCode> connectionlist;
	private File photo;
	private String photoContentType;
	@Resource
	private UpLoadFileService fileService;

	private Map<String, StaffResume> recordmap;
	private List<BaseBean> pRecordList;
		

	public List<BaseBean> getPRecordList() {
		return pRecordList;
	}

	public void setPRecordList(List<BaseBean> recordList) {
		pRecordList = recordList;
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

	public StaffResume getRecord() {
		return record;
	}

	public void setRecord(StaffResume record) {
		this.record = record;
	}

	public String showExcel() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { record.getStaffID() };
		codePostSituationList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423vw54xx7r4f0000000053");
		Map<String, String> map = new HashMap<String, String>();
		for (CCode b : codePostSituationList) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		StaffResume.setOMap(map);
		String hql = " from StaffResume where staffID = ? order by recordID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService
				.showExcel(StaffResume.columnHeadings(), list);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出个人履历", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String savePRecord() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		record = new StaffResume();

		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		if (null != recordmap) {
			List<String> parermiters = new ArrayList<String>();
			for (StaffResume p : recordmap.values()) {
				record.setStaffID(p.getStaffID());
				if (p.getFilePhoto() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService
							.savePhoto(path, p.getFilePhotoFileName(), p
									.getFilePhoto(), account.getCompanyID(),
									"/human/record/"
											+ Utilities.getDateString(
													new Date(), "yyyy-MM-dd"));
					p.setPhoto(photoPath);
				}
				if (null == p.getRecordID() || "".equals(p.getRecordID())) {
					p.setRecordID(serverService.getServerID("record"));
					CS cs=(CS)baseBeanService.getBeanByHqlAndParams("from CS where companyID=? and staffID=?", new Object[]{p.getCcompanyID(),p.getStaffID()});
					if(cs==null){
						cs=new CS();
						cs.setCsID(serverService.getServerID("cs"));
						cs.setCompanyID(p.getCcompanyID());
						cs.setStaffID(p.getStaffID());
						baseBeanList.add(cs);
					}
					parameter = "添加个人覆历";
				} else {
					StaffResume cStaffResume=(StaffResume)baseBeanService.getBeanByHqlAndParams("from StaffResume where recordID=? and staffID=?", new Object[]{p.getRecordID(),p.getStaffID()});
					CS cs=(CS)baseBeanService.getBeanByHqlAndParams("from CS where companyID=? and staffID=?", new Object[]{cStaffResume.getCcompanyID(),cStaffResume.getStaffID()});
					if(cs==null){
						cs=new CS();
						cs.setCsID(serverService.getServerID("cs"));
						cs.setCompanyID(p.getCcompanyID());
						cs.setStaffID(p.getStaffID());
					}else{
						cs.setCompanyID(p.getCcompanyID());
					}
					baseBeanList.add(cs);
					parameter = "修改个人覆历";
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

		return "succ";
	}

	public String delPRecord() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String[] hql = { "delete StaffResume where  staffID= ? and recordID = ?" };
		Object[] params = { record.getStaffID(), record.getRecordID() };
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { record.getStaffID() });
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "删除个人履历(人员名称："
				+ staff.getStaffName() + ")", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		return "succ";
	}

	public String getListPRecord() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { record.getStaffID() };
		pRecordList = baseBeanService.getListBeanByHqlAndParams(" from StaffResume where  staffID = ? order by recordID desc"
				, params);
		
		codePostSituationList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423vw54xx7r4f0000000053");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
		"scode20110224xpd2t2jvda0000000002");
		return "list";
	}

	public List<CCode> getCodePostSituationList() {
		return codePostSituationList;
	}

	public void setCodePostSituationList(List<CCode> codePostSituationList) {
		this.codePostSituationList = codePostSituationList;
	}

	public Map<String, StaffResume> getRecordmap() {
		return recordmap;
	}

	public void setRecordmap(Map<String, StaffResume> recordmap) {
		this.recordmap = recordmap;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

}
