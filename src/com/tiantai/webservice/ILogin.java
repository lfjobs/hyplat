package com.tiantai.webservice;
  
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.tiantai.webservice.bo.CheckVouchs;
 

@WebService(targetNamespace = "http://service.impf2010.com", name = "Login") 
public interface ILogin {
	/**
	 * 用户登录
	 * @param companyIdentifier 组织机构码
	 * @param user 登录账号信息
	 * @param companyType 公司类型
	 * @return 0：成功
	 */
	//int login(LoginUser user,String companyType);
	
	/**
	 * 用户登录，返回当前账号信息 
	 * 组成格式 companyid, organizationid,accountemail,accountpassword,staffname,staffid
	 * @param user
	 * @return 
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getLoginUser(@WebParam(name = "userName",targetNamespace="http://service.impf2010.com")String userName,
			@WebParam(name = "loginPwd",targetNamespace="http://service.impf2010.com")String loginPwd,
			@WebParam(name = "companyIdentity",targetNamespace="http://service.impf2010.com")String companyIdentity);
	
	/**
	 * 获取公司信息 hy.ea.bo.Company
	 * @param companyIdentifier 组织机构码
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getCompany(@WebParam(name = "companyIdentity",targetNamespace="http://service.impf2010.com") String companyIdentity);
	
	/**
	 * 获取公司信息 hy.ea.bo.Company 
	 * @param companyId
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getCompanyById(@WebParam(name = "companyId",targetNamespace="http://service.impf2010.com") String companyId);
	
	/**
	 * 公司的部门  organizationID,organizationName,organizationPID
	 * @param companyId
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getOrg(@WebParam(name = "companyId",targetNamespace="http://service.impf2010.com") String companyId);
	
	/** 
	 * 获取档案类别  id,name
	 * @param companyId
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getArchivesCatalog(@WebParam(name = "companyId",targetNamespace="http://service.impf2010.com") String companyId);
	
	/**
	 * 获取档案位置
	 * @param userId 用户Id
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getLocationByUser(@WebParam(name = "staffId",targetNamespace="http://service.impf2010.com") String staffId);
	
	/**staff.REFERENCE
	 * 根据部门Id获取在职员工信息  staff.staffid,staff.staffName
	 * @param orgId
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getStaffsByOrganizationId(@WebParam(name = "orgId",targetNamespace="http://service.impf2010.com") String orgId);
	
	/**
	 * 档案盘点
	 * @param checkVouchs
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getChipsIdByCondition(@WebParam(name = "checkVouchs",targetNamespace="http://service.impf2010.com")CheckVouchs checkVouchs);
	
	/**
	 * 根据芯片获取档案信息
	 * @param chip
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getArchives(@WebParam(name = "chip",targetNamespace="http://service.impf2010.com")String chip,@WebParam(name = "barCode",targetNamespace="http://service.impf2010.com")String barCode);
	/**
	 * 获取档案盒及档案盒中档案信息
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getChivesInfo(@WebParam(name = "chip",targetNamespace="http://service.impf2010.com")String chip);
	
	/**
	 * 获取人事档案
	 * @param chipOrBarCode　芯片号或条码
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getHrArchives(@WebParam(name = "chipOrBarCode",targetNamespace="http://service.impf2010.com")String chipOrBarCode);
}
