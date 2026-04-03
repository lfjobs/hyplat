package hy.ea.service.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.dao.CompanyDao;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

@Service
public class CLogBookServiceImpl implements CLogBookService {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CompanyDao companyDao;
	@Resource
	private COrganizationService organizationService;
	
	@Override
	public CLogBook saveCLogBook(String organizationID, String parameter,CAccount account) {
		/**
		 * 获取IP地址
		 */
		String visitorUrl;
		String visitorIp;
		try {
			HttpServletRequest req=(HttpServletRequest)ServletActionContext.getRequest();
			
		    visitorIp = req.getHeader("x-forwarded-for");   
			if(visitorIp == null || visitorIp.length() == 0 || "unknown".equalsIgnoreCase(visitorIp)) {   
				visitorIp = req.getHeader("Proxy-Client-IP");   
			}   
			if(visitorIp == null || visitorIp.length() == 0 || "unknown".equalsIgnoreCase(visitorIp)) {   
				visitorIp = req.getHeader("WL-Proxy-Client-IP");   
			}   
			if(visitorIp == null || visitorIp.length() == 0 || "unknown".equalsIgnoreCase(visitorIp)) {   
				visitorIp = req.getRemoteAddr();   
			}   
			visitorUrl=req.getServletPath();
		} catch (Exception e) {
			e.getStackTrace();
			visitorUrl = "";
			parameter = "退出(session失效)";
			visitorIp= "";
		}
		String clogbookCounect = parameter;
		if(organizationID != null){
		if(organizationID.startsWith("organization")){
			String OrganizationName = organizationService.getOrganization(account.getCompanyID(), organizationID).getOrganizationName();
			clogbookCounect ="("+OrganizationName+")"+parameter;
		}else if(organizationID.startsWith("company")){
			String OrganizationName = companyDao.getCompanyByCompanyID(organizationID).getCompanyName();
			clogbookCounect ="("+OrganizationName+")"+parameter;
		}
		}
		CLogBook cLogBook = new CLogBook();
		cLogBook.setClogbookID(serverService.getServerID("clogbook"));
		cLogBook.setAccountEmail(account.getAccountEmail());
		cLogBook.setClogbookCounect(clogbookCounect);
		cLogBook.setClogbookDay(new Date());
		cLogBook.setClogbookIP(visitorIp);
		cLogBook.setClogbookUrl(visitorUrl);
		cLogBook.setCompanyID(account.getCompanyID());
		return cLogBook;
	}
	
	/**
	 * 保存多个日志子类对象
	 * @param organizationID 组织ID
	 * @param parameters 数组类型的信息
	 * @param CAccount 账户信息 
	 */
	@Override
	public void saveLogsListAndexecuteHqlsByParams(String organizationID,List<String> parameters,CAccount account) {
		List<BaseBean> bBean=new ArrayList<BaseBean>();
		for(int i=0;i<parameters.size();i++){
			/**
			 * 获取IP地址
			 */
			String  parameter = parameters.get(i);
			String visitorUrl;
			String visitorIp;
			try {
				HttpServletRequest req=(HttpServletRequest)ServletActionContext.getRequest();
				
			    visitorIp = req.getHeader("x-forwarded-for");   
				if(visitorIp == null || visitorIp.length() == 0 || "unknown".equalsIgnoreCase(visitorIp)) {   
					visitorIp = req.getHeader("Proxy-Client-IP");   
				}   
				if(visitorIp == null || visitorIp.length() == 0 || "unknown".equalsIgnoreCase(visitorIp)) {   
					visitorIp = req.getHeader("WL-Proxy-Client-IP");   
				}   
				if(visitorIp == null || visitorIp.length() == 0 || "unknown".equalsIgnoreCase(visitorIp)) {   
					visitorIp = req.getRemoteAddr();   
				}   
				visitorUrl=req.getServletPath();
			} catch (Exception e) {
				e.getStackTrace();
				visitorUrl = "";
				parameter = "退出(session失效)";
				visitorIp= "";
			}
			String clogbookCounect = parameter;
			if(organizationID != null){
				String OrganizationName = organizationService.getOrganization(account.getCompanyID(), organizationID).getOrganizationName();
				clogbookCounect ="("+OrganizationName+")"+parameter;
			}
			CLogBook cLogBook = new CLogBook();
			cLogBook.setClogbookID(serverService.getServerID("clogbook"));
			cLogBook.setAccountEmail(account.getAccountEmail());
			cLogBook.setClogbookCounect(clogbookCounect);
			cLogBook.setClogbookDay(new Date());
			cLogBook.setClogbookIP(visitorIp);
			cLogBook.setClogbookUrl(visitorUrl);
			cLogBook.setCompanyID(account.getCompanyID());
			bBean.add(cLogBook);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(bBean,null,null);
	}


}
