package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.Certificate;
import hy.ea.service.CCodeService;
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
/*
 * 来往单位证件管理
 * */
@Controller
@Scope("prototype")
public class CertificateAction {
	private int pageNumber;
	private InputStream excelStream;
	private PageForm pageForm;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private Certificate certificate;
	private String search;
	private String ccompanyID;
	private String parameter;
	private Map<String, Certificate> certificatemap;
	private List<CCode> cccList;
	@Resource
	private CCodeService codeService;
	@Resource
	private UpLoadFileService fileService;

	//根据条件查询证件管理列表
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", certificate);
		return getaCertificateList();
	}

	private DetachedCriteria getCList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(Certificate.class);
		dc.add(Restrictions.eq("ccompanyID", certificate.getCcompanyID()));
		cccList = codeService.getCCodeListByPID(account.getCompanyID(),"scode20100423qr9eg4m2nx0000000006");
		if (search != null && search.equals("search")) {
			certificate = (Certificate) session.get("tablesearch");
			if(null!=certificate.getCertificateCode()&&!"".equals(certificate.getCertificateCode()))
			{
			dc.add(Restrictions.like("certificateCode", certificate.getCertificateCode(), MatchMode.ANYWHERE));
			}
		} 
		
		return  dc;
	}
	/***********************************************/
	/***********************************************/

	//证件管理列表
	public String getaCertificateList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?4:pageNumber),getCList());
		return "certificateList";	
	}
	
	// 导出证件管理列表

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		excelStream = excelService.showExcel(Certificate.columnHeadings(), baseBeanService.getListByDC(getCList()));
		CLogBook cLogBook = logBookService.saveCLogBook(null,"导出证件列表", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}
  //保存证件管理列表记录
    
    public String saveCertificate(){
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> bBaseBean = new ArrayList<BaseBean>();
		for(Certificate cc:certificatemap.values()){
   			this.certificate.setCcompanyID(cc.getCcompanyID());
   			if (cc.getPhoto()!= null) {
   				String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
   				String photoPath = fileService.savePhoto(path, cc.getPhotoFileName(),
   						cc.getPhoto(), account.getCompanyID(), "/human/credentials/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
   				cc.setCertificateAccessory(photoPath);
   			}
   			if(null==cc.getCertificateID()||"".equals(cc.getCertificateID())){
   				cc.setCertificateID(serverService.getServerID("cc"));
   				parameter = "添加证件管理列表(证件编号:"+certificate.getCertificateCode()+")";
   			}else{
   				String hql = " from Certificate where companyID=? and certificateID=?";
   				Certificate cf=(Certificate) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),cc.getCertificateID()});
   				parameter = "修改证件管理列表(证件编号:"+cf.getCertificateCode()+")";
   			}
			cc.setCompanyID(account.getCompanyID());
			CLogBook cLogBook = logBookService.saveCLogBook(null, parameter, account);
			bBaseBean.add(cc);
			bBaseBean.add(cLogBook);

    }
		baseBeanService.saveBeansListAndexecuteHqlsByParams(bBaseBean, null, null);
		return "success";
    }

    //删除证件管理列表
	 public String delCertificate(){
		    CAccount account =(CAccount) ActionContext.getContext().getSession().get("account");
		    Object[] params = {certificate.getCcompanyID(),certificate.getCertificateID()};
		    String hql1=" from Certificate where ccompanyID=? and certificateID=?" ;
			Certificate cf=(Certificate) baseBeanService.getBeanByHqlAndParams(hql1, params);
			CLogBook cLogBook = logBookService.saveCLogBook(null, "删除证件管理列表(证件编号:"+cf.getCertificateCode()+")", account);
	    	String hql="delete from Certificate where  ccompanyID=? and certificateID=?";
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

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public Map<String, Certificate> getCertificatemap() {
		return certificatemap;
	}

	public void setCertificatemap(Map<String, Certificate> certificatemap) {
		this.certificatemap = certificatemap;
	}

	public List<CCode> getCccList() {
		return cccList;
	}

	public void setCccList(List<CCode> cccList) {
		this.cccList = cccList;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	
}
