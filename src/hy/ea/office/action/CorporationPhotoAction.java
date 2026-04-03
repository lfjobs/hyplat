package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CorporationPhoto;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 企业图片列表
 */
@Controller
@Scope("prototype")
public class CorporationPhotoAction {
	private int pageNumber;
	private InputStream excelStream;
	private PageForm pageForm;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;

	private String search;
	private String parameter;
	@Resource
	private BaseBeanService baseBeanService;
	private CorporationPhoto corporationPhoto;
	@Resource
	private UpLoadFileService fileService;
	
	

	//根据条件查询企业图片列表
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", corporationPhoto);
		return getCorporationPhotoList();
	}

	private DetachedCriteria getCAList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CorporationPhoto.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			corporationPhoto = (CorporationPhoto) session.get("tablesearch");
			if(null!=corporationPhoto.getCorporationPhotoCode()&&!"".equals(corporationPhoto.getCorporationPhotoCode()))
			{
			dc.add(Restrictions.like("corporationPhotoCode", corporationPhoto.getCorporationPhotoCode(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	/***********************************************/

	//企业图片列表
	public String getCorporationPhotoList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?4:pageNumber),getCAList());
		return "corporationPhotoList";	
	}
	
	// 导出企业图片列表

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(CorporationPhoto.columnHeadings(), baseBeanService.getListByDC(getCAList()));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,"导出企业图片列表", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}
  //保存企业图片列表
    
    public String saveCorporationPhoto(){
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (corporationPhoto.getPhoto() != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path, corporationPhoto.getPhotoFileName(),
					corporationPhoto.getPhoto(), account.getCompanyID(), "/human/logbook/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			corporationPhoto.setPhotoFile(photoPath);
		}
		if (corporationPhoto.getCorporationPhotoID()== null
				|| "".equals(corporationPhoto.getCorporationPhotoID())) {
			corporationPhoto.setCorporationPhotoID(serverService.getServerID("corporationPhotoID"));
			parameter = "添加企业图片列表(图片编码:"+corporationPhoto.getCorporationPhotoCode()+")";
		}
		else
		{
		 String hql2="from CorporationPhoto where companyID=?  and corporationPhotoID=?";
		 CorporationPhoto aff=(CorporationPhoto) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),corporationPhoto.getCorporationPhotoID() });
		 parameter = "修改企业图片列表(图片编码:"+aff.getCorporationPhotoCode()+")";
		
		}
		corporationPhoto.setCompanyID(account.getCompanyID());
		//baseBeanService.update(corporationPhoto);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(corporationPhoto);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
    }

    //删除企业图片列表
	 public String delCorporationPhoto(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),corporationPhoto.getCorporationPhotoID()};
		    String hql1=" from CorporationPhoto where companyID=? and corporationPhotoID=?" ;
		    CorporationPhoto cf=(CorporationPhoto) baseBeanService.getBeanByHqlAndParams(hql1, params);
		    CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除企业图片列表(图片编码:"+cf.getCorporationPhotoCode()+")", account);
	    	String hql="delete from CorporationPhoto where  companyID=? and corporationPhotoID=?";
			//baseBeanService.executeHqlByParams(hql, params);
	    	List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
			baseBeansList.add(cLogBook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql}, params);
			return "success";
	    }

	    public int getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public CorporationPhoto getCorporationPhoto() {
		return corporationPhoto;
	}

	public void setCorporationPhoto(CorporationPhoto corporationPhoto) {
		this.corporationPhoto = corporationPhoto;
	}

}
