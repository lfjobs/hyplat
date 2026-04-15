package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.office.EnterpriseCitation;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * 企业奖状奖牌
 * @author Administrator
 *
 */
public class EnterpriseCitationAction {
	
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
	
	private EnterpriseCitation enterpriseCitation;
	private String parameter; 
	private PageForm pageForm;
	private List<COrganization> orgnizationList;
	private String search; 
	private int pageNumber;
	 
	private String downLoadPath;
	private InputStream excelStream;
	 
	private String sDate;
	private String eDate;
	
	//保存企业奖状奖牌
	public String saveEnterpriseCitation(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		
		//保存文件
		if(null!=enterpriseCitation.getCitationFile()){
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String filePath = fileService.savePhoto(path, enterpriseCitation.getCitationFileFileName(),
					           enterpriseCitation.getCitationFile(), account.getCompanyID(), "/office/enterprisecitation/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			enterpriseCitation.setCitationFilePath(filePath);
		}
		//保存其他数据
		if(null==enterpriseCitation.getEnterpriseCitationID()||"".equals(enterpriseCitation.getEnterpriseCitationID())){
			enterpriseCitation.setEnterpriseCitationID(serverService.getServerID("enterprisecitation"));
			parameter = "添加企业奖状奖牌(名称:"+enterpriseCitation.getEnName()+")";
		}else{
			String hql = "from EnterpriseCitation where companyID = ? and  enterpriseCitationID = ? ";
			EnterpriseCitation q0 = (EnterpriseCitation) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),enterpriseCitation.getEnterpriseCitationID()});
			parameter = "修改企业奖状奖牌(名称:"+q0.getEnName()+")";
		} 
		enterpriseCitation.setCompanyID(companyID);
		enterpriseCitation.setOrganizationID(organizationID);
		List<BaseBean> beans=new ArrayList<BaseBean>();
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(enterpriseCitation);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	//删除企业奖状奖牌
	 public String delEnterpriseCitation()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseCitation.getEnterpriseCitationID()};
		    String hql2="from EnterpriseCitation where companyID=?  and enterpriseCitationID = ? ";
		    EnterpriseCitation q0=(EnterpriseCitation) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    List<BaseBean> beans=new ArrayList<BaseBean>();
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除企业奖状奖牌(名称:"+q0.getEnName()+")", account);
	    	String hql="delete from EnterpriseCitation where companyID=?  and enterpriseCitationID = ?";
			beans.add(logbook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		    return "success";
	    }
	 
	 //根据条件查询企业奖状奖牌列表
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", enterpriseCitation);
			return getEnterpriseCitationList();
		}
	 //企业奖状奖牌列表
		public String getEnterpriseCitationList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "enterprisecitationlist";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID(); 
			
			DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseCitation.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				enterpriseCitation = (EnterpriseCitation) session.get("tablesearch");
				if(null!=enterpriseCitation.getEnName()&&!"".equals(enterpriseCitation.getEnName()))
				{
					dc.add(Restrictions.like("enName", enterpriseCitation.getEnName(), MatchMode.ANYWHERE));
				}  
				if(null!=sDate&&!"".equals(sDate)&&null!=eDate&&!"".equals(eDate)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						dc.add(Restrictions.between("enDate", sdf.parse(sDate), sdf.parse(eDate)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} 
			} 
			return dc;
		}
		
		// 导出企业奖状奖牌
		public String showEnterpriseCitationExcel() {
			excelStream = excelService.showExcel(EnterpriseCitation.columnHeadings(), baseBeanService.getListByDC(getList()));
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			 CLogBook  logbook=logBookService.saveCLogBook(null,"企业奖状奖牌导出", account);
			 baseBeanService.update(logbook);
			return "showexcel";
		}	 

		//下载文件
		public void downFile(){ 
			FileUtil fu = new FileUtil();
			try {
				fu.downFile(downLoadPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		public EnterpriseCitation getEnterpriseCitation() {
			return enterpriseCitation;
		}
		public void setEnterpriseCitation(EnterpriseCitation enterpriseCitation) {
			this.enterpriseCitation = enterpriseCitation;
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
		public InputStream getExcelStream() {
			return excelStream;
		}
		public void setExcelStream(InputStream excelStream) {
			this.excelStream = excelStream;
		}
		public String getDownLoadPath() {
			return downLoadPath;
		}
		public void setDownLoadPath(String downLoadPath) {
			this.downLoadPath = downLoadPath;
		}
		public String getSDate() {
			return sDate;
		}
		public void setSDate(String date) {
			sDate = date;
		}
		public String getEDate() {
			return eDate;
		}
		public void setEDate(String date) {
			eDate = date;
		}
		public List<COrganization> getOrgnizationList() {
			return orgnizationList;
		}
		public void setOrgnizationList(List<COrganization> orgnizationList) {
			this.orgnizationList = orgnizationList;
		} 
		
}
