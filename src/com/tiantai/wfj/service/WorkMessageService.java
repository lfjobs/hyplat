package com.tiantai.wfj.service;

import java.util.List;
import java.util.Map;

import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import mobile.tiantai.android.bo.AuditRecord;

public interface WorkMessageService {
	/**
	 * 同意审核
	 * @param audi  审核id
	 * @param AuditComment 审核意见
	 * @return
	 */
	 boolean agreeMessage(String audi, String AuditComment, String ccompanyID);
	 
	 /**
		 * 驳回审核
		 * @param audi  审核id
		 * @param AuditComment 审核意见
		 * @return
		 */
	 boolean rejectedMessage(String audi, String AuditComment, String ccompanyID);
	 
	 /**
	  * 查询审核的进度人
	  * @param batchNum 相关人的进度
	  * @return
	  */
	 List<BaseBean> findAudiPeople(String batchNum);
	 
	 /**
	  * 利用auid查看Audi
	  * @param auid
	  * @return
	  */
	 AuditRecord findByAuid(String auid);
	 
	 /**
	  * 查询相关的公司
	  * @param companyid
	  * @return
	  */
	 Map<String,List<BaseBean>> findCompany(String companyid);
	 
	 /**
	  * 
	  * 查询当前公司的正常部门
	  * @param companyId
	  * @return
	  */
	 List<BaseBean> findOrg(String companyId);
	 
	 /**
	  * 
	  * 查询人
	  * @param companyId
	  * @param orgID
	  * @return
	  */
	 List<BaseBean> findStaffList(String companyId, String orgID);
	 
}
