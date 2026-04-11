
package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.NetDisk;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
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
 * 网络硬盘信息
 * @author Administrator
 *
 */
public class NetDiskAction {
	private static final Logger logger = LoggerFactory.getLogger(NetDiskAction.class);
	
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
	
	private NetDisk netDisk;
	private String parameter; 
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	 
	private InputStream excelStream;
	
	private String downLoadPath;
	
	//保存网络硬盘(netDisk)信息
	public String saveNetDisk(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		
		//保存文件
		if(null!=netDisk.getNetDiskFile()){
			File upFile = netDisk.getNetDiskFile();
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String filePath = fileService.savePhoto(path, netDisk.getNetDiskFileFileName(),
					           netDisk.getNetDiskFile(), account.getCompanyID(), "/office/netdisk/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			
			 
			//保存文件其他信息
			String fileName = netDisk.getNetDiskFileFileName();
			String fielType = netDisk.getNetDiskFileContentType().split("/")[1];
			String fileSize = (double)(upFile.length()/1024)+"k";
			if(null==netDisk.getNetDiskName()||"".equals(netDisk.getNetDiskName())){
				netDisk.setNetDiskName(fileName);
			} 
			netDisk.setNetDiskType(fielType);
			netDisk.setNetDiskSize(fileSize);
			netDisk.setNetDiskPath(filePath);
		}
		//保存其他数据
		if(null==netDisk.getNetDiskID()||"".equals(netDisk.getNetDiskID())){
			netDisk.setNetDiskID(serverService.getServerID("netdisk"));
			parameter = "添加网络硬盘信息(文件名:"+netDisk.getNetDiskName()+")";
		}else{
			String hql = "from NetDisk where companyID = ? and  netDiskID = ? ";
			NetDisk q0 = (NetDisk) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),netDisk.getNetDiskID()});
			parameter = "修改网络硬盘信息(文件名:"+q0.getNetDiskName()+")";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		Date fileDate = new java.util.Date();
		netDisk.setNetDiskDate(fileDate);
		netDisk.setCompanyID(companyID);
		netDisk.setOrganizationID(organizationID);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(netDisk);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	//删除网络硬盘(netDisk)信息
	 public String delNetDisk()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),netDisk.getNetDiskID()};
		    String hql2="from NetDisk where companyID=?  and netDiskID = ? ";
		    NetDisk q0=(NetDisk) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除网络存储信息(文件名:"+q0.getNetDiskName()+")", account);
	    	String[] hql={"delete from NetDisk where companyID=?  and netDiskID = ?"};
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
			
		    return "success";
	    }
	 
	 //根据条件查询网络存储(netDisk)列表
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", netDisk);
			return getNetDiskList();
		}
	 // 网络存储(netDisk)列表
		public String getNetDiskList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "netdisklist";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(NetDisk.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				netDisk = (NetDisk) session.get("tablesearch");
				if(null!=netDisk.getNetDiskNum()&&!"".equals(netDisk.getNetDiskNum()))
				{
					dc.add(Restrictions.like("netDiskNum", netDisk.getNetDiskNum(), MatchMode.ANYWHERE));
				} 
				if(null!=netDisk.getNetDiskName()&&!"".equals(netDisk.getNetDiskName()))
				{
					dc.add(Restrictions.like("netDiskName", netDisk.getNetDiskName(), MatchMode.ANYWHERE));
				} 
			} 
			return dc;
		}
		
		// 导出网络存储(netDisk)
		public String showNetDiskExcel() {
			excelStream = excelService.showExcel(NetDisk.columnHeadings(), baseBeanService.getListByDC(getList()));
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			CLogBook  logbook=logBookService.saveCLogBook(null,"导出网络存储列表", account);
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
	public NetDisk getNetDisk() {
			return netDisk;
		}
		public void setNetDisk(NetDisk netDisk) {
			this.netDisk = netDisk;
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
		
}
