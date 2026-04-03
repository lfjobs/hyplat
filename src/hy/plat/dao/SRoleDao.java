package hy.plat.dao;

import hy.plat.bo.SRole;

import java.util.List;


public interface SRoleDao {
	/**
	 * 根据ID(Not Key)删除SRole及相关信息
	 * @param roleID
	 */
	void deleteRoleByID(String roleID);
	
	/**
	 * 根据ID(Not Key)取得SRole信息
	 * @param roleID
	 * @return
	 */
	SRole getRoleByID(String roleID);
	
	/**
	 * 取得所有的Role信息
	 * @return
	 */
	List<SRole> getRoleList();
}
