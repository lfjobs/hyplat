package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffDocumentation;
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
 * 资料列表
 * 
 * @author zgzg
 * 
 */
@Controller
@Scope("prototype")
public class DocumentationAction {
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
	private CLogBookService logBookService;
	private List<BaseBean> documentationList;
	
	private StaffDocumentation documentation;
	private File photo;
	private String photoContentType;
	private String parameter;
	private Map<String, StaffDocumentation> documentationmap;
	private List<BaseBean> beans;

	public String savedocumentation() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		documentation = new StaffDocumentation();
		beans = new ArrayList<BaseBean>();
		if (documentationmap != null) {
			for (StaffDocumentation doc : documentationmap.values()) {
				this.documentation.setStaffID(doc.getStaffID());
				if (doc.getPhotos() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService
							.savePhoto(path, doc.getPhotosFileName(), doc
									.getPhotos(), account.getCompanyID(),
									"/human/documentation/"
											+ Utilities.getDateString(
													new Date(), "yyyy-MM-dd"));
					doc.setPhoto(photoPath);
				}
				if (null == doc.getDocumentationID()
						|| "".equals(doc.getDocumentationID())) {
					doc.setDocumentationID(serverService
							.getServerID("documentation"));
					parameter = "添加资料";
				} else {
					parameter = "修改资料";

				}
				String hql2 = "from Staff where staffID=?";
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2, new Object[] { doc.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				doc.setCompanyID(account.getCompanyID());
				beans.add(doc);
			}
			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "success";
	}

	public String deldocumentation() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = "delete StaffDocumentation where staffID= ? and documentationID = ?";
		Object[] params = { documentation.getStaffID(),
				documentation.getDocumentationID() };

		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { documentation.getStaffID() });
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "删除资料(人员名称："
				+ staff.getStaffName() + ")", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
	}

	public String getListDocumentation() {
		Object[] params = { documentation.getStaffID() };
		documentationList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffDocumentation where staffID = ? order by documentationID desc", params);
		return "list";
	}

	public String showExcel() {
		List<BaseBean> list;
		Object[] params = { documentation.getStaffID() };
		String hql = " from StaffDocumentation where staffID = ? order by documentationID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(StaffDocumentation
				.columnHeadings(), list);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出资料", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public Map<String, StaffDocumentation> getDocumentationmap() {
		return documentationmap;
	}

	public void setDocumentationmap(
			Map<String, StaffDocumentation> documentationmap) {
		this.documentationmap = documentationmap;
	}

	public StaffDocumentation getDocumentation() {
		return documentation;
	}

	public void setDocumentation(StaffDocumentation documentation) {
		this.documentation = documentation;
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

	public List<BaseBean> getDocumentationList() {
		return documentationList;
	}

	public void setDocumentationList(List<BaseBean> documentationList) {
		this.documentationList = documentationList;
	}
}
