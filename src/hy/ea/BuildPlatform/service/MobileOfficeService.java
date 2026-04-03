package hy.ea.BuildPlatform.service;

import hy.ea.bo.company.ContactCompany;

import java.util.List;
import java.util.Map;

public interface MobileOfficeService {
	/**
	 * 
	 * @param or 组织机构名称
	 * @param user 用户名
	 * @param password	密码
	 */
	Integer login(String or,String user,String password);
	/**
	 *	个人信息
	 *	@param staffId  
	 */
	List<Object> personalInfo(String staffId);
	/**
	 *	学员信息
	 *	@param staffId
	 */
	List<Object> StudentInfo(String staffId);
	/**
	 *	快捷应用
	 *	@param companyId 
	 */
	Map <String,Object> fastApplication(String companyId);
	/**
	 * 查询下级 
	 * @param ppId
	 */
	Map<String,Object> toAddApplication(String ppId);
	/**
	 * 删除快捷应用
	 * @param ppId 父级id
	 *  
	 */
	void delApplication(String ppId);
	/**
	 * 增加快捷应用
	 */
	void addApplication(String ppId);
	/**
	 * 调整登录
	 */
	List<Object> toMobileLogin(String companyId);
	/**
	 * 查询往来单位
	 */
	ContactCompany authentication(String companyID);

	/**
	 * 查询学员信息是否完善
	 * @param staffId
	 * @return
	 */
	public Map<String,Object> StudentCard(String staffId);
	
}
