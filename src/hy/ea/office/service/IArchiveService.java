package hy.ea.office.service;

import hy.plat.bo.BaseBean;

import java.util.List;

/**
 * 档案管理
 *
 */
public interface IArchiveService {
	/*
	 * 
	 * 获取档案
	 */
	public String getCode(String companyID);
    /**
     * 获取档案分类
     * @param companyID 
     * @return #List<BaseBean>
     */
	List<BaseBean> getCatalogueList(String companyID);
	/**
	 * 获取档案存放位置
	 * @param staffID
	 * @return
	 */
//	List<DtArchivesInventorylocation> getLocation(String staffID);
	/**
	 * 获取档案信息
	 * @param userId 档案所属用户Id
	 * @param catalogueId 档案分类Id
	 * @param locationid 档案所在位置Id
	 * @return
	 */
//	List<DtArchivesArchives> getArchiveList(String userId, String catalogueId,
//			String locationid);
//	
//	/**
//	 * 根据条件查询用户在库档案,
//	 * @see IArchiveService#getArchiveList(String userId, String catalogueId,String locationid) 
//	 * @param chipsIds 芯片号列表 用","分隔 eg:EDASDF1564541411,E0SF45812ASDFSFE5
//	 * 返回盘点结果：完全符合："0" 档案有差："档案的编号:名字,"列表
//	 */
//	String archivesCheckvouchs(String chipsIds,String userId, String catalogueId,
//			String locationid);
}
