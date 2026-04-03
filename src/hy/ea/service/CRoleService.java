package hy.ea.service;

import hy.ea.bo.CLogBook;
import hy.ea.bo.CRole;

import java.util.List;


public interface CRoleService {
	
	/**
	 * 根据ID(Not Key)删除Role及相关信息
	 * @param companyID
	 * @param roleID
	 * @param logBook
	 */
	void deleteRoleByID(String companyID,String roleID,CLogBook logBook);
	
	/**
	 * 根据ID(Not Key)取得Role信息
	 * @param companyID
	 * @param roleID
	 * @return
	 */
	CRole getRoleByID(String companyID,String roleID);
	
	/**
	 * 取得所有的Role信息
	 * @param companyID
	 * @return
	 */
	List<CRole> getRoleList(String companyID);
}
