package hy.ea.human.dao;


import hy.ea.bo.human.COrganization;
import hy.plat.bo.BaseBean;

import java.util.List;

public interface COrganizationDao {
	//更新机构列表
	void updateOrganizationByID(String organizationID,String unitsID);
	//根据机构PID，和单位ID查询机构列表
	List<COrganization> getOrganizationList(String organizationPID,String unitsID);
	/**
	 * lwt
	 */
	//根据机构ID和公司ID查询机构下的子机构
	COrganization getOrganization(String organizationID,String  companyID);
	//查询角色所拥有的机构树
	List<COrganization> getOrganizationListForTree(String organizationPID,String CompanyID,String RoleID);
	/*
	 * 根据机构ID查询其信息
	 */
	COrganization getCoranizationForOTree(String organizationID);
	/*
	 * 根据机构ID查询子机构
	 */
	List<COrganization> getCOrganizationListForOTree(String organizationPID,String RoleID);
	
	List<COrganization> getOrganizationList(String companyID);

    List<COrganization> getOrganizationListAll(String companyID);
	/**
	 * 查询公司下的五大部门
	 * @param companyId
	 * @return
	 */
	List<BaseBean> getThenFiveDepartments(String companyId);
	/**
	 * 根据登录账户查询组织机构
	 * @param accId登录账户id
	 * @return
	 */
	List<COrganization> findOrgByAcc(String accId);
}
