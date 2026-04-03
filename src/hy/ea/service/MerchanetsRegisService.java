package hy.ea.service;

import com.wechat.bo.sft.*;
import hy.ea.bo.company.ContactCompany;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import mobile.tiantai.android.bo.AuditRecord;

import java.util.List;
import java.util.Map;

public interface MerchanetsRegisService {


	/**
	 * 入驻的商户
	 * @param pageNumber
	 * @param pageSize
	 * @param applyParam
	 * @param companyID
	 * @return
	 */
	public PageForm getMerchanetsList(int pageNumber, int pageSize, ApplyParam applyParam, String companyID,String state);

	/**
	 *
	 * 微信进件
	 * @param out_request_no
	 * @param staffID
	 * @return
	 */
	public ApplyResult synwx(String out_request_no, String staffID,String path);

	/**
	 *
	 * 查询进件结果
	 * @param out_request_no
	 * @return
	 */
	public ApplyResult  searchSyncResult(String out_request_no);


	/**
	 *
	 * 完善资料
	 * @param applyParam
	 * @return
	 */
	public ApplyParam improveMaterial(ApplyParam applyParam);


	/**
	 *
	 * 获取资料
	 * @param out_request_no
	 * @return
	 */
	public ApplyParam getMaterialPage(String out_request_no);

	/**
	 *
	 * 获取往来单位
	 * @param applyID
	 * @return
	 */
	public ContactCompany getContactC(String applyID);


	/**
	 *
	 * 保存资料
	 * @param applyParam
	 * @param business_license_info
	 * @param organization_cert_info
	 * @param id_card_info
	 * @param account_info
	 * @param contact_info
	 * @param sales_scene_info
	 * @return
	 */
	public ApplyParam saveMaterial(ApplyParam applyParam,BusinessLicenseInfo business_license_info,OrganizationCertInfo organization_cert_info,IdCardInfo id_card_info,AccountInfo account_info,ContactInfo contact_info,SalesSceneInfo sales_scene_info,ContactCompany contactCompany,String mode);


	/**
	 *
	 * 提交审核
	 * @param staffID
	 * @param companyID
	 * @param contactCompany
	 * @return
	 */
	public ContactCompany submitAudit(String staffID,String companyID,ContactCompany contactCompany);

	public void saveInfo(BusinessLicenseInfo business_license_info,OrganizationCertInfo organization_cert_info,IdCardInfo id_card_info,AccountInfo account_info,ContactInfo contact_info,SalesSceneInfo sales_scene_info);


	public Map<String,Object> getInfo(BusinessLicenseInfo business_license_info,OrganizationCertInfo organization_cert_info,IdCardInfo id_card_info,AccountInfo account_info,ContactInfo contact_info,SalesSceneInfo sales_scene_info);
	/**
	 *
	 * 获取银行类型
	 * @return
	 */
	public List<BaseBean> getBackTypeList();

	/**
	 *
	 * 获取认证类型
	 * @param companyID
	 * @return
	 */
	public ApplyParam getApplyInfo(String companyID);

	/**
	 *
	 *店铺信息
	 * @param companyID
	 * @return
	 */
	public Map<String,Object> getApplydp(String companyID);



	/**
	 *
	 *  申请
	 * @return
	 */
	public ApplyParam getApply(String out_request_no);

	/**
	 * 获取往来单位
	 * @param companyID
	 * @return
	 */
	public ContactCompany getCCompany(String companyID);

	/**
	 *
	 * 获取认证结果
	 * @param ccompanyID
	 * @return
	 */
	public AuditRecord queryAudit(String ccompanyID);




}