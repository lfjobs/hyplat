package hy.ea.human.service;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.COrganization;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.io.InputStream;
import java.util.List;

public interface COrganizationService {
	 //查询机构树,根据机构ID和对应单位ID查询
	List<COrganization> getOrganizationList(String organizationPID,String unitsID);
	
	//分页查询
	public PageForm getPageForm(int pageNumber,int pageSize, String organizationID,String companyID);
	//导出excel时根据机构ID，公司ID，机构列表，得到organizationlist
	List<COrganization> getOrganizationByID(String organizationID,String companyID,List<COrganization> list) ;
	//根据登录账户查询组织机构
	List<COrganization> findOrgByAcc(String accId,List<COrganization> orgLst);
	//更新机构列表（删除机构时将该机构下的子机构挂到公司下面）
	void updateOrganizationByID(String organizationID, String unitsID);
	//根据公司ID，根据机构ID查询该机构下的子机构
	COrganization getOrganization(String companyID,
			String organizationID);
	// 导出当前机构的所有人员信息
	InputStream exportOrganization(List<COrganization> list);
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
	/**
	 * 查询公司所有的下树
	 * @param companyID
	 * @return
	 */
	List<COrganization> getOrganizationList(String companyID);

    List<COrganization> getOrganizationListAll(String companyID);
	/**
	 * 查询公司下的五大部门
	 * @param companyId
	 * @return
	 */
	List<BaseBean> getThenFiveDepartments(String companyId);

	/**
	 *
	 * 新版保存机构
	 * @param account
	 * @param organization
	 * @return
	 */
	public String saveOrg(CAccount account, COrganization organization);

	/**
	 *
	 * 新版删除机构
	 * @param companyID
	 * @param organizationID
	 */
	public  void delOrg(String companyID,String organizationID);
}
