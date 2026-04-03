package hy.ea.bo.human.publicreceipts;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 人事单据公共表
 */
public class Publicreceipts implements BaseBean,ExcelBean,Serializable {
	private String prKey;                    //主键
	private String prID;                     //逻辑主键
	private String pcompanyID;               //父公司ID
	private String companyID;                //公司ID
	private String organizationID;           //部门ID
	//private String deptID;                 //选择部门ID
	private String voucherNum;               //凭证号
	private String principal;                //责任人name
	private String principalID;				//责任人ID
	private String principalOrganizationID;  //责任人部门ID
	private String type;                     //单据类型
	private String applyDate;                //申请日期(制单时间)
	private String firstAuditor;             //部门主管审核人
	private String secondAuditor;            //人力资源部审核人
	private String finalAuditor;             //总经理审核人
	private String receiptsStatus;           //单据状态   P:待审  F:部门主管审核通过  S:人力资源部审核通过  A:总经理审核通过  R:驳回作废  B:撤销 D:草稿
	private String operator;                 //操作人(制单人)
	private String accessory;                //附件
	
	private String accountinformID;			  //被罚人的staffid;
	private String refundSccid;				  //被罚人sccid
	private String accountinform;             //帐号信息（微分金帐号  最终惩罚的人）
	private String custype;					  //帐号级别
	private String firstAuditComments;		  //部门主管审核意见
	private String secondAuditComments;		  //人力资源部审核意见
	private Date refundDate; 				  //拒绝时间
	
	
	@SuppressWarnings("unused")
	private String companyName;
	
	private static Map<String , String> oMap;
	
	public static String[] columnPublicreceipt() {
		String[] titles={"序号","公司","部门","凭证号","责任人","单据类型","申请日期","制单人","部门主管审核人","人力资源部审核人","总经理审核人","单据状态"};
		return titles;
	}
	
	@Override
	public String[] properties() { 
		return null;
	}
	/**
	 * 凭证号
	 * @param voucherNum
	 */
	public String getVoucherNum() {
		return voucherNum;
	}
	/**
	 * 凭证号
	 * @param voucherNum
	 */
	public void setVoucherNum(String voucherNum) {
		this.voucherNum = voucherNum;
	}
	/**
	 * 主键
	 * @param prKey
	 */
	public String getPrKey() {
		return prKey;
	}
	/**
	 * 主键
	 * @param prKey
	 */
	public void setPrKey(String prKey) {
		this.prKey = prKey;
	}
	/**
	 * 逻辑主键
	 * @param prID
	 */
	public String getPrID() {
		return prID;
	}
	/**
	 * 逻辑主键
	 * @param prID
	 */
	public void setPrID(String prID) {
		this.prID = prID;
	}
	/**
	 * 父公司ID
	 * @param pcompanyID
	 */
	public String getPcompanyID() {
		return pcompanyID;
	}
	/**
	 * 父公司ID
	 * @param pcompanyID
	 */
	public void setPcompanyID(String pcompanyID) {
		this.pcompanyID = pcompanyID;
	}
	/**
	 * 公司ID
	 * @param companyID
	 */
	public String getCompanyID() {
		return companyID;
	}
	/**
	 * 公司ID
	 * @param companyID
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	/**
	 * 部门ID
	 * @param organizationID
	 */
	public String getOrganizationID() {
		return organizationID;
	}
	/**
	 * 部门ID
	 * @param organizationID
	 */
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	/**
	 * 责任人
	 * @param principal
	 */
	public String getPrincipal() {
		return principal;
	}
	/**
	 * 责任人
	 * @param principal
	 */
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	/**
	 * 责任人部门ID
	 * @param principalOrganizationID
	 */
	public String getPrincipalOrganizationID() {
		return principalOrganizationID;
	}
	/**
	 * 责任人部门ID
	 * @param principalOrganizationID
	 */
	public void setPrincipalOrganizationID(String principalOrganizationID) {
		this.principalOrganizationID = principalOrganizationID;
	}
	/**
	 * 单据类型
	 * @param type
	 */
	public String getType() {
		return type;
	}
	/**
	 * 单据类型
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 申请日期(制单时间)
	 * @param applyDate
	 */
	public String getApplyDate() {
		return applyDate;
	}
	/**
	 * 申请日期(制单时间)
	 * @param applyDate
	 */
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	/**
	 * 一审人
	 * @param firstAuditor
	 */
	public String getFirstAuditor() {
		return firstAuditor;
	}
	/**
	 * 一审人
	 * @param firstAuditor
	 */
	public void setFirstAuditor(String firstAuditor) {
		this.firstAuditor = firstAuditor;
	}
	/**
	 * 二审人
	 * @param secondAuditor
	 */
	public String getSecondAuditor() {
		return secondAuditor;
	}
	/**
	 * 二审人
	 * @param secondAuditor
	 */
	public void setSecondAuditor(String secondAuditor) {
		this.secondAuditor = secondAuditor;
	}
	/**
	 * 三审人
	 * @param finalAuditor
	 */
	public String getFinalAuditor() {
		return finalAuditor;
	}
	/**
	 * 三审人
	 * @param finalAuditor
	 */
	public void setFinalAuditor(String finalAuditor) {
		this.finalAuditor = finalAuditor;
	}
	/**
	 * 单据状态   P:待审  F:部门主管审核通过  S:人力资源部审核通过  A:总经理审核通过  R:驳回作废  B:撤销
	 * @param receiptsStatus
	 */
	public String getReceiptsStatus() {
		return receiptsStatus;
	}
	/**
	 * 单据状态   P:待审  F:部门主管审核通过  S:人力资源部审核通过  A:总经理审核通过  R:驳回作废  B:撤销
	 * @param receiptsStatus
	 */
	public void setReceiptsStatus(String receiptsStatus) {
		this.receiptsStatus = receiptsStatus;
	}
	/**
	 * 操作人(制单人)
	 * @param operator
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * 操作人(制单人)
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAccessory() {
		return accessory;
	}
	/**
	 * 附件
	 * @param accessory
	 */
	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}
	
	public String getCompanyName() {
		return oMap.get(principalOrganizationID);
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public static Map<String, String> getOMap() {
		return oMap;
	}

	public String getPrincipalID() {
		return principalID;
	}

	public void setPrincipalID(String principalID) {
		this.principalID = principalID;
	}

	public String getAccountinform() {
		return accountinform;
	}

	public void setAccountinform(String accountinform) {
		this.accountinform = accountinform;
	}

	public String getCustype() {
		return custype;
	}

	public void setCustype(String custype) {
		this.custype = custype;
	}

	public String getAccountinformID() {
		return accountinformID;
	}

	public void setAccountinformID(String accountinformID) {
		this.accountinformID = accountinformID;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getRefundSccid() {
		return refundSccid;
	}

	public void setRefundSccid(String refundSccid) {
		this.refundSccid = refundSccid;
	}

	public String getFirstAuditComments() {
		return firstAuditComments;
	}

	public void setFirstAuditComments(String firstAuditComments) {
		this.firstAuditComments = firstAuditComments;
	}

	public String getSecondAuditComments() {
		return secondAuditComments;
	}

	public void setSecondAuditComments(String secondAuditComments) {
		this.secondAuditComments = secondAuditComments;
	}

	

	
	
}
