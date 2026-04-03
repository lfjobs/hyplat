package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 企业印章管理
 * 
 * @author Administrator
 * 
 */
public class EnterpriseStamp implements BaseBean, ExcelBean ,java.io.Serializable{

	private String enterpriseStampKey;
	private String enterpriseStampID;

	private String companyID;
	private String organizationID;
	
	private String companyName;
	private String organizationName;

	private String enterpriseStampCode; // 编号
	private String stampName; // 印章名称
	private String stampContent;// 印章内容
	private Date awardedAnnual;// 授予年度
	private Date registeredAnnual;// 注册年度
	private Date createTime;// 创建时间
	private String scanningAccessories;// 扫描附件
	private String stampType;// 印章类型
	private String stampNote;// 备注
	private String type;// 区分是印章还是签名
	private String useStatus;//使用状态：停用；使用中；
	private String useStaffID;
	private Date useDate;
	private String gore;//用于区分是普通印章还是电子印章；  e:电子 g:普通
    private String responsibleID;//责任人ID
    private String responsibleName;
    private String isStuStamp;//如果是1；即为学员上的章，否则为空；
	private String delStatus;//删除状态99 删除
	private String delStaffID;//删除人
	private Date delDate;//删除日期


	//审核

	private String auditStatus;//审核状态
	private String  rejectReason;//驳回理由
	private String auditCompanyID;//审核公司ID
	private String auditID;//审核人
	private Date  auditDate;//审核时间



	private String signId;//君子签ID





	public String getIsStuStamp() {
		return isStuStamp;
	}

	public void setIsStuStamp(String isStuStamp) {
		this.isStuStamp = isStuStamp;
	}

	public static String[] columnHeadings() {
		String[] titles = { "序号", "编号", "名称", "内容", "授予年度", "注册年度", "印章类型",
				"创建时间", "备注" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { enterpriseStampCode, stampName, stampContent,
				String.format("%1$tF", awardedAnnual),
				String.format("%1$tF", registeredAnnual), stampType,
				String.format("%1$tc", createTime), stampNote };
		return properties;
	}

	public String getEnterpriseStampKey() {
		return enterpriseStampKey;
	}

	public void setEnterpriseStampKey(String enterpriseStampKey) {
		this.enterpriseStampKey = enterpriseStampKey;
	}

	public String getEnterpriseStampID() {
		return enterpriseStampID;
	}

	public void setEnterpriseStampID(String enterpriseStampID) {
		this.enterpriseStampID = enterpriseStampID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getEnterpriseStampCode() {
		return enterpriseStampCode;
	}

	public void setEnterpriseStampCode(String enterpriseStampCode) {
		this.enterpriseStampCode = enterpriseStampCode;
	}

	public String getStampName() {
		return stampName;
	}

	public void setStampName(String stampName) {
		this.stampName = stampName;
	}

	public String getStampContent() {
		return stampContent;
	}

	public void setStampContent(String stampContent) {
		this.stampContent = stampContent;
	}

	public Date getAwardedAnnual() {
		return awardedAnnual;
	}

	public void setAwardedAnnual(Date awardedAnnual) {
		this.awardedAnnual = awardedAnnual;
	}

	public Date getRegisteredAnnual() {
		return registeredAnnual;
	}

	public void setRegisteredAnnual(Date registeredAnnual) {
		this.registeredAnnual = registeredAnnual;
	}

	public String getScanningAccessories() {
		return scanningAccessories;
	}

	public void setScanningAccessories(String scanningAccessories) {
		this.scanningAccessories = scanningAccessories;
	}

	public String getStampType() {
		return stampType;
	}

	public void setStampType(String stampType) {
		this.stampType = stampType;
	}

	public String getStampNote() {
		return stampNote;
	}

	public void setStampNote(String stampNote) {
		this.stampNote = stampNote;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public String getGore() {
		return gore;
	}

	public void setGore(String gore) {
		this.gore = gore;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public String getResponsibleID() {
		return responsibleID;
	}

	public void setResponsibleID(String responsibleID) {
		this.responsibleID = responsibleID;
	}
	public String getResponsibleName() {
		return responsibleName;
	}

	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getDelStaffID() {
		return delStaffID;
	}

	public void setDelStaffID(String delStaffID) {
		this.delStaffID = delStaffID;
	}

	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	public String getUseStaffID() {
		return useStaffID;
	}

	public void setUseStaffID(String useStaffID) {
		this.useStaffID = useStaffID;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditCompanyID() {
		return auditCompanyID;
	}

	public void setAuditCompanyID(String auditCompanyID) {
		this.auditCompanyID = auditCompanyID;
	}

	public String getAuditID() {
		return auditID;
	}

	public void setAuditID(String auditID) {
		this.auditID = auditID;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}
}
