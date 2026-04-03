package hy.ea.BuildPlatform.service;

import java.util.Map;
import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.plat.bo.PageForm;
public interface PeopleManageMenService {
	/**
	 *分页 
	 *@param ca 登录caccount
	 *@param pageForm
	 */
	PageForm getPageFormBySql(CAccount ca,PageForm pageForm);
	/**
	 *保存修改 
	 *@param staff
	 *@param ca
	 *@param ppId
	 */
	void savaOrUpdate(Staff staff,CAccount ca,String ppId);
	/**
	 *查询
	 *@param staffId
	 */
	Map<String,Object> getPeopleInfo(String staffId);
	/**
	 *保存个人简介 
	 *@param pp
	 *@param content
	 */
	String savePersonalBrief(ProductPackaging pp,String content,String companyId);
	/**
	 *粉丝好友 
	 *@param pageForm
	 *@param search
	 */
	PageForm fansFriends(PageForm pageForm,String search);
	/**
	 *查看个人简介 
	 *@param pp
	 *@param content
	 */
	Map<String,Object> getPersonalBrief(String ppId);
	/**
	 *查询staff
	 *@param staffId
	 */
	Staff toGetStaff(String staffid);
	/**
	 *查询公司所在在职员工
	 *@param companyId 
	 */
	PageForm getStaffForCompany(PageForm pageForm,String companyId,String search);
}
