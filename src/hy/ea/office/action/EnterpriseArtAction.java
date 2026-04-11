package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseArt;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
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

import com.opensymphony.xwork2.ActionContext;
/**
 * 企业文化艺术作品管理
 * @author Administrator
 *
 */
public class EnterpriseArtAction {
	private static final Logger logger = LoggerFactory.getLogger(EnterpriseArtAction.class);
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private EnterpriseArt enterpriseArt;
	private String parameter; 
	private PageForm pageForm; 
	private String search; 
	private int pageNumber;
	 
	private String downLoadPath;
	private InputStream excelStream;
	
	//保存企业文化艺术
	public String saveEnterpriseArt(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		
		//保存文件
		if(null!=enterpriseArt.getArtFile()){
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String filePath = fileService.savePhoto(path, enterpriseArt.getArtFileFileName(),
					           enterpriseArt.getArtFile(), account.getCompanyID(), "/office/enterpriseart/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			enterpriseArt.setArtFilePath(filePath);
		}
		//保存其他数据
		if(null==enterpriseArt.getEnterpriseArtID()||"".equals(enterpriseArt.getEnterpriseArtID())){
			enterpriseArt.setEnterpriseArtID(serverService.getServerID("enterpriseart"));
			parameter = "添加企业文化艺术(名称:"+enterpriseArt.getEnName()+")";
		}else{
			String hql = "from EnterpriseArt where companyID = ? and  enterpriseArtID = ? ";
			EnterpriseArt q0 = (EnterpriseArt) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),enterpriseArt.getEnterpriseArtID()});
			parameter = "修改企业文化艺术(名称:"+q0.getEnName()+")";
		} 
		enterpriseArt.setCompanyID(companyID);
		enterpriseArt.setOrganizationID(organizationID);
		List<BaseBean> beans=new ArrayList<BaseBean>();
		CLogBook logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(enterpriseArt);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	//删除企业文化艺术
	 public String delEnterpriseArt()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseArt.getEnterpriseArtID()};
		    String hql2="from EnterpriseArt where companyID=?  and enterpriseArtID = ? ";
		    EnterpriseArt q0=(EnterpriseArt) baseBeanService.getBeanByHqlAndParams(hql2, params);
			List<BaseBean> beans=new ArrayList<BaseBean>();   
			CLogBook logbook=logBookService.saveCLogBook(organizationID, "删除企业文化艺术(名称:"+q0.getEnName()+")", account);
	    	String hql="delete from EnterpriseArt where companyID=?  and enterpriseArtID = ?";
	    	beans.add(logbook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
			
		    return "success";
	    }
	 
	 //根据条件查询企业文化艺术列表
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", enterpriseArt);
			return getEnterpriseArtList();
		}
	 //企业文化艺术列表
		public String getEnterpriseArtList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "enterpriseartlist";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID(); 
			
			DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseArt.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				enterpriseArt = (EnterpriseArt) session.get("tablesearch");
				if(null!=enterpriseArt.getEnName()&&!"".equals(enterpriseArt.getEnName()))
				{
					dc.add(Restrictions.like("enName", enterpriseArt.getEnName(), MatchMode.ANYWHERE));
				}  
				if(null!=enterpriseArt.getEnType()&&!"".equals(enterpriseArt.getEnType())){
					dc.add(Restrictions.eq("enType", enterpriseArt.getEnType()));
				} 
			} 
			return dc;
		}
		
		// 导出企业文化艺术
		public String showEnterpriseArtExcel() {
			excelStream = excelService.showExcel(EnterpriseArt.columnHeadings(), baseBeanService.getListByDC(getList()));
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			CLogBook logbook=logBookService.saveCLogBook(null,"企业文化艺术列表", account);
			baseBeanService.update(logbook);
			return "showexcel";
		}	 

		//下载文件
		public void downFile(){ 
			FileUtil fu = new FileUtil();
			try {
				fu.downFile(downLoadPath);
			} catch (IOException e) {
				logger.error("操作异常", e);
			}
		}
		public EnterpriseArt getEnterpriseArt() {
			return enterpriseArt;
		}
		public void setEnterpriseArt(EnterpriseArt enterpriseArt) {
			this.enterpriseArt = enterpriseArt;
		}
		public String getParameter() {
			return parameter;
		}
		public void setParameter(String parameter) {
			this.parameter = parameter;
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
		public int getPageNumber() {
			return pageNumber;
		}
		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}
		public String getDownLoadPath() {
			return downLoadPath;
		}
		public void setDownLoadPath(String downLoadPath) {
			this.downLoadPath = downLoadPath;
		}
		public InputStream getExcelStream() {
			return excelStream;
		}
		public void setExcelStream(InputStream excelStream) {
			this.excelStream = excelStream;
		}   
}
