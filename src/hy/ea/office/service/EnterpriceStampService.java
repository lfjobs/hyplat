package hy.ea.office.service;

import hy.ea.bo.office.EnterpriseStamp;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

public interface EnterpriceStampService {


	/**
	 *
	 * 获取电子印章
	 * @param staffID
	 * @param companyID
	 * @return
	 */
	public List<BaseBean> getStampList(String staffID, String companyID,String parameter);

	/**
	 *
	 * 删除印章
	 * @param staffID
	 * @param enterpriseStampID
	 * @return
	 */
	public void deleteStamp(String staffID,String enterpriseStampID);

	/**
	 *
	 * 使用状态
	 * @param staffID
	 * @param enterpriseStampID
	 * @return
	 */
	public void setUseStamp(String staffID,String enterpriseStampID);


	/**
	 *
	 * 添加修改印章
	 * @param staffID
	 * @param companyID
	 * @param enterpriseStamp
	 */
	public void addUpdateStamp(String staffID, String companyID, EnterpriseStamp enterpriseStamp,String realpath);

	/**
	 *
	 * 获取信息
	 * @param enterpriseStampID
	 * @return
	 */
	public EnterpriseStamp getEditInfo(String enterpriseStampID);

	/**
	 *
	 * 获取待审核印章
	 * @param pageSize
	 * @param pageNumber
	 * @param parameter
	 * @param companyID
	 * @return
	 */
	public PageForm getAuditStampList(int pageSize,int pageNumber,String parameter,String companyID,String auditStatus);

	/**
	 *
	 * 审核
	 * @param enterpriseStampID
	 * @param auditStatus
	 * @param rejectReason
	 * @param staffID
	 * @return
	 */
	public void auditStamp(String enterpriseStampID,String auditStatus,String rejectReason,String staffID);

}
